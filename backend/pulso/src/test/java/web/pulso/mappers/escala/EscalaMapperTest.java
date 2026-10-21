package web.pulso.mappers.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.pulso.dto.response.escala.EscalaResponseDTO;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.entity.*;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.mappers.PlantaoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaMapper")
class EscalaMapperTest {

    @Mock
    private PlantaoMapper plantaoMapper;

    private EscalaMapper escalaMapper;

    private static final Long   ID_ESCALA    = 1L;
    private static final String NOME_ESCALA  = "Escala Fevereiro";
    private static final String NOME_LIDER   = "Dr. João";
    private static final String FOTO_LIDER   = "https://foto.com/joao.jpg";
    private static final String NOME_SETOR   = "UTI";
    private static final String NOME_INST    = "Hospital Central";

    private LocalDate     mesReferencia;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @BeforeEach
    void setUp() {
        escalaMapper = new EscalaMapper(null, null, plantaoMapper);

        mesReferencia    = LocalDate.now().plusMonths(2).withDayOfMonth(1);
        dataCriacao      = LocalDateTime.now().minusDays(2);
        dataAtualizacao  = LocalDateTime.now().minusDays(1);
    }

    @Nested
    @DisplayName("toResponseDTO")
    class ToResponseDTO {

        @Test
        @DisplayName("[CE-nulo] Deve retornar null quando escala for nula")
        void deveRetornarNullQuandoEscalaForNula() {
            assertThat(escalaMapper.toResponseDTO(null)).isNull();
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear campos diretos corretamente")
        void deveMapearCamposDiretosCorretamente() {
            // Arrange
            Escala escala = buildEscalaBase();

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(ID_ESCALA);
            assertThat(dto.getNome()).isEqualTo(NOME_ESCALA);
            assertThat(dto.getMesReferencia()).isEqualTo(mesReferencia);
            assertThat(dto.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
        }

        @Test
        @DisplayName("[CE-setor-nulo] Deve retornar DTO sem dados de setor quando setor for nulo")
        void deveRetornarDtoSemSetorQuandoSetorForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setSetor(null);

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeSetor()).isNull();
            assertThat(dto.getEspecialidadeSetor()).isNull();
            assertThat(dto.getNomeInstituicao()).isNull();
        }

