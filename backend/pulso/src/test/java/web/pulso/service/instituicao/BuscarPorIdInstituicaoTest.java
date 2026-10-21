package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.InstituicaoResponseDTOFactory.criarInstituicaoResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método buscarPorId da classe InstituicaoService")
class BuscarPorIdInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve buscar instituição por id com sucesso")
    void deveBuscarInstituicaoPorIdComSucesso() {

        Long id = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoResponseDTO dto = criarInstituicaoResponseDTOValido();

        when(instituicaoRepository.findById(id))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(dto);

        InstituicaoResponseDTO resultado = instituicaoService.buscarPorId(id);

        assertNotNull(resultado);

        assertEquals(dto, resultado);

        verify(instituicaoRepository).findById(id);

        verify(instituicaoMapper).toResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(instituicaoRepository.findById(null))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> instituicaoService.buscarPorId(null));

        verifyNoInteractions(instituicaoMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long id = 999L;

        when(instituicaoRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.buscarPorId(id));

        verifyNoInteractions(instituicaoMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar dto nulo")
    void deveLancarErroQuandoMapperRetornarDtoNulo() {

        Long id = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        when(instituicaoRepository.findById(id))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.buscarPorId(id));
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper lançar exceção")
    void deveLancarErroQuandoMapperLancarExcecao() {

        Long id = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        when(instituicaoRepository.findById(id))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenThrow(new RuntimeException("Erro no mapper"));

        assertThrows(
                RuntimeException.class,
                () -> instituicaoService.buscarPorId(id));
    }
}
