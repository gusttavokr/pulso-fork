# Documento Geral de Casos de Teste - Icaro

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os teste aqui descritos abrangem diferentes caoss de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada caso de teste inclui informações detalhadas sobre o cenário a ser avaliado, os dados de entrada necessários e resultados esperados.

## CDU 01 - Gerenciar escala

### Fluxo principal - Criar escala

#### Objetivo: 
Adicionar uma nova escala ao sistema

#### Variáveis de Entrada:
- Variável 1: nome
- Variável 2: mes
- Variável 3: copia
- Variável 4: setorId

#### Condições de Negócio:
- Condição 1: apenas o atributo `copia` não é obrigatório
- Condição 2: `nome` deve ser do tipo string com ao menos 3 caracteres e no máximo 255 caracteres
- Condição 3: `mes` deve ser do tipo string no formato ISO ("AAAA-MM-DD") não podendo estar no passado levando em conta o dia atual
- Condição 4: `copia` deve ser do tipo booleano e caso não informado deve ter como valor padrão `false`
- Condição 5: `setorId` deve ser do tipo Long, acima de 0 e fazer referência a um setor já existente com o valor informado
- Condição 6: não pode haver escalas repetidas com o mesmo `setorId` e `mes`

#### Classes de Equivalência
| Classes Válidas             | Classes Inválidas           |
|-----------------------------|-----------------------------|
| `nome` com 3 caracteres | `nome` com menos de 3 caracteres |
| `nome` com tamanho entre 3 e 255 caracteres | `nome com mais de 255 caracteres` |
| `mes` em formato ISO válido | `mes` em formato inválido |
| `mes` igual à data atual | `mes` em data passada |
| `mes` em data futura | `mes` nulo ou vazio |
| `copia` informada como `true` ou `false` | `copia` com valor diferente de boolean |
| `copia` não informado | --- |
| `setorId` > 0 e existente no sistema | `setorId` ≤ 0 |
| `setorId` válido e existente | `setorId` existente no banco |
| combinação única de `setorId` + `mes` | combinação duplicada de `setorId` + `mes` |
| todos os campos obrigatórios (`nome`, `mes`, `setorId`) informados corretamente | algum dos campos obrigatórios não informado |

