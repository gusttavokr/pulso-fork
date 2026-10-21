package web.pulso.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import web.pulso.entity.Instituicao;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long>, JpaSpecificationExecutor<Instituicao> {
    List<Instituicao> findByExigeVinculoFalse();

    Optional<Instituicao> findByUsuarioId(Long usuarioId);
    Boolean existsByUsuarioId(Long usuarioId);


}