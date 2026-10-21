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
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método recusarTroca da classe TrocaService")
public class RecusarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve recusar troca com sucesso")
    void deveRecusarTrocaComSucesso() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        TrocaResponseDTO resultado = trocaService.recusarTroca(trocaId);

        assertNotNull(resultado);

        assertEquals(
                StatusTroca.RECUSADO,
                troca.getStatus());

        verify(trocaRepository)
                .save(troca);

        verify(trocaMapper)
                .toResponseDTO(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não existir")
    void deveLancarErroQuandoTrocaNaoExistir() {

        Long trocaId = 1L;

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.recusarTroca(trocaId));

        verify(trocaRepository, never())
                .save(any());

        verifyNoInteractions(trocaMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não estiver pendente")
    void deveLancarErroQuandoTrocaNaoEstiverPendente() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.recusarTroca(trocaId));

        verify(trocaRepository, never())
                .save(any());

        verifyNoInteractions(trocaMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando trocaId for nulo")
    void deveLancarErroQuandoTrocaIdForNulo() {

        when(trocaRepository.findById(null))
                .thenThrow(
                        new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> trocaService.recusarTroca(null));

        verify(trocaRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando repository lançar exceção no save")
    void deveLancarErroQuandoRepositoryLancarExcecaoNoSave() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(trocaRepository.save(troca))
                .thenThrow(
                        new RuntimeException("Erro ao salvar"));

        assertThrows(
                RuntimeException.class,
                () -> trocaService.recusarTroca(trocaId));

        verify(trocaRepository)
                .save(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar null")
    void deveLancarErroQuandoMapperRetornarNull() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        when(trocaRepository.save(troca))
                .thenReturn(troca);

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(null);

        TrocaResponseDTO resultado = trocaService.recusarTroca(trocaId);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca já estiver recusada")
    void deveLancarErroQuandoTrocaJaEstiverRecusada() {

        Long trocaId = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.RECUSADO);

        when(trocaRepository.findById(trocaId))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.recusarTroca(trocaId));
    }
}