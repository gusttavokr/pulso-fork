# CDU07. Consultar Plantões Abertos

* **Ator principal**: Médico
* **Atores secundários**: Sistema
* **Resumo**: O médico acessa o feed de plantões abertos - seja disponiveis por escalas publicadas ou vindas de algum repasse de outro médico. É possivel aplicar filtros ou buscas para visualizar os plantões de seu interesse, como por hospital, localidade, setor, sala ou especialidade.
* **Pré-condição**: Médico logado no sistema e plantões disponíveis vindo de escalas publicadas ou de repasses.
* **Pós-condição**: O sistema exibe a lista de plantões de acordo com os filtros aplicados pelo médico.

## Fluxo Principal

| Ações do Médico | Ações do Sistema |
|-----------------|------------------|
| 1 - Acessa o feed de plantões abertos. | |
| | 2 - Busca plantões abertos de acordo com [Regra de Negócio](../../regra-de-negocio/regra-negocio.md): **RN62**. |
| 3 - Visualiza os plantões que são classificados como abertos. | |

---

## Protótipo

<img width="1920" height="1080" alt="Plantões abertos" src="https://github.com/user-attachments/assets/dea0e57f-3adf-4320-835c-b2c65a6c2ab8" />
