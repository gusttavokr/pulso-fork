package web.pulso.service.plantao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

import web.pulso.entity.*;
import web.pulso.dto.filtros.PlantaoFilter;
import web.pulso.dto.request.PlantaoRequestDTO;
import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.factory.PlantaoFactory;
import web.pulso.repository.*;
import web.pulso.service.mappers.PlantaoMapper;
import web.pulso.service.PlantaoService;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.entity.enums.StatusTroca;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("PlantaoService")
class PlantaoServiceTest {

        @InjectMocks
        private PlantaoService plantaoService;

        @Mock
        private PlantaoRepository plantaoRepository;

        @Mock
        private EscalaRepository escalaRepository;

        @Mock
        private PlantaoMapper plantaoMapper;

        @Mock
        private ProfissionalRepository profissionalRepository;

        @Mock
        private TrocaRepository trocaRepository;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        /**
         * ==================== criar ====================
         *
         * Método: criar()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: DTO válido com escala existente e horários válidos -> cria plantão
         *
         * Inválidas:
         * CEI1: escalaId nulo -> BadRequestException
         * CEI2: escala inexistente -> NotFoundException
         * CEI3: horaEntrada menor que 7 dias de antecedência -> ConflictException
         * CEI4: horaEntrada fora do mês da escala -> BadRequestException
         * CEI5: horaSaida fora do mês da escala -> BadRequestException
         *
         * VL (Valores Limite):
         *
         * VL1: horaEntrada exatamente 7 dias após agora -> permitido
         * VL2: horaEntrada exatamente no início do mês -> permitido
         * VL3: horaSaida exatamente no limite final permitido -> permitido
         */
        @Test
        @DisplayName("Deve criar plantão com sucesso")
        void deveCriarPlantaoComSucesso() {
                Long escalaId = 1L;
                PlantaoRequestDTO dto = PlantaoFactory.criarPlantaoRequestDTOPadrao(escalaId);
                LocalDate mesReferencia = LocalDate.now().plusMonths(1).withDayOfMonth(1);

                Escala escala = PlantaoFactory.criarEscalaPadrao(escalaId, StatusEscala.RASCUNHO);
                escala.setMesReferencia(mesReferencia);

                Plantao plantao = new Plantao();
                Plantao salvo = new Plantao();
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(escalaRepository.findById(escalaId))
                                .thenReturn(Optional.of(escala));
                when(plantaoMapper.toEntity(dto))
                                .thenReturn(plantao);
                when(plantaoRepository.save(plantao))
                                .thenReturn(salvo);
                when(plantaoMapper.toResponseDTO(salvo, null))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.criar(dto);

                assertThat(resultado).isEqualTo(response);

                verify(escalaRepository).findById(escalaId);
                verify(plantaoMapper).toEntity(dto);
                verify(plantaoRepository).save(plantao);
                verify(plantaoMapper).toResponseDTO(salvo, null);
        }

