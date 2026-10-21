package web.pulso.mappers.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.service.mappers.PlantaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.TrocaMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do método toResponseDTO da classe TrocaMapper")
public class ToResponseDTOTest {

    @Mock
    private PlantaoMapper plantaoMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @InjectMocks
    private TrocaMapper mapper;

    @Test
    @DisplayName("Deve retornar null quando troca for nula")
    void deveRetornarNullQuandoTrocaForNula() {

        TrocaResponseDTO dto = mapper.toResponseDTO(null);

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

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

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
    @DisplayName("Deve mapear data de solicitação corretamente")
    void deveMapearDataSolicitacaoCorretamente() {

        Troca troca = criarTrocaValida();

        LocalDateTime dataSolicitacao = LocalDateTime.now();

        troca.setDataSolicitacao(dataSolicitacao);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getDataSolicitacao())
                .isEqualTo(dataSolicitacao);
    }

    @Test
    @DisplayName("Deve mapear data de resposta corretamente")
    void deveMapearDataRespostaCorretamente() {

        Troca troca = criarTrocaValida();

        LocalDateTime dataResposta = LocalDateTime.now();

        troca.setDataResposta(dataResposta);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getDataResposta())
                .isEqualTo(dataResposta);
    }

    @Test
    @DisplayName("Deve mapear data de solicitação nula")
    void deveMapearDataSolicitacaoNula() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getDataSolicitacao())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear data de resposta nula")
    void deveMapearDataRespostaNula() {

        Troca troca = criarTrocaValida();

        troca.setDataResposta(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getDataResposta())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear profissional de origem utilizando ProfissionalMapper")
    void deveMapearProfissionalDeOrigemUtilizandoProfissionalMapper() {

        Troca troca = criarTrocaValida();

        Profissional profissional = criarProfissionalValido();

        troca.setProfissionalOrigem(profissional);

        ProfissionalSimpleResponseDTO profissionalDTO = new ProfissionalSimpleResponseDTO();

        when(profissionalMapper.toSimpleResponseDTO(profissional))
                .thenReturn(profissionalDTO);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getProfissionalOrigem())
                .isEqualTo(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve mapear profissional de destino utilizando ProfissionalMapper")
    void deveMapearProfissionalDeDestinoUtilizandoProfissionalMapper() {

        Troca troca = criarTrocaValida();

        Profissional profissional = criarOutroProfissionalValido();

        troca.setProfissionalDestino(profissional);

        ProfissionalSimpleResponseDTO profissionalDTO = new ProfissionalSimpleResponseDTO();

        when(profissionalMapper.toSimpleResponseDTO(any(Profissional.class)))
                .thenReturn(profissionalDTO);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getProfissionalDestino())
                .isEqualTo(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve mapear plantão de origem utilizando PlantaoMapper")
    void deveMapearPlantaoDeOrigemUtilizandoPlantaoMapper() {

        Troca troca = criarTrocaValida();

        Plantao plantao = new Plantao();

        troca.setPlantaoOrigem(plantao);

        PlantaoSimpleResponseDTO plantaoDTO = new PlantaoSimpleResponseDTO();

        when(plantaoMapper.toSimpleResponseDTO(plantao))
                .thenReturn(plantaoDTO);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getPlantaoOrigem())
                .isEqualTo(plantaoDTO);

        verify(plantaoMapper)
                .toSimpleResponseDTO(plantao);
    }

    @Test
    @DisplayName("Deve mapear plantão de destino utilizando PlantaoMapper")
    void deveMapearPlantaoDeDestinoUtilizandoPlantaoMapper() {

        Troca troca = criarTrocaValida();

        Plantao plantao = new Plantao();

        troca.setPlantaoDestino(plantao);

        PlantaoSimpleResponseDTO plantaoDTO = new PlantaoSimpleResponseDTO();

        when(plantaoMapper.toSimpleResponseDTO(any(Plantao.class)))
                .thenReturn(plantaoDTO);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getPlantaoDestino())
                .isEqualTo(plantaoDTO);

        verify(plantaoMapper)
                .toSimpleResponseDTO(plantao);
    }

    @Test
    @DisplayName("Não deve mapear profissional de origem quando for nulo")
    void naoDeveMapearProfissionalDeOrigemQuandoForNulo() {

        Troca troca = criarTrocaValida();

        troca.setProfissionalOrigem(null);
        troca.setProfissionalDestino(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getProfissionalOrigem())
                .isNull();

        verify(profissionalMapper, never())
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Não deve mapear profissional de destino quando for nulo")
    void naoDeveMapearProfissionalDeDestinoQuandoForNulo() {

        Troca troca = criarTrocaValida();

        troca.setProfissionalOrigem(null);
        troca.setProfissionalDestino(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getProfissionalDestino())
                .isNull();

        verify(profissionalMapper, never())
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Não deve mapear plantão de origem quando for nulo")
    void naoDeveMapearPlantaoDeOrigemQuandoForNulo() {

        Troca troca = criarTrocaValida();

        troca.setPlantaoOrigem(null);
        troca.setPlantaoDestino(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getPlantaoOrigem())
                .isNull();

        verify(plantaoMapper, never())
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Não deve mapear plantão de destino quando for nulo")
    void naoDeveMapearPlantaoDeDestinoQuandoForNulo() {

        Troca troca = criarTrocaValida();

        troca.setPlantaoOrigem(null);
        troca.setPlantaoDestino(null);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto.getPlantaoDestino())
                .isNull();

        verify(plantaoMapper, never())
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Troca troca = criarTrocaValida();

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

        assertThat(dto)
                .isNotNull()
                .isNotSameAs(troca);
    }

    @Test
    @DisplayName("Não deve alterar troca ao modificar DTO")
    void naoDeveAlterarTrocaAoModificarDto() {

        Troca troca = criarTrocaValida();

        troca.setComentario("Comentário original");

        troca.setTipo(TipoTroca.SIMPLES);

        troca.setStatus(StatusTroca.PENDENTE);

        TrocaResponseDTO dto = mapper.toResponseDTO(troca);

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
