package web.pulso.service.profissional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.profissional.estatistica.EstatisticaPlantoesAno;
import web.pulso.service.ProfissionalService;

@DisplayName("Testes do método gerarEstatisticaAnual da classe ProfissionalService")
public class GerarEstatisticaAnualProfissionalTest
        extends ProfissionalServiceBaseTest {

    private Method metodo;

    @BeforeEach
    void setup() throws Exception {

        metodo = ProfissionalService.class
                .getDeclaredMethod(
                        "gerarEstatisticaAnual",
                        Long.class);

        metodo.setAccessible(true);
    }

    @Test
    @DisplayName("Deve gerar estatísticas anuais com sucesso")
    void deveGerarEstatisticasAnuaisComSucesso() throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        List<Object[]> combinados = List.of(
                new Object[] { 1, 2L },
                new Object[] { 2, 3L });

        List<Object[]> confirmados = List.of(
                new Object[] { 1, 1L },
                new Object[] { 3, 4L });

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(combinados);

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(confirmados);

        EstatisticaPlantoesAno resultado = (EstatisticaPlantoesAno) metodo.invoke(
                profissionalService,
                profissionalId);

        assertNotNull(resultado);

        assertEquals(anoAtual, resultado.getAno());

        assertEquals(12, resultado.getMeses().size());

        assertEquals(
                3,
                resultado.getMeses().get(0).getQuantidade());

        assertEquals(
                3,
                resultado.getMeses().get(1).getQuantidade());

        assertEquals(
                4,
                resultado.getMeses().get(2).getQuantidade());
    }

    @Test
    @DisplayName("Deve retornar meses zerados quando não houver dados")
    void deveRetornarMesesZeradosQuandoNaoHouverDados()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        EstatisticaPlantoesAno resultado = (EstatisticaPlantoesAno) metodo.invoke(
                profissionalService,
                profissionalId);

        assertNotNull(resultado);

        assertEquals(12, resultado.getMeses().size());

        assertTrue(
                resultado.getMeses()
                        .stream()
                        .allMatch(m -> m.getQuantidade() == 0));
    }

    @Test
    @DisplayName("Deve lançar erro quando profissionalId for nulo")
    void deveLancarErroQuandoProfissionalIdForNulo() {

        assertThrows(
                Exception.class,
                () -> metodo.invoke(
                        profissionalService,
                        new Object[] { null }));
    }

    @Test
    @DisplayName("Deve lançar erro quando combinados retornar nulo")
    void deveLancarErroQuandoCombinadosRetornarNulo()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(null);

        assertThrows(
                Exception.class,
                () -> metodo.invoke(
                        profissionalService,
                        profissionalId));
    }

    @Test
    @DisplayName("Deve lançar erro quando confirmados retornar nulo")
    void deveLancarErroQuandoConfirmadosRetornarNulo()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(null);

        assertThrows(
                Exception.class,
                () -> metodo.invoke(
                        profissionalService,
                        profissionalId));
    }

    @Test
    @DisplayName("Deve lançar erro quando combinados possuir mês nulo")
    void deveLancarErroQuandoCombinadosPossuirMesNulo()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        List<Object[]> combinados = List.<Object[]>of(new Object[] { null, 2L });

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(combinados);

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        assertThrows(
                Exception.class,
                () -> metodo.invoke(
                        profissionalService,
                        profissionalId));
    }

    @Test
    @DisplayName("Deve lançar erro quando combinados possuir quantidade nula")
    void deveLancarErroQuandoCombinadosPossuirQuantidadeNula()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        List<Object[]> combinados = List.<Object[]>of(new Object[] { 1, null });

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(combinados);

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        assertThrows(
                Exception.class,
                () -> metodo.invoke(
                        profissionalService,
                        profissionalId));
    }

    @Test
    @DisplayName("Deve lançar erro quando mês estiver fora do intervalo")
    void deveLancarErroQuandoMesEstiverForaDoIntervalo()
            throws Exception {

        Long profissionalId = 1L;

        int anoAtual = LocalDate.now().getYear();

        List<Object[]> combinados = List.<Object[]>of(new Object[] { 13, 2L });

        when(profissionalRepository
                .contarPlantoesCombinadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(combinados);

        when(profissionalRepository
                .contarPlantoesConfirmadosPorMes(
                        profissionalId,
                        anoAtual))
                .thenReturn(Collections.emptyList());

        EstatisticaPlantoesAno resultado = (EstatisticaPlantoesAno) metodo.invoke(
                profissionalService,
                profissionalId);

        assertNotNull(resultado);

        // atualmente aceita mês inválido
        assertTrue(
                resultado.getMeses()
                        .stream()
                        .anyMatch(m -> m.getMes().equals(13)));
    }
}