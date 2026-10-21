package web.pulso.entity.plantao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Escala;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;

import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Plantão")
public class PlantaoTest {

        private Plantao plantao;

        @BeforeEach
        void setUp() {

                plantao = new Plantao();

                plantao.setProfissionaisCombinados(new HashSet<>());

                plantao.setProfissionaisConfirmados(new HashSet<>());
        }

        private Profissional criarProfissional(Long id) {

                Profissional profissional = new Profissional();

                profissional.setId(id);

                return profissional;
        }

        private Instituicao criarInstituicao(Long id) {

                Instituicao instituicao = new Instituicao();

                instituicao.setId(id);

                return instituicao;
        }

        private Escala criarEscala(StatusEscala status, Instituicao instituicao) {

                Setor setor = new Setor();

                setor.setInstituicao(instituicao);

                Escala escala = new Escala();

                escala.setStatus(status);

                escala.setSetor(setor);

                return escala;
        }

        // ==================== getProfissionaisAlocados ====================

        /**
         * Método: getProfissionaisAlocados()
         *
         * CE (Classes de Equivalência):
         * Válidas
         * CEV1: Possui profissionais combinados e confirmados -> retorna união
         * CEV2: Sem profissionais -> retorna vazio
         * CEV3: Mesmo profissional nas duas listas -> não duplica
         *
         * Inválidas:
         * CEI1: Tentativa de modificar retorno da lista set ->
         * UnsupportedOperationException
         *
         * VL (Valores Limite):
         * VL1: 0 profissionais -> vazio
         * VL2: 1 profissional -> tamanho 1
         */

        @Test
        @DisplayName("Deve retornar união de profissionais combinados e confirmados")
        void deveRetornarUniaoDeProfissionaisCombinadosEConfirmados() {

                Profissional combinado = criarProfissional(1L);

                Profissional confirmado = criarProfissional(2L);

                plantao.getProfissionaisCombinados().add(combinado);

                plantao.getProfissionaisConfirmados().add(confirmado);

                Set<Profissional> resultado = plantao.getProfissionaisAlocados();

                assertThat(resultado)
                                .containsExactlyInAnyOrder(combinado, confirmado);
        }

