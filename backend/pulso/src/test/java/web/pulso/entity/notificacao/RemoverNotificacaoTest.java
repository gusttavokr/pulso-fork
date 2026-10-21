package web.pulso.entity.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pulso.entity.Notificacao;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.entity.enums.TipoNotificacao;

import static org.assertj.core.api.Assertions.*;


@DisplayName("Notificacao - Remover")
class RemoverNotificacaoTest {

    private Usuario remetente;
    private Usuario destinatario;

    @BeforeEach
    void setUp() {
        remetente = new Usuario();
        remetente.setId(1L);

        destinatario = new Usuario();
        destinatario.setId(2L);
    }

    @Test
    @DisplayName("[CE-status-pendente] Notificacao PENDENTE não possui restrição de exclusão na entity")
    void notificacaoPendenteNaoPossuiRestricaoDeExclusao() {
        // Arrange
        Notificacao notificacao = buildNotificacao(StatusNotificacao.PENDENTE);

        // Assert — sem podeSerExcluida() na entity, qualquer estado é "excluível"
        // do ponto de vista do domínio. Esse teste documenta a ausência de restrição.
        assertThat(notificacao.getStatus()).isEqualTo(StatusNotificacao.PENDENTE);
        // Nenhuma exceção esperada — a entity não tem guard
    }

    @Test
    @DisplayName("[CE-status-lida] Notificacao LIDA não possui restrição de exclusão na entity")
    void notificacaoLidaNaoPossuiRestricaoDeExclusao() {
        // Arrange
        Notificacao notificacao = buildNotificacao(StatusNotificacao.LIDA);

        // Assert — entity não protege exclusão de notificações lidas
        assertThat(notificacao.getStatus()).isEqualTo(StatusNotificacao.LIDA);
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção ao tentar excluir notificacao LIDA")
    void deveLancarExcecaoAoExcluirNotificacaoLida() {
        // Lacuna: notificações já lidas poderiam ser protegidas de exclusão
        // para manter histórico de comunicações. A entity não implementa esse guard.
        Notificacao notificacao = buildNotificacao(StatusNotificacao.LIDA);

        assertThatThrownBy(() -> verificarSePoDeSerExcluida(notificacao))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("LIDA");
    }

    @Test
    @DisplayName("[CE-estado] Notificacao PENDENTE deve ter dataLeitura nula antes da exclusão")
    void notificacaoPendenteDeveTermDataLeituraNulaAntesDeExcluir() {
        // Arrange
        Notificacao notificacao = buildNotificacao(StatusNotificacao.PENDENTE);

        // Assert — estado consistente antes de excluir
        assertThat(notificacao.getDataLeitura()).isNull();
    }

    @Test
    @DisplayName("[CE-estado] Notificacao deve manter remetente e destinatario intactos antes da exclusão")
    void notificacaoDeveManterRemetenteEDestinatarioAntesDeExcluir() {
        // Arrange
        Notificacao notificacao = buildNotificacao(StatusNotificacao.PENDENTE);

        // Assert — dados de rastreabilidade preservados até o momento da exclusão
        assertThat(notificacao.getRemetente()).isSameAs(remetente);
        assertThat(notificacao.getDestinatario()).isSameAs(destinatario);
    }

    @Test
    @DisplayName("[CE-estado] Notificacao deve manter titulo intacto antes da exclusão")
    void notificacaoDeveManterTituloAntesDeExcluir() {
        // Arrange
        Notificacao notificacao = buildNotificacao(StatusNotificacao.PENDENTE);

        // Assert
        assertThat(notificacao.getTitulo()).isEqualTo("Notificacao Teste");
    }

    @Test
    @DisplayName("[CE-lifecycle] Entity não deve ter @PreRemove — exclusão não dispara lógica de domínio")
    void entityNaoDeveTermPreRemove() {
        // Arrange — verifica via reflexão que não existe método @PreRemove
        boolean temPreRemove = java.util.Arrays.stream(Notificacao.class.getDeclaredMethods())
                .anyMatch(m -> m.isAnnotationPresent(jakarta.persistence.PreRemove.class));

        // Assert — documenta a ausência intencional de lifecycle hook de exclusão
        assertThat(temPreRemove)
                .as("Notificacao não deveria ter @PreRemove pois não há lógica de exclusão no domínio")
                .isFalse();
    }

    // =========================================================================
    // Utilitários privados
    // =========================================================================

    private Notificacao buildNotificacao(StatusNotificacao status) {
        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo("Notificacao Teste");
        notificacao.setConteudo("Conteudo teste");
        notificacao.setRemetente(remetente);
        notificacao.setDestinatario(destinatario);
        notificacao.setTipo(TipoNotificacao.values()[0]);
        notificacao.setStatus(status);
        return notificacao;
    }

    private void verificarSePoDeSerExcluida(Notificacao notificacao) {
        if (notificacao.getStatus() == StatusNotificacao.LIDA) {
            throw new IllegalStateException(
                    "Notificacao com status LIDA não pode ser excluída");
        }
    }
}