package web.pulso.service.profissional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.jpa.domain.Specification;

import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;
import web.pulso.entity.Escala;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.service.profissional.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.profissional.SetorFactory.criarSetorValido;
import static web.pulso.service.profissional.EscalaFactory.criarEscalaValida;
import static web.pulso.service.profissional.PlantaoFactory.criarPlantaoValido;
import static web.pulso.service.profissional.ProfissionalEstatisticaResponseDTOFactory.criarProfissionalEstatisticaResponseDTOValido;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes do método listar da classe ProfissionalService")
public class ListarProfissionalTest extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve listar profissionais com sucesso")
    void deveListarProfissionaisComSucesso() {

        Long idPlantao = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Setor setor = criarSetorValido();
        setor.setInstituicao(instituicao);

        Escala escala = criarEscalaValida();
        escala.setSetor(setor);

        Plantao plantao = criarPlantaoValido();
        plantao.setEscala(escala);

        Profissional profissional = criarProfissionalValido();

        ProfissionalEstatisticaResponseDTO dto = criarProfissionalEstatisticaResponseDTOValido();

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        when(profissionalRepository.findAll(
                ArgumentMatchers.<Specification<Profissional>>any()))
                .thenReturn(List.of(profissional));

        when(profissionalMapper.toEstatisticaResponseDTO(profissional))
                .thenReturn(dto);

        List<ProfissionalEstatisticaResponseDTO> resultado = profissionalService.listar(idPlantao);

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        verify(plantaoRepository).findById(idPlantao);

        verify(profissionalRepository).findAll(ArgumentMatchers.<Specification<Profissional>>any());

        verify(profissionalMapper)
                .toEstatisticaResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve lançar erro quando idPlantao for nulo")
    void deveLancarErroQuandoIdPlantaoForNulo() {

        assertThrows(
                BadRequestException.class,
                () -> profissionalService.listar(null));

        verifyNoInteractions(plantaoRepository);

        verifyNoInteractions(profissionalRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão não existir")
    void deveLancarErroQuandoPlantaoNaoExistir() {

        Long idPlantao = 1L;

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.listar(idPlantao));

        verify(plantaoRepository).findById(idPlantao);

        verifyNoInteractions(profissionalRepository);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver profissionais")
    void deveRetornarListaVaziaQuandoNaoHouverProfissionais() {

        Long idPlantao = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Setor setor = criarSetorValido();
        setor.setInstituicao(instituicao);

        Escala escala = criarEscalaValida();
        escala.setSetor(setor);

        Plantao plantao = criarPlantaoValido();
        plantao.setEscala(escala);

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        when(profissionalRepository.findAll(
                ArgumentMatchers.<Specification<Profissional>>any()))
                .thenReturn(Collections.emptyList());

        List<ProfissionalEstatisticaResponseDTO> resultado = profissionalService.listar(idPlantao);

        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        verify(profissionalRepository).findAll(
                ArgumentMatchers.<Specification<Profissional>>any());
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão possuir escala nula")
    void deveLancarErroQuandoPlantaoPossuirEscalaNula() {

        Long idPlantao = 1L;

        Plantao plantao = criarPlantaoValido();

        plantao.setEscala(null);

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.listar(idPlantao));

        verify(profissionalRepository)
                .findAll(
                        ArgumentMatchers.<Specification<Profissional>>any());
    }

    @Test
    @DisplayName("Deve lançar erro quando setor for nulo")
    void deveLancarErroQuandoSetorForNulo() {

        Long idPlantao = 1L;

        Escala escala = criarEscalaValida();

        escala.setSetor(null);

        Plantao plantao = criarPlantaoValido();

        plantao.setEscala(escala);

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.listar(idPlantao));

        verify(profissionalRepository)
                .findAll(
                        ArgumentMatchers.<Specification<Profissional>>any());
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição for nula")
    void deveLancarErroQuandoInstituicaoForNula() {

        Long idPlantao = 1L;

        Setor setor = criarSetorValido();

        setor.setInstituicao(null);

        Escala escala = criarEscalaValida();

        escala.setSetor(setor);

        Plantao plantao = criarPlantaoValido();

        plantao.setEscala(escala);

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.listar(idPlantao));

        verify(profissionalRepository)
                .findAll(
                        ArgumentMatchers.<Specification<Profissional>>any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar dto nulo")
    void deveLancarErroQuandoMapperRetornarDtoNulo() {

        Long idPlantao = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Setor setor = criarSetorValido();

        setor.setInstituicao(instituicao);

        Escala escala = criarEscalaValida();

        escala.setSetor(setor);

        Plantao plantao = criarPlantaoValido();

        plantao.setEscala(escala);

        Profissional profissional = criarProfissionalValido();

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        when(profissionalRepository.findAll(
                ArgumentMatchers.<Specification<Profissional>>any()))
                .thenReturn(List.of(profissional));

        when(profissionalMapper.toEstatisticaResponseDTO(profissional))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.listar(idPlantao));
    }

    @Test
    @DisplayName("Deve lançar erro quando repository retornar nulo")
    void deveLancarErroQuandoRepositoryRetornarNulo() {

        Long idPlantao = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        Setor setor = criarSetorValido();

        setor.setInstituicao(instituicao);

        Escala escala = criarEscalaValida();

        escala.setSetor(setor);

        Plantao plantao = criarPlantaoValido();

        plantao.setEscala(escala);

        when(plantaoRepository.findById(idPlantao))
                .thenReturn(Optional.of(plantao));

        when(profissionalRepository.findAll(
                ArgumentMatchers.<Specification<Profissional>>any()))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.listar(idPlantao));
    }
}