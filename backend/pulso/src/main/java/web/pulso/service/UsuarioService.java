package web.pulso.service;

import java.sql.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.pulso.auth.JWTService;
import web.pulso.dto.request.CadastroRequestDTO;
import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.service.mappers.UsuarioMapper;
import web.pulso.entity.Usuario;
import web.pulso.auth.UsuarioAutenticado;
import web.pulso.repository.UsuarioRepository;
import web.pulso.repository.specification.UsuarioSpecification;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ProfissionalService profissionalService;
    private final InstituicaoService instituicaoService;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            ProfissionalService profissionalService,
            InstituicaoService instituicaoService,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder,
            JWTService jwtService,
            @Lazy AuthenticationManager authManager) {
        this.usuarioRepository = usuarioRepository;
        this.profissionalService = profissionalService;
        this.instituicaoService = instituicaoService;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    // =========================
    // CADASTRO
    // =========================
    @Transactional
    public UsuarioResponseDTO cadastrar(CadastroRequestDTO dto) {

        Usuario usuario = this.criarEntidade(dto.getEmail(), dto.getSenha());

        if (dto.getProfissional() == null && dto.getInstituicao() == null)
            throw new BadRequestException("Informe profissional ou instituição para o cadastro");

        if (dto.getProfissional() != null && dto.getInstituicao() != null)
            throw new BadRequestException("Informe apenas profissional OU instituição, não ambos");

        if (dto.getProfissional() != null)
            profissionalService.criarComUsuario(dto.getProfissional(), usuario);
        else
            instituicaoService.criarComUsuario(dto.getInstituicao(), usuario);
        usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public Usuario criarEntidade(String email, String senha) {

        Usuario usuario = new Usuario();
        usuario.setAtivo(true);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    // =========================
    // SPRING SECURITY
    // =========================
    @Override
    public UserDetails loadUserByUsername(String email){
        return usuarioRepository.findByEmail(email)
                .map(UsuarioAutenticado::new)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public UsuarioLoginResponseDTO autenticar(String email, String senha) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, senha);

        Authentication authentication = authManager.authenticate(authToken);

        UsuarioAutenticado usuario = (UsuarioAutenticado) authentication.getPrincipal();

        String token = jwtService.generateToken(authentication);

        return usuarioMapper.toLoginResponseDTO(usuario.getUsuario(), token);
    }

    // =========================
    // ATUALIZAÇÃO
    // =========================
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    // =========================
    // CONSULTAS
    // =========================
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        return usuarioMapper.toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listar(
            Boolean ativo,
            Date criadoInicio,
            Date criadoFim
    ) {

        Specification<Usuario> spec = Specification.allOf(
                UsuarioSpecification.ativo(ativo),
                UsuarioSpecification.criadoEntre(criadoInicio, criadoFim)
        );

        return usuarioRepository.findAll(spec)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    // =========================
    // REMOÇÃO
    // =========================
    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
