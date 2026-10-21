# Documento Geral de Casos de Teste - Asaph

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os teste aqui descritos abrangem diferentes caoss de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada caso de teste inclui informações detalhadas sobre o cenário a ser avaliado, os dados de entrada necessários e resultados esperados.

## CDU 07 - Consultar Plantões Abertos

### Fluxo principal - Listar plantões abertos

#### Objetivo:
Exibir ao médico logado a lista de plantões abertos disponíveis para visualização e possível alocação.

### Variáveis de Entrada:
- Variável 1: usuarioId
- Variável 2: abertos
- Variável 3: data
- Variável 4: especialidade
- Variável 5: precoMin
- Variável 6: precoMax
- Variável 7: horariosInicio
- Variável 8: horariosFim
- Variável 9: ehNovoPlantao
- Variável 10: ehRepasse
- Variável 11: ehTroca

### Condições de Negócio:
- Condição 1: `usuarioId` é obrigatório e deve estar associado a um profissional existente
- Condição 2: o parâmetro `abertos` deve estar como `true`
- Condição 3: o plantão deve estar para acontecer no futuro (**RN62**)
- Condição 4: o plantão deve pertencer a uma escala com status **PUBLICADA** (**RN62**)
- Condição 5: o profissional logado deve possuir vínculo com a instituição do plantão (**RN62**)
- Condição 6: o profissional logado deve possuir a especialidade exigida pelo setor do plantão (**RN62**)
- Condição 7: filtros opcionais, quando informados, devem restringir corretamente os resultados listados
- Condição 8: se `ehNovoPlantao = true`, apenas plantões novos devem ser exibidos
- Condição 9: se `ehRepasse = true`, apenas plantões de repasse devem ser exibidos
- Condição 10: se `ehTroca = true`, apenas plantões de troca devem ser exibidos

### Classes de Equivalência
| Classes Válidas | Classes Inválidas |
| :--- | :--- |
| `usuarioId` existente e associado a profissional | `usuarioId` inexistente ou sem profissional associado |
| `abertos = true` | `abertos = false` sem outro contexto válido |
| plantão futuro | plantão passado ou em andamento |
| escala `PUBLICADA` | escala `RASCUNHO` ou `ARQUIVADA` |
| profissional com vínculo com a instituição | profissional sem vínculo com a instituição |
| profissional com especialidade compatível | profissional sem especialidade compatível |
| filtros opcionais consistentes | filtros inválidos ou incompatíveis |
| plantão classificado corretamente como novo/repasse/troca | plantão fora da categoria filtrada |
### Fluxo Principal - Login

#### Testes funcionais
| usuarioId | abertos | data | especialidade | ehNovoPlantao | ehRepasse | ehTroca | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | true | null | null | null | null | null | lista(Plantões abertos disponíveis ao profissional) | --- | --- |
| null | true | null | null | null | null | null | erro("usuarioId não foi informado") | --- | --- |
| 999 | true | null | null | null | null | null | erro("profissional não encontrado para o usuário informado") | --- | --- |
| 1 | true | null | null | null | null | null | lista vazia(quando não existirem plantões abertos) | --- | --- |
| 1 | true | "2026-05-10" | null | null | null | null | lista(apenas plantões da data informada) | --- | --- |
| 1 | true | null | "CARDIOLOGIA" | null | null | null | lista(apenas plantões da especialidade informada) | --- | --- |
| 1 | true | null | null | true | false | false | lista(apenas plantões novos) | --- | --- |
| 1 | true | null | null | false | true | false | lista(apenas plantões de repasse) | --- | --- |
| 1 | true | null | null | false | false | true | lista(apenas plantões de troca) | --- | --- |
| 1 | true | null | null | null | null | null | lista sem plantões de escala não publicada | --- | --- |
| 1 | true | null | null | null | null | null | lista sem plantões fora da especialidade do profissional | --- | --- |
| 1 | true | null | null | null | null | null | lista sem plantões de instituição sem vínculo | --- | --- |
| 1 | false | null | null | null | null | null | erro("ao menos um parâmetro de contexto deve ser true") | --- | --- |
---

## CDU 06 - Pegar Plantão

### Fluxo principal - Pegar plantão

#### Objetivo:
Permitir que o médico logado assuma um plantão disponível no feed de plantões abertos.

