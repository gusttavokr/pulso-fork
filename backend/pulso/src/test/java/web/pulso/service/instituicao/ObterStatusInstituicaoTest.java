package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.ProfissionalFactory.criarProfissionalValido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.instituicao.status.InstituicaoProfissionaisStatusResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método obterStatusInstituicao da classe InstituicaoService")
class ObterStatusInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve obter status da instituição com sucesso")
    void deveObterStatusInstituicaoComSucesso() {

        Long usuarioId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissionalClt = criarProfissionalValido();

        Profissional profissionalPj = criarProfissionalValido();
        profissionalPj.setId(2L);

        instituicao.setProfissionais_clt(List.of(profissionalClt));
        instituicao.setProfisionais_pj(List.of(profissionalPj));

        when(instituicaoRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.countMedicosAlocadosNoMes(
                eq(instituicao.getId()),
                any(LocalDate.class)))
                .thenReturn(1L);

        InstituicaoProfissionaisStatusResponseDTO resultado =
                instituicaoService.obterStatusInstituicao(usuarioId);

        assertNotNull(resultado);

        assertEquals(2L, resultado.getTotal());

        assertEquals(1L, resultado.getAlocados());

        assertEquals(1L, resultado.getNaoAlocados());

        verify(instituicaoRepository).findByUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve contar profissionais duplicados apenas uma vez")
    void deveContarProfissionaisDuplicadosApenasUmaVez() {

        Long usuarioId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissional = criarProfissionalValido();

        instituicao.setProfissionais_clt(List.of(profissional));
        instituicao.setProfisionais_pj(List.of(profissional));

        when(instituicaoRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.countMedicosAlocadosNoMes(
                eq(instituicao.getId()),
                any(LocalDate.class)))
                .thenReturn(0L);

        InstituicaoProfissionaisStatusResponseDTO resultado =
                instituicaoService.obterStatusInstituicao(usuarioId);

        assertEquals(1L, resultado.getTotal());

        assertEquals(1L, resultado.getNaoAlocados());
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long usuarioId = 999L;

        when(instituicaoRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.obterStatusInstituicao(usuarioId));
    }
}
