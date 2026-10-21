package web.pulso.entity.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pulso.entity.Escala;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.BadRequestException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Escala - Excluir")
class RemoverEscalaTest {


    private Setor setorValido;
    private LocalDate mesValido;

    @BeforeEach
    void setUp() {
        setorValido = new Setor();
        setorValido.setId(1L);

        mesValido = LocalDate.now().plusMonths(2).withDayOfMonth(1);
    }

    @Test
    @DisplayName("[CE-status-valido] podeSerExcluida deve retornar true quando status for RASCUNHO")
    void podeSerExcluidaDeveRetornarTrueQuandoStatusForRascunho() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.RASCUNHO);

        // Act & Assert
        assertThat(escala.podeSerExcluida()).isTrue();
    }

    @Test
    @DisplayName("[CE-status-invalido] podeSerExcluida deve retornar false quando status for PUBLICADA")
    void podeSerExcluidaDeveRetornarFalseQuandoStatusForPublicada() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.PUBLICADA);

        // Act & Assert
        assertThat(escala.podeSerExcluida()).isFalse();
    }

    @Test
    @DisplayName("[CE-status-invalido] podeSerExcluida deve retornar false quando status for ARQUIVADA")
    void podeSerExcluidaDeveRetornarFalseQuandoStatusForArquivada() {
        Escala escala = buildEscala(StatusEscala.ARQUIVADA);

        assertThat(escala.podeSerExcluida()).isFalse();
    }

    @Test
    @DisplayName("[CE-status-invalido] [ESPERADO FALHAR] podeSerExcluida deve retornar false quando status for nulo")
    void podeSerExcluidaDeveRetornarFalseQuandoStatusForNulo() {
        // Arrange
        Escala escala = buildEscala(null);

        // Act & Assert
        assertThatCode(() -> escala.podeSerExcluida())
                .doesNotThrowAnyException();

        assertThat(escala.podeSerExcluida()).isFalse();
    }

    @Test
    @DisplayName("[CE-status-invalido] isPublicada deve retornar true quando status for PUBLICADA")
    void isPublicadaDeveRetornarTrueQuandoStatusForPublicada() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.PUBLICADA);

        // Act & Assert
        assertThat(escala.isPublicada()).isTrue();
    }

    @Test
    @DisplayName("[CE-status-valido] isPublicada deve retornar false quando status for RASCUNHO")
    void isPublicadaDeveRetornarFalseQuandoStatusForRascunho() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.RASCUNHO);

        // Act & Assert
        assertThat(escala.isPublicada()).isFalse();
    }

    @Test
    @DisplayName("[CE-status-valido] isPublicada deve retornar false quando status for ARQUIVADA")
    void isPublicadaDeveRetornarFalseQuandoStatusForArquivada() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.ARQUIVADA);

        // Act & Assert
        assertThat(escala.isPublicada()).isFalse();
    }

    @Test
    @DisplayName("[CE-consistencia] Escala PUBLICADA: isPublicada true e podeSerExcluida false devem ser mutuamente consistentes")
    void escalaPublicadaDeveSerConsistente() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.PUBLICADA);

        // Act & Assert — os dois métodos devem concordar: publicada não pode ser excluída
        assertThat(escala.isPublicada()).isTrue();
        assertThat(escala.podeSerExcluida()).isFalse();
    }

    @Test
    @DisplayName("[CE-consistencia] Escala RASCUNHO: isPublicada false e podeSerExcluida true devem ser mutuamente consistentes")
    void escalaRascunhoDeveSerConsistente() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.RASCUNHO);

        // Act & Assert — rascunho nunca está publicada e sempre pode ser excluída
        assertThat(escala.isPublicada()).isFalse();
        assertThat(escala.podeSerExcluida()).isTrue();
    }

    @Test
    @DisplayName("[CE-consistencia] Escala ARQUIVADA: isPublicada false e podeSerExcluida false — não publicada, mas também não excluível")
    void escalaArquivadaDeveSerConsistente() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.ARQUIVADA);

        // Act & Assert — ARQUIVADA é um terceiro estado: não publicada, mas protegida de exclusão
        assertThat(escala.isPublicada()).isFalse();
        assertThat(escala.podeSerExcluida()).isFalse();
    }

    @Test
    @DisplayName("[CE-status-valido] Deve permitir exclusão quando escala estiver em RASCUNHO")
    void devePermitirExclusaoQuandoEscalaEstiverEmRascunho() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.RASCUNHO);

        // Act — simula o guard que o service deveria fazer antes de deletar
        if (!escala.podeSerExcluida()) {
            fail("Escala em RASCUNHO deveria poder ser excluída");
        }

        // Assert — chegou aqui sem falhar: exclusão liberada
        assertThat(escala.podeSerExcluida()).isTrue();
    }

    @Test
    @DisplayName("[CE-status-invalido] Deve bloquear exclusão e lançar exceção quando escala estiver PUBLICADA")
    void deveLancarExcecaoAoTentarExcluirEscalaPublicada() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.PUBLICADA);

        // Act & Assert — simula o guard do service com lançamento de exceção
        assertThatThrownBy(() -> {
            if (!escala.podeSerExcluida()) {
                throw new BadRequestException("não é possivel excluir escala já publicada");
            }
        })
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("publicada");
    }

    @Test
    @DisplayName("[CE-status-invalido] Deve bloquear exclusão quando escala estiver ARQUIVADA")
    void deveLancarExcecaoAoTentarExcluirEscalaArquivada() {
        // Arrange
        Escala escala = buildEscala(StatusEscala.ARQUIVADA);

        // Act & Assert — ARQUIVADA também não pode ser excluída
        assertThatThrownBy(() -> {
            if (!escala.podeSerExcluida()) {
                throw new BadRequestException("não é possivel excluir escala arquivada");
            }
        })
                .isInstanceOf(BadRequestException.class);
    }

    // =========================================================================
    // Utilitários privados
    // =========================================================================

    private Escala buildEscala(StatusEscala status) {
        Escala escala = new Escala();
        escala.setNome("Escala Teste");
        escala.setMesReferencia(mesValido);
        escala.setSetor(setorValido);
        escala.setStatus(status);
        return escala;
    }
}