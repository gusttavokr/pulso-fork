# Avaliação por inspeção com lista Eureca

Este documento apresenta a avaliação individual das interfaces do sistema Pulso, realizada pelo avaliador ícaro Gabriel Pereira Carvalho, com base nas heurísticas Eureca (Matos & Freire).

Foram analisadas as interfaces relacionadas aos casos de uso: gerenciar escala, gerenciar plantão, alocar profissional em plantão, consultar plantões abertos, pegar plantão e dashboard.

## CDU 01 - Gerenciar escala

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual especialidade cada escala faz referência (principalmente por ter um filtro de especialidades) | CO4–Metáfora | Adicionar ícones assim como existem nos plantões | Média | Baixo |

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/cebb4a7e-522f-477d-b1eb-34393a95ae28" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual mês ela faz referência (para o mês ser indicado ele deve ser escrito no nome da escala) | FM1–Visibilidade | Junto ao nome da escala colocar o mês automaticamente e não ter que escrever no nome da escala. | Média | Baixo |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/e7511062-9530-4ec5-95a3-c0e3baf39f18" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Ícone dos status do plantão mantem-se os mesmos em diferentes status | CO4–Metáfora | Módificar ícones para diferentes status de escala | Leve | Baixo |

<img width="600" alt="3" src="https://github.com/user-attachments/assets/eea3136d-79ba-46f6-93b7-bbcd6ed7b623" />

--- 

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | Não é possível fechar modal de criar escala simplesmente clicando fora do modal | AF2- Flexibilidade de ação | Ao clicar em qualquer parte fora do modal ele deve fechar automaticamente | Leve | Baixo |

<img width="600" src="https://github.com/user-attachments/assets/e2aec56a-93b7-442d-b573-e9497de134de" />

--- 

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | Depois de criado uma escala, se for criar outra o modal de criação inicia no passo 2 | AF1– Funcionalidade | Sempre que o formulário estiver vázio, iniciar pelo passo 1 | Alta | Baixo |

<img width="600" alt="5" src="https://github.com/user-attachments/assets/144adc2d-ba0d-4894-89dd-ae3897bf4743" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6 | Ícones e cores para os status das escalas estão diferentes nas telas de listagem e de detalhamento | FM6- Consistência interna | Usar mesmo padrão na listagem de escalas | Leve | Baixo |

<img width="600" alt="6 2" src="https://github.com/user-attachments/assets/778b9ec4-9697-4077-baba-d0cf659f7a38" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 7 | No detalhamento de escalas não há ícone indicando qual especialidade a escala faz referência | CO4–Metáfora | Adicionar ícone de especialidades a escala faz referência | Leve | Baixo |

<img width="600" alt="7" src="https://github.com/user-attachments/assets/83bf5d57-2a86-416d-8183-f3c895149225" />

--- 

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 8 | Não seta de voltar para tela anterior pela tela de detalhamento de escala | AF3- Edição de ações | Adicionar botão de voltar na parte superior esquerda da tela | Alta | Médio |

<img width="600" src="https://github.com/user-attachments/assets/6b41943e-adc5-4584-b92e-5c7de7e04f17" />

---

## CDU 02 - Gerenciar Plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Na listagem dos plantões, não tem como saber qual ícone está relacionado a qual especialidade pela filtragem | CO3–Affordance- dicas | Adicionar os ícones da especialidade junto ao filtro | Médio | Baixo |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/6079a0de-655a-4779-8732-6c25ee364808" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Não existe uma forma de ordenar plantões na listagem dos plantões | FM4- Ordenação da informação | Adicionar campo de ordenação junto dos filtros | Média | Alto |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/afa21b8d-c67e-4b7d-87bd-5f3067b796a8" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Falta de uma tela de carregamento para a listagem de plantões | FM16-Tela de carregamento | Adicionar uma tela de carregamento | Baixo | Médio |

<img width="600" src="https://github.com/user-attachments/assets/57c18b15-359d-4f6d-9567-d23b0d104509" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | No detalhamento do plantão, o ícone de profissionais não está coerente | CO3–Affordance- dicas | Trocar o ícone por um que faça sentido | Baixo | Leve |

