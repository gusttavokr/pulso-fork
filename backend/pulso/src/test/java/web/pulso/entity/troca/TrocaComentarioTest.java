package web.pulso.entity.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Troca;

@DisplayName("Testes de comentário da entidade Troca")
public class TrocaComentarioTest {

    @Test
    @DisplayName("Deve aceitar comentário nulo")
    void deveAceitarComentarioNulo() {

        Troca troca = criarTrocaValida();

        troca.setComentario(null);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando comentário estiver vazio")
    void deveLancarErroQuandoComentarioEstiverVazio() {

        Troca troca = criarTrocaValida();

        troca.setComentario("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O comentário da troca não pode estar vazio.");
    }

    @Test
    @DisplayName("Deve lançar erro quando comentário possuir apenas espaços")
    void deveLancarErroQuandoComentarioPossuirApenasEspacos() {

        Troca troca = criarTrocaValida();

        troca.setComentario("   ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O comentário da troca não pode estar vazio.");
    }

    @Test
    @DisplayName("Deve aceitar comentário com tamanho máximo permitido")
    void deveAceitarComentarioComTamanhoMaximoPermitido() {

        Troca troca = criarTrocaValida();

        troca.setComentario("a".repeat(255));

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando comentário ultrapassar tamanho máximo")
    void deveLancarErroQuandoComentarioUltrapassarTamanhoMaximo() {

        Troca troca = criarTrocaValida();

        troca.setComentario("a".repeat(256));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O comentário da troca não pode possuir mais de 255 caracteres.");
    }

    /*
     * Poderia fazer uso do @ParameterizedTest, mas prefiro deixar assim pra
     * facilitar o entendimento
     * 
     * @ParameterizedTest
     * 
     * @NullAndEmptySource
     * 
     * @ValueSource(strings = { " ", "   ", "\t" })
     * 
     * @DisplayName("Deve lançar erro quando comentário for inválido")
     * void deveLancarErroQuandoComentarioForInvalido(String comentario)
     */

    @Test
    @DisplayName("Deve aceitar comentário com espaços no início e fim")
    void deveAceitarComentarioComEspacosNoInicioEFim() {

        Troca troca = criarTrocaValida();

        troca.setComentario("   Comentário válido   ");

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve remover espaços no início e fim do comentário")
    void deveRemoverEspacosNoInicioEFimDoComentario() {

        Troca troca = criarTrocaValida();

        troca.setComentario("   Comentário válido   ");

        troca.validar();

        assertThat(troca.getComentario())
                .isEqualTo("Comentário válido");
    }

    @Test
    @DisplayName("Deve lançar erro quando comentário possuir tags HTML")
    void deveLancarErroQuandoComentarioPossuirTagsHtml() {

        Troca troca = criarTrocaValida();

        troca.setComentario("<script>alert('teste')</script>");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O comentário da troca não pode possuir tags HTML.");
    }

}
