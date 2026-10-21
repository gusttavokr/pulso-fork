package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método deletar da classe InstituicaoService")
class DeletarInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve deletar instituição com sucesso")
    void deveDeletarInstituicaoComSucesso() {

        Long id = 1L;

        when(instituicaoRepository.existsById(id))
                .thenReturn(true);

        doNothing()
                .when(instituicaoRepository)
                .deleteById(id);

        assertDoesNotThrow(
                () -> instituicaoService.deletar(id));

        verify(instituicaoRepository).existsById(id);

        verify(instituicaoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long id = 999L;

        when(instituicaoRepository.existsById(id))
                .thenReturn(false);

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.deletar(id));

        verify(instituicaoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(instituicaoRepository.existsById(null))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> instituicaoService.deletar(null));

        verify(instituicaoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando deleteById lançar exceção")
    void deveLancarErroQuandoDeleteByIdLancarExcecao() {

        Long id = 1L;

        when(instituicaoRepository.existsById(id))
                .thenReturn(true);

        doThrow(new RuntimeException("Erro ao deletar"))
                .when(instituicaoRepository)
                .deleteById(id);

        assertThrows(
                RuntimeException.class,
                () -> instituicaoService.deletar(id));

        verify(instituicaoRepository).deleteById(id);
    }
}