<img width="600" alt="4" src="https://github.com/user-attachments/assets/313b9e7b-1aa3-44e1-9845-283f5cf03bfe" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | Na criação/atualização de plantão, não existe botão para voltar passos assim como na tela de criação de escala | FM6- Consistência interna | Adicionar o botão de voltar semelhante ao já existente no criar escala | Baixa | Leve |

<img width="600" alt="5" src="https://github.com/user-attachments/assets/5db99b63-f5a5-4316-a903-256da7ac790d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6 | No detalhamento do plantão, o alinhamento do ícone da especialidade está incorreto | FM9–Alinhamento | Deixar mais centralizado o ícone | Baixo | Leve |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/6feb9271-a766-47d8-9bfa-ef6e5480e995" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 7 | O filtro de data não traz plantões que estão indo de um dia para o outro | AF1– Funcionalidade | Ajustar filto de plantão corretamente no backend | Alto | Alto |

<img width="600" alt="0 1" src="https://github.com/user-attachments/assets/ff1eb170-9dd5-4434-a3b8-2e920abd966a" />

<img width="600" alt="0 2" src="https://github.com/user-attachments/assets/cd0ce13d-166e-424b-b96c-9e36f841145c" />

---

## CDU 03 - Alocar profissional em plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Quando não tem nenhum profissional selecionado na lista de selecionados, o botão de remover não fica desativado como o de adicionar | FM6- Consistência interna | Colocar opção de desativado para o botão de remover | Baixa | Médio |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/ae01f2bc-f068-4cfb-adfe-dd57e00e5d37" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Ao selecionar um profissional, mas não clicar para adicionar, o botão de remover mantem-se ativado | FM6- Consistência interna | Colocar opção de desativado para o botão de remover | Baixa | Médio |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/f08113f5-42a6-4583-bae9-9cd00fc3f544" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Depois de selecionado um profissional não é mais possível deseleciona-lo | AF2- Flexibilidade de ação | Adicionar um x para fechar detalhamento do profissional | Baixa | Leve |

<img width="600" alt="3" src="https://github.com/user-attachments/assets/8b24e5cd-cea3-47da-820e-0e4cc8be5f4d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | Não é possível fechar o modal de alocar profissional a plantão clicando fora da área do modal | AF2- Flexibilidade de ação | Adicionar função que ao clicar na área externa do modal, ele fecha | Baixo | Leve |

<img width="600" src="https://github.com/user-attachments/assets/5f536c70-0de1-4b2b-b080-6dbb784fcaf5" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | Ao fechar modal de alocação de profissional sem confirmar o profissional, os profissionais que tinham sido marcados não são salvos | AF4- Recuperação de dados perdidos | Salvar a lista de profissionais marcados | Médio | Médio |

<img width="600" alt="5 1" src="https://github.com/user-attachments/assets/403766db-039a-41cc-ad97-184bb3234521" />
<img width="600" alt="5 2" src="https://github.com/user-attachments/assets/8175bbc4-a5aa-4a81-a3ef-b9b242c2199f" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6 | Em caso de um profissional ter um e-mail grande, o email sai da área do card | FM9–Alinhamento | Se um e-mail ultrapassar uma quantidade x de caracteres, adiciona "..." no final | Baixa | Leve |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/e53da717-9be2-4f21-8fee-f53e5011b641" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 7 | Em caso de um profissional ter um nome grande, o nome faz o card ficar ocupando uma área maior | FM9–Alinhamento | Se um nome ultrapassar uma quantidade x de caracteres, adiciona "..." no final | Baixa | Leve |

<img width="600" alt="7" src="https://github.com/user-attachments/assets/fbfc029d-d5e1-4cb3-b5dd-674e9cd9a9b3" />

---

