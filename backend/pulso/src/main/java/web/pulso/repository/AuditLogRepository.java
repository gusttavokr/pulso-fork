package web.pulso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.pulso.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{
}
