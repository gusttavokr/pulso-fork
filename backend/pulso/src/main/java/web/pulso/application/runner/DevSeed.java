package web.pulso.application.runner;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import web.pulso.dto.request.*;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;
import web.pulso.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Profile({"!test","!prod"})
public class DevSeed implements ApplicationRunner {
        private final UsuarioService usuarioService;
        private final SetorService setorService;
        private final EscalaService escalaService;
        private final InstituicaoService instituicaoService;
        private final PlantaoService plantaoService;

        public DevSeed(UsuarioService usuarioService, SetorService setorService, PlantaoService plantaoService,
                        EscalaService escalaService, InstituicaoService instituicaoService) {
                this.usuarioService = usuarioService;
                this.setorService = setorService;
                this.escalaService = escalaService;
                this.instituicaoService = instituicaoService;
                this.plantaoService = plantaoService;
        }

        @Override
        @Transactional
        public void run(ApplicationArguments args) {

                log.info("🚀 Iniciando SeedRunner");

                try {
                        log.info("➡️ criando usuarios");
                        create_users();
                        log.info("✅ usuarios criados");
                } catch (Exception e) {
                        log.error("💥 ERRO ao criar usuarios", e);
                        throw e;
                }

                try {
                        log.info("➡️ criando setores");
                        create_setor();
                        log.info("✅ setores criados");
                } catch (Exception e) {
                        log.error("💥 ERRO ao criar setores", e);
                        throw e;
                }

                try {
                        log.info("➡️ vinculando profissionais e instituicoes");
                        create_vinculos();
                        log.info("✅ vinculados com sucesso");
                } catch (Exception e) {
                        log.error("💥 ERRO ao vincular", e);
                        throw e;
                }

                try {
                        log.info("➡️ vinculando lideres de escala");
                        create_vinculos_lider_escala();
                        log.info("✅ lideres de escala vinculados com sucesso");
                } catch (Exception e) {
                        log.error("💥 ERRO ao vincular lideres de escala", e);
                        throw e;
                }

                try {
                        log.info("➡️ criando escalas");
                        create_escala();
                        log.info("✅ escalas criadas");
                } catch (Exception e) {
                        log.error("💥 ERRO ao criar escalas", e);
                        throw e;
                }

                log.info("SeedRunner FINALIZADO COM SUCESSO");
        }

        private void create_users() {

                List<CadastroRequestDTO> cadastros = List.of(

                        // =========================
                        // LÍDERES DE ESCALA (3)
                        // =========================
                        new CadastroRequestDTO(
                                "lider1@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Lucas Almeida",
                                        List.of("lucas.almeida@pulso.dev"),
                                        "+5511999991111",
                                        "https://cdn.pixabay.com/photo/2018/03/17/12/12/portrait-3233851_1280.jpg",
                                        LocalDate.of(2000, 12, 1),
                                        "lucas.almeida@pix.com",
                                        TipoPix.CPF,
                                        "12345",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 3, 10),
                                        Set.of(Especialidade.CLINICA_GERAL, Especialidade.ANESTESIOLOGIA)),
                                null),

