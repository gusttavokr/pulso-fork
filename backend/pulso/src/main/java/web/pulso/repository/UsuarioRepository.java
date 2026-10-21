package web.pulso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import web.pulso.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    List<Usuario> findByAtivoTrue();
    Boolean existsByIdAndInstituicaoIsNotNull(Long usuarioId);

    Optional<Usuario> findByEmail(String email);
}