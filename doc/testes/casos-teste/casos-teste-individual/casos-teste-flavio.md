# Documento Geral de Casos de Teste - Flavio Matias

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada caso de teste inclui informações detalhadas sobre o cenário a ser avaliado, os dados de entrada necessários e resultados esperados.


## CDU 02 - Gerenciar plantão

### Fluxo principal - Criar plantão

#### Objetivo: 
Adicionar um novo plantão a uma escala já existente no sistema.

#### Variáveis de Entrada:
- Variável 1: escalaId
- Variável 2: horaEntrada
- Variável 3: horaSaida
- Variável 4: quantidadeProfissionais
- Variável 5: estimativaValor
- Variável 6: formaPagamento
- Variável 7: estimativaPagamento
- Variável 8: comentario

#### Condições de Negócio:
- Condição 1: os atributos `escalaId`, `horaEntrada`, `horaSaida` e `quantidadeProfissionais` são obrigatórios
- Condição 2: `horaEntrada` deve ser no formato ISO ("AAAA-MM-DDTHH:MM:SS") com antecedência mínima de 7 dias da data atual
- Condição 3: `horaEntrada` e `horaSaida` devem estar compreendidas dentro do mês de referência da escala informado em `escalaId` (**RN40**)
- Condição 4: `quantidadeProfissionais` deve ser um número inteiro acima de 0
- Condição 5: `escalaId` deve ser do tipo Long, acima de 0 e fazer referência a uma escala já existente
- Condição 6: não pode haver sobreposição de horários para plantões no mesmo setor e dia (**RN39**)

#### Classes de Equivalência
| Classes Válidas | Classes Inválidas |
| :--- | :--- |
| `escalaId` > 0 e existente | `escalaId` ≤ 0 ou inexistente |
| `horaEntrada` com antecedência ≥ 7 dias | `horaEntrada` com antecedência < 7 dias |
| `horaEntrada`/`Saida` dentro do mês da escala | `horaEntrada`/`Saida` fora do mês da escala |
| `quantidadeProfissionais` > 0 | `quantidadeProfissionais` ≤ 0 |
| combinação única de setor + horário | combinação de setor + horário já existente |
| todos os campos obrigatórios informados | algum campo obrigatório não informado |

#### Testes funcionais
| escalaId | horaEntrada | horaSaida | quantidadeProfissionais | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | "2026-05-10T07:00:00" | "2026-05-10T19:00:00" | 2 | Plantão(escalaId: 1, status: PENDENTE) | --- | --- |
| null | "2026-05-10T07:00:00" | "2026-05-10T19:00:00" | 2 | erro("escalaId não foi informado") | --- | --- |
| 1 | "2026-04-22T07:00:00" | "2026-04-22T19:00:00" | 2 | erro("antecedência mínima de 7 dias não respeitada") | --- | --- |
| 1 | "2026-05-10T07:00:00" | "2026-05-10T19:00:00" | 0 | erro("quantidade de profissionais deve ser maior que 0") | --- | --- |
| 1 (Maio) | "2026-05-01T00:00:00" | "2026-05-01T12:00:00" | 1 | Sucesso (Limite inferior do mês) | --- | --- |
| 1 (Maio) | "2026-05-31T23:59:00" | "2026-06-01T07:00:00" | 1 | Sucesso (Limite superior do mês) | --- | --- |
| 1 (Maio) | "2026-06-01T08:00:00" | "2026-06-01T20:00:00" | 1 | erro("data fora do mês de referência da escala") | --- | --- |
| 1 | "2026-05-10T07:00:00" | "2026-05-10T19:00:00" | 1 | erro("não pode haver sobreposição de horários") | --- | --- |
| 321 | "2026-05-10T07:00:00" | "2026-05-10T19:00:00" | 1 | erro("escala não encontrada com o escalaId informado") | --- | --- |


### Fluxo principal - Atualizar plantão

#### Objetivo: 
Atualizar os dados de um plantão já existente no sistema.

#### Variáveis de Entrada:
- Variável 1: plantaoId
- Variável 2: escalaId
- Variável 3: quantidadeProfissionais
- Variável 4: comentario

#### Condições de Negócio:
- Condição 1: `plantaoId` e `escalaId` são obrigatórios
- Condição 2: `plantaoId` deve referenciar um plantão existente com status **RASCUNHO** ou em escala **NÃO PUBLICADA**
- Condição 3: não é permitido alterar o `escalaId` de um plantão (não pode mover entre escalas)
- Condição 4: não é permitido reduzir `quantidadeProfissionais` para um valor menor que o número de profissionais já alocados no plantão

#### Testes funcionais
| plantaoId | escalaId | qtdProf | comentario | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 10 | 1 | 2 | "Novo comentário" | Plantão(id: 10, comentario: "Novo comentário") | --- | --- |
| null | 1 | 2 | "Teste" | erro("plantaoId não foi informado") | --- | --- |
| 10 | 2 | 2 | "Teste" | erro("não é permitido mover o plantão para outra escala") | --- | --- |
| 10 | 1 | 1 (com 2 alocados) | "Teste" | erro("vagas menores que profissionais alocados") | --- | --- |
| 10 (Publicada) | 1 | 2 | "Teste" | erro("não é possível alterar plantões de uma escala publicada") | --- | --- |


### Fluxo principal - Excluir plantão

#### Objetivo: 
Remover um plantão existente do sistema.

#### Variáveis de Entrada:
- Variável 1: plantaoId

#### Condições de Negócio:
- Condição 1: `plantaoId` é obrigatório, tipo Long e acima de 0
- Condição 2: não é permitido excluir plantão de uma escala já **PUBLICADA**
- Condição 3: não é permitido excluir plantão cuja data de entrada já tenha passado em relação ao horário atual

#### Testes funcionais
| plantaoId | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- |
| 10 | sucesso("plantão excluído com sucesso") | --- | --- |
| null | erro("plantaoId não foi informado") | --- | --- |
| 10 (escala publicada) | erro("não é possível excluir plantão de escala publicada") | --- | --- |
| 10 (data passada) | erro("não é possível deletar plantões com data retroativa") | --- | --- |

### Fluxo principal - Listar plantões

#### Objetivo: 
Exibir a lista de plantões relacionados ao usuário.

#### Variáveis de Entrada:
- Variável 1: usuarioId
- Variável 2: profissional (boolean)
- Variável 3: liderEscala (boolean)

#### Condições de Negócio:
- Condição 1: `usuarioId` é obrigatório
- Condição 2: ao menos um dos parâmetros (`profissional` ou `liderEscala`) deve ser `true`

#### Testes funcionais
| usuarioId | profissional | liderEscala | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | true | false | lista(Plantões do profissional) | --- | --- |
| 1 | false | true | lista(Plantões sob gestão do líder) | --- | --- |
| 1 | false | false | erro("ao menos um parâmetro de contexto deve ser true") | --- | --- |


### Fluxo principal - Detalhar plantão

#### Objetivo: 
Exibir informações detalhadas e alocações de um plantão.

#### Variáveis de Entrada:
- Variável 1: plantaoId

#### Testes funcionais
| plantaoId | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- |
| 10 | sucesso(Dados do plantão + profissionais alocados) | --- | --- |
| 999 (inexistente) | erro("plantão não encontrado") | --- | --- |
