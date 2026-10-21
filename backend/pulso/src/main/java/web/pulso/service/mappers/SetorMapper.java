package web.pulso.service.mappers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.dto.response.setor.SetorSimpleResponseDTO;
import web.pulso.entity.Setor;

@Component
public class SetorMapper {

    private final InstituicaoMapper instituicaoMapper;
    private final EscalaMapper escalaMapper;

    @Autowired
    public SetorMapper(@Lazy InstituicaoMapper instituicaoMapper, EscalaMapper escalaMapper) {
        this.instituicaoMapper = instituicaoMapper;
        this.escalaMapper = escalaMapper;
    }

    public Setor toEntity(SetorRequestDTO dto) {
        if (dto == null)
            return null;

        var setor = new Setor();
        setor.setNome(dto.getNome());
        setor.setEspecialidade(dto.getEspecialidade());

        return setor;
    }

    public SetorResponseDTO toResponseDTO(Setor setor) {
        if (setor == null)
            return null;

        var dto = new SetorResponseDTO();
        dto.setId(setor.getId());
        dto.setNome(setor.getNome());
        dto.setEspecialidade(setor.getEspecialidade());

        if (setor.getInstituicao() != null)
            dto.setInstituicao(instituicaoMapper.toSimpleResponseDTO(setor.getInstituicao()));

        if (setor.getEscalas() != null)
            dto.setEscalas(
                    setor.getEscalas().stream()
                            .map(escalaMapper::toSimpleResponseDTO)
                            .collect(Collectors.toList()));

        return dto;
    }

    public SetorSimpleResponseDTO toSimpleResponseDTO(Setor setor) {
        if (setor == null)
            return null;

        var dto = new SetorSimpleResponseDTO();
        dto.setId(setor.getId());
        dto.setNome(setor.getNome());
        dto.setEspecialidade(setor.getEspecialidade());
        return dto;
    }
}
