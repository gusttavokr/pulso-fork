package web.pulso.service.instituicao;

import java.util.List;

import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;

public class InstituicaoResponseDTOFactory {

    public static InstituicaoResponseDTO criarInstituicaoResponseDTOValido() {

        InstituicaoResponseDTO dto = new InstituicaoResponseDTO();

        dto.setId(1L);

        dto.setNome("Hospital Central");

        dto.setEmails(List.of("contato@hospital.com"));

        dto.setTelefone("84999999999");

        dto.setFotoPerfil("hospital.png");

        dto.setEndereco("Rua das Flores, 123");

        dto.setCnpj("12345678000199");

        dto.setExigeVinculo(true);

        dto.setSetores(List.of());

        dto.setProfissionaisClt(List.of());

        dto.setProfissionaisPj(List.of());

        return dto;
    }
}