#### Testes funcionais
| nome | mes | copia | setorId | Resultado esperado | Resultado obitido | Situação |
|------|-----|-------|---------|--------------------|-------------------|----------|
| "Escala fevereiro" | "2026-02-01" | null | 1 | Escala(nome: "Escala fevereiro", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| null | "2026-02-01" | null | 1 | erro("Nome não foi informado") | --- | --- |
| true | "2026-02-01" | null | 1 | erro("Nome informado não é um texto") | --- | --- |
| "Es" | "2026-02-01" | null | 1 | erro("Nome informado tem menos de 3 caracteres") | --- | --- |
| "Esc" | "2026-02-01" | null | 1 | Escala(nome: "Esc", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| "Escala...escala" (255 caracteres) | "2026-02-01" | null | 1 | Escala(nome: "Escala...escala", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| "Escala...escala" (256 caracteres) | "2026-02-01" | null | 1 | erro("Nome informado tem maos de 255 caracteres") | --- | --- |
| "Esc" | null | null | 1 | erro("Mes de referência não informado") | --- | --- |
| "Esc" | "" | null | 1 | erro("Mês de referência informado não está no formato ISO") | --- | --- |
| "Esc" | true | null | 1 | erro("Mês de referência informado não é um texto") | --- | --- |
| "Esc" | "2025-02-01" | null | 1 | erro("Mês de referência informado está no passado") | --- | --- |
| "Esc" | "2026-02-01" | "true" | 1 | erro("Copia informada não é um booleano") | --- | --- |
| "Esc" | "2026-02-01" | null | null | erro("setorId não foi informado") | --- | --- |
| "Esc" | "2026-02-01" | null | "1" | erro("setorId informado não é um número") | --- | --- |
| "Esc" | "2026-02-01" | null | 0 | erro("setorId informado é menor que 1") | --- | --- |
| "Esc" | "2026-02-01" | null | 321 (setor não existente) | erro("setor não encontrado com o setorId informado") | --- | --- |
| "Esc" | "2026-02-01" (escala criada com mês e setor já informados) | null | 1 (escala criada com mês e setor já informados) | erro("escala já existente com mês de referência e setorId informados") | --- | --- |

---

### Fluxo principal - Atualizar escala

#### Objetivo: 
Atualizar uma escala já existente no sistema

#### Variáveis de Entrada:
- Variável 1: escalaId
- Variável 2: nome

#### Condições de Negócio:
- Condição 1: todos os atributos são obrigatórios
- Condição 2: `escalaId` deve ser do tipo Long, acima de 0 e fazer referência a uma escala já existente com o valor informado
- Condição 3: `escalaId` deve fazer referência a uma escala com status **RASCUNHO**
- Condição 4: `nome` deve ser do tipo string com ao menos 3 caracteres e no máximo 255 caracteres

#### Classes de Equivalência
| Classes Válidas             | Classes Inválidas           |
|-----------------------------|-----------------------------|
| `escalaId` > 0 e existente no sistema | `escalaId` ≤ 0 |
| `escalaId` válido e existente | `escalaId` existente no banco |
| `escalaId` referenciando escala com status **RASCUNHO** | `escalaId` referenciando escala com status diferente de **RASCUNHO** (ex: **PUBLICADA**, **ARQUIVADA**) |
| `nome` com 3 caracteres | `nome` com menos de 3 caracteres |
| `nome` com tamanho entre 3 e 255 caracteres | `nome com mais de 255 caracteres` |
| todos os campos obrigatórios (`escalaId`, `nome`) informados corretamente | algum dos campos obrigatórios não informado |

#### Testes funcionais
| escalaId | nome | Resultado esperado | Resultado obitido | Situação |
|----------|------|--------------------|-------------------|----------|
| 1 (escala criada no exemplo de criação) | "Escala fevereiro do setor 1" | Escala(nome: "Escala fevereiro do setor 1", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| null | "Esc" | erro("escalaId não foi informado")  | --- | --- |
| "1" | "Esc" | erro("escalaId informado não é um número") | --- | --- |
| 0 | "Esc" | erro("escalaId informado é menor que 1") | --- | --- |
| 321 (setor não existente) | "Esc" | erro("escala não encontrada com o escalaId informado") | --- | --- |
| 2 (escala já publicada) | "Esc" | erro("não é possivel atualizar escala já publicada") | --- | --- |
| 1 | null | erro("Nome não foi informado") | --- | --- |
| 1 | true | erro("Nome informado não é um texto") | --- | --- |
| 1 | "Es" | erro("Nome informado tem menos de 3 caracteres") | --- | --- |
| 1 | "Esc" | Escala(nome: "Esc", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| 1 | "Escala...escala" (255 caracteres) | Escala(nome: "Escala...escala", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | --- | --- |
| 1 | "Escala...escala" (256 caracteres) | erro("Nome informado tem maos de 255 caracteres") | --- | --- |

---

### Fluxo principal - Excluir escala

#### Objetivo: 
Excluir uma escala já existente no sistema

#### Variáveis de Entrada:
- Variável 1: escalaId

#### Condições de Negócio:
- Condição 1: `escalaId` é obrigatória
- Condição 2: `escalaId` deve ser do tipo Long, acima de 0 e fazer referência a uma escala já existente com o valor informado
- Condição 3: `escalaId` deve fazer referência a uma escala com status **RASCUNHO**

#### Classes de Equivalência
| Classes Válidas             | Classes Inválidas           |
|-----------------------------|-----------------------------|
| `escalaId` > 0 e existente no sistema | `escalaId` ≤ 0 |
| `escalaId` válido e existente | `escalaId` existente no banco |
| `escalaId` referenciando escala com status **RASCUNHO** | `escalaId` referenciando escala com status diferente de **RASCUNHO** (ex: **PUBLICADA**, **ARQUIVADA**) |
| `escalaId` informado corretamente | `escalaId` não informado |

#### Testes funcionais
| escalaId | Resultado esperado | Resultado obitido | Situação |
|----------|--------------------|-------------------|----------|
| 1 (escala criada no exemplo de criação) | sucesso("escala excluída com sucesso") | --- | --- |
| null | erro("escalaId não foi informado")  | --- | --- |
| "1" | erro("escalaId informado não é um número") | --- | --- |
| 0 | erro("escalaId informado é menor que 1") | --- | --- |
| 321 (setor não existente) | erro("escala não encontrada com o escalaId informado") | --- | --- |
| 2 (escala já publicada) | erro("não é possivel excluir escala já publicada") | --- | --- |

---

### Fluxo principal - Listar escalas

#### Objetivo: 
Exibir lista de escalas criadas

#### Variáveis de Entrada:
- Variável 1: usuarioId
- Variável 2: mesReferencia
- Variável 3: especialidade
- Variável 4: liderEscala
- Variável 5: instituicao

#### Condições de Negócio:
- Condição 1: o único campo obrigátorio é o `usuarioId`
- Condição 2: `usuarioId` deve ser do tipo Long, acima de 0 e fazer referência a um usuário já existente com o valor informado
- Condição 3: `mesReferencia` deve ser do tipo string no formato ISO ("AAAA-MM-DD")
- Condição 4: `especialidade` deve ser do tipo `Especialidades`
- Condição 5: `liderEscala` deve ser do tipo booleano
- Condição 6: `instituicao` deve ser do tipo booleano
- Condição 7: pelo menos um dos parâmetros `liderEscala` ou `instituicao` deve ser igual a `true`

#### Classes de Equivalência
| Classes Válidas             | Classes Inválidas           |
|-----------------------------|-----------------------------|
| `usuarioId` > 0 e existente no sistema | `usuarioId` ≤ 0 |
| `usuarioId` válido e existente | `usuarioId` existente no banco |
| `mesReferencia` no formato válido ISO (`AAAA-MM-DD`) | `mesReferencia` em formato inválido |
| `especialidade` válida do tipo `Especialidades` | `especialidade` inválida (fora do enum) |
| `liderEscala = true` | `liderEscala = false` e `instituicao = false` |
| `instituicao = true` | `liderEscala = null` e `instituicao = null` |
| `liderEscala = true` e/ou `instituicao = true` | `liderEscala = false` e `instituicao = null` |
| pelo menos um entre `liderEscala` ou `instituicao` igual a `true` | ambos não são `true` (false ou null) |
| `usuarioId` informado corretamente | `usuarioId` não informado |

#### Testes funcionais
| usuarioId | mesReferencia | especialidade | liderEscala | instituicao | Resultado esperado | Resultado obitido | Situação |
|-----------|---------------|---------------|-------------|-------------|--------------------|-------------------|----------|
| 1 | null | null | true | null | lista(Escala, Escala, Escala, ...) | --- | --- |
| null | null | null | true | null | erro("usuarioId não foi informado") | --- | --- |
| "1" | null | null | true | null | erro("usuarioId informado não é um número") | --- | --- |
| 0 | null | null | true | null | erro("usuarioId informado é menor que 1") | --- | --- |
| 321(usuário não existente) | null | null | true | null | erro("usuario não encontrada com o usuarioId informado") | --- | --- |
| 1 | "" | null | true | null | erro("Mês de referência informado não está no formato ISO") | --- | --- |
| 1 | true | null | true | null | erro("Mês de referência informado não é um texto") | --- | --- |
| 1 | null | true | true | null | erro("especialidade não encontrada") | --- | --- |
| 1 | null | null | null | null | erro("Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true") | --- | --- |
| 1 | null | null | false | null | erro("Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true") | --- | --- |
| 1 | null | null | null | false | erro("Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true") | --- | --- |
| 1 | null | null | false | false | erro("Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true") | --- | --- |
| 1 | null | null | null | true | lista(Escala, Escala, Escala, ...) | --- | --- |
| 1 | null | null | false | true | lista(Escala, Escala, Escala, ...) | --- | --- |
| 1 | null | null | true | false | lista(Escala, Escala, Escala, ...) | --- | --- |

---

### Fluxo principal - Detalhar escala

#### Objetivo: 
Exibir detalhamento de escala selecionada

#### Variáveis de Entrada:
- Variável 1: escalaId

#### Condições de Negócio:
- Condição 1: `escalaId` é obrigatória
- Condição 2: `escalaId` deve ser do tipo Long, acima de 0 e fazer referência a uma escala já existente com o valor informado

#### Classes de Equivalência
| Classes Válidas             | Classes Inválidas           |
|-----------------------------|-----------------------------|
| `escalaId` > 0 e existente no sistema | `escalaId` ≤ 0 |
| `escalaId` válido e existente | `escalaId` existente no banco |
| `escalaId` informado corretamente | `escalaId` não informado |

#### Testes funcionais
| escalaId | Resultado esperado | Resultado obitido | Situação |
|----------|--------------------|-------------------|----------|
| 1 (escala criada no exemplo de criação) | sucesso("escala excluída com sucesso") | --- | --- |
| null | erro("escalaId não foi informado")  | --- | --- |
| "1" | erro("escalaId informado não é um número") | --- | --- |
| 0 | erro("escalaId informado é menor que 1") | --- | --- |
| 321 (setor não existente) | erro("escala não encontrada com o escalaId informado") | --- | --- |