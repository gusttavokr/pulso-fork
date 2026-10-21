package web.pulso.service.profissional;

import java.time.LocalDate;
import java.util.List;

import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;

public class ProfissionalResponseDTOFactory {

    public static ProfissionalResponseDTO criarProfissionalResponseDTOValido() {

        ProfissionalResponseDTO dto = new ProfissionalResponseDTO();

        dto.setId(1L);

        dto.setNome("João da Silva");

        dto.setEmails(List.of("email@gmail.com"));

        dto.setTelefone("84999999999");

        dto.setFotoPerfil("foto.png");

        dto.setAnoFormacao(LocalDate.now().minusYears(5));

        dto.setPix("12345678901");

        dto.setTipoPix(TipoPix.CPF);

        dto.setCrmsFormatados(List.of("CRM/RN 12345"));

        dto.setDataNasc(LocalDate.now().minusYears(30));

        dto.setEspecialidades(List.of(Especialidade.CARDIOLOGIA));

        dto.setInstituicoesClt(List.of());

        dto.setInstituicoesPj(List.of());

        dto.setInstituicoesLiderEscala(List.of());

        dto.setEscalas(List.of());

        dto.setPlantoesCombinados(List.of());

        dto.setPlantoesConfirmados(List.of());

        dto.setTrocasOrigem(List.of());

        dto.setTrocasDestino(List.of());

        return dto;
    }
}