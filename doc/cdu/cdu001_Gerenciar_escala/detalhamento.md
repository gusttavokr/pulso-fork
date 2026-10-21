# CDU001. Gerenciar Escala

- **Ator principal**: Líder de Escala
- **Atores secundários**: Sistema
- **Resumo**: Permite ao líder de escala criar e gerenciar escalas médicas, inserindo dados básicos e escolhendo se deseja reutilizar uma escala modelo (template) ou iniciar uma escala vazia. Após a criação, é possível editar ou excluir a escala conforme regras de negócio.
- **Pré-condição**: O líder de escala deve estar autenticado no sistema.
- **Pós-condição**: Uma nova escala e seus plantões são criados, prontos para edição e gerenciamento.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1 - Acessa a opção “Criar Escala”. | |
| | 2 - Exibe formulário para criação da escala com campos: nome da escala, mês de referência, especialidade, instituição ([**RN48**](../../regra-de-negocio/regra-negocio.md)), setor ([**RN49**](../../regra-de-negocio/regra-negocio.md)) e opção de copiar dados da escala passada. |
| 3 - Preenche os dados do formulário e confirma. | |
| | 4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN02**, **RN03**, **RN04**, **RN05**, **RN09** e cria a nova escala, conforme **RN10**. |
| 5 - Visualiza detalhamento da nova escala criada com seus plantões. | |

---

## Fluxos Alternativos

### Fluxo Alternativo I - [Líder seleciona “Copiar dados”]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 3.1 - Informa, no formulário, que deseja criar a escala com base na escala do último mês. | |
| | 3.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN02**, **RN03**, **RN04**, **RN05**, **RN09** e cria nova escala replicando automaticamente os plantões e alocações do mês passado, ajustando-os ao mês de referência, conforme **RN07**. |
| 3.3 - Visualiza o detalhamento da nova escala criada com os plantões replicados. | |

### Fluxo Alternativo II - [Editar dados da escala]

|  Ações do Ator | Ações do Sistema |
| :------------: | :--------------: |
| 1.1 - Na tela de detalhes da escala, seleciona “Editar”. | |
|  |  1.2 - Exibe formulário de edição dos dados da escala. |
| 1.3 - Atualiza as informações e salva. | |
| | 1.4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN02** e **RN011**. |


### Fluxo Alternativo III - [Excluir escala]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a - Na tela de detalhes da escala, seleciona “Excluir”. | |
| | 1.b - Valida as [Regra de Negócio](../../regra-de-negocio/regra-negocio.md): **RN08** e remove a escala e os dados associados. |
| 1.c - Redirecionado para listagem de escalas. | |

### Fluxo Alternativo IV - [Detalhar escala]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a2 - Seleciona escala específica da listagem de escalas ou plantão de referencia. | |
| | 1.b2 - Recupera os dados da escala, plantões, alocações e profissionais para amostragem. |
| 1.c2 - Visualiza escala montada. | |

### Fluxo Alternativo V - [Detalhar escala por plantão]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.1 - Seleciona "Ver escala relacionada" no detalhamento do plantão. | |
| | 1.2 - Recupera dados da escala selecionada, plantões, alocações e profissionais para amostragem. |
| 1.3 - Visualiza escala montada. | |

### Fluxo Alternativo VI - [Listar escala]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a3 - Seleciona opção de "Minhas escalas". | |
| | 1.b3 - Recupera escalas vinculadas ao usuário logado com base na [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN50**, **RN51** e **RN52**. |
| 1.c3 - Visualiza listagem de escalas. | |

---

## Fluxos Exceção

### Fluxo Exceção I - [Falha na validação dos dados]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 0.1 - Preenche formulário de criação da escala com dados incorretos ou incompletos. | |
| | 0.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN02**, **RN03**, **RN04**, **RN05**, **RN09**. |
| 0.3 - Visualiza mensagem de erro destacando os campos inválidos ou ausentes. | |

### Fluxo Exceção II - [Excluir escala já publicada]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a.1 - Na tela de detalhes da escala, seleciona “Excluir”. | |
| | 1.a.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN08**. |
| 1.a.3 - Visualiza mensagem de erro informando que não é possível excluir uma escala já publicada. | |

### Fluxo Exceção III - [Detalhar escala inexistente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a2.1 - Seleciona escala específica da listagem de escalas ou plantão de referencia. | |
| | 1.a2.2 - Verifica existência da escala e, se não encontrada, exibe mensagem de erro informando que não foi possível localizar a escala. |

---

## Protótipo
### Criar

<img width="1920" height="1080" alt="Criar nova escala" src="https://github.com/user-attachments/assets/4b676bf8-fdbb-439d-8699-b668ccd70b65" />
<img width="1920" height="1080" alt="Criar nova escala 4" src="https://github.com/user-attachments/assets/df9621c9-19cb-4c4f-9e69-1ba7e90e1793" />

### Listar escala

<img width="1920" height="1080" alt="Minhas escalas" src="https://github.com/user-attachments/assets/ebe7fe79-49da-47d4-bd00-f6f876ee0b95" />

### Detalhar, editar e excluir escala

<img width="1920" height="1080" alt="Minhas escalas" src="https://github.com/user-attachments/assets/cee0e63a-3bca-4adc-ab9f-b4c4c969a104" />
<img width="1920" height="1080" alt="Minhas escalas" src="https://github.com/user-attachments/assets/da975a2e-d2c9-4081-9c27-676273b0c3e8" />

### Detalhar por plantão

<img width="1920" height="1080" alt="Detalhe plantão - Médico" src="https://github.com/user-attachments/assets/871fa005-1cf9-4e0e-82b0-27288c34049b" />

## Diagramas de Atividade

### Criar Escala
<img src="https://github.com/user-attachments/assets/cedd797b-77e3-4428-9dbe-8e48941f0481" alt="CDU - Criar Escala" width="800"/>

### Listar Escalas
<img src="https://github.com/user-attachments/assets/8e378210-73b0-428e-bbfe-af867fac944d" alt="CDU - Listar Escalas" width="800"/>

### Detalhar Escala
<img src="https://github.com/user-attachments/assets/d0dd131c-4ca6-4d93-a783-6a1d49ae1f1b" alt="CDU - Detalhar Escala" width="800"/>

### Atualizar Escala
<img src="https://github.com/user-attachments/assets/5b867aa1-5e3b-4163-8c32-c6ee07a1f17f" alt="CDU - Atualizar Escala" width="800"/>

### Excluir Escala
<img src="https://github.com/user-attachments/assets/6b02243d-0530-40f6-a922-7bc134894e76" alt="CDU - Excluir Escala" width="800"/>
