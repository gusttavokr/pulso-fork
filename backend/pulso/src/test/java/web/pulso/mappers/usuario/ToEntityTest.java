package web.pulso.mappers.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.mappers.usuario.UsuarioRequestDTOFactory.criarUsuarioRequestDTOValido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.entity.Usuario;
import web.pulso.service.mappers.UsuarioMapper;

@DisplayName("Testes do método toEntity da classe UsuarioMapper")
public class ToEntityTest {

    private UsuarioMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new UsuarioMapper(null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando dto for nulo")
    void deveRetornarNullQuandoDtoForNulo() {

        Usuario usuario = mapper.toEntity(null);

        assertThat(usuario).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos corretamente")
    void deveMapearCamposCorretamente() {

        UsuarioRequestDTO dto = criarUsuarioRequestDTOValido();

        Usuario usuario = mapper.toEntity(dto);

        assertThat(usuario.getEmail())
                .isEqualTo(dto.getEmail());

        assertThat(usuario.getSenha())
                .isEqualTo(dto.getSenha());

        assertThat(usuario.getAtivo())
                .isTrue();
    }

    @Test
    @DisplayName("Deve criar nova instância de usuário")
    void deveCriarNovaInstanciaDeUsuario() {

        UsuarioRequestDTO dto = criarUsuarioRequestDTOValido();

        Usuario usuario = mapper.toEntity(dto);

        assertThat(usuario)
                .isNotNull()
                .isNotSameAs(dto);
    }

}