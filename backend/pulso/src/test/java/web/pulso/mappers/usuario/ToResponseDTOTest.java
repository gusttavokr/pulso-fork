package web.pulso.mappers.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;

import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.UsuarioMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do método toResponseDTO da classe UsuarioMapper")
public class ToResponseDTOTest {

    @Mock
    private InstituicaoMapper instituicaoMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @InjectMocks
    private UsuarioMapper mapper;

    @Test
    @DisplayName("Deve retornar null quando usuário for nulo")
    void deveRetornarNullQuandoUsuarioForNulo() {

        UsuarioResponseDTO dto = mapper.toResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Usuario usuario = criarUsuarioValido();

        usuario.setId(1L);

        usuario.setUltimoLogin(new Date(System.currentTimeMillis()));

        UsuarioResponseDTO dto = mapper.toResponseDTO(usuario);

        assertThat(dto.getId())
                .isEqualTo(usuario.getId());

        assertThat(dto.getEmail())
                .isEqualTo(usuario.getEmail());

        assertThat(dto.getAtivo())
                .isEqualTo(usuario.getAtivo());

        assertThat(dto.getUltimoLogin())
                .isEqualTo(usuario.getUltimoLogin());

        assertThat(dto.getCriadoEm())
                .isEqualTo(usuario.getDataCriacao());

        assertThat(dto.getAtualizadoEm())
                .isEqualTo(usuario.getDataAtualizacao());
    }

    @Test
    @DisplayName("Deve mapear instituição utilizando InstituicaoMapper")
    void deveMapearInstituicaoUtilizandoInstituicaoMapper() {

        Usuario usuario = criarUsuarioValido();

        Instituicao instituicao = new Instituicao();

        usuario.setInstituicao(instituicao);

        InstituicaoSimpleResponseDTO instituicaoDTO = new InstituicaoSimpleResponseDTO();

        when(instituicaoMapper.toSimpleResponseDTO(instituicao))
                .thenReturn(instituicaoDTO);

        UsuarioResponseDTO dto = mapper.toResponseDTO(usuario);

        assertThat(dto.getInstituicao())
                .isEqualTo(instituicaoDTO);

        verify(instituicaoMapper)
                .toSimpleResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve mapear profissional utilizando ProfissionalMapper")
    void deveMapearProfissionalUtilizandoProfissionalMapper() {

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = new Profissional();

        usuario.setProfissional(profissional);

        ProfissionalSimpleResponseDTO profissionalDTO = new ProfissionalSimpleResponseDTO();

        when(profissionalMapper.toSimpleResponseDTO(profissional))
                .thenReturn(profissionalDTO);

        UsuarioResponseDTO dto = mapper.toResponseDTO(usuario);

        assertThat(dto.getProfissional())
                .isEqualTo(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Usuario usuario = criarUsuarioValido();

        UsuarioResponseDTO dto = mapper.toResponseDTO(usuario);

        assertThat(dto)
                .isNotNull()
                .isNotSameAs(usuario);
    }

}