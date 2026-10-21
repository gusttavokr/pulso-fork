package web.pulso.service.mappers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleLoginResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.entity.Instituicao;

@Component
public class InstituicaoMapper {

    private final UsuarioMapper usuarioMapper;
    private final SetorMapper setorMapper;
    private final ProfissionalMapper profissionalMapper;

    @Autowired
    public InstituicaoMapper(
            @Lazy UsuarioMapper usuarioMapper,
            SetorMapper setorMapper,
            ProfissionalMapper profissionalMapper) {
        this.usuarioMapper = usuarioMapper;
        this.setorMapper = setorMapper;
        this.profissionalMapper = profissionalMapper;
    }

    public Instituicao toEntity(InstituicaoRequestDTO dto) {
        if (dto == null)
            return null;

        var instituicao = new Instituicao();
        instituicao.setNome(dto.getNome());
        instituicao.setEmails(dto.getEmails() != null ? new ArrayList<>(dto.getEmails()) : new ArrayList<>());
        instituicao.setTelefone(dto.getTelefone());
        instituicao.setFotoPerfil(dto.getFotoPerfil());
        instituicao.setEndereco(dto.getEndereco());
        instituicao.setCnpj(dto.getCnpj());
        instituicao.setExigeVinculo(dto.getExigeVinculo());

        return instituicao;
    }

    public InstituicaoResponseDTO toResponseDTO(Instituicao instituicao) {
        if (instituicao == null)
            return null;

        var dto = new InstituicaoResponseDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setEmails(instituicao.getEmails() != null ? new ArrayList<>(instituicao.getEmails()) : new ArrayList<>());
        dto.setTelefone(instituicao.getTelefone());
        dto.setFotoPerfil(instituicao.getFotoPerfil());
        dto.setEndereco(instituicao.getEndereco());
        dto.setCnpj(instituicao.getCnpj());
        dto.setExigeVinculo(instituicao.getExigeVinculo());

        if (instituicao.getUsuario() != null)
            dto.setUsuario(usuarioMapper.toSimpleResponseDTO(instituicao.getUsuario()));

        if (instituicao.getSetores() != null)
            dto.setSetores(
                    instituicao.getSetores().stream()
                            .map(setorMapper::toSimpleResponseDTO)
                            .collect(Collectors.toList()));

        if (instituicao.getProfissionais_clt() != null)
            dto.setProfissionaisClt(
                    instituicao.getProfissionais_clt().stream()
                            .map(profissionalMapper::toSimpleResponseDTO)
                            .collect(Collectors.toList()));

        if (instituicao.getProfisionais_pj() != null)
            dto.setProfissionaisPj(
                    instituicao.getProfisionais_pj().stream()
                            .map(profissionalMapper::toSimpleResponseDTO)
                            .collect(Collectors.toList()));

        return dto;
    }

    public InstituicaoSimpleResponseDTO toSimpleResponseDTO(Instituicao instituicao) {
        if (instituicao == null)
            return null;

        var dto = new InstituicaoSimpleResponseDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setEmails(instituicao.getEmails() != null ? new ArrayList<>(instituicao.getEmails()) : new ArrayList<>());
        dto.setEndereco(instituicao.getEndereco());
        dto.setCnpj(instituicao.getCnpj());

        return dto;
    }

    public InstituicaoSimpleLoginResponseDTO toSimpleLoginResponseDTO(Instituicao instituicao) {
        if (instituicao == null)
            return null;

        var dto = new InstituicaoSimpleLoginResponseDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setFotoPerfil(instituicao.getFotoPerfil());

        return dto;
    }
}