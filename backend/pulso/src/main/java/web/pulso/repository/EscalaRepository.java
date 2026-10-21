package web.pulso.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.pulso.entity.Escala;

@Repository
public interface EscalaRepository extends JpaRepository<Escala, Long>, JpaSpecificationExecutor<Escala> {

    @Query("""
                select count(e) > 0
                from Escala e
                where e.setor.id = :setorId
                  and e.mesReferencia = :mesReferencia
            """)
    boolean existsEscalaNoSetorNoMes(@Param("setorId") Long setorId, @Param("mesReferencia") LocalDate mesReferencia);
}