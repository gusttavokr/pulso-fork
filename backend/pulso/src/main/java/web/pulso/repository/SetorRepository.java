package web.pulso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import web.pulso.entity.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long>, JpaSpecificationExecutor<Setor> {
    List<Setor> findByInstituicaoId(Long instituicaoId);

    boolean existsByNomeAndInstituicaoId(String nome, Long instituicaoId);
}