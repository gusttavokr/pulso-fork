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
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.EscalaRepository;
import web.pulso.service.EscalaService;
import web.pulso.service.PlantaoService;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.resolver.escala.EscalaContexto;
import web.pulso.service.resolver.escala.EscalaContextoResolver;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaService - Criar")
class CriarEscalaServiceTest {

    // -------------------------------------------------------------------------
    // Dependências mockadas
    // -------------------------------------------------------------------------

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

    private static final Long USUARIO_ID_VALIDO = 1L;
    private static final Long SETOR_ID_VALIDO   = 1L;

    private Setor setorValido;
    private Profissional liderValido;
    private EscalaContexto contextoValido;
    private EscalaRequestDTO dtoValido;
    private EscalaSimpleResponseDTO responseValido;
    private LocalDate mesValido;

    @BeforeEach
    void setUp() {
        mesValido = LocalDate.now().plusMonths(2).withDayOfMonth(1);

        setorValido = new Setor();
        setorValido.setId(SETOR_ID_VALIDO);

        liderValido = new Profissional();
        liderValido.setId(USUARIO_ID_VALIDO);

        contextoValido = new EscalaContexto(liderValido, setorValido);

        dtoValido = new EscalaRequestDTO();
        dtoValido.setNome("Escala Fevereiro");
        dtoValido.setMes(mesValido);
        dtoValido.setSetorId(SETOR_ID_VALIDO);
        dtoValido.setCopia(null); // copia omitida → default false

        responseValido = new EscalaSimpleResponseDTO();
        responseValido.setNome("Escala Fevereiro");
        responseValido.setStatus(StatusEscala.RASCUNHO);
    }

    @Test
    @DisplayName("[CE-valido] Deve criar escala com sucesso quando todos os dados forem válidos e copia for nula")
    void deveCriarComSucessoQuandoDadosValidosECopiaForNula() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        EscalaSimpleResponseDTO resultado = escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Escala Fevereiro");
        assertThat(resultado.getStatus()).isEqualTo(StatusEscala.RASCUNHO);

        verify(escalaRepository).save(any(Escala.class));
        verify(escalaMapper).toSimpleResponseDTO(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-valido] Deve criar escala com sucesso quando copia for false explicitamente")
    void deveCriarComSucessoQuandoCopiaForFalseExplicito() {
        // Arrange
        dtoValido.setCopia(false);

        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        EscalaSimpleResponseDTO resultado = escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert
        assertThat(resultado).isNotNull();

        // copia=false → não deve consultar contexto de cópia de plantões
        verify(plantaoService, never()).criarContextoParaCopia(any(), any(), any());
    }

    @Test
    @DisplayName("[CE-valido] Deve criar escala com plantoes copiados quando copia for true")
    void deveCriarComSucessoQuandoCopiaForTrue() {
        // Arrange
        dtoValido.setCopia(true);

        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(plantaoService.criarContextoParaCopia(mesValido, SETOR_ID_VALIDO, USUARIO_ID_VALIDO))
                .thenReturn(Collections.emptyMap());
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        EscalaSimpleResponseDTO resultado = escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert
        assertThat(resultado).isNotNull();

        // copia=true → deve consultar contexto de cópia
        verify(plantaoService).criarContextoParaCopia(mesValido, SETOR_ID_VALIDO, USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("[CE-usuario-invalido] Deve lançar exceção quando usuarioId não existir no sistema")
    void deveLancarExcecaoQuandoUsuarioIdNaoExistir() {
        // Arrange
        Long usuarioInexistente = 321L;

        when(contextoResolver.resolver(usuarioInexistente, SETOR_ID_VALIDO))
                .thenThrow(new NotFoundException("usuario não encontrado com o usuarioId informado"));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.criar(dtoValido, usuarioInexistente))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("usuario");

        // Nenhuma interação com o repositório deve ocorrer após falha no contexto
        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-setor-invalido] Deve lançar exceção quando setorId não existir no sistema")
    void deveLancarExcecaoQuandoSetorIdNaoExistir() {
        // Arrange
        Long setorInexistente = 321L;
        dtoValido.setSetorId(setorInexistente);

        when(contextoResolver.resolver(USUARIO_ID_VALIDO, setorInexistente))
                .thenThrow(new NotFoundException("setor não encontrado com o setorId informado"));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.criar(dtoValido, USUARIO_ID_VALIDO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("setor");

        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-duplicata] Deve lançar ConflictException quando já existir escala com mesmo setorId e mes")
    void deveLancarConflictExceptionQuandoJaExistirEscalaNoSetorNoMes() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(true); // duplicata

        // Act & Assert
        assertThatThrownBy(() -> escalaService.criar(dtoValido, USUARIO_ID_VALIDO))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("mês de referência");

        // Não deve salvar nem gerar plantões
        verify(escalaRepository, never()).save(any());
        verify(plantaoService, never()).criarContextoParaCopia(any(), any(), any());
    }

    @Test
    @DisplayName("[CE-valido] Não deve lançar exceção quando combinação setorId + mes for única")
    void naoDeveLancarExcecaoQuandoCombinacaoSetorMesForUnica() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act & Assert
        assertThatCode(() -> escalaService.criar(dtoValido, USUARIO_ID_VALIDO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-ordem] Deve resolver contexto antes de checar duplicata")
    void deveResolverContextoAntesDeChecarDuplicata() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenThrow(new NotFoundException("setor não encontrado"));

        // Act
        assertThatThrownBy(() -> escalaService.criar(dtoValido, USUARIO_ID_VALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert — duplicata nunca deve ser checada se o contexto falhou
        verify(escalaRepository, never()).existsEscalaNoSetorNoMes(any(), any());
    }

    @Test
    @DisplayName("[CE-ordem] Deve checar duplicata antes de salvar")
    void deveChecarDuplicataAntesDeSalvar() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(true);

        // Act
        assertThatThrownBy(() -> escalaService.criar(dtoValido, USUARIO_ID_VALIDO))
                .isInstanceOf(ConflictException.class);

        // Assert — save nunca deve ser chamado se há duplicata
        verify(escalaRepository, never()).save(any());
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve chamar contextoResolver com usuarioId e setorId corretos")
    void deveChamarContextoResolverComParametrosCorretos() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert
        verify(contextoResolver).resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve checar duplicata com setorId e mes vindos do DTO")
    void deveChecarDuplicataComSetorIdEMesDoDTO() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert
        verify(escalaRepository).existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve chamar mapper após salvar a escala")
    void deveChamarMapperAposSalvar() {
        // Arrange
        when(contextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .thenReturn(contextoValido);
        when(escalaRepository.existsEscalaNoSetorNoMes(SETOR_ID_VALIDO, mesValido))
                .thenReturn(false);
        when(escalaRepository.save(any(Escala.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseValido);

        // Act
        escalaService.criar(dtoValido, USUARIO_ID_VALIDO);

        // Assert — inOrder garante que save veio antes do mapper
        var inOrder = inOrder(escalaRepository, escalaMapper);
        inOrder.verify(escalaRepository).save(any(Escala.class));
        inOrder.verify(escalaMapper).toSimpleResponseDTO(any(Escala.class));
    }
}