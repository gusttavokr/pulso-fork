package web.pulso.dto.response.notificacao;

import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.entity.enums.TipoNotificacao;

import java.time.LocalDateTime;
import java.util.Map;

public record NotificacaoDTO(
                Long id,
                String titulo,
                Map<String, Object> conteudo, // JSON
                Long idRemetente,
                Long idDestinatario,
                TipoNotificacao tipo,
                StatusNotificacao status,
                LocalDateTime dataCriacao,
                LocalDateTime dataLeitura) {
}