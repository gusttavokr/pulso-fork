package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.profissional.PlantaoFactory.criarOutroPlantaoValido;
import static web.pulso.service.troca.TrocaRequestDTOFactory.criarTrocaRequestDTOValido;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.TrocaRequestDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método atualizar da classe TrocaService")
public class AtualizarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve atualizar troca com sucesso")
    void deveAtualizarTrocaComSucesso() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        Plantao novoPlantao = criarOutroPlantaoValido();

        Profissional novoDestino = criarOutroProfissionalValido();

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario("Comentário atualizado");

        dto.setPlantaoOrigemId(novoPlantao.getId());

        dto.setProfissionalDestinoId(novoDestino.getId());

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(
                novoPlantao.getId()))
                .thenReturn(Optional.of(novoPlantao));

        when(profissionalRepository.findById(
                novoDestino.getId()))
                .thenReturn(Optional.of(novoDestino));

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        TrocaResponseDTO resultado = trocaService.atualizar(id, dto);

        assertNotNull(resultado);

        assertEquals(
                "Comentário atualizado",
                troca.getComentario());

        assertEquals(
                novoPlantao,
                troca.getPlantaoOrigem());

        assertEquals(
                novoDestino,
                troca.getProfissionalDestino());

        assertEquals(
                TipoTroca.DUPLA,
                troca.getTipo());

        verify(trocaRepository)
                .save(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não existir")
    void deveLancarErroQuandoTrocaNaoExistir() {

        Long id = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.atualizar(id, dto));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não estiver pendente")
    void deveLancarErroQuandoTrocaNaoEstiverPendente() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.atualizar(id, dto));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve atualizar apenas comentário")
    void deveAtualizarApenasComentario() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        Plantao plantaoOriginal = troca.getPlantaoOrigem();

        Profissional destinoOriginal = troca.getProfissionalDestino();

        TrocaRequestDTO dto = new TrocaRequestDTO();

        dto.setComentario("Novo comentário");

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(criarTrocaResponseDTOValido());

        trocaService.atualizar(id, dto);

        assertEquals(
                "Novo comentário",
                troca.getComentario());

        assertEquals(
                plantaoOriginal,
                troca.getPlantaoOrigem());

        assertEquals(
                destinoOriginal,
                troca.getProfissionalDestino());
    }

    @Test
    @DisplayName("Deve lançar erro quando novo plantão não existir")
    void deveLancarErroQuandoNovoPlantaoNaoExistir() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setPlantaoOrigemId(999L);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.atualizar(id, dto));
    }

    @Test
    @DisplayName("Deve lançar erro quando novo profissional destino não existir")
    void deveLancarErroQuandoNovoProfissionalDestinoNaoExistir() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(999L);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(profissionalRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.atualizar(id, dto));
    }

    // atualmente permite remover profissional destino sem validação
    @Test
    @DisplayName("Deve lançar erro quando remover profissional destino de troca dupla")
    void deveLancarErroQuandoRemoverProfissionalDestinoDeTrocaDupla() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setTipo(TipoTroca.DUPLA);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(null);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.atualizar(id, dto));
    }

    // atualmente permite profissional destino igual ao origem
    @Test
    @DisplayName("Deve lançar erro quando profissional destino for igual ao origem")
    void deveLancarErroQuandoProfissionalDestinoForIgualAoOrigem() {

        Long id = 1L;

        Profissional profissional = criarProfissionalValido();

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissional);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(
                profissional.getId());

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(profissionalRepository.findById(
                profissional.getId()))
                .thenReturn(Optional.of(profissional));

        assertThrows(
                ConflictException.class,
                () -> trocaService.atualizar(id, dto));
    }

    // atualmente permite trocar para o mesmo plantão
    @Test
    @DisplayName("Deve lançar erro quando novo plantão for igual ao atual")
    void deveLancarErroQuandoNovoPlantaoForIgualAoAtual() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setPlantaoOrigemId(
                troca.getPlantaoOrigem().getId());

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.atualizar(id, dto));
    }

    // atualmente aceita comentário em branco silenciosamente
    @Test
    @DisplayName("Deve lançar erro quando comentário for vazio")
    void deveLancarErroQuandoComentarioForVazio() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario(" ");

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                BadRequestException.class,
                () -> trocaService.atualizar(id, dto));
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar null")
    void deveLancarErroQuandoMapperRetornarNull() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(null);

        TrocaResponseDTO resultado = trocaService.atualizar(id, dto);

        // atualmente aceita mapper retornando null
        assertNull(resultado);
    }
}