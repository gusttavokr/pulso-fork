package web.pulso.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import web.pulso.dto.response.notificacao.NotificacaoDTO;
import web.pulso.entity.Notificacao;
import web.pulso.entity.Usuario;

import java.util.Collections;
import java.util.Map;

@Component
public class NotificacaoMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificacaoDTO toResponseDTO(Notificacao n) {
        if (n == null)
            return null;
        return new NotificacaoDTO(
                n.getId(),
                n.getTitulo(),
                toMap(n.getConteudo()),
                n.getRemetente().getId(),
                n.getDestinatario().getId(),
                n.getTipo(),
                n.getStatus(),
                n.getDataCriacao(),
                n.getDataLeitura());
    }

    public Notificacao toEntity(NotificacaoDTO dto) {
        if (dto == null)
            return null;

        Notificacao n = new Notificacao();
        var remetente = new Usuario();
        var destinatario = new Usuario();

        remetente.setId(dto.idRemetente());
        destinatario.setId(dto.idDestinatario());

        n.setId(dto.id());
        n.setTitulo(dto.titulo());
        n.setRemetente(remetente);
        n.setDestinatario(remetente);
        n.setTipo(dto.tipo());
        n.setStatus(dto.status());
        n.setDataCriacao(dto.dataCriacao());
        n.setDataLeitura(dto.dataLeitura());
        n.setConteudo(toJson(dto.conteudo()));

        return n;
    }

    // ------------------ Conversores de conteudo ------------------

    private Map toMap(String json) {
        if (json == null || json.isBlank())
            return Collections.emptyMap();
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private String toJson(Map<String, Object> map) {
        if (map == null || map.isEmpty())
            return "{}";
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
