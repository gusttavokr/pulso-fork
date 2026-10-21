package web.pulso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import web.pulso.entity.Plantao;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;

@Repository
public interface TrocaRepository extends JpaRepository<Troca, Long>, JpaSpecificationExecutor<Troca> {
    Optional<Troca> findByPlantaoOrigemAndStatusAndProfissionalDestinoIsNull(Plantao plantao, StatusTroca status);
    Boolean existsByIdAndProfissionalDestinoUsuarioId(Long idTroca, Long idUsuario);
    Optional<Troca> findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(Plantao plantao);


}