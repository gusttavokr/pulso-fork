package web.pulso.service.usuario;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import web.pulso.auth.JWTService;
import web.pulso.repository.UsuarioRepository;
import web.pulso.service.InstituicaoService;
import web.pulso.service.ProfissionalService;
import web.pulso.service.UsuarioService;
import web.pulso.service.mappers.UsuarioMapper;

@ExtendWith(MockitoExtension.class)
public abstract class UsuarioServiceBaseTest {

    @InjectMocks
    protected UsuarioService usuarioService;

    @Mock
    protected UsuarioRepository usuarioRepository;

    @Mock
    protected ProfissionalService profissionalService;

    @Mock
    protected InstituicaoService instituicaoService;

    @Mock
    protected UsuarioMapper usuarioMapper;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @Mock
    protected JWTService jwtService;

    @Mock
    protected AuthenticationManager authManager;
}
