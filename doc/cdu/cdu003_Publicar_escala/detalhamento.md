# CDU003. Publicar Escala

* **Ator principal**: Líder de escala
* **Atores secundários**: Sistema e Médicos
* **Resumo**: Permite ao líder de escala publicar uma escala finalizada. O sistema altera o status da escala de "rascunho" para "publicada" e notifica os médicos alocados, permitindo que visualizem seus plantões.
* **Pré-condição**: O líder de escala está autenticado e acessou a tela de detalhamento de uma escala ainda em rascunho.
* **Pós-condição**: A escala passa a estar publicada e visível para os médicos nos seus respectivos plantões.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1 - Acessa a tela de detalhamento da escala e clica no botão "Publicar". | |
| | 2 - Busca escala selecionada, valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN06** e **RN40**, atualiza o status da escala de “Rascunho” para “Publicada”. |
| 3 - Visualiza escala agora publicada. | |

---


## Fluxos Exceção

### Fluxo Exceção I - [Publicar escala já publicada]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.1 - Clicar no botão "Publicar" de uma escala já publicada. | |
| | 1.2 - Busca escala selecionada e exibe mensagem de erro relacionado à [**RN040**](../../regra-de-negocio/regra-negocio.md). |

### Fluxo Exceção II - [Publicar escala inexistente]

| Ações do Ator | Ações do Sistema |
| :-----------: | :--------------: |
| 1.1.1 - Clicar no botão "Publicar" de uma escala não existete. | |
| | 1.1.2 - Busca escala selecionada e exibe mensagem de erro informando que não foi possível encontrar a escala selecionada. |

### Fluxo Exceção III - [Publicar escala sem plantões]

| Ações do ator | Ações do Sistema |
|---------------|------------------|
| 1.a - Clicar no botão "Publicar" de uma escala sem plantões associados. | |
| | 1.b - Busca escala selecionada e exibe mensagem de erro relacionado à [**RN06**](../../regra-de-negocio/regra-negocio.md). |

---

## Protótipo

<img width="1920" height="1080" alt="Minhas escalas" src="https://github.com/user-attachments/assets/a0a418cb-1ed2-4c09-b3bf-c23bb50952d5" />
