package web.pulso.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Usuario;

import java.util.Date;

public class UsuarioSpecification {

    public static Specification<Usuario> ativo(Boolean ativo) {

        return (root, query, cb) -> {

            if (ativo == null)
                return null;

            return cb.equal(
                    root.get("ativo"),
                    ativo
            );
        };
    }

    public static Specification<Usuario> criadoEntre(Date inicio, Date fim) {

        return (root, query, cb) -> {

            if (inicio == null && fim == null)
                return null;

            if (inicio != null && fim != null)
                return cb.between(root.get("criadoEm"), inicio, fim);

            if (inicio != null)
                return cb.greaterThanOrEqualTo(root.get("criadoEm"), inicio);

            return cb.lessThanOrEqualTo(root.get("criadoEm"), fim);
        };
    }
}