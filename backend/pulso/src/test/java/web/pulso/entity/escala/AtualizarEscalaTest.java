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

@DisplayName("Escala - Atualizar")
class AtualizarEscalaTest {


    private Setor setorOriginal;
    private LocalDate mesOriginal;
    private Escala escalaRascunho;

    @BeforeEach
    void setUp() {
        setorOriginal = new Setor();
        setorOriginal.setId(1L);

        mesOriginal = LocalDate.now().plusMonths(2).withDayOfMonth(1);

        escalaRascunho = buildEscalaExistente("Escala Original", mesOriginal, setorOriginal, StatusEscala.RASCUNHO);
    }



    @Test
    @DisplayName("[CE-nome-valido | VL-min] Deve atualizar quando novo nome tiver exatamente 3 caracteres")
    void deveAtualizarQuandoNovoNomeTiverExatamente3Caracteres() {
        // Arrange
        String novoNome = "Esc"; // VL: mínimo válido

        // Act
        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);
        simularPreUpdate(escalaRascunho);

        // Assert
        assertThat(escalaRascunho.getNome()).isEqualTo(novoNome);
    }

    @Test
    @DisplayName("[CE-nome-valido] Deve atualizar quando novo nome tiver tamanho entre 3 e 255 caracteres")
    void deveAtualizarQuandoNovoNomeTiverTamanhoNormal() {
        // Arrange
        String novoNome = "Escala Fevereiro do Setor 1";

        // Act
        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);
        simularPreUpdate(escalaRascunho);

        // Assert
        assertThat(escalaRascunho.getNome()).isEqualTo(novoNome);
    }

    @Test
    @DisplayName("[CE-nome-valido | VL-max] Deve atualizar quando novo nome tiver exatamente 255 caracteres")
    void deveAtualizarQuandoNovoNomeTiverExatamente255Caracteres() {
        // Arrange
        String novoNome = "A".repeat(255); // VL: máximo válido

        // Act
        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);
        simularPreUpdate(escalaRascunho);

        // Assert
        assertThat(escalaRascunho.getNome()).hasSize(255);
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção quando novo nome for nulo")
    void deveLancarExcecaoQuandoNovoNomeForNulo() {
        // Arrange
        escalaRascunho.validarAlteracoesPermitidas(null, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(null);

        // Act & Assert
        assertThatThrownBy(() -> simularPreUpdate(escalaRascunho))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-nome-invalido | VL-abaixo-min] Deve lançar exceção quando novo nome tiver exatamente 2 caracteres")
    void deveLancarExcecaoQuandoNovoNomeTiverExatamente2Caracteres() {
        // Arrange
        String novoNome = "Es"; // VL: um abaixo do mínimo

        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);

        // Act & Assert
        assertThatThrownBy(() -> simularPreUpdate(escalaRascunho))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-nome-invalido | VL-acima-max] [ESPERADO FALHAR] Deve lançar exceção quando novo nome tiver 256 caracteres")
    void deveLancarExcecaoQuandoNovoNomeTiverMaisDe255Caracteres() {
        String novoNome = "A".repeat(256); // VL: um acima do máximo

        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);


        assertThatThrownBy(() -> simularPreUpdate(escalaRascunho))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("255");
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção em validarAlteracoesPermitidas quando novo nome for string vazia")
    void deveLancarExcecaoEmValidarQuandoNovoNomeForVazio() {

        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("", setorOriginal.getId(), mesOriginal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("vazio");
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve lançar exceção em validarAlteracoesPermitidas quando novo nome for apenas espaços")
    void deveLancarExcecaoEmValidarQuandoNovoNomeForApenasEspacos() {

        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("   ", setorOriginal.getId(), mesOriginal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("vazio");
    }


    @Test
    @DisplayName("[CE-status-invalido] Deve lançar exceção ao tentar atualizar escala PUBLICADA")
    void deveLancarExcecaoAoAtualizarEscalaPublicada() {

        Escala escalaPublicada = buildEscalaExistente(
                "Escala Publicada", mesOriginal, setorOriginal, StatusEscala.PUBLICADA);

        assertThatThrownBy(
                () -> escalaPublicada.validarAlteracoesPermitidas("Novo Nome", setorOriginal.getId(), mesOriginal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("publicada");
    }

    @Test
    @DisplayName("[CE-status-invalido] [ESPERADO FALHAR] Deve lançar exceção ao tentar atualizar escala ARQUIVADA")
    void deveLancarExcecaoAoAtualizarEscalaArquivada() {

        Escala escalaArquivada = buildEscalaExistente(
                "Escala Arquivada", mesOriginal, setorOriginal, StatusEscala.ARQUIVADA);

        assertThatThrownBy(
                () -> escalaArquivada.validarAlteracoesPermitidas("Novo Nome", setorOriginal.getId(), mesOriginal))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("[CE-status-valido] Deve permitir atualização quando escala estiver em RASCUNHO")
    void devePermitirAtualizacaoQuandoStatusForRascunho() {

        assertThatCode(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorOriginal.getId(), mesOriginal))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-setor-invalido] Deve lançar exceção ao tentar alterar o setor da escala")
    void deveLancarExcecaoAoTentarAlterarSetor() {
        // Arrange
        Long setorDiferenteId = 99L;

        // Act & Assert
        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorDiferenteId, mesOriginal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("setor");
    }

    @Test
    @DisplayName("[CE-setor-invalido] [ESPERADO FALHAR] Deve lançar exceção quando novo setorId for nulo")
    void deveLancarExcecaoQuandoNovoSetorIdForNulo() {

        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", null, mesOriginal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("informado"); // mensagem esperada para campo nulo
    }

    @Test
    @DisplayName("[CE-setor-valido] Deve permitir quando setorId for o mesmo da escala original")
    void devePermitirQuandoSetorIdForOMesmo() {

        assertThatCode(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorOriginal.getId(), mesOriginal))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-mes-invalido] Deve lançar exceção ao tentar alterar o mês de referência")
    void deveLancarExcecaoAoTentarAlterarMes() {
        // Arrange
        LocalDate mesDiferente = mesOriginal.plusMonths(1);

        // Act & Assert
        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorOriginal.getId(), mesDiferente))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("mês");
    }

    @Test
    @DisplayName("[CE-mes-invalido] [ESPERADO FALHAR] Deve lançar exceção quando novo mes for nulo")
    void deveLancarExcecaoQuandoNovoMesForNulo() {
        assertThatThrownBy(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorOriginal.getId(), null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("informado"); // mensagem esperada para campo nulo
    }

    @Test
    @DisplayName("[CE-mes-valido] Deve permitir quando novo mes for igual ao mes original")
    void devePermitirQuandoNovoMesForOMesmo() {

        assertThatCode(
                () -> escalaRascunho.validarAlteracoesPermitidas("Nome Novo", setorOriginal.getId(), mesOriginal))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-valido] Deve atualizar escala com sucesso quando todos os dados forem válidos")
    void deveAtualizarComSucessoQuandoDadosForemValidos() {
        // Arrange
        String novoNome = "Escala Atualizada com Sucesso";

        // Act
        escalaRascunho.validarAlteracoesPermitidas(novoNome, setorOriginal.getId(), mesOriginal);
        escalaRascunho.setNome(novoNome);
        simularPreUpdate(escalaRascunho);

        // Assert
        assertThat(escalaRascunho.getNome()).isEqualTo(novoNome);
        assertThat(escalaRascunho.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
        assertThat(escalaRascunho.getMesReferencia()).isEqualTo(mesOriginal);
        assertThat(escalaRascunho.getSetor().getId()).isEqualTo(setorOriginal.getId());
    }


    @Test
    @DisplayName("[CE-multiplos-invalidos] Deve lançar exceção quando status for PUBLICADA e nome for vazio")
    void deveLancarExcecaoQuandoStatusPublicadaENomeVazio() {
        // Arrange — PUBLICADA é verificada antes do nome em validarAlteracoesPermitidas
        Escala escalaPublicada = buildEscalaExistente(
                "Escala Publicada", mesOriginal, setorOriginal, StatusEscala.PUBLICADA);

        // Act & Assert — a primeira condição violada deve lançar
        assertThatThrownBy(
                () -> escalaPublicada.validarAlteracoesPermitidas("", setorOriginal.getId(), mesOriginal))
                .isInstanceOf(BadRequestException.class);
    }

    // =========================================================================
    // Utilitários privados
    // =========================================================================

    private Escala buildEscalaExistente(String nome, LocalDate mes, Setor setor, StatusEscala status) {
        Escala escala = new Escala();
        escala.setNome(nome);
        escala.setMesReferencia(mes);
        escala.setSetor(setor);
        escala.setStatus(status);
        return escala;
    }


    private void simularPreUpdate(Escala escala) {
        try {
            var method = Escala.class.getDeclaredMethod("aoAtualizar");
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
}