        @Test
        @DisplayName("[CE-setor-valido] Deve mapear nomeSetor e especialidadeSetor quando setor não for nulo")
        void deveMapearSetorQuandoNaoForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setSetor(buildSetor(NOME_SETOR, Especialidade.values()[0], null));

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeSetor()).isEqualTo(NOME_SETOR);
            assertThat(dto.getEspecialidadeSetor()).isEqualTo(Especialidade.values()[0]);
            assertThat(dto.getNomeInstituicao()).isNull(); // instituicao ainda nula
        }

        @Test
        @DisplayName("[CE-instituicao-nula] Deve retornar nomeInstituicao nulo quando instituicao do setor for nula")
        void deveRetornarInstituicaoNulaQuandoInstituicaoForNula() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setSetor(buildSetor(NOME_SETOR, Especialidade.values()[0], null));

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeInstituicao()).isNull();
        }

        @Test
        @DisplayName("[CE-instituicao-valida] Deve mapear nomeInstituicao quando instituicao do setor não for nula")
        void deveMapearInstituicaoQuandoNaoForNula() {
            // Arrange
            Instituicao instituicao = new Instituicao();
            instituicao.setNome(NOME_INST);

            Escala escala = buildEscalaBase();
            escala.setSetor(buildSetor(NOME_SETOR, Especialidade.values()[0], instituicao));

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeInstituicao()).isEqualTo(NOME_INST);
        }

        @Test
        @DisplayName("[CE-lider-nulo] Deve retornar DTO sem dados de lider quando liderEscala for nulo")
        void deveRetornarDtoSemLiderQuandoLiderForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(null);

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isNull();
            assertThat(dto.getFotoLider()).isNull();
        }

        @Test
        @DisplayName("[CE-lider-valido] Deve mapear nomeLider e fotoLider quando liderEscala não for nulo")
        void deveMapearLiderQuandoNaoForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(buildProfissional(NOME_LIDER, FOTO_LIDER));

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isEqualTo(FOTO_LIDER);
        }

        @Test
        @DisplayName("[CE-lider-valido] Deve mapear nomeLider mesmo quando fotoLider for nula")
        void deveMapearNomeLiderMesmoComFotoNula() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(buildProfissional(NOME_LIDER, null));

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isNull();
        }

        @Test
        @DisplayName("[CE-plantoes-nulos] Deve retornar DTO sem plantoes quando lista de plantoes for nula")
        void deveRetornarDtoSemPlantoesQuandoListaForNula() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setPlantoes(null);

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getPlantoes()).isNull();
            verifyNoInteractions(plantaoMapper);
        }

        @Test
        @DisplayName("[CE-plantoes-vazios] Deve retornar lista vazia quando lista de plantoes estiver vazia")
        void deveRetornarListaVaziaQuandoPlantoesEstiverVazia() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setPlantoes(List.of());

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getPlantoes()).isNotNull().isEmpty();
            verifyNoInteractions(plantaoMapper);
        }

        @Test
        @DisplayName("[CE-plantoes-validos] Deve mapear cada plantao via plantaoMapper quando lista não for nula")
        void deveMapearCadaPlantaoViaPlantaoMapper() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();

            Escala escala = buildEscalaBase();
            escala.setPlantoes(List.of(p1, p2));

            var plantaoDTO = new Object();
            when(plantaoMapper.toEscalaSimplesDTO(any(Plantao.class)))
                    .thenAnswer(inv -> plantaoDTO);

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getPlantoes()).hasSize(2);
            verify(plantaoMapper, times(2)).toEscalaSimplesDTO(any(Plantao.class));
        }

        @Test
        @DisplayName("[CE-plantoes-validos] Deve chamar plantaoMapper com cada instância de plantao correta")
        void deveChamarPlantaoMapperComCadaInstanciaCorreta() {
            // Arrange
            Plantao p1 = new Plantao();
            Plantao p2 = new Plantao();

            Escala escala = buildEscalaBase();
            escala.setPlantoes(List.of(p1, p2));

            when(plantaoMapper.toEscalaSimplesDTO(any(Plantao.class)))
                    .thenReturn(null);

            // Act
            escalaMapper.toResponseDTO(escala);

            // Assert — cada instância de plantão foi passada ao mapper
            verify(plantaoMapper).toEscalaSimplesDTO(p1);
            verify(plantaoMapper).toEscalaSimplesDTO(p2);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear todos os campos quando escala estiver completamente preenchida")
        void deveMapearTodosOsCamposComEscalaCompleta() {
            // Arrange
            Instituicao instituicao = new Instituicao();
            instituicao.setNome(NOME_INST);

            Escala escala = buildEscalaBase();
            escala.setSetor(buildSetor(NOME_SETOR, Especialidade.values()[0], instituicao));
            escala.setLiderEscala(buildProfissional(NOME_LIDER, FOTO_LIDER));
            escala.setPlantoes(List.of(new Plantao()));

            when(plantaoMapper.toEscalaSimplesDTO(any(Plantao.class)))
                    .thenReturn(null);

            // Act
            EscalaResponseDTO dto = escalaMapper.toResponseDTO(escala);

            // Assert
            assertThat(dto.getId()).isEqualTo(ID_ESCALA);
            assertThat(dto.getNome()).isEqualTo(NOME_ESCALA);
            assertThat(dto.getMesReferencia()).isEqualTo(mesReferencia);
            assertThat(dto.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
            assertThat(dto.getNomeSetor()).isEqualTo(NOME_SETOR);
            assertThat(dto.getEspecialidadeSetor()).isEqualTo(Especialidade.values()[0]);
            assertThat(dto.getNomeInstituicao()).isEqualTo(NOME_INST);
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isEqualTo(FOTO_LIDER);
            assertThat(dto.getPlantoes()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("toSimpleResponseDTO")
    class ToSimpleResponseDTO {

        @Test
        @DisplayName("[CE-nulo] Deve retornar null quando escala for nula")
        void deveRetornarNullQuandoEscalaForNula() {
            assertThat(escalaMapper.toSimpleResponseDTO(null)).isNull();
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear campos diretos corretamente")
        void deveMapearCamposDiretosCorretamente() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setDataCriacao(dataCriacao);
            escala.setDataAtualizacao(dataAtualizacao);

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(ID_ESCALA);
            assertThat(dto.getNome()).isEqualTo(NOME_ESCALA);
            assertThat(dto.getMesReferencia()).isEqualTo(mesReferencia);
            assertThat(dto.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
            assertThat(dto.getDataCriacao()).isEqualTo(dataCriacao);
            assertThat(dto.getDataAtualizacao()).isEqualTo(dataAtualizacao);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear corretamente quando dataCriacao e dataAtualizacao forem nulas")
        void deveMapearQuandoDatasForemNulas() {
            // Arrange — entity recém criada sem datas preenchidas
            Escala escala = buildEscalaBase();

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto.getDataCriacao()).isNull();
            assertThat(dto.getDataAtualizacao()).isNull();
        }

        @Test
        @DisplayName("[CE-lider-nulo] Deve retornar DTO sem dados de lider quando liderEscala for nulo")
        void deveRetornarDtoSemLiderQuandoLiderForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(null);

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isNull();
            assertThat(dto.getFotoLider()).isNull();
        }

        @Test
        @DisplayName("[CE-lider-valido] Deve mapear nomeLider e fotoLider quando liderEscala não for nulo")
        void deveMapearLiderQuandoNaoForNulo() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(buildProfissional(NOME_LIDER, FOTO_LIDER));

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isEqualTo(FOTO_LIDER);
        }

        @Test
        @DisplayName("[CE-lider-valido] Deve mapear nomeLider mesmo quando fotoLider for nula")
        void deveMapearNomeLiderMesmoComFotoNula() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(buildProfissional(NOME_LIDER, null));

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isNull();
        }

        @Test
        @DisplayName("[CE-colaboracao] Não deve interagir com plantaoMapper ao serializar versão simples")
        void naoDeveInteragirComPlantaoMapper() {
            // Arrange
            Escala escala = buildEscalaBase();

            // Act
            escalaMapper.toSimpleResponseDTO(escala);

            // Assert — toSimpleResponseDTO não mapeia plantões
            verifyNoInteractions(plantaoMapper);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear todos os campos quando escala estiver completamente preenchida")
        void deveMapearTodosOsCamposComEscalaCompleta() {
            // Arrange
            Escala escala = buildEscalaBase();
            escala.setLiderEscala(buildProfissional(NOME_LIDER, FOTO_LIDER));
            escala.setDataCriacao(dataCriacao);
            escala.setDataAtualizacao(dataAtualizacao);

            // Act
            EscalaSimpleResponseDTO dto = escalaMapper.toSimpleResponseDTO(escala);

            // Assert
            assertThat(dto.getId()).isEqualTo(ID_ESCALA);
            assertThat(dto.getNome()).isEqualTo(NOME_ESCALA);
            assertThat(dto.getMesReferencia()).isEqualTo(mesReferencia);
            assertThat(dto.getStatus()).isEqualTo(StatusEscala.RASCUNHO);
            assertThat(dto.getDataCriacao()).isEqualTo(dataCriacao);
            assertThat(dto.getDataAtualizacao()).isEqualTo(dataAtualizacao);
            assertThat(dto.getNomeLider()).isEqualTo(NOME_LIDER);
            assertThat(dto.getFotoLider()).isEqualTo(FOTO_LIDER);
        }
    }


    private Escala buildEscalaBase() {
        Escala escala = new Escala();
        escala.setId(ID_ESCALA);
        escala.setNome(NOME_ESCALA);
        escala.setMesReferencia(mesReferencia);
        escala.setStatus(StatusEscala.RASCUNHO);
        escala.setSetor(null);
        escala.setLiderEscala(null);
        escala.setPlantoes(null);
        return escala;
    }

    private Setor buildSetor(String nome, Especialidade especialidade, Instituicao instituicao) {
        Setor setor = new Setor();
        setor.setNome(nome);
        setor.setEspecialidade(especialidade);
        setor.setInstituicao(instituicao);
        return setor;
    }

    private Profissional buildProfissional(String nome, String foto) {
        Profissional profissional = new Profissional();
        profissional.setNome(nome);
        profissional.setFotoPerfil(foto);
        return profissional;
    }
}