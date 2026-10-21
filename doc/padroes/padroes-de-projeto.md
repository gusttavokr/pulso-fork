# 🏗️ Padroes de Projeto - Pulso

## 🎯 Objetivo
Definir e documentar os padroes de projeto que serao utilizados no **Pulso**, garantindo consistencia, legibilidade e facilidade de manutencao do codigo.

---

## 📌 Padroes Definidos

### 1. Nomenclatura
- **Classes:** `PascalCase`  
  Ex: `UsuarioController`, `HospitalService`.
- **Metodos:** `camelCase`  
  Ex: `buscarPorId`, `criarUsuario`.
- **Variaveis locais e atributos:** `camelCase`  
  Ex: `quantidadeUsuarios`, `email`.
- **Constantes:** `UPPER_CASE` com `_`  
  Ex: `MAX_HORAS_PLANTAO`.
- **Pacotes:** sempre em **minusculo** e no singular  
  Ex: `controller`, `service`, `repository`, `model`.

---

### 2. Estrutura de Diretorios (por dominio/modulo)

src/main/java/web/pulso/

├── conta/ <br>
│ ├── controller/<br>
│ ├── service/<br>
│ ├── repository/<br>
│ └── model/<br>
├── hospital/<br>
├── notificacao/<br>
├── escala/<br>
├── excecoes/ # excecoes compartilhadas<br>
└── configuracao/ # configuracoes globais<br>


---

### 3. Padroes de Projeto
- **MVC:** separacao clara entre Model, Controller e Service.  
- **DTOs:** utilizados para entrada e saida de dados na API (nao expor entidades diretamente).  
- **Repository Pattern:** repositorios extendendo `JpaRepository`.  
- **Service Layer:** toda regra de negocio deve estar nos `Services`, nunca nos `Controllers`.  
- **Tratamento de excecoes:** centralizado em `@ControllerAdvice` dentro de `excecoes/`.

---

### 4. Estilo de Codigo
- Usar **Lombok** (`@Getter`, `@Setter`, `@Builder`) para reduzir boilerplate.  
- Anotacoes do **Spring Boot** bem posicionadas (`@Service`, `@RestController`, `@Repository`).  
- **Identacao:** 4 espacos.  
- **Linhas:** maximo 120 caracteres.  
- Comentarios apenas quando necessario para explicar logica complexa.

---

### 5. Banco de Dados
- Entidades devem estar em `model/` anotadas com `@Entity`.  
- **Nome de tabelas:** prefixo `tb_` e no **singular**  
  Ex: `tb_usuario`, `tb_conta`, `tb_hospital`.  
- **Nome de colunas:** `snake_case`  
  Ex: `data_criacao`, `numero_registro`.

---

### 6. Boas Praticas Gerais
- Nunca expor entidades diretamente nas APIs.  
- Evitar logica de negocio em `Controllers`.  
- Reutilizar excecoes padronizadas em `excecoes/`.  
- Criar `DTOs` claros para requisicoes e respostas.  
- Garantir consistencia no uso de `ResponseEntity` nos endpoints.

---

## 📂 Templates de Classes

### Controller
```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }
}
```
### Service

```java
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO buscarPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO criar(UsuarioDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }
}
```
### Repository
```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
```
### Model
```java
@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
}
```

