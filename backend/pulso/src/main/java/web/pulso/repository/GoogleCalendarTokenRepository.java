package web.pulso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.pulso.entity.GoogleCalendarToken;
import web.pulso.entity.Usuario;

import java.util.Optional;

@Repository
public interface GoogleCalendarTokenRepository extends JpaRepository<GoogleCalendarToken, Long> {
    Optional<GoogleCalendarToken> findByUsuario(Usuario usuario);
    Optional<GoogleCalendarToken> findByUsuarioId(Long usuarioId);
    void deleteByUsuario(Usuario usuario);
}
