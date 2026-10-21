package web.pulso.repository.specification;


import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

public class SetorSpecification {

    public static Specification<Setor> nome(String nome) {

        return (root, query, cb) -> {

            if (nome == null || nome.isBlank())
                return null;

            return cb.like(
                    cb.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Setor> especialidade(Especialidade especialidade) {

        return (root, query, cb) -> {

            if (especialidade == null)
                return null;

            return cb.equal(
                    root.get("especialidade"),
                    especialidade
            );
        };
    }

    public static Specification<Setor> daInstituicao(Long instituicaoId) {

        return (root, query, cb) -> {

            if (instituicaoId == null)
                return null;

            return cb.equal(
                    root.get("instituicao").get("id"),
                    instituicaoId
            );
        };
    }
}