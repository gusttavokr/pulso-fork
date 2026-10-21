package web.pulso.mappers.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.troca.TrocaSimpleResponseDTO;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.service.mappers.TrocaMapper;

@DisplayName("Testes do método toSimpleResponseDTO da classe TrocaMapper")
public class ToSimpleResponseDTOTest {

    private TrocaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TrocaMapper(null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando troca for nula")
    void deveRetornarNullQuandoTrocaForNula() {

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Troca troca = criarTrocaValida();

        troca.setId(1L);

        troca.setComentario("Troca de plantão");

        troca.setTipo(TipoTroca.SIMPLES);

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto.getId())
                .isEqualTo(troca.getId());

        assertThat(dto.getComentario())
                .isEqualTo(troca.getComentario());

        assertThat(dto.getTipo())
                .isEqualTo(troca.getTipo());

        assertThat(dto.getStatus())
                .isEqualTo(troca.getStatus());
    }

    @Test
    @DisplayName("Deve mapear id nulo")
    void deveMapearIdNulo() {

        Troca troca = criarTrocaValida();

        troca.setId(null);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto.getId())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear comentário nulo")
    void deveMapearComentarioNulo() {

        Troca troca = criarTrocaValida();

        troca.setComentario(null);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto.getComentario())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear tipo nulo")
    void deveMapearTipoNulo() {

        Troca troca = criarTrocaValida();

        troca.setTipo(null);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto.getTipo())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear status nulo")
    void deveMapearStatusNulo() {

        Troca troca = criarTrocaValida();

        troca.setStatus(null);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto.getStatus())
                .isNull();
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Troca troca = criarTrocaValida();

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        assertThat(dto)
                .isNotNull();

        assertThat(dto)
                .isNotSameAs(troca);
    }

    @Test
    @DisplayName("Não deve alterar troca ao modificar DTO")
    void naoDeveAlterarTrocaAoModificarDto() {

        Troca troca = criarTrocaValida();

        troca.setId(1L);

        troca.setComentario("Comentário original");

        troca.setTipo(TipoTroca.SIMPLES);

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaSimpleResponseDTO dto = mapper.toSimpleResponseDTO(troca);

        dto.setComentario("Comentário alterado");

        dto.setTipo(TipoTroca.DUPLA);

        dto.setStatus(StatusTroca.CONFIRMADO);

        assertThat(troca.getComentario())
                .isEqualTo("Comentário original");

        assertThat(troca.getTipo())
                .isEqualTo(TipoTroca.SIMPLES);

        assertThat(troca.getStatus())
                .isEqualTo(StatusTroca.PENDENTE);
    }
}
