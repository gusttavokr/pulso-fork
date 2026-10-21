package web.pulso.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import web.pulso.dto.request.RespostaTrocaRequestDTO;
import web.pulso.dto.request.TrocaRequestDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.service.TrocaService;

@RestController
@RequestMapping("/api/v1/trocas")
@Tag(name = "Trocas", description = "Gerenciamento de solicitações e confirmações de trocas de plantões")
public class TrocaController {

        private final TrocaService trocaService;

        public TrocaController(TrocaService trocaService) {
                this.trocaService = trocaService;
        }

        @Operation(summary = "Criar nova solicitação de troca", description = """
                        Registra uma nova solicitação de troca de plantões entre profissionais.
                        A troca pode ser simples (um único plantão) ou dupla (duas trocas simultâneas entre profissionais).
                        Todos os campos obrigatórios devem ser informados no corpo da requisição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Troca criada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição.")
        })
        @PostMapping
        public ResponseEntity<TrocaResponseDTO> criar(
                        @Parameter(description = "Dados da troca a ser registrada.") @Valid @RequestBody TrocaRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                TrocaResponseDTO novaTroca = trocaService.criar(dto, idUsuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(novaTroca);
        }

        @Operation(summary = "Atualizar troca existente", description = """
                        Atualiza os dados de uma troca já registrada, identificada pelo seu ID.
                        Apenas trocas ainda pendentes podem ser modificadas.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Troca atualizada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
                        @ApiResponse(responseCode = "404", description = "Troca não encontrada para o ID informado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<TrocaResponseDTO> atualizar(
                        @Parameter(description = "ID da troca a ser atualizada", example = "1") @PathVariable Long id,
                        @Parameter(description = "Novos dados da troca.") @Valid @RequestBody TrocaRequestDTO dto) {
                TrocaResponseDTO trocaAtualizada = trocaService.atualizar(id, dto);
                return ResponseEntity.ok(trocaAtualizada);
        }

        @Operation(summary = "Listar trocas com filtros opcionais", description = """
                        Retorna uma lista de trocas cadastradas no sistema.
                        É possível aplicar filtros por tipo, status, profissionais envolvidos, plantões e período de solicitação.
                        Caso nenhum filtro seja informado, todas as trocas serão retornadas.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de trocas retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Filtros inválidos ou inconsistentes.")
        })
        @GetMapping
        public ResponseEntity<List<TrocaResponseDTO>> listar(
                        @Parameter(description = "Comentário da troca") @RequestParam(required = false) String comentario,
                        @Parameter(description = "Tipo da troca (SIMPLES ou DUPLA)") @RequestParam(required = false) TipoTroca tipo,
                        @Parameter(description = "Status da troca (CONFIRMADO, PENDENTE, RECUSADO)") @RequestParam(required = false) StatusTroca status,
                        @Parameter(description = "ID do profissional que solicitou a troca (origem)") @RequestParam(required = false) Long idProfissionalOrigem,
                        @Parameter(description = "ID do profissional que recebeu a solicitação (destino)") @RequestParam(required = false) Long idProfissionalDestino,
                        @Parameter(description = "ID do plantão de origem da troca") @RequestParam(required = false) Long idPlantaoOrigem,
                        @Parameter(description = "ID do plantão de destino da troca (se for dupla)") @RequestParam(required = false) Long idPlantaoDestino,
                        @Parameter(description = "Data inicial da solicitação da troca") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                        @Parameter(description = "Data final da solicitação da troca") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                List<TrocaResponseDTO> trocas = trocaService.listar(
                                idUsuario, comentario, tipo, status,
                                idProfissionalOrigem, idProfissionalDestino,
                                idPlantaoOrigem, idPlantaoDestino,
                                dataInicio, dataFim);
                return ResponseEntity.status(HttpStatus.OK).body(trocas);
        }

        @Operation(summary = "Buscar troca por ID", description = """
                        Retorna os detalhes de uma troca específica com base no seu identificador único.
                        Inclui informações sobre os profissionais e plantões envolvidos.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Troca encontrada com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Troca não encontrada para o ID informado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<TrocaResponseDTO> buscarPorId(
                        @Parameter(description = "ID da troca a ser buscada", example = "1") @PathVariable Long id) {
                TrocaResponseDTO troca = trocaService.buscarPorId(id);
                return ResponseEntity.ok(troca);
        }

        @Operation(summary = "Excluir troca existente", description = """
                        Remove uma solicitação de troca do sistema com base no ID informado.
                        Apenas trocas pendentes podem ser removidas; trocas já aceitas ou recusadas não podem ser excluídas.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Troca removida com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Troca não encontrada para o ID informado."),
                        @ApiResponse(responseCode = "409", description = "A troca não pode ser removida pois já foi respondida.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID da troca a ser deletada", example = "1") @PathVariable Long id) {
                trocaService.deletar(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Responder a uma solicitação de troca", description = """
                        Permite aceitar ou recusar uma solicitação de troca de plantões.
                        Caso a troca seja **aceita**, é obrigatório informar o **ID do plantão de destino**.
                        Caso seja **recusada**, apenas o ID da troca é necessário.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Troca respondida com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição."),
                        @ApiResponse(responseCode = "404", description = "Troca não encontrada para o ID informado."),
                        @ApiResponse(responseCode = "409", description = "Não é possível responder a uma troca já processada.")
        })
        @PostMapping("/resposta")
        public ResponseEntity<TrocaResponseDTO> responderTroca(
                        @Parameter(description = "Dados da resposta à solicitação de troca.") @Valid @RequestBody RespostaTrocaRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario");
                TrocaResponseDTO resposta;

                resposta = trocaService.responderTroca(dto, idUsuario);

                return ResponseEntity.ok(resposta);
        }
}
