package web.pulso.mappers.instituicao;

import java.time.LocalDate;
import java.util.List;

import web.pulso.dto.request.InstituicaoRequestDTO;

public class InstituicaoRequestDTOFactory {

    public static InstituicaoRequestDTO criarInstituicaoRequestDTOValido() {

        InstituicaoRequestDTO dto = new InstituicaoRequestDTO();

        dto.setNome("Hospital Central");

        dto.setEmails(List.of("hospital@gmail.com"));

        dto.setTelefone("84999999999");

        dto.setFotoPerfil("foto.png");

        dto.setEndereco("Rua Central, 123");

        dto.setCnpj("12345678000199");

        dto.setExigeVinculo(true);

        return dto;
    }
}