package web.pulso.service.troca;

import web.pulso.dto.request.RespostaTrocaRequestDTO;

public class RespostaTrocaRequestDTOFactory {

    public static RespostaTrocaRequestDTO criarRespostaTrocaRequestDTOAceita() {

        RespostaTrocaRequestDTO dto = new RespostaTrocaRequestDTO();

        dto.setTrocaId(1L);

        dto.setAceita(true);

        dto.setPlantaoDestinoId(2L);

        return dto;
    }

    public static RespostaTrocaRequestDTO criarRespostaTrocaRequestDTORecusa() {

        RespostaTrocaRequestDTO dto = new RespostaTrocaRequestDTO();

        dto.setTrocaId(1L);

        dto.setAceita(false);

        dto.setPlantaoDestinoId(null);

        return dto;
    }

    public static RespostaTrocaRequestDTO criarRespostaTrocaRequestDTOSemTrocaId() {

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        dto.setTrocaId(null);

        return dto;
    }

    public static RespostaTrocaRequestDTO criarRespostaTrocaRequestDTOSemAceita() {

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        dto.setAceita(null);

        return dto;
    }

    public static RespostaTrocaRequestDTO criarRespostaTrocaRequestDTOAceitaSemPlantaoDestino() {

        RespostaTrocaRequestDTO dto = criarRespostaTrocaRequestDTOAceita();

        dto.setPlantaoDestinoId(null);

        return dto;
    }
}