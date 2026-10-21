# CDU004. Consultar Notificações e Alertas

- **Ator principal**: Usuário
- **Atores secundários**: Sistema
- **Resumo**: Permite ao usuário visualizar notificações e alertas relacionados à sua conta, receber atualizações em tempo real e acessar detalhes completos de cada notificação.
- **Pré-condição**: Usuário autenticado no sistema e com sessão ativa.
- **Pós-condição**: O usuário visualiza notificações novas e antigas, podendo consultar detalhes completos de cada alerta.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1 - Seleciona notificação específica de interesse. | |
| | 2 - Resgata dados da notificação, entidades relacionadas e as expõe ao usuário. |
| 3 - Visualiza dados da notificação e ao que ela se refere. | |

---

## Fluxos Alternativos

### Fluxo Alternativo I - [Listar notificações]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.1 - Seleciona opção de "Minhas notificações". | |
| | 1.2 - Resgata usuário logado e notificações vinculadas a ele, com informações basicas. |
| 1.3 - Visualiza lista de notificações. | |

### Fluxo Alternativo II - [Listar notificações pelo ícone]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a - Seleciona ícone de notificações na parte superior da tela. | |
| | 1.b - Resgata usuário logado e notificações vinculadas a ele, com informações basicas. |
| 1.c - Visualiza lista de notificações de forma reduzida. | |

---

## Fluxos Exceção

### Fluxo Exceção I - [Detalhar notificação não relacionada ao usuário logado]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.aa - Acessa notificação não vinculada a si. | |
| | 1.ab - Valida notificação e exibe erro informando que não é possível acessar uma notificação que não foi direcionada para o usuário em questão. |

### Fluxo Exceção II - [Detalhar notificação não existente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.aaa - Acessa notificação não registrada. | |
| | 1.aab - Valida notificação e exibe erro informando que não foi possível encontrar notificação. |