package web.pulso.service.usuario;

import java.util.Date;

import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;

public class UsuarioFactory {

    public static Usuario criarUsuarioValido() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);

        usuario.setEmail("usuario@pulso.com");

        usuario.setSenha("senhaEncoded");

        usuario.setAtivo(true);

        usuario.setUltimoLogin(new Date());

        return usuario;
    }

    public static Usuario criarUsuarioInativo() {

        Usuario usuario = criarUsuarioValido();

        usuario.setAtivo(false);

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
