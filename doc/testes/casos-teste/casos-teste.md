# Documento Geral de Casos de Teste

## Histórico de Revisões

| Data | Versão | Descrição | Autores |
|------|--------|-----------|---------|
| 09/04/2026 | 1.0 | Versão inicial | [Gustavo Maia](https://github.com/gusttavokr), [Ícaro Gabriel](https://github.com/icarogabrielicaro), [Robson Alencastro](https://github.com/alencast) |

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os testes aqui descritos abrangem diferentes casos de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

Cada caso de teste inclui informações detalhadas sobre o cenário a ser avaliado, os dados de entrada necessários e resultados esperados.

## CDU 01 - Autenticação

#### Objetivo: 
O objetivo do fluxo de autenticação é validar a identidade do usuário de forma segura e eficiente, garantindo que apenas usuários cadastrados tenham acesso a plataforma e acesso a áreas restritas, funcionalidades ou dados confidenciais. Além disso, permitir que novos usuários, seja profissional ou instituição, possa acessar a plataforma via cadastro.

### Fluxo Principal - Login 

#### Variáveis de Entrada:
- Variável 1: E-mail
- Variável 2: Senha

#### Condições de Negócio:
- Condição 1: Ambos os atributos são obrigatórios.
- Condição 2: E-mail deve ser String.
- Condição 3: E-mail deve conter '@'.
- Condição 4: E-mail deve conter texto antes do '@'.
- Condição 5: E-mail deve ter um domínio válido.
- Condição 6: Senha deve ser String.
- Condição 7: Senha deve possuir no mínimo 8 caracteres.
- Condição 8: Senha deve corresponder ao E-mail informado.

#### Classes de Equivalência
| Classes Válidas                         | Classes Inválidas                          |
|-----------------------------------------|--------------------------------------------|
| `E-mail` tipo String                    | `E-mail` não ser String.                   | 
| `E-mail` deve conter '@'                | `E-mail` não conter '@'                    |
| `E-mail` deve conter texto antes do '@' | `E-mail` não conter texto antes do '@'     |
| `E-mail` deve possuir um domínio válido | `E-mail` não possuir domínio válido        |
| `Senha` tipo String                     | `Senha` não ser String                     |
| `Senha` possuir 8 ou mais caracteres.   | `Senha` não possuir menos de 8 caracteres. |
| `Senha` correspondente ao E-mail.       | `Senha` não-correspondente ao E-mail.      |


#### Testes funcionais

| E-mail              | Senha       | Resultado esperado | Resultado Obtido 
|---------------------|-------------|--------------------|--------------------
| 'gustavo@gmail.com' | 'senha123#' | Usuario(id:"28", token:"token", tipoUsuario: tipoUsuario, roles: roles[], instituicao: instituicao, profissional: profissional) | Usuario(id:"28", token:"token", tipoUsuario: tipoUsuario, roles: roles[], instituicao: instituicao, profissional: profissional) 
| ' ' | ' ' | 'Preencha todos os campos.' | 'Preencha todos os campos.'
| 12345               | 'senha123#' | 'E-mail inválido: O e-mail informado está incorreto!'        | 'Preencha todos os campos.'
| 'gustavo.com'       | 'senha123#' | 'E-mail inválido: O e-mail informado está incorreto!'        | 'Preencha todos os campos.'
| '@gmail.com'        | 'senha123#' | 'E-mail inválido: O e-mail informado está incorreto!'        | 'Preencha todos os campos.'
| 'gustavo@.com'      | 'senha123#' | 'E-mail inválido: O e-mail informado está incorreto!'        | 'Preencha todos os campos.'
| 'gustavo@gmail.com' | 250.2       | 'Senha inválida: A senha informada está incorreta!'          | 'Erro inesperado.'
| 'gustavo@gmail.com' | 'senha12'   | 'Senha inválida: A senha deve possuir 8 ou mais caracteres!' | 'Erro inesperado.'
| 'gustavo@gmail.com' | 'senha456#' | 'Senha incorreta: Tente novamente!'                          | 'Erro inesperado.'

### Fluxo Secundário - Cadastro de Profissional

#### Variáveis de Entrada:
- Variável 1: Nome
- Variável 2: E-mail
- Variável 3: Data de Nascimento
- Variável 4: Telefone
- Variável 5: Senha
- Variável 6: CRM (Credencial Regional de Medicina)
- Variável 7: Estado
- Variável 8: Data de Formação
- Variável 9: Especialidade
- Variável 10: Tipo de Pix
- Variável 11: Chave do Pix

#### Condições de Negócio:
- Condição 1: Todos os atributos, menos: Telefone, devem ser obrigatórios.
- Condição 2: Nome deve ter no mínimo 3 caracteres.
- Condição 3: Senha deve ter no mínimo 6 caracteres.
- Condição 4: E-mail deve conter '@'.
- Condição 5: E-mail deve conter texto antes do '@'.
- Condição 6: E-mail deve ter um domínio válido.
- Condição 7: Telefone deve ser opcional.
- Condição 8: Telefone deve ter 11 caracteres.
- Condição 9: CRM deve ter no mínimo 4 caracteres.
- Condição 10: Os tipos de Pix devem ser: CPF, E-mail, ou Telefone.
- Condição 11: Os estados disponíveis devem ser todos os estados brasileiros.
- Condição 12: O usuário deve ter data de nascimento para no mínimo 18 anos.
- Condição 13: Data de Formação não pode ser no futuro.
- Condição 14: Os caracteres da chave pix informada deve corresponder ao tipo de pix informado.
- Condição 15: Ter pelo menos 1 especialidade.
- Condição 16: E-mail deve ser único.

#### Classes de Equivalência
| Classes Válidas                         | Classes Inválidas                          |
|-----------------------------------------|--------------------------------------------|
| `Nome` com >= 3 caracteres | `Nome` com < 3 caracteres
| `Senha` com >= 6 caracteres | `Senha` com < 6 caracteres
| `E-mail` deve conter '@'                | `E-mail` não conter '@'                    |
| `E-mail` deve conter texto antes do '@' | `E-mail` não conter texto antes do '@'     |
| `E-mail` deve possuir um domínio válido | `E-mail` não possuir domínio válido        |
| `Telefone` com 11 caracteres | `Telefone` < 11 caracteres; <br> `Telefone` > 11 caracteres; 
| `CRM` >= 4 caracteres; <br> `CRM` < 7 caracteres; | `CRM` < 5 caracteres; <br> `CRM` > 6 caracteres;        |
| `Tipo de Pix` = (CPF ou E-mail ou Telefone) | `Tipo de Pix` != (CPF ou E-mail ou Telefone)
| `Estado` ser um estado Brasileiro. | `Estado` não ser um estado Brasileiro.
| `Data de Nascimento` para no mínimo 18 anos. | `Data de Nascimento` para no menos de 18 anos.
| `Data de Formação` não ser no futuro. | `Data de Formação` ser no futuro.
| `Chave Pix` for CPF ou Telefone, caracteres = 11 | `Chave Pix` for CPF, caracteres != 11
| `Chave Pix` for E-mail, possuir '@' e um domínio válido | `Chave Pix` for E-mail, não possuir '@' ou um domínio inválido
| `Especialidade` pertencente à lista de especialidades; | `Especialidade` não pertencente à lista de especialidades;

#### Testes Funcionais
| Nome | E-mail | Data de Nascimento | Telefone | Senha | CRM | Estado | Data de Formação | Especialidade | Tipo de Pix | Chave do Pix | Resultado Esperado | Resultado Obtido
|---|---|---|---|---|---|---|---|---|---|---|---|---
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Cadastro concluído!' | 'Cadastro concluído!'
| 'Gu' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Nome deve ter no mínimo 3 caracteres.' | 'Nome deve ter no mínimo 3 caracteres.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Senha deve ter no mínimo 3 caracteres.' | 'Senha deve ter no mínimo 6 caracteres.'
| 'Gustavo' | 'gustavo.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.'
| 'Gustavo' | 'gustavo@' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.'
| 'Gustavo' | 'gustavo@' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Erro: E-mail informado já existente.' | -
| 'Gustavo' | '@gmail.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Telefone deve conter 11 números (ex: 11999999999).' | 'Telefone deve conter 11 números (ex: 11999999999).'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Senha é obrigatório.' | -
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Senha deve ter no mínimo 6 caracteres.' | 'Senha deve ter no mínimo 6 caracteres.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '123' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'CRM deve ter no mínimo 4 caracteres.' | 'CRM deve ter no mínimo 4 caracteres.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | 12345 | 'Washington' | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Erro: Estado deve ser brasileiro.' | 'Erro: Estado deve ser brasileiro.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | 12345 | RN | 19/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email.com' | 'Erro: Data de formação inválida.' | 'Erro: Data de formação inválida.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | 12345 | RN | 17/08/2026 | 'Ator' | E-mail | 'gustavo@email.com' | 'Erro: Especialidade inválida.' | 'Erro: Especialidade inválida.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | 12345 | RN | 17/08/2026 | ' ' | E-mail | 'gustavo@email.com' | 'Erro: Especialidade obrigatória.' | 'Erro: Especialidade obrigatória.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | ' ' | 'gustavo@email.com' | 'Erro: Tipo de Pix inválido.' | 'Erro: Tipo de Pix inválido.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | CPF | '111' | 'Chave pix deve ter no mínimo 11 caracteres.' | 'Chave pix deve ter no mínimo 11 caracteres.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | Telefone | '111' | 'Chave pix deve ter no mínimo 11 caracteres.' | 'Chave pix deve ter no mínimo 11 caracteres.'
| 'Gustavo' | 'gustavo@email.com' | 27/09/2006 | '84999999999' | '123456' | '12345' | RN | 17/08/2026 | 'Clínico Geral' | E-mail | 'gustavo@email' | 'Chave pix deve ter E-mail válido.' | 'Chave pix deve ter E-mail válido.'

### Fluxo Secundário - Cadastro de Instituição

#### Variáveis de Entrada:
- Variável 1: Nome
- Variável 2: E-mail
- Variável 3: CNPJ
- Variável 4: Telefone
- Variável 5: Senha
- Variável 6: Endereço Completo
- Variável 7: fotoURL

#### Condições de Negócio
- Condição 1: Todos os atributos devem ser obrigatórios.
- Condição 2: Nome deve ter no mínimo 3 caracteres.
- Condição 3: Senha deve ter no mínimo 6 caracteres.
- Condição 4: E-mail deve conter '@'.
- Condição 5: E-mail deve conter texto antes do '@'.
- Condição 6: E-mail deve ter um domínio válido.
- Condição 7: Telefone deve ter 11 caracteres.
- Condição 8: CNPJ deve ter 14 caracteres.
- Condição 9: CNPJ informado deve ser válido.
- Condição 10: E-mail deve ser único.

#### Classes de Equivalência
| Classes Válidas                         | Classes Inválidas                          |
|-----------------------------------------|--------------------------------------------|
| `Nome` com >= 3 caracteres | `Nome` com < 3 caracteres
| `Senha` com >= 6 caracteres | `Senha` com < 6 caracteres
| `E-mail` deve conter '@'                | `E-mail` não conter '@'                    |
| `E-mail` deve conter texto antes do '@' | `E-mail` não conter texto antes do '@'     |
| `E-mail` deve possuir um domínio válido | `E-mail` não possuir domínio válido        |
| `E-mail` deve ser único                 | `E-mail` não ser único                     |
| `Telefone` com 11 caracteres | `Telefone` < 11 caracteres; <br> `Telefone` > 11 caracteres; 
| `CNPJ` = 14 caracteres; | `CNPJ` < 14 caracteres; <br> `CNPJ` > 14 caracteres;       |

#### Testes Funcionais
| Nome | E-mail | CNPJ | Telefone | Senha | Endereço | FotoURL | Resultado Esperado | Resultado Obtido
|---|---|---|---|---|---|---|---|---|
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '99.191.919/1919-24' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Cadastro concluído!' | 'Cadastro concluído!' 
| 'W' | 'walfredo@gmail.com' | '99.191.919/1919-24' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Nome deve ter no mínimo 3 caracteres.' | 'Nome deve ter no mínimo 3 caracteres.'
| 'Walfredo Gurgel' | 'walfredo@' | '99.191.919/1919-24' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.' 
| 'Walfredo Gurgel' | 'walfredo.com' | '99.191.919/1919-24' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.' 
| 'Walfredo Gurgel' | '@gmail.com' | '99.191.919/1919-24' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Digite um e-mail válido.' | 'Digite um e-mail válido.' 
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '12.345.678/0001-90' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Digite um CNPJ válido.' | 'Digite um CNPJ válido.'
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '123' | '84999999999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'CNPJ deve ter no mínimo 14 caracteres.' | 'CNPJ deve ter no mínimo 14 caracteres.'
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '12.345.678/0001-90' | '9999' | '123456' | 'Rua Itaperuna' | 'foto.png' | 'Telefone deve ter no mínimo 11 caracteres.' | 'CNPJ deve ter no mínimo 11 caracteres.'
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '12.345.678/0001-90' | '84999999999' | '123' | 'Rua Itaperuna' | 'foto.png' | 'Senha deve ter no mínimo 6 caracteres.' | 'Senha deve ter no mínimo 6 caracteres.'
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '12.345.678/0001-90' | '84999999999' | '123456' | '' | 'foto.png' | 'Endereço é obrigatório.' | 'Endereço é obrigatório.'
| 'Walfredo Gurgel' | 'walfredo@gmail.com' | '12.345.678/0001-90' | '84999999999' | '123456' | 'Rua Itaperuna' | '' | 'Foto é obrigatório.' | 'Foto é obrigatório.'

---

## CDU 02 - Gerenciar escala

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

---

## CDU 03 - Gerenciar plantão

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

---

## CDU 04 - Publicar Escala

#### Objetivo: 
O objetivo do Publicar Escala se trata dos profissionais líderes de escala terem poder de divulgação das escalas que entrarão em ação, saindo do estado de rascunho para publicado.

### Fluxo Principal

#### Variáveis de Entrada:
- Variável 1: ID da Escala
- Variável 2: Publicada

#### Condições de Negócio:
- Condição 1: Ambos os atributos são obrigatórios.
- Condição 2: Uma escala só pode ser publicada se tiver status como: **RASCUNHO**.
- Condição 3: Uma escala não pode ser despublicada.
- Condição 4: Uma escala já publicada não pode ser publicada novamente.
- Condição 5: O ID informado deve ser associado a uma escala existente.

#### Classes de Equivalência

| Classes Válidas                         | Classes Inválidas                          |
|-----------------------------------------|--------------------------------------------|
| `ID da Escala` > 0 e existente. | `ID da Escala` <= 0 ou inexistente.
| `ID da Escala` de status = 'RASCUNHO' | `ID da Escala` de status != 'RASCUNHO'
| `Publicada` = True | `Publicada` = False | 
| `qtd_plantoes` > 0 | `qtd_plantoes` <= 0

#### Testes Funcionais
| ID da Escala | Publicada | Resultado Esperado | Resultado Obtido |
|---|---|---|---|
| 1 (RASCUNHO, qtd_plantoes = 5) | True | 'Escala publicada com sucesso!' | 'Escala publicada com sucesso!' 
| 191919 (RASCUNHO, qtd_plantoes = 5) | True | 'Erro: Escala não encontrada.' | 'Escala não encontrada com ID 191919' 
| -20 (RASCUNHO, qtd_plantoes = 5) | True | 'Erro: Escala não encontrada.' | 'Escala não encontrada com ID -20' 
| 1 (PUBLICADA, qtd_plantoes = 5) | True | 'Erro: Só é possivel publicar escalas em rascunho.' | 'só é possivel publicar escalas em rascunho' 
| 1 (RASCUNHO, qtd_plantoes = 5) | False | 'Erro: Não é possível despublicar uma escala.' | 'Uma escala publicada não pode ser despublicada.' 
| 1 (PUBLICADA, qtd_plantoes = 5) | False | 'Erro: Não é possível despublicar uma escala.' | 'Uma escala publicada não pode ser despublicada.' 
| 1 (RASCUNHO, qtd_plantoes = 0) | True | 'Erro: Uma escala não pode ser publicada sem plantões!' | 'Escala publicada com sucesso!' 
| 1 (RASCUNHO, qtd_plantoes = -5) | True | 'Erro: Uma escala não pode ser publicada sem plantões!' | 'Escala publicada com sucesso!' 

---

## CDU 05 - Consultar Plantões Abertos

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

---

## CDU 07 - Conectar com Google Calendário

### Objetivo:
Permitir que o usuário conecte sua conta Google ao sistema para sincronizar eventos e enviar plantões ao Google Calendar.

### Variáveis de Entrada:
- Variável 1: email
- Variável 2: code
- Variável 3: maxResults

### Condições de Negócio:
- Condição 1: o usuário deve estar autenticado no sistema.
- Condição 2: o e-mail deve pertencer a um usuário existente.
- Condição 3: o código de autorização (`code`) deve ser informado e ser válido no Google OAuth.
- Condição 4: as credenciais OAuth do Google devem estar configuradas no servidor.
- Condição 5: ao conectar com sucesso, os tokens devem ser armazenados no banco de dados.
- Condição 6: ao consultar eventos, o usuário deve ter autorização prévia para o Google Calendar.
- Condição 7: ao consultar eventos, `maxResults` deve ser inteiro maior ou igual a 1.

### Classes de Equivalência
| Classes Válidas | Classes Inválidas |
|-----------------|-------------------|
| `email` de usuário existente e autenticado | `email` de usuário inexistente |
| `code` informado e válido | `code` inválido, expirado, vazio ou ausente |
| credenciais OAuth configuradas no servidor | credenciais OAuth ausentes ou inválidas |
| conexão ainda não existente e criada com sucesso | tentativa de uso sem autorização prévia |
| conexão existente no banco de dados | desconexão sem token salvo |
| `maxResults` inteiro >= 1 | `maxResults` nulo, zero, negativo ou não numérico |

### Aplicação de Valor Limite
| Variável | Regra/Limite | Valor limite inválido (abaixo) | Valor limite válido | Valor logo acima do limite | Casos adicionais de fronteira |
|----------|--------------|---------------------------------|---------------------|----------------------------|-------------------------------|
| `maxResults` | inteiro >= 1 | -1 e 0 | 1 | 2 | 20 (limite de listagem usado no sistema) |



### Fluxo Principal - Conectar Google Calendar

#### Testes funcionais

| email | code | Resultado esperado | Resultado obitido | Situação |
|-------|------|--------------------|-------------------|----------|
| "usuario@hospital.com" | "code-valido-google" | sucesso({"message": "Conta Google Calendar conectada com sucesso!"}) | --- | --- |
| "usuario@hospital.com" | "code-valido-google" | sucesso({"message": "Conta Google Calendar conectada com sucesso!"}) e conexão atualizada quando o token já existir | --- | --- |
| "usuario@hospital.com" | "code-invalido-ou-expirado" | erro({"error": "Erro ao conectar com Google Calendar: código de autorização inválido ou expirado"}) | --- | --- |
| "usuario-inexistente@hospital.com" | "code-valido-google" | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção - Callback sem code (c valor limite)

#### Testes funcionais

| email | code | Resultado esperado | Resultado obitido | Situação |
|-------|------|--------------------|-------------------|----------|
| "usuario@hospital.com" | null | erro("Requisição inválida: code não informado") | --- | --- |
| "usuario@hospital.com" | "" | erro({"error": "Erro ao conectar com Google Calendar: código de autorização inválido ou expirado"}) | --- | --- |
| "usuario@hospital.com" | "x" | erro({"error": "Erro ao conectar com Google Calendar: código inválido"}) ou sucesso caso o Google aceite o código | --- | --- |

### Fluxo Secundário - Verificar status da conexão

#### Testes funcionais

| email | Resultado esperado | Resultado obitido | Situação |
|-------|--------------------|-------------------|----------|
| "usuario@hospital.com" | sucesso({"connected": true}) | --- | --- |
| "usuario-sem-token@hospital.com" | sucesso({"connected": false}) | --- | --- |
| "usuario-inexistente@hospital.com" | sucesso({"connected": false}) | --- | --- |

### Fluxo Secundário - Listar eventos do Google Calendar

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "usuario@hospital.com" | null | 1 | sucesso(lista de eventos do Google Calendar com no máximo 1 item) | --- | --- |
| "usuario@hospital.com" | null | 2 | sucesso(lista de eventos do Google Calendar com no máximo 2 itens) | --- | --- |
| "usuario@hospital.com" | null | 10 | sucesso(lista de eventos do Google Calendar) | --- | --- |
| "usuario@hospital.com" | null | 20 | sucesso(lista de eventos do Google Calendar) | --- | --- |
| "usuario@hospital.com" | null | 0 | erro("maxResults deve ser maior ou igual a 1") ou erro de validação de requisição | --- | --- |
| "usuario@hospital.com" | null | -1 | erro("maxResults deve ser maior ou igual a 1") ou erro de validação de requisição | --- | --- |
| "usuario-sem-token@hospital.com" | null | 10 | erro("Usuário não autorizou acesso ao Google Calendar.") | --- | --- |
| "usuario-inexistente@hospital.com" | null | 10 | erro("Usuário não encontrado") | --- | --- |

### Fluxo Secundário - Desconectar Google Calendar

#### Testes funcionais

| email | Resultado esperado | Resultado obitido | Situação |
|-------|--------------------|-------------------|----------|
| "usuario@hospital.com" | sucesso({"message": "Google Calendar desconectado com sucesso!"}) | --- | --- |
| "usuario-sem-token@hospital.com" | sucesso({"message": "Google Calendar desconectado com sucesso!"}) | --- | --- |
| "usuario-inexistente@hospital.com" | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção I - Usuário não encontrado

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "email.invalido@hospital.com" | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |
| "" | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |
| null | "code-valido-google" | 10 | erro("Usuário não encontrado") | --- | --- |

### Fluxo de Exceção II - Acesso ao Google Calendar não autorizado

#### Testes funcionais

| email | code | maxResults | Resultado esperado | Resultado obitido | Situação |
|-------|------|------------|--------------------|-------------------|----------|
| "usuario-sem-token@hospital.com" | null | 10 | erro("Usuário não autorizou acesso ao Google Calendar. Porfavor, conecte sua conta.") | --- | --- |
| "usuario-sem-token@hospital.com" | null | 20 | erro("Usuário não autorizou acesso ao Google Calendar.") | --- | --- |

### Fluxo de Exceção III - Credenciais OAuth não configuradas
