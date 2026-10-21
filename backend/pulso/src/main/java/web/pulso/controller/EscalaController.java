package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import web.pulso.dto.request.EscalaRequestDTO;
import web.pulso.dto.response.escala.EscalaResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.service.EscalaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/escalas")
@Tag(name = "Escalas", description = "Gerenciamento de escalas")
public class EscalaController {

        private final EscalaService escalaService;

        public EscalaController(EscalaService escalaService) {
                this.escalaService = escalaService;
        }

        @Operation(summary = "Criar nova escala de plantões", description = """
                        Permite criar uma nova escala para um determinado setor, associando os plantões
                        que poderão ser alocados posteriormente aos profissionais.

                        - Caso não exista uma escala anterior para o mesmo setor, é gerada uma escala com plantões default.
                        - Caso exista uma escala anterior, é possível copiar a configuração da última escala como base.

                        Essa operação é realizada por um líder de escala que será vinculado à escala com base no seu login.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Escala criada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida ou erro de validação nos dados fornecidos."),
                        @ApiResponse(responseCode = "404", description = "Setor ou informações relacionadas não encontradas.")
        })
        @PostMapping
        public ResponseEntity<EscalaSimpleResponseDTO> criar(
                        @Parameter(description = "Dados necessários para criação da nova escala, incluindo informações do setor e comportamento da geração.", required = true) @Valid @RequestBody EscalaRequestDTO dto,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario");
                EscalaSimpleResponseDTO novaEscala = escalaService.criar(dto, idUsuario);
                return ResponseEntity.status(201).body(novaEscala);
        }

        @Operation(summary = "Listar escalas com filtros opcionais", description = """
                        Retorna uma lista de escalas existentes no sistema vinculados ao líder de escala logado, permitindo a aplicação de diversos filtros opcionais.

                        Pode ser utilizado por líderes de escala para visualizar escalas ativas ou anteriores.

                        Filtros disponíveis:
                        - `nome`: Nome da escala (ou parte dele) para busca textual.
                        - `mes_referencia`: Mês de referência da escala.
                        - `status`: Status atual da escala (ex: PUBLICADA, RASCUNHO, ARQUIVADA).
                        - `setor_id`: ID do setor ao qual a escala pertence.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de escalas retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos ou inconsistentes."),
                        @ApiResponse(responseCode = "404", description = "Nenhuma escala encontrada com os filtros informados.")
        })
        @GetMapping
        public ResponseEntity<List<EscalaSimpleResponseDTO>> listar(
                        @Parameter(description = "Mês de referência da escala.", example = "02") @RequestParam(required = false) String mesReferencia,
                        @Parameter(description = "Status atual da escala (ex: PUBLICADA, RASCUNHO, ARQUIVADA).", example = "PUBLICADA") @RequestParam(required = false) StatusEscala status,
                        @Parameter(description = "Especialidade do setor") @RequestParam(required = false) Especialidade especialidade,
                        @Parameter(description = "Filtrar apenas plantões do líder de escala") @RequestParam(required = false) Boolean liderEscala,
                        @Parameter(description = "Filtrar apenas plantões da instituição") @RequestParam(required = false) Boolean instituicao,
                        @AuthenticationPrincipal Jwt jwt) {

                Long idUsuario = jwt.getClaim("id_usuario"); // Resgate do usuário logado
                List<EscalaSimpleResponseDTO> response = escalaService.listar(
                                idUsuario,
                                mesReferencia,
                                status,
                                especialidade,
                                liderEscala,
                                instituicao);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Buscar escala por ID", description = """
                        Retorna os detalhes completos de uma escala específica com base em seu identificador único (ID).

                        Este endpoint é utilizado para visualizar as informações de uma escala já existente, incluindo
                        dados como setor, mês de referência, status e plantões associados.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Escala encontrada e retornada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "O ID informado é inválido."),
                        @ApiResponse(responseCode = "404", description = "Nenhuma escala encontrada para o ID informado.")
        })
        @GetMapping("/{id}")
        public ResponseEntity<EscalaResponseDTO> buscarPorId(
                        @Parameter(description = "ID único da escala a ser consultada.", example = "42", required = true) @PathVariable Long id) {
                EscalaResponseDTO response = escalaService.buscarPorId(id);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Atualizar uma escala existente", description = """
                        Atualiza os dados de uma escala já cadastrada, identificada pelo seu ID na URL.

                        Os novos dados devem ser enviados no corpo da requisição. Essa operação permite
                        alterar informações como nome da escala.

                        Somente usuários com permissão de líderes de escala
                        podem realizar esta operação.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Escala atualizada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos no corpo da requisição."),
                        @ApiResponse(responseCode = "404", description = "Escala não encontrada para o ID informado.")
        })
        @PutMapping("/{id}")
        public ResponseEntity<EscalaSimpleResponseDTO> atualizar(
                        @Parameter(description = "ID único da escala que será atualizada.", example = "42", required = true) @PathVariable Long id,
                        @Parameter(description = "Novos dados a serem aplicados à escala existente.", required = true) @Valid @RequestBody EscalaRequestDTO dto) {
                EscalaSimpleResponseDTO response = escalaService.atualizar(id, dto);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Excluir uma escala existente", description = """
                        Remove permanentemente uma escala do sistema com base em seu identificador único (ID).

                        Essa operação deve ser utilizada com cautela, pois a exclusão é definitiva e pode impactar
                        registros relacionados, como plantões e alocações vinculadas à escala.

                        Somente usuários com permissão de líderes de escala ou administradores
                        podem realizar esta ação.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Escala excluída com sucesso."),
                        @ApiResponse(responseCode = "400", description = "O ID informado é inválido."),
                        @ApiResponse(responseCode = "404", description = "Escala não encontrada para o ID informado.")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                        @Parameter(description = "ID único da escala que será excluída.", example = "42", required = true) @PathVariable Long id) {
                escalaService.excluir(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Publicar uma escala existente", description = """
                        Altera o status de uma escala para **PUBLICADA**, tornando-a oficialmente disponível
                        para visualização e alocação de plantões pelos profissionais.

                        Essa operação é utilizada após a criação e configuração completa da escala,
                        garantindo que ela esteja pronta para ser disponibilizada no sistema.

                        Somente usuários com permissão de líderes de escala
                        podem realizar esta ação.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Escala publicada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Requisição inválida ou escala já publicada."),
                        @ApiResponse(responseCode = "404", description = "Escala não encontrada para o ID informado.")
        })
        @PatchMapping("/{id}/publicacao")
        public ResponseEntity<EscalaSimpleResponseDTO> atualizarPublicacao(
                        @Parameter(description = "ID da escala que será atualizada", required = true) @PathVariable Long id,
                        @Parameter(description = "Define se a escala deve ser publicada (true) ou despublicada (false)", required = true) @RequestParam boolean publicada) {

                EscalaSimpleResponseDTO response = escalaService.publicar(id, publicada);
                return ResponseEntity.ok(response);
        }
}