#### Variáveis de Entrada:
- Variável 1: usuarioId
- Variável 2: plantaoId

#### Condições de Negócio:
- Condição 1: `usuarioId` é obrigatório e deve estar associado a um profissional existente
- Condição 2: `plantaoId` é obrigatório, do tipo Long, acima de 0 e deve referenciar um plantão existente
- Condição 3: o plantão deve estar para acontecer no futuro (**RN58**)
- Condição 4: o plantão deve pertencer a uma escala com status **PUBLICADA** (**RN59**)
- Condição 5: o profissional deve possuir vínculo com a instituição do plantão (**RN60**)
- Condição 6: o profissional deve possuir a especialidade exigida pelo setor do plantão (**RN61**)
- Condição 7: o profissional não pode já estar vinculado ao plantão
- Condição 8: o plantão deve possuir vagas disponíveis
- Condição 9: se o plantão estiver vinculado a um repasse pendente, ao pegar o plantão a troca deve ser confirmada

#### Classes de Equivalência
| Classes Válidas | Classes Inválidas |
| :--- | :--- |
| `usuarioId` existente e associado a profissional | `usuarioId` inexistente ou sem profissional associado |
| `plantaoId` > 0 e existente | `plantaoId` ≤ 0 ou inexistente |
| plantão futuro | plantão passado ou em andamento |
| plantão de escala `PUBLICADA` | plantão de escala não publicada |
| profissional com vínculo com a instituição | profissional sem vínculo |
| profissional com especialidade compatível | profissional sem especialidade compatível |
| profissional ainda não vinculado ao plantão | profissional já combinado ou confirmado no plantão |
| plantão com vagas disponíveis | plantão sem vagas |
| plantão comum ou repasse pendente tratado corretamente | plantão com inconsistência de alocação/troca |

#### Testes funcionais
| usuarioId | plantaoId | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- |
| 1 | 10 | sucesso("plantão vinculado ao profissional logado") | --- | --- |
| null | 10 | erro("usuarioId não foi informado") | --- | --- |
| 999 | 10 | erro("profissional não encontrado para o usuário informado") | --- | --- |
| 1 | null | erro("plantaoId não foi informado") | --- | --- |
| 1 | 999 | erro("plantão não encontrado") | --- | --- |
| 1 | 10 (escala publicada) | sucesso("plantão pego com sucesso") | --- | --- |
| 1 | 10 (escala rascunho) | erro("não é possível pegar plantão de escala não publicada") | --- | --- |
| 1 | 10 (data passada) | erro("não é possível pegar plantão passado ou em andamento") | --- | --- |
| 1 | 10 (sem vínculo com a instituição) | erro("profissional não possui vínculo com a instituição do plantão") | --- | --- |
| 1 | 10 (sem especialidade compatível) | erro("profissional não possui a especialidade exigida pelo setor do plantão") | --- | --- |
| 1 | 10 (já confirmado) | erro("o profissional já está vinculado a este plantão") | --- | --- |
| 1 | 10 (sem vagas disponíveis) | erro("não há vagas disponíveis para este plantão") | --- | --- |
| 1 | 10 (repasse pendente) | sucesso("plantão pego e repasse confirmado") | --- | --- |

### Fluxo alternativo - Pegar plantão de repasse

#### Objetivo:
Permitir que o médico logado assuma um plantão proveniente de repasse, confirmando a troca pendente associada.

#### Variáveis de Entrada:
- Variável 1: usuarioId
- Variável 2: plantaoId
- Variável 3: trocaPendente

#### Condições de Negócio:
- Condição 1: deve existir uma troca simples pendente associada ao plantão
- Condição 2: ao pegar o plantão, o profissional logado deve ser registrado como destino da troca
- Condição 3: a troca deve ter seu status alterado para **CONFIRMADO**
- Condição 4: o plantão deve passar a constar como vinculado ao novo profissional

#### Testes funcionais
| usuarioId | plantaoId | trocaPendente | Resultado esperado | Resultado obtido | Situação |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | 15 | true | sucesso("profissional assume o plantão e a troca é confirmada") | --- | --- |
| 1 | 15 | false | sucesso("profissional assume o plantão normalmente, sem atualização de troca") | --- | --- |
| 1 | 15 | inconsistente | erro("troca do plantão não encontrada ou inválida") | --- | --- |