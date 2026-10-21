package web.pulso.service.patterns.builder;

import java.time.LocalDate;

import web.pulso.entity.Escala;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EscalaBuilder {

    private String nome;
    private LocalDate mesReferencia;
    private Profissional lider;
    private Setor setor;
    private List<Plantao> plantoes = new ArrayList<>();

    private EscalaBuilder() {
    }

    public static EscalaBuilder builder() {
        return new EscalaBuilder();
    }

    public EscalaBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public EscalaBuilder mesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
        return this;
    }

    public EscalaBuilder lider(Profissional lider) {
        this.lider = lider;
        return this;
    }

    public EscalaBuilder setor(Setor setor) {
        this.setor = setor;
        return this;
    }

    public EscalaBuilder plantoes(List<Plantao> plantoes) {
        if (plantoes != null) {
            this.plantoes = new ArrayList<>(plantoes); // cópia defensiva
        }
        return this;
    }

    public EscalaBuilder adicionarPlantao(Plantao plantao) {
        if (plantao != null) {
            this.plantoes.add(plantao);
        }
        return this;
    }

    public Escala build() {

        validarCamposObrigatorios();

        Escala escala = new Escala();
        escala.setNome(nome);
        escala.setMesReferencia(mesReferencia);
        escala.setLiderEscala(lider);
        escala.setSetor(setor);

        if (!plantoes.isEmpty()) {
            vincularPlantoes(escala);
        }

        return escala;
    }

    private void validarCamposObrigatorios() {
        Objects.requireNonNull(nome, "nome é obrigatório");
        Objects.requireNonNull(mesReferencia, "mesReferencia é obrigatório");
        Objects.requireNonNull(lider, "lider é obrigatório");
        Objects.requireNonNull(setor, "setor é obrigatório");
    }

    private void vincularPlantoes(Escala escala) {
        for (Plantao plantao : plantoes) {
            plantao.setEscala(escala);
        }
        escala.setPlantoes(plantoes);
    }
}
