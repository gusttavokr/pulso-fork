package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.profissional.PlantaoFactory.criarPlantaoValido;
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
import web.pulso.exception.business.BusinessException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método criar da classe TrocaService")
public class CriarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve criar troca simples com sucesso")
    void deveCriarTrocaSimplesComSucesso() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(null);

        Profissional profissionalOrigem = criarProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Troca troca = criarTrocaValida();

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(plantaoRepository.findById(dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantaoOrigem));

        when(trocaMapper.toEntity(dto))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        TrocaResponseDTO resultado = trocaService.criar(dto, idUsuario);

        assertNotNull(resultado);

        assertEquals(
                TipoTroca.SIMPLES,
                troca.getTipo());

        assertEquals(
                StatusTroca.PENDENTE,
                troca.getStatus());

        assertEquals(
                profissionalOrigem,
                troca.getProfissionalOrigem());

        assertEquals(
                plantaoOrigem,
                troca.getPlantaoOrigem());

        verify(trocaRepository)
                .save(troca);
    }

    @Test
    @DisplayName("Deve criar troca dupla com sucesso")
    void deveCriarTrocaDuplaComSucesso() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Troca troca = criarTrocaValida();

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(profissionalRepository.findById(
                dto.getProfissionalDestinoId()))
                .thenReturn(Optional.of(profissionalDestino));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantaoOrigem));

        when(trocaMapper.toEntity(dto))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        TrocaResponseDTO resultado = trocaService.criar(dto, idUsuario);

        assertNotNull(resultado);

        assertEquals(
                TipoTroca.DUPLA,
                troca.getTipo());

        assertEquals(
                profissionalDestino,
                troca.getProfissionalDestino());

        assertNull(troca.getPlantaoDestino());

        verify(trocaRepository)
                .save(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando idUsuario for nulo")
    void deveLancarErroQuandoIdUsuarioForNulo() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        assertThrows(
                BadRequestException.class,
                () -> trocaService.criar(dto, null));

        verifyNoInteractions(trocaRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando plantaoOrigemId for nulo")
    void deveLancarErroQuandoPlantaoOrigemIdForNulo() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setPlantaoOrigemId(null);

        assertThrows(
                BadRequestException.class,
                () -> trocaService.criar(dto, idUsuario));

        verifyNoInteractions(trocaRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional origem não existir")
    void deveLancarErroQuandoProfissionalOrigemNaoExistir() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.criar(dto, idUsuario));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão origem não existir")
    void deveLancarErroQuandoPlantaoOrigemNaoExistir() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissionalOrigem = criarProfissionalValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.criar(dto, idUsuario));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional destino não existir")
    void deveLancarErroQuandoProfissionalDestinoNaoExistir() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissionalOrigem = criarProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantaoOrigem));

        when(profissionalRepository.findById(
                dto.getProfissionalDestinoId()))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.criar(dto, idUsuario));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar entidade nula")
    void deveLancarErroQuandoMapperRetornarEntidadeNula() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissionalOrigem = criarProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantaoOrigem));

        when(trocaMapper.toEntity(dto))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> trocaService.criar(dto, idUsuario));
    }

    // atualmente aceita profissional destino igual ao origem
    @Test
    @DisplayName("Deve lançar erro quando profissional destino for igual ao origem")
    void deveLancarErroQuandoProfissionalDestinoForIgualAoOrigem() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissional = criarProfissionalValido();

        Plantao plantao = criarPlantaoValido();

        Troca troca = criarTrocaValida();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissional));

        when(profissionalRepository.findById(
                dto.getProfissionalDestinoId()))
                .thenReturn(Optional.of(profissional));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantao));

        when(trocaMapper.toEntity(dto))
                .thenReturn(troca);

        assertThrows(
                Exception.class,
                () -> trocaService.criar(dto, idUsuario));
    }

    // atualmente permite troca em plantão sem vínculo do profissional
    @Test
    @DisplayName("Deve lançar erro quando profissional não estiver no plantão")
    void deveLancarErroQuandoProfissionalNaoEstiverNoPlantao() {

        Long idUsuario = 1L;

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantao = criarPlantaoValido();

        Troca troca = criarTrocaValida();

        when(profissionalRepository.findByUsuarioId(idUsuario))
                .thenReturn(Optional.of(profissionalOrigem));

        when(profissionalRepository.findById(
                dto.getProfissionalDestinoId()))
                .thenReturn(Optional.of(profissionalDestino));

        when(plantaoRepository.findById(
                dto.getPlantaoOrigemId()))
                .thenReturn(Optional.of(plantao));

        when(trocaMapper.toEntity(dto))
                .thenReturn(troca);

        assertThrows(
                BusinessException.class,
                () -> trocaService.criar(dto, idUsuario));
    }
}