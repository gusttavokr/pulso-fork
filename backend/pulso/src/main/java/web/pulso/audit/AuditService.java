package web.pulso.audit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import web.pulso.entity.AuditLog;
import web.pulso.auth.UsuarioAutenticado;
import web.pulso.repository.AuditLogRepository;
import web.pulso.utils.TransactionContext;

import java.time.LocalDateTime;
import java.util.Iterator;

@Service
public class AuditService {

    private final AuditLogRepository repository;
    private final ObjectMapper mapper;

    public AuditService(AuditLogRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void audit(
            Object entity,
            Object entityId,
            String operation,
            Object[] oldState,
            Object[] newState,
            String[] propertyNames
    ) {

        try {

            HttpServletRequest request = getRequest();

            JsonNode oldJson = buildJson(oldState, propertyNames);
            JsonNode newJson = buildJson(newState, propertyNames);

            AuditLog log = new AuditLog();

            log.setTransactionId(TransactionContext.getTransactionId());
            log.setRequestId(
                    request != null
                            ?  MDC.get("requestId")
                            : null
            );

            log.setIp(request != null ? request.getRemoteAddr() : "system");
            log.setUserAgent(request != null ? request.getHeader("User-Agent") : "system");

            log.setUserId(getUserId());

            log.setEntity(entity.getClass().getSimpleName());
            log.setEntityId(entityId != null ? entityId.toString() : null);

            log.setOperation(operation);
            log.setEndpoint(request != null ? request.getRequestURI() : "internal");

            log.setOldData(oldJson);
            log.setNewData(newJson);

            log.setCreatedAt(LocalDateTime.now());

            repository.save(log);

            printAuditLog(log);

        } catch (Exception e) {
            throw new RuntimeException("Audit serialization error", e);
        }
    }

    private JsonNode buildJson(Object[] state, String[] propertyNames) {

        if (state == null) {
            return null;
        }

        ObjectNode json = mapper.createObjectNode();

        for (int i = 0; i < propertyNames.length; i++) {

            Object value = state[i];
            String name = propertyNames[i];

            if (value == null) {
                json.putNull(name);
                continue;
            }

            Class<?> type = value.getClass();

            if (value instanceof String
                    || value instanceof Number
                    || value instanceof Boolean
                    || value instanceof Character
                    || value instanceof java.time.temporal.Temporal
                    || type.isEnum()) {

                json.set(name, mapper.valueToTree(value));
                continue;
            }

            if (value instanceof java.util.Collection) {
                continue;
            }

            if (type.getName().contains("HibernateProxy")) {
                continue;
            }

            try {

                var idField = value.getClass().getDeclaredField("id");
                idField.setAccessible(true);

                Object idValue = idField.get(value);

                if (idValue != null) {
                    json.put(name + "Id", idValue.toString());
                }

            } catch (Exception ignored) {
                // se não tiver id, ignora
            }
        }

        return json;
    }
    
    private HttpServletRequest getRequest() {

        try {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes == null) {
                return null;
            }

            return attributes.getRequest();

        } catch (Exception e) {
            return null;
        }
    }

    private Long getUserId() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();


        if (principal instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {

            Object id = jwt.getClaim("id_usuario");

            if (id instanceof Number n) {
                return n.longValue();
            }

            if (id instanceof String s) {
                return Long.valueOf(s);
            }
        }

        // caso UserDetails custom
        if (principal instanceof UsuarioAutenticado usuario) {
            return usuario.getId();
        }

        return null;
    }

    private void printAuditLog(AuditLog log) {

        String RESET = "\u001B[0m";
        String CYAN = "\u001B[36m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String RED = "\u001B[31m";
        String PURPLE = "\u001B[35m";
        String GRAY = "\u001B[90m";

        String operationColor = switch (log.getOperation()) {
            case "INSERT" -> GREEN;
            case "UPDATE" -> YELLOW;
            case "DELETE" -> RED;
            default -> CYAN;
        };

        System.out.println();

        System.out.println(CYAN + "┌──────────────────────────── AUDIT LOG ────────────────────────────" + RESET);

        System.out.println(
                GRAY + "│ " + log.getCreatedAt() + RESET +
                        "   " + operationColor + log.getOperation() + RESET
        );

        System.out.println(
                "│ entity: " + PURPLE + log.getEntity() + RESET +
                        " (id=" + log.getEntityId() + ")"
        );

        System.out.println("│ userId: " + log.getUserId());
        System.out.println("│ endpoint: " + log.getEndpoint());
        System.out.println("│ ip: " + log.getIp());
        System.out.println("│ userAgent: " + log.getUserAgent());

        System.out.println("│");

        System.out.println(CYAN + "│ FIELD CHANGE" + RESET);
        System.out.println(indent(generateDiff(log.getOldData(), log.getNewData())));

        System.out.println("│");

        System.out.println(GRAY + "│ transaction: " + log.getTransactionId() + RESET);
        System.out.println(GRAY + "│ requestId: " + log.getRequestId() + RESET);

        System.out.println(CYAN + "└────────────────────────────────────────────────────────────────────" + RESET);
    }

    private String generateDiff(JsonNode oldJson, JsonNode newJson) {

        if (oldJson == null && newJson == null) {
            return "null";
        }

        if (oldJson == null) {
            return "+ INSERT\n" + formatJson(newJson);
        }

        if (newJson == null) {
            return "- DELETE\n" + formatJson(oldJson);
        }

        StringBuilder diff = new StringBuilder();

        Iterator<String> fields = newJson.fieldNames();

        while (fields.hasNext()) {

            String field = fields.next();

            JsonNode oldValue = oldJson.get(field);
            JsonNode newValue = newJson.get(field);

            if (oldValue == null && newValue != null) {

                diff.append("+ ")
                        .append(field)
                        .append(" = ")
                        .append(newValue)
                        .append("\n");

            } else if (!oldValue.equals(newValue)) {

                diff.append("~ ")
                        .append(field)
                        .append(": ")
                        .append(oldValue)
                        .append(" → ")
                        .append(newValue)
                        .append("\n");
            }
        }

        return diff.toString();
    }

    private String formatJson(JsonNode json) {

        try {

            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(json);

        } catch (Exception e) {

            return json.toString();
        }
    }

    private String indent(String text) {

        String[] lines = text.split("\n");

        StringBuilder sb = new StringBuilder();

        for (String line : lines) {

            sb.append("│ ").append(line).append("\n");
        }

        return sb.toString();
    }
}