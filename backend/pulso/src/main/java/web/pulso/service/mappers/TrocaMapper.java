package web.pulso.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pulso.dto.request.TrocaRequestDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.dto.response.troca.TrocaSimpleResponseDTO;
import web.pulso.entity.Troca;

@Component
public class TrocaMapper {

    private final ProfissionalMapper profissionalMapper;
    private final PlantaoMapper plantaoMapper;

    @Autowired
    public TrocaMapper(ProfissionalMapper profissionalMapper, PlantaoMapper plantaoMapper) {
        this.profissionalMapper = profissionalMapper;
        this.plantaoMapper = plantaoMapper;
    }

    public Troca toEntity(TrocaRequestDTO dto) {
        if (dto == null)
            return null;

        var troca = new Troca();
        troca.setComentario(dto.getComentario());

        return troca;
    }

    public TrocaResponseDTO toResponseDTO(Troca troca) {
        if (troca == null)
            return null;

        var dto = new TrocaResponseDTO();
        dto.setId(troca.getId());
        dto.setComentario(troca.getComentario());
        dto.setTipo(troca.getTipo());
        dto.setStatus(troca.getStatus());

        if (troca.getProfissionalOrigem() != null)
            dto.setProfissionalOrigem(profissionalMapper.toSimpleResponseDTO(troca.getProfissionalOrigem()));

        if (troca.getProfissionalDestino() != null)
            dto.setProfissionalDestino(profissionalMapper.toSimpleResponseDTO(troca.getProfissionalDestino()));

        if (troca.getPlantaoOrigem() != null)
            dto.setPlantaoOrigem(plantaoMapper.toSimpleResponseDTO(troca.getPlantaoOrigem()));

        if (troca.getPlantaoDestino() != null)
            dto.setPlantaoDestino(plantaoMapper.toSimpleResponseDTO(troca.getPlantaoDestino()));

        if (troca.getDataSolicitacao() != null)
            dto.setDataSolicitacao(troca.getDataSolicitacao());

        if (troca.getDataResposta() != null)
            dto.setDataResposta(troca.getDataResposta());

        return dto;
    }

    public TrocaSimpleResponseDTO toSimpleResponseDTO(Troca troca) {
        if (troca == null)
            return null;

        var dto = new TrocaSimpleResponseDTO();
        dto.setId(troca.getId());
        dto.setComentario(troca.getComentario());
        dto.setTipo(troca.getTipo());
        dto.setStatus(troca.getStatus());
        return dto;
    }
}
