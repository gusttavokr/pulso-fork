package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.InstituicaoRequestDTOFactory.criarInstituicaoRequestDTOComTodosCamposVazios;
import static web.pulso.service.instituicao.InstituicaoRequestDTOFactory.criarInstituicaoRequestDTOValido;
import static web.pulso.service.instituicao.InstituicaoResponseDTOFactory.criarInstituicaoResponseDTOValido;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método atualizar da classe InstituicaoService")
class AtualizarInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve atualizar instituição com sucesso")
    void deveAtualizarInstituicaoComSucesso() {

        Long instituicaoId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();
        dto.setNome("Hospital Atualizado");
        dto.setEmails(List.of("novo@hospital.com"));
        dto.setTelefone("84888888888");
        dto.setFotoPerfil("nova-foto.png");
        dto.setEndereco("Rua Nova, 456");
        dto.setCnpj("98765432000188");
        dto.setExigeVinculo(false);

        InstituicaoResponseDTO responseDTO = criarInstituicaoResponseDTOValido();

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoRepository.save(instituicao))
                .thenReturn(instituicao);

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(responseDTO);

        InstituicaoResponseDTO resultado = instituicaoService.atualizar(instituicaoId, dto);

        assertNotNull(resultado);

        assertEquals(dto.getNome(), instituicao.getNome());

        assertEquals(dto.getEmails(), instituicao.getEmails());

        assertEquals(dto.getTelefone(), instituicao.getTelefone());

        assertEquals(dto.getFotoPerfil(), instituicao.getFotoPerfil());

        assertEquals(dto.getEndereco(), instituicao.getEndereco());

        assertEquals(dto.getCnpj(), instituicao.getCnpj());

        assertEquals(dto.getExigeVinculo(), instituicao.getExigeVinculo());

        verify(instituicaoRepository).save(instituicao);

        verify(instituicaoMapper).toResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve lançar erro quando instituição não existir")
    void deveLancarErroQuandoInstituicaoNaoExistir() {

        Long instituicaoId = 999L;

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.atualizar(instituicaoId, dto));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        Long instituicaoId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        assertThrows(
                NullPointerException.class,
                () -> instituicaoService.atualizar(instituicaoId, null));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar campos vazios ou nulos")
    void naoDeveAtualizarCamposVaziosOuNulos() {

        Long instituicaoId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        String nomeOriginal = instituicao.getNome();
        List<String> emailsOriginais = new ArrayList<>(instituicao.getEmails());
        String telefoneOriginal = instituicao.getTelefone();
        String fotoOriginal = instituicao.getFotoPerfil();
        String enderecoOriginal = instituicao.getEndereco();
        String cnpjOriginal = instituicao.getCnpj();
        Boolean exigeVinculoOriginal = instituicao.getExigeVinculo();

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOComTodosCamposVazios();

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoRepository.save(instituicao))
                .thenReturn(instituicao);

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(criarInstituicaoResponseDTOValido());

        instituicaoService.atualizar(instituicaoId, dto);

        assertEquals(nomeOriginal, instituicao.getNome());
        assertEquals(emailsOriginais, instituicao.getEmails());
        assertEquals(telefoneOriginal, instituicao.getTelefone());
        assertEquals(fotoOriginal, instituicao.getFotoPerfil());
        assertEquals(enderecoOriginal, instituicao.getEndereco());
        assertEquals(cnpjOriginal, instituicao.getCnpj());
        assertEquals(exigeVinculoOriginal, instituicao.getExigeVinculo());

        verify(instituicaoRepository).save(instituicao);
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        Long instituicaoId = 1L;

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoRequestDTO dto = new InstituicaoRequestDTO();

        dto.setEmails(List.of("novo@hospital.com"));

        when(instituicaoRepository.findById(instituicaoId))
                .thenReturn(Optional.of(instituicao));

        when(instituicaoRepository.save(instituicao))
                .thenReturn(instituicao);

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(criarInstituicaoResponseDTOValido());

        instituicaoService.atualizar(instituicaoId, dto);

        assertEquals(dto.getEmails(), instituicao.getEmails());

        org.assertj.core.api.Assertions.assertThat(instituicao.getEmails())
                .isNotSameAs(dto.getEmails());
    }
}
