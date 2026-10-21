package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método deletar da classe TrocaService")
public class DeletarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve deletar troca com sucesso")
    void deveDeletarTrocaComSucesso() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        doNothing()
                .when(trocaRepository)
                .delete(troca);

        trocaService.deletar(id);

        verify(trocaRepository)
                .delete(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não existir")
    void deveLancarErroQuandoTrocaNaoExistir() {

        Long id = 1L;

        when(trocaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.deletar(id));

        verify(trocaRepository, never())
                .delete(Mockito.any(Troca.class));
    }

    @Test
    @DisplayName("Deve lançar erro quando troca estiver confirmada")
    void deveLancarErroQuandoTrocaEstiverConfirmada() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.deletar(id));

        verify(trocaRepository, never())
                .delete(Mockito.any(Troca.class));
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(trocaRepository.findById(null))
                .thenThrow(
                        new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> trocaService.deletar(null));

        verify(trocaRepository, never())
                .delete(Mockito.any(Troca.class));
    }

    @Test
    @DisplayName("Deve lançar erro quando repository lançar exceção no delete")
    void deveLancarErroQuandoRepositoryLancarExcecaoNoDelete() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        doThrow(new RuntimeException("Erro delete"))
                .when(trocaRepository)
                .delete(troca);

        assertThrows(
                RuntimeException.class,
                () -> trocaService.deletar(id));
    }

    // atualmente permite deletar troca recusada sem auditoria
    @Test
    @DisplayName("Deve lançar erro quando troca estiver recusada")
    void deveLancarErroQuandoTrocaEstiverRecusada() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.RECUSADO);

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        assertThrows(
                ConflictException.class,
                () -> trocaService.deletar(id));
    }
}