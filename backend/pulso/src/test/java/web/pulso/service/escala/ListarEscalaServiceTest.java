package web.pulso.service.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.entity.Escala;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.EscalaRepository;
import web.pulso.service.EscalaService;
import web.pulso.service.PlantaoService;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.resolver.escala.EscalaContextoResolver;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaService - Listar")
class ListarEscalaServiceTest {

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

    private EscalaSimpleResponseDTO responseItem;

    @BeforeEach
    void setUp() {
        responseItem = new EscalaSimpleResponseDTO();
        responseItem.setNome("Escala Teste");
        responseItem.setStatus(StatusEscala.RASCUNHO);
    }

    @Test
    @DisplayName("[CE-filtro-invalido] Deve lançar BadRequestException quando liderEscala e instituicao forem ambos nulos")
    void deveLancarExcecaoQuandoAmbosForemNulos() {
        // Arrange & Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, null, null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("liderEscala")
                .hasMessageContaining("instituicao");

        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-filtro-invalido] Deve lançar BadRequestException quando liderEscala for false e instituicao for nula")
    void deveLancarExcecaoQuandoLiderFalseEInstituicaoNula() {
        // Arrange & Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, false, null))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-filtro-invalido] Deve lançar BadRequestException quando liderEscala for nula e instituicao for false")
    void deveLancarExcecaoQuandoLiderNulaEInstituicaoFalse() {
        // Arrange & Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, null, false))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-filtro-invalido] Deve lançar BadRequestException quando liderEscala e instituicao forem ambos false")
    void deveLancarExcecaoQuandoAmbosForemFalse() {
        // Arrange & Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, false, false))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(escalaRepository);
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando liderEscala for true e instituicao for nula")
    void deveListarQuandoLiderTrueEInstituicaoNula() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, null);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
        verify(escalaRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando liderEscala for true e instituicao for false")
    void deveListarQuandoLiderTrueEInstituicaoFalse() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, false);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando liderEscala for nula e instituicao for true")
    void deveListarQuandoLiderNulaEInstituicaoTrue() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, null, true);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando liderEscala for false e instituicao for true")
    void deveListarQuandoLiderFalseEInstituicaoTrue() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, false, true);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando liderEscala e instituicao forem ambos true")
    void deveListarQuandoAmbosForemTrue() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala(), new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, true);

        // Assert
        assertThat(resultado).isNotNull().hasSize(2);
    }

    @Test
    @DisplayName("[CE-mes-valido] Deve listar quando mesReferencia for nulo (sem filtro de mês)")
    void deveListarQuandoMesReferenciaForNulo() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, null);

        // Assert
        assertThat(resultado).isNotNull().isEmpty();
        verify(escalaRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("[CE-mes-valido] Deve listar quando mesReferencia estiver no formato ISO válido")
    void deveListarQuandoMesReferenciaForValido() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, "2026-06-01", null, null, true, null);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-mes-invalido] Deve lançar exceção quando mesReferencia estiver em formato inválido")
    void deveLancarExcecaoQuandoMesReferenciaForInvalido() {

        // Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                USUARIO_ID_VALIDO, "formato-invalido", null, null, true, null))
                .isInstanceOf(Exception.class);

        verify(escalaRepository, never()).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar quando especialidade for nula (sem filtro de especialidade)")
    void deveListarQuandoEspecialidadeForNula() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, null);

        // Assert
        assertThat(resultado).isNotNull();
        verify(escalaRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar filtrando por especialidade válida")
    void deveListarQuandoEspecialidadeForValida() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, Especialidade.values()[0], true, null);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-filtro-valido] Deve listar filtrando por status")
    void deveListarQuandoStatusForValido() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(new Escala()));
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, StatusEscala.RASCUNHO, null, true, null);

        // Assert
        assertThat(resultado).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("[CE-resultado] Deve retornar lista vazia quando repositório não encontrar escalas")
    void deveRetornarListaVaziaQuandoNaoHouverEscalas() {
        // Arrange
        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, null);

        // Assert
        assertThat(resultado).isNotNull().isEmpty();
        verify(escalaMapper, never()).toSimpleResponseDTO(any());
    }

    @Test
    @DisplayName("[CE-resultado] Deve retornar lista com todos os itens mapeados quando repositório retornar múltiplas escalas")
    void deveRetornarTodosOsItensMapeados() {
        // Arrange
        int quantidadeEscalas = 3;
        List<Escala> escalas = List.of(new Escala(), new Escala(), new Escala());

        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(escalas);
        when(escalaMapper.toSimpleResponseDTO(any(Escala.class)))
                .thenReturn(responseItem);

        // Act
        List<EscalaSimpleResponseDTO> resultado = escalaService.listar(
                USUARIO_ID_VALIDO, null, null, null, true, null);

        // Assert
        assertThat(resultado).hasSize(quantidadeEscalas);
        verify(escalaMapper, times(quantidadeEscalas)).toSimpleResponseDTO(any(Escala.class));
    }

    @Test
    @DisplayName("[LACUNA] [ESPERADO FALHAR] Deve lançar exceção quando usuarioId não existir no sistema")
    void deveLancarExcecaoQuandoUsuarioIdNaoExistir() {
        // Arrange
        Long usuarioInexistente = 321L;

        when(escalaRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> escalaService.listar(
                usuarioInexistente, null, null, null, true, null))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("usuario");
    }
}