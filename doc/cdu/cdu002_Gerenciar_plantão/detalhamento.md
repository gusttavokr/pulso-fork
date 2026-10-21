# CDU002. Gerenciar Plantão

- **Ator principal**: Líder de escala
- **Atores secundários**: Sistema
- **Resumo**: Permite ao líder de escala criar, editar ou excluir plantões dentro de uma escala já criada, alocando profissionais e ajustando os dados necessários.
- **Pré-condição**: O líder de escala está autenticado e acessou a tela de detalhamento de uma escala existente.
- **Pós-condição**: A escala refletirá corretamente os plantões criados, atualizados ou removidos.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 0 - Acessa a tela de detalhamento da escala. | |
|  | 1 - Exibe escala completa e seus plantões. |
| 2 - Seleciona opção de criar escala. | |
| |  3 - Exibe formulário de plantão com campos obrigatórios dia de inicio, dia de fim, hora de inicio, hora de fim e quantidade de profissionais e campos opcionais de estimativa do valor do plantão, qual a forma de pagamento desse valor, data estimada para o pagamento do plantão e comentário. |
| 4 - Preenche formulário e clica em salvar. | |
| | 5 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN12**, **RN13**, **RN14**, **RN23**, **RN39**, **RN40**, **RN53**, **RN54**, cria o plantão, aloca o profissional e exibe o plantão na tela, conforme **RN10**. |

---

## Fluxos Alternativos

### Fluxo Alternativo I – [Atualizar plantão existente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
|  1.1 - Seleciona opção de atualizar em um plantão já criado na escala. | |
| | 1.2 - Exibe formulário com dados do plantão já preenchidos. |
| 1.3 - Altera campos desejados e clica em salvar. | |
| | 1.4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN12**, **RN13**, **RN14**, **RN16**, **RN23**, **RN40**, **RN53**, **RN54**, atualiza os dados do plantão e exibe-o atualizado na tela. |

### Fluxo Alternativo II – [Excluir plantão]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.1.1 - Clica em um plantão existente e seleciona a opção excluir. | |
| | 1.1.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN16**, **RN24**, remove o plantão da escala e apaga seus dados. |

### Fluto Alternativo III - [Detalhar plantão]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a - Seleciona um plantão. | |
| | 1.b - Busca pelo plantão na base de dados junto com suas alocações e profissionais. |
| 1.c - Visualiza dados do plantão. | |

### Fluxo Alternativo IV - [Listar plantões]

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.1 - Acessa a opção "Meus plantões". | |
| | 1.2 - Consulta a base de dados e exibe a lista de planões relacionados ao médico logado. |
| 1.3 - Visualiza os plantões listados. | |

---

## Fluxos Exceção

### Fluxo Exceção I - [Criando um plantão para um horário/dia/setor de plantão já existente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 2.1 - Seleciona um horário em determinado dia. | |
| | 2.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN39** e exibe mensagem de erro correspondente. |

### Fluxo Exceção II - [Removendo plantão já pego ou com data passada]

| Ações do Ator  | Ações do Sistema |
| :------------: | :--------------: |
| 1.1.2.1 - Seleciona a opção excluir. | |
| | 1.1.2.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN16**, **RN24** e exibe mensagem de erro correspondente. |

### Fluxo Exceção III - [Buscando plantão inexistente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.a - Seleciona um plantão. | |
| | 1.a.1 - Exibe mensagem de erro informando que não foi possível encontrar plantão na base de dados. |

### Fluxo Exceção IV - [Criando um plantão com horário de entrada/saída fora do mês de referência da sua escala]

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 2.1.1 - Seleciona um horário de um determinado dia. | |
| | 2.1.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN40** e exibe mensagem de erro correspondente. |

---

## Protótipo

### Criar e atualizar plantão

<img width="1920" height="1080" alt="Criando plantão" src="https://github.com/user-attachments/assets/feb53a28-3863-4146-95dd-cbdbf7df9b45" />
<img width="1920" height="1080" alt="Criando plantão 2" src="https://github.com/user-attachments/assets/5de3d9f2-366f-4534-9988-db9970874ae8" />

### Detalhar plantão

<img width="1920" height="1080" alt="Detalhe plantão - Médico" src="https://github.com/user-attachments/assets/eb21267b-c7ea-42a8-bf12-480419ee20b6" />
<img width="1920" height="1080" alt="Detalhe plantão - Médico" src="https://github.com/user-attachments/assets/85779e3f-19e5-45e9-a98a-fd5558d06e8f" />

### Listagem de plantões

<img width="1920" height="1080" alt="Meus plantões" src="https://github.com/user-attachments/assets/6ef3885a-cc25-4b89-aa43-c3e956f1093c" />

## Diagramas de Atividade

### Criar Plantão
<img width="1181" height="1210" alt="CDU - Criar Plantão" src="https://github.com/user-attachments/assets/55da288c-b587-4eab-b879-94c961b9b478" />

### Detalhar Plantão
<img width="1068" height="929" alt="CDU - Detalhar Plantão" src="https://github.com/user-attachments/assets/49a22c33-2179-47cf-8327-cf2c79ee2611" />

### Atualizar Plantão
<img width="1129" height="1224" alt="CDU - Atualizar Plantão" src="https://github.com/user-attachments/assets/2695a7b8-7e7b-4eb5-b7fd-b2bbb2c36821" />

### Excluir Plantão
<img width="838" height="1129" alt="CDU - Excluir Plantão" src="https://github.com/user-attachments/assets/52d9ceb0-6eef-4486-b957-cf4422c9d6fe" />

## Diagrama de Interação (Sequência ou Comunicação)

## Diagrama de Classes de Projeto
