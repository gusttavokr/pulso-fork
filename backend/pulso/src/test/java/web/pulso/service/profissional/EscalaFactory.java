package web.pulso.service.profissional;

import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.service.profissional.PlantaoFactory.criarPlantaoValido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import web.pulso.entity.Escala;
import web.pulso.entity.enums.StatusEscala;

public class EscalaFactory {

    public static Escala criarEscalaValida() {

        Escala escala = new Escala();

        escala.setId(1L);

        escala.setNome("Escala Janeiro 2026");

        escala.setMesReferencia(
                LocalDate.of(2026, 1, 1));

        escala.setStatus(
                StatusEscala.RASCUNHO);

        escala.setPublicadaEm(
                LocalDateTime.now());

        escala.setSetor(
                SetorFactory.criarSetorValido());

        escala.setLiderEscala(criarProfissionalValido());

        escala.setPlantoes(new ArrayList<>());

        return escala;
    }

    public static Escala criarEscalaPublicada() {

        Escala escala = criarEscalaValida();

        escala.setStatus(StatusEscala.PUBLICADA);

        return escala;
    }

    public static Escala criarEscalaSemSetor() {

        Escala escala = criarEscalaValida();

        escala.setSetor(null);

        return escala;
    }

    public static Escala criarEscalaSemLider() {

        Escala escala = criarEscalaValida();

        escala.setLiderEscala(null);

        return escala;
    }

    public static Escala criarEscalaSemNome() {

        Escala escala = criarEscalaValida();

        escala.setNome(null);

        return escala;
    }

    public static Escala criarEscalaSemMesReferencia() {

        Escala escala = criarEscalaValida();

        escala.setMesReferencia(null);

        return escala;
    }

    public static Escala criarEscalaComPlantoes() {

        Escala escala = criarEscalaValida();

        escala.setPlantoes(
                List.of(criarPlantaoValido()));

        return escala;
    }
}