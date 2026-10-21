package web.pulso.mappers.usuario;

import web.pulso.dto.request.UsuarioRequestDTO;

public class UsuarioRequestDTOFactory {

    public static UsuarioRequestDTO criarUsuarioRequestDTOValido() {

        UsuarioRequestDTO dto = new UsuarioRequestDTO();

        dto.setEmail("usuario@gmail.com");

        dto.setSenha("123456");

        return dto;
    }
}