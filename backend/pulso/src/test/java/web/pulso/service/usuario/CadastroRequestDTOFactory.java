package web.pulso.service.usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import web.pulso.dto.request.CadastroRequestDTO;
import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

public class CadastroRequestDTOFactory {

    public static CadastroRequestDTO criarCadastroComInstituicaoValido() {

        return new CadastroRequestDTO(
                "usuario@pulso.com",
                "senha123",
                null,
                criarInstituicaoRequestDTOValido());
    }

    public static CadastroRequestDTO criarCadastroComProfissionalValido() {

        return new CadastroRequestDTO(
                "usuario@pulso.com",
                "senha123",
                criarProfissionalRequestDTOValido(),
                null);
    }

    public static CadastroRequestDTO criarCadastroSemPerfil() {

        return new CadastroRequestDTO(
                "usuario@pulso.com",
                "senha123",
                null,
                null);
    }

    public static CadastroRequestDTO criarCadastroComDoisPerfis() {

        return new CadastroRequestDTO(
                "usuario@pulso.com",
                "senha123",
                criarProfissionalRequestDTOValido(),
                criarInstituicaoRequestDTOValido());
    }

    public static InstituicaoRequestDTO criarInstituicaoRequestDTOValido() {

        return new InstituicaoRequestDTO(
                "Hospital Vida",
                List.of("contato@hospital.com"),
                "84999999999",
                "hospital.png",
                "Rua A, 123",
                "12345678000199",
                true);
    }

    public static ProfissionalRequestDTO criarProfissionalRequestDTOValido() {

        ProfissionalRequestDTO dto = new ProfissionalRequestDTO();

        dto.setNome("João da Silva");

        dto.setEmails(List.of("joao@pulso.com"));

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
