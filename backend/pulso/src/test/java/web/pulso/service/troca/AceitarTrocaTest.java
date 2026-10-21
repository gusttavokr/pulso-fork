package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.profissional.PlantaoFactory.criarOutroPlantaoValido;
import static web.pulso.service.profissional.PlantaoFactory.criarPlantaoValido;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método aceitarTroca da classe TrocaService")
public class AceitarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve aceitar troca com sucesso")
    void deveAceitarTrocaComSucesso() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Plantao plantaoDestino = criarOutroPlantaoValido();

        profissionalOrigem.setPlantoesConfirmados(
                new ArrayList<>(List.of(plantaoOrigem)));

        profissionalDestino.setPlantoesConfirmados(
                new ArrayList<>(List.of(plantaoDestino)));

        plantaoOrigem.setProfissionaisConfirmados(
                new HashSet<>(Set.of(profissionalOrigem)));

        plantaoDestino.setProfissionaisConfirmados(
                new HashSet<>(Set.of(profissionalDestino)));

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissionalOrigem);

        troca.setProfissionalDestino(profissionalDestino);

        troca.setPlantaoOrigem(plantaoOrigem);

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.of(plantaoDestino));

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        TrocaResponseDTO resultado = trocaService.aceitarTroca(
                trocaId,
                plantaoDestinoId);

        assertNotNull(resultado);

        assertEquals(
                StatusTroca.CONFIRMADO,
                troca.getStatus());

        assertTrue(
                plantaoDestino.getProfissionaisConfirmados()
                        .contains(profissionalOrigem));

        assertTrue(
                plantaoOrigem.getProfissionaisConfirmados()
                        .contains(profissionalDestino));

        verify(plantaoRepository)
                .saveAll(anyList());

        verify(trocaRepository)
                .save(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não existir")
    void deveLancarErroQuandoTrocaNaoExistir() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));

        verifyNoInteractions(plantaoRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não estiver pendente")
    void deveLancarErroQuandoTrocaNaoEstiverPendente() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));

        verifyNoInteractions(plantaoRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão destino for nulo")
    void deveLancarErroQuandoPlantaoDestinoForNulo() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        assertThrows(
                BadRequestException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        null));

        verifyNoInteractions(plantaoRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão destino não existir")
    void deveLancarErroQuandoPlantaoDestinoNaoExistir() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));
    }

    // atualmente aceita profissional origem sem vínculo no plantão origem
    @Test
    @DisplayName("Deve lançar erro quando profissional origem não estiver no plantão origem")
    void deveLancarErroQuandoProfissionalOrigemNaoEstiverNoPlantaoOrigem() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Plantao plantaoDestino = criarOutroPlantaoValido();

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissionalOrigem);

        troca.setProfissionalDestino(profissionalDestino);

        troca.setPlantaoOrigem(plantaoOrigem);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.of(plantaoDestino));

        assertThrows(
                ConflictException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));
    }

    // atualmente aceita profissional destino sem vínculo no plantão destino
    @Test
    @DisplayName("Deve lançar erro quando profissional destino não estiver no plantão destino")
    void deveLancarErroQuandoProfissionalDestinoNaoEstiverNoPlantaoDestino() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Plantao plantaoDestino = criarOutroPlantaoValido();

        plantaoOrigem.setProfissionaisConfirmados(
                new HashSet<>(Set.of(profissionalOrigem)));

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissionalOrigem);

        troca.setProfissionalDestino(profissionalDestino);

        troca.setPlantaoOrigem(plantaoOrigem);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.of(plantaoDestino));

        assertThrows(
                ConflictException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));
    }

    // atualmente aceita troca para o mesmo plantão
    @Test
    @DisplayName("Deve lançar erro quando plantão origem e destino forem iguais")
    void deveLancarErroQuandoPlantaoOrigemEDestinoForemIguais() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 1L;

        Profissional profissionalOrigem = criarProfissionalValido();

        Profissional profissionalDestino = criarOutroProfissionalValido();

        Plantao plantao = criarPlantaoValido();

        plantao.setProfissionaisConfirmados(
                new HashSet<>(
                        Set.of(
                                profissionalOrigem,
                                profissionalDestino)));

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissionalOrigem);

        troca.setProfissionalDestino(profissionalDestino);

        troca.setPlantaoOrigem(plantao);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.of(plantao));

        assertThrows(
                ConflictException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));
    }

    // atualmente aceita troca com profissional destino nulo
    @Test
    @DisplayName("Deve lançar erro quando profissional destino for nulo")
    void deveLancarErroQuandoProfissionalDestinoForNulo() {

        Long trocaId = 1L;

        Long plantaoDestinoId = 2L;

        Profissional profissionalOrigem = criarProfissionalValido();

        Plantao plantaoOrigem = criarPlantaoValido();

        Plantao plantaoDestino = criarOutroPlantaoValido();

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setProfissionalOrigem(profissionalOrigem);

        troca.setProfissionalDestino(null);

        troca.setPlantaoOrigem(plantaoOrigem);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(plantaoRepository.findById(plantaoDestinoId))
                .thenReturn(Optional.of(plantaoDestino));

        assertThrows(
                ConflictException.class,
                () -> trocaService.aceitarTroca(
                        trocaId,
                        plantaoDestinoId));
    }
}