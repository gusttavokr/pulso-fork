# Avaliação por inspeção com Lista Eureca

Este documento apresenta a avaliação individual das interfaces do sistema Pulso, realizada pelo avaliador Gustavo Maia Militão, com base nas heurísticas Eureca (Matos & Freire).

Foram analisadas as interfaces relacionadas aos casos de uso: Gerenciar escala, Gerenciar plantão, Alocar profissional em plantão, Consultar plantões abertos e Pegar plantão.

---
## CDU 01 - Gerenciar escala

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1  | Ao digitar um dado errado não aparece nada, após alguma ação que aparece "Erro inesperado" | CO2 - Feedback Adequado | Deveria ser "Dado incorreto" | Média | Moderado |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/ed06f2ec-653a-4d94-b74f-7b2b238b731a" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2  | O ícone de rascunho é igual ao de publicado. | CO4 - Metáfora | Colocar um ícone de [lápis](https://lucide.dev/icons/pencil-line) | Baixa | Leve |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/1648844c-3d67-4cfe-bd38-77d02e09f1dc" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3  | Não é possível excluir a escala. | AF1 - Funcionalidade | Analisar o usuário autenticado e dar a permissão com o controller, em usuário que não pode, remover o ícone. | Alto | Grande |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/e30c2f8e-bcec-48ef-aa42-aeec7f395690" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4  | Não é possível detalhar a escala clicando em cima da escala ou no nome, apenas no botão. | AF2 - Flexibilidade de ação | Referenciar o link do plantão na tag do card ou no título e no botão. | Baixo | Moderado |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/62d98437-8a6c-45ce-ad01-3eadba77914a" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5  | Ao excluir uma escala pela listagem o retorno é de criação. | CO2 - Feedback Adequado | Adaptar a mensagem conforme o POST | Alta | Moderado |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/a7f5c278-f565-4796-93a2-af12a7636df8" />

---

## CDU 02 - Gerenciar Plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6  | As opções de especialidades não possuem ordenação e nem busca por escrita. | FM4 - Ordenação da informação <br> AF5 - Autopreenchimento | Utilizar componente de input ordenado e autopreenchido do PrimeNG | Médio | Moderado |
<img width="1600" height="852" alt="image" src="https://github.com/user-attachments/assets/124421a4-7481-4248-b20d-f13354a1bde1" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 7  | É possível criar um plantão com 1 minuto de duração. | AF9 - Prevenção de Erros | Deve gerar um erro exigindo uma diferença mínima. | Alta | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/69c2a808-686c-4162-b25f-f2f866777eae" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 8  | A entrada de "Quantidade de profissionais" não segue o padrão das outras entradas. Além disso, diversos componentes do Pulso no modo escuro estão pretos. | FM6 - Consistência Interna | Testar em outros navegadores/dispositivos | Médio | Leve |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/6e663321-f399-4938-a702-03af904850bb" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 9  | Não é explicado o motivo da não-criação do plantão, apenas que houve um erro. | CO2 - Feedback adequado | Explicar o motivo do erro se houver. | Moderado | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/00227a66-637c-4ea0-b771-b63c3e1e6b36" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 10 | O cursor não indica que o plantão é clicável. | CO2 - Feedback Adequado | Atualizar o plantão com hover. | Baixo | Leve |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/8aa88b5c-8afe-43b1-b101-09aacbe40b09" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 11 | É possível criar um plantão em uma escala já publicada. | AF1 - Funcionalidade | Bloquear botão se escala já publicada | Alta | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/26d38e61-8196-42fb-8c89-da5ca899a072" />

---

