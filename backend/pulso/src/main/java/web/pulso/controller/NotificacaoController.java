package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.pulso.dto.response.notificacao.NotificacaoDTO;
import web.pulso.service.NotificacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacoes")
@Tag(name = "Notificações", description = "Gerenciamento de notificações dos usuários")
public class NotificacaoController {

        private final NotificacaoService notificacaoService;

        public NotificacaoController(NotificacaoService notificacaoService) {
                this.notificacaoService = notificacaoService;
        }

        @Operation(summary = "Listar notificações de um usuário", description = """
                        Retorna todas as notificações associadas a um usuário específico,
                        com base no seu ID.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
        })
        @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<List<NotificacaoDTO>> getNotificacoesPorUsuario(
                        @Parameter(description = "ID do usuário cujas notificações devem ser listadas.", example = "12") @PathVariable Long usuarioId) {
                List<NotificacaoDTO> notificacoes = notificacaoService.buscarNotificacaoDeUsuarioId(usuarioId);
                return ResponseEntity.status(HttpStatus.OK).body(notificacoes);
        }

        @Operation(summary = "Buscar notificação por ID", description = "Retorna os detalhes de uma notificação específica a partir do seu ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Notificação encontrada e retornada com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Notificação não encontrada.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<NotificacaoDTO> getNotificacaoPorId(
                        @Parameter(description = "ID da notificação a ser buscada.", example = "45") @PathVariable Long id) {
                NotificacaoDTO notificacao = notificacaoService.buscarPorId(id);
                return ResponseEntity.status(HttpStatus.OK).body(notificacao);
        }

        @Operation(summary = "Marcar notificação como lida", description = """
                        Atualiza o status da notificação para **LIDA**, indicando que o usuário visualizou a mensagem.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Notificação marcada como lida com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Notificação não encontrada.")
        })
        @PostMapping("/{id}/lido")
        public ResponseEntity<Void> marcarComoLida(
                        @Parameter(description = "ID da notificação que será marcada como lida.", example = "45") @PathVariable Long id) {
                notificacaoService.foiLido(id);
                return ResponseEntity.noContent().build();
        }
}
