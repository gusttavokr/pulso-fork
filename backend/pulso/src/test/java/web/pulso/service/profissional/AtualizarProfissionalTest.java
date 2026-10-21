package web.pulso.service.profissional;

import org.junit.jupiter.api.Test;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.entity.enums.UF;
import web.pulso.exception.business.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.mappers.profissional.ProfissionalRequestDTOFactory.criarProfissionalRequestDTOValido;
import static web.pulso.service.profissional.ProfissionalResponseDTOFactory.criarProfissionalResponseDTOValido;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes do método atualizar da classe ProfissionalService")
class AtualizarProfissionalTest extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve atualizar profissional com sucesso")
    void deveAtualizarProfissionalComSucesso() {

        Long profissionalId = 1L;

        Profissional profissionalExistente = criarProfissionalValido();

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        ProfissionalResponseDTO responseDTO = criarProfissionalResponseDTOValido();

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissionalExistente));

        when(profissionalMapper.toResponseDTO(profissionalExistente))
                .thenReturn(responseDTO);

        ProfissionalResponseDTO resultado = profissionalService.atualizar(profissionalId, dto);

        assertNotNull(resultado);

        assertEquals(dto.getNome(), profissionalExistente.getNome());

        assertEquals(dto.getTelefone(), profissionalExistente.getTelefone());

        assertEquals(dto.getFotoPerfil(), profissionalExistente.getFotoPerfil());

        assertEquals(dto.getPix(), profissionalExistente.getPix());

        assertEquals(dto.getTipoPix(), profissionalExistente.getTipoPix());

        assertEquals(dto.getCrmNumero(), profissionalExistente.getCrmNumero());

        assertEquals(dto.getAnoFormacao(), profissionalExistente.getAnoFormacao());

        assertEquals(dto.getDataNasc(), profissionalExistente.getDataNasc());

        verify(profissionalRepository).findById(profissionalId);

        verify(profissionalRepository).save(profissionalExistente);

        verify(profissionalMapper).toResponseDTO(profissionalExistente);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional não existir")
    void deveLancarErroQuandoProfissionalNaoExistir() {

        Long profissionalId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.atualizar(profissionalId, dto));

        verify(profissionalRepository).findById(profissionalId);

        verify(profissionalRepository, never()).save(any());
    }

    // TESTE DE CONTRATO
    // Hoje provavelmente gera NullPointerException
    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        Long profissionalId = 1L;

        Profissional profissional = criarProfissionalValido();

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.atualizar(profissionalId, null));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando save retornar nulo")
    void deveLancarErroQuandoSaveRetornarNulo() {

        Long profissionalId = 1L;

        Profissional profissional = criarProfissionalValido();

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        when(profissionalRepository.save(profissional))
                .thenReturn(null);

        assertThrows(
                Exception.class,
                () -> profissionalService.atualizar(profissionalId, dto));
    }

    @Test
    @DisplayName("Não deve atualizar campos string quando forem vazios")
    void naoDeveAtualizarCamposStringQuandoForemVazios() {

        Long profissionalId = 1L;

        Profissional profissional = criarProfissionalValido();

        String nomeOriginal = profissional.getNome();

        String telefoneOriginal = profissional.getTelefone();

        String crmOriginal = profissional.getCrmNumero();

        ProfissionalRequestDTO dto = new ProfissionalRequestDTO();

        dto.setNome("");

        dto.setTelefone(" ");

        dto.setCrmNumero("");

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(criarProfissionalResponseDTOValido());

        profissionalService.atualizar(profissionalId, dto);

        assertEquals(nomeOriginal, profissional.getNome());

        assertEquals(telefoneOriginal, profissional.getTelefone());

        assertEquals(crmOriginal, profissional.getCrmNumero());

        verify(profissionalRepository).save(profissional);
    }

    @Test
    @DisplayName("Não deve atualizar listas quando forem vazias")
    void naoDeveAtualizarListasQuandoForemVazias() {

        Long profissionalId = 1L;

        Profissional profissional = criarProfissionalValido();

        List<String> emailsOriginais = new ArrayList<>(profissional.getEmails());

        Set<UF> crmsOriginais = new HashSet<>(profissional.getCrms());

        ProfissionalRequestDTO dto = new ProfissionalRequestDTO();

        dto.setEmails(List.of());

        dto.setCrms(Set.of());

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(criarProfissionalResponseDTOValido());

        profissionalService.atualizar(profissionalId, dto);

        assertEquals(emailsOriginais, profissional.getEmails());

        assertEquals(crmsOriginais, profissional.getCrms());

        verify(profissionalRepository).save(profissional);
    }

    @Test
    @DisplayName("Deve permitir atualizar crm para valor inválido")
    void devePermitirAtualizarCrmParaValorInvalido() {

        Long profissionalId = 1L;

        Profissional profissional = criarProfissionalValido();

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setCrmNumero("X");

        when(profissionalRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(criarProfissionalResponseDTOValido());

        profissionalService.atualizar(profissionalId, dto);

        assertEquals("X", profissional.getCrmNumero());

        verify(profissionalRepository).save(profissional);
    }
}