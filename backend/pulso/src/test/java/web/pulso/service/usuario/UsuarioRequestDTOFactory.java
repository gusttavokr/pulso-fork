package web.pulso.service.usuario;

import web.pulso.dto.request.UsuarioRequestDTO;

public class UsuarioRequestDTOFactory {

    public static UsuarioRequestDTO criarUsuarioRequestDTOValido() {

        return new UsuarioRequestDTO(
                "usuario@pulso.com",
                "novaSenha123");
    }

    public static UsuarioRequestDTO criarUsuarioRequestDTOComSenhaVazia() {

        return new UsuarioRequestDTO(
                "usuario@pulso.com",
                " ");
    }
}
