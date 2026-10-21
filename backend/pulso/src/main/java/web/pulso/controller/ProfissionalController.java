package web.pulso.controller;

import java.util.List;

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
import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.service.ProfissionalService;

@RestController
@RequestMapping("/api/v1/profissionais")
@Tag(name = "Profissionais", description = "Endpoints para gerenciamento de profissionais")
public class ProfissionalController {

        private final ProfissionalService profissionalService;

        public ProfissionalController(ProfissionalService profissionalService) {
                this.profissionalService = profissionalService;
        }

        @Operation(summary = "Criar novo profissional", description = """
                        Cria um novo profissional no sistema com os dados fornecidos no corpo da requisição.
                        É necessário informar informações pessoais, dados de contato, formação e especialidades.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida — dados ausentes ou incorretos."),
                        @ApiResponse(responseCode = "409", description = "Já existe um profissional cadastrado com o mesmo e-mail ou CRM.")
        })
        @PostMapping
        public ResponseEntity<ProfissionalResponseDTO> criarProfissional(
                        @Parameter(description = "Dados do profissional a ser criado.") @Valid @RequestBody ProfissionalRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {
                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                ProfissionalResponseDTO criado = profissionalService.criar(dto, idUsuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }

        @Operation(summary = "Atualizar profissional existente", description = """
                        Atualiza os dados de um profissional existente com base no seu ID.
                        É necessário informar o ID na URL e os novos dados no corpo da requisição.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida — dados ausentes ou incorretos."),
                        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<ProfissionalResponseDTO> atualizarProfissional(
                        @Parameter(description = "ID do profissional a ser atualizado.", example = "1") @PathVariable Long id,
                        @Parameter(description = "Novos dados do profissional.") @Valid @RequestBody ProfissionalRequestDTO dto) {
                ProfissionalResponseDTO atualizado = profissionalService.atualizar(id, dto);
                return ResponseEntity.ok(atualizado);
        }

        @Operation(summary = "Listar profissionais com filtros opcionais", description = """
                        Retorna uma lista de profissionais cadastrados no sistema.
                        É possível aplicar diversos filtros opcionais, como:
                        - **nome**, **email**, **telefone**, **CRM**
                        - **anoFormacao** e **tipoPix**
                        - **especialidade**, **ehLiderEscala**
                        - **idInstituicao**
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Filtros inválidos.")
        })
        @GetMapping
        public ResponseEntity<List<ProfissionalEstatisticaResponseDTO>> listar(
                        @Parameter(description = "Filtrar profissionais que podem ser alocados ao plantão") @RequestParam(required = false) Long idPlantao) {
                List<ProfissionalEstatisticaResponseDTO> profissionais = profissionalService
                                .listar(idPlantao);
                return ResponseEntity.status(HttpStatus.OK).body(profissionais);
        }

        @Operation(summary = "Buscar profissional por ID", description = """
                        Retorna os dados detalhados de um profissional específico com base no seu ID.
                        Inclui informações de contato, formação, especialidades e vínculos.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<ProfissionalResponseDTO> buscarPorId(
                        @Parameter(description = "ID do profissional a ser buscado", example = "1") @PathVariable Long id) {
                ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);
                return ResponseEntity.ok(profissional);
        }

        @Operation(summary = "Excluir profissional", description = """
                        Remove um profissional do sistema com base no seu ID.
                        Essa operação é irreversível e remove todos os vínculos associados ao profissional.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Profissional removido com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID do profissional a ser deletado", example = "1") @PathVariable Long id) {
                profissionalService.deletar(id);
                return ResponseEntity.noContent().build();
        }
}