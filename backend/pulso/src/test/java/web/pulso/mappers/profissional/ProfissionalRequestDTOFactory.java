package web.pulso.mappers.profissional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

public class ProfissionalRequestDTOFactory {

    public static ProfissionalRequestDTO criarProfissionalRequestDTOValido() {

        ProfissionalRequestDTO dto = new ProfissionalRequestDTO();

        dto.setNome("João da Silva");

        dto.setEmails(List.of("email@gmail.com"));

        dto.setTelefone("84999999999");

        dto.setFotoPerfil("foto.png");

        dto.setAnoFormacao(LocalDate.now().minusYears(5));

        dto.setPix("12345678901");

        dto.setTipoPix(TipoPix.CPF);

        dto.setCrmNumero("12345");

        dto.setCrms(Set.of(UF.RN));

        dto.setDataNasc(LocalDate.now().minusYears(30));

        dto.setEspecialidades(Set.of(Especialidade.CARDIOLOGIA));

        return dto;
    }
}
