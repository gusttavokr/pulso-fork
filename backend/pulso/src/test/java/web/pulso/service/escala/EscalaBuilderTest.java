package web.pulso.service.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import web.pulso.entity.Escala;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.service.patterns.builder.EscalaBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("EscalaBuilder")
class EscalaBuilderTest {

    private static final String NOME_ESCALA = "Escala Fevereiro";

    private LocalDate    mesReferencia;
    private Profissional lider;
    private Setor        setor;

    @BeforeEach
    void setUp() {
        mesReferencia = LocalDate.now().plusMonths(2).withDayOfMonth(1);

        lider = new Profissional();
        lider.setId(1L);

        setor = new Setor();
        setor.setId(1L);
    }

    @Nested
    @DisplayName("Campos obrigatórios")
    class CamposObrigatorios {

        @Test
        @DisplayName("[CE-nome-nulo] Deve lançar NullPointerException com mensagem quando nome for nulo")
        void deveLancarExcecaoQuandoNomeForNulo() {
            assertThatThrownBy(() ->
                    EscalaBuilder.builder()
                            .nome(null)
                            .mesReferencia(mesReferencia)
                            .lider(lider)
                            .setor(setor)
                            .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("nome");
        }

        @Test
        @DisplayName("[CE-nome-nulo] Deve lançar NullPointerException quando nome não for informado")
        void deveLancarExcecaoQuandoNomeNaoForInformado() {
            // nome() nunca chamado — campo permanece null
            assertThatThrownBy(() ->
                    EscalaBuilder.builder()
                            .mesReferencia(mesReferencia)
                            .lider(lider)
                            .setor(setor)
                            .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("nome");
        }

        @Test
        @DisplayName("[CE-mes-nulo] Deve lançar NullPointerException com mensagem quando mesReferencia for nulo")
        void deveLancarExcecaoQuandoMesReferenciaForNulo() {
            assertThatThrownBy(() ->
                    EscalaBuilder.builder()
                            .nome(NOME_ESCALA)
                            .mesReferencia(null)
                            .lider(lider)
                            .setor(setor)
                            .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("mesReferencia");
        }

        @Test
        @DisplayName("[CE-lider-nulo] Deve lançar NullPointerException com mensagem quando lider for nulo")
        void deveLancarExcecaoQuandoLiderForNulo() {
            assertThatThrownBy(() ->
                    EscalaBuilder.builder()
                            .nome(NOME_ESCALA)
                            .mesReferencia(mesReferencia)
                            .lider(null)
                            .setor(setor)
                            .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("lider");
        }

        @Test
        @DisplayName("[CE-setor-nulo] Deve lançar NullPointerException com mensagem quando setor for nulo")
        void deveLancarExcecaoQuandoSetorForNulo() {
            assertThatThrownBy(() ->
                    EscalaBuilder.builder()
                            .nome(NOME_ESCALA)
                            .mesReferencia(mesReferencia)
                            .lider(lider)
                            .setor(null)
                            .build())
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("setor");
        }
    }

    @Nested
    @DisplayName("build()")
    class Build {

        @Test
        @DisplayName("[CE-valido] Deve retornar Escala não nula quando todos os campos obrigatórios forem informados")
        void deveRetornarEscalaQuandoCamposObrigatoriosInformados() {
            // Act
            Escala escala = buildValido();

            // Assert
            assertThat(escala).isNotNull();
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear nome corretamente na Escala construída")
        void deveMapearNomeCorretamente() {
            Escala escala = buildValido();
            assertThat(escala.getNome()).isEqualTo(NOME_ESCALA);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear mesReferencia corretamente na Escala construída")
        void deveMapearMesReferenciaCorretamente() {
            Escala escala = buildValido();
            assertThat(escala.getMesReferencia()).isEqualTo(mesReferencia);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear liderEscala corretamente na Escala construída")
        void deveMapearLiderCorretamente() {
            Escala escala = buildValido();
            assertThat(escala.getLiderEscala()).isSameAs(lider);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear setor corretamente na Escala construída")
        void deveMapearSetorCorretamente() {
            Escala escala = buildValido();
            assertThat(escala.getSetor()).isSameAs(setor);
        }

        @Test
        @DisplayName("[CE-valido] Deve retornar Escala sem plantoes quando nenhum plantao for adicionado")
        void deveRetornarEscalaSemPlantoesQuandoNenhumForAdicionado() {
            Escala escala = buildValido();
            // plantoes vazio → vincularPlantoes não é chamado → lista permanece nula ou vazia
            assertThat(escala.getPlantoes()).isNullOrEmpty();
        }

        @Test
        @DisplayName("[CE-valido] Cada chamada a build() deve retornar uma nova instância de Escala")
        void cadaBuildDeveRetornarNovaInstancia() {
            // Arrange
            EscalaBuilder builder = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor);

            // Act
            Escala escala1 = builder.build();
            Escala escala2 = builder.build();

            // Assert
            assertThat(escala1).isNotSameAs(escala2);
        }
    }

    @Nested
    @DisplayName("plantoes()")
    class Plantoes {

        @Test
        @DisplayName("[CE-plantoes-nulos] Deve ignorar quando lista de plantoes for nula e manter lista interna vazia")
        void deveIgnorarQuandoListaForNula() {
            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(null)
                    .build();

            // Assert — null não substitui a lista interna; escala sem plantões
            assertThat(escala.getPlantoes()).isNullOrEmpty();
        }

        @Test
        @DisplayName("[CE-plantoes-vazios | VL-min] Deve construir escala sem plantoes quando lista passada for vazia")
        void deveConstruirSemPlantoesQuandoListaVazia() {
            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(List.of())
                    .build();

            // Assert
            assertThat(escala.getPlantoes()).isNullOrEmpty();
        }

        @Test
        @DisplayName("[CE-plantoes-validos | VL-1] Deve vincular plantoes quando lista tiver exatamente 1 elemento")
        void deveVincularQuandoListaTiverUmElemento() {
            // Arrange — VL: mínimo não vazio
            Plantao plantao = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(List.of(plantao))
                    .build();

            // Assert
            assertThat(escala.getPlantoes()).hasSize(1);
        }

        @Test
        @DisplayName("[CE-copia-defensiva] Mutação na lista original após passada ao builder não deve afetar a Escala construída")
        void mutacaoExternaNaoDeveAfetar() {
            // Arrange
            Plantao plantao1 = new Plantao();
            List<Plantao> listaOriginal = new ArrayList<>(List.of(plantao1));

            EscalaBuilder builder = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(listaOriginal);

            // Mutação após passar ao builder
            listaOriginal.add(new Plantao());

            // Act
            Escala escala = builder.build();

            // Assert — escala deve ter apenas o plantão original, não o adicionado depois
            assertThat(escala.getPlantoes()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("adicionarPlantao()")
    class AdicionarPlantao {

        @Test
        @DisplayName("[CE-plantao-nulo] Deve ignorar silenciosamente quando plantao adicionado for nulo")
        void deveIgnorarPlantaoNulo() {
            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .adicionarPlantao(null)
                    .build();

            // Assert — nulo ignorado, escala sem plantões
            assertThat(escala.getPlantoes()).isNullOrEmpty();
        }

        @Test
        @DisplayName("[CE-plantao-valido] Deve adicionar plantao à lista quando plantao não for nulo")
        void deveAdicionarPlantaoQuandoNaoForNulo() {
            // Arrange
            Plantao plantao = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .adicionarPlantao(plantao)
                    .build();

            // Assert
            assertThat(escala.getPlantoes()).hasSize(1).contains(plantao);
        }

        @Test
        @DisplayName("[CE-plantao-valido] Deve acumular múltiplos plantoes adicionados individualmente")
        void deveAcumularMultiplosPlantoes() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();
            Plantao p3 = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .adicionarPlantao(p1)
                    .adicionarPlantao(p2)
                    .adicionarPlantao(p3)
                    .build();

            // Assert
            assertThat(escala.getPlantoes()).hasSize(3).containsExactly(p1, p2, p3);
        }

        @Test
        @DisplayName("[CE-plantao-valido] Deve ignorar nulos intercalados entre plantoes válidos")
        void deveIgnorarNulosIntercalados() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .adicionarPlantao(p1)
                    .adicionarPlantao(null)
                    .adicionarPlantao(p2)
                    .build();

            // Assert — apenas os não nulos
            assertThat(escala.getPlantoes()).hasSize(2).containsExactly(p1, p2);
        }
    }

    @Nested
    @DisplayName("vincularPlantoes()")
    class VincularPlantoes {

        @Test
        @DisplayName("[CE-vinculo] Deve setar a escala em cada plantao ao construir")
        void deveSetarEscalaEmCadaPlantao() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(List.of(p1, p2))
                    .build();

            // Assert — vínculo bidirecional: cada plantão aponta para a escala construída
            assertThat(p1.getEscala()).isSameAs(escala);
            assertThat(p2.getEscala()).isSameAs(escala);
        }

        @Test
        @DisplayName("[CE-vinculo] Escala deve conter os mesmos plantoes passados ao builder")
        void escalaDeveConterOsMesmosPlantoes() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(List.of(p1, p2))
                    .build();

            // Assert
            assertThat(escala.getPlantoes()).containsExactly(p1, p2);
        }

        @Test
        @DisplayName("[CE-vinculo] Todos os plantoes devem apontar para a mesma instância de escala")
        void todosOsPlantoesDemApontarParaMesmaInstanciaDeEscala() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();
            Plantao p3 = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .plantoes(List.of(p1, p2, p3))
                    .build();

            // Assert — isSameAs garante referência, não apenas igualdade
            assertThat(p1.getEscala()).isSameAs(escala);
            assertThat(p2.getEscala()).isSameAs(escala);
            assertThat(p3.getEscala()).isSameAs(escala);
        }

        @Test
        @DisplayName("[CE-vinculo] Plantao adicionado via adicionarPlantao deve também ter escala vinculada")
        void plantaoAdicionadoIndividualmenteTambemDeveSerVinculado() {
            // Arrange
            Plantao plantao = new Plantao();

            // Act
            Escala escala = EscalaBuilder.builder()
                    .nome(NOME_ESCALA)
                    .mesReferencia(mesReferencia)
                    .lider(lider)
                    .setor(setor)
                    .adicionarPlantao(plantao)
                    .build();

            // Assert
            assertThat(plantao.getEscala()).isSameAs(escala);
        }
    }

    // =========================================================================
    // API fluente — cada método retorna this
    // =========================================================================

    @Nested
    @DisplayName("API fluente")
    class ApiFluente {

        @Test
        @DisplayName("nome() deve retornar a mesma instância do builder")
        void nomeDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.nome(NOME_ESCALA)).isSameAs(builder);
        }

        @Test
        @DisplayName("mesReferencia() deve retornar a mesma instância do builder")
        void mesReferenciaDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.mesReferencia(mesReferencia)).isSameAs(builder);
        }

        @Test
        @DisplayName("lider() deve retornar a mesma instância do builder")
        void liderDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.lider(lider)).isSameAs(builder);
        }

        @Test
        @DisplayName("setor() deve retornar a mesma instância do builder")
        void setorDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.setor(setor)).isSameAs(builder);
        }

        @Test
        @DisplayName("plantoes() deve retornar a mesma instância do builder")
        void plantoesDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.plantoes(List.of())).isSameAs(builder);
        }

        @Test
        @DisplayName("adicionarPlantao() deve retornar a mesma instância do builder")
        void adicionarPlantaoDeveRetornarMesmaInstancia() {
            EscalaBuilder builder = EscalaBuilder.builder();
            assertThat(builder.adicionarPlantao(new Plantao())).isSameAs(builder);
        }
    }

    private Escala buildValido() {
        return EscalaBuilder.builder()
                .nome(NOME_ESCALA)
                .mesReferencia(mesReferencia)
                .lider(lider)
                .setor(setor)
                .build();
    }
}