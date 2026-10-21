package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.pulso.dto.request.CadastroRequestDTO;
import web.pulso.dto.request.UsuarioLoginRequestDTO;
import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.service.UsuarioService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Gerencia as operações relacionadas aos usuários do sistema.")
public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
        }

        @Operation(summary = "Logar no sistema", description = "Realiza a autenticação do usuário.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso.")
        })
        @PostMapping("/login")
        public ResponseEntity<UsuarioLoginResponseDTO> login(
                        @Parameter(description = "Dados de login do usuário.") @Valid @RequestBody UsuarioLoginRequestDTO dto) {

                UsuarioLoginResponseDTO token = usuarioService.autenticar(dto.getEmail(), dto.getSenha());

                return ResponseEntity.ok(token);
        }

        @Operation(summary = "Cadastrar novo usuário", description = "Realiza o cadastro de um novo usuário no sistema.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
                        @ApiResponse(responseCode = "409", description = "Usuário já existente.")
        })
        @PostMapping("/cadastro")
        public ResponseEntity<UsuarioResponseDTO> cadastrar(
                        @Parameter(description = "Dados necessários para o cadastro do usuário") @Valid @RequestBody CadastroRequestDTO dto) {

                UsuarioResponseDTO response = usuarioService.cadastrar(dto);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @Operation(summary = "Atualizar usuário existente", description = "Atualiza os dados de um usuário com base no ID.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
                        @Parameter(description = "ID do usuário.", example = "1") @PathVariable Long id,

                        @Parameter(description = "Novos dados do usuário.") @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

                UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(id, usuarioRequestDTO);

                return ResponseEntity.ok(usuarioAtualizado);
        }

        @Operation(summary = "Listar usuários com filtros opcionais", description = """
                        Retorna usuários cadastrados, com filtros opcionais:
                        - **ativo**: status do usuário
                        - **criadoInicio / criadoFim**: intervalo de criação
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
        })
        @GetMapping
        public ResponseEntity<List<UsuarioResponseDTO>> listar(
                        @Parameter(description = "Define se o usuário está ativo") @RequestParam(required = false) Boolean ativo,

                        @Parameter(description = "Data inicial de criação") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date criadoInicio,

                        @Parameter(description = "Data final de criação") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date criadoFim) {

                List<UsuarioResponseDTO> usuarios = usuarioService.listar(ativo, criadoInicio, criadoFim);

                return ResponseEntity.ok(usuarios);
        }

        @Operation(summary = "Buscar usuário por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> buscarPorId(
                        @Parameter(description = "ID do usuário.", example = "1") @PathVariable Long id) {

                return ResponseEntity.ok(usuarioService.buscarPorId(id));
        }

        @Operation(summary = "Deletar usuário")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID do usuário.", example = "1") @PathVariable Long id) {

                usuarioService.deletar(id);
                return ResponseEntity.noContent().build();
        }
}
