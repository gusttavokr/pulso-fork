package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Troca;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método buscarPorId da classe TrocaService")
public class BuscarPorIdTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve buscar troca por id com sucesso")
    void deveBuscarTrocaPorIdComSucesso() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        TrocaResponseDTO responseDTO = criarTrocaResponseDTOValido();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(responseDTO);

        TrocaResponseDTO resultado = trocaService.buscarPorId(id);

        assertNotNull(resultado);

        verify(trocaRepository)
                .findById(id);

        verify(trocaMapper)
                .toResponseDTO(troca);
    }

    @Test
    @DisplayName("Deve lançar erro quando troca não existir")
    void deveLancarErroQuandoTrocaNaoExistir() {

        Long id = 1L;

        when(trocaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> trocaService.buscarPorId(id));

        verifyNoInteractions(trocaMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(trocaRepository.findById(null))
                .thenThrow(
                        new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> trocaService.buscarPorId(null));
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper lançar exceção")
    void deveLancarErroQuandoMapperLancarExcecao() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenThrow(
                        new RuntimeException("Erro mapper"));

        assertThrows(
                RuntimeException.class,
                () -> trocaService.buscarPorId(id));
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar null")
    void deveLancarErroQuandoMapperRetornarNull() {

        Long id = 1L;

        Troca troca = criarTrocaValida();

        when(trocaRepository.findById(id))
                .thenReturn(Optional.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(null);

        TrocaResponseDTO resultado = trocaService.buscarPorId(id);

        assertNull(resultado);
    }
}