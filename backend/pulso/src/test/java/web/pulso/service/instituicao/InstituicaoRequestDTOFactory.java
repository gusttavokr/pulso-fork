package web.pulso.service.instituicao;

import java.util.List;

import web.pulso.dto.request.InstituicaoRequestDTO;

public class InstituicaoRequestDTOFactory {

    public static InstituicaoRequestDTO criarInstituicaoRequestDTOValido() {

        return new InstituicaoRequestDTO(
                "Hospital Central",
                List.of("contato@hospital.com"),
                "84999999999",
                "hospital.png",
                "Rua das Flores, 123",
                "12345678000199",
                true);
    }

    public static InstituicaoRequestDTO criarInstituicaoRequestDTOComTodosCamposVazios() {

        return new InstituicaoRequestDTO(
                "",
                List.of(),
                " ",
                "",
                "",
                "",
                null);
    }
}
