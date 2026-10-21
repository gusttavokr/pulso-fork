package web.pulso.factory;

import org.mockito.Mockito;
import web.pulso.dto.request.PlantaoRequestDTO;
import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.entity.*;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.entity.enums.StatusTroca;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlantaoFactory {

    public static PlantaoRequestDTO criarPlantaoRequestDTOPadrao(Long escalaId) {
        PlantaoRequestDTO dto = new PlantaoRequestDTO();
        dto.setEscalaId(escalaId);
        LocalDate mesReferencia = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        LocalDateTime entrada = mesReferencia.atStartOfDay().plusDays(10);
        LocalDateTime saida = entrada.plusHours(12);
        dto.setHoraEntrada(entrada);
        dto.setHoraSaida(saida);
        return dto;
    }

    public static Escala criarEscalaPadrao(Long id, StatusEscala status) {
        Escala escala = new Escala();
        if (id != null) escala.setId(id);
        escala.setStatus(status);
        escala.setMesReferencia(LocalDate.now().plusMonths(1).withDayOfMonth(1));
        return escala;
    }

    public static Plantao criarPlantaoPadrao(Escala escala) {
        Plantao plantao = new Plantao();
        plantao.setEscala(escala);
        if (escala != null && escala.getMesReferencia() != null) {
            LocalDateTime entrada = escala.getMesReferencia().atStartOfDay().plusDays(10);
            plantao.setHoraEntrada(entrada);
            plantao.setHoraSaida(entrada.plusHours(12));
        }
        return plantao;
    }

    public static Usuario criarUsuarioPadrao(Long id) {
        Usuario usuario = new Usuario();
        if (id != null) usuario.setId(id);
        usuario.setEmail("teste@email.com");
        return usuario;
    }

    public static Profissional criarProfissionalPadrao(Usuario usuario) {
        Profissional profissional = new Profissional();
        profissional.setUsuario(usuario);
        return profissional;
    }

    public static Troca criarTrocaPadrao(StatusTroca status) {
        Troca troca = new Troca();
        if (status != null) troca.setStatus(status);
        return troca;
    }

    public static Setor criarSetorPadrao() {
        return new Setor();
    }

    public static PlantaoResponseDTO mockPlantaoResponseDTO() {
        return Mockito.mock(PlantaoResponseDTO.class);
    }

    public static PlantaoSimpleResponseDTO mockPlantaoSimpleResponseDTO() {
        return Mockito.mock(PlantaoSimpleResponseDTO.class);
    }

    public static Instituicao criarInstituicaoPadrao(String nome, String endereco) {
        Instituicao inst = new Instituicao();
        inst.setNome(nome);
        inst.setEndereco(endereco);
        return inst;
    }

    public static Setor criarSetorCompleto(String nome, web.pulso.entity.enums.Especialidade especialidade, Instituicao instituicao) {
        Setor setor = new Setor();
        setor.setNome(nome);
        setor.setEspecialidade(especialidade);
        setor.setInstituicao(instituicao);
        return setor;
    }

    public static Profissional criarProfissionalCompleto(Long id, String nome, String fotoPerfil) {
        Profissional pro = new Profissional();
        if (id != null) pro.setId(id);
        pro.setNome(nome);
        pro.setFotoPerfil(fotoPerfil);
        return pro;
    }

    public static Escala criarEscalaCompleta(Long id, StatusEscala status, Setor setor, Profissional lider) {
        Escala escala = criarEscalaPadrao(id, status);
        escala.setSetor(setor);
        escala.setLiderEscala(lider);
        return escala;
    }
}
