package web.pulso.mappers.troca;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.mappers.troca.TrocaRequestDTOFactory.criarTrocaRequestDTOValido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.TrocaRequestDTO;
import web.pulso.entity.Troca;
import web.pulso.service.mappers.TrocaMapper;

@DisplayName("Testes do método toEntity da classe TrocaMapper")
public class ToEntityTest {

    private TrocaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TrocaMapper(null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando dto for nulo")
    void deveRetornarNullQuandoDtoForNulo() {

        Troca troca = mapper.toEntity(null);

        assertThat(troca).isNull();
    }

    @Test
    @DisplayName("Deve mapear comentário corretamente")
    void deveMapearComentarioCorretamente() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario("Troca de plantão");

        Troca troca = mapper.toEntity(dto);

        assertThat(troca.getComentario())
                .isEqualTo(dto.getComentario());
    }

    @Test
    @DisplayName("Deve mapear comentário nulo")
    void deveMapearComentarioNulo() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario(null);

        Troca troca = mapper.toEntity(dto);

        assertThat(troca.getComentario())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear comentário vazio")
    void deveMapearComentarioVazio() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario("");

        Troca troca = mapper.toEntity(dto);

        assertThat(troca.getComentario())
                .isEqualTo("");
    }

    @Test
    @DisplayName("Deve mapear comentário com espaços")
    void deveMapearComentarioComEspacos() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario("   comentário com espaços   ");

        Troca troca = mapper.toEntity(dto);

        assertThat(troca.getComentario())
                .isEqualTo(dto.getComentario());
    }

    @Test
    @DisplayName("Deve criar nova instância de troca")
    void deveCriarNovaInstanciaDeTroca() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        Troca troca = mapper.toEntity(dto);

        assertThat(troca)
                .isNotNull()
                .isNotSameAs(dto);
    }

}
