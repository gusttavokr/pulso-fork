# Relatório de Testes Manuais - Gustavo

## Histórico de Revisões
| **Data**   | **Versão** | **Descrição**   | **Autor** | 
| ---------- | ---------- | --------------- | --------- |
| 02/06/2026 | 1.0        | Primeira versão | [Gustavo Maia](https://github.com/gusttavokr)

## RF10 - Acesso às Escalas (CDU-001 e CDU-003)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1  | Minhas Escalas | Listar Escalas | Listagem Válida | usuarioId: 1 <br> mesReferencia: null <br> especialidade: null <br> liderEscala: true <br> instituicao: null  | lista(Escala, Escala, Escala, ...) | lista(Escala, Escala, Escala, ...) | Passou | -|
| 2  | Minhas Escalas | Listar Escalas | Listagem Não sendo Líder | usuarioId: 2 <br> mesReferencia: null <br> especialidade: null <br> liderEscala: false <br> instituicao: null  | erro("Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true") | Nenhum encontrado. | Passou | -|

## RF11 - Criação de Escalas (CDU-001)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1  | Minhas Escalas | Criar Nova Escala | Criação Válida | Nome: "Escala Julho" <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | Escala(nome: "Escala fevereiro", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | "Escala criada com sucesso!" | Passou | -|
| 2  | Minhas Escalas | Criar Nova Escala | Criação com Nome Vazio | Nome: "" <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | erro("Nome não foi informado") | "Preencha todos os campos obrigatórios" | Passou | -|
| 3  | Minhas Escalas | Criar Nova Escala | Nome com menos do mínimo de caracteres | Nome: "Es" <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | 	erro("Nome informado tem menos de 3 caracteres") | "Erro ao criar escala" | Passou | -|
| 4  | Minhas Escalas | Criar Nova Escala | Nome com mínimo caracteres | Nome: "Esc" <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | Escala(nome: "Escala fevereiro", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | "Escala criada com sucesso!" | Passou | - 
| 5  | Minhas Escalas | Criar Nova Escala | Nome com máximo de caracteres | Nome: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | Escala(nome: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in ", mes: "2026-02-01", copia: false, setorId: 1 -> Setor(id: "1"), status: RASCUNHO) | "Erro ao criar escala" | Não passou | -
| 6  | Minhas Escalas | Criar Nova Escala | Nome com mais do máximo de caracteres | Nome: "Esta string serve para testar a contagem de caracteres e garantir que ela tenha exatamente o tamanho exato solicitado, contendo exatamente trezentos caracteres no total. Vamos conferir se o comprimento está correto e se nenhum espaço a mais ou a menos foi inserido por acidente aqui." <br> Mês: "2026-07-01" <br> Copia: false <br> SetorId: 1 | erro("Nome informado tem maos de 255 caracteres") | "Erro ao criar escala" | Não passou | -
| 7  | Minhas Escalas | Criar Nova Escala | Criação com Mês Vazio | Nome: "Teste" <br> Mês: "" <br> Copia: false <br> SetorId: 1 | erro("Nome não foi informado") | "Preencha todos os campos antes de continuar." | Passou | -|
| 8  | Minhas Escalas | Criar Nova Escala | Criação com Mês Passado | Nome: "Teste" <br> Mês: "2026-05-01" <br> Copia: false <br> SetorId: 1 | erro("Mês de referência informado está no passado") | "Erro ao criar escala." | Passou | -|

## RF13 - Envio de Oportunidade "Publicar Escala" (CDU-001)
| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1  | Detalhar Escala | Publicar Escala | Publicação Válida | idEscala: 1 <br> Publicado: True  | 'Escala publicada com sucesso!' |'Escala publicada com sucesso!' | Passou | -|
| 2  | Detalhar Escala | Publicar Escala | Publicação de Escala já publicada | idEscala: 1 <br> Publicado: True  | 'Erro: Só é possivel publicar escalas em rascunho.' |'Escala publicada com sucesso!' | Não Passou | - |

## ⚠️ Observações
> Foi identificado que, embora tenha um retorno compreensível para os usuários. O sistema não te explica que erro foi encontrado. Por exemplo: "Erro ao criar escala", não te indica se foi erro de mês inválido, nome inválido.
