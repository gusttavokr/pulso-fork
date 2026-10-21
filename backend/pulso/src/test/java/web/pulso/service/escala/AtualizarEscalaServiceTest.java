package web.pulso.service.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.pulso.dto.request.EscalaRequestDTO;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.entity.Escala;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.EscalaRepository;
import web.pulso.service.EscalaService;
import web.pulso.service.PlantaoService;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.resolver.escala.EscalaContextoResolver;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaService - Atualizar")
class AtualizarEscalaServiceTest {

    @Mock
    private EscalaMapper escalaMapper;

    @Mock
    private EscalaRepository escalaRepository;

    @Mock
    private EscalaContextoResolver contextoResolver;

    @Mock
    private PlantaoService plantaoService;

    @InjectMocks
    private EscalaService escalaService;

    private static final Long ESCALA_ID_VALIDO   = 1L;
    private static final Long ESCALA_ID_INVALIDO = 321L;
    private static final Long SETOR_ID_VALIDO    = 1L;

    private LocalDate mesValido;
    private Setor setorValido;
    private Escala escalaRascunho;
    private EscalaRequestDTO dtoValido;
    private EscalaSimpleResponseDTO responseValido;

    @BeforeEach
    void setUp() {
        mesValido = LocalDate.now().plusMonths(2).withDayOfMonth(1);

        setorValido = new Setor();
        setorValido.setId(SETOR_ID_VALIDO);

        // Escala já persistida em RASCUNHO — estado base para atualização
        escalaRascunho = new Escala();
        escalaRascunho.setNome("Escala Original");
        escalaRascunho.setMesReferencia(mesValido);
        escalaRascunho.setSetor(setorValido);
        escalaRascunho.setStatus(StatusEscala.RASCUNHO);

        dtoValido = new EscalaRequestDTO();
        dtoValido.setNome("Escala Atualizada");
        dtoValido.setMes(mesValido);
        dtoValido.setSetorId(SETOR_ID_VALIDO);

        responseValido = new EscalaSimpleResponseDTO();
        responseValido.setNome("Escala Atualizada");
        responseValido.setStatus(StatusEscala.RASCUNHO);
    }

    @Test
    @DisplayName("[CE-valido] Deve atualizar escala com sucesso quando todos os dados forem válidos")
    void deveAtualizarComSucessoQuandoDadosForemValidos() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        EscalaSimpleResponseDTO resultado = escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Escala Atualizada");
        assertThat(resultado.getStatus()).isEqualTo(StatusEscala.RASCUNHO);

        verify(escalaRepository).save(any(Escala.class));
        verify(escalaMapper).toSimpleResponseDTO(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-valido | VL-min] Deve atualizar quando novo nome tiver exatamente 3 caracteres")
    void deveAtualizarQuandoNovoNomeTiverExatamente3Caracteres() {
        // Arrange
        dtoValido.setNome("Esc"); // VL: mínimo válido

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        EscalaSimpleResponseDTO responseMin = new EscalaSimpleResponseDTO();
        responseMin.setNome("Esc");
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseMin);

        // Act
        EscalaSimpleResponseDTO resultado = escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido);

        // Assert
        assertThat(resultado.getNome()).isEqualTo("Esc");
    }

    @Test
    @DisplayName("[CE-valido | VL-max] Deve atualizar quando novo nome tiver exatamente 255 caracteres")
    void deveAtualizarQuandoNovoNomeTiverExatamente255Caracteres() {
        // Arrange
        String nomeMax = "A".repeat(255); // VL: máximo válido
        dtoValido.setNome(nomeMax);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act & Assert
        assertThatCode(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException quando escalaId não existir")
    void deveLancarNotFoundQuandoEscalaIdNaoExistir() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_INVALIDO, dtoValido))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Escala não encontrada");

        // Nenhuma interação além do findById deve ocorrer
        verify(escalaRepository, never()).save(any());
        verify(escalaMapper, never()).toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException antes de qualquer validação de negócio")
    void deveLancarNotFoundAntesDeValidarNegocio() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_INVALIDO, dtoValido))
                .isInstanceOf(NotFoundException.class);

        // Assert — findById foi chamado, mais nada
        verify(escalaRepository).findById(ESCALA_ID_INVALIDO);
        verifyNoMoreInteractions(escalaRepository);
        verifyNoInteractions(escalaMapper);
    }

    @Test
    @DisplayName("[CE-status-invalido] Deve propagar exceção quando escala estiver PUBLICADA")
    void devePropagarExcecaoQuandoEscalaEstiverPublicada() {
        // Arrange
        Escala escalaPublicada = spy(new Escala());
        escalaPublicada.setNome("Escala Publicada");
        escalaPublicada.setMesReferencia(mesValido);
        escalaPublicada.setSetor(setorValido);
        escalaPublicada.setStatus(StatusEscala.PUBLICADA);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaPublicada));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("publicada");

        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-status-invalido] Deve propagar exceção quando escala estiver ARQUIVADA")
    void devePropagarExcecaoQuandoEscalaEstiverArquivada() {
        // Arrange
        Escala escalaArquivada = spy(new Escala());
        escalaArquivada.setNome("Escala Arquivada");
        escalaArquivada.setMesReferencia(mesValido);
        escalaArquivada.setSetor(setorValido);
        escalaArquivada.setStatus(StatusEscala.ARQUIVADA);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaArquivada));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .isInstanceOf(BadRequestException.class);

        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-setor-invalido] Deve propagar exceção quando DTO tentar alterar o setor")
    void devePropagarExcecaoQuandoDtoTentarAlterarSetor() {
        // Arrange
        Long setorDiferenteId = 99L;
        dtoValido.setSetorId(setorDiferenteId);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("setor");

        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-mes-invalido] Deve propagar exceção quando DTO tentar alterar o mês de referência")
    void devePropagarExcecaoQuandoDtoTentarAlterarMes() {
        // Arrange
        dtoValido.setMes(mesValido.plusMonths(1));

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("mês");

        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-nome-invalido] Deve propagar exceção quando novo nome for string vazia")
    void devePropagarExcecaoQuandoNovoNomeForVazio() {
        // Arrange
        // "" é rejeitado em validarAlteracoesPermitidas (isBlank) antes do save
        dtoValido.setNome("");

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido))
                .isInstanceOf(BadRequestException.class);

        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-valido] Deve aplicar novo nome na entity antes de salvar")
    void deveAplicarNovoNomeNaEntityAntesDoSave() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> {
                    Escala salva = inv.getArgument(0);
                    // Verifica que o nome já foi atualizado quando save é chamado
                    assertThat(salva.getNome()).isEqualTo("Escala Atualizada");
                    return salva;
                });
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido);

        // Assert — a verificação está dentro do thenAnswer acima
        verify(escalaRepository).save(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-ordem] Deve buscar escala antes de validar e salvar")
    void deveBuscarEscalaAntesDeValidarESalvar() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido);

        // Assert
        var inOrder = inOrder(escalaRepository, escalaMapper);
        inOrder.verify(escalaRepository).findById(ESCALA_ID_VALIDO);
        inOrder.verify(escalaRepository).save(any(Escala.class));
        inOrder.verify(escalaMapper).toSimpleResponseDTO(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-ordem] Deve chamar mapper somente após salvar")
    void deveChamarMapperSomenteAposSalvar() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.atualizar(ESCALA_ID_VALIDO, dtoValido);

        // Assert
        var inOrder = inOrder(escalaRepository, escalaMapper);
        inOrder.verify(escalaRepository).save(any(Escala.class));
        inOrder.verify(escalaMapper).toSimpleResponseDTO(any(Escala.class));
    }
}