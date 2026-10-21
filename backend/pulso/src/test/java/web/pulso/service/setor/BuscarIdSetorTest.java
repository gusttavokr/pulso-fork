package web.pulso.service.setor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;
import web.pulso.exception.business.NotFoundException;


@DisplayName("Testes para o método Buscar por ID da classe SetorService")
public class BuscarIdSetorTest extends SetorServiceBaseTest{

    @Test
    @DisplayName("Busca por Setor Válido")
    void setorValido(){
        
        Long id = 1L;

        Setor setor = SetorFactoryRequest.criarSetor(1L, "UTI", Especialidade.ENDOCRINOLOGIA);

        SetorResponseDTO dto = SetorFactoryResponse.criarSetorResponseDTO(1L, "UTI", Especialidade.ENDOCRINOLOGIA);
        
        when(setorRepository.findById(id)).thenReturn(Optional.of(setor));
        when(setorMapper.toResponseDTO(setor)).thenReturn(dto);

        SetorResponseDTO resultado = setorService.buscarPorId(id);

        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getNome()).isEqualTo(setor.getNome());
        assertThat(resultado.getEspecialidade()).isEqualTo(setor.getEspecialidade());
    }

    @Test
    @DisplayName("Busca por Setor não existente")
    void setorInexistente(){

        Long id = 1L;

        when(setorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> setorService.buscarPorId(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Setor não encontrado com ID: " + id);

        verify(setorRepository).findById(id);
    }
}
