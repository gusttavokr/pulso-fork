package web.pulso.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.pulso.entity.Plantao;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, Long>, JpaSpecificationExecutor<Plantao> {

    @Query("""
                SELECT p
                FROM Plantao p
                WHERE p.escala.mesReferencia = (
                    SELECT MAX(e.mesReferencia)
                    FROM Escala e
                    WHERE e.setor.id = :setorId
                      AND e.liderEscala.id = :liderId
                      AND e.mesReferencia < :mesReferenciaAtual
                )
                AND p.escala.setor.id = :setorId
            """)
    List<Plantao> buscarPlantoesUltimaEscalaAnterior(
            @Param("setorId") Long setorId,
            @Param("liderId") Long liderId,
            @Param("mesReferenciaAtual") LocalDate mesReferenciaAtual);

    @Query("""
                SELECT p FROM Plantao p
                JOIN FETCH p.escala e
                JOIN FETCH e.setor s
                JOIN FETCH s.instituicao i
                WHERE p.id = :plantaoId
                  AND EXISTS (
                      SELECT 1 FROM Profissional lider
                      JOIN lider.instituicoesLiderEscala inst
                      WHERE lider.id = :liderId
                        AND inst.id = i.id
                  )
            """)
    Optional<Plantao> findByIdAndLiderValido(@Param("plantaoId") Long plantaoId,
            @Param("liderId") Long liderId);

}