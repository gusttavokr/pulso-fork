package web.pulso.service.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.TipoUsuario;

@Component
public class UsuarioMapper {

    private final InstituicaoMapper instituicaoMapper;
    private final ProfissionalMapper profissionalMapper;

    @Autowired
    public UsuarioMapper(
            InstituicaoMapper instituicaoMapper,
            ProfissionalMapper profissionalMapper) {
        this.instituicaoMapper = instituicaoMapper;
        this.profissionalMapper = profissionalMapper;
    }

    /**
     * Cadastro de novo usuário
     */
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null)
            return null;

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setAtivo(true);

        return usuario;
    }

    /**
     * Response completo
     */
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null)
            return null;

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setAtivo(usuario.getAtivo());
        dto.setUltimoLogin(usuario.getUltimoLogin());
        dto.setCriadoEm(usuario.getDataCriacao());
        dto.setAtualizadoEm(usuario.getDataAtualizacao());

        if (usuario.getInstituicao() != null)
            dto.setInstituicao(
                    instituicaoMapper.toSimpleResponseDTO(usuario.getInstituicao()));

        if (usuario.getProfissional() != null)
            dto.setProfissional(
                    profissionalMapper.toSimpleResponseDTO(usuario.getProfissional()));

        return dto;
    }

    /**
     * Response simples
     */
    public UsuarioSimpleResponseDTO toSimpleResponseDTO(Usuario usuario) {
        if (usuario == null)
            return null;

        UsuarioSimpleResponseDTO dto = new UsuarioSimpleResponseDTO();
        dto.setId(usuario.getId());
        dto.setAtivo(usuario.getAtivo());
        return dto;
    }

    /**
     * Retorno do login (JWT)
     */
    public UsuarioLoginResponseDTO toLoginResponseDTO(Usuario usuario, String token) {
        if (usuario == null || token == null)
            return null;

        UsuarioLoginResponseDTO dto = new UsuarioLoginResponseDTO();
        dto.setId(usuario.getId());
        dto.setToken(token);

        // Tipo do usuario
        if (usuario.getInstituicao() != null)
            dto.setTipo(TipoUsuario.INSTITUICAO);
        else if (usuario.getProfissional() != null)
            dto.setTipo(TipoUsuario.PROFISSIONAL);
        else
            dto.setTipo(TipoUsuario.DESCONHECIDO);

        // Roles
        List<String> roles = new ArrayList<>();

        if (usuario.getInstituicao() != null)
            roles.add("INSTITUICAO");

        if (usuario.getProfissional() != null) {
            roles.add("PROFISSIONAL");

            if (usuario.getProfissional().getInstituicoesLiderEscala() != null &&
                    !usuario.getProfissional().getInstituicoesLiderEscala().isEmpty()) {
                roles.add("LIDER_ESCALA");
            }
        }

        dto.setRoles(roles);

        // Dados da instituição

        if (usuario.getInstituicao() != null)
            dto.setInstituicao(
                    instituicaoMapper.toSimpleLoginResponseDTO(usuario.getInstituicao()));

        // Dados do profissional
        if (usuario.getProfissional() != null)
            dto.setProfissional(
                    profissionalMapper.toSimpleLoginResponseDTO(usuario.getProfissional()));

        return dto;
    }
}
