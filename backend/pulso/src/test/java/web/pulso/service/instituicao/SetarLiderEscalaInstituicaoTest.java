package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.ProfissionalFactory.criarProfissionalValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.VinculoLiderEscalaRequestDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método setarLiderEscala da classe InstituicaoService")
class SetarLiderEscalaInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve setar líder de escala com vínculo CLT")
    void deveSetarLiderEscalaComVinculoClt() {

        Long instituicaoId = 1L;

        Long liderId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional lider = criarProfissionalValido();

        instituicao.getProfissionais_clt().add(lider);

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(liderId);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(liderId))
                .thenReturn(Optional.of(lider));

        instituicaoService.setarLiderEscala(dto, instituicaoId);

        assertTrue(instituicao.getProfissionais_lider_escala().contains(lider));

        assertTrue(lider.getInstituicoesLiderEscala().contains(instituicao));
    }

    @Test
    @DisplayName("Deve setar líder de escala com vínculo PJ")
    void deveSetarLiderEscalaComVinculoPj() {

        Long instituicaoId = 1L;

        Long liderId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional lider = criarProfissionalValido();

        instituicao.getProfisionais_pj().add(lider);

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(liderId);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(liderId))
                .thenReturn(Optional.of(lider));

        instituicaoService.setarLiderEscala(dto, instituicaoId);

        assertTrue(instituicao.getProfissionais_lider_escala().contains(lider));
    }

    @Test
    @DisplayName("Não deve duplicar líder já vinculado")
    void naoDeveDuplicarLiderJaVinculado() {

        Long instituicaoId = 1L;

        Long liderId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional lider = criarProfissionalValido();

        instituicao.getProfissionais_clt().add(lider);

        instituicao.getProfissionais_lider_escala().add(lider);

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(liderId);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(liderId))
                .thenReturn(Optional.of(lider));

        instituicaoService.setarLiderEscala(dto, instituicaoId);

        org.assertj.core.api.Assertions.assertThat(instituicao.getProfissionais_lider_escala())
                .containsExactly(lider);
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long instituicaoId = 999L;

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(2L);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.setarLiderEscala(dto, instituicaoId));
    }

    @Test
    @DisplayName("Deve lançar erro quando líder não existir")
    void deveLancarErroQuandoLiderNaoExistir() {

        Long instituicaoId = 1L;

        Long liderId = 999L;

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(liderId);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(criarInstituicaoValida()));

        when(profissionalRepository.findById(liderId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.setarLiderEscala(dto, instituicaoId));
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não estiver vinculado")
    void deveLancarErroQuandoProfissionalNaoEstiverVinculado() {

        Long instituicaoId = 1L;

        Long liderId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional lider = criarProfissionalValido();

        VinculoLiderEscalaRequestDTO dto = new VinculoLiderEscalaRequestDTO(liderId);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(liderId))
                .thenReturn(Optional.of(lider));

        assertThrows(
                ConflictException.class,
                () -> instituicaoService.setarLiderEscala(dto, instituicaoId));
    }
}
