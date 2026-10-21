package web.pulso.service.troca;

import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;

public class ProfissionalSimpleResponseDTOFactory {

    public static ProfissionalSimpleResponseDTO criarProfissionalSimpleResponseDTOValido() {

        ProfissionalSimpleResponseDTO dto = new ProfissionalSimpleResponseDTO();

        dto.setId(1L);

        dto.setFoto("foto-profissional.png");

        dto.setNome("João Silva");

        return dto;
    }

    public static ProfissionalSimpleResponseDTO criarOutroProfissionalSimpleResponseDTOValido() {

        ProfissionalSimpleResponseDTO dto = new ProfissionalSimpleResponseDTO();

        dto.setId(2L);

        dto.setFoto("foto-profissional-2.png");

        dto.setNome("Maria Oliveira");

        return dto;
    }

    public static ProfissionalSimpleResponseDTO criarProfissionalSimpleResponseDTOSemNome() {

        ProfissionalSimpleResponseDTO dto = criarProfissionalSimpleResponseDTOValido();

        dto.setNome(null);

        return dto;
    }

    public static ProfissionalSimpleResponseDTO criarProfissionalSimpleResponseDTOSemFoto() {

        ProfissionalSimpleResponseDTO dto = criarProfissionalSimpleResponseDTOValido();

        dto.setFoto(null);

        return dto;
    }

    public static ProfissionalSimpleResponseDTO criarProfissionalSimpleResponseDTOSemId() {

        ProfissionalSimpleResponseDTO dto = criarProfissionalSimpleResponseDTOValido();

        dto.setId(null);

        return dto;
    }
}