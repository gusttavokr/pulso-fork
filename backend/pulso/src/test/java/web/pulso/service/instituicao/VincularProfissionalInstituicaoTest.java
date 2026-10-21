package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.ProfissionalFactory.criarProfissionalValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.VinculoRequestDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método vincularProfissional da classe InstituicaoService")
class VincularProfissionalInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve vincular profissional como PJ com sucesso")
    void deveVincularProfissionalComoPjComSucesso() {

        Long instituicaoId = 1L;

        Long profissionalId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissional = criarProfissionalValido();

        VinculoRequestDTO dto = new VinculoRequestDTO(profissionalId, true);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        instituicaoService.vincularProfissional(dto, instituicaoId);

        assertTrue(instituicao.getProfisionais_pj().contains(profissional));

        assertTrue(profissional.getInstituicoesPj().contains(instituicao));
    }

    @Test
    @DisplayName("Deve vincular profissional como CLT com sucesso")
    void deveVincularProfissionalComoCltComSucesso() {

        Long instituicaoId = 1L;

        Long profissionalId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissional = criarProfissionalValido();

        VinculoRequestDTO dto = new VinculoRequestDTO(profissionalId, false);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        instituicaoService.vincularProfissional(dto, instituicaoId);

        assertTrue(instituicao.getProfissionais_clt().contains(profissional));

        assertTrue(profissional.getInstituicoesClt().contains(instituicao));
    }

    @Test
    @DisplayName("Deve tratar vínculo nulo como CLT")
    void deveTratarVinculoNuloComoClt() {

        Long instituicaoId = 1L;

        Long profissionalId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissional = criarProfissionalValido();

        VinculoRequestDTO dto = new VinculoRequestDTO(profissionalId, null);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        instituicaoService.vincularProfissional(dto, instituicaoId);

        assertTrue(instituicao.getProfissionais_clt().contains(profissional));
    }

    @Test
    @DisplayName("Não deve duplicar vínculo PJ existente")
    void naoDeveDuplicarVinculoPjExistente() {

        Long instituicaoId = 1L;

        Long profissionalId = 2L;

        Instituicao instituicao = criarInstituicaoValida();

        Profissional profissional = criarProfissionalValido();

        instituicao.getProfisionais_pj().add(profissional);

        VinculoRequestDTO dto = new VinculoRequestDTO(profissionalId, true);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        instituicaoService.vincularProfissional(dto, instituicaoId);

        org.assertj.core.api.Assertions.assertThat(instituicao.getProfisionais_pj())
                .containsExactly(profissional);
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long instituicaoId = 999L;

        VinculoRequestDTO dto = new VinculoRequestDTO(2L, true);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.vincularProfissional(dto, instituicaoId));
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não existir")
    void deveLancarErroQuandoProfissionalNaoExistir() {

        Long instituicaoId = 1L;

        Long profissionalId = 999L;

        VinculoRequestDTO dto = new VinculoRequestDTO(profissionalId, true);

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(criarInstituicaoValida()));

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.vincularProfissional(dto, instituicaoId));

        verify(profissionalRepository).findById(profissionalId);
    }
}
