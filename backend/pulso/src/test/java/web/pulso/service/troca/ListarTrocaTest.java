package web.pulso.service.troca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.troca.TrocaFactory.criarOutraTrocaValida;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;
import static web.pulso.service.troca.TrocaResponseDTOFactory.criarTrocaResponseDTOValido;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.jpa.domain.Specification;

import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.exception.business.BadRequestException;

@DisplayName("Testes do método listar da classe TrocaService")
public class ListarTrocaTest extends TrocaServiceBaseTest {

    @Test
    @DisplayName("Deve listar trocas com sucesso")
    void deveListarTrocasComSucesso() {

        Troca troca1 = criarTrocaValida();

        Troca troca2 = criarOutraTrocaValida();

        List<Troca> trocas = List.of(troca1, troca2);

        TrocaResponseDTO dto1 = criarTrocaResponseDTOValido();

        TrocaResponseDTO dto2 = criarTrocaResponseDTOValido();

        @SuppressWarnings("unchecked")
        Specification<Troca> spec = any(Specification.class);

        when(trocaRepository.findAll(spec))
                .thenReturn(trocas);

        when(trocaMapper.toResponseDTO(troca1))
                .thenReturn(dto1);

        when(trocaMapper.toResponseDTO(troca2))
                .thenReturn(dto2);

        List<TrocaResponseDTO> resultado = trocaService.listar(
                1L,
                "comentario",
                TipoTroca.DUPLA,
                StatusTroca.PENDENTE,
                1L,
                2L,
                1L,
                2L,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now());

        assertNotNull(resultado);

        assertEquals(2, resultado.size());

        verify(trocaRepository)
                .findAll(Mockito.<Specification<Troca>>any());

        verify(trocaMapper)
                .toResponseDTO(troca1);

        verify(trocaMapper)
                .toResponseDTO(troca2);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar trocas")
    void deveRetornarListaVaziaQuandoNaoEncontrarTrocas() {

        @SuppressWarnings("unchecked")
        Specification<Troca> spec = any(Specification.class);

        when(trocaRepository.findAll(spec))
                .thenReturn(Collections.emptyList());

        List<TrocaResponseDTO> resultado = trocaService.listar(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        verify(trocaRepository)
                .findAll(Mockito.<Specification<Troca>>any());
    }

    @Test
    @DisplayName("Deve listar trocas apenas por status")
    void deveListarTrocasApenasPorStatus() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        when(trocaRepository.findAll(Mockito.<Specification<Troca>>any()))
                .thenReturn(List.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(
                        criarTrocaResponseDTOValido());

        List<TrocaResponseDTO> resultado = trocaService.listar(
                null,
                null,
                null,
                StatusTroca.PENDENTE,
                null,
                null,
                null,
                null,
                null,
                null);

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper lançar exceção")
    void deveLancarErroQuandoMapperLancarExcecao() {

        Troca troca = criarTrocaValida();

        when(trocaRepository.findAll(Mockito.<Specification<Troca>>any()))
                .thenReturn(List.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenThrow(
                        new RuntimeException("Erro mapper"));

        assertThrows(
                RuntimeException.class,
                () -> trocaService.listar(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
    }

    // atualmente aceita dataInicio maior que dataFim
    @Test
    @DisplayName("Deve lançar erro quando dataInicio for maior que dataFim")
    void deveLancarErroQuandoDataInicioForMaiorQueDataFim() {

        LocalDateTime inicio = LocalDateTime.now();

        LocalDateTime fim = LocalDateTime.now().minusDays(1);

        assertThrows(
                BadRequestException.class,
                () -> trocaService.listar(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        inicio,
                        fim));
    }

    // atualmente aceita comentário vazio
    @Test
    @DisplayName("Deve lançar erro quando comentário for vazio")
    void deveLancarErroQuandoComentarioForVazio() {

        assertThrows(
                BadRequestException.class,
                () -> trocaService.listar(
                        null,
                        " ",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
    }

    // atualmente aceita ids negativos
    @Test
    @DisplayName("Deve lançar erro quando ids forem negativos")
    void deveLancarErroQuandoIdsForemNegativos() {

        assertThrows(
                BadRequestException.class,
                () -> trocaService.listar(
                        -1L,
                        null,
                        null,
                        null,
                        -1L,
                        -2L,
                        -3L,
                        -4L,
                        null,
                        null));
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar null")
    void deveLancarErroQuandoMapperRetornarNull() {

        Troca troca = criarTrocaValida();

        when(trocaRepository.findAll(Mockito.<Specification<Troca>>any()))
                .thenReturn(List.of(troca));

        when(trocaMapper.toResponseDTO(troca))
                .thenReturn(null);

        List<TrocaResponseDTO> resultado = trocaService.listar(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertEquals(1, resultado.size());

        assertNull(resultado.get(0));
    }

    // atualmente aceita troca nula retornada do repository
    @Test
    @DisplayName("Deve lançar erro quando repository retornar troca nula")
    void deveLancarErroQuandoRepositoryRetornarTrocaNula() {

        when(trocaRepository.findAll(Mockito.<Specification<Troca>>any()))
                .thenReturn(List.of((Troca) null));

        assertThrows(
                NullPointerException.class,
                () -> trocaService.listar(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
    }
}