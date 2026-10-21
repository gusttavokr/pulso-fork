package web.pulso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.service.SetorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/setores")
@Tag(name = "Setores", description = "Gerenciamento de setores e suas associações com instituições")
public class SetorController {

        private final SetorService setorService;

        public SetorController(SetorService setorService) {
                this.setorService = setorService;
        }

        @Operation(summary = "Criar novo setor", description = """
                        Registra um novo setor no sistema com base nas informações fornecidas no corpo da requisição.
                        O setor deve estar vinculado a uma instituição e a uma especialidade específica.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Setor criado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição.")
        })
        @PostMapping
        public ResponseEntity<SetorResponseDTO> criarSetor(
                        @Parameter(description = "Dados do novo setor a ser criado.") @Valid @RequestBody SetorRequestDTO setorRequestDTO,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                SetorResponseDTO setorCriado = setorService.criar(setorRequestDTO, idUsuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(setorCriado);
        }

        @Operation(summary = "Listar setores com filtros opcionais", description = """
                        Retorna uma lista de setores cadastrados no sistema.
                        É possível aplicar filtros opcionais pelos seguintes campos:
                        - **nome**: nome parcial ou completo do setor;
                        - **especialidade**: especialidade associada ao setor;
                        - **instituicaoId**: ID da instituição à qual o setor pertence.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de setores retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Filtros inválidos ou inconsistentes.")
        })
        @GetMapping
        public ResponseEntity<List<SetorResponseDTO>> listar(
                        @Parameter(description = "Nome do setor a ser buscado", example = "Cardiologia") @RequestParam(required = false) String nome,
                        @Parameter(description = "Especialidade do setor a ser buscada", example = "CARDIOLOGIA") @RequestParam(required = false) Especialidade especialidade,
                        @AuthenticationPrincipal Jwt jwt) {
                // Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                List<SetorResponseDTO> response = setorService.listar(nome, especialidade, null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Buscar setor por ID", description = """
                        Recupera os dados de um setor específico com base no seu identificador único.
                        Caso o setor não exista, será retornado um erro 404.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Setor encontrado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Setor não encontrado para o ID informado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<SetorResponseDTO> buscarPorId(
                        @Parameter(description = "ID do setor a ser buscado", example = "1") @PathVariable Long id) {
                SetorResponseDTO setor = setorService.buscarPorId(id);
                return ResponseEntity.ok(setor);
        }

        @Operation(summary = "Atualizar setor existente", description = """
                        Atualiza os dados de um setor já cadastrado, utilizando o ID como referência.
                        Todos os campos obrigatórios devem ser informados novamente.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição."),
                        @ApiResponse(responseCode = "404", description = "Setor não encontrado para o ID informado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<SetorResponseDTO> atualizarSetor(
                        @Parameter(description = "ID do setor a ser atualizado", example = "1") @PathVariable Long id,
                        @Parameter(description = "Novos dados do setor.") @Valid @RequestBody SetorRequestDTO setorRequestDTO) {
                SetorResponseDTO setorAtualizado = setorService.atualizar(id, setorRequestDTO);
                return ResponseEntity.ok(setorAtualizado);
        }

        @Operation(summary = "Excluir setor", description = """
                        Remove permanentemente um setor do sistema com base no ID informado.
                        Caso o setor esteja vinculado a outras entidades (como escalas ou profissionais),
                        a exclusão poderá ser impedida pelo sistema.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Setor removido com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Setor não encontrado para o ID informado."),
                        @ApiResponse(responseCode = "409", description = "O setor não pode ser removido devido a vínculos existentes.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID do setor a ser deletado", example = "1") @PathVariable Long id) {
                setorService.deletar(id);
                return ResponseEntity.noContent().build();
        }
}