## CDU 03 - Alocar profissional em plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 12 | O botão de remover está ativo mesmo não tendo selecionado nenhum profissional. | AF1 - Funcionalidade | Verificar se não há profissionais alocados e mudar estado do botão. | Médio | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/4f559f33-5710-4832-af86-a34f86e9ebbc" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 13 | A busca por profissionais não lista os profissionais existentes. | AF1 - Funcionalidade | Trocar as sugestões pelos profissionais disponíveis | Alto | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/efb220e3-c2ef-49bf-bf3a-c662037f09c4" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 14 | Na página de detalhar o plantão, a migalha de pão mostra "Detalhe plantão" | NA4 - Migalhas de pão | Atualizar migalha de pão de acordo com o identificador do conteúdo: nome ou ID | Alto | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/f44dc8f0-58fb-42c8-b6ab-61851ccbe144" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 15 | Ao confirmar alocação dos profissionais para detalhar o plantão, o usuário é direcionado para a página principal. | AF1 - Funcionalidade | Atualizar rota do botão | Médio | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/dfdb8a4e-174f-4543-8800-4fba7bcecdd5" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 16 | O ícone da lixeira cinza indica que não é possível remover o profissional (está desabilitado), mas é possível pois a escala ainda está em rascunho. | CO3 - Affordance | Atualizar estado do botão de acordo com status da escala. | Alto | Grande |
<img width="472" height="522" alt="image" src="https://github.com/user-attachments/assets/d355b51e-1d10-4db7-8e96-bae8ee90c2a9" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 17 | A rota para o detalhar plantão pela escala está errado. Mostra como se tivesse ido pela listagem de meus plantões. | NA4 - Migalhas de pão | Atualizar migalha de pão de acordo com a ultima rota acessada de forma hierárquica. | Alto | Grande |
<img width="1078" height="713" alt="image" src="https://github.com/user-attachments/assets/7ba93793-f9df-4d32-b94d-07c190a7cb18" />

---

## CDU 04 - Consultar plantões abertos

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 18 | A listagem exibe todos os plantões em abertos | PU4 - Redução do esforço cognitivo | Seria bom um paginator que limita a cada 20 plantões, por exemplo. | Médio | Moderado |
<img width="1827" height="613" alt="image" src="https://github.com/user-attachments/assets/3e9c5dda-9262-438c-96f5-7b32e9197eb8" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 19 | Não é possível pegar o novo plantão aberto. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão. | Alto | Grande |
<img width="1018" height="638" alt="image" src="https://github.com/user-attachments/assets/acfd98c4-61d2-4043-aabb-ad45242841e3" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 20 | O ícone de urgência não varia de acordo com o tempo próximo do plantão aberto. | CO3 - Affordance | Atualizar estado do ícone conforme proximidade do dia do plantão. | Alto | Grande |
<img width="966" height="518" alt="image" src="https://github.com/user-attachments/assets/9fa8a4c7-363b-4ca9-b81e-a33cf8d0823d" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 21 | Não há ordenações como "Do menor pro maior preço" ou "Do mais caro pro mais barato". | FM4 - Ordenação da informação | Implementar essas ordenações | Alto | Grande |
<img width="1406" height="765" alt="image" src="https://github.com/user-attachments/assets/203404d8-f07c-4ab0-8f45-cd59412559b7" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 22 | Não há uma tela de carregamento para os dados a serem exibidos. | FM16 - Tela de carregamento | Adicionar componente de loading do primeNG | Médio | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/32739948-0cf2-43e7-8369-31311e85e552" />

---

## CDU 05 - Pegar plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 23 | Não é possível pegar o plantão pelo detalhar plantão. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão. | Alto | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/7c5381a7-a0b6-4f5a-ba69-539390839d4e" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 24 | Mesmo com o plantão em aberto e com vaga sobrando, não é possível pegar o plantão no modal em detalhar escala. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão de acordo com a disponibilidade. | Alto | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/9f5691ec-750d-4f0a-bcc5-2c9e5d38ed32" />

ㅤㅤㅤㅤㅤ
| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 25 | Um médico qualquer não deveria poder deletar um plantão. | AF1 - Funcionalidade; <br> CO3 - Affordance | Caso o profisisonal não seja líder, remover esse botão ou deixar ele desabilitado | Alto | Moderado |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/35428a56-1671-43a1-b9d2-40b11451b82c" />

---

## Dashboard

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 26 | Os dados métricos, próximo plantão e carga-horária mensal são dados fixos | AF1 - Funcionalidade | Implementar endpoint GET para retornar os dados reais. | Alto | Grande |
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/88e3ce32-a9d0-40a5-b64f-72231590d6dd" />

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 27 | A tela não está responsiva | PD3 - Responsividade | Adaptar interface para outros dispositivos | Alto | Grande |
<img width="1919" height="896" alt="image" src="https://github.com/user-attachments/assets/ffcd8e05-c195-4945-9583-8d22e5334fd5" />

