package web.pulso.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import web.pulso.entity.*;
import web.pulso.entity.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PlantaoSpecification {

    public static Specification<Plantao> data(LocalDate data) {
        return (root, query, cb) -> {
            if (data == null) return null;
            LocalDateTime inicioDia = data.atStartOfDay();
            LocalDateTime fimDia = data.atTime(LocalTime.MAX);
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get("horaEntrada"), inicioDia),
                    cb.lessThanOrEqualTo(root.get("horaSaida"), fimDia)
            );
        };
    }

    public static Specification<Plantao> doProfissional(Long idUsuario) {
        return (root, query, cb) -> {
            Join<Plantao, Profissional> profissionais = root.join("profissionaisConfirmados");
            Join<Profissional, Usuario> usuario = profissionais.join("usuario");
            return cb.equal(usuario.get("id"), idUsuario);
        };
    }

    public static Specification<Plantao> doLiderEscala(Long idUsuario) {
        return (root, query, cb) -> {
            Join<Plantao, Escala> escala = root.join("escala");
            Join<Escala, Profissional> lider = escala.join("liderEscala");
            Join<Profissional, Usuario> usuario = lider.join("usuario");
            return cb.equal(usuario.get("id"), idUsuario);
        };
    }

    public static Specification<Plantao> daInstituicao(Long idUsuario) {
        return (root, query, cb) -> {
            Join<Plantao, Escala> escala = root.join("escala");
            Join<Escala, Instituicao> instituicao = escala.join("instituicao");
            Join<Instituicao, Usuario> usuario = instituicao.join("usuario");
            return cb.equal(usuario.get("id"), idUsuario);
        };
    }

    public static Specification<Plantao> plantoesAbertos(Profissional profissional) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Plantao, Escala> escala = root.join("escala");
            Join<Escala, Setor> setor = escala.join("setor");
            Join<Setor, Instituicao> instituicao = setor.join("instituicao");

            Predicate escalaPublicada = cb.equal(escala.get("status"), StatusEscala.PUBLICADA);
            Predicate futuro = cb.greaterThan(root.get("horaSaida"), cb.currentTimestamp());
            Predicate especialidade = setor.get("especialidade").in(profissional.getEspecialidades());
            Predicate semConfirmados = cb.isEmpty(root.get("profissionaisConfirmados"));
            Predicate semCombinados = cb.isEmpty(root.get("profissionaisCombinados"));
            Predicate semTrocas = cb.isEmpty(root.get("trocasOrigem"));
            Predicate vinculo = cb.or(
                    cb.isFalse(instituicao.get("exigeVinculo")),
                    cb.isMember(profissional, instituicao.get("profissionais_clt")),
                    cb.isMember(profissional, instituicao.get("profisionais_pj"))
            );

            return cb.and(
                    escalaPublicada,
                    futuro,
                    especialidade,
                    semConfirmados,
                    semCombinados,
                    semTrocas,
                    vinculo
            );
        };
    }

    public static Specification<Plantao> especialidade(Especialidade especialidade) {
        return (root, query, cb) -> {
            if (especialidade == null) return null;
            Join<Plantao, Escala> escala = root.join("escala");
            Join<Escala, Setor> setor = escala.join("setor");
            return cb.equal(setor.get("especialidade"), especialidade);
        };
    }

    public static Specification<Plantao> precoMin(Integer precoMin) {
        return (root, query, cb) -> {
            if (precoMin == null) return null;
            return cb.greaterThanOrEqualTo(root.get("estimativaValor"), precoMin);
        };
    }

    public static Specification<Plantao> precoMax(Integer precoMax) {
        return (root, query, cb) -> {
            if (precoMax == null) return null;
            return cb.lessThanOrEqualTo(root.get("estimativaValor"), precoMax);
        };
    }

    // --- Ajuste PostgreSQL usando EXTRACT para hora/minuto ---
    public static Specification<Plantao> horarios(List<LocalTime> horariosInicio, List<LocalTime> horariosFim) {
        return (root, query, cb) -> {
            if (horariosInicio == null || horariosFim == null || horariosInicio.isEmpty() || horariosFim.isEmpty())
                return null;

            List<Predicate> pares = new ArrayList<>();

            for (int i = 0; i < horariosInicio.size(); i++) {
                LocalTime inicio = horariosInicio.get(i);
                LocalTime fim = horariosFim.get(i);

                Predicate p = cb.and(
                        cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("hour"), root.get("horaEntrada")), inicio.getHour()),
                        cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("minute"), root.get("horaEntrada")), inicio.getMinute()),
                        cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("hour"), root.get("horaSaida")), fim.getHour()),
                        cb.equal(cb.function("DATE_PART", Integer.class, cb.literal("minute"), root.get("horaSaida")), fim.getMinute())
                );

                pares.add(p);
            }

            return cb.or(pares.toArray(new Predicate[0]));
        };
    }

    public static Specification<Plantao> ehNovoPlantao(Boolean ehNovoPlantao) {
        return (root, query, cb) -> {
            if (!Boolean.TRUE.equals(ehNovoPlantao)) return null;
            query.distinct(true);
            return cb.and(
                    cb.isEmpty(root.get("profissionaisConfirmados")),
                    cb.isEmpty(root.get("profissionaisCombinados")),
                    cb.isEmpty(root.get("trocasOrigem"))
            );
        };
    }

    public static Specification<Plantao> ehRepasse(Boolean ehRepasse) {
        return (root, query, cb) -> {
            if (!Boolean.TRUE.equals(ehRepasse)) return null;
            query.distinct(true);
            Join<Plantao, Troca> trocas = root.join("trocasOrigem");
            return cb.equal(trocas.get("tipo"), TipoTroca.SIMPLES);
        };
    }

    public static Specification<Plantao> ehTroca(Boolean ehTroca) {
        return (root, query, cb) -> {
            if (!Boolean.TRUE.equals(ehTroca)) return null;
            query.distinct(true);
            Join<Plantao, Troca> trocas = root.join("trocasOrigem");
            return cb.equal(trocas.get("tipo"), TipoTroca.DUPLA);
        };
    }
}