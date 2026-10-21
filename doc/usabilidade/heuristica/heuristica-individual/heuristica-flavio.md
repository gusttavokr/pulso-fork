# Avaliação por inspeção com lista Eureca

Este documento apresenta a avaliação individual das interfaces do sistema Pulso, realizada pelo avaliador Flavio da silva matias, com base nas heurísticas Eureca (Matos & Freire).

Foram analisadas as interfaces relacionadas aos casos de uso: gerenciar escala, gerenciar plantão, alocar profissional em plantão, consultar plantões abertos, pegar plantão e dashboard.

## Problemas Gerais (Sistema)

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | **Inconsistência de Tema nos Inputs:** Devido à herança do tema do navegador, alguns campos de texto aparecem escuros em modais claros, dificultando a leitura e quebrando a estética. | FM6–Consistência interna e FM11–Contraste | Forçar via CSS que os inputs ignorem o tema do SO ou implementar suporte oficial a Dark Mode. | Média | Médio |

<img width="607" height="470" alt="image" src="https://github.com/user-attachments/assets/1acc716e-58d0-4f50-b6f4-466ecb7a2082" />


---

## CDU 01 - Gerenciar escala

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual especialidade cada escala faz referência | CO4–Metáfora | Adicionar ícones assim como existem nos plantões | Média | Baixo |

<img width="796" height="495" alt="image" src="https://github.com/user-attachments/assets/150eae7d-f695-4eae-a681-d3aed3d2e716" />



| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Os passos do modal de criação (1, 2, 3) não possuem rótulos descritivos. | FM1–Visibilidade | Adicionar nomes às etapas (ex: 1. Identificação, 2. Horários). | Média | Baixo |

<img width="1115" height="585" alt="image" src="https://github.com/user-attachments/assets/973ca133-63e1-468e-bae4-86f83413e4e5" />




| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Ausência de botão "Cancelar" explícito nos modais, dependendo apenas do "X" de fechar. | AF8–Cancelamento de ações | Incluir botão "Cancelar" ao lado de "Próximo" ou "Salvar". | Leve | Baixo |

<img width="1115" height="585" alt="image" src="https://github.com/user-attachments/assets/01f132f0-3855-40f9-b713-633c828e7313" />


---

## CDU 03 - Alocar profissional em plantão


| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | **Texto invisível por conflito de tema:** Quando o navegador está em modo escuro, as informações dos profissionais (nome e contato) mudam para a cor branca, mas o fundo do card permanece branco, eliminando o contraste. | FM11–Contraste e FM6–Consistência | Forçar cores fixas (hardcoded) para os textos e fundos desses cards ou utilizar uma meta tag para obrigar o modal a renderizar apenas em `light mode`. | Alta | Baixo |

<img width="1115" height="585" alt="image" src="https://github.com/user-attachments/assets/d1d4d8eb-889d-4b4b-904c-73a8e5f2725e" />
<img width="1115" height="585" alt="image" src="https://github.com/user-attachments/assets/06c8b10a-9b79-4ee2-8c51-42ba53a82d09" />


| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Ausência de feedback quando a busca não retorna resultados. | CO2–Feedback adequado | Exibir mensagem "Nenhum profissional encontrado". | Média | Baixo |

<img width="1115" height="585" alt="image" src="https://github.com/user-attachments/assets/9630def6-4730-4ba1-9de3-505153148e7f" />


---

## CDU 04 - Consultar plantões abertos

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Filtro de especialidade em dropdown simples pode ser lento se a lista crescer. | AF11–Digitação otimizada | Utilizar seletor com busca interna. | Baixa | Médio |

<img width="393" height="307" alt="image" src="https://github.com/user-attachments/assets/87b2b37c-7109-488e-b6e5-96a37c33f06d" />



| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Distinção de status baseada apenas em cor (prejudicial para daltônicos). | FM11–Contraste / FM12-Cores | Adicionar rótulos de texto ou ícones distintos para cada status. | Média | Baixo |

<img width="791" height="259" alt="image" src="https://github.com/user-attachments/assets/eb8dada6-fd08-4f30-803a-038ece7549de" />


---

## CDU 06 - Dashboard

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Gráfico de carga horária não mostra o valor total de horas nominal. | FM1–Visibilidade | Exibir o total de horas previstas no centro ou legenda do gráfico. | Média | Médio |

<img width="438" height="239" alt="image" src="https://github.com/user-attachments/assets/07c273ef-d0de-4832-8363-e31cb0f7f6ca" />

