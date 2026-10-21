package web.pulso.mappers.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.Usuario;
import web.pulso.service.mappers.UsuarioMapper;

@DisplayName("Testes do método toSimpleResponseDTO da classe UsuarioMapper")
public class ToSimpleResponseDTOTest {

    private UsuarioMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new UsuarioMapper(null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando usuário for nulo")
    void deveRetornarNullQuandoUsuarioForNulo() {

        UsuarioSimpleResponseDTO dto = mapper.toSimpleResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Usuario usuario = criarUsuarioValido();

        usuario.setId(1L);

        UsuarioSimpleResponseDTO dto = mapper.toSimpleResponseDTO(usuario);

        assertThat(dto.getId())
                .isEqualTo(usuario.getId());

        assertThat(dto.getAtivo())
                .isEqualTo(usuario.getAtivo());
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Usuario usuario = criarUsuarioValido();

        UsuarioSimpleResponseDTO dto = mapper.toSimpleResponseDTO(usuario);

        assertThat(dto)
                .isNotNull()
                .isNotSameAs(usuario);
    }

}