        @Test
        @DisplayName("Deve retornar conjunto vazio quando não houver profissionais vinculados")
        void deveRetornarConjuntoVazioQuandoNaoHouverProfissionaisVinculados() {

                Set<Profissional> resultado = plantao.getProfissionaisAlocados();

                assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Não deve duplicar profissional presente nas duas coleções")
        void naoDeveDuplicarProfissionalPresenteNasDuasColecoes() {

                Profissional profissional = criarProfissional(1L);

                plantao.getProfissionaisCombinados().add(profissional);

                plantao.getProfissionaisConfirmados().add(profissional);

                Set<Profissional> resultado = plantao.getProfissionaisAlocados();

                assertThat(resultado)
                                .hasSize(1)
                                .contains(profissional);
        }

        @Test
        @DisplayName("Deve retornar conjunto imutável")
        void deveRetornarConjuntoImutavel() {

                Set<Profissional> resultado = plantao.getProfissionaisAlocados();

                assertThatThrownBy(() -> resultado.add(criarProfissional(99L)))
                                .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Deve retornar conjunto contendo apenas um profissional")
        void deveRetornarConjuntoComApenasUmProfissional() {

                Profissional profissional = criarProfissional(1L);

                plantao.getProfissionaisCombinados().add(profissional);

                Set<Profissional> resultado = plantao.getProfissionaisAlocados();

                assertThat(resultado)
                                .hasSize(1)
                                .contains(profissional);
        }

        // ==================== validarPermissaoEditarComentario ====================

        /**
         * Método: validarPermissaoEditarComentario()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional líder em escala RASCUNHO -> permite edição
         *
         * Inválidas:
         * CEI1: Profissional não líder -> ForbiddenException
         * CEI2: Escala PUBLICADA -> ConflictException
         *
         * VL:
         * VL1: Instituições de liderança vazias -> sem permissão
         */

        @Test
        @DisplayName("Deve permitir edição quando profissional for líder e escala estiver em rascunho")
        void devePermitirEdicaoQuandoProfissionalForLiderERascunho() {

                Instituicao instituicao = criarInstituicao(1L);

                Escala escala = criarEscala(StatusEscala.RASCUNHO, instituicao);

                plantao.setEscala(escala);

                Profissional profissional = criarProfissional(1L);

                profissional.setInstituicoesLiderEscala(
                                List.of(instituicao));

                assertThatCode(() -> plantao.validarPermissaoEditarComentario(profissional)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Deve lançar ForbiddenException quando profissional não for líder")
        void deveLancarForbiddenExceptionQuandoProfissionalNaoForLider() {

                Instituicao instituicao = criarInstituicao(1L);

                Escala escala = criarEscala(StatusEscala.RASCUNHO, instituicao);

                plantao.setEscala(escala);

                Profissional profissional = criarProfissional(1L);

                profissional.setInstituicoesLiderEscala(List.of());

                assertThatThrownBy(() -> plantao.validarPermissaoEditarComentario(profissional))
                                .isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando escala estiver publicada")
        void deveLancarConflictExceptionQuandoEscalaEstiverPublicada() {

                Instituicao instituicao = criarInstituicao(1L);

                Escala escala = criarEscala(StatusEscala.PUBLICADA, instituicao);

                plantao.setEscala(escala);

                Profissional profissional = criarProfissional(1L);

                profissional.setInstituicoesLiderEscala(
                                List.of(instituicao));

                assertThatThrownBy(() -> plantao.validarPermissaoEditarComentario(profissional))
                                .isInstanceOf(ConflictException.class);
        }

        // ==================== verificarProfissionalJaVinculado ====================

        /**
         * Método: verificarProfissionalJaVinculado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional ainda não vinculado -> não lança exceção
         *
         * Inválidas:
         * CEI1: Profissional já confirmado -> ConflictException
         * CEI2: Profissional já combinado -> ConflictException
         *
         * VL:
         * VL1: Primeiro vínculo do profissional -> permitido
         * VL2: Profissional já presente em uma coleção -> bloqueado
         */

        @Test
        @DisplayName("Não deve lançar exceção quando profissional não estiver vinculado")
        void naoDeveLancarExcecaoQuandoProfissionalNaoEstiverVinculado() {

                Profissional profissional = criarProfissional(1L);

                assertThatCode(() -> plantao.verificarProfissionalJaVinculado(profissional)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando profissional já estiver confirmado")
        void deveLancarConflictExceptionQuandoProfissionalJaEstiverConfirmado() {

                Profissional profissional = criarProfissional(1L);

                plantao.getProfissionaisConfirmados().add(profissional);

                assertThatThrownBy(() -> plantao.verificarProfissionalJaVinculado(profissional))
                                .isInstanceOf(ConflictException.class);
        }

        @Test
        @DisplayName("Deve lançar ConflictException quando profissional já estiver combinado")
        void deveLancarConflictExceptionQuandoProfissionalJaEstiverCombinado() {

                Profissional profissional = criarProfissional(1L);

                plantao.getProfissionaisCombinados().add(profissional);

                assertThatThrownBy(() -> plantao.verificarProfissionalJaVinculado(profissional))
                                .isInstanceOf(ConflictException.class);
        }

        // ==================== adicionarProfissionalCombinado ====================

        /**
         * Método: adicionarProfissionalCombinado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional válido -> adiciona vínculo bidirecional profissional <->
         * plantão
         *
         * Inválidas:
         * CEI1: Profissional nulo -> não adiciona e não lança exceção
         *
         * VL:
         * VL1: Primeiro profissional adicionado -> coleção tamanho 1
         */

        @Test
        @DisplayName("Deve adicionar profissional combinado")
        void deveAdicionarProfissionalCombinado() {

                Profissional profissional = criarProfissional(1L);

                profissional.setPlantoesCombinados(new ArrayList<>());

                plantao.adicionarProfissionalCombinado(profissional);

                assertThat(plantao.getProfissionaisCombinados())
                                .contains(profissional);

                assertThat(profissional.getPlantoesCombinados())
                                .contains(plantao);
        }

        @Test
        @DisplayName("Não deve adicionar profissional quando profissional for nulo")
        void naoDeveAdicionarProfissionalQuandoProfissionalForNulo() {

                plantao.adicionarProfissionalCombinado(null);

                assertThat(plantao.getProfissionaisCombinados())
                                .isEmpty();
        }

        // ==================== removerProfissionalCombinado ====================

        /**
         * Método: removerProfissionalCombinado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional existente na coleção -> remove vínculo
         *
         * Inválidas:
         * CEI1: Profissional nulo -> não remove e não lança exceção
         *
         * VL:
         * VL1: Último profissional removido -> coleção vazia
         */

        @Test
        @DisplayName("Deve remover profissional combinado")
        void deveRemoverProfissionalCombinado() {

                Profissional profissional = criarProfissional(1L);

                profissional.setPlantoesCombinados(new ArrayList<>());

                plantao.getProfissionaisCombinados().add(profissional);

                profissional.getPlantoesCombinados().add(plantao);

                plantao.removerProfissionalCombinado(profissional);

                assertThat(plantao.getProfissionaisCombinados())
                                .doesNotContain(profissional);

                assertThat(profissional.getPlantoesCombinados())
                                .doesNotContain(plantao);
        }

        @Test
        @DisplayName("Não deve remover profissional quando profissional for nulo")
        void naoDeveRemoverProfissionalQuandoProfissionalForNulo() {

                plantao.removerProfissionalCombinado(null);

                assertThat(plantao.getProfissionaisCombinados())
                                .isEmpty();
        }

        // ==================== adicionarProfissionalConfirmado ====================

        /**
         * Método: adicionarProfissionalConfirmado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional válido -> adiciona vínculo bidirecional
         *
         * Inválidas:
         * CEI1: Profissional nulo -> NullPointerException
         *
         * VL:
         * VL1: Primeiro profissional confirmado -> coleção tamanho 1
         */
        @Test
        @DisplayName("Deve adicionar profissional confirmado")
        void deveAdicionarProfissionalConfirmado() {

                Profissional profissional = criarProfissional(1L);

                profissional.setPlantoesConfirmados(new ArrayList<>());

                plantao.adicionarProfissionalConfirmado(profissional);

                assertThat(plantao.getProfissionaisConfirmados())
                                .contains(profissional);

                assertThat(profissional.getPlantoesConfirmados())
                                .contains(plantao);
        }

        @Test
        @DisplayName("Deve lançar NullPointerException quando profissional for nulo")
        void deveLancarNullPointerExceptionQuandoProfissionalForNulo() {

                assertThatThrownBy(() -> plantao.adicionarProfissionalConfirmado(null))
                                .isInstanceOf(NullPointerException.class);
        }

        // ==================== removerProfissionalConfirmado ====================

        /**
         * Método: removerProfissionalConfirmado()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: Profissional presente na coleção -> remove vínculo
         *
         * Inválidas:
         * CEI1: Profissional nulo -> NullPointerException
         *
         * VL:
         * VL1: Último profissional removido -> coleção vazia
         */

        @Test
        @DisplayName("Deve remover profissional confirmado")
        void deveRemoverProfissionalConfirmado() {

                Profissional profissional = criarProfissional(1L);

                profissional.setPlantoesConfirmados(new ArrayList<>());

                plantao.getProfissionaisConfirmados().add(profissional);

                profissional.getPlantoesConfirmados().add(plantao);

                plantao.removerProfissionalConfirmado(profissional);

                assertThat(plantao.getProfissionaisConfirmados())
                                .doesNotContain(profissional);

                assertThat(profissional.getPlantoesConfirmados())
                                .doesNotContain(plantao);
        }

        @Test
        @DisplayName("Deve lançar NullPointerException quando profissional for nulo")
        void deveLancarNullPointerExceptionAoRemoverProfissionalNulo() {

                assertThatThrownBy(() -> plantao.removerProfissionalConfirmado(null))
                                .isInstanceOf(NullPointerException.class);
        }

        // ==================== mesmaEscalaId ====================

        /**
         * Método: mesmaEscalaId()
         *
         * CE (Classes de Equivalência):
         *
         * Válidas:
         * CEV1: escalaId igual ao da escala do plantão -> retorna true
         *
         * Inválidas:
         * CEI1: escalaId diferente -> retorna false
         *
         * VL:
         * VL1: Comparação com mesmo valor
         * VL2: Comparação com valor diferente
         */

        @Test
        @DisplayName("Deve retornar true quando escalaId for igual")
        void deveRetornarTrueQuandoEscalaIdForIgual() {

                Instituicao instituicao = criarInstituicao(1L);

                Escala escala = criarEscala(StatusEscala.RASCUNHO, instituicao);

                escala.setId(1L);

                plantao.setEscala(escala);

                boolean resultado = plantao.mesmaEscalaId(1L);

                assertThat(resultado).isTrue();
        }

        @Test
        @DisplayName("Deve retornar false quando escalaId for diferente")
        void deveRetornarFalseQuandoEscalaIdForDiferente() {

                Instituicao instituicao = criarInstituicao(1L);

                Escala escala = criarEscala(StatusEscala.RASCUNHO, instituicao);

                escala.setId(1L);

                plantao.setEscala(escala);

                boolean resultado = plantao.mesmaEscalaId(2L);

                assertThat(resultado).isFalse();
        }

}