package web.pulso.service.troca;

import java.time.LocalDateTime;

import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;

public class PlantaoSimpleResponseDTOFactory {

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTOValido() {

        PlantaoSimpleResponseDTO dto = new PlantaoSimpleResponseDTO();

        dto.setId(1L);

        dto.setNomeInstituicao("Hospital Central");

        dto.setFotoLider("lider.png");

        dto.setNomeLider("Dr. João Silva");

        dto.setEspecialidade("CARDIOLOGIA");

        dto.setHoraEntrada(
                LocalDateTime.of(2026, 1, 10, 7, 0));

        dto.setHoraSaida(
                LocalDateTime.of(2026, 1, 10, 19, 0));

        dto.setEstimativaValor(1500);

        dto.setEhNovoPlantao(true);

        dto.setEhTroca(false);

        dto.setEhRepasse(false);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTODeTroca() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setEhNovoPlantao(false);

        dto.setEhTroca(true);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTORepasse() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setEhNovoPlantao(false);

        dto.setEhRepasse(true);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTOSemInstituicao() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setNomeInstituicao(null);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTOSemLider() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setNomeLider(null);

        dto.setFotoLider(null);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTOSemEspecialidade() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setEspecialidade(null);

        return dto;
    }

    public static PlantaoSimpleResponseDTO criarPlantaoSimpleResponseDTOSemHorario() {

        PlantaoSimpleResponseDTO dto = criarPlantaoSimpleResponseDTOValido();

        dto.setHoraEntrada(null);

        dto.setHoraSaida(null);

        return dto;
    }
}