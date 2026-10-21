package web.pulso.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.pulso.entity.Profissional;

@Repository
public interface ProfissionalRepository
        extends JpaRepository<Profissional, Long>, JpaSpecificationExecutor<Profissional> {
    Optional<Profissional> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);

    @Query("""
                select count(distinct p.id)
                from Profissional p
                left join p.plantoesConfirmados pc
                left join p.plantoesCombinados pco
                join Escala e
                    on (pc.escala = e or pco.escala = e)
                join e.setor s
                where s.instituicao.id = :instituicaoId
                  and e.mesReferencia >= :mesReferencia
            """)
    long countMedicosAlocadosNoMes(
            @Param("instituicaoId") Long instituicaoId,
            @Param("mesReferencia") LocalDate mesReferencia);

    @Query("""
                SELECT
                    MONTH(p.horaEntrada) AS mes,
                    COUNT(p.id) AS quantidade
                FROM Profissional prof
                JOIN prof.plantoesCombinados p
                WHERE prof.id = :profissionalId
                  AND YEAR(p.horaEntrada) = :ano
                GROUP BY MONTH(p.horaEntrada)
            """)
    List<Object[]> contarPlantoesCombinadosPorMes(
            @Param("profissionalId") Long profissionalId,
            @Param("ano") int ano);

    @Query("""
                SELECT
                    MONTH(p.horaEntrada) AS mes,
                    COUNT(p.id) AS quantidade
                FROM Profissional prof
                JOIN prof.plantoesConfirmados p
                WHERE prof.id = :profissionalId
                  AND YEAR(p.horaEntrada) = :ano
                GROUP BY MONTH(p.horaEntrada)
            """)
    List<Object[]> contarPlantoesConfirmadosPorMes(
            @Param("profissionalId") Long profissionalId,
            @Param("ano") int ano);

    @Query("""
                SELECT p FROM Profissional p
                LEFT JOIN FETCH p.instituicoesLiderEscala
                WHERE p.usuario.id = :usuarioId
            """)
    Optional<Profissional> findByUsuarioIdWithInstituicoes(@Param("usuarioId") Long usuarioId);
}
