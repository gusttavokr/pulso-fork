# CDU008. Gerenciar setores

* **Ator principal**: RH da instituição 
* **Atores secundários**: Sistema
* **Resumo**: O RH do hospital pode criar, editar, visualizar e remover setores do hospital. Esses setores representam áreas físicas específicas dentro das especialidades da instituição e são utilizados para organizar escalas, plantões e alocação de médicos.  
* **Pré-condição**: O hospital deve estar previamente cadastrado no sistema junto com suas especialidades.  
* **Pós-condição**: O setor estará cadastrado (ou atualizado/removido) e poderá ser utilizado nos plantões, escalas e outras funcionalidades do sistema.  

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
| ------------- | ---------------- |
| 1 - Seleciona a opção "Adicionar novo setor". | |
| | 2 - Exibe formulário de criação de um novo setor. |
| 3 - Informa nome do setor e a qual especialidade do hospital ele está relacionado. | |
| | 4 - Valida entradas e cria novo setor com as informações fornecidas. |

---

## Fluxos Alternativos

### Fluxo Alternativo I - [Atualizar setor]

| Ações do Ator | Ações do Sistema |
| ------------- | ---------------- |
| 1.1 - Seleciona opção de atualizar em setor específico. | |
| | 1.2 - Exibe formulário com os dados atuais. |
| 1.3 - Edita as informações desejadas e confirma. | |
| | 1.4 - Valida entradas, procura setor e o atualiza na lista. |

### Fluxo Alternativo II - [Excluir setor]

| Ações do Ator | Ações do Sistema |
| ------------- | ---------------- |
| 1.a - Seleciona opção de excluir em um setor específico. | |
| | 1.b - Procura setor e o apaga da base de dados. |

### Fluxo Alternativo III - [Listar setores]

| Ações do Ator | Ações do Sistema |
| ------------- | ---------------- |
| 1.aa - Seleciona opção de "Meus setores". | |
| | 1.ab - Procura instituição logada e resgata seus setores e especialidades. |

---

## Fluxos Exceção 

### Fluxo Exceção I - [Acessar setor não inexistente]

| Ações do Ator | Ações do Sistema |  
| ------------- | ---------------- |
| 1.1.1 - Seleciona ação em setor inexistente. | |
| | 1.1.2 - Exibe mensagem de erro informando que não foi possível encontrar setor selecionado. |