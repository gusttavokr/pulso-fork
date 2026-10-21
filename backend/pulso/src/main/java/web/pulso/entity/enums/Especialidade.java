package web.pulso.entity.enums;

public enum Especialidade {
    CARDIOLOGIA("Cardiologia"),
    PEDIATRIA("Pediatria"),
    DERMATOLOGIA("Dermatologia"),
    GINECOLOGIA("Ginecologia"),
    ORTOPEDIA("Ortopedia"),
    NEUROLOGIA("Neurologia"),
    PSIQUIATRIA("Psiquiatria"),
    CLINICA_GERAL("Clinica Geral"),
    OFTALMOLOGIA("Oftalmologia"),
    OTORRINOLARINGOLOGIA("Otorrinolaringologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    UROLOGIA("Urologia"),
    REUMATOLOGIA("Reumatologia"),
    HEMATOLOGIA("Hematologia"),
    INFECTOLOGIA("Infectologia"),
    NEFROLOGIA("Nefrologia"),
    PNEUMOLOGIA("Pneumologia"),
    ONCOLOGIA("Oncologia"),
    RADIOLOGIA("Radiologia"),
    ANESTESIOLOGIA("Anestesiologia"),
    CIRURGIA_GERAL("Cirurgia Geral"),
    MEDICINA_DO_TRABALHO("Medicina do Trabalho"),
    MEDICINA_INTENSIVA("Medicina Intensiva"),
    MEDICINA_ESPORTIVA("Medicina Esportiva");

    private final String label;

    Especialidade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}