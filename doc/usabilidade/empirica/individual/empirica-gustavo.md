# Relatório Individual da Avaliação Empírica

| **Projeto** | **Modalidade** | **Ferramentas Utilizadas**      | **Data**       |
|-------------|----------------|---------------------------------|----------------|
| Pulso       | Presencial     | Bloco de Notas, Github, Gravador de Voz | **29/06/2026** |

O presente documento, produzido por [Gustavo Maia](https://github.com/gusttavokr), apresenta o relatório de avaliação empírica para a plataforma Pulso. Além de analisar o comportamento dos sujeitos do sistema, o objetivo é extrair potenciais melhorias e defeitos presentes no sistema. 

Por fim, é importante que a cada resposta do sujeito, perguntar se houve dificuldades, se tem sugestões, e sempre detalhar nas respostas descritas por "RS:".

## 1. Perfil do Sujeito
- Nome: Fernanda Thais C. de Andrade
- Perfil Classificado:
  - [x] Apoiador
  - [ ] Indiferente
  - [ ] Oponente
- Familiaridade:
  - [ ] Utilizador
  - [ ] Primeiro contato
  - [ ] Indireto
  - [x] Potencial
 
## 2. Registros da Sessão
Primeiramente tire as conclusões do sujeito, para somente depois apresentar o sistema verdadeiramente. Estando sempre disponível para oferecer suporte e tirar dúvidas das funcionalidades aqui presentes. Além disso, **NUNCA** direcionar o comportamento do sujeito para determinados casos, sendo um objetivo encontrar potenciais falhas.

### Tarefa 1 - Primeiras Impressões + Autenticação
O objetivo dessa tarefa é guiar o usuário pela autenticação do sistema. Realizar seu Cadastro e Login no Pulso.

#### 1. Ao observar a tela principal, para que serve o aplicativo? (Após a resposta, explicar conceitualmente.)
RS: Suspeito que seja um sistema que ajuda no atendimento diário. Na área da saúde.

#### 2. Primeiro iremos fazer o seu cadastro. Para onde vamos?
RS: Posso entrar com o Google, ou em "Não possuo conta".

#### 3. Agora vamos iniciar o cadastro, qual perfil devemos escolher? (Após a resposta, esperar o usuário se cadastrar.)
RS: Já que é pra mim, vou entrar como profissional.

#### 4. Agora, basta entrar no sistema! 
RS: Entrei.

#### 5. O que achou do login do sistema? Alguma dificuldade?
RS: Sem dificuldades.

---

### Tarefa 2 - Médico
Neste cenário, iremos investigar a usabilidade do sujeito no que se trata as funcionalidades dos médicos. Portanto, primeiras impressões do Dashboard, ver e pegar plantões, ver escalas.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: senha123
```

#### 1. Agora estamos no painel do médico. Consegue me explicar o que vê na tela? (Aqui, o objetivo é entender os potenciais comportamentos ao entrar na tela inicial)
RS: Possui itens que preciso saber. De cara ele me fornece os plantões e seus quantitativos. Na esquerda vejo que possui relação a escala, calendário... Como a questão do calendário ainda vai ser trabalhada, acredito que é importante colocar aqui já pensando no amanhã, precisamos pensar em 2 ou 3 plantões a mais. Já nos preparamos pro próximo mês. Não pensar só no próximo.

#### 2. Se eu quiser sincronizar o meu Google Calendário, como faço isso? (Explicar previamente o objetivo da funcionalidade, sem dizer como fazer)
RS: Clico aqui em "Conectar com o Google".

#### 3. Precisamos pegar novos plantões para trabalhar! Para onde iremos?
RS: Clico em "Plantões", e ele dá a possibilidade de "Plantões Abertos". Ele me mostra os plantões, detalho e pego o plantão.

#### 4. Se quisermos filtrar os plantões, por exemplo: por especialidade, por preço ou data, como fazemos isso?
RS: Eu vou colocar a data que quero. Só acho que a data deveria ter filtro por localização, as vezes posso focar em um só lugar. Além disso, seria bom que a data fosse por período.

#### 5. Agora, como você faria para ver informações mais detalhadas do plantão?
RS: Só se eu detalhar, consigo ver todas as informações.

#### 6. Você consegue me explicar as informações presentes no plantão?
RS: Tem a data, horários, valores, etc... Acho suficiente.

#### 7. Se você quiser ver a Escala desse Plantão, como você faria? (Explicar previamente o conceito)
RS: Tem um botão ao ver o plantão. Eu só acho que eu poderia ver a escala antes de pegar o plantão.

#### 8. Agora, pegue seu DOIS primeiros plantões!
RS: Peguei.

#### 9. Antes de finalizarmos, como você faria para ver todos os seus plantões?
RS: Basta ir em "Meus plantões".

#### 10. Por fim, você sentiu falta de alguma informação relevante até o momento?
RS: Sem dificuldade.

--- 

### Tarefa 3 - Líder de Escala
Agora iremos simular o cenário de um líder de escala. Em seguida, explique a diferença do Líder de Escala para um Médico, seus poderes de gerenciar escalas, plantões e alocação de profissionais.

#### 1. Agora você tem algumas tarefas. Crie TRÊS escalas:
- Uma para criar plantões e publicar, chamada de "Escala 1"
- Uma para ser excluída, usando TEMPLATE, chamada de "Escala 2"
- Outra para deixar em rascunho, chamada de "Escala 3"
RS: Fui em minhas escalas e criei, mas deu erro várias vezes, e não sei o porquê. Se você não tivesse olhado, eu não saberia. Deveria ter explicado quando deu erro.

#### 2. Navegue para as minhas escalas e filtre por uma especialidade.
RS: O filtro não está funcionando.

#### 3. Selecione a "Escala 1"
RS: Foi.

#### 4. Crie 2 plantões na "Escala 1"
RS: Não está criando os plantões.

#### 5. Aloque DOIS profissionais. (Explique o conceito alocar profissionais.)
RS: Aloquei.

#### 6. Publique a "Escala 1", e me diga o que seria esse publicar.
RS: Vou deixar em acesso público. Qualquer pessoa vai ter acesso a escala, mas não significa que pode mexer. Vou saber quem está na escala, no caso os profissionais.

#### 7. Agora, quero que você edite a "Escala 3", mude o nome e o setor.
RS: Não é possível editar. Deveria ter um ícone de um lápis, ou um botão. 

#### 8. Antes de finalizarmos, quero que você remova um dos profissionais do plantão.
RS: Pronto.

#### 9. Por fim, exclua a "Escala 2".
RS: Posso excluir aqui pela lista, né? Pronto.

#### 10. Você sentiu alguma dificuldade em entender o fluxo? Alguma informação te confundiu?
RS: Não senti. Só aquilo que falamos pra saber o que estamos errando. Mas em termo de informação, não, é o que é esperado. É simplificado, você sabe, o tempo é curto. Não senti dificuldade. Só acho que na alocação do profissional, deveria ter uma informação mais clara sobre a disponibilidade do profissional, nem todo mundo sabe ler um gráfico, e esse em específico não fala sobre a quantidade de plantões do profissional. Saber se ele está em outro setor, simplificar, ficar mais claro.

## 3. Conclusões do Sujeito
### 1. Como você avalia a facilidade de realizar as tarefas propostas no sistema?
- [ ] Muito difícil
- [ ] Difícil
- [ ] Regular
- [x] Fácil
- [ ] Muito fácil

### 2. De modo geral, quão satisfeito você se sentiu ao usar este sistema?
- [ ] Muito insatisfeito
- [ ] Insatisfeito
- [ ] Indiferente
- [ ] Satisfeito
- [x] Muito satisfeito

### 3. As informações na tela estavam claras e fáceis de entender?
- [ ] Nada claras
- [x] Pouco claras
- [ ] Razoável
- [ ] Claras
- [ ] Muito claras

### 4. O que você mais gostou no sistema?
Gostei que a plataforma me dá uma margem abrangente do que preciso saber. Detalhes essenciais que não me faz procurar, como data, valores, etc.

### 5. O que você menos gostou no sistema?
A plataforma não dá um retorno claro em alguns momentos. Preciso supor em certos momentos, como ao errar formulários.

### 6. Problemas Encontrados

| **Nº** | **Problema** | **Tela** | **Gravidade** | **Esforço** | **Sugestão** |
|--------|--------------|----------|---------------|-------------|--------------|
| 1      | Próximos plantões | Dashboard | Baixo | Moderado | "Colocar já pensando no amanhã, precisamos pensar em 2 ou 3 plantões a mais. Já nos preparamos pro próximo mês. Não pensar só no próximo." |
| 2      | Filtro localização | Telas de listagem | Médio | Moderado | "Acho que a data deveria ter filtro por localização, as vezes posso focar em um só lugar." |
| 3      | Filtro de Data | Telas de listagem | Médio | Moderado | "Seria bom que a data fosse por período." |
| 4      | Ver escala | Detalhe Plantão | Grave | Baixo | "Eu só acho que eu poderia ver a escala antes de pegar o plantão.", deixar o botão com visual clicável. |
| 5      | Filtragem | Telas de listagem | Grave | Moderado | "Os filtros não estão funcionando", corrigir funcionalidade. |
| 6      | Criação de Plantão | Criar plantão | Grave | Moderado | "Não está criando os plantões", corrigir funcionalidade. |
| 7      | Edição de Escala | Detalhe Escala | Grave | Leve | "Não é possível editar. Deveria ter um ícone de um lápis, ou um botão." |
| 8      | Feedback Adequado | Erro | Grave | Moderado | Retornar mensagem identificando a causa do erro para erros simples. |
| 9      | Feedback sobre o Profissional | Alocação de Profissional | Baixo | Moderado | "Só acho que na alocação do profissional, deveria ter uma informação mais clara sobre a disponibilidade do profissional, nem todo mundo sabe ler um gráfico, e esse em específico não fala sobre a quantidade de plantões do profissional. Saber se ele está em outro setor, simplificar, ficar mais claro." |
