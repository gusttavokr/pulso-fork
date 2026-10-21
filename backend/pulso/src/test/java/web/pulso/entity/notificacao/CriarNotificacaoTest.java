package web.pulso.entity.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pulso.entity.Notificacao;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.entity.enums.TipoNotificacao;

import static org.assertj.core.api.Assertions.*;


@DisplayName("Notificacao - Criar")
class CriarNotificacaoTest {

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
    @DisplayName("[CE-status] Deve definir status como PENDENTE automaticamente quando status for nulo no @PrePersist")
    void deveDefinirStatusPendenteQuandoNulo() {
        // Arrange
        Notificacao notificacao = buildNotificacao();
        assertThat(notificacao.getStatus()).isNull(); // antes do persist

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getStatus()).isEqualTo(StatusNotificacao.PENDENTE);
    }

    @Test
    @DisplayName("[CE-status] Não deve sobrescrever status se já estava definido antes do @PrePersist")
    void naoDeveSobrescreverStatusSeJaDefinido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();
        notificacao.setStatus(StatusNotificacao.LIDA);

        // Act
        simularPrePersist(notificacao);

        // Assert — status LIDA deve ser mantido, não substituído por PENDENTE
        assertThat(notificacao.getStatus()).isEqualTo(StatusNotificacao.LIDA);
    }


    @Test
    @DisplayName("[CE-valido] Deve manter titulo atribuído após construção")
    void deveManterTituloAtribuido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();
        notificacao.setTitulo("Você tem um novo plantão");

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getTitulo()).isEqualTo("Você tem um novo plantão");
    }

    @Test
    @DisplayName("[CE-valido] Deve manter conteudo atribuído após construção")
    void deveManterConteudoAtribuido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();
        notificacao.setConteudo("Plantão no dia 15/02 às 08h no setor UTI.");

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getConteudo()).isEqualTo("Plantão no dia 15/02 às 08h no setor UTI.");
    }

    @Test
    @DisplayName("[CE-valido] Deve aceitar conteudo nulo pois campo não é obrigatório")
    void deveAceitarConteudoNulo() {
        // Arrange — conteudo não tem nullable=false
        Notificacao notificacao = buildNotificacao();
        notificacao.setConteudo(null);

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getConteudo()).isNull();
    }

    @Test
    @DisplayName("[CE-valido] Deve manter tipo atribuído após construção")
    void deveManterTipoAtribuido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();
        notificacao.setTipo(TipoNotificacao.values()[0]);

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getTipo()).isEqualTo(TipoNotificacao.values()[0]);
    }

    @Test
    @DisplayName("[CE-valido] Deve manter remetente atribuído após construção")
    void deveManterRemetenteAtribuido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getRemetente()).isSameAs(remetente);
    }

    @Test
    @DisplayName("[CE-valido] Deve manter destinatario atribuído após construção")
    void deveManterDestinatarioAtribuido() {
        // Arrange
        Notificacao notificacao = buildNotificacao();

        // Act
        simularPrePersist(notificacao);

        // Assert
        assertThat(notificacao.getDestinatario()).isSameAs(destinatario);
    }

    @Test
    @DisplayName("[CE-valido] dataLeitura deve ser nula ao criar uma notificacao nova")
    void dataLeituraDeveSerNulaAoCriar() {
        // Arrange
        Notificacao notificacao = buildNotificacao();

        // Act
        simularPrePersist(notificacao);

        // Assert — notificação recém criada ainda não foi lida
        assertThat(notificacao.getDataLeitura()).isNull();
    }

    // =========================================================================
    // LACUNAS — validações ausentes na entity
    // =========================================================================

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção quando titulo for nulo")
    void deveLancarExcecaoQuandoTituloForNulo() {
        // A entity não valida titulo null — @Column(nullable=false) é restrição
        // de banco, não de domínio. Lacuna: a entity deveria rejeitar titulo nulo.
        Notificacao notificacao = buildNotificacao();
        notificacao.setTitulo(null);

        assertThatThrownBy(() -> simularPrePersist(notificacao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("titulo");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção quando remetente for nulo")
    void deveLancarExcecaoQuandoRemetenteForNulo() {
        Notificacao notificacao = buildNotificacao();
        notificacao.setRemetente(null);

        assertThatThrownBy(() -> simularPrePersist(notificacao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("remetente");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção quando destinatario for nulo")
    void deveLancarExcecaoQuandoDestinatarioForNulo() {
        Notificacao notificacao = buildNotificacao();
        notificacao.setDestinatario(null);

        assertThatThrownBy(() -> simularPrePersist(notificacao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("destinatario");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção quando tipo for nulo")
    void deveLancarExcecaoQuandoTipoForNulo() {
        Notificacao notificacao = buildNotificacao();
        notificacao.setTipo(null);

        assertThatThrownBy(() -> simularPrePersist(notificacao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tipo");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção quando remetente e destinatario forem o mesmo usuario")
    void deveLancarExcecaoQuandoRemetenteEDestinatarioForemOMesmo() {
        // Regra de negócio ausente: faz sentido impedir auto-notificação.
        Notificacao notificacao = buildNotificacao();
        notificacao.setRemetente(remetente);
        notificacao.setDestinatario(remetente); // mesmo usuário

        assertThatThrownBy(() -> simularPrePersist(notificacao))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Notificacao buildNotificacao() {
        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo("Notificacao Teste");
        notificacao.setConteudo("Conteudo teste");
        notificacao.setRemetente(remetente);
        notificacao.setDestinatario(destinatario);
        notificacao.setTipo(TipoNotificacao.values()[0]);
        return notificacao;
    }

    private void simularPrePersist(Notificacao notificacao) {
        try {
            var method = Notificacao.class.getDeclaredMethod("onCreate");
            method.setAccessible(true);
            method.invoke(notificacao);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException re) throw re;
            throw new RuntimeException(cause);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}