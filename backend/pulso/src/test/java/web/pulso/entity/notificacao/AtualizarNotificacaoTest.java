package web.pulso.entity.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.pulso.entity.Notificacao;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.entity.enums.TipoNotificacao;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@DisplayName("Notificacao - Atualizar")
class AtualizarNotificacaoTest {

    private Notificacao notificacaoPendente;
    private Notificacao notificacaoLida;

    @BeforeEach
    void setUp() {
        Usuario remetente = new Usuario();
        remetente.setId(1L);

        Usuario destinatario = new Usuario();
        destinatario.setId(2L);

        notificacaoPendente = new Notificacao();
        notificacaoPendente.setTitulo("Notificacao Pendente");
        notificacaoPendente.setRemetente(remetente);
        notificacaoPendente.setDestinatario(destinatario);
        notificacaoPendente.setTipo(TipoNotificacao.values()[0]);
        notificacaoPendente.setStatus(StatusNotificacao.PENDENTE);

        notificacaoLida = new Notificacao();
        notificacaoLida.setTitulo("Notificacao Lida");
        notificacaoLida.setRemetente(remetente);
        notificacaoLida.setDestinatario(destinatario);
        notificacaoLida.setTipo(TipoNotificacao.values()[0]);
        notificacaoLida.setStatus(StatusNotificacao.LIDA);
        notificacaoLida.setDataLeitura(LocalDateTime.now().minusHours(1));
    }

    @Test
    @DisplayName("[CE-valido] Deve aceitar mudança de status de PENDENTE para LIDA")
    void deveAceitarMudancaDeStatusParaLida() {
        // Act
        notificacaoPendente.setStatus(StatusNotificacao.LIDA);

        // Assert
        assertThat(notificacaoPendente.getStatus()).isEqualTo(StatusNotificacao.LIDA);
    }

    @Test
    @DisplayName("[CE-valido] Deve registrar dataLeitura ao marcar notificacao como lida")
    void deveRegistrarDataLeituraAoMarcarComoLida() {
        // Arrange
        LocalDateTime agora = LocalDateTime.now();

        // Act
        notificacaoPendente.setStatus(StatusNotificacao.LIDA);
        notificacaoPendente.setDataLeitura(agora);

        // Assert
        assertThat(notificacaoPendente.getDataLeitura()).isEqualTo(agora);
    }

    @Test
    @DisplayName("[CE-valido] dataLeitura deve ser nula enquanto status for PENDENTE")
    void dataLeituraDeveSerNulaEnquantoStatusForPendente() {
        // Assert — estado consistente: pendente sem data de leitura
        assertThat(notificacaoPendente.getStatus()).isEqualTo(StatusNotificacao.PENDENTE);
        assertThat(notificacaoPendente.getDataLeitura()).isNull();
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção ao tentar reverter status de LIDA para PENDENTE")
    void deveLancarExcecaoAoReverterStatusParaPendente() {
        // A entity não protege a transição de status — qualquer setter é permitido.
        // Lacuna: uma notificação lida não deveria poder voltar para PENDENTE.
        assertThatThrownBy(() -> notificacaoLida.setStatus(StatusNotificacao.PENDENTE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("LIDA");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção ao marcar como lida sem informar dataLeitura")
    void deveLancarExcecaoAoMarcarLidaSemDataLeitura() {
        // Lacuna: setar status=LIDA sem dataLeitura resulta em estado inconsistente.
        // A entity deveria garantir atomicidade dessa transição.
        assertThatThrownBy(() -> notificacaoPendente.setStatus(StatusNotificacao.LIDA))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("dataLeitura");
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção ao tentar alterar remetente após criação")
    void deveLancarExcecaoAoAlterarRemetente() {
        Usuario novoRemetente = new Usuario();
        novoRemetente.setId(99L);

        assertThatThrownBy(() -> notificacaoPendente.setRemetente(novoRemetente))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[LACUNA | ESPERADO FALHAR] Deve lançar exceção ao tentar alterar destinatario após criação")
    void deveLancarExcecaoAoAlterarDestinatario() {
        // Lacuna: destinatario é definido na criação e não deveria ser alterável.
        Usuario novoDestinatario = new Usuario();
        novoDestinatario.setId(99L);

        assertThatThrownBy(() -> notificacaoPendente.setDestinatario(novoDestinatario))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("[CE-valido] Deve aceitar atualização de titulo")
    void deveAceitarAtualizacaoDeTitulo() {
        // Arrange
        String novoTitulo = "Titulo Atualizado";

        // Act
        notificacaoPendente.setTitulo(novoTitulo);

        // Assert
        assertThat(notificacaoPendente.getTitulo()).isEqualTo(novoTitulo);
    }

    @Test
    @DisplayName("[CE-valido] Deve aceitar atualização de conteudo")
    void deveAceitarAtualizacaoDeConteudo() {
        // Arrange
        String novoConteudo = "Conteudo atualizado com novas informações.";

        // Act
        notificacaoPendente.setConteudo(novoConteudo);

        // Assert
        assertThat(notificacaoPendente.getConteudo()).isEqualTo(novoConteudo);
    }

    @Test
    @DisplayName("[CE-valido] Deve aceitar conteudo nulo na atualização pois campo não é obrigatório")
    void deveAceitarConteudoNuloNaAtualizacao() {
        // Act
        notificacaoPendente.setConteudo(null);

        // Assert
        assertThat(notificacaoPendente.getConteudo()).isNull();
    }


    @Test
    @DisplayName("[CE-consistencia] Notificacao LIDA deve ter dataLeitura preenchida")
    void notificacaoLidaDeveTermDataLeituraPreenchida() {
        // Assert — estado esperado de uma notificação lida
        assertThat(notificacaoLida.getStatus()).isEqualTo(StatusNotificacao.LIDA);
        assertThat(notificacaoLida.getDataLeitura()).isNotNull();
    }

    @Test
    @DisplayName("[CE-consistencia] Notificacao PENDENTE deve ter dataLeitura nula")
    void notificacaoPendenteDeveTermDataLeituraNula() {
        // Assert — estado esperado de uma notificação ainda não lida
        assertThat(notificacaoPendente.getStatus()).isEqualTo(StatusNotificacao.PENDENTE);
        assertThat(notificacaoPendente.getDataLeitura()).isNull();
    }
}