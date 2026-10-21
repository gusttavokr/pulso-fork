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


@DisplayName("Escala - Criar")
class CriarEscalaTest {

    private Setor setorValido;
    private LocalDate mesValido;

    @BeforeEach
    void setUp() {
        setorValido = new Setor();
        setorValido.setId(1L);
        mesValido = LocalDate.now().plusMonths(2).withDayOfMonth(1);
    }

    @Test
    @DisplayName("[CE-nome-valido | VL-min] Deve criar escala quando nome tiver exatamente 3 caracteres")
    void deveCriarQuandoNomeTiverExatamente3Caracteres() {
        // Arrange
        String nome = "Esc"; // VL: mínimo válido

        // Act
        Escala escala = buildEscala(nome, mesValido, setorValido);
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getNome()).isEqualTo(nome);
        assertThat(escala.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
    }

    @Test
    @DisplayName("[CE-nome-valido] Deve criar escala quando nome tiver tamanho entre 3 e 255 caracteres")
    void deveCriarQuandoNomeTiverTamanhoNormal() {
        // Arrange
        String nome = "Escala Fevereiro Setor 1";

        // Act
        Escala escala = buildEscala(nome, mesValido, setorValido);
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getNome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("[CE-nome-valido | VL-max] Deve criar escala quando nome tiver exatamente 255 caracteres")
    void deveCriarQuandoNomeTiverExatamente255Caracteres() {
        // Arrange
        String nome = "A".repeat(255); // VL: máximo válido

        // Act
        Escala escala = buildEscala(nome, mesValido, setorValido);
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getNome()).hasSize(255);
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção quando nome for nulo")
    void deveLancarExcecaoQuandoNomeForNulo() {
        // Arrange
        Escala escala = buildEscala(null, mesValido, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-nome-invalido | VL-abaixo-min] Deve lançar exceção quando nome tiver exatamente 2 caracteres")
    void deveLancarExcecaoQuandoNomeTiverExatamente2Caracteres() {
        // Arrange
        String nome = "Es"; // VL: um abaixo do mínimo

        Escala escala = buildEscala(nome, mesValido, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-nome-invalido | VL-acima-max] [ESPERADO FALHAR] Deve lançar exceção quando nome tiver 256 caracteres")
    void deveLancarExcecaoQuandoNomeTiverMaisDe255Caracteres() {
        // Arrange
        String nome = "A".repeat(256); // VL: um acima do máximo

        Escala escala = buildEscala(nome, mesValido, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("255");
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção quando nome for string vazia")
    void deveLancarExcecaoQuandoNomeForVazio() {
        // Arrange
        Escala escala = buildEscala("", mesValido, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção quando nome for apenas espaços em branco")
    void deveLancarExcecaoQuandoNomeForApenasEspacos() {
        // Arrange
        // trim() é aplicado antes de checar length, então "   " tem 0 chars após trim.
        Escala escala = buildEscala("   ", mesValido, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-mes-valido] Deve criar escala quando mes for o mês atual (não está no passado)")
    void deveCriarQuandoMesForOMesAtual() {
        // Arrange
        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);

        assumeTrue(LocalDate.now().getDayOfMonth() <= 24,
                "Cenário ignorado: muito próximo do mês seguinte para garantir os 7 dias de antecedência");

        Escala escala = buildEscala("Escala Mês Atual", mesAtual, setorValido);

        // Act
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getMesReferencia()).isEqualTo(mesAtual);
    }

    @Test
    @DisplayName("[CE-mes-valido] Deve criar escala quando mes for data futura")
    void deveCriarQuandoMesForDataFutura() {
        // Arrange — mesValido já é +2 meses no setUp

        // Act
        Escala escala = buildEscala("Escala Futura", mesValido, setorValido);
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getMesReferencia()).isEqualTo(mesValido);
    }

    @Test
    @DisplayName("[CE-mes-invalido] [ESPERADO FALHAR] Deve lançar exceção quando mesReferencia for nulo")
    void deveLancarExcecaoQuandoMesForNulo() {
        // Arrange
        Escala escala = buildEscala("Escala Valida", null, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("mês");
    }

    @Test
    @DisplayName("[CE-mes-invalido | VL] Deve lançar exceção quando mes for o mês imediatamente anterior ao atual")
    void deveLancarExcecaoQuandoMesForMesAnterior() {
        // Arrange
        LocalDate mesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(1); // VL: um mês antes

        Escala escala = buildEscala("Escala Passada", mesPassado, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("passado");
    }

    @Test
    @DisplayName("[CE-mes-invalido] Deve lançar exceção quando mes for ano anterior")
    void deveLancarExcecaoQuandoMesForAnoAnterior() {
        // Arrange
        LocalDate mesAnoPassado = LocalDate.now().minusYears(1).withDayOfMonth(1);

        Escala escala = buildEscala("Escala Ano Passado", mesAnoPassado, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("passado");
    }

    @Test
    @DisplayName("[VL-antecedencia] Deve lançar exceção quando início do mês estiver a menos de 7 dias")
    void deveLancarExcecaoQuandoMesForMuitoProximo() {
        // Arrange
        LocalDate hoje = LocalDate.now();
        LocalDate inicioPróximoMes = hoje.plusMonths(1).withDayOfMonth(1);
        long diasAteProximoMes = java.time.temporal.ChronoUnit.DAYS.between(hoje, inicioPróximoMes);

        assumeTrue(diasAteProximoMes < 7,
                "Cenário só executado quando o próximo mês começa em menos de 7 dias (dia >= 25)");

        Escala escala = buildEscala("Escala Próxima", inicioPróximoMes, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("antecedência");
    }

    @Test
    @DisplayName("[VL-antecedencia] Deve criar quando mês estiver com folga de antecedência suficiente")
    void deveCriarQuandoMesEstiverComAntecedenciaSuficiente() {
        // Arrange
        Escala escala = buildEscala("Escala Com Folga", mesValido, setorValido);

        // Act
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getMesReferencia()).isEqualTo(mesValido);
    }

    @Test
    @DisplayName("[CE-status] Deve definir status como RASCUNHO automaticamente ao criar escala")
    void deveDefinirStatusComoRascunhoAoCriar() {
        // Arrange
        Escala escala = buildEscala("Escala Status", mesValido, setorValido);
        assertThat(escala.getStatus()).isNull(); // sem status antes do persist

        // Act
        simularPrePersist(escala);

        // Assert
        assertThat(escala.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
    }

    @Test
    @DisplayName("[CE-status] Não deve sobrescrever status se já estava definido antes do persist")
    void naoDeveSobrescreverStatusSeJaDefinido() {
        // Arrange — simula entity reconstruída com status já atribuído
        Escala escala = buildEscala("Escala Status Existente", mesValido, setorValido);
        escala.setStatus(StatusEscala.PUBLICADA);

        // Act
        simularPrePersist(escala);

        // Assert — a lógica atual só define RASCUNHO se status == null
        assertThat(escala.getStatus()).isEqualTo(StatusEscala.PUBLICADA);
    }

    @Test
    @DisplayName("[CE-setor-invalido] [ESPERADO FALHAR] Deve lançar exceção quando setor for nulo")
    void deveLancarExcecaoQuandoSetorForNulo() {
        // Arrange
        Escala escala = buildEscala("Escala Sem Setor", mesValido, null);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("setor");
    }

    @Test
    @DisplayName("[CE-multiplos-invalidos] Deve lançar exceção quando nome e mes forem ambos inválidos")
    void deveLancarExcecaoQuandoNomeEMesForemInvalidos() {
        // Arrange — nome curto + mês passado: entity falha no primeiro erro encontrado
        LocalDate mesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        Escala escala = buildEscala("Es", mesPassado, setorValido);

        // Act & Assert
        assertThatThrownBy(() -> simularPrePersist(escala))
                .isInstanceOf(BadRequestException.class);
    }

    // =========================================================================
    // Utilitários privados
    // =========================================================================

    private Escala buildEscala(String nome, LocalDate mes, Setor setor) {
        Escala escala = new Escala();
        escala.setNome(nome);
        escala.setMesReferencia(mes);
        escala.setSetor(setor);
        return escala;
    }


    private void simularPrePersist(Escala escala) {
        try {
            var method = Escala.class.getDeclaredMethod("aoCriar");
            method.setAccessible(true);
            method.invoke(escala);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException re) throw re;
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assumeTrue(boolean condition, String reason) {
        org.junit.jupiter.api.Assumptions.assumeTrue(condition, reason);
    }
}