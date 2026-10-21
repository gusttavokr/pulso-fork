package web.pulso.mappers.plantao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import web.pulso.service.mappers.PlantaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;

import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.factory.PlantaoFactory;
import web.pulso.entity.*;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("PlantaoMapper")
class PlantaoMapperTest {

    @InjectMocks
    private PlantaoMapper plantaoMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * ==================== toResponseDTO ====================
     *
     * Método: toResponseDTO()
     *
     * CE (Classes de Equivalência):
     *
     * Válidas:
     * CEV1: plantão válido + troca válida
     * -> deve mapear todos os dados do plantão e da troca corretamente
     *
     * CEV2: plantão válido + troca nula
     * -> deve mapear apenas os dados do plantão
     *
     * CEV3: plantão com trocas confirmadas
     * -> deve mapear profissionais de troca destino
     *
     * Inválidas:
     * CEI1: plantão nulo
     * -> deve retornar null
     *
     * VL (Valores Limite):
     *
     * VL1: listas vazias de profissionais/trocas
     * -> deve retornar listas vazias sem erro
     */

    @Test
    @DisplayName("Deve mapear PlantaoResponseDTO com sucesso quando plantão e troca forem válidos")
    void deveMapearPlantaoResponseDTOComSucesso() {

        // ================= PREPARAÇÃO DADOS =================
        Instituicao instituicao = PlantaoFactory.criarInstituicaoPadrao("Hospital Walfredo", "Natal/RN");
        Setor setor = PlantaoFactory.criarSetorCompleto("UTI", mock(Especialidade.class), instituicao);
        Profissional lider = PlantaoFactory.criarProfissionalCompleto(99L, "Robson", "foto.png");
        Escala escala = PlantaoFactory.criarEscalaCompleta(1L, StatusEscala.RASCUNHO, setor, lider);

        // ================= PROFISSIONAIS =================
        Profissional profissionalCombinado = new Profissional();
        Profissional profissionalConfirmado = new Profissional();
        Profissional profissionalTroca = new Profissional();

        // ================= TROCA =================
        Troca troca = PlantaoFactory.criarTrocaPadrao(null);
        troca.setComentario("Troca realizada");
        troca.setTipo(TipoTroca.SIMPLES);

        Troca trocaOrigem = PlantaoFactory.criarTrocaPadrao(StatusTroca.CONFIRMADO);
        trocaOrigem.setProfissionalDestino(profissionalTroca);

        // ================= PLANTÃO =================
        Plantao plantao = PlantaoFactory.criarPlantaoPadrao(escala);
        plantao.setId(10L);
        plantao.setHoraEntrada(LocalDateTime.of(2026, 5, 17, 7, 0));
        plantao.setHoraSaida(LocalDateTime.of(2026, 5, 17, 19, 0));
        plantao.setComentario("Comentário plantão");
        plantao.setEstimativaValor(1500);
        plantao.setQuantidadeProfissionais(2);
        plantao.setProfissionaisCombinados(Set.of(profissionalCombinado));
        plantao.setProfissionaisConfirmados(Set.of(profissionalConfirmado));
        plantao.setTrocasOrigem(List.of(trocaOrigem));

        // ================= MOCKS DTO PROFISSIONAL =================
        when(profissionalMapper.toSimpleResponseDTO(any()))
                .thenReturn(mock(ProfissionalSimpleResponseDTO.class));

        // ================= EXECUÇÃO =================
        PlantaoResponseDTO dto = plantaoMapper.toResponseDTO(plantao, troca);

        // ================= ASSERTS =================
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(10L);

        assertThat(dto.getNomeInstituicao())
                .isEqualTo("Hospital Walfredo");

        assertThat(dto.getEnderecoInstituicao())
                .isEqualTo("Natal/RN");

        assertThat(dto.getNomeSetor())
                .isEqualTo("UTI");

        assertThat(dto.getNomeLider())
                .isEqualTo("Robson");

        assertThat(dto.getFotoLider())
                .isEqualTo("foto.png");

        assertThat(dto.getComentario())
                .isEqualTo("Comentário plantão");

        assertThat(dto.getEstimativaValor())
                .isEqualTo(1500);

        assertThat(dto.getQuantidadeProfissionais())
                .isEqualTo(2);

        assertThat(dto.getComentarioTroca())
                .isEqualTo("Troca realizada");

        assertThat(dto.getTipoTroca())
                .isEqualTo(TipoTroca.SIMPLES);

        assertThat(dto.getProfissionaisCombinados())
                .hasSize(1);

        assertThat(dto.getProfissionaisConfirmados())
                .hasSize(1);

        assertThat(dto.getProfissionaisTrocaDestino())
                .hasSize(1);

        // ================= VERIFICAÇÕES =================
        verify(profissionalMapper, times(3))
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Deve mapear PlantaoResponseDTO com sucesso quando troca for nula")
    void deveMapearPlantaoResponseDTOSemTroca() {

        // ================= PREPARAÇÃO DADOS =================
        Instituicao instituicao = PlantaoFactory.criarInstituicaoPadrao("Hospital Walfredo", "Natal/RN");
        Setor setor = PlantaoFactory.criarSetorCompleto("UTI", mock(Especialidade.class), instituicao);
        Profissional lider = PlantaoFactory.criarProfissionalCompleto(99L, "Robson", "foto.png");
        Escala escala = PlantaoFactory.criarEscalaCompleta(1L, StatusEscala.RASCUNHO, setor, lider);

        // ================= PLANTÃO =================
        Plantao plantao = PlantaoFactory.criarPlantaoPadrao(escala);
        plantao.setId(10L);
        plantao.setHoraEntrada(LocalDateTime.of(2026, 5, 17, 7, 0));
        plantao.setHoraSaida(LocalDateTime.of(2026, 5, 17, 19, 0));
        plantao.setComentario("Comentário plantão");
        plantao.setEstimativaValor(1500);
        plantao.setQuantidadeProfissionais(2);
        plantao.setProfissionaisCombinados(Set.of());
        plantao.setProfissionaisConfirmados(Set.of());
        plantao.setTrocasOrigem(List.of());

        // ================= EXECUÇÃO =================
        PlantaoResponseDTO dto = plantaoMapper.toResponseDTO(plantao, null);

        // ================= ASSERTS =================
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(10L);

        assertThat(dto.getComentarioTroca()).isNull();

        assertThat(dto.getTipoTroca()).isNull();

        assertThat(dto.getProfissionaisTrocaDestino())
                .isEmpty();

        // ================= VERIFICAÇÕES =================
        verify(profissionalMapper, never())
                .toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("Deve mapear profissionais de troca destino quando houver trocas confirmadas")
    void deveMapearProfissionaisTrocaDestinoQuandoHouverTrocasConfirmadas() {

        // ================= PREPARAÇÃO DADOS =================
        Instituicao instituicao = PlantaoFactory.criarInstituicaoPadrao("Hospital Walfredo", "Natal/RN");
        Setor setor = PlantaoFactory.criarSetorCompleto("UTI", mock(Especialidade.class), instituicao);
        Profissional lider = PlantaoFactory.criarProfissionalCompleto(1L, "Robson", "foto.png");
        Escala escala = PlantaoFactory.criarEscalaCompleta(1L, StatusEscala.RASCUNHO, setor, lider);

        // ================= PROFISSIONAL DESTINO =================
        Profissional profissionalDestino = new Profissional();

        // ================= TROCAS =================
        Troca trocaConfirmada = PlantaoFactory.criarTrocaPadrao(StatusTroca.CONFIRMADO);
        trocaConfirmada.setProfissionalDestino(profissionalDestino);

        Troca trocaPendente = PlantaoFactory.criarTrocaPadrao(StatusTroca.PENDENTE);

        // ================= PLANTÃO =================
        Plantao plantao = PlantaoFactory.criarPlantaoPadrao(escala);
        plantao.setId(10L);
        plantao.setHoraEntrada(LocalDateTime.of(2026, 5, 17, 7, 0));
        plantao.setHoraSaida(LocalDateTime.of(2026, 5, 17, 19, 0));
        plantao.setEstimativaValor(1500);
        plantao.setQuantidadeProfissionais(2);
        plantao.setProfissionaisCombinados(Set.of());
        plantao.setProfissionaisConfirmados(Set.of());
        plantao.setTrocasOrigem(List.of(trocaConfirmada, trocaPendente));

        // ================= MOCK DTO =================
        ProfissionalSimpleResponseDTO profissionalDTO = mock(ProfissionalSimpleResponseDTO.class);

        when(profissionalMapper.toSimpleResponseDTO(profissionalDestino))
                .thenReturn(profissionalDTO);

        // ================= EXECUÇÃO =================
        PlantaoResponseDTO dto = plantaoMapper.toResponseDTO(plantao, null);

        // ================= ASSERTS =================
        assertThat(dto).isNotNull();

        assertThat(dto.getProfissionaisTrocaDestino())
                .hasSize(1)
                .contains(profissionalDTO);

        // ================= VERIFICAÇÕES =================
        verify(profissionalMapper)
                .toSimpleResponseDTO(profissionalDestino);
    }

    @Test
    @DisplayName("Deve retornar null quando plantão for nulo")
    void deveRetornarNullQuandoPlantaoForNuloNoToResponseDTO() {

        // ================= EXECUÇÃO =================
        PlantaoResponseDTO dto = plantaoMapper.toResponseDTO(null, null);

        // ================= ASSERT =================
        assertThat(dto).isNull();

        // ================= VERIFICAÇÕES =================
        verifyNoInteractions(profissionalMapper);
    }

    /**
     * ==================== toSimpleResponseDTO ====================
     *
     * Método: toSimpleResponseDTO()
     *
     * CE (Classes de Equivalência):
     *
     * Válidas:
     * CEV1: Plantão sem interações -> ehNovoPlantao = true, ehTroca = false, ehRepasse = false
     * CEV2: Plantão com repasse (TipoTroca.SIMPLES) -> ehNovoPlantao = false, ehTroca = false, ehRepasse = true
     * CEV3: Plantão com troca (TipoTroca.DUPLA) -> ehNovoPlantao = false, ehTroca = true, ehRepasse = false
     * CEV4: Plantão válido com valores preenchidos na escala -> mapeia nome, foto lider, especialidade, instituicao
     *
     * Inválidas:
     * CEI1: plantão nulo -> deve retornar null
     */

    @Test
    @DisplayName("Deve retornar null quando plantão for nulo no toSimpleResponseDTO")
    void deveRetornarNullQuandoPlantaoForNuloNoToSimpleResponseDTO() {
        PlantaoSimpleResponseDTO dto = plantaoMapper.toSimpleResponseDTO(null);
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear dados do plantão para toSimpleResponseDTO com sucesso e definir como novo plantão")
    void deveMapearPlantaoSimplesNovoComSucesso() {
        // ================= ESPECIALIDADE =================
        Especialidade especialidade = Especialidade.CLINICA_GERAL;

        // ================= INSTITUIÇÃO =================
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("Hospital Tacchini");

        // ================= SETOR =================
        Setor setor = new Setor();
        setor.setEspecialidade(especialidade);
        setor.setInstituicao(instituicao);

        // ================= LÍDER =================
        Profissional lider = new Profissional();
        lider.setNome("Marilia");
        lider.setFotoPerfil("marilia.jpg");

        // ================= ESCALA =================
        Escala escala = new Escala();
        escala.setSetor(setor);
        escala.setLiderEscala(lider);

        // ================= PLANTÃO =================
        Plantao plantao = new Plantao();
        plantao.setId(50L);
        plantao.setHoraEntrada(LocalDateTime.of(2026, 5, 20, 8, 0));
        plantao.setHoraSaida(LocalDateTime.of(2026, 5, 20, 14, 0));
        plantao.setEstimativaValor(600);
        plantao.setEscala(escala);

        // Define plantão "NOVO" (listas vazias ou nulas)
        plantao.setProfissionaisCombinados(Set.of());
        plantao.setProfissionaisConfirmados(Set.of());
        plantao.setTrocasOrigem(List.of());
        plantao.setTrocasDestino(List.of());

        // ================= EXECUÇÃO =================
        PlantaoSimpleResponseDTO dto = plantaoMapper.toSimpleResponseDTO(plantao);

        // ================= ASSERTS =================
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(50L);
        assertThat(dto.getHoraEntrada()).isEqualTo(LocalDateTime.of(2026, 5, 20, 8, 0));
        assertThat(dto.getHoraSaida()).isEqualTo(LocalDateTime.of(2026, 5, 20, 14, 0));
        assertThat(dto.getEstimativaValor()).isEqualTo(600);

        assertThat(dto.getNomeLider()).isEqualTo("Marilia");
        assertThat(dto.getFotoLider()).isEqualTo("marilia.jpg");
        assertThat(dto.getEspecialidade()).isEqualTo(Especialidade.CLINICA_GERAL.getLabel());
        assertThat(dto.getNomeInstituicao()).isEqualTo("Hospital Tacchini");

        assertThat(dto.getEhNovoPlantao()).isTrue();
        assertThat(dto.getEhTroca()).isFalse();
        assertThat(dto.getEhRepasse()).isFalse();
    }

    @Test
    @DisplayName("Deve sinalizar que plantão é repasse quando contiver troca SIMPLES na origem")
    void deveSinalizarPlantaoComoRepasse() {
        Plantao plantao = new Plantao();
        
        // Simular que já teve interações (então não é novo)
        Profissional p = new Profissional();
        plantao.setProfissionaisConfirmados(Set.of(p));
        
        Troca trocaSimples = new Troca();
        trocaSimples.setTipo(TipoTroca.SIMPLES);
        plantao.setTrocasOrigem(List.of(trocaSimples));

        PlantaoSimpleResponseDTO dto = plantaoMapper.toSimpleResponseDTO(plantao);

        assertThat(dto).isNotNull();
        assertThat(dto.getEhNovoPlantao()).isFalse();
        assertThat(dto.getEhRepasse()).isTrue();
        assertThat(dto.getEhTroca()).isFalse();
    }

    @Test
    @DisplayName("Deve sinalizar que plantão é troca quando contiver troca DUPLA na origem")
    void deveSinalizarPlantaoComoTroca() {
        Plantao plantao = new Plantao();
        
        // Simular que já teve interações (então não é novo)
        Profissional p = new Profissional();
        plantao.setProfissionaisConfirmados(Set.of(p));
        
        Troca trocaDupla = new Troca();
        trocaDupla.setTipo(TipoTroca.DUPLA);
        plantao.setTrocasOrigem(List.of(trocaDupla));

        PlantaoSimpleResponseDTO dto = plantaoMapper.toSimpleResponseDTO(plantao);

        assertThat(dto).isNotNull();
        assertThat(dto.getEhNovoPlantao()).isFalse();
        assertThat(dto.getEhRepasse()).isFalse();
        assertThat(dto.getEhTroca()).isTrue();
    }

}