package web.pulso.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import web.pulso.dto.response.escala.EscalaResponseDTO;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.entity.Escala;

@Component
public class EscalaMapper {

    // private final SetorMapper setorMapper;
    // private final ProfissionalMapper profissionalMapper;
    private final PlantaoMapper plantaoMapper;

    @Autowired
    public EscalaMapper(
            @Lazy SetorMapper setorMapper,
            @Lazy ProfissionalMapper profissionalMapper,
            @Lazy PlantaoMapper plantaoMapper) {
        // this.setorMapper = setorMapper;
        // this.profissionalMapper = profissionalMapper;
        this.plantaoMapper = plantaoMapper;
    }

    public EscalaResponseDTO toResponseDTO(Escala escala) {
        if (escala == null)
            return null;

        EscalaResponseDTO dto = new EscalaResponseDTO();

        dto.setId(escala.getId());
        dto.setNome(escala.getNome());
        dto.setMesReferencia(escala.getMesReferencia());
        dto.setStatus(escala.getStatus());

        // Setor
        if (escala.getSetor() != null) {
            dto.setNomeSetor(escala.getSetor().getNome());
            dto.setEspecialidadeSetor(escala.getSetor().getEspecialidade());

            if (escala.getSetor().getInstituicao() != null) {
                dto.setNomeInstituicao(escala.getSetor().getInstituicao().getNome());
            }
        }

        // Líder da escala
        if (escala.getLiderEscala() != null) {
            dto.setNomeLider(escala.getLiderEscala().getNome());
            dto.setFotoLider(escala.getLiderEscala().getFotoPerfil());
        }

        // Plantões
        if (escala.getPlantoes() != null) {
            dto.setPlantoes(
                    escala.getPlantoes().stream()
                            .map(plantaoMapper::toEscalaSimplesDTO)
                            .toList());
        }

        return dto;
    }

    public EscalaSimpleResponseDTO toSimpleResponseDTO(Escala escala) {
        if (escala == null)
            return null;

        var dto = new EscalaSimpleResponseDTO();
        dto.setId(escala.getId());
        dto.setNome(escala.getNome());
        dto.setMesReferencia(escala.getMesReferencia());
        dto.setStatus(escala.getStatus());
        dto.setDataCriacao(escala.getDataCriacao());
        dto.setDataAtualizacao(escala.getDataAtualizacao());

        if (escala.getLiderEscala() != null) {
            dto.setNomeLider(escala.getLiderEscala().getNome());
            dto.setFotoLider(escala.getLiderEscala().getFotoPerfil());
        }

        return dto;
    }

}
