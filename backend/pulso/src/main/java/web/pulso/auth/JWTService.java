package web.pulso.auth;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Primary
public class JWTService {
    private final JwtEncoder encoder;

    public JWTService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        Instant agora = Instant.now();
        long expiraEm = 3600L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("pulso")
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiraEm))
                .subject(authentication.getName())
                .claim("id_usuario", ((UsuarioAutenticado) authentication.getPrincipal()).getId())
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}