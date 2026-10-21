package web.pulso.service.setor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes para o método Deletar da classe SetorService")
public class DeleteSetorTest extends SetorServiceBaseTest{
    @Test
    @DisplayName("Delete de Setor Válido")
    void deletarSetorValido(){
        
        Long id = 1L;

        when(setorRepository.existsById(id)).thenReturn(true);
        
        setorService.deletar(id);
        
        verify(setorRepository).existsById(id);
        verify(setorRepository).deleteById(id);
    }
    
    @Test
    @DisplayName("Delete de Setor não existente")
    void setorInexistente(){
        
        Long id = 1L;
        
        when(setorRepository.existsById(id)).thenReturn(false);
        
        assertThatThrownBy(() -> setorService.deletar(id))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Setor não encontrado com ID: " + id);
        
        verify(setorRepository).existsById(id);
        verify(setorRepository, never()).deleteById(id);
    }
}
