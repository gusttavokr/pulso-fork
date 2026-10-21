package web.pulso.entity.profissional;

import org.junit.jupiter.api.Test;

import web.pulso.entity.Profissional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Teste de validação da entidade Profissional")
public class ProfissionalValidacaoTest {

    @Test
    @DisplayName("Deve aceitar profissional válido")
    void deveAceitarProfissionalValido() {

        Profissional profissional = criarProfissionalValido();

        assertDoesNotThrow(profissional::validar);
    }

}
