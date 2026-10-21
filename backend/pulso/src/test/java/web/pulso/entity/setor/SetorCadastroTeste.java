package web.pulso.entity.setor;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.setor.SetorFactory.criarSetorValido;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

@DisplayName("Testes para Cadastro de Setor")
public class SetorCadastroTeste {
    
    // Nome do Setor

    @Test
    @DisplayName("Aceitar: Setor válido")
    public void setorValido(){
        Setor setor = criarSetorValido();
        assertDoesNotThrow(setor::validar);
    }

    @Test
    @DisplayName("Erro: Nome não informado")
    public void nomeNaoInformado(){
        Setor setor = criarSetorValido();

        setor.setNome(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O nome do setor é obrigatório.");
    }

    @Test
    @DisplayName("Erro: Nome vazio")
    public void nomeVazio(){
        Setor setor = criarSetorValido();

        setor.setNome("");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O nome do setor é obrigatório.");
    }

    @Test
    @DisplayName("Erro: Nome com espaços")
    public void nomeEspaco(){
        Setor setor = criarSetorValido();
        setor.setNome("  ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O nome do setor é obrigatório.");
    }
    
    @Test
    @DisplayName("Aceitar: Nome com mínimo de caracteres")
    public void nome3caracteres(){
        Setor setor = criarSetorValido();
        setor.setNome("UTI");

        assertDoesNotThrow(setor::validar);
    }

    @Test
    @DisplayName("Erro: Nome com menos de 3 caracteres")
    public void nomeMenor(){
        Setor setor = criarSetorValido();
        setor.setNome("ui");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O nome do setor deve ter no mínimo 3 caracteres");
    }

    @Test
    @DisplayName("Aceitar: Nome com máximo de caracteres")
    public void nome255caracteres(){
        Setor setor = criarSetorValido();
        setor.setNome("U".repeat(255));

        assertDoesNotThrow(setor::validar);
    }

    @Test
    @DisplayName("Erro: Nome com mais de 255 caracteres")
    public void nomeMaior(){
        Setor setor = criarSetorValido();
        setor.setNome("A".repeat(256));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O nome do setor deve ter no máximo 255 caracteres");
    }

    // Especialidade do Setor

    @ParameterizedTest
    @EnumSource(Especialidade.class)
    @DisplayName("Aceitar: Setor com Especialidade válida")
    public void especialidadeUnica(Especialidade especialidade){
        Setor setor = criarSetorValido();
        setor.setEspecialidade(especialidade);

        assertDoesNotThrow(setor::validar);
    }

    @Test
    @DisplayName("Erro: Setor com Especialidade Nula")
    public void especialidadeNula(){
        Setor setor = criarSetorValido();
        setor.setEspecialidade(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            setor::validar);

        assertThat(exception.getMessage()).isEqualTo("O setor deve possuir uma especialidade.");
    }
}
