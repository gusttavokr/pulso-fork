package web.pulso.service.setor;

import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.enums.Especialidade;

public class SetorFactoryResponse {
    public static SetorResponseDTO criarSetorResponseDTO(Long id, String nome, Especialidade especialidade) {
        SetorResponseDTO response = new SetorResponseDTO();
        response.setId(id);
        response.setNome(nome);
        response.setEspecialidade(especialidade);
        return response;
    }
}
