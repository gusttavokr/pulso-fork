package web.pulso.service.usuario;

import web.pulso.dto.response.usuario.UsuarioResponseDTO;

public class UsuarioResponseDTOFactory {

    public static UsuarioResponseDTO criarUsuarioResponseDTOValido() {

        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(1L);

        dto.setEmail("usuario@pulso.com");

        dto.setAtivo(true);

        return dto;
    }
}