                        new CadastroRequestDTO(
                                "lider2@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Marina Costa",
                                        List.of("marina.costa@pulso.dev"),
                                        "+5511988882222",
                                        "https://fotografiadicas.com.br/wp-content/uploads/2017/10/15-Como-fotografar-pessoas-Fotografia-Dicas.jpg",
                                        LocalDate.of(2000, 6, 1),
                                        "marina.costa@pix.com",
                                        TipoPix.CPF,
                                        "23456",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 7, 22),
                                        Set.of(Especialidade.CARDIOLOGIA)),
                                null),

                        new CadastroRequestDTO(
                                "lider3@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Rafael Nunes",
                                        List.of("rafael.nunes@pulso.dev"),
                                        "+5511977773333",
                                        "https://www.agendartecultura.com.br/wp-content/uploads/2022/12/meneson.jpg",
                                        LocalDate.of(2000, 1, 1),
                                        "rafael.nunes@pix.com",
                                        TipoPix.CPF,
                                        "34567",
                                        Set.of(UF.MG),
                                        LocalDate.of(1945, 11, 5),
                                        Set.of(Especialidade.ORTOPEDIA)),
                                null),

                        // =========================
                        // MÉDICOS (30)
                        // =========================
                        new CadastroRequestDTO(
                                "medico1@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Ana Souza",
                                        List.of("ana.souza@pulso.dev"),
                                        "+5511966664444",
                                        "https://www.tribunapr.com.br/wp-content/uploads/2020/08/16145536/jovem-feliz-freepik-ArthurHidden-1.jpg",
                                        LocalDate.of(2000, 12, 1),
                                        "ana.souza@pix.com",
                                        TipoPix.CPF,
                                        "45678",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 4, 18),
                                        Set.of(Especialidade.PEDIATRIA)),
                                null),

                        new CadastroRequestDTO(
                                "medico2@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Bruno Lima",
                                        List.of("bruno.lima@pulso.dev"),
                                        "+5511955555555",
                                        "https://thumbs.dreamstime.com/b/retrato-normal-real-da-pessoa-22299703.jpg",
                                        LocalDate.of(2000, 12, 1),
                                        "bruno.lima@pix.com",
                                        TipoPix.CPF,
                                        "56789",
                                        Set.of(UF.PR),
                                        LocalDate.of(1945, 9, 9),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico3@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Paula Mendes",
                                        List.of("paula.mendes@pulso.dev"),
                                        "+5511944446666",
                                        "https://d84b69db4b5196db9928-e5549dd2315da8dbd494b1ca4b47d4b8.ssl.cf1.rackcdn.com/PostImagem/13672/eu-fotografo-para-pessoas-especiais_513a6639-editar.jpg",
                                        LocalDate.of(2000, 12, 1),
                                        "paula.mendes@pix.com",
                                        TipoPix.TELEFONE,
                                        "67890",
                                        Set.of(UF.SC),
                                        LocalDate.of(1945, 1, 30),
                                        Set.of(Especialidade.CARDIOLOGIA)),
                                null),

                        new CadastroRequestDTO(
                                "medico4@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Diego Rocha",
                                        List.of("diego.rocha@pulso.dev"),
                                        "+5511933337777",
                                        "https://tse4.mm.bing.net/th/id/OIP.9vFtzwrfkSgr1ISGmTIR-AHaF7?rs=1&pid=ImgDetMain&o=7&rm=3",
                                        LocalDate.of(2000, 12, 1),
                                        "diego.rocha@pix.com",
                                        TipoPix.CPF,
                                        "78901",
                                        Set.of(UF.BA),
                                        LocalDate.of(1945, 6, 14),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico5@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Fabiana Costa",
                                        List.of("fabiana.costa@pulso.dev"),
                                        "+5511961112222",
                                        "https://randomuser.me/api/portraits/women/1.jpg",
                                        LocalDate.of(2000, 5, 1),
                                        "fabiana.costa@pix.com",
                                        TipoPix.CPF,
                                        "89012",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 2, 14),
                                        Set.of(Especialidade.NEUROLOGIA)),
                                null),

                        new CadastroRequestDTO(
                                "medico6@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Carlos Mendes",
                                        List.of("carlos.mendes@pulso.dev"),
                                        "+5511977778888",
                                        "https://randomuser.me/api/portraits/men/2.jpg",
                                        LocalDate.of(2000, 7, 1),
                                        "carlos.mendes@pix.com",
                                        TipoPix.CPF,
                                        "90123",
                                        Set.of(UF.MG),
                                        LocalDate.of(1945, 12, 20),
                                        Set.of(Especialidade.CARDIOLOGIA)),
                                null),

                        new CadastroRequestDTO(
                                "medico7@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Juliana Silva",
                                        List.of("juliana.silva@pulso.dev"),
                                        "+5511988889999",
                                        "https://randomuser.me/api/portraits/women/3.jpg",
                                        LocalDate.of(2000, 3, 1),
                                        "juliana.silva@pix.com",
                                        TipoPix.CPF,
                                        "01234",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 9, 10),
                                        Set.of(Especialidade.PEDIATRIA)),
                                null),

                        new CadastroRequestDTO(
                                "medico8@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Ricardo Lima",
                                        List.of("ricardo.lima@pulso.dev"),
                                        "+5511965554444",
                                        "https://randomuser.me/api/portraits/men/4.jpg",
                                        LocalDate.of(2000, 11, 1),
                                        "ricardo.lima@pix.com",
                                        TipoPix.CPF,
                                        "12345",
                                        Set.of(UF.PR),
                                        LocalDate.of(1945, 7, 22),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),
                        new CadastroRequestDTO(
                                "medico9@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Patrícia Alves",
                                        List.of("patricia.alves@pulso.dev"),
                                        "+5511977771111",
                                        "https://randomuser.me/api/portraits/women/4.jpg",
                                        LocalDate.of(2000, 2, 1),
                                        "patricia.alves@pix.com",
                                        TipoPix.CPF,
                                        "23456",
                                        Set.of(UF.RS),
                                        LocalDate.of(1945, 5, 12),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico10@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Felipe Santos",
                                        List.of("felipe.santos@pulso.dev"),
                                        "+5511966667777",
                                        "https://randomuser.me/api/portraits/men/5.jpg",
                                        LocalDate.of(2000, 4, 1),
                                        "felipe.santos@pix.com",
                                        TipoPix.CPF,
                                        "34567",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 11, 3),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico11@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Renata Oliveira",
                                        List.of("renata.oliveira@pulso.dev"),
                                        "+5511988882222",
                                        "https://randomuser.me/api/portraits/women/5.jpg",
                                        LocalDate.of(2000, 6, 1),
                                        "renata.oliveira@pix.com",
                                        TipoPix.CPF,
                                        "45678",
                                        Set.of(UF.MG),
                                        LocalDate.of(1945, 8, 21),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico12@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Gustavo Ferreira",
                                        List.of("gustavo.ferreira@pulso.dev"),
                                        "+5511965558888",
                                        "https://randomuser.me/api/portraits/men/6.jpg",
                                        LocalDate.of(2000, 7, 1),
                                        "gustavo.ferreira@pix.com",
                                        TipoPix.CPF,
                                        "56789",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 3, 18),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico13@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Carla Ribeiro",
                                        List.of("carla.ribeiro@pulso.dev"),
                                        "+5511977773333",
                                        "https://randomuser.me/api/portraits/women/6.jpg",
                                        LocalDate.of(2000, 8, 1),
                                        "carla.ribeiro@pix.com",
                                        TipoPix.CPF,
                                        "67890",
                                        Set.of(UF.BA),
                                        LocalDate.of(1945, 12, 12),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico14@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Ricardo Gomes",
                                        List.of("ricardo.gomes@pulso.dev"),
                                        "+5511966669999",
                                        "https://randomuser.me/api/portraits/men/7.jpg",
                                        LocalDate.of(2000, 9, 1),
                                        "ricardo.gomes@pix.com",
                                        TipoPix.CPF,
                                        "78901",
                                        Set.of(UF.SC),
                                        LocalDate.of(1945, 6, 15),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico15@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Beatriz Nunes",
                                        List.of("beatriz.nunes@pulso.dev"),
                                        "+5511988884444",
                                        "https://randomuser.me/api/portraits/women/7.jpg",
                                        LocalDate.of(2000, 10, 1),
                                        "beatriz.nunes@pix.com",
                                        TipoPix.CPF,
                                        "89012",
                                        Set.of(UF.PR),
                                        LocalDate.of(1945, 4, 5),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico16@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Thiago Castro",
                                        List.of("thiago.castro@pulso.dev"),
                                        "+5511965551111",
                                        "https://randomuser.me/api/portraits/men/8.jpg",
                                        LocalDate.of(2000, 11, 1),
                                        "thiago.castro@pix.com",
                                        TipoPix.CPF,
                                        "90123",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 2, 22),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico17@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Amanda Rocha",
                                        List.of("amanda.rocha@pulso.dev"),
                                        "+5511988885555",
                                        "https://randomuser.me/api/portraits/women/8.jpg",
                                        LocalDate.of(2000, 12, 1),
                                        "amanda.rocha@pix.com",
                                        TipoPix.CPF,
                                        "01234",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 10, 10),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico18@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Eduardo Lima",
                                        List.of("eduardo.lima@pulso.dev"),
                                        "+5511965552222",
                                        "https://randomuser.me/api/portraits/men/9.jpg",
                                        LocalDate.of(2000, 1, 1),
                                        "eduardo.lima@pix.com",
                                        TipoPix.CPF,
                                        "12345",
                                        Set.of(UF.MG),
                                        LocalDate.of(1945, 9, 3),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico19@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Renan Souza",
                                        List.of("renan.souza@pulso.dev"),
                                        "+5511966661111",
                                        "https://randomuser.me/api/portraits/men/10.jpg",
                                        LocalDate.of(2000, 2, 1),
                                        "renan.souza@pix.com",
                                        TipoPix.CPF,
                                        "23456",
                                        Set.of(UF.SC),
                                        LocalDate.of(1945, 8, 14),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico20@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Viviane Martins",
                                        List.of("viviane.martins@pulso.dev"),
                                        "+5511988886666",
                                        "https://randomuser.me/api/portraits/women/9.jpg",
                                        LocalDate.of(2000, 3, 1),
                                        "viviane.martins@pix.com",
                                        TipoPix.CPF,
                                        "34567",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 5, 25),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),
                        new CadastroRequestDTO(
                                "medico21@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Leonardo Pinto",
                                        List.of("leonardo.pinto@pulso.dev"),
                                        "+5511965553333",
                                        "https://randomuser.me/api/portraits/men/11.jpg",
                                        LocalDate.of(1999, 4, 1),
                                        "leonardo.pinto@pix.com",
                                        TipoPix.CPF,
                                        "45678",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 6, 2),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico22@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Tatiane Alves",
                                        List.of("tatiane.alves@pulso.dev"),
                                        "+5511988887777",
                                        "https://randomuser.me/api/portraits/women/10.jpg",
                                        LocalDate.of(1998, 5, 1),
                                        "tatiane.alves@pix.com",
                                        TipoPix.CPF,
                                        "56789",
                                        Set.of(UF.PR),
                                        LocalDate.of(1945, 7, 8),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico23@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Marcelo Carvalho",
                                        List.of("marcelo.carvalho@pulso.dev"),
                                        "+5511965554444",
                                        "https://randomuser.me/api/portraits/men/12.jpg",
                                        LocalDate.of(1997, 6, 1),
                                        "marcelo.carvalho@pix.com",
                                        TipoPix.CPF,
                                        "67890",
                                        Set.of(UF.BA),
                                        LocalDate.of(1945, 12, 30),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico24@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Larissa Souza",
                                        List.of("larissa.souza@pulso.dev"),
                                        "+5511988888888",
                                        "https://randomuser.me/api/portraits/women/11.jpg",
                                        LocalDate.of(1996, 7, 1),
                                        "larissa.souza@pix.com",
                                        TipoPix.CPF,
                                        "78901",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 10, 19),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico25@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Rodrigo Lima",
                                        List.of("rodrigo.lima@pulso.dev"),
                                        "+5511965555555",
                                        "https://randomuser.me/api/portraits/men/13.jpg",
                                        LocalDate.of(1995, 8, 1),
                                        "rodrigo.lima@pix.com",
                                        TipoPix.CPF,
                                        "89012",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 9, 15),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico26@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Camila Pinto",
                                        List.of("camila.pinto@pulso.dev"),
                                        "+5511988889999",
                                        "https://randomuser.me/api/portraits/women/12.jpg",
                                        LocalDate.of(1994, 9, 1),
                                        "camila.pinto@pix.com",
                                        TipoPix.CPF,
                                        "90123",
                                        Set.of(UF.MG),
                                        LocalDate.of(1945, 1, 20),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico27@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Guilherme Souza",
                                        List.of("guilherme.souza@pulso.dev"),
                                        "+5511965556666",
                                        "https://randomuser.me/api/portraits/men/14.jpg",
                                        LocalDate.of(1993, 10, 1),
                                        "guilherme.souza@pix.com",
                                        TipoPix.CPF,
                                        "01234",
                                        Set.of(UF.PR),
                                        LocalDate.of(1945, 2, 25),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico28@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Aline Martins",
                                        List.of("aline.martins@pulso.dev"),
                                        "+5511988880000",
                                        "https://randomuser.me/api/portraits/women/13.jpg",
                                        LocalDate.of(1992, 11, 1),
                                        "aline.martins@pix.com",
                                        TipoPix.CPF,
                                        "12345",
                                        Set.of(UF.BA),
                                        LocalDate.of(1945, 3, 7),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico29@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Fernando Costa",
                                        List.of("fernando.costa@pulso.dev"),
                                        "+5511965557777",
                                        "https://randomuser.me/api/portraits/men/15.jpg",
                                        LocalDate.of(1991, 12, 1),
                                        "fernando.costa@pix.com",
                                        TipoPix.CPF,
                                        "23456",
                                        Set.of(UF.SP),
                                        LocalDate.of(1945, 5, 5),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),

                        new CadastroRequestDTO(
                                "medico30@pulso.dev", "senha123",
                                new ProfissionalRequestDTO(
                                        "Mariana Lima",
                                        List.of("mariana.lima@pulso.dev"),
                                        "+5511988881111",
                                        "https://randomuser.me/api/portraits/women/14.jpg",
                                        LocalDate.of(1990, 1, 1),
                                        "mariana.lima@pix.com",
                                        TipoPix.CPF,
                                        "34567",
                                        Set.of(UF.RJ),
                                        LocalDate.of(1945, 8, 30),
                                        Set.of(Especialidade.CLINICA_GERAL)),
                                null),


                        // =========================
                        // INSTITUIÇÕES (3)
                        // =========================
                        new CadastroRequestDTO(
                                "hospital@pulso.dev", "senha123",
                                null,
                                new InstituicaoRequestDTO(
                                        "Hospital Central",
                                        List.of("contato@hospitalcentral.com"),
                                        "+5511999998888",
                                        "https://tse4.mm.bing.net/th/id/OIP.Jzx16GCqcukGDw3yzxiUzQHaFj?rs=1&pid=ImgDetMain&o=7&rm=3",
                                        "Rua Central, 100 - São Paulo - SP",
                                        "11111111000111",
                                        true)),

                        new CadastroRequestDTO(
                                "upa@pulso.dev", "senha123",
                                null,
                                new InstituicaoRequestDTO(
                                        "UPA Zona Norte",
                                        List.of("contato@upanorte.com"),
                                        "+5511988889999",
                                        "https://wallpaperaccess.com/full/4113310.jpg",
                                        "Av. Norte, 200 - São Paulo - SP",
                                        "22222222000122",
                                        false)),

                        new CadastroRequestDTO(
                                "clinica@pulso.dev", "senha123",
                                null,
                                new InstituicaoRequestDTO(
                                        "Clínica Vida",
                                        List.of("contato@clinicavida.com"),
                                        "+5511977770000",
                                        "https://media.istockphoto.com/id/1312706413/pt/foto/modern-hospital-building.jpg?s=612x612&w=0&k=20&c=QtiuwaHxGTvb2Z7AqNo3YA--gkxojDXTzKvb87CzSFY=",
                                        "Rua da Vida, 300 - Campinas - SP",
                                        "33333333000133",
                                        false))
                );

                cadastros.forEach(usuarioService::cadastrar);
        }


        private void create_setor() {

                // =========================
                // INSTITUIÇÃO - ID USUÁRIO 8 (HOSPITAL)
                // =========================
                setorService.criar(new SetorRequestDTO("Emergência Adulto", Especialidade.CLINICA_GERAL), 34L);
                setorService.criar(new SetorRequestDTO("UTI", Especialidade.CLINICA_GERAL), 34L);
                setorService.criar(new SetorRequestDTO("Clínica Médica", Especialidade.CLINICA_GERAL), 34L);
                setorService.criar(new SetorRequestDTO("Cardiologia", Especialidade.CARDIOLOGIA), 34L);
                setorService.criar(new SetorRequestDTO("Neurologia", Especialidade.NEUROLOGIA), 34L);

                // =========================
                // INSTITUIÇÃO - ID USUÁRIO 9 (UPA)
                // =========================
                setorService.criar(new SetorRequestDTO("Emergência", Especialidade.CLINICA_GERAL), 35L);
                setorService.criar(new SetorRequestDTO("Clínica Geral", Especialidade.CLINICA_GERAL), 35L);
                setorService.criar(new SetorRequestDTO("Ortopedia", Especialidade.ORTOPEDIA), 35L);
                setorService.criar(new SetorRequestDTO("Pediatria", Especialidade.PEDIATRIA), 35L);
                setorService.criar(new SetorRequestDTO("Observação", Especialidade.CLINICA_GERAL), 36L);

                // =========================
                // INSTITUIÇÃO - ID USUÁRIO 10 (CLÍNICA)
                // =========================
                setorService.criar(new SetorRequestDTO("Clínica Geral", Especialidade.CLINICA_GERAL), 36L);
                setorService.criar(new SetorRequestDTO("Cardiologia", Especialidade.CARDIOLOGIA), 36L);
                setorService.criar(new SetorRequestDTO("Endocrinologia", Especialidade.ENDOCRINOLOGIA), 36L);
                setorService.criar(new SetorRequestDTO("Dermatologia", Especialidade.DERMATOLOGIA), 36L);
                setorService.criar(new SetorRequestDTO("Ginecologia", Especialidade.GINECOLOGIA), 36L);
        }

        private void create_vinculos() {

                // =========================
                // HOSPITAL CENTRAL
                // =========================
                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(1L, false), 3L // Lucas - CLT
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(2L, false), 3L // Marina - CLT
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(7L, false), 3L);
                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(4L, true), 3L);
                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(6L, false), 3L);

                // =========================
                // UPA ZONA NORTE
                // =========================
                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(3L, false), 2L // Rafael - CLT
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(5L, false), 2L // Bruno - CLT
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(7L, false), 2L // Diego - CLT
                );

                // =========================
                // CLÍNICA VIDA
                // =========================
                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(2L, true), 1L // Marina - PJ
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(6L, true), 1L // Paula - PJ
                );

                instituicaoService.vincularProfissional(
                                new VinculoRequestDTO(7L, true), 1L // Diego - PJ
                );
                instituicaoService.vincularProfissional(new VinculoRequestDTO(5L, true), 1L );
                instituicaoService.vincularProfissional(new VinculoRequestDTO(3L, false), 1L );
                instituicaoService.vincularProfissional(new VinculoRequestDTO(8L, true), 1L);  // Ricardo Lima - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(9L, true), 1L);  // Patrícia Alves - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(10L, true), 1L); // Felipe Santos - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(11L, true), 1L); // Renata Oliveira - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(12L, true), 1L); // Gustavo Ferreira - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(13L, true), 1L); // Carla Ribeiro - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(14L, true), 1L); // Ricardo Gomes - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(15L, true), 1L); // Beatriz Nunes - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(16L, true), 1L); // Thiago Castro - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(17L, true), 1L); // Amanda Rocha - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(18L, true), 1L); // Eduardo Lima - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(19L, true), 1L); // Renan Souza - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(20L, true), 1L); // Viviane Martins - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(21L, true), 1L); // Leonardo Pinto - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(22L, true), 1L); // Tatiane Alves - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(23L, true), 1L); // Marcelo Carvalho - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(24L, true), 1L); // Larissa Souza - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(25L, true), 1L); // Rodrigo Lima - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(26L, true), 1L); // Camila Pinto - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(27L, true), 1L); // Guilherme Souza - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(28L, true), 1L); // Aline Martins - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(29L, true), 1L); // Fernando Costa - PJ
                instituicaoService.vincularProfissional(new VinculoRequestDTO(30L, true), 1L); // Mariana Lima - PJ

        }

        private void create_vinculos_lider_escala() {
                instituicaoService.setarLiderEscala(
                                new VinculoLiderEscalaRequestDTO(1L), 3L);

                instituicaoService.setarLiderEscala(
                                new VinculoLiderEscalaRequestDTO(2L), 3L);
        }

        public void create_escala() {

                LocalDate mes = LocalDate.now()
                        .plusMonths(2)
                        .withDayOfMonth(1);

                EscalaSimpleResponseDTO e1_l1 = escalaService.criar(
                                new EscalaRequestDTO(
                                                "Escala Emergência - Março/2026",
                                                mes,
                                                false,
                                                11L // Emergência Adulto - CLINICA_GERAL
                                ),
                                1L);

                EscalaSimpleResponseDTO e2_l1 = escalaService.criar(
                        new EscalaRequestDTO(
                                "Escala Emergência - abril/2026",
                                mes.plusMonths(1),
                                false,
                                11L // Emergência Adulto - CLINICA_GERAL
                        ),
                        1L);

                plantaoService.atualizar(1L, new PlantaoRequestDTO(
                        null,           // horaEntrada
                        null,           // horaSaida
                        0,              // estimativaValor
                        null,           // formaPagamento
                        null,           // estimativaPagamento
                        null,           // comentario
                        5,              // quantidadeProfissionais
                        1L              // escalaId
                ));

                plantaoService.atualizarPlantaoCombinado(1L, 4L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(2L, 7L, 1L, true);
                // =========================
                // Plantão ID 1 - Profissionais 8, 9, 10 (quantidade 3)
                // =========================
                plantaoService.atualizarPlantaoCombinado(1L, 8L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(1L, 9L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(1L, 10L, 1L, true);

                // =========================
                // Plantão ID 1 - Profissionais 11 ao 30 (quantidade 1)
                // =========================
                plantaoService.atualizarPlantaoCombinado(3L, 11L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(4L, 12L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(5L, 13L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(6L, 14L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(7L, 15L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(8L, 16L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(9L, 17L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(10L, 18L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(11L, 19L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(12L, 20L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(13L, 21L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(14L, 22L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(15L, 23L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(16L, 24L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(17L, 25L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(18L, 26L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(19L, 27L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(12L, 28L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(11L, 29L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(13L, 30L, 1L, true);

                plantaoService.atualizarPlantaoCombinado(100L, 11L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(101L, 12L, 1L, true);
                plantaoService.atualizarPlantaoCombinado(102L, 13L, 1L, true);

                escalaService.publicar(2L, true);
        }

}
