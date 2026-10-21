package web.pulso.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.enums.Especialidade;

public class ProfissionalSpecification {

    public static Specification<Profissional> daEspecialidade(Especialidade especialidade) {
        return (root, query, cb) -> {
            if (especialidade == null)
                return null;

            Join<Profissional, Especialidade> joinEspecialidades = root.join("especialidades");

            return cb.equal(joinEspecialidades, especialidade);
        };
    }

    public static Specification<Profissional> naInstituicao(Long instituicaoId) {
        return (root, query, cb) -> {
            if (instituicaoId == null)
                return null;

            Join<Profissional, Instituicao> joinClt = root.join("instituicoesClt", JoinType.LEFT);
            Join<Profissional, Instituicao> joinPj = root.join("instituicoesPj", JoinType.LEFT);
            Join<Profissional, Instituicao> joinLider = root.join("instituicoesLiderEscala", JoinType.LEFT);

            assert query != null;
            query.distinct(true);

            return cb.or(
                    cb.equal(joinClt.get("id"), instituicaoId),
                    cb.equal(joinPj.get("id"), instituicaoId),
                    cb.equal(joinLider.get("id"), instituicaoId));
        };
    }

    public static Specification<Profissional> naoConfirmadoNoPlantao(Long idPlantao) {
        return (root, query, cb) -> {
            if (idPlantao == null)
                return null;

            Join<Profissional, Plantao> plantoes = root.join("plantoesConfirmados", JoinType.LEFT);

            return cb.or(
                    cb.isNull(plantoes.get("id")),
                    cb.notEqual(plantoes.get("id"), idPlantao));
        };
    }

    public static Specification<Profissional> ativo() {
        return (root, query, cb) -> cb.isTrue(root.get("ativo"));
    }
}