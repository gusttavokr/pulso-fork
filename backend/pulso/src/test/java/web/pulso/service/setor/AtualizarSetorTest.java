package web.pulso.service.setor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.exception.business.NotFoundException;

import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

@DisplayName("Testes para o método ATUALIZAR da classe SetorService")
public class AtualizarSetorTest extends SetorServiceBaseTest {
    
    @Test
    @DisplayName("Atualizar um Setor Válido")
    void atualizarSetor(){
        Long idSetor = 1L;

        SetorRequestDTO request = SetorFactoryRequest.criarSetorRequestDTO("UTI Pediatria", Especialidade.PEDIATRIA);
        Setor setorExistente = SetorFactoryRequest.criarSetor(idSetor, "UTI", Especialidade.PEDIATRIA);

        Setor response = SetorFactoryRequest.criarSetor(idSetor, "UTI Pediatria", Especialidade.PEDIATRIA);
        SetorResponseDTO responseDTO = SetorFactoryResponse.criarSetorResponseDTO(idSetor, "UTI Pediatria", Especialidade.PEDIATRIA);

        when(setorRepository.findById(idSetor)).thenReturn(Optional.of(setorExistente));

        when(setorRepository.save(setorExistente)).thenReturn(response);
        when(setorMapper.toResponseDTO(response)).thenReturn(responseDTO);

        SetorResponseDTO resultado = setorService.atualizar(idSetor, request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("UTI Pediatria");
        assertThat(resultado.getEspecialidade()).isEqualTo(Especialidade.PEDIATRIA);

        verify(setorRepository).findById(idSetor);
        verify(setorRepository).save(setorExistente);
    }

    @Test
    @DisplayName("Atualizar apenas o nome de um Setor")
    void atualizarNomeSetor(){
        
        Long idSetor = 1L;

        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("Novo Nome", null);
        Setor setorExistente = SetorFactoryRequest.criarSetor(idSetor, "UTI", Especialidade.ENDOCRINOLOGIA);

        when(setorRepository.findById(idSetor)).thenReturn(Optional.of(setorExistente));
        when(setorRepository.save(setorExistente)).thenReturn(setorExistente);

        setorService.atualizar(idSetor, dto);
        assertThat(setorExistente.getNome()).isEqualTo("Novo Nome");
        assertThat(setorExistente.getEspecialidade()).isEqualTo(Especialidade.ENDOCRINOLOGIA);
    }

    @Test
    @DisplayName("Atualizar apenas a especialidade de um Setor")
    void atualizarEspecialidadeSetor(){
        
        Long idSetor = 1L;

        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO(null, Especialidade.PEDIATRIA);
        Setor setorExistente = SetorFactoryRequest.criarSetor(idSetor, "UTI", Especialidade.ENDOCRINOLOGIA);

        when(setorRepository.findById(idSetor)).thenReturn(Optional.of(setorExistente));
        when(setorRepository.save(setorExistente)).thenReturn(setorExistente);

        setorService.atualizar(idSetor, dto);
        assertThat(setorExistente.getNome()).isEqualTo("UTI");
        assertThat(setorExistente.getEspecialidade()).isEqualTo(Especialidade.PEDIATRIA);
    }

    @Test
    @DisplayName("Não deve atualizar um Setor inexistente.")
    void atualizarSetorInexistente(){
        Long idSetor = 1L;

        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI Pediatria", Especialidade.PEDIATRIA);

        when(setorRepository.findById(idSetor)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> setorService.atualizar(idSetor, dto))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Setor não encontrado com ID: " + idSetor);

        verify(setorRepository).findById(1L);
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Não deve atualizar se os dados do Setor forem inválidos (falha no validar)")
    void naoAtualizarSeDadosInvalidos() {
        Long idSetor = 1L;

        SetorRequestDTO dtoComDadoInvalido = SetorFactoryRequest.criarSetorRequestDTO("ui", Especialidade.PEDIATRIA);
        
        Setor setorExistente = SetorFactoryRequest.criarSetor(idSetor, "UTI Valida", Especialidade.PEDIATRIA);

        when(setorRepository.findById(idSetor)).thenReturn(Optional.of(setorExistente));

        assertThatThrownBy(() -> setorService.atualizar(idSetor, dtoComDadoInvalido))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("O nome do setor deve ter no mínimo 3 caracteres"); 

        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }
}
