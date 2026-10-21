package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.InstituicaoResponseDTOFactory.criarInstituicaoResponseDTOValido;
import static web.pulso.service.instituicao.ProfissionalFactory.criarLiderComEspecialidadeCardiologia;
import static web.pulso.service.instituicao.SetorFactory.criarSetorCardiologia;
import static web.pulso.service.instituicao.SetorFactory.criarSetorNeurologia;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método listar da classe InstituicaoService")
class ListarInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve listar instituições com sucesso sem filtro de líder")
    void deveListarInstituicoesComSucessoSemFiltroDeLider() {

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoResponseDTO dto = criarInstituicaoResponseDTOValido();

        when(instituicaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(instituicao));

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(dto);

        List<InstituicaoResponseDTO> resultado = instituicaoService.listar(
                null,
                "Hospital",
                "contato@hospital.com",
                "84999999999",
                "Rua",
                "12345678000199",
                true);

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        assertEquals(dto, resultado.get(0));

        verifyNoInteractions(profissionalRepository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver instituições")
    void deveRetornarListaVaziaQuandoNaoHouverInstituicoes() {

        when(instituicaoRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        List<InstituicaoResponseDTO> resultado = instituicaoService.listar(
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional líder não existir")
    void deveLancarErroQuandoProfissionalLiderNaoExistir() {

        Long usuarioId = 1L;

        when(profissionalRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.listar(usuarioId, null, null, null, null, null, null));

        verifyNoInteractions(instituicaoRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não for líder de escala")
    void deveLancarErroQuandoProfissionalNaoForLiderDeEscala() {

        Long usuarioId = 1L;

        Profissional profissional = criarLiderComEspecialidadeCardiologia();

        profissional.setInstituicoesLiderEscala(Collections.emptyList());

        when(profissionalRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.of(profissional));

        assertThrows(
                ForbiddenException.class,
                () -> instituicaoService.listar(usuarioId, null, null, null, null, null, null));

        verifyNoInteractions(instituicaoRepository);
    }

    @Test
    @DisplayName("Deve filtrar setores pela especialidade do líder")
    void deveFiltrarSetoresPelaEspecialidadeDoLider() {

        Long usuarioId = 1L;

        Profissional lider = criarLiderComEspecialidadeCardiologia();

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setSetores(List.of(
                criarSetorCardiologia(),
                criarSetorNeurologia()));

        when(profissionalRepository.findByUsuarioId(usuarioId))
                .thenReturn(Optional.of(lider));

        when(instituicaoRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(instituicao));

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(criarInstituicaoResponseDTOValido());

        instituicaoService.listar(usuarioId, null, null, null, null, null, null);

        assertEquals(1, instituicao.getSetores().size());

        assertEquals(
                lider.getEspecialidades().iterator().next(),
                instituicao.getSetores().get(0).getEspecialidade());
    }

    @Test
    @DisplayName("Deve lançar erro quando repository retornar nulo")
    void deveLancarErroQuandoRepositoryRetornarNulo() {

        when(instituicaoRepository.findAll(any(Specification.class)))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> instituicaoService.listar(null, null, null, null, null, null, null));
    }
}
