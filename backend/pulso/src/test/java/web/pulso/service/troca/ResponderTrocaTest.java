package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;
import static web.pulso.service.troca.RespostaTrocaRequestDTOFactory.criarRespostaTrocaRequestDTOAceita;
import static web.pulso.service.troca.RespostaTrocaRequestDTOFactory.criarRespostaTrocaRequestDTORecusa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import web.pulso.dto.request.RespostaTrocaRequestDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.service.TrocaService;

@DisplayName("Testes do método responderTroca da classe TrocaService")
public class ResponderTrocaTest extends TrocaServiceBaseTest {

    @Spy
    @InjectMocks
    protected TrocaService trocaService;

    @Test
    @DisplayName("Deve aceitar troca com sucesso")
    void deveAceitarTrocaComSucesso() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        doReturn(responseDTO)
                .when(trocaService)
                .aceitarTroca(
                        dto.getTrocaId(),
                        dto.getPlantaoDestinoId());

        TrocaResponseDTO resultado = trocaService.responderTroca(dto, idUsuario);

        assertNotNull(resultado);

        verify(trocaService)
                .aceitarTroca(
                        dto.getTrocaId(),
                        dto.getPlantaoDestinoId());

        verify(trocaService, never())
                .recusarTroca(any());
    }

    @Test
    @DisplayName("Deve recusar troca com sucesso")
    void deveRecusarTrocaComSucesso() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTORecusa();

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        doReturn(responseDTO)
                .when(trocaService)
                .recusarTroca(dto.getTrocaId());

        TrocaResponseDTO resultado = trocaService.responderTroca(dto, idUsuario);

        assertNotNull(resultado);

        verify(trocaService)
                .recusarTroca(dto.getTrocaId());

        verify(trocaService, never())
                .aceitarTroca(any(), any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não for destino")
    void deveLancarErroQuandoUsuarioNaoForDestino() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> trocaService.responderTroca(dto, idUsuario));

        verify(trocaService, never())
                .aceitarTroca(any(), any());

        verify(trocaService, never())
                .recusarTroca(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        assertThrows(
                NullPointerException.class,
                () -> trocaService.responderTroca(null, 1L));
    }

    @Test
    @DisplayName("Deve lançar erro quando idUsuario for nulo")
    void deveLancarErroQuandoIdUsuarioForNulo() {

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        null))
                .thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> trocaService.responderTroca(dto, null));
    }

    // atualmente aceita resposta com aceita = null
    @Test
    @DisplayName("Deve lançar erro quando aceita for nulo")
    void deveLancarErroQuandoAceitaForNulo() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        dto.setAceita(null);

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        doReturn(responseDTO)
                .when(trocaService)
                .recusarTroca(dto.getTrocaId());

        assertThrows(
                BadRequestException.class,
                () -> trocaService.responderTroca(dto, idUsuario));
    }

    // atualmente permite aceitar troca sem plantão destino
    @Test
    @DisplayName("Deve lançar erro quando aceitar troca sem plantão destino")
    void deveLancarErroQuandoAceitarTrocaSemPlantaoDestino() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        dto.setPlantaoDestinoId(null);

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> trocaService.responderTroca(dto, idUsuario));
    }

    @Test
    @DisplayName("Deve lançar erro quando aceitarTroca lançar exceção")
    void deveLancarErroQuandoAceitarTrocaLancarExcecao() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        doThrow(new ConflictException("Erro"))
                .when(trocaService)
                .aceitarTroca(
                        dto.getTrocaId(),
                        dto.getPlantaoDestinoId());

        assertThrows(
                ConflictException.class,
                () -> trocaService.responderTroca(dto, idUsuario));
    }

    @Test
    @DisplayName("Deve lançar erro quando recusarTroca lançar exceção")
    void deveLancarErroQuandoRecusarTrocaLancarExcecao() {

        Long idUsuario = 1L;

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTORecusa();

        when(trocaRepository
                .existsByIdAndProfissionalDestinoUsuarioId(
                        dto.getTrocaId(),
                        idUsuario))
                .thenReturn(true);

        doThrow(new ConflictException("Erro"))
                .when(trocaService)
                .recusarTroca(dto.getTrocaId());

        assertThrows(
                ConflictException.class,
                () -> trocaService.responderTroca(dto, idUsuario));
    }
}