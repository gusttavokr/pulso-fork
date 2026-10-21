# CDU012. Conectar com Google Calendário

* **Ator principal**: Usuário
* **Atores secundários**: Sistema
* **Resumo**: Permite abrir conexão para coleta de dados do Google Calendário e exposição no sistema e dados do sistema serem enviados para o Google calendário.
* **Pré-condição**: O usuário deve estar devidamente logado e ter uma conta google.
* **Pós-condição**: Dados de plantões e informações do Google Calendário são compartilhados entre os sistemas.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1 - Seleciona opção de "Conectar-se ao Google Calendário". | |
| | 2 - Exibe tela de confirmação de dados do Google. |
| 3 - Informa e-mail e confirma. | |
| | 4 - Estabele conexão e resgata dados do Google calendário para exibir no sistema. |

---

## Fluxos Execeção

### Fluxo Exceção I - [E-mail inválido]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.a - O usuário informa um e-mail invalido| |
| | 3.b - O sistema exibe mensagem de erro informando que não foi possível estabeler conexão com o Google Calendário. |

---

## Protótipo
<img width="1920" height="1080" alt="Index - Líder de Escala" src="https://github.com/user-attachments/assets/a3c8ce6c-9737-453a-9cdf-7fd782e8b1e5" />
