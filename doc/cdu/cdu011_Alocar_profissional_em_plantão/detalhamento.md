# CDU011. Alocar profissional

* **Ator principal**: Líder de escala
* **Atores secundários**: Médico PJ e CLT
* **Resumo**: Permite que profissionais aloquem diretamente um profissional ao plantão.
* **Pré-condição**: O líder de escala deve estar devidamente logado e o médico cadastrado no sistema.
* **Pós-condição**: Listagem de profissionais alocados para o plantão vai estar preenchida com os profissionais selecionados.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1 - O líder de escala ao detalhar um plantão seleciona opção de "Alocar profissionais." | |
| | 2 - Exibe os dados dos profissionais possíveis de serem alocados de acordo com [Regra de Negócio](../../regra-de-negocio/regra-negocio.md): **RN63**. |
| 3 - O líder seleciona profissionais se interesse, aloca e confirma. | |
| | 4 - Valida as [Regra de Negócio](../../regra-de-negocio/regra-negocio.md): **RN63** e adiciona profissionais a listagem de profissionais combinados para o plantão. |

---

## Fluxos Execeção

### Fluxo Exceção I - [Profissional não corresponde as restrições]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.a - O líder seleciona um profissional que não atende a [Regra de Negócio](../../regra-de-negocio/regra-negocio.md): **RN63**. | |
| | 3.b - O sistema exibe mensagem de erro relacioado a regra de negócio. |

---

## Protótipo
![WhatsApp Image 2026-01-29 at 16 32 55](https://github.com/user-attachments/assets/cc0b974d-96a6-400f-a17b-ec26bda8772f)
![WhatsApp Image 2026-01-29 at 16 33 45](https://github.com/user-attachments/assets/8aea1ec0-cd50-42bb-9f51-6b33dbce2348)
![WhatsApp Image 2026-01-29 at 16 36 19](https://github.com/user-attachments/assets/a914af3b-a556-4df4-839e-dbf4709710ee)
