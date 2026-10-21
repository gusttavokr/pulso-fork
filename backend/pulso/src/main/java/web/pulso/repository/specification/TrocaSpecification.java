package web.pulso.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

import java.time.LocalDateTime;

public class TrocaSpecification {

    public static Specification<Troca> doUsuario(Long idUsuario) {

        return (root, query, cb) -> {

            if (idUsuario == null)
                return null;

            return cb.or(
                    cb.equal(root.get("profissionalOrigem").get("id"), idUsuario),
                    cb.equal(root.get("profissionalDestino").get("id"), idUsuario)
            );
        };
    }

    public static Specification<Troca> comentario(String comentario) {

        return (root, query, cb) -> {

            if (comentario == null || comentario.isBlank())
                return null;

            return cb.like(
                    cb.lower(root.get("comentario")),
                    "%" + comentario.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Troca> tipo(TipoTroca tipo) {

        return (root, query, cb) -> {

            if (tipo == null)
                return null;

            return cb.equal(
                    root.get("tipo"),
                    tipo
            );
        };
    }

    public static Specification<Troca> status(StatusTroca status) {

        return (root, query, cb) -> {

            if (status == null)
                return null;

            return cb.equal(
                    root.get("status"),
                    status
            );
        };
    }

    public static Specification<Troca> profissionalOrigem(Long idProfissionalOrigem) {

        return (root, query, cb) -> {

            if (idProfissionalOrigem == null)
                return null;

            return cb.equal(
                    root.get("profissionalOrigem").get("id"),
                    idProfissionalOrigem
            );
        };
    }

    public static Specification<Troca> profissionalDestino(Long idProfissionalDestino) {

        return (root, query, cb) -> {

            if (idProfissionalDestino == null)
                return null;

            return cb.equal(
                    root.get("profissionalDestino").get("id"),
                    idProfissionalDestino
            );
        };
    }

    public static Specification<Troca> plantaoOrigem(Long idPlantaoOrigem) {

        return (root, query, cb) -> {

            if (idPlantaoOrigem == null)
                return null;

            return cb.equal(
                    root.get("plantaoOrigem").get("id"),
                    idPlantaoOrigem
            );
        };
    }

    public static Specification<Troca> plantaoDestino(Long idPlantaoDestino) {

        return (root, query, cb) -> {

            if (idPlantaoDestino == null)
                return null;

            return cb.equal(
                    root.get("plantaoDestino").get("id"),
                    idPlantaoDestino
            );
        };
    }

    public static Specification<Troca> dataSolicitacao(
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    ) {

        return (root, query, cb) -> {

            if (dataInicio == null && dataFim == null)
                return null;

            if (dataInicio != null && dataFim != null)
                return cb.between(root.get("dataSolicitacao"), dataInicio, dataFim);

            if (dataInicio != null)
                return cb.greaterThanOrEqualTo(root.get("dataSolicitacao"), dataInicio);

            return cb.lessThanOrEqualTo(root.get("dataSolicitacao"), dataFim);
        };
    }
}