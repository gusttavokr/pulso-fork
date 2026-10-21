package web.pulso.service.troca;

import web.pulso.dto.request.TrocaRequestDTO;

public class TrocaRequestDTOFactory {

    public static TrocaRequestDTO criarTrocaRequestDTOValido() {

        TrocaRequestDTO dto = new TrocaRequestDTO();

        dto.setComentario(
                "Preciso trocar este plantão.");

        dto.setPlantaoOrigemId(1L);

        dto.setProfissionalDestinoId(2L);

        dto.setPlantaoDestinoId(3L);

        return dto;
    }

    public static TrocaRequestDTO criarTrocaSimplesRequestDTO() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(null);

        dto.setPlantaoDestinoId(null);

        return dto;
    }

    public static TrocaRequestDTO criarTrocaRequestDTOSemPlantaoOrigem() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setPlantaoOrigemId(null);

        return dto;
    }

    public static TrocaRequestDTO criarTrocaRequestDTOSemComentario() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario(null);

        return dto;
    }

    public static TrocaRequestDTO criarTrocaRequestDTOComComentarioGrande() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setComentario("a".repeat(256));

        return dto;
    }

    public static TrocaRequestDTO criarTrocaRequestDTOComMesmoProfissional() {

        TrocaRequestDTO dto = criarTrocaRequestDTOValido();

        dto.setProfissionalDestinoId(1L);

        return dto;
    }
}