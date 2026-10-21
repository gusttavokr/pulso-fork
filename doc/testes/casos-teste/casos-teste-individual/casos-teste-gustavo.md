# Documento Geral de Casos de Teste - Gustavo

## Introdução
Este documento reúne os casos de teste elaborados para validar os principais fluxos da aplicação Pulso, desenvolvida no contexto do PDS Corporativo. Os testes aqui descritos abrangem diferentes caoss de uso da aplicação, com o objetivo de assegurar que cada funcionalidade opere conforme os requisitos definidos.

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

## CDU 02 - Publicar Escala

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