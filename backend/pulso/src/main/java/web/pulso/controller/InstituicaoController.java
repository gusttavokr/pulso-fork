package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.request.VinculoLiderEscalaRequestDTO;
import web.pulso.dto.request.VinculoRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.dto.response.instituicao.status.InstituicaoProfissionaisStatusResponseDTO;
import web.pulso.service.InstituicaoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instituicoes")
@Tag(name = "Instituições", description = "Gerenciamento de instituições de ensino e unidades hospitalares")
public class InstituicaoController {

        private final InstituicaoService instituicaoService;

        public InstituicaoController(InstituicaoService instituicaoService) {
                this.instituicaoService = instituicaoService;
        }

        @Operation(summary = "Criar nova instituição", description = """
                        Cria uma nova instituição de ensino ou unidade hospitalar no sistema.

                        Essa operação permite cadastrar instituições que servirão como base para a
                        alocação de profissionais e gestão administrativa.

                        O corpo da requisição deve conter todas as informações obrigatórias para identificação e registro da instituição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Instituição criada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados obrigatórios ausentes."),
                        @ApiResponse(responseCode = "409", description = "Já existe uma instituição registrada com os mesmos dados informados.")
        })
        @PostMapping
        public ResponseEntity<InstituicaoResponseDTO> criarInstituicao(
                        @Parameter(description = "Dados necessários para o cadastro da nova instituição, incluindo nome, tipo e informações de contato.", required = true) @Valid @RequestBody InstituicaoRequestDTO instituicaoRequestDTO,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                InstituicaoResponseDTO instituicaoCriada = instituicaoService.criar(instituicaoRequestDTO, idUsuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoCriada);
        }

        @Operation(summary = "Listar instituições com filtros opcionais", description = """
                        Retorna uma lista de instituições cadastradas no sistema, permitindo aplicar diversos filtros opcionais.

                        Pode ser utilizada por administradores ou gestores para localizar instituições de ensino
                        ou unidades hospitalares específicas, conforme os parâmetros informados.

                        Filtros disponíveis:
                        - `idLider`: faz com que retorne apenas instituições e setores do lider de escala logado
                        - `nome`: Nome (ou parte do nome) da instituição.
                        - `email`: Endereço de email cadastrado.
                        - `telefone`: Número de telefone associado.
                        - `endereco`: Endereço ou parte do endereço.
                        - `cnpj`: CNPJ da instituição.
                        - `exigeVinculo`: Indica se a instituição exige vínculo prévio dos profissionais.
                        - `idUsuario`: ID do usuário relacionado à instituição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de instituições retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos ou inconsistentes."),
                        @ApiResponse(responseCode = "404", description = "Nenhuma instituição encontrada com os filtros informados.")
        })
        @GetMapping
        public ResponseEntity<List<InstituicaoResponseDTO>> listar(
                        @Parameter(description = "id do lider de escala.") @RequestParam(required = false, defaultValue = "false") Boolean doLider,
                        @Parameter(description = "Nome (ou parte do nome) da instituição.", example = "Hospital Santa Luzia") @RequestParam(required = false) String nome,
                        @Parameter(description = "Endereço de email da instituição.", example = "contato@hospital.com") @RequestParam(required = false) String email,
                        @Parameter(description = "Telefone de contato da instituição.", example = "(11) 98765-4321") @RequestParam(required = false) String telefone,
                        @Parameter(description = "Endereço completo ou parcial da instituição.", example = "Av. Paulista, 1000 - São Paulo") @RequestParam(required = false) String endereco,
                        @Parameter(description = "CNPJ da instituição.", example = "12.345.678/0001-90") @RequestParam(required = false) String cnpj,
                        @Parameter(description = "Indica se a instituição exige vínculo formal dos profissionais.", example = "true") @RequestParam(required = false) Boolean exigeVinculo,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idLider = null;
                if (doLider)
                        idLider = jwt.getClaim("id_usuario");

                List<InstituicaoResponseDTO> response = instituicaoService.listar(idLider, nome, email,
                                telefone,
                                endereco,
                                cnpj, exigeVinculo);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Buscar instituição por ID", description = """
                        Retorna os detalhes completos de uma instituição cadastrada, com base em seu identificador único (ID).

                        Este endpoint é utilizado para consultar informações específicas de uma instituição,
                        como nome, endereço, CNPJ, dados de contato e configurações de vínculo.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Instituição encontrada e retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "O ID informado é inválido."),
                        @ApiResponse(responseCode = "404", description = "Instituição não encontrada para o ID informado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<InstituicaoResponseDTO> buscarPorId(
                        @Parameter(description = "ID único da instituição a ser consultada.", example = "15", required = true) @PathVariable Long id) {
                InstituicaoResponseDTO instituicao = instituicaoService.buscarPorId(id);
                return ResponseEntity.ok(instituicao);
        }

        @Operation(summary = "Atualizar instituição", description = """
                        Atualiza os dados de uma instituição existente no sistema.

                        O ID da instituição deve ser informado na URL e os novos dados devem ser enviados no corpo da requisição.
                        Este endpoint permite modificar informações como nome, endereço, telefone, e demais atributos da instituição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Instituição atualizada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
                        @ApiResponse(responseCode = "404", description = "Instituição não encontrada para o ID informado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<InstituicaoResponseDTO> atualizarInstituicao(
                        @Parameter(description = "ID único da instituição a ser atualizada.", example = "12", required = true) @PathVariable Long id,
                        @Parameter(description = "Novos dados da instituição a serem atualizados.", required = true) @Valid @RequestBody InstituicaoRequestDTO instituicaoRequestDTO) {
                InstituicaoResponseDTO instituicaoAtualizada = instituicaoService.atualizar(id, instituicaoRequestDTO);
                return ResponseEntity.ok(instituicaoAtualizada);
        }

        @Operation(summary = "Excluir instituição", description = """
                        Remove uma instituição existente do sistema com base no seu ID.

                        Este endpoint exclui permanentemente os dados da instituição e não poderá ser desfeito.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Instituição excluída com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Instituição não encontrada para o ID informado."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID único da instituição a ser removida.", example = "15", required = true) @PathVariable Long id) {
                instituicaoService.deletar(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Criar vínculo entre profissional e instituição", description = """
                        Cria um vínculo entre um profissional e uma instituição existente no sistema.

                        O tipo de vínculo (por exemplo, PJ ou CLT) deve ser informado no corpo da requisição.
                        Caso o profissional ou a instituição não existam, será retornado um erro 404.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados informados."),
                        @ApiResponse(responseCode = "404", description = "Instituição ou profissional não encontrado.")
        })
        @PatchMapping("/vinculo")
        public ResponseEntity<Void> criarVinculo(
                        @Parameter(description = "Dados necessários para criação do vínculo, incluindo IDs e tipo de vínculo (PJ ou CLT).", required = true) @Valid @RequestBody VinculoRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario");
                instituicaoService.vincularProfissional(dto, idUsuario);
                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Definir ou remover líder de escala", description = """
                        Define ou remove o profissional de líder de escala da instituição.
                        O corpo da requisição deve conter o ID do profissional e o valor booleano
                        indicando se ele deve ou não ser líder de escala.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Status de liderança atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida — dados ausentes ou incorretos."),
                        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
        })
        @PatchMapping("/vinculo-lider-escala")
        public ResponseEntity<Void> criarVinculoLiderEscala(
                        @Parameter(description = "Dados necessários para criação do vínculo com líder de escala", required = true) @Valid @RequestBody VinculoLiderEscalaRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario");
                instituicaoService.setarLiderEscala(dto, idUsuario);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Status da instituição", description = """
                        Retorna metadados e indicadores da instituição associada
                        ao usuário autenticado, como quantidade de profissionais,
                        distribuições e percentuais.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Status da instituição retornado com sucesso."),
                        @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
                        @ApiResponse(responseCode = "404", description = "Instituição não encontrada para o usuário.")
        })
        @GetMapping("/status")
        public ResponseEntity<InstituicaoProfissionaisStatusResponseDTO> status(
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario");
                InstituicaoProfissionaisStatusResponseDTO response = instituicaoService
                                .obterStatusInstituicao(idUsuario);

                return ResponseEntity.ok(response);
        }

}
