package web.pulso.mappers.troca;

import web.pulso.dto.request.TrocaRequestDTO;

public class TrocaRequestDTOFactory {

    public static TrocaRequestDTO criarTrocaRequestDTOValido() {

        TrocaRequestDTO dto = new TrocaRequestDTO();

        dto.setComentario("Troca de plantão");

        dto.setPlantaoOrigemId(1L);

        dto.setPlantaoDestinoId(2L);

        dto.setProfissionalDestinoId(3L);

        return dto;
    }

}