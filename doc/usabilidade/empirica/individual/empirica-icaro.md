# Relatório Individual da Avaliação Empírica

| **Projeto** | **Modalidade** | **Ferramentas Utilizadas**      | **Data**       |
|-------------|----------------|---------------------------------|----------------|
| Pulso       | Remoto         | Github, Docker, Chrome, Discord | **25/06/2026** |

O presente documento, produzido por [Ícaro Gabriel](https://github.com/IcaroGabrielIcaro), apresenta o relatório de avaliação empírica para a plataforma Pulso. Além de analisar o comportamento dos sujeitos do sistema, o objetivo é extrair potenciais melhorias e defeitos presentes no sistema. 

Por fim, é importante que a cada resposta do sujeito, perguntar se houve dificuldades, se tem sugestões, e sempre detalhar nas respostas descritas por "RS:".

## 1. Perfil do Sujeito
- Nome:
- Perfil Classificado:
  - [ ] Apoiador
  - [x] Indiferente
  - [ ] Oponente
- Familiaridade:
  - [ ] Utilizador
  - [x] Primeiro contato
  - [ ] Indireto
  - [ ] Potencial
 
## 2. Registros da Sessão
Primeiramente tire as conclusões do sujeito, para somente depois apresentar o sistema verdadeiramente. Estando sempre disponível para oferecer suporte e tirar dúvidas das funcionalidades aqui presentes. Além disso, **NUNCA** direcionar o comportamento do sujeito para determinados casos, sendo um objetivo encontrar potenciais falhas.

### Tarefa 1 - Primeiras Impressões + Autenticação
O objetivo dessa tarefa é guiar o usuário pela autenticação do sistema. Realizar seu Cadastro e Login no Pulso.

#### 1. Ao observar a tela principal, para que serve o aplicativo? (Após a resposta, explicar conceitualmente.)
RS: Aparência de um sistema de saúde, mas sem dizer exatamente do que seria. Pode ser do plano de saúde ou qualquer outra coisa.
#### 2. Primeiro iremos fazer o seu cadastro. Para onde vamos?
RS: Clico em "não possuo uma conta"
#### 3. Agora vamos iniciar o cadastro, qual perfil devemos escolher? (Após a resposta, esperar o usuário se cadastrar.)
RS: Eu sou um profissional.
#### 4. Agora, basta entrar no sistema! 
RS: Só colocar meus dados e selecionar para entrar.
#### 5. O que achou do login do sistema? Alguma dificuldade?
RS: Os dados não estão sendo salvos quando avança e volta etapas e o problemas de cadastro não estão claros, tive que pedir ajuda.

---

### Tarefa 2 - Médico
Neste cenário, iremos investigar a usabilidade do sujeito no que se trata as funcionalidades dos médicos. Portanto, primeiras impressões do Dashboard, ver e pegar plantões, ver escalas.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: Senha123`
```

#### 1. Agora estamos no painel do médico. Consegue me explicar o que vê na tela? (Aqui, o objetivo é entender os potenciais comportamentos ao entrar na tela inicial)
RS: Eu vejo que tem a minha foto de perfil, tem o nome, a especialidade e a carga horária que é o tanto que eu trabalhei. Tem um lugar que eu posso escolher várias abas, em plantões eu acho que consigo ver os meus plantões marcados, em instituição eu supunho que sejam as intituições que eu faço parte, em calendário deve ter as informações das datas dos meus plantões, trocas e repasses eu não vou saber não, deve ser trocar com outro médico, nos relatórios devem ter relatórios sobre os plantões tipo quanto eu trabalhei, quantas pessoas eu atendi e nó sair eu devo sair. No meio, na tela inicial, eu consigo ver as informações sobre os meus plantões e é o core do sistema, com informações de plantões que eu tenho marcado, que eu tenho cobertos e pendentes e eu consigo ver meu próximo plantão e detalhes sobre a minha carga horária. E na direita eu consigo me conectar com o google calendar para melhorar meu acesso as escalas e marcar melhor com as informações do horário e daonde é.
#### 2. Se eu quiser sincronizar o meu Google Calendário, como faço isso? (Explicar previamente o objetivo da funcionalidade, sem dizer como fazer)
RS: Eu acredito que seja clicando nesse botão de "conectar com google calendário".
#### 3. Precisamos pegar novos plantões para trabalhar! Para onde iremos?
RS: Acho que aqui em plantões e eu não sei... eu acho que em "meus plantões". Não sei.
#### 4. Se quisermos filtrar os plantões, por exemplo: por especialidade, por preço ou data, como fazemos isso?
RS: Só ir em "meus plantões" e "filtros".
#### 5. Agora, como você faria para ver informações mais detalhadas do plantão?
RS: Clico em "detalhar"
#### 6. Você consegue me explicar as informações presentes no plantão?
RS: Consigo. Tem endereço, tem a data do plantão, o horário do plantão, o líder e o local. Não entendi a diferença de clinica geral - clinica geral
#### 7. Se você quiser ver a Escala desse Plantão, como você faria? (Explicar previamente o conceito)
RS: Acho que clico em "ver escala"
#### 8. Agora, pegue seu DOIS primeiros plantões!
RS: Eu não posso pegar pela lista direto? Vou detalhar e clico em "pegar plantão".
#### 9. Antes de finalizarmos, como você faria para ver todos os seus plantões?
RS: Vou em "meus plantões"
#### 10. Por fim, você sentiu falta de alguma informação relevante até o momento?
RS: Não dá pra pegar um plantão diretamente pela lista, não tem como eu trocar meu plantão e o "meus plantões" e "plantões abertos" estão invisiveis aqui do lado, tive que pedir ajuda.

--- 

### Tarefa 3 - Líder de Escala
Agora iremos simular o cenário de um líder de escala. Em seguida, explique a diferença do Líder de Escala para um Médico, seus poderes de gerenciar escalas, plantões e alocação de profissionais.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: Senha123`
```

#### 1. Agora você tem algumas tarefas. Crie TRÊS escalas:
- Uma para criar plantões e publicar, chamada de "Escala 1"
- Uma para ser excluída, usando TEMPLATE, chamada de "Escala 2"
- Outra para deixar em rascunho, chamada de "Escala 3"
RS: Vou em "minhas escalas" e clico em "criar nova escala", coloco um nome que não dá nem pra ver o nome que estou botando, digo a instituição, o setor, o mês e clico pra criar.
#### 2. Navegue para as minhas escalas e filtre por uma especialidade.
RS: Vou em filtros e filtro por "urologia" e ele não filtra, não consigo filtrar.
#### 3. Selecione a "Escala 1"
RS: Vou em "detalhar"
#### 4. Crie 2 plantões na "Escala 1"
RS: Vou em "criar plantão", e eu não consigo mais ver a escala. Coloco a data, a hora, o encerramento e a observação, próximo, a quanitdade de proffisionais, a forma de pagamento, o valor, vai ser em maio e deu errado.
#### 5. Aloque DOIS profissionais. (Explique o conceito alocar profissionais.)
RS: Vou na escala, escolho um plantão, detalho ele, vou em "alocar profissionais" e não tem nenhuma informação aparecendo e tem gráficos e eu "aloco" e "confirmo"
#### 6. Publique a "Escala 1", e me diga o que seria esse publicar.
RS: Vou na escala, detalho e clico em "publicar"
#### 7. Agora, quero que você edite a "Escala 3", mude o nome e o setor.
RS: Vou na escala, detalho e... eu acho que cliquei no canto errado e... eu lembro que tinha um negócio... 
#### 8. Antes de finalizarmos, quero que você remova um dos profissionais do plantão.
RS: Vou em "meus plantões" e aí não tem mais como tirar ele de lá... Vou nas minhas escalas, vou no plantão e eu não consigo tirar? Eu vou botar um cara e tirar ele de novo.
#### 9. Por fim, exclua a "Escala 2".
RS: Só clicar na lixeira
#### 10. Você sentiu alguma dificuldade em entender o fluxo? Alguma informação te confundiu?
RS: Muitas, não é fácil de usar, tive que ficar indo e voltando muitas vezes para chegar nos cantos, nada está bem explicito e eu tenho que ficar perguntando o tempo todo. Eu acho que alguns botões podiam ser mais direitos, eu tenho que clicar em "detalhar" e não somente clicar no plantão e o publicar tem que ir na escala e quando eu clicar para criar um novo plantão eu queria continuar vendo o calendário para poder ver aonde está.

## 3. Conclusões do Sujeito
### 1. Como você avalia a facilidade de realizar as tarefas propostas no sistema?
- [ ] Muito difícil
- [ ] Difícil
- [X] Regular
- [ ] Fácil
- [ ] Muito fácil

### 2. De modo geral, quão satisfeito você se sentiu ao usar este sistema?
- [ ] Muito insatisfeito
- [ ] Insatisfeito
- [X] Indiferente
- [ ] Satisfeito
- [ ] Muito satisfeito

### 3. As informações na tela estavam claras e fáceis de entender?
- [ ] Nada claras
- [ ] Pouco claras
- [ ] Razoável
- [X] Claras
- [ ] Muito claras

### 4. O que você mais gostou no sistema?
Eu acho que eu gostei do visual, é bem moderno e o layout que se mexe e é todo dinâmico. É bem satisfatório mexer nele.

### 5. O que você menos gostou no sistema?
Eu acho que a questão da organização. Para fazer as coisas não está tão evidente para achar e chegar. Fora que a pessoa clica, não aparece nada e depois chega tudo.

### 6. Problemas Encontrados

| **Nº** | **Problema** | **Tela** | **Gravidade** | **Esforço** | **Sugestão** |
|--------|--------------|----------|---------------|-------------|--------------|
| 1      | Não diz o que que deu erro | Todas | (Grave) | (Moderado) | Pegar o erro que vem do back, deve enviar ele tratado já do back, pegar e coloca-lo no modal |
| 2      | Tela de cadastro não mostra o nome do sistema | Autenticação | (Leve) | (Leve) | Adicionar junto aos corações o nome do sistema |
| 3      | Dados não são salvos quando volta e avança entre etapas | Cadastro | (Leve) | (Médio) | Adicionar váriaveis que passam entre as etapas sem perder os dados |
| 4      | Todos os inputs estão ruins com o modo escuro | Todas | (Leve) | (Leve) | Remover propriedade do modo escuro do componente pronto |
| 5      | Quando é a primeira vez entrando na tela inicial, os card não ficam bem formatados | Tela inicial | (Leve) | (Médio) | Usar mesmo booleano para a sidebar e para a organização dos cards |
| 6      | Sidebar não está mostrando "meus plantões" e "plantões abertos" | Tela inicial | (Alta) | (Leve) | Alterar visualização do modo escuro |
| 7      | O nome "plantões abertos" ainda não está intuitivo | Tela inicial | (Leve) | (Leve) | Buscar novas opções de nome para usar |
| 8      | Setor e especialidade não estão bem diferenciados, não dá pra saber quem é quem | Detalhe do plantão | (Média) | (Leve) | Adicionar texto escrito "Setor:" e "Especialidade:" |
| 9      | Não é possível pegar um plantão pela lista de plantões | Plantões abertos | (Leve) | (Leve) | Adicionar botão de "pegar plantão" com mesma funcionalidade que tem no detalhar |
| 10      | Depois de pegar um plantão, eu não consigo me desfazer dele | Meus plantões | (Alta) | (Alto) | Criar toda lógica de troca de plantão |
| 11      | Filtros não estão funcionando | Filtros | (Alta) | (Alto) | Corrigir lógica de filtragem |
| 12      | Ao criar um plantão, não é possível ver aonde esse plantão ficou na escala enquanto eu estou criando, o calendário some | Criar plantão | (Leve) | (Alto) | Deixar criar escala como modal |
| 13      | Informações dos profisisonais não aparecem | Alocar profissional | (Alto) | (Leve) | Ajustar visualização no modo escuro do navegador |
| 14      | Não dá pra editar escala | Detalhar escala | (Alto) | (Alto) | Criar fluxo de editar escala |
| 15      | Não dá pra saber que não é possível remover um profissional porque a escala está publicada | Detalhar plantão | (Leve) | (Leve) | Adicionar informações da escala ao detalhe do plantão |
| 16      | Não existem telas de carregamento | Todo | (Leve) | (Médio) | Adicionar telas de carregamento |
