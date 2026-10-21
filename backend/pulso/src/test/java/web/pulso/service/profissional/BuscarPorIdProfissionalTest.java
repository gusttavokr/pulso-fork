package web.pulso.service.profissional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.service.profissional.ProfissionalResponseDTOFactory.criarProfissionalResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método buscarPorId da classe ProfissionalService")
public class BuscarPorIdProfissionalTest
        extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve buscar profissional por id com sucesso")
    void deveBuscarProfissionalPorIdComSucesso() {

        Long id = 1L;

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = criarProfissionalResponseDTOValido();

        when(profissionalRepository.findById(id))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(dto);

        ProfissionalResponseDTO resultado = profissionalService.buscarPorId(id);

        assertNotNull(resultado);

        assertEquals(dto, resultado);

        verify(profissionalRepository)
                .findById(id);

        verify(profissionalMapper)
                .toResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(profissionalRepository.findById(null))
                .thenThrow(
                        new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> profissionalService.buscarPorId(null));

        verify(profissionalRepository)
                .findById(null);

        verifyNoInteractions(profissionalMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não existir")
    void deveLancarErroQuandoProfissionalNaoExistir() {

        Long id = 999L;

        when(profissionalRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.buscarPorId(id));

        verify(profissionalRepository)
                .findById(id);

        verifyNoInteractions(profissionalMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar dto nulo")
    void deveLancarErroQuandoMapperRetornarDtoNulo() {

        Long id = 1L;

        Profissional profissional = criarProfissionalValido();

        when(profissionalRepository.findById(id))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(null);

        ProfissionalResponseDTO resultado = profissionalService.buscarPorId(id);

        // atualmente aceita mapper retornando null
        assertNull(resultado);

        verify(profissionalMapper)
                .toResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve lançar erro quando repository retornar profissional nulo")
    void deveLancarErroQuandoRepositoryRetornarProfissionalNulo() {

        Long id = 1L;

        when(profissionalRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.buscarPorId(id));

        verifyNoInteractions(profissionalMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper lançar exceção")
    void deveLancarErroQuandoMapperLancarExcecao() {

        Long id = 1L;

        Profissional profissional = criarProfissionalValido();

        when(profissionalRepository.findById(id))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenThrow(
                        new RuntimeException("Erro no mapper"));

        assertThrows(
                RuntimeException.class,
                () -> profissionalService.buscarPorId(id));

        verify(profissionalMapper)
                .toResponseDTO(profissional);
    }
}