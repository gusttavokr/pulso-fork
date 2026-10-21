package web.pulso.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "audit_log",
        indexes = {
                @Index(name = "idx_audit_entity", columnList = "entity"),
                @Index(name = "idx_audit_entity_id", columnList = "entity_id"),
                @Index(name = "idx_audit_user_id", columnList = "user_id"),
                @Index(name = "idx_audit_created_at", columnList = "created_at"),
                @Index(name = "idx_audit_transaction_id", columnList = "transaction_id"),
                @Index(name = "idx_audit_request_id", columnList = "request_id")
        }
)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, length = 100)
    private String transactionId;

    @Column(name = "request_id", length = 100)
    private String requestId;

    @Column(name = "ip", length = 45)
    private String ip;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "entity", nullable = false, length = 120)
    private String entity;

    @Column(name = "entity_id", length = 120)
    private String entityId;

    @Column(name = "operation", nullable = false, length = 20)
    private String operation;

    @Column(name = "endpoint", length = 255)
    private String endpoint;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_data", columnDefinition = "jsonb")
    private JsonNode oldData;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_data", columnDefinition = "jsonb")
    private JsonNode newData;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}