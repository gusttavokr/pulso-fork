# Relatório Individual da Avaliação Empírica

| **Projeto** | **Modalidade** | **Ferramentas Utilizadas**      | **Data**       |
|-------------|----------------|---------------------------------|----------------|
| Pulso       | Presencial     | Bloco de Notas, Github, VS Code | **28/06/2026** |

O presente documento, produzido por  [Asaph Arruda](https://github.com/ArrudaAsaph), apresenta o relatório de avaliação empírica para a plataforma Pulso. Além de analisar o comportamento dos sujeitos do sistema, o objetivo é extrair potenciais melhorias e defeitos presentes no sistema. 

Por fim, é importante que a cada resposta do sujeito, perguntar se houve dificuldades, se tem sugestões, e sempre detalhar nas respostas descritas por "RS:".

## 1. Perfil do Sujeito
- Nome: Sabrina Soriano 
- Profissao: Médica
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
RS: Parece ser um sistema para profissionais da saude acompanharem plantoes. Pela tela inicial, entendi que existe login e cadastro, mas nao fica muito claro se e para medico, hospital ou os dois.
#### 2. Primeiro iremos fazer o seu cadastro. Para onde vamos?
RS: Eu iria em "nao possuo conta" ou algo parecido. A tela nao explica muito o que vai acontecer depois, entao eu estou indo pelo texto do botao.
#### 3. Agora vamos iniciar o cadastro, qual perfil devemos escolher? (Após a resposta, esperar o usuário se cadastrar.)
RS: Escolho profissional, porque sou profissional da saude. Achei a escolha simples, mas senti falta de uma explicacao curta sobre a diferenca entre profissional e instituicao.
#### 4. Agora, basta entrar no sistema! 
RS: Só colocar os dados que cadastrei e entrar
#### 5. O que achou do login do sistema? Alguma dificuldade?
RS: O visual e bom, mas quando da erro a mensagem e muito generica. Eu gostaria de saber se o problema foi senha, e-mail. Tambem senti falta de mostrar o carregamento.


---

### Tarefa 2 - Médico
Neste cenário, iremos investigar a usabilidade do sujeito no que se trata as funcionalidades dos médicos. Portanto, primeiras impressões do Dashboard, ver e pegar plantões, ver escalas.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: Senha123`
```

#### 1. Agora estamos no painel do médico. Consegue me explicar o que vê na tela? (Aqui, o objetivo é entender os potenciais comportamentos ao entrar na tela inicial)
RS: Vejo meu perfil, algumas informacoes de carga horaria e cards sobre plantoes. Parece que tem um resumo do mes e um proximo plantao. Tambem tem uma barra lateral com opcoes como plantoes, instituicoes, calendario e relatorios.
#### 2. Se eu quiser sincronizar o meu Google Calendário, como faço isso? (Explicar previamente o objetivo da funcionalidade, sem dizer como fazer)
RS: Eu clicaria no botao de conectar com Google Calendar. Antes de conectar, eu gostaria de saber exatamente quais dados do meu calendario o sistema vai acessar.
#### 3. Precisamos pegar novos plantões para trabalhar! Para onde iremos?
RS: Eu iria em "plantoes". Fiquei em duvida entre "meus plantoes" e "plantoes abertos", porque "meus" parece ser o que ja peguei e "abertos" parece ser o que ainda da para pegar.
#### 4. Se quisermos filtrar os plantões, por exemplo: por especialidade, por preço ou data, como fazemos isso?
RS: Eu usaria a area de filtros. O filtro de especialidade e facil de achar, mas se tiver muitas especialidades seria melhor conseguir pesquisar digitando. Tambem senti falta de ordenar por valor, data ou plantoes mais proximos.
#### 5. Agora, como você faria para ver informações mais detalhadas do plantão?
RS: Eu clicaria em "detalhar". Tambem tentei clicar no card inteiro, porque parecia clicavel, mas nao tive certeza se funcionava.
#### 6. Você consegue me explicar as informações presentes no plantão?
RS: Consigo ver data, horario, local, especialidade e valor. Algumas informacoes parecem repetidas ou sem rotulo suficiente, como setor e especialidade. Eu preferia que estivesse escrito explicitamente o que cada campo significa.
#### 7. Se você quiser ver a Escala desse Plantão, como você faria? (Explicar previamente o conceito)
RS: Eu procuraria um botao de "ver escala". Se o plantao esta dentro de uma escala, acho importante conseguir voltar para ela sem precisar procurar tudo de novo.
#### 8. Agora, pegue seu DOIS primeiros plantões!
RS: Eu esperava conseguir pegar direto pela lista, principalmente quando ja sei que quero aquele plantao. Se tenho que entrar em detalhar toda vez, parece um passo a mais. Em alguns momentos nao ficou claro se o plantao estava realmente disponivel para pegar.
#### 9. Antes de finalizarmos, como você faria para ver todos os seus plantões?
RS: Eu iria em "meus plantoes". Esse nome faz sentido para ver os plantoes que ja assumi.
#### 10. Por fim, você sentiu falta de alguma informação relevante até o momento?
RS: Senti falta de informacoes mais claras sobre vagas disponiveis, status do plantao, motivo de erro e possibilidade de desistir ou trocar um plantao depois de pegar. Tambem seria util ter mais feedback quando uma lista esta carregando ou quando nenhum resultado e encontrado.


--- 

### Tarefa 3 - Líder de Escala
Agora iremos simular o cenário de um líder de escala. Em seguida, explique a diferença do Líder de Escala para um Médico, seus poderes de gerenciar escalas, plantões e alocação de profissionais.

#### 1. Agora você tem algumas tarefas. Crie TRÊS escalas:
- Uma para criar plantões e publicar, chamada de "Escala 1"
- Uma para ser excluída, usando TEMPLATE, chamada de "Escala 2"
- Outra para deixar em rascunho, chamada de "Escala 3"
RS: Vou em minhas escalas e procuro criar nova escala. O fluxo de criacao tem etapas, mas os numeros sozinhos nao explicam o que cada etapa significa. Depois de criar uma escala, se eu for criar outra, eu espero que o formulario comece do inicio.
#### 2. Navegue para as minhas escalas e filtre por uma especialidade.
RS: Uso os filtros, mas queria que as escalas mostrassem no card qual especialidade e qual mes elas representam. Sem isso, eu tenho que entrar no detalhe ou colocar tudo no nome.
#### 3. Selecione a "Escala 1"
RS: Eu clicaria no nome ou no card, mas parece que preciso clicar em "detalhar". Para mim, o card inteiro deveria abrir a escala.
#### 4. Crie 2 plantões na "Escala 1"
RS: Clico em criar plantao, preencho data, horario, quantidade de profissionais, forma de pagamento e valor. Tive duvida sobre campos obrigatorios, porque nem todos parecem marcados. Tambem gostaria de continuar vendo o calendario da escala enquanto crio o plantao, para saber onde ele vai ficar.
#### 5. Aloque DOIS profissionais. (Explique o conceito alocar profissionais.)
RS: Entro no plantao e procuro alocar profissionais. A busca deveria mostrar profissionais reais disponiveis. Se nao encontrar nada, eu preciso de uma mensagem dizendo que nao tem resultado. No modo escuro, algumas informacoes ficam dificeis de ler.
#### 6. Publique a "Escala 1", e me diga o que seria esse publicar.
RS: Eu entendo que publicar libera a escala para os profissionais visualizarem e pegarem plantoes. Antes de publicar, eu esperaria uma confirmacao dizendo que depois disso certas edicoes podem ficar bloqueadas.
#### 7. Agora, quero que você edite a "Escala 3", mude o nome e o setor.
RS: Eu procurei um botao de editar no detalhe da escala, mas nao ficou claro onde fazer isso. Se nao existe edicao, o sistema deveria deixar isso claro ou oferecer a acao diretamente.
#### 8. Antes de finalizarmos, quero que você remova um dos profissionais do plantão.
RS: Procuro a lista de profissionais alocados dentro do plantao. O icone de remover nao comunica bem quando esta ativo ou bloqueado. Se a escala publicada impede remocao, quero uma mensagem explicando isso.
#### 9. Por fim, exclua a "Escala 2".
RS: Uso a lixeira. No modal de confirmacao, os botoes precisam deixar mais claro qual acao cancela e qual confirma a exclusao.
#### 10. Você sentiu alguma dificuldade em entender o fluxo? Alguma informação te confundiu?
RS: Sim. O sistema tem uma interface bonita, mas alguns fluxos exigem muito entrar, voltar e procurar botoes. Eu senti falta de botao de voltar no detalhe, labels nas etapas, mensagens de erro especificas e estados de carregamento. A parte de escala e plantao precisa deixar mais visivel o que esta em rascunho, publicado, disponivel ou bloqueado.

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
- [ ] Indiferente
- [X] Satisfeito
- [ ] Muito satisfeito

### 3. As informações na tela estavam claras e fáceis de entender?
- [ ] Nada claras
- [X] Pouco claras
- [ ] Razoável
- [ ] Claras
- [ ] Muito claras

### 4. O que você mais gostou no sistema?
Gostei do visual geral, dos cards e da ideia de centralizar plantoes, escalas e calendario em uma unica plataforma. O sistema parece moderno e util para organizar a rotina de profissionais da saude.

### 5. O que você menos gostou no sistema?
O que menos gostei foi a falta de feedback claro. Quando algo da errado, quando esta carregando, quando uma acao esta bloqueada ou quando um filtro nao traz resultado, o sistema nao explica bem o que aconteceu.

### 6. Problemas Encontrados

| **Nº** | **Problema** | **Tela** | **Gravidade** | **Esforco** | **Sugestao** |
|--------|--------------|----------|---------------|-------------|--------------|
| 1 | Mensagens de erro genericas nao explicam a causa da falha | Login/Cadastro/Plantao | Grave | Moderado | Exibir mensagem especifica, tratada para o usuario final |
| 2 | Falta feedback de carregamento em operacoes com requisicao | Todo o sistema | Medio | Moderado | Adicionar mensagens, bloquear botao durante envio e mostrar estado de carregamento |
| 3 | Cadastro nao explica claramente a diferenca entre profissional e instituicao | Cadastro | Baixo | Leve | Adicionar descricao curta em cada opcao de perfil |
| 4 | Campos obrigatorios nem sempre sao destacados | Cadastro/Criar plantao | Medio | Leve | Deixar os campos como obrigatorios e mensagens de validacao |
| 5 | Integracao com Google Calendar nao informa claramente quais dados serao acessados | Dashboard/Google Calendar | Grave | Leve | Inserir texto de transparencia sobre finalidade, escopo e privacidade |
| 6 | Dados do dashboard parecem fixos ou pouco verificaveis | Dashboard | Medio | Grande | Integrar cards e graficos aos dados reais |
| 7 | "Meus plantoes" e "plantoes abertos" podem gerar duvida inicial | Sidebar/Plantoes | Baixo | Leve | Ajustar nome ou adicionar uma descricao nos itens |
| 8 | Plantao nao pode ser assumido diretamente pela lista | Plantoes abertos | Medio | Moderado | Adicionar botao "Pegar plantao" no card quando houver vaga |
| 9 | Lista de plantoes nao possui ordenacao por preco, data ou proximidade | Plantoes abertos | Medio | Alto | Criar controle de ordenacao junto aos filtros |
| 10 | Filtros nao deixam claro quando nao ha resultados ou quando foram limpos | Filtros | Medio | Baixo | Exibir mensagem de lista vazia e implementar reset de filtros consistente |
