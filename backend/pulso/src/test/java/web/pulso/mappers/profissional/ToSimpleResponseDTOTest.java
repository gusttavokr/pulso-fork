package web.pulso.mappers.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.service.mappers.ProfissionalMapper;

@DisplayName("Testes do método toSimpleResponseDTO da classe ProfissionalMapper")
public class ToSimpleResponseDTOTest {

        private ProfissionalMapper mapper;

        @BeforeEach
        void setUp() {

                mapper = new ProfissionalMapper(null, null, null, null, null);
        }

        @Test
        @DisplayName("Deve retornar null quando profissional for nulo")
        void deveRetornarNullQuandoProfissionalForNulo() {

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(null);

                assertThat(dto).isNull();
        }

        @Test
        @DisplayName("Deve mapear campos simples corretamente")
        void deveMapearCamposSimplesCorretamente() {

                Profissional profissional = criarProfissionalValido();

                profissional.setId(1L);

                profissional.setFotoPerfil("foto.png");

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                assertThat(dto.getId())
                                .isEqualTo(profissional.getId());

                assertThat(dto.getNome())
                                .isEqualTo(profissional.getNome());

                assertThat(dto.getFoto())
                                .isEqualTo(profissional.getFotoPerfil());
        }

        @Test
        @DisplayName("Deve mapear id nulo")
        void deveMapearIdNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setId(null);

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                assertThat(dto.getId())
                                .isNull();
        }

        @Test
        @DisplayName("Deve mapear nome nulo")
        void deveMapearNomeNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome(null);

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                assertThat(dto.getNome())
                                .isNull();
        }

        @Test
        @DisplayName("Deve mapear foto nula")
        void deveMapearFotoNula() {

                Profissional profissional = criarProfissionalValido();

                profissional.setFotoPerfil(null);

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                assertThat(dto.getFoto())
                                .isNull();
        }

        @Test
        @DisplayName("Deve criar nova instância de DTO")
        void deveCriarNovaInstanciaDeDto() {

                Profissional profissional = criarProfissionalValido();

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                assertThat(dto)
                                .isNotNull();

                assertThat(dto)
                                .isNotSameAs(profissional);
        }

        @Test
        @DisplayName("Não deve alterar profissional ao modificar DTO")
        void naoDeveAlterarProfissionalAoModificarDto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setId(1L);

                profissional.setNome("João da Silva");

                profissional.setFotoPerfil("foto.png");

                ProfissionalSimpleResponseDTO dto = mapper.toSimpleResponseDTO(profissional);

                dto.setNome("Maria Oliveira");
                dto.setFoto("nova-foto.png");

                assertThat(profissional.getNome())
                                .isEqualTo("João da Silva");

                assertThat(profissional.getFotoPerfil())
                                .isEqualTo("foto.png");
        }

}
