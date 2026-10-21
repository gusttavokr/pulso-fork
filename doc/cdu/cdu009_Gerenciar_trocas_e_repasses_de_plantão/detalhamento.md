# CDU009. Gerenciar troca de plantão

* **Ator principal**: Médico PJ
* **Atores secundários**: Médico PJ e Líder de Escala
* **Resumo**: Permite que médicos PJ solicitem a troca de um plantão em que já estão alocados com algum outro médico escolhido pelo solicitante.
* **Pré-condição**: O médico PJ estar devidamente autenticado no sistema e ter um plantão vinculado a si.
* **Pós-condição**: Plantão devidamente encaminhado para o médico solicitado e registro de troca aberto e salvo no sistema.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1 - O médico seleciona "Criar nova troca" ou "Trocar/Repassar" pelo plantão. | |
| | 2 - O sistema exibe formulário de criação de troca. |
| 3 - O médico informa plantão que deseja trocar, médico que deseja fazer a troca e comentário da troca. | |
| | 4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN27**, cria nova troca do tipo "dupla" e envia solicitação para o médico solicitado. |

## Fluxos Alternativos

### Fluxo Alternativo I – [Repassar plantão]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.1 - O médico informa plantão que deseja trocar e comentário de troca. | |
| | 3.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN27** e cria nova troca do tipo "simples". |

### Fluxo Alternativo II – [Atualizar troca]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.1 - O médico seleciona troca de interesse para atualização.  | |
| | 1.2 - O sitema exibe dados atuais da troca para edição. |
| 1.3 - O médico envia dados atualizados da troca para o sistema. | |
| | 1.4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN42**, **RN43**, **RN44** e envia aviso de mudança para o médico solicitado, se tiver. |

### Fluxo Alternativo III – [Excluir troca]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.1.1 - O médico seleciona troca de interesse para exclusão. | |
| | 1.1.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN29** e remove dos registros do médico. |

### Fluxo Alternativo IV – [Detalhar troca]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.a - O médico seleciona troca de interesse para ver dados de detalhamento. | |
| | 1.b - O sistema resgata informações da troca, plantão e médico solicitado, se tiver. |
| 1.c - O médico visualiza dados reunidos. | |

### Fluxo Alternativo V - [Listar trocas]
| Ações do Ator | Ações do Sistema  |
|---------------|-------------------|
| 1.a.1 - O médico seleciona opção de "Minhas solicitações". | |
| | 1.a.2 - Sistema resgata trocas feitas pelo médico loado. |

---

## Fluxos Execeção

### Fluxo Exceção I - [Atualizar campos inválidos da troca]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.3.1 - O médico envia dados atualizados da troca já confirmada ou recusada para o sistema. | |
| | 1.3.2 - O sistema exibe mensagem de erro relacionadas a [**RN42**, **RN43** e **RN44**](../../regra-de-negocio/regra-negocio.md). |

### Fluxo Exceção II - [Excluir troca confirmada ou recusada]
| Ações do Ator | Ações do Sistema  |
|---------------|-------------------|
| 1.1.1.1 - O médico seleciona troca confimada ou recusada de interesse para exclusão. | |
| | 1.1.1.2 - O sistema exibe mensagem de erro relacionada à [**RN29**](../../regra-de-negocio/regra-negocio.md). |

### Fluxo Exceção III - [Interação com troca inexistente]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.a - O médico informa plantão inexistente para trocar | |
| | 3.b - O sistema exibe mensagem de erro informando que não foi possível encontrar o plantão na base de dados |

---

## Diagramas de Atividade

### Criar troca de plantão
<img width="2740" height="1520" alt="CDU - Criar Troca de Plantao" src="https://github.com/user-attachments/assets/c3e7d6cd-5a78-4e31-8f8a-3f78f2054986" />