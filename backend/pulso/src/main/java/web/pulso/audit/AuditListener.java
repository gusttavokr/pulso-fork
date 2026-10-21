package web.pulso.audit;

import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.pulso.audit.annotation.Auditable;

@Component
public class AuditListener implements
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {

    @Autowired
    private ObjectProvider<AuditService> auditService;

    private boolean isAuditable(Object entity) {
        return entity.getClass().isAnnotationPresent(Auditable.class);
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {

        Object entity = event.getEntity();

        if (!isAuditable(entity)) {
            return;
        }

        auditService.getObject().audit(
                entity,
                event.getId(),
                "INSERT",
                null,
                event.getState(),
                event.getPersister().getPropertyNames()
        );
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {

        Object entity = event.getEntity();

        if (!isAuditable(entity)) {
            return;
        }

        auditService.getObject().audit(
                entity,
                event.getId(),
                "UPDATE",
                event.getOldState(),
                event.getState(),
                event.getPersister().getPropertyNames()
        );
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {

        Object entity = event.getEntity();

        if (!isAuditable(entity)) {
            return;
        }

        auditService.getObject().audit(
                entity,
                event.getId(),
                "DELETE",
                event.getDeletedState(),
                null,
                event.getPersister().getPropertyNames()
        );
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}