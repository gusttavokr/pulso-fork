package web.pulso.service.profissional;

import java.util.Date;

import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;

public class UsuarioFactory {

    public static Usuario criarUsuarioValido() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        usuario.setEmail("usuario@gmail.com");

        usuario.setSenha("123456");

        usuario.setAtivo(true);

        usuario.setUltimoLogin(new Date());

        return usuario;
    }

    public static Usuario criarUsuarioComInstituicao() {

        Usuario usuario = criarUsuarioValido();

        usuario.setInstituicao(new Instituicao());

        return usuario;
    }

    public static Usuario criarUsuarioComProfissional() {

        Usuario usuario = criarUsuarioValido();

        usuario.setProfissional(new Profissional());

        return usuario;
    }
}