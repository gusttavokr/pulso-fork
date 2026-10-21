package web.pulso.service.setor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes para o método Listar da classe SetorService")
public class ListarSetorTest extends SetorServiceBaseTest{

    @Test
    @DisplayName("Listagem válida dos setores")
    void listarSetor(){

        String nomeRecebido = "UTI";
        Especialidade especialidadeRecebida = Especialidade.PEDIATRIA;
        Long idUsuarioRecebido = 1L;

        Instituicao instituicao = SetorFactoryRequest.criarInstituicao(100L); 
        
        Setor setorEncontrado = SetorFactoryRequest.criarSetor(1L, "UTI Pediátrica", Especialidade.PEDIATRIA);
        List<Setor> listaDoBanco = List.of(setorEncontrado);
        
        SetorResponseDTO setorDTO = SetorFactoryResponse.criarSetorResponseDTO(1L, "UTI Pediátrica", Especialidade.PEDIATRIA);
        
        when(instituicaoRepository.findByUsuarioId(idUsuarioRecebido)).thenReturn(Optional.of(instituicao));
        when(setorRepository.findAll(any(Specification.class))).thenReturn(listaDoBanco);
        when(setorMapper.toResponseDTO(setorEncontrado)).thenReturn(setorDTO);

        List<SetorResponseDTO> resultado = setorService.listar(nomeRecebido, especialidadeRecebida, idUsuarioRecebido);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("UTI Pediátrica");
        
        verify(instituicaoRepository).findByUsuarioId(idUsuarioRecebido);
        verify(setorRepository).findAll(any(Specification.class));
        verify(setorMapper).toResponseDTO(setorEncontrado);
    }

    @Test
    @DisplayName("Não se deve listar Setor se a instituição (buscado por usuário) não for encontrada.")
    void instituicaoNaoEncontrada(){
        Long idUsuarioRecebido = 1L;
        
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);
        
        when(instituicaoRepository.findByUsuarioId(1L)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> setorService.listar(dto.getNome(), dto.getEspecialidade(), idUsuarioRecebido))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Instituição não encontrada para o usuário ID " + 1L);
        
        verify(setorRepository, never()).findAll(any(Specification.class));
        verify(instituicaoRepository).findByUsuarioId(idUsuarioRecebido);
    }
    
    @Test
    @DisplayName("Não se deve listar Setor se idUsuario é nulo")
    // TODO Implementar na Service
    void idUsuarioNulo(){
        SetorRequestDTO dto = SetorFactoryRequest.criarSetorRequestDTO("UTI", Especialidade.ENDOCRINOLOGIA);
        assertThatThrownBy(() -> setorService.listar(dto.getNome(), dto.getEspecialidade(), null))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("O campo idUsuario é obrigatório para criar um setor.");

        verify(instituicaoRepository, org.mockito.Mockito.never()).findById(null);
        verify(setorRepository, org.mockito.Mockito.never()).save(any());
    }
}
