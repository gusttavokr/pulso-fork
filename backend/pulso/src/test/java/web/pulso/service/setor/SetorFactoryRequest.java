package web.pulso.service.setor;

import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

public class SetorFactoryRequest {

    public static SetorRequestDTO criarSetorRequestDTO(String nome, Especialidade especialidade) {
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome(nome);
        dto.setEspecialidade(especialidade);
        return dto;
    }

    public static Setor criarSetor(Long id, String nome, Especialidade especialidade) {
        Setor setor = new Setor();
        setor.setId(id);
        setor.setNome(nome);
        setor.setEspecialidade(especialidade);
        return setor;
    }

    public static Instituicao criarInstituicao(Long id) {
        Instituicao instituicao = new Instituicao();
        instituicao.setId(id);
        return instituicao;
    }

    // REQUEST DE NOME

    public static SetorRequestDTO criaSetorRequestDTONomeVazio(){
        // Nome de Setor vazio
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome("");
        dto.setEspecialidade(Especialidade.ENDOCRINOLOGIA);
        return dto;
    }

    public static SetorRequestDTO criaSetorRequestDTONomeNulo(){
        // Nome de Setor nulo
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome(null);
        dto.setEspecialidade(Especialidade.ENDOCRINOLOGIA);
        return dto;
    }

    public static SetorRequestDTO criaSetorRequestDTONomeEspaco(){
        // Nome de Setor com espaço
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome("  ");
        dto.setEspecialidade(Especialidade.ENDOCRINOLOGIA);
        return dto;
    }
    
    public static SetorRequestDTO criarSetorRequestDTONomeMenor(){
        // Nome de Setor menor que 3
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome("UT");
        dto.setEspecialidade(Especialidade.ENDOCRINOLOGIA);
        return dto;
    }

    public static SetorRequestDTO criarSetorRequestDTONomeMaior(){
        // Nome de Setor maior que 255
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome("U".repeat(256));
        dto.setEspecialidade(Especialidade.ENDOCRINOLOGIA);
        return dto;
    }

    public static SetorRequestDTO criarSetorRequestDTOEspecialidadeNula(){
        SetorRequestDTO dto = new SetorRequestDTO();
        dto.setNome("UTI");
        dto.setEspecialidade(null);
        return dto;
    }

    public static SetorRequestDTO criarSetorRequestDTONulo(){
        SetorRequestDTO dto = new SetorRequestDTO();
        return dto;
    }
}