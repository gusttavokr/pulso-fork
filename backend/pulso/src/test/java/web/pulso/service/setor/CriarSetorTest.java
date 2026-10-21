package web.pulso.service.setor;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;


import java.util.Optional;

import org.junit.jupiter.api.Test;

import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;

import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;


@DisplayName("Testes do método CRIAR da classe SetorService")
public class CriarSetorTest extends SetorServiceBaseTest{
    
    @Test
    @DisplayName("Criar um Setor Válido.")
    void deveCriarSetorValido() {

        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);
        Setor setor = SetorFactoryRequest.criarSetor(null, "UTI", Especialidade.ENDOCRINOLOGIA);
        Setor salvo = SetorFactoryRequest.criarSetor(1L, "UTI", Especialidade.ENDOCRINOLOGIA);
        Instituicao instituicao = SetorFactoryRequest.criarInstituicao(1L);
        SetorResponseDTO response = SetorFactoryResponse.criarSetorResponseDTO(1L, "UTI", Especialidade.ENDOCRINOLOGIA);

        when(instituicaoRepository.findByUsuarioId(1L)).thenReturn(Optional.of(instituicao));
        when(setorMapper.toEntity(dto)).thenReturn(setor);
        when(setorRepository.save(setor)).thenReturn(salvo);
        when(setorMapper.toResponseDTO(salvo)).thenReturn(response);

        SetorResponseDTO resultado = setorService.criar(dto, 1L);

        assertThat(resultado).isEqualTo(response);
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("UTI");
        assertThat(resultado.getEspecialidade()).isEqualTo(Especialidade.ENDOCRINOLOGIA);

        verify(instituicaoRepository).findByUsuarioId(1L);
        verify(setorMapper).toEntity(dto);
        verify(setorRepository).save(setor);
        verify(setorMapper).toResponseDTO(salvo);
    }

    @Test
    @DisplayName("Não se deve criar Setor se a instituição (buscado por usuário) não for encontrada.")
    void instituicaoNaoEncontrada(){
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);

        when(instituicaoRepository.findByUsuarioId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> setorService.criar(dto, 1L))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Instituição não encontrada com ID: " + 1L);

        verify(instituicaoRepository, org.mockito.Mockito.never()).findById(1L);
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }
    
    @Test
    @DisplayName("Não se deve criar Setor com nome duplicado")
    // TODO : Implementar na Service
    void setorDuplicado(){
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);
        Instituicao instituicao = SetorFactoryRequest.criarInstituicao(1L);
        
        when(instituicaoRepository.findById(1L)).thenReturn(Optional.of(instituicao));
        when(setorRepository.existsByNomeAndInstituicaoId("UTI", 1L)).thenReturn(true);
        
        assertThatThrownBy(() -> setorService.criar(dto, 1L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Esse Setor já existe na Instituição.");
        
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }
    
    @Test
    @DisplayName("Não se deve criar Setor com id de usuário nulo")
    void idUsuarioNulo(){
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);

        assertThatThrownBy(() -> setorService.criar(dto, null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("O campo idUsuario é obrigatório para criar um setor.");

        verify(instituicaoRepository, org.mockito.Mockito.never()).findById(null);
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }
    
    @Test
    @DisplayName("Não se deve criar Setor se Request for nulo")
    void setorRequestNulo(){
        // TODO : Implementar na Service
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTONulo();

        assertThatThrownBy(() -> setorService.criar(dto, 1L))
        .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Os campos Nome e Especialidade são Obrigatórios.");

        verify(instituicaoRepository, org.mockito.Mockito.never()).findById(1L);
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Não se deve criar Setor se Mapper for nulo")
    void setorMapperNulo(){
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ORTOPEDIA);
        Instituicao instituicao = SetorFactoryRequest.criarInstituicao(1L);

        when(instituicaoRepository.findByUsuarioId(1L)).thenReturn(Optional.of(instituicao));
        when(setorMapper.toEntity(dto)).thenReturn(null);

        assertThatThrownBy(() -> setorService.criar(dto, 1L))
            .isInstanceOf(NullPointerException.class); // Espera que quebre com NPE

        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }

}
