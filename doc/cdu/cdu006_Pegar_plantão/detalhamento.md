# CDU06. Pegar plantão

* **Ator principal**: Médico PJ
* **Atores secundários**: Líder de escala e Médico PJ
* **Resumo**: Permite que os médicos PJ vinculem-se a plantões disponíveis, seja diretamente enviado pelo líder ou vindo de um repasse de outro médico.
* **Pré-condição**: Deve existir pelo menos um plantão disponível (pelo líder ou por repasse) no feed e o médico deve possuir a especialidade necessária para assumir o plantão.
* **Pós-condição**: O sistema registra a alocação do médico ao plantão e confirma troca, caso exista, finalizando com o médico passando a ser o responsável pelo plantão.

### Fluxo Principal

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1 - O médico seleciona plantão disponível de seu interesse. | |
| | 2 - O sistema exibe dados do plantão selecionado. |
| 3 - O médico seleciona "Pegar plantão". | |
| | 4 - O sistema valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN58**, **RN59**, **RN60** e **RN61** e cria a alocação do médico para o plantão. |
| 5 - O médico visualiza plantão agora vinculado a si. | |

---

## Fluxos Alternativos

### Fluxo Alternativo I - [Pegar plantão de repasse]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.1 - O médico seleciona "Pegar plantão" de um plantão vindo de um repasse. | |
| | 3.2 - O sistema valida profissional, plantão, especialidade, troca e alocação. |
| | 3.3 - O sistema troca profissional da alocação já criada para o plantão e muda status da troca para Confrimada. |

---

## Fluxos Exceção

### Fluxo Exceção I - [Profissional não encontrado]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 4.1 - O sistema valida profissional, plantão e especialidade. |
| | 4.2 - O sistema exibe mensagem de erro informando que não foi possível encontrar um profissional relacionado ao usuário logado. |

### Fluxo Exceção II - [Plantão não encontrado]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 4.a - O sistema valida profissional, plantão e especialidade. |
| | 4.b - O sistema exibe mensagem de erro informando que não foi possível encontrar o plantão desejado a ser pego. |

### Fluxo Exceção III - [Profissional não vinculado a especialidade do plantão]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 4.aa - O sistema valida profissional, plantão e especialidade. |
| | 4.ab - O sistema exibe mensagem de erro informando que plantão escolhido não é de uma especialidade do profissional. |

### Fluxo Exceção IV - [Troca não encontrada]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 3.2.1 - O sistema valida profissional, plantão, especialidade, troca e alocação. |
| | 3.2.2 - O sistema exibe mensagem de erro informando que troca do plantão escolhido não foi encontrada. |

### Fluxo Exceção V - [Alocação não encontrada]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 3.2.a - O sistema valida profissional, plantão, especialidade, troca e alocação. |
| | 3.2.b - O sistema exibe mensagem de erro informando que alocação original do plantão escolhido não foi encontrada. |

---

## Protótipo
<img width="1920" height="1080" alt="Detalhe plantão - Médico" src="https://github.com/user-attachments/assets/5e1158c7-cc29-49d1-8191-30f3ec5d7a56" />

## Diagrama de Atividade
<img width="2324" height="1438" alt="CDU - Pegar Plantao" src="https://github.com/user-attachments/assets/98f758c6-5408-4205-a1f5-4c0e916381df" />