## CDU 04 - Consultar plantões abertos

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Na listagem dos plantões, não tem como saber qual ícone está relacionado a qual especialidade pela filtragem | CO3–Affordance- dicas | Adicionar os ícones da especialidade junto ao filtro | Médio | Baixo |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/d079d934-7b8b-49d1-9470-639bf141e225" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Não existe uma forma de ordenar plantões na listagem dos plantões | FM4- Ordenação da informação | Adicionar campo de ordenação junto dos filtros | Média | Alto |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/30aa30a5-d107-4ac4-8732-29eb24c4d54d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Falta de uma tela de carregamento para a listagem de plantões | FM16-Tela de carregamento | Adicionar uma tela de carregamento | Baixo | Médio |

<img width="600" src="https://github.com/user-attachments/assets/40dc602f-6168-451d-a1bf-46d31534350d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | O alinhamento do status do plantão nos cards não está adequada | FM9–Alinhamento | Adicionar a mesma distancia entre todos os elementos mais a direita do card | Baixo | Leve |

<img width="600" alt="4" src="https://github.com/user-attachments/assets/b0148264-aeb0-4a36-b5cf-f867c4525816" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | Não existe um conceito claro do que significam os ícones de alerta dos plantões a nível de interface | PU4- Redução do esforço cognitivo | Adicionar izinho que contem a explicação do que significa o termo | Médio | Médio |

<img width="600" alt="5" src="https://github.com/user-attachments/assets/1956897d-d983-4a53-b572-5196d0db3230" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6 | É possível tentar fazer o filtro de preços negativos | AF9- Prevenção de erros | Adicionar um limitador com uma mensagem de que não é possível filtrar plantões de custo negativo | Baixo | Médio |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/c3efb63e-33c4-4875-a55c-89d3b919a358" />

---

## CDU 05 - Pegar plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Não é possível pegar um plantão disponível para ser pego acessando pelo detalhamento da escala | AF1– Funcionalidade | Adicionar ao guard do plantão o detalhamento por meio da escala | Médio | Médio |

<img width="600" alt="1 1" src="https://github.com/user-attachments/assets/6d2515a8-0656-43e4-82e3-05fba2c4fba1" />

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/e35eecc2-b49e-4197-bf8a-f22344c9514e" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | O botão de pegar plantão não está sendo disponibilizado para os plantões disponíveis para ser pego | AF1– Funcionalidade | Adicionar correta verificação para exibição do botão | Alto | Médio |

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/e35eecc2-b49e-4197-bf8a-f22344c9514e" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | No card do plantão presente no detalhamento da escala o termo quando o plantão está completa não passa essa ideia | CO1- Linguagem apropriada | Corrigir os termos para ficarem adequadas as suas situações | Baixo | Leve |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/b31bdefd-0564-4175-8434-3e7cb0a362b7" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | Não está sendo possível pegar um plantão que está sendo possível para ser pego | AF1– Funcionalidade | Corrigir lógica do backend | Alto | Alto |

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/e35eecc2-b49e-4197-bf8a-f22344c9514e" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | A mensagem de erro não trás um feedback do que realmente aconteceu para ter dado erro | CO2- Feedback adequado | Passar as mensagens de erro tratadas do back para o modal | Médio | Alto |

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/74064c17-aca7-4c2b-9909-373d31af0841" />

---

## CDU 06 - Dashboard

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | Ícone status "livre" no card Carga Horaria Mensal não existe | CO4–Metáfora | Adicionar ícone de relógio no quadradinho | Baixo | Leve |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/4385d3e0-ef57-4204-a1b9-c412ef02cebc" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | Botão de detalhar plantão não redireciona para a tela de plantão marcado | AF1– Funcionalidade | Resgatar plantão verdadeiro do back para haver correto redirecionamento | Médio | Leve |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/66703ae5-6466-400b-9bbf-a895818e8f6f" />

--- 

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | Dados dos cards estão todos sem fazer sentido com os dados reais do sistema | AF1– Funcionalidade | Ajustar backend para entregar dados corretos | Médio | Alto |

<img width="867" height="268" alt="3" src="https://github.com/user-attachments/assets/ce4e453d-2f1c-4d14-a8b0-47413e6c9636" />
