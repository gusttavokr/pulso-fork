package web.pulso.service.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.pulso.dto.request.PlantaoRequestDTO;
import web.pulso.dto.response.plantao.PlantaoEscalaSimplesResponseDTO;
import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.entity.Plantao;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

@Component
public class PlantaoMapper {

    private final ProfissionalMapper profissionalMapper;

    @Autowired
    public PlantaoMapper(
            ProfissionalMapper profissionalMapper) {
        this.profissionalMapper = profissionalMapper;
    }

    public Plantao toEntity(PlantaoRequestDTO dto) {
        if (dto == null)
            return null;

        var plantao = new Plantao();
        plantao.setHoraEntrada(dto.getHoraEntrada());
        plantao.setHoraSaida(dto.getHoraSaida());
        plantao.setEstimativaValor(dto.getEstimativaValor());
        plantao.setFormaPagamento(dto.getFormaPagamento());
        plantao.setEstimativaPagamento(dto.getEstimativaPagamento());
        plantao.setComentario(dto.getComentario());
        plantao.setQuantidadeProfissionais(dto.getQuantidadeProfissionais());

        return plantao;
    }

    public PlantaoResponseDTO toResponseDTO(Plantao plantao, Troca troca) {
        if (plantao == null)
            return null;

        var dto = new PlantaoResponseDTO();
        dto.setId(plantao.getId());
        dto.setEspecialidade(plantao.getEscala().getSetor().getEspecialidade());
        dto.setNomeInstituicao(plantao.getEscala().getSetor().getInstituicao().getNome());
        dto.setHoraEntrada(plantao.getHoraEntrada());
        dto.setHoraSaida(plantao.getHoraSaida());
        dto.setIdLider(plantao.getEscala().getLiderEscala().getId());
        dto.setFotoLider(plantao.getEscala().getLiderEscala().getFotoPerfil());
        dto.setNomeLider(plantao.getEscala().getLiderEscala().getNome());
        dto.setNomeSetor(plantao.getEscala().getSetor().getNome());
        dto.setEnderecoInstituicao(plantao.getEscala().getSetor().getInstituicao().getEndereco());
        dto.setComentario(plantao.getComentario());
        dto.setFormaPagamento(plantao.getFormaPagamento());
        dto.setEstimativaValor(plantao.getEstimativaValor());
        dto.setIdEscala(plantao.getEscala().getId());
        dto.setStatusEscala(plantao.getEscala().getStatus());
        dto.setQuantidadeProfissionais(plantao.getQuantidadeProfissionais());
        dto.setEstimativaPagamento(plantao.getEstimativaPagamento());

        dto.setProfissionaisCombinados(
                plantao.getProfissionaisCombinados()
                        .stream()
                        .map(profissionalMapper::toSimpleResponseDTO)
                        .toList());

        dto.setProfissionaisConfirmados(
                plantao.getProfissionaisConfirmados()
                        .stream()
                        .map(profissionalMapper::toSimpleResponseDTO)
                        .toList());

        dto.setProfissionaisTrocaDestino(
                Optional.ofNullable(plantao.getTrocasOrigem())
                        .orElse(List.of())
                        .stream()
                        .filter(t -> t.getStatus() == StatusTroca.CONFIRMADO &&
                                t.getProfissionalDestino() != null)
                        .map(Troca::getProfissionalDestino)
                        .map(profissionalMapper::toSimpleResponseDTO)
                        .toList());

        if (troca != null) {
            dto.setComentarioTroca(troca.getComentario());
            dto.setTipoTroca(troca.getTipo());
        }

        return dto;
    }

