package web.pulso.entity.usuario;

import web.pulso.entity.Usuario;

public class UsuarioFactory {

    public static Usuario criarUsuarioValido() {

        Usuario usuario = new Usuario();

        usuario.setEmail("usuario@gmail.com");

        usuario.setSenha("12345678");

        usuario.setAtivo(true);

        return usuario;
    }
}