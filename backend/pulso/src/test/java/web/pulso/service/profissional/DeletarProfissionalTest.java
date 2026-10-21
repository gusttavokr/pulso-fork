package web.pulso.service.profissional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyLong;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método deletar da classe ProfissionalService")
public class DeletarProfissionalTest
        extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve deletar profissional com sucesso")
    void deveDeletarProfissionalComSucesso() {

        Long id = 1L;

        when(profissionalRepository.existsById(id))
                .thenReturn(true);

        doNothing()
                .when(profissionalRepository)
                .deleteById(id);

        assertDoesNotThrow(
                () -> profissionalService.deletar(id));

        verify(profissionalRepository)
                .existsById(id);

        verify(profissionalRepository)
                .deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não existir")
    void deveLancarErroQuandoProfissionalNaoExistir() {

        Long id = 999L;

        when(profissionalRepository.existsById(id))
                .thenReturn(false);

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.deletar(id));

        verify(profissionalRepository)
                .existsById(id);

        verify(profissionalRepository, never())
                .deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(profissionalRepository.existsById(null))
                .thenThrow(
                        new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> profissionalService.deletar(null));

        verify(profissionalRepository, never())
                .deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando deleteById lançar exceção")
    void deveLancarErroQuandoDeleteByIdLancarExcecao() {

        Long id = 1L;

        when(profissionalRepository.existsById(id))
                .thenReturn(true);

        doThrow(
                new RuntimeException("Erro ao deletar"))
                .when(profissionalRepository)
                .deleteById(id);

        assertThrows(
                RuntimeException.class,
                () -> profissionalService.deletar(id));

        verify(profissionalRepository)
                .deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro quando existsById lançar exceção")
    void deveLancarErroQuandoExistsByIdLancarExcecao() {

        Long id = 1L;

        when(profissionalRepository.existsById(id))
                .thenThrow(
                        new RuntimeException("Erro no banco"));

        assertThrows(
                RuntimeException.class,
                () -> profissionalService.deletar(id));

        verify(profissionalRepository, never())
                .deleteById(anyLong());
    }

    // atualmente permite deletar profissional vinculado
    @Test
    @DisplayName("Deve lançar erro quando profissional possuir vínculos")
    void deveLancarErroQuandoProfissionalPossuirVinculos() {

        Long id = 1L;

        when(profissionalRepository.existsById(id))
                .thenReturn(true);

        assertThrows(
                Exception.class,
                () -> profissionalService.deletar(id));
    }
}