        @Test
        @DisplayName("Deve lançar BadRequestException quando escalaId for nulo")
        void deveLancarBadRequestExceptionQuandoEscalaIdForNulo() {
                PlantaoRequestDTO dto = new PlantaoRequestDTO();

                assertThatThrownBy(() -> plantaoService.criar(dto))
                                .isInstanceOf(BadRequestException.class);

                verifyNoInteractions(escalaRepository, plantaoRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando escala não existir")
        void deveLancarNotFoundExceptionQuandoEscalaNaoExistir() {
                Long escalaId = 1L;
                PlantaoRequestDTO dto = PlantaoFactory.criarPlantaoRequestDTOPadrao(escalaId);

                when(escalaRepository.findById(escalaId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.criar(dto))
                                .isInstanceOf(NotFoundException.class);

                verify(escalaRepository).findById(escalaId);
                verifyNoInteractions(plantaoRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando horaEntrada tiver menos de 7 dias")
        void deveLancarConflictExceptionQuandoHoraEntradaTiverMenosDe7Dias() {
                Long escalaId = 1L;
                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(escalaId);
                dto.setHoraEntrada(LocalDateTime.now().plusDays(3));
                dto.setHoraSaida(LocalDateTime.now().plusDays(3).plusHours(12));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);

                when(escalaRepository.findById(escalaId))
                                .thenReturn(Optional.of(escala));

                assertThatThrownBy(() -> plantaoService.criar(dto))
                                .isInstanceOf(ConflictException.class);

                verify(escalaRepository).findById(escalaId);
                verifyNoInteractions(plantaoRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar BadRequestException quando horaEntrada estiver fora do mês da escala")
        void deveLancarBadRequestExceptionQuandoHoraEntradaEstiverForaDoMesDaEscala() {
                Long escalaId = 1L;
                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(escalaId);

                LocalDate mesReferencia = LocalDate.now().plusMonths(1).withDayOfMonth(1);
                dto.setHoraEntrada(mesReferencia.minusDays(1).atStartOfDay());
                dto.setHoraSaida(mesReferencia.minusDays(1).atStartOfDay().plusHours(12));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                escala.setMesReferencia(mesReferencia);

                when(escalaRepository.findById(escalaId))
                                .thenReturn(Optional.of(escala));

                assertThatThrownBy(() -> plantaoService.criar(dto))
                                .isInstanceOf(BadRequestException.class);

                verify(escalaRepository).findById(escalaId);
                verifyNoInteractions(plantaoRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar BadRequestException quando horaSaida estiver fora do mês da escala")
        void deveLancarBadRequestExceptionQuandoHoraSaidaEstiverForaDoMesDaEscala() {
                Long escalaId = 1L;
                LocalDate mesReferencia = LocalDate.now().plusMonths(1).withDayOfMonth(1);
                LocalDateTime entrada = mesReferencia.atStartOfDay().plusDays(10);

                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(escalaId);
                dto.setHoraEntrada(entrada);
                dto.setHoraSaida(entrada.plusMonths(1).plusDays(2));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                escala.setMesReferencia(mesReferencia);

                when(escalaRepository.findById(escalaId))
                                .thenReturn(Optional.of(escala));

                assertThatThrownBy(() -> plantaoService.criar(dto))
                                .isInstanceOf(BadRequestException.class);

                verify(escalaRepository).findById(escalaId);
                verifyNoInteractions(plantaoRepository, plantaoMapper);
        }

        /**
         * ==================== atualizar ====================
         *
         * Método: atualizar()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Plantão válido e mesma escala -> atualiza com sucesso
         *
         * Inválidas:
         * CEI1: Plantão inexistente -> NotFoundException
         * CEI2: Escala publicada -> ConflictException
         * CEI3: Escala diferente -> ConflictException
         *
         * VL (Valores Limite):
         *
         * VL1: Atualização sem troca de escala -> permitido
         */

        @Test
        @DisplayName("Deve atualizar plantão com sucesso")
        void deveAtualizarPlantaoComSucesso() {
                Long plantaoId = 1L;
                Long escalaId = 1L;

                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(escalaId);

                Escala escala = PlantaoFactory.criarEscalaPadrao(escalaId, StatusEscala.RASCUNHO);
                Plantao plantao = mock(Plantao.class);
                Troca troca = PlantaoFactory.criarTrocaPadrao(null);
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.of(plantao));
                when(plantao.getEscala()).thenReturn(escala);
                when(plantao.mesmaEscalaId(escalaId)).thenReturn(true);
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.of(troca));
                when(plantaoMapper.toResponseDTO(plantao, troca))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.atualizar(plantaoId, dto);

                assertThat(resultado).isEqualTo(response);

                verify(plantaoMapper).atualizarEntityComDTO(plantao, dto);
                verify(plantaoRepository).save(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, troca);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando plantão não existir")
        void deveLancarNotFoundExceptionQuandoPlantaoNaoExistir() {
                PlantaoRequestDTO dto = new PlantaoRequestDTO();

                when(plantaoRepository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.atualizar(1L, dto))
                                .isInstanceOf(NotFoundException.class);

                verify(plantaoRepository).findById(1L);
                verifyNoInteractions(plantaoMapper, trocaRepository);
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando escala estiver publicada")
        void deveLancarConflictExceptionQuandoEscalaEstiverPublicada() {
                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(1L);

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.PUBLICADA);
                Plantao plantao = new Plantao();
                plantao.setEscala(escala);

                when(plantaoRepository.findById(1L))
                                .thenReturn(Optional.of(plantao));

                assertThatThrownBy(() -> plantaoService.atualizar(1L, dto))
                                .isInstanceOf(ConflictException.class);

                verify(plantaoRepository).findById(1L);
                verifyNoInteractions(plantaoMapper, trocaRepository);
                verify(plantaoRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando tentar mover plantão para outra escala")
        void deveLancarConflictExceptionQuandoTentarMoverPlantaoParaOutraEscala() {
                PlantaoRequestDTO dto = new PlantaoRequestDTO();
                dto.setEscalaId(2L);

                Escala escala = PlantaoFactory.criarEscalaPadrao(1L, StatusEscala.RASCUNHO);
                Plantao plantao = spy(new Plantao());
                plantao.setEscala(escala);

                when(plantaoRepository.findById(1L))
                                .thenReturn(Optional.of(plantao));

                assertThatThrownBy(() -> plantaoService.atualizar(1L, dto))
                                .isInstanceOf(ConflictException.class);

                verify(plantaoRepository).findById(1L);
                verify(plantao, atLeastOnce()).mesmaEscalaId(2L);
                verifyNoInteractions(plantaoMapper, trocaRepository);
                verify(plantaoRepository, never()).save(any());
        }

        /**
         * ==================== listar ====================
         *
         * Método: listar()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Filtro com abertos=true e profissional existente -> retorna lista
         * CEV2: Filtro com profissional=true -> retorna lista
         * CEV3: Filtro com liderEscala=true -> retorna lista
         * CEV4: Filtro com instituicao=true -> retorna lista
         * CEV5: Lista vazia de plantões -> retorna lista vazia
         *
         * Inválidas:
         * CEI1: abertos=true com profissional inexistente -> NotFoundException
         * CEI2: Nenhum contexto informado -> BadRequestException
         *
         * VL (Valores Limite):
         *
         * VL1: Um único plantão retornado -> lista tamanho 1
         * VL2: Nenhum plantão encontrado -> lista vazia
         */

        @Test
        @DisplayName("Deve listar plantões abertos com sucesso")
        void deveListarPlantoesAbertosComSucesso() {
                Long usuarioId = 1L;
                PlantaoFilter filter = new PlantaoFilter();
                filter.setAbertos(true);
                filter.setIdUsuario(usuarioId);

                Profissional profissional = PlantaoFactory.criarProfissionalPadrao(
                                PlantaoFactory.criarUsuarioPadrao(usuarioId));
                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                PlantaoSimpleResponseDTO dto = PlantaoFactory.mockPlantaoSimpleResponseDTO();

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));
                when(plantaoRepository.findAll(ArgumentMatchers.<Specification<Plantao>>any()))
                                .thenReturn(List.of(plantao));
                when(plantaoMapper.toSimpleResponseDTO(plantao))
                                .thenReturn(dto);

                List<PlantaoSimpleResponseDTO> resultado = plantaoService.listar(filter);

                assertThat(resultado).hasSize(1).contains(dto);

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findAll(ArgumentMatchers.<Specification<Plantao>>any());
                verify(plantaoMapper).toSimpleResponseDTO(plantao);
        }

        @Test
        @DisplayName("Deve listar plantões do profissional")
        void deveListarPlantoesDoProfissional() {
                PlantaoFilter filter = new PlantaoFilter();
                filter.setProfissional(true);
                filter.setIdUsuario(1L);

                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                PlantaoSimpleResponseDTO dto = PlantaoFactory.mockPlantaoSimpleResponseDTO();

                when(plantaoRepository.findAll(any(Specification.class)))
                                .thenReturn(List.of(plantao));
                when(plantaoMapper.toSimpleResponseDTO(plantao))
                                .thenReturn(dto);

                List<PlantaoSimpleResponseDTO> resultado = plantaoService.listar(filter);

                assertThat(resultado).hasSize(1).contains(dto);

                verify(plantaoRepository).findAll(any(Specification.class));
                verify(plantaoMapper).toSimpleResponseDTO(plantao);
        }

        @Test
        @DisplayName("Deve listar plantões do líder de escala")
        void deveListarPlantoesDoLiderEscala() {
                PlantaoFilter filter = new PlantaoFilter();
                filter.setLiderEscala(true);
                filter.setIdUsuario(1L);

                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                PlantaoSimpleResponseDTO dto = PlantaoFactory.mockPlantaoSimpleResponseDTO();

                when(plantaoRepository.findAll(any(Specification.class)))
                                .thenReturn(List.of(plantao));
                when(plantaoMapper.toSimpleResponseDTO(plantao))
                                .thenReturn(dto);

                List<PlantaoSimpleResponseDTO> resultado = plantaoService.listar(filter);

                assertThat(resultado).hasSize(1).contains(dto);

                verify(plantaoRepository).findAll(any(Specification.class));
                verify(plantaoMapper).toSimpleResponseDTO(plantao);
        }

        @Test
        @DisplayName("Deve listar plantões da instituição")
        void deveListarPlantoesDaInstituicao() {
                PlantaoFilter filter = new PlantaoFilter();
                filter.setInstituicao(true);
                filter.setIdUsuario(1L);

                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                PlantaoSimpleResponseDTO dto = PlantaoFactory.mockPlantaoSimpleResponseDTO();

                when(plantaoRepository.findAll(any(Specification.class)))
                                .thenReturn(List.of(plantao));
                when(plantaoMapper.toSimpleResponseDTO(plantao))
                                .thenReturn(dto);

                List<PlantaoSimpleResponseDTO> resultado = plantaoService.listar(filter);

                assertThat(resultado).hasSize(1).contains(dto);

                verify(plantaoRepository).findAll(any(Specification.class));
                verify(plantaoMapper).toSimpleResponseDTO(plantao);
        }

        @Test
        @DisplayName("Deve lançar BadRequestException quando nenhum contexto for informado")
        void deveLancarBadRequestExceptionQuandoNenhumContextoForInformado() {
                PlantaoFilter filter = new PlantaoFilter();

                assertThatThrownBy(() -> plantaoService.listar(filter))
                                .isInstanceOf(BadRequestException.class);

                verifyNoInteractions(plantaoRepository, plantaoMapper, profissionalRepository);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando profissional não existir")
        void deveLancarNotFoundExceptionQuandoProfissionalNaoExistir() {
                Long usuarioId = 1L;
                PlantaoFilter filter = new PlantaoFilter();
                filter.setAbertos(true);
                filter.setIdUsuario(usuarioId);

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.listar(filter))
                                .isInstanceOf(NotFoundException.class);

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verifyNoInteractions(plantaoRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver plantões")
        void deveRetornarListaVaziaQuandoNaoHouverPlantoes() {
                PlantaoFilter filter = new PlantaoFilter();
                filter.setProfissional(true);
                filter.setIdUsuario(1L);

                when(plantaoRepository.findAll(any(Specification.class)))
                                .thenReturn(List.of());

                List<PlantaoSimpleResponseDTO> resultado = plantaoService.listar(filter);

                assertThat(resultado).isEmpty();

                verify(plantaoRepository).findAll(any(Specification.class));
                verifyNoInteractions(plantaoMapper);
        }

        /**
         * ==================== buscarPorId ====================
         *
         * Método: buscarPorId()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Plantão existente sem troca associada -> retorna DTO com troca null
         * CEV2: Plantão existente com troca associada -> retorna DTO com troca mais
         * recente
         *
         * Inválidas:
         * CEI1: Plantão inexistente -> NotFoundException
         *
         * VL (Valores Limite):
         *
         * VL1: Primeiro plantão encontrado no sistema -> retorno válido
         * VL2: Plantão com múltiplas trocas -> retorna apenas a mais recente
         */

        @Test
        @DisplayName("Deve buscar plantão por ID sem troca associada")
        void deveBuscarPlantaoPorIdSemTroca() {
                Long id = 1L;

                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.of(plantao));
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.empty());
                when(plantaoMapper.toResponseDTO(plantao, null))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.buscarPorId(id);

                assertThat(resultado).isEqualTo(response);

                verify(plantaoRepository).findById(id);
                verify(trocaRepository).findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, null);
        }

        @Test
        @DisplayName("Deve buscar plantão por ID com troca mais recente")
        void deveBuscarPlantaoPorIdComTroca() {
                Long id = 1L;

                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(null);
                Troca troca = PlantaoFactory.criarTrocaPadrao(null);
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.of(plantao));
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.of(troca));
                when(plantaoMapper.toResponseDTO(plantao, troca))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.buscarPorId(id);

                assertThat(resultado).isEqualTo(response);

                verify(plantaoRepository).findById(id);
                verify(trocaRepository).findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, troca);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando plantão não existir")
        void LancarNotFoundExceptionQuandoPlantaoNaoExistir() {
                Long id = 1L;

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.buscarPorId(id))
                                .isInstanceOf(NotFoundException.class);

                verify(plantaoRepository).findById(id);
                verifyNoInteractions(trocaRepository, plantaoMapper);
        }

        /**
         * ==================== deletar ====================
         *
         * Método: deletar()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Plantão existente, escala não publicada e horaEntrada futura -> deleta
         * com sucesso
         *
         * Inválidas:
         * CEI1: Plantão inexistente -> NotFoundException
         * CEI2: Escala publicada -> ConflictException
         * CEI3: horaEntrada já passada -> ConflictException
         *
         * VL (Valores Limite):
         *
         * VL1: horaEntrada exatamente no futuro (agora + 1ms ou mais) -> permitido
         * VL2: horaEntrada exatamente no passado recente -> bloqueado
         * VL3: Plantão recém-criado com entrada distante -> permitido
         */

        @Test
        @DisplayName("Deve deletar plantão com sucesso")
        void deveDeletarPlantaoComSucesso() {
                Long id = 1L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Plantao plantao = new Plantao();
                plantao.setEscala(escala);
                plantao.setHoraEntrada(LocalDateTime.now().plusDays(2));

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.of(plantao));

                plantaoService.deletar(id);

                verify(plantaoRepository).findById(id);
                verify(plantaoRepository).delete(plantao);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando plantão não existir")
        void deveLancarNotFoundExceptionAoDeletarQuandoPlantaoNaoExistir() {
                Long id = 1L;

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.deletar(id))
                                .isInstanceOf(NotFoundException.class);

                verify(plantaoRepository).findById(id);
                verify(plantaoRepository, never()).delete(any(Plantao.class));
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando escala estiver publicada")
        void deveLancarConflictExceptionQuandoEscalaPublicada() {
                Long id = 1L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.PUBLICADA);
                Plantao plantao = new Plantao();
                plantao.setEscala(escala);
                plantao.setHoraEntrada(LocalDateTime.now().plusDays(2));

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.of(plantao));

                assertThatThrownBy(() -> plantaoService.deletar(id))
                                .isInstanceOf(ConflictException.class);

                verify(plantaoRepository).findById(id);
                verify(plantaoRepository, never()).delete(any(Plantao.class));
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando horaEntrada já tiver passado")
        void deveLancarConflictExceptionQuandoHoraEntradaPassada() {
                Long id = 1L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Plantao plantao = new Plantao();
                plantao.setEscala(escala);
                plantao.setHoraEntrada(LocalDateTime.now().minusDays(1));

                when(plantaoRepository.findById(id))
                                .thenReturn(Optional.of(plantao));

                assertThatThrownBy(() -> plantaoService.deletar(id))
                                .isInstanceOf(ConflictException.class);

                verify(plantaoRepository).findById(id);
                verify(plantaoRepository, never()).delete(any(Plantao.class));
        }

        /**
         * ==================== pegarPlantao ====================
         *
         * Método: pegarPlantao()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional válido + plantão válido + especialidade compatível + sem
         * troca pendente -> pega plantão com sucesso
         * CEV2: Profissional válido + plantão válido + troca pendente existente ->
         * confirma troca e pega plantão
         *
         * Inválidas:
         * CEI1: usuário não possui profissional -> NotFoundException
         * CEI2: plantão inexistente -> NotFoundException
         * CEI3: escala publicada -> ConflictException
         * CEI4: profissional sem especialidade para setor -> ConflictException
         * CEI5: profissional já vinculado ao plantão -> Exception (regra de domínio)
         *
         * VL (Valores Limite):
         *
         * VL1: primeiro profissional a pegar plantão -> sucesso
         * VL2: último instante antes de validação de vínculo -> permitido
         */

        @Test
        @DisplayName("Deve pegar plantão com sucesso quando não há troca pendente")
        void devePegarPlantaoComSucessoSemTroca() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                Usuario usuario = PlantaoFactory.criarUsuarioPadrao(usuarioId);
                Profissional profissional = spy(PlantaoFactory.criarProfissionalPadrao(usuario));

                doReturn(true).when(profissional).possuiEspecialidadeParaSetor(any());

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = spy(PlantaoFactory.criarPlantaoPadrao(escala));

                doNothing().when(plantao).verificarProfissionalJaVinculado(profissional);
                doNothing().when(plantao).adicionarProfissionalConfirmado(profissional);

                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.of(plantao));
                when(trocaRepository.findByPlantaoOrigemAndStatusAndProfissionalDestinoIsNull(
                                plantao, StatusTroca.PENDENTE))
                                .thenReturn(Optional.empty());
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.empty());

                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoMapper.toResponseDTO(plantao, null))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.pegarPlantao(plantaoId, usuarioId);

                assertThat(resultado).isEqualTo(response);

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findById(plantaoId);
                verify(plantao).verificarProfissionalJaVinculado(profissional);
                verify(plantao).adicionarProfissionalConfirmado(profissional);
                verify(plantaoRepository).save(plantao);
                verify(profissionalRepository).save(profissional);
                verify(plantaoMapper).toResponseDTO(plantao, null);
        }

        @Test
        @DisplayName("Deve pegar plantão com sucesso quando há troca pendente")
        void devePegarPlantaoComSucessoComTrocaPendente() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                Usuario usuario = PlantaoFactory.criarUsuarioPadrao(usuarioId);
                Profissional profissional = spy(PlantaoFactory.criarProfissionalPadrao(usuario));

                doReturn(true).when(profissional).possuiEspecialidadeParaSetor(any());

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = spy(PlantaoFactory.criarPlantaoPadrao(escala));

                doNothing().when(plantao).verificarProfissionalJaVinculado(profissional);
                doNothing().when(plantao).adicionarProfissionalConfirmado(profissional);

                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.of(plantao));

                Troca trocaPendente = spy(PlantaoFactory.criarTrocaPadrao(StatusTroca.PENDENTE));

                when(trocaRepository.findByPlantaoOrigemAndStatusAndProfissionalDestinoIsNull(
                                plantao, StatusTroca.PENDENTE))
                                .thenReturn(Optional.of(trocaPendente));
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.of(trocaPendente));

                doNothing().when(trocaPendente).confirmarParaProfissional(profissional);

                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoMapper.toResponseDTO(plantao, trocaPendente))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.pegarPlantao(plantaoId, usuarioId);

                assertThat(resultado).isEqualTo(response);

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findById(plantaoId);
                verify(plantao).verificarProfissionalJaVinculado(profissional);
                verify(plantao).adicionarProfissionalConfirmado(profissional);
                verify(trocaPendente).confirmarParaProfissional(profissional);
                verify(trocaRepository).save(trocaPendente);
                verify(profissionalRepository).save(profissional);
                verify(plantaoRepository).save(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, trocaPendente);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando usuário não possuir profissional")
        void deveLancarNotFoundExceptionQuandoUsuarioNaoPossuirProfissional() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.pegarPlantao(plantaoId, usuarioId))
                                .isInstanceOf(NotFoundException.class)
                                .hasMessageContaining("Profissional não encontrado");

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verifyNoInteractions(plantaoRepository, trocaRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando plantão não existir")
        void deveLancarNotFoundExceptionQuandoPlantaoNaoExistirNoPegarPlantao() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                Usuario usuario = PlantaoFactory.criarUsuarioPadrao(usuarioId);
                Profissional profissional = PlantaoFactory.criarProfissionalPadrao(usuario);

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));
                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.pegarPlantao(plantaoId, usuarioId))
                                .isInstanceOf(NotFoundException.class)
                                .hasMessageContaining("Plantão não encontrado");

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findById(plantaoId);
                verifyNoInteractions(trocaRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando escala estiver publicada")
        void deveLancarConflictExceptionQuandoEscalaEstiverPublicadaNoPegarPlantao() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                Usuario usuario = PlantaoFactory.criarUsuarioPadrao(usuarioId);
                Profissional profissional = PlantaoFactory.criarProfissionalPadrao(usuario);

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.PUBLICADA);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = new Plantao();
                plantao.setEscala(escala);

                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.of(plantao));

                assertThatThrownBy(() -> plantaoService.pegarPlantao(plantaoId, usuarioId))
                                .isInstanceOf(ConflictException.class)
                                .hasMessageContaining("não está publicada");

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findById(plantaoId);
                verifyNoInteractions(trocaRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar exceção quando profissional já estiver vinculado ao plantão")
        void deveLancarExceptionQuandoProfissionalJaVinculado() {
                Long usuarioId = 1L;
                Long plantaoId = 10L;

                Usuario usuario = PlantaoFactory.criarUsuarioPadrao(usuarioId);
                Profissional profissional = spy(PlantaoFactory.criarProfissionalPadrao(usuario));

                doReturn(true).when(profissional).possuiEspecialidadeParaSetor(any());

                when(profissionalRepository.findByUsuarioId(usuarioId))
                                .thenReturn(Optional.of(profissional));

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = spy(new Plantao());
                plantao.setEscala(escala);

                when(plantaoRepository.findById(plantaoId))
                                .thenReturn(Optional.of(plantao));

                doThrow(new ConflictException("Profissional já vinculado"))
                                .when(plantao)
                                .verificarProfissionalJaVinculado(profissional);

                assertThatThrownBy(() -> plantaoService.pegarPlantao(plantaoId, usuarioId))
                                .isInstanceOf(ConflictException.class)
                                .hasMessageContaining("vinculado");

                verify(profissionalRepository).findByUsuarioId(usuarioId);
                verify(plantaoRepository).findById(plantaoId);
                verify(plantao).verificarProfissionalJaVinculado(profissional);
                verifyNoInteractions(trocaRepository, plantaoMapper);
                verify(plantaoRepository, never()).save(any());
                verify(profissionalRepository, never()).save(any());
        }

        /**
         * ==================== atualizarPlantaoCombinado ====================
         *
         * Método: atualizarPlantaoCombinado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Líder válido + profissional existente + adicionar = true
         * -> adiciona profissional ao plantão combinado com sucesso
         *
         * CEV2: Líder válido + profissional existente + adicionar = false
         * -> remove profissional do plantão combinado com sucesso
         *
         * Inválidas:
         * CEI1: líder sem permissão para o plantão -> exceção de validação
         * (validarPermissaoLider)
         * CEI2: profissional inexistente -> NotFoundException
         *
         * VL (Valores Limite):
         *
         * VL1: primeiro profissional adicionado ao combinado -> operação válida
         * VL2: remoção do último profissional do combinado -> operação válida
         */

        @Test
        @DisplayName("Deve adicionar profissional ao plantão combinado com sucesso")
        void deveAdicionarProfissionalCombinadoComSucesso() {
                Long plantaoId = 1L;
                Long profissionalUsuarioId = 2L;
                Long liderUsuarioId = 3L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = spy(PlantaoFactory.criarPlantaoPadrao(escala));
                Profissional profissional = PlantaoFactory.criarProfissionalPadrao(
                                PlantaoFactory.criarUsuarioPadrao(profissionalUsuarioId));
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId))
                                .thenReturn(Optional.of(plantao));
                when(profissionalRepository.findByUsuarioId(profissionalUsuarioId))
                                .thenReturn(Optional.of(profissional));
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.empty());
                when(plantaoMapper.toResponseDTO(plantao, null))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.atualizarPlantaoCombinado(
                                plantaoId, profissionalUsuarioId, liderUsuarioId, true);

                assertThat(resultado).isEqualTo(response);

                verify(plantaoRepository).findByIdAndLiderValido(plantaoId, liderUsuarioId);
                verify(profissionalRepository).findByUsuarioId(profissionalUsuarioId);
                verify(plantao).adicionarProfissionalCombinado(profissional);
                verify(profissionalRepository).save(profissional);
                verify(plantaoRepository).save(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, null);
        }

        @Test
        @DisplayName("Deve remover profissional do plantão combinado com sucesso")
        void deveRemoverProfissionalCombinadoComSucesso() {
                Long plantaoId = 1L;
                Long profissionalUsuarioId = 2L;
                Long liderUsuarioId = 3L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Setor setor = PlantaoFactory.criarSetorPadrao();
                escala.setSetor(setor);

                Plantao plantao = spy(PlantaoFactory.criarPlantaoPadrao(escala));
                Profissional profissional = PlantaoFactory.criarProfissionalPadrao(
                                PlantaoFactory.criarUsuarioPadrao(profissionalUsuarioId));
                PlantaoResponseDTO response = PlantaoFactory.mockPlantaoResponseDTO();

                when(plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId))
                                .thenReturn(Optional.of(plantao));
                when(profissionalRepository.findByUsuarioId(profissionalUsuarioId))
                                .thenReturn(Optional.of(profissional));
                when(trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao))
                                .thenReturn(Optional.empty());
                when(plantaoMapper.toResponseDTO(plantao, null))
                                .thenReturn(response);

                PlantaoResponseDTO resultado = plantaoService.atualizarPlantaoCombinado(
                                plantaoId, profissionalUsuarioId, liderUsuarioId, false);

                assertThat(resultado).isEqualTo(response);

                verify(plantaoRepository).findByIdAndLiderValido(plantaoId, liderUsuarioId);
                verify(profissionalRepository).findByUsuarioId(profissionalUsuarioId);
                verify(plantao).removerProfissionalCombinado(profissional);
                verify(profissionalRepository).save(profissional);
                verify(plantaoRepository).save(plantao);
                verify(plantaoMapper).toResponseDTO(plantao, null);
        }

        @Test
        @DisplayName("Deve lançar ForbiddenException quando líder não tiver permissão no plantão")
        void deveLancarForbiddenExceptionQuandoLiderNaoTiverPermissao() {
                Long plantaoId = 1L;
                Long profissionalUsuarioId = 2L;
                Long liderUsuarioId = 3L;

                when(plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.atualizarPlantaoCombinado(
                                plantaoId, profissionalUsuarioId, liderUsuarioId, true))
                                .isInstanceOf(ForbiddenException.class);

                verify(plantaoRepository).findByIdAndLiderValido(plantaoId, liderUsuarioId);
                verifyNoInteractions(profissionalRepository, trocaRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar ForbiddenException quando líder não tem permissão para o plantão")
        void deveLancarForbiddenExceptionQuandoLiderNaoTemPermissao() {
                Long plantaoId = 1L;
                Long profissionalUsuarioId = 10L;
                Long liderUsuarioId = 99L;

                when(plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.atualizarPlantaoCombinado(
                                plantaoId, profissionalUsuarioId, liderUsuarioId, true))
                                .isInstanceOf(ForbiddenException.class);

                verify(plantaoRepository).findByIdAndLiderValido(plantaoId, liderUsuarioId);
                verifyNoInteractions(profissionalRepository, trocaRepository, plantaoMapper);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando profissional não existir")
        void deveLancarNotFoundExceptionQuandoProfissionalNaoExistirNoCombinado() {
                Long plantaoId = 1L;
                Long profissionalUsuarioId = 10L;
                Long liderUsuarioId = 99L;

                Escala escala = PlantaoFactory.criarEscalaPadrao(null, StatusEscala.RASCUNHO);
                Plantao plantao = PlantaoFactory.criarPlantaoPadrao(escala);

                when(plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId))
                                .thenReturn(Optional.of(plantao));
                when(profissionalRepository.findByUsuarioId(profissionalUsuarioId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> plantaoService.atualizarPlantaoCombinado(
                                plantaoId, profissionalUsuarioId, liderUsuarioId, true))
                                .isInstanceOf(NotFoundException.class)
                                .hasMessageContaining("Profissional não encontrado");

                verify(plantaoRepository).findByIdAndLiderValido(plantaoId, liderUsuarioId);
                verify(profissionalRepository).findByUsuarioId(profissionalUsuarioId);
                verifyNoInteractions(trocaRepository, plantaoMapper);
                verify(plantaoRepository, never()).save(any());
        }

        /**
         * ==================== criarContextoParaCopia ====================
         *
         * Método: criarContextoParaCopia()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Existem plantões na escala anterior -> retorna Map<LocalDateTime,
         * Plantao>
         *
         * Inválidas:
         * CEI1: Não existem plantões na escala anterior -> NotFoundException
         *
         * VL (Valores Limite):
         *
         * VL1: Um único plantão retornado -> Map com tamanho 1
         * VL2: Múltiplos plantões retornados -> Map com múltiplos registros
         * VL3: Plantões com horários distintos (chave única no Map)
         */

        @Test
        @DisplayName("Deve criar contexto de cópia com sucesso quando existir escala anterior")
        void deveCriarContextoParaCopiaComSucesso() {
                LocalDate mes = LocalDate.now().plusMonths(1);
                Long setorId = 1L;
                Long liderId = 2L;

                Plantao p1 = PlantaoFactory.criarPlantaoPadrao(null);
                p1.setHoraEntrada(LocalDateTime.now().plusDays(1));

                Plantao p2 = PlantaoFactory.criarPlantaoPadrao(null);
                p2.setHoraEntrada(LocalDateTime.now().plusDays(2));

                when(plantaoRepository.buscarPlantoesUltimaEscalaAnterior(setorId, liderId, mes))
                                .thenReturn(List.of(p1, p2));

                Map<LocalDateTime, Plantao> resultado = plantaoService.criarContextoParaCopia(mes, setorId, liderId);

                assertThat(resultado)
                                .hasSize(2)
                                .containsEntry(p1.getHoraEntrada(), p1)
                                .containsEntry(p2.getHoraEntrada(), p2);

                verify(plantaoRepository).buscarPlantoesUltimaEscalaAnterior(setorId, liderId, mes);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando não existirem plantões na escala anterior")
        void deveLancarNotFoundExceptionQuandoNaoExistiremPlantoesNaEscalaAnterior() {
                LocalDate mes = LocalDate.now().plusMonths(1);
                Long setorId = 1L;
                Long liderId = 2L;

                when(plantaoRepository.buscarPlantoesUltimaEscalaAnterior(setorId, liderId, mes))
                                .thenReturn(List.of());

                assertThatThrownBy(() -> plantaoService.criarContextoParaCopia(mes, setorId, liderId))
                                .isInstanceOf(NotFoundException.class)
                                .hasMessageContaining("Não existe escala anterior para cópia");

                verify(plantaoRepository).buscarPlantoesUltimaEscalaAnterior(setorId, liderId, mes);
                verifyNoMoreInteractions(plantaoRepository);
        }

}
