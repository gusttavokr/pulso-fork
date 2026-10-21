package web.pulso.entity.instituicao;

import java.util.List;

import web.pulso.entity.Instituicao;

public class InstituicaoFactory {

    public static Instituicao criarInstituicaoValida() {

        Instituicao instituicao = new Instituicao();

        instituicao.setNome("Hospital Vida");

        instituicao.setEmails(List.of("contato@hospital.com"));

        instituicao.setTelefone("84999999999");

        instituicao.setFotoPerfil("hospital.png");

        instituicao.setEndereco("Rua A, 123");

        instituicao.setCnpj("12345678000199");

        instituicao.setExigeVinculo(true);

        return instituicao;
    }
}
