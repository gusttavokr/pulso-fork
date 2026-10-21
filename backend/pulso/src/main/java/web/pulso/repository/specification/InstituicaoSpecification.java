package web.pulso.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;

public class InstituicaoSpecification {

    public static Specification<Instituicao> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;

            return cb.like(
                    cb.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Instituicao> emailContem(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;

            query.distinct(true);

            Join<Instituicao, String> emailsJoin = root.join("emails");

            return cb.like(
                    cb.lower(emailsJoin),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Instituicao> telefoneContem(String telefone) {
        return (root, query, cb) -> {
            if (telefone == null || telefone.isBlank()) return null;

            return cb.like(
                    cb.lower(root.get("telefone")),
                    "%" + telefone.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Instituicao> enderecoContem(String endereco) {
        return (root, query, cb) -> {
            if (endereco == null || endereco.isBlank()) return null;

            return cb.like(
                    cb.lower(root.get("endereco")),
                    "%" + endereco.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Instituicao> cnpjIgual(String cnpj) {
        return (root, query, cb) -> {
            if (cnpj == null || cnpj.isBlank()) return null;

            return cb.equal(root.get("cnpj"), cnpj);
        };
    }

    public static Specification<Instituicao> exigeVinculo(Boolean exigeVinculo) {
        return (root, query, cb) -> {
            if (exigeVinculo == null) return null;

            return cb.equal(root.get("exigeVinculo"), exigeVinculo);
        };
    }

    public static Specification<Instituicao> liderVinculado(Profissional lider) {
        return (root, query, cb) -> {

            if (lider == null) return null;

            assert query != null;
            query.distinct(true);

            Join<Instituicao, Profissional> cltJoin =
                    root.join("profissionais_clt", JoinType.LEFT);

            Join<Instituicao, Profissional> pjJoin =
                    root.join("profisionais_pj", JoinType.LEFT);

            return cb.or(
                    cb.equal(cltJoin.get("id"), lider.getId()),
                    cb.equal(pjJoin.get("id"), lider.getId())
            );
        };
    }
}