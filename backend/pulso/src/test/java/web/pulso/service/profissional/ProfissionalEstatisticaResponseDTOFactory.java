package web.pulso.service.profissional;

import java.util.List;

import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;

public class ProfissionalEstatisticaResponseDTOFactory {

    public static ProfissionalEstatisticaResponseDTO criarProfissionalEstatisticaResponseDTOValido() {

        ProfissionalEstatisticaResponseDTO dto = new ProfissionalEstatisticaResponseDTO();

        dto.setId(1L);

        dto.setFoto("foto-profissional.png");

        dto.setNome("João Silva");

        dto.setEmails(
                List.of(
                        "joao.silva@email.com",
                        "contato.joao@email.com"));

        dto.setTelefone("84999999999");

        dto.setEstatisticasPlantoes(
                EstatisticaPlantoesAnoFactory
                        .criarEstatisticaPlantoesAnoValido());

        return dto;
    }

    public static ProfissionalEstatisticaResponseDTO criarProfissionalEstatisticaResponseDTOSemNome() {

        ProfissionalEstatisticaResponseDTO dto = criarProfissionalEstatisticaResponseDTOValido();

        dto.setNome(null);

        return dto;
    }

    public static ProfissionalEstatisticaResponseDTO criarProfissionalEstatisticaResponseDTOSemEmails() {

        ProfissionalEstatisticaResponseDTO dto = criarProfissionalEstatisticaResponseDTOValido();

        dto.setEmails(null);

        return dto;
    }

    public static ProfissionalEstatisticaResponseDTO criarProfissionalEstatisticaResponseDTOSemTelefone() {

        ProfissionalEstatisticaResponseDTO dto = criarProfissionalEstatisticaResponseDTOValido();

        dto.setTelefone(null);

        return dto;
    }

    public static ProfissionalEstatisticaResponseDTO criarProfissionalEstatisticaResponseDTOSemEstatisticas() {

        ProfissionalEstatisticaResponseDTO dto = criarProfissionalEstatisticaResponseDTOValido();

        dto.setEstatisticasPlantoes(null);

        return dto;
    }
}