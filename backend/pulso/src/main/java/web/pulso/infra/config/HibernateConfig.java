package web.pulso.infra.config;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.pulso.audit.AuditListener;

import java.util.List;

@Configuration
public class HibernateConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernateCustomizer(AuditListener auditListener) {
        return hibernateProperties -> hibernateProperties.put(
                "hibernate.integrator_provider",
                (IntegratorProvider) () -> List.of(new Integrator() {
                    @Override
                    public void integrate(
                            Metadata metadata,
                            SessionFactoryImplementor sessionFactory,
                            SessionFactoryServiceRegistry serviceRegistry) {

                        EventListenerRegistry registry =
                                serviceRegistry.getService(EventListenerRegistry.class);

                        registry.appendListeners(EventType.POST_INSERT, auditListener);
                        registry.appendListeners(EventType.POST_UPDATE, auditListener);
                        registry.appendListeners(EventType.POST_DELETE, auditListener);
                    }

                    @Override
                    public void disintegrate(
                            SessionFactoryImplementor sessionFactory,
                            SessionFactoryServiceRegistry serviceRegistry) {}
                })
        );
    }
}