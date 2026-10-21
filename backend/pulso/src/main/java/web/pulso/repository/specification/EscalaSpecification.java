package web.pulso.repository.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Escala;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;

import java.time.LocalDate;

public class EscalaSpecification {

    public static Specification<Escala> mesReferencia(LocalDate mesReferencia) {
        return (root, query, cb) -> {
            if (mesReferencia == null) return null;

            return cb.equal(
                    cb.function("DATE_TRUNC", LocalDate.class, cb.literal("month"), root.get("mesReferencia")),
                    mesReferencia.withDayOfMonth(1)
            );
        };
    }

    public static Specification<Escala> status(StatusEscala status) {

        return (root, query, cb) -> {

            if (status == null)
                return null;

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Escala> especialidade(Especialidade especialidade) {

        return (root, query, cb) -> {

            if (especialidade == null)
                return null;

            Join<Escala, Setor> setor = root.join("setor");

            return cb.equal(setor.get("especialidade"), especialidade);
        };
    }

    public static Specification<Escala> doLiderEscala(Long usuarioId) {

        return (root, query, cb) -> {

            if (usuarioId == null)
                return null;

            Join<Escala, Profissional> lider = root.join("liderEscala");
            Join<Profissional, Usuario> usuario = lider.join("usuario");

            return cb.equal(usuario.get("id"), usuarioId);
        };
    }

    public static Specification<Escala> daInstituicao(Long usuarioId) {

        return (root, query, cb) -> {

            if (usuarioId == null)
                return null;

            Join<Escala, Instituicao> instituicao = root.join("instituicao");
            Join<Instituicao, Usuario> usuario = instituicao.join("usuario");

            return cb.equal(usuario.get("id"), usuarioId);
        };
    }
}