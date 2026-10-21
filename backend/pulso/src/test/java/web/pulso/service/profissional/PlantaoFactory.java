package web.pulso.service.profissional;

import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import web.pulso.entity.Plantao;
import web.pulso.entity.enums.FormaPagamento;

public class PlantaoFactory {

        public static Plantao criarPlantaoValido() {

                Plantao plantao = new Plantao();

                plantao.setId(1L);

                plantao.setHoraEntrada(
                                LocalDateTime.of(2026, 1, 10, 7, 0));

                plantao.setHoraSaida(
                                LocalDateTime.of(2026, 1, 10, 19, 0));

                plantao.setEstimativaValor(1500);

                plantao.setFormaPagamento(
                                FormaPagamento.PIX);

                plantao.setEstimativaPagamento(
                                LocalDate.of(2026, 1, 20));

                plantao.setComentario(
                                "Plantão diurno UTI adulto");

                plantao.setQuantidadeProfissionais(2);

                plantao.setEscala(
                                EscalaFactory.criarEscalaValida());

                plantao.setProfissionaisCombinados(
                                new HashSet<>());

                plantao.setProfissionaisConfirmados(
                                new HashSet<>());

                plantao.setTrocasOrigem(
                                new ArrayList<>());

                plantao.setTrocasDestino(
                                new ArrayList<>());

                return plantao;
        }

        public static Plantao criarOutroPlantaoValido() {

                Plantao plantao = new Plantao();

                plantao.setId(2L);

                plantao.setHoraEntrada(
                                LocalDateTime.of(2026, 1, 11, 7, 0));

                plantao.setHoraSaida(
                                LocalDateTime.of(2026, 1, 11, 19, 0));

                plantao.setEstimativaValor(1800);

                plantao.setFormaPagamento(
                                FormaPagamento.BOLETO);

                plantao.setEstimativaPagamento(
                                LocalDate.of(2026, 1, 25));

                plantao.setComentario(
                                "Plantão noturno emergência");

                plantao.setQuantidadeProfissionais(3);

                plantao.setEscala(
                                EscalaFactory.criarEscalaValida());

                plantao.setProfissionaisCombinados(
                                new HashSet<>());

                plantao.setProfissionaisConfirmados(
                                new HashSet<>());

                plantao.setTrocasOrigem(
                                new ArrayList<>());

                plantao.setTrocasDestino(
                                new ArrayList<>());

                return plantao;
        }

        public static Plantao criarPlantaoSemEscala() {

                Plantao plantao = criarPlantaoValido();

                plantao.setEscala(null);

                return plantao;
        }

        public static Plantao criarPlantaoSemHoraEntrada() {

                Plantao plantao = criarPlantaoValido();

                plantao.setHoraEntrada(null);

                return plantao;
        }

        public static Plantao criarPlantaoSemHoraSaida() {

                Plantao plantao = criarPlantaoValido();

                plantao.setHoraSaida(null);

                return plantao;
        }

        public static Plantao criarPlantaoSemQuantidadeProfissionais() {

                Plantao plantao = criarPlantaoValido();

                plantao.setQuantidadeProfissionais(null);

                return plantao;
        }

        public static Plantao criarPlantaoComProfissionaisConfirmados() {

                Plantao plantao = criarPlantaoValido();

                plantao.setProfissionaisConfirmados(
                                Set.of(criarProfissionalValido()));

                return plantao;
        }

        public static Plantao criarPlantaoComProfissionaisCombinados() {

                Plantao plantao = criarPlantaoValido();

                plantao.setProfissionaisCombinados(
                                Set.of(criarProfissionalValido()));

                return plantao;
        }
}