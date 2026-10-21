package web.pulso.service.profissional;

import java.util.ArrayList;
import java.util.List;

import web.pulso.entity.Instituicao;

public class InstituicaoFactory {

    public static Instituicao criarInstituicaoValida() {

        Instituicao instituicao = new Instituicao();

        instituicao.setId(1L);

        instituicao.setNome("Hospital Central");

        instituicao.setEmails(List.of("contato@hospital.com"));

        instituicao.setTelefone("84999999999");

        instituicao.setFotoPerfil("hospital.png");

        instituicao.setEndereco("Rua das Flores, 123");

        instituicao.setCnpj("12345678000199");

        instituicao.setExigeVinculo(true);

        instituicao.setSetores(new ArrayList<>());

        instituicao.setProfissionais_clt(new ArrayList<>());

        instituicao.setProfisionais_pj(new ArrayList<>());

        instituicao.setProfissionais_lider_escala(new ArrayList<>());

        return instituicao;
    }

    public static Instituicao criarInstituicaoComUsuario() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setUsuario(
                UsuarioFactory.criarUsuarioValido());

        return instituicao;
    }
}