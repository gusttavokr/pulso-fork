package web.pulso.mappers.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleLoginResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.TipoUsuario;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.UsuarioMapper;

@DisplayName("Testes da classe UsuarioMapper")
public class UsuarioMapperTest {

    @Mock
    private InstituicaoMapper instituicaoMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    private UsuarioMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new UsuarioMapper(instituicaoMapper, profissionalMapper);
    }

    @Test
    @DisplayName("Deve mapear DTO de usuário para entidade com ativo verdadeiro")
    void deveMapearDtoParaEntidade() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("usuario@pulso.com", "senha123");

        Usuario usuario = mapper.toEntity(dto);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getEmail()).isEqualTo(dto.getEmail());
        assertThat(usuario.getSenha()).isEqualTo(dto.getSenha());
        assertThat(usuario.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Deve mapear entidade para response DTO incluindo instituição e profissional")
    void deveMapearEntidadeParaResponseDto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@pulso.com");
        usuario.setAtivo(true);
        usuario.setUltimoLogin(new Date());

        Instituicao instituicao = new Instituicao();
        instituicao.setId(2L);
        usuario.setInstituicao(instituicao);

        Profissional profissional = new Profissional();
        profissional.setId(3L);
        usuario.setProfissional(profissional);

        UsuarioSimpleResponseDTO usuarioSimpleDto = new UsuarioSimpleResponseDTO();
        usuarioSimpleDto.setId(usuario.getId());
        usuarioSimpleDto.setAtivo(usuario.getAtivo());

        InstituicaoSimpleResponseDTO instituicaoSimpleDto = new InstituicaoSimpleResponseDTO();
        instituicaoSimpleDto.setId(instituicao.getId());
        instituicaoSimpleDto.setNome("Hospital Vida");
        instituicaoSimpleDto.setEmails(List.of("contato@hospital.com"));
        instituicaoSimpleDto.setEndereco("Rua A");
        instituicaoSimpleDto.setCnpj("12345678000199");

        ProfissionalSimpleResponseDTO profissionalSimpleDto = new ProfissionalSimpleResponseDTO();
        profissionalSimpleDto.setId(profissional.getId());

        when(instituicaoMapper.toSimpleResponseDTO(instituicao))
                .thenReturn(instituicaoSimpleDto);
        when(profissionalMapper.toSimpleResponseDTO(profissional))
                .thenReturn(profissionalSimpleDto);

        UsuarioResponseDTO response = mapper.toResponseDTO(usuario);

        assertThat(response).isNotNull();
        assertThat(response.getInstituicao()).isEqualTo(instituicaoSimpleDto);
        assertThat(response.getProfissional()).isEqualTo(profissionalSimpleDto);
        assertThat(response.getEmail()).isEqualTo(usuario.getEmail());
    }

    @Test
    @DisplayName("Deve mapear entidade para login DTO com tipo de usuário de instituição")
    void deveMapearParaLoginResponseDtoComInstituicao() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@pulso.com");
        usuario.setAtivo(true);

        Instituicao instituicao = new Instituicao();
        instituicao.setId(2L);
        usuario.setInstituicao(instituicao);

        InstituicaoSimpleLoginResponseDTO simpleLoginDto = new InstituicaoSimpleLoginResponseDTO();
        simpleLoginDto.setId(instituicao.getId());
        simpleLoginDto.setNome("Hospital Vida");
        simpleLoginDto.setFotoPerfil("hospital.png");

        when(instituicaoMapper.toSimpleLoginResponseDTO(instituicao))
                .thenReturn(simpleLoginDto);

        UsuarioLoginResponseDTO loginDto = mapper.toLoginResponseDTO(usuario, "token-123");

        assertThat(loginDto).isNotNull();
        assertThat(loginDto.getTipo()).isEqualTo(TipoUsuario.INSTITUICAO);
        assertThat(loginDto.getRoles()).containsExactly("INSTITUICAO");
        assertThat(loginDto.getToken()).isEqualTo("token-123");
        assertThat(loginDto.getInstituicao()).isEqualTo(simpleLoginDto);
    }
}
