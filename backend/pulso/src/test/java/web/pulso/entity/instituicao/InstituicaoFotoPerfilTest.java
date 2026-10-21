package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de foto de perfil da entidade Instituicao")
class InstituicaoFotoPerfilTest {

    @Test
    @DisplayName("Deve lançar erro quando foto de perfil for nula")
    void deveLancarErroQuandoFotoPerfilForNula() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setFotoPerfil(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A foto de perfil da instituição é obrigatória.");
    }

    @Test
    @DisplayName("Deve lançar erro quando foto de perfil estiver vazia")
    void deveLancarErroQuandoFotoPerfilEstiverVazia() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setFotoPerfil("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A foto de perfil da instituição é obrigatória.");
    }
}
