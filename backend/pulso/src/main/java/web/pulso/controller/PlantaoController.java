package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import web.pulso.dto.filtros.PlantaoFilter;
import web.pulso.dto.request.AlocacaoRequestDTO;
import web.pulso.dto.request.PlantaoRequestDTO;
import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.service.PlantaoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plantoes")
@Tag(name = "Plantoes", description = "Gerenciamento de plantões médicos")
public class PlantaoController {
        private final PlantaoService plantaoService;

        public PlantaoController(PlantaoService plantaoService) {
                this.plantaoService = plantaoService;
        }

        @Operation(summary = "Criar novo plantão", description = """
                        Cria um novo plantão com os dados fornecidos no corpo da requisição.
                        O plantão será associado à escala e instituição indicadas.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Plantão criado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou inconsistentes."),
                        @ApiResponse(responseCode = "404", description = "Escala ou instituição não encontrada.")
        })
        @PostMapping
        public ResponseEntity<PlantaoResponseDTO> criarPlantao(
                        @Parameter(description = "Dados do plantão a ser criado.") @Valid @RequestBody PlantaoRequestDTO dto) {
                PlantaoResponseDTO criado = plantaoService.criar(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }

        @Operation(summary = "Atualizar plantão existente", description = """
                        Atualiza os dados de um plantão existente com base no seu ID.
                        É necessário informar o ID do plantão na URL e os novos dados no corpo da requisição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Plantão atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                        @ApiResponse(responseCode = "404", description = "Plantão não encontrado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<PlantaoResponseDTO> atualizarPlantao(
                        @Parameter(description = "ID do plantão a ser atualizado.", example = "1") @PathVariable Long id,
                        @Parameter(description = "Novos dados do plantão.") @Valid @RequestBody PlantaoRequestDTO dto) {
                PlantaoResponseDTO atualizado = plantaoService.atualizar(id, dto);
                return ResponseEntity.ok(atualizado);
        }

        @GetMapping
        @Operation(summary = "Listar plantões com filtros opcionais", description = """
                        Retorna uma lista de plantões do usuário logado, permitindo filtrar por diversos parâmetros opcionais:
                        - **abertos**: Filtra apenas plantões abertos disponíveis.
                        - **idEscala**: ID da escala vinculada.
                        - **formaPagamento**: Tipo de pagamento (ex: PIX, DINHEIRO).
                        - **estimativaPagamento**: Data estimada de pagamento.
                        - **horaEntrada / horaSaida**: Período do plantão.
                        - **confirmado**: Status de confirmação do profissional.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de plantões retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Filtros inválidos.")
        })
        public ResponseEntity<List<PlantaoSimpleResponseDTO>> listar(
                        @ParameterObject PlantaoFilter filter,
                        @AuthenticationPrincipal Jwt jwt) {

                filter.setIdUsuario(jwt.getClaim("id_usuario"));
                List<PlantaoSimpleResponseDTO> response = plantaoService.listar(filter);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Buscar plantão por ID", description = "Retorna os detalhes de um plantão específico com base no seu ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Plantão encontrado e retornado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Plantão não encontrado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<PlantaoResponseDTO> buscarPorId(
                        @Parameter(description = "ID do plantão a ser buscado.", example = "10") @PathVariable Long id) {
                PlantaoResponseDTO response = plantaoService.buscarPorId(id);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Excluir plantão", description = """
                        Remove um plantão existente com base no seu ID.
                        Essa operação é irreversível.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Plantão excluído com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Plantão não encontrado.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID do plantão a ser excluído.", example = "7") @PathVariable Long id) {
                plantaoService.deletar(id);
                return ResponseEntity.noContent().build();

        }

        @Operation(summary = "Alocar profissional em um plantão", description = """
                        Permite alocar um profissional em um plantão, de duas formas possíveis:

                        - **Pegar plantão:** o próprio profissional logado assume o plantão (campo `idUsuario` informado).
                        - **Combinar plantão:** o líder de escala aloca um profissional específico (campo `idProfissional` informado).

                        Apenas **um** dos campos (`idUsuario` ou `idProfissional`) deve ser informado.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Alocação realizada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida ou conflito de lógica."),
                        @ApiResponse(responseCode = "404", description = "Plantão, usuário ou profissional não encontrado.")
        })
        @PostMapping("/alocacao")
        public ResponseEntity<PlantaoResponseDTO> alocarProfissional(
                        @Parameter(description = "Dados necessários para alocar um profissional em um plantão.") @Valid @RequestBody AlocacaoRequestDTO dto,
                        @Parameter(description = "Marcador se está alocando um profissional ou removendo sua alocação.") @RequestParam(required = false) Boolean adicionar,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                PlantaoResponseDTO response;

                if (dto.getIdProfissional() == null) {
                        // Usuário pegando o próprio plantão
                        response = plantaoService.pegarPlantao(dto.getIdPlantao(), idUsuario);
                } else {
                        response = plantaoService.atualizarPlantaoCombinado(
                                        dto.getIdPlantao(),
                                        dto.getIdProfissional(),
                                        idUsuario,
                                        adicionar);
                }

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Atualizar comentário do plantão", description = """
                        Permite atualizar ou remover o comentário de um plantão.

                        - Apenas usuários autenticados podem realizar a ação.
                        - O comentário é opcional: ao enviar vazio ou nulo, o comentário será removido.
                        - A atualização respeita as regras de permissão definidas para o plantão.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                        @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para editar o plantão."),
                        @ApiResponse(responseCode = "404", description = "Plantão não encontrado.")
        })
        @PatchMapping("/{id}/comentario")
        public ResponseEntity<PlantaoResponseDTO> atualizarComentario(
                        @Parameter(description = "ID do plantão que terá o comentário atualizado.") @PathVariable Long id,

                        @Parameter(description = "Novo comentário a ser colocado no plantão. Se nulo ou vazio, o comentário será removido.") @RequestParam(required = false) String comentario,

                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario"); // Usuário logado

                PlantaoResponseDTO response = plantaoService.atualizarComentario(
                                id,
                                comentario,
                                idUsuario);

                return ResponseEntity.ok(response);
        }
}