    public PlantaoSimpleResponseDTO toSimpleResponseDTO(Plantao plantao) {
        if (plantao == null)
            return null;

        PlantaoSimpleResponseDTO dto = new PlantaoSimpleResponseDTO();

        /*
         * =========================
         * DADOS DIRETOS DO PLANTÃO
         * =========================
         */
        dto.setId(plantao.getId());
        dto.setHoraEntrada(plantao.getHoraEntrada());
        dto.setHoraSaida(plantao.getHoraSaida());
        dto.setEstimativaValor(plantao.getEstimativaValor());

        /*
         * =========================
         * DADOS VINDOS DA ESCALA
         * =========================
         */
        var escala = plantao.getEscala();
        if (escala != null) {

            /*
             * -------------------------
             * Líder da escala
             * -------------------------
             */
            var lider = escala.getLiderEscala();
            if (lider != null) {
                dto.setNomeLider(lider.getNome());
                dto.setFotoLider(lider.getFotoPerfil());
            }

            /*
             * -------------------------
             * Setor / Especialidade / Instituição
             * -------------------------
             */
            var setor = escala.getSetor();
            if (setor != null) {

                // Especialidade do setor
                if (setor.getEspecialidade() != null) {
                    dto.setEspecialidade(setor.getEspecialidade().getLabel());
                }

                // Nome da instituição (vem do setor)
                var instituicao = setor.getInstituicao();
                if (instituicao != null) {
                    dto.setNomeInstituicao(instituicao.getNome());
                }
            }
        }

        /*
         * =========================
         * REGRAS DE NEGÓCIO
         * =========================
         */

        /*
         * Um plantão é considerado NOVO quando:
         * - Não possui profissionais combinados
         * - Não possui profissionais confirmados
         * - Não possui trocas como origem
         * - Não possui trocas como destino
         *
         * Ou seja: nenhuma interação ocorreu ainda.
         */
        boolean semCombinados = plantao.getProfissionaisCombinados() == null
                || plantao.getProfissionaisCombinados().isEmpty();

        boolean semConfirmados = plantao.getProfissionaisConfirmados() == null
                || plantao.getProfissionaisConfirmados().isEmpty();

        boolean semTrocasOrigem = plantao.getTrocasOrigem() == null
                || plantao.getTrocasOrigem().isEmpty();

        boolean semTrocasDestino = plantao.getTrocasDestino() == null
                || plantao.getTrocasDestino().isEmpty();

        boolean ehNovoPlantao = semCombinados && semConfirmados && semTrocasOrigem && semTrocasDestino;

        dto.setEhNovoPlantao(ehNovoPlantao);

        /*
         * =========================
         * TROCA e REPASSE
         * =========================
         *
         * A lógica se baseia nas trocas onde o plantão é ORIGEM:
         *
         * - TipoTroca.SIMPLES -> REPASSE
         * - TipoTroca.DUPLA -> TROCA
         *
         * Observação:
         * - Um plantão novo nunca será troca ou repasse
         */
        boolean ehTroca = false;
        boolean ehRepasse = false;

        if (!ehNovoPlantao && plantao.getTrocasOrigem() != null) {
            for (Troca troca : plantao.getTrocasOrigem()) {
                if (troca.getTipo() == TipoTroca.DUPLA) {
                    ehTroca = true;
                } else if (troca.getTipo() == TipoTroca.SIMPLES) {
                    ehRepasse = true;
                }
            }
        }

        dto.setEhTroca(ehTroca);
        dto.setEhRepasse(ehRepasse);

        return dto;
    }

    public PlantaoEscalaSimplesResponseDTO toEscalaSimplesDTO(Plantao plantao) {
        if (plantao == null)
            return null;

        PlantaoEscalaSimplesResponseDTO dto = new PlantaoEscalaSimplesResponseDTO();

        dto.setId(plantao.getId());
        dto.setHoraEntrada(plantao.getHoraEntrada());
        dto.setHoraSaida(plantao.getHoraSaida());
        dto.setEstimativaValor(String.valueOf(plantao.getEstimativaValor()));
        dto.setQtdProfissionais(String.valueOf(plantao.getQuantidadeProfissionais()));

        if (plantao.getEscala() != null &&
                plantao.getEscala().getSetor() != null) {

            dto.setEspecialidade(
                    plantao.getEscala().getSetor().getEspecialidade());

            if (plantao.getEscala().getSetor().getInstituicao() != null) {
                dto.setNomeInstituicao(
                        plantao.getEscala().getSetor().getInstituicao().getNome());
            }
        }

        // Profissionais alocados (confirmados)
        if (plantao.getProfissionaisConfirmados() != null) {
            dto.setProfissionaisAlocados(
                    plantao.getProfissionaisAlocados().stream()
                            .map(profissionalMapper::toSimpleResponseDTO)
                            .toList());
        }

        return dto;
    }
    public void atualizarEntityComDTO(Plantao existente, PlantaoRequestDTO dto) {
        if (dto.getHoraEntrada() != null) existente.setHoraEntrada(dto.getHoraEntrada());
        if (dto.getHoraSaida() != null) existente.setHoraSaida(dto.getHoraSaida());
        if (dto.getFormaPagamento() != null) existente.setFormaPagamento(dto.getFormaPagamento());
        if (dto.getEstimativaPagamento() != null) existente.setEstimativaPagamento(dto.getEstimativaPagamento());
        if (dto.getComentario() != null) existente.setComentario(dto.getComentario());

        existente.setEstimativaValor(dto.getEstimativaValor());
        existente.setQuantidadeProfissionais(dto.getQuantidadeProfissionais());
    }

}