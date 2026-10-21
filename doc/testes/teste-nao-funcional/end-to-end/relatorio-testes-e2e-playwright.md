# Relatório de Testes E2E - Playwright Pulso

## Resumo

Foram executados testes End-to-End (E2E) utilizando o framework **Playwright** para validar fluxos críticos da aplicação Pulso através da interface do usuário.

O objetivo dos testes foi garantir que funcionalidades principais do sistema pudessem ser realizadas simulando o comportamento real de um usuário, desde a autenticação até fluxos relacionados à criação de escalas e cadastro de usuários.

Os testes foram executados utilizando o navegador **Chromium** em ambiente local.

---

## Ambiente de Teste

| Item                | Configuração              |
| ------------------- | ------------------------- |
| Framework de teste  | Playwright                |
| Tipo de teste       | End-to-End (E2E)          |
| Navegador utilizado | Chromium                  |
| Frontend            | Angular                   |
| URL da aplicação    | http://localhost:4200     |
| Execução            | Ambiente local com Docker |

---

## Critérios de Aceitação

| Critério                                                          | Resultado                   | Status |
| ----------------------------------------------------------------- | --------------------------- | ------ |
| Aplicação deve carregar corretamente                              | Página acessível            | ✅     |
| Usuário deve conseguir realizar login                             | Login realizado com sucesso | ✅     |
| Usuário autenticado deve acessar Minhas Escalas                   | Página carregada            | ✅     |
| Usuário autenticado e líder de escala deve conseguir criar escala | Escala criada               | ✅     |
| Usuário deve conseguir realizar cadastro                          | Usuário criado              | ✅     |

---

# Resultados dos Cenários Testados

| Cenário                                       | Resultado             | Status |
| --------------------------------------------- | --------------------- | ------ |
| Acesso à aplicação                            | Executado com sucesso | ✅     |
| Login com usuário válido                      | Executado com sucesso | ✅     |
| Navegação para Minhas Escalas                 | Executado com sucesso | ✅     |
| Criação de Escalas                            | Executado com sucesso | ✅     |
| Cadastro de Usuários profissional/instituição | Executado com sucesso | ✅     |

---

# Teste 1 - Criação de Escala

## Objetivo

Validar que um usuário autenticado com permissão de líder de escala consegue acessar a área de Minhas Escalas e realizar a criação de uma nova escala.

Este teste valida os critérios:

- Usuário deve conseguir realizar login.
- Usuário autenticado deve acessar Minhas Escalas.
- Usuário autenticado e líder de escala deve conseguir criar escala.

## Critérios validados

| Critério                                                             | Resultado                                                                | Status |
| -------------------------------------------------------------------- | ------------------------------------------------------------------------ | ------ |
| Usuário deve conseguir realizar cadastro de profissional/instituição | Login realizado com sucesso através de usuário líder de escala           | ✅     |
| Usuário deve conseguir realizar login                                | Login realizado com sucesso através de usuário líder de escala           | ✅     |
| Usuário autenticado deve acessar Minhas Escalas                      | Página "Minhas Escalas" carregada corretamente após autenticação         | ✅     |
| Usuário autenticado e líder de escala deve conseguir criar escala    | Modal de criação aberto e fluxo de criação executado até a segunda etapa | ✅     |

## Resultado

O cenário foi executado com sucesso.

A aplicação permitiu que o usuário:

- realizasse autenticação;
- acessasse a página de Minhas Escalas;
- abrisse o modal de criação de escala;
- preenchesse as informações necessárias;
- avançasse pelas etapas do formulário;
- concluísse a criação da escala.

A nova escala foi criada corretamente e ficou disponível no sistema após a finalização do fluxo, confirmando que um usuário autenticado com permissão de líder consegue realizar o cadastro de uma escala.

Status: ✅ Aprovado

# Teste 2 - Cadastro de Usuário Profissional/Instituição

## Objetivo

Validar o fluxo de cadastro de um novo usuário profissional/instituição através da interface da aplicação Pulso.

O teste verifica se o sistema permite preencher as informações necessárias e concluir o cadastro corretamente.

## Fluxo executado

- Acessar a tela de cadastro.
- Preencher os dados solicitados.
- Informar dados profissionais.
- Selecionar informações relacionadas à instituição.
- Enviar o formulário.
- Validar que o usuário foi criado.

## Resultado

O cenário foi executado com sucesso.

A aplicação permitiu que o usuário:

- acessasse a tela de cadastro;
- preenchesse as informações obrigatórias;
- realizasse o envio do formulário;
- concluísse o cadastro.

O usuário foi criado corretamente, atendendo ao critério de aceitação relacionado ao cadastro profissional/institucional.

Status: ✅ Aprovado

---

# Validação dos Critérios de Aceitação

| Critério                                                          | Resultado                              | Status |
| ----------------------------------------------------------------- | -------------------------------------- | ------ |
| Aplicação deve carregar corretamente                              | Página acessível                       | ✅     |
| Usuário deve conseguir realizar login                             | Login realizado com sucesso            | ✅     |
| Usuário autenticado deve acessar Minhas Escalas                   | Página carregada corretamente          | ✅     |
| Usuário autenticado e líder de escala deve conseguir criar escala | Fluxo de criação executado com sucesso | ✅     |
| Usuário deve conseguir realizar cadastro                          | Usuário criado com sucesso             | ✅     |

---

# Conclusão

Os testes End-to-End utilizando Playwright validaram os principais fluxos da aplicação Pulso através da simulação de ações realizadas por um usuário real.

Foram validados os seguintes cenários:

- acesso à aplicação;
- autenticação com usuário válido;
- navegação para Minhas Escalas;
- criação de escalas;
- cadastro de usuários profissional/instituição.

Todos os critérios de aceitação definidos foram atendidos durante a execução dos testes.

Resultado final:

✅ Todos os cenários executados foram aprovados.

---

# Execução dos Testes

Para executar todos os testes:

```bash
npx playwright test --project=chromium
```

Se desejar testar um arquivo especifico:

```bash
npx playwright test tests/cadastro.spec.ts --project=chromium
```

Se desejar testar com a interface do navegador aberta:

```bash
npx playwright test tests/cadastro.spec.ts --project=chromium --headed
```
