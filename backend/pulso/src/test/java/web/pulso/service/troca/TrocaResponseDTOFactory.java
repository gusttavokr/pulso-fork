package web.pulso.service.troca;

import java.time.LocalDateTime;

import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

public class TrocaResponseDTOFactory {

        public static TrocaResponseDTO criarTrocaResponseDTOValido() {

                TrocaResponseDTO dto = new TrocaResponseDTO();

                dto.setId(1L);

                dto.setComentario(
                                "Troca solicitada.");

                dto.setTipo(
                                TipoTroca.DUPLA);

                dto.setStatus(
                                StatusTroca.PENDENTE);

                dto.setDataSolicitacao(
                                LocalDateTime.now());

                dto.setDataResposta(null);

                dto.setProfissionalOrigem(
                                ProfissionalSimpleResponseDTOFactory
                                                .criarProfissionalSimpleResponseDTOValido());

                dto.setProfissionalDestino(
                                ProfissionalSimpleResponseDTOFactory
                                                .criarProfissionalSimpleResponseDTOValido());

                dto.setPlantaoOrigem(
                                PlantaoSimpleResponseDTOFactory
                                                .criarPlantaoSimpleResponseDTOValido());

                dto.setPlantaoDestino(
                                PlantaoSimpleResponseDTOFactory
                                                .criarPlantaoSimpleResponseDTOValido());

                return dto;
        }

        public static TrocaResponseDTO criarTrocaSimplesResponseDTO() {

                TrocaResponseDTO dto = criarTrocaResponseDTOValido();

                dto.setTipo(TipoTroca.SIMPLES);

                dto.setProfissionalDestino(null);

                dto.setPlantaoDestino(null);

                return dto;
        }

        public static TrocaResponseDTO criarTrocaResponseDTOSemComentario() {

                TrocaResponseDTO dto = criarTrocaResponseDTOValido();

                dto.setComentario(null);

                return dto;
        }

        public static TrocaResponseDTO criarTrocaResponseDTOSemResposta() {

                TrocaResponseDTO dto = criarTrocaResponseDTOValido();

                dto.setDataResposta(null);

                return dto;
        }

        public static TrocaResponseDTO criarTrocaResponseDTORecusada() {

                TrocaResponseDTO dto = criarTrocaResponseDTOValido();

                dto.setStatus(StatusTroca.RECUSADO);

                return dto;
        }
}