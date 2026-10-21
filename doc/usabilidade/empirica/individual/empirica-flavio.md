# Relatório Individual da Avaliação Empírica

| **Projeto** | **Modalidade** | **Ferramentas Utilizadas**      | **Data**       |
|-------------|----------------|---------------------------------|----------------|
| Pulso       | remoto    | github | **28/06/2026** |

O presente documento, produzido por [Flavio Matias](https://github.com/FlavioMatias), apresenta o relatório de avaliação empírica para a plataforma Pulso. Além de analisar o comportamento dos sujeitos do sistema, o objetivo é extrair potenciais melhorias e defeitos presentes no sistema. 

Por fim, é importante que a cada resposta do sujeito, perguntar se houve dificuldades, se tem sugestões, e sempre detalhar nas respostas descritas por "RS:".

## 1. Perfil do Sujeito
- Nome: Ana Amália
- Perfil Classificado:
  - [x] Apoiador
  - [ ] Indiferente
  - [ ] Oponente
- Familiaridade:
  - [ ] Utilizador
  - [ ] Primeiro contato
  - [x] Indireto
  - [ ] Potencial
 
## 2. Registros da Sessão
Primeiramente tire as conclusões do sujeito, para somente depois apresentar o sistema verdadeiramente. Estando sempre disponível para oferecer suporte e tirar dúvidas das funcionalidades aqui presentes. Além disso, **NUNCA** direcionar o comportamento do sujeito para determinados casos, sendo um objetivo encontrar potenciais falhas.
### Tarefa 1 - Primeiras Impressões + Autenticação
O objetivo dessa tarefa é guiar o usuário pela autenticação do sistema. Realizar seu Cadastro e Login no Pulso.

#### 1. Ao observar a tela principal, para que serve o aplicativo? (Após a resposta, explicar conceitualmente.)
RS: acredito ser um sistema para cardiologistas pelo coração
#### 2. Primeiro iremos fazer o seu cadastro. Para onde vamos?
RS: em não possuo uma conta ou no logar com google, porem achei o não possuo conta muito apagado
#### 3. Agora vamos iniciar o cadastro, qual perfil devemos escolher? (Após a resposta, esperar o usuário se cadastrar.)
RS: Eu sou um profissional. porem achei estranho que ao selecionar não foi direto para a proxima etapa
#### 4. Agora, basta entrar no sistema! 
RS: login realizado
#### 5. O que achou do login do sistema? Alguma dificuldade?
RS: partes pouco intuitiva se tiver cansado ou apressado, como precisar selecionar e clicar em continuar, ao inves de só selecionar

---

### Tarefa 2 - Médico
Neste cenário, iremos investigar a usabilidade do sujeito no que se trata as funcionalidades dos médicos. Portanto, primeiras impressões do Dashboard, ver e pegar plantões, ver escalas.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: Senha123`
```

#### 1. Agora estamos no painel do médico. Consegue me explicar o que vê na tela? (Aqui, o objetivo é entender os potenciais comportamentos ao entrar na tela inicial)
RS: a pagina inicial, plantões coberto, plantões pendente, carga horaria e proximos plantões, notificações, meu perfil, calendario e uma casinha no topo
#### 2. Se eu quiser sincronizar o meu Google Calendário, como faço isso? (Explicar previamente o objetivo da funcionalidade, sem dizer como fazer)
RS: no calendario na sidebar, mas avaliando melhor, na esquerda tem uma setinha para entrar
#### 3. Precisamos pegar novos plantões para trabalhar! Para onde iremos?
RS: em plantões e depois em meus plantões
#### 4. Se quisermos filtrar os plantões, por exemplo: por especialidade, por preço ou data, como fazemos isso?
RS: na barra de busca inicialmente, talvez nos filtros como segunda opção, normalmente na barra de busca ja tem os filtros
#### 5. Agora, como você faria para ver informações mais detalhadas do plantão?
RS: no botão azul escrito detalhar
#### 6. Você consegue me explicar as informações presentes no plantão?
RS: meu nome, a clinica, novo plantão, endereço, data e hora de inicio e fim, uma observação vazia e profissionais 
#### 7. Se você quiser ver a Escala desse Plantão, como você faria? (Explicar previamente o conceito)
RS: Acho que clico em minhas escalas 
#### 8. Agora, pegue seu DOIS primeiros plantões!
RS: não consegui pegar
#### 9. Antes de finalizarmos, como você faria para ver todos os seus plantões?
RS: clicaria em calendario
#### 10. Por fim, você sentiu falta de alguma informação relevante até o momento?
RS: na tela inicial seria bacana conseguir acessar mais facilmente o calendario na tela inicial e poder editar o perfil

--- 
--- 

### Tarefa 3 - Líder de Escala
Agora iremos simular o cenário de um líder de escala. Em seguida, explique a diferença do Líder de Escala para um Médico, seus poderes de gerenciar escalas, plantões e alocação de profissionais.

#### 1. Agora você tem algumas tarefas. Crie TRÊS escalas:
- Uma para criar plantões e publicar, chamada de "Escala 1"
- Uma para ser excluída, usando TEMPLATE, chamada de "Escala 2"
- Outra para deixar em rascunho, chamada de "Escala 3"
RS: escala criadas
#### 2. Navegue para as minhas escalas e filtre por uma especialidade.
RS: retornou as duas que coloquei de clinica geral apareceu
#### 3. Selecione a "Escala 1"
RS: selecionado 
#### 4. Crie 2 plantões na "Escala 1"
RS: não consegui
#### 5. Aloque DOIS profissionais. (Explique o conceito alocar profissionais.)
RS: não consegui, talvez pelo horario
#### 6. Publique a "Escala 1", e me diga o que seria esse publicar.
RS: publiquei, acredito que é para organizar na agenda e lançar pro sistema
#### 7. Agora, quero que você edite a "Escala 3", mude o nome e o setor.
RS: não consegui, não achei como editar
#### 8. Antes de finalizarmos, quero que você remova um dos profissionais do plantão.
RS: não consegui(não alocou)
#### 9. Por fim, exclua a "Escala 2".
RS: consegui excluir, mas apareceu escala criada com sucesso
#### 10. Você sentiu alguma dificuldade em entender o fluxo? Alguma informação te confundiu?
RS: não, mas o detalhar atrapalha, poderia ser interessante realizar operações de excçuir, editar, publicar na propria listagem

## 3. Conclusões do Sujeito
### 1. Como você avalia a facilidade de realizar as tarefas propostas no sistema?
- [ ] Muito difícil
- [ ] Difícil
- [x] Regular
- [ ] Fácil
- [ ] Muito fácil

### 2. De modo geral, quão satisfeito você se sentiu ao usar este sistema?
- [ ] Muito insatisfeito
- [ ] Insatisfeito
- [ ] Indiferente
- [x] Satisfeito
- [ ] Muito satisfeito

### 3. As informações na tela estavam claras e fáceis de entender?
- [ ] Nada claras
- [x] Pouco claras
- [ ] Razoável
- [ ] Claras
- [ ] Muito claras

### 4. O que você mais gostou no sistema?
achei organizado e confortavel de se ver, informações na lateral que facilita bastante
### 5. O que você menos gostou no sistema?
ausencia de algumas informçoes, edição de escala, google calendar não tao claro

### 6. Problemas Encontrados

| **Nº** | **Problema** | **Tela** | **Gravidade** | **Esforço** | **Sugestão** |
|--------|--------------|----------|---------------|-------------|--------------|
| 1 | Botão "não possuo uma conta" está visualmente apagado, com baixo contraste | Autenticação | Médio | Leve | Aumentar destaque visual do link, equiparando com o botão de login com Google |
| 2 | Selecionar um perfil de cadastro não avança automaticamente para a próxima etapa | Cadastro | Leve | Leve | Avançar para a próxima etapa automaticamente ao selecionar o perfil, eliminando o clique extra em "continuar" |
| 3 | Não é possível pegar plantões pelo fluxo esperado | Plantões Abertos | Alto | Alto | Investigar e corrigir o fluxo de captura de plantão; adicionar botão de ação direta na listagem |
| 4 | Acesso ao Google Calendar não está claro na tela inicial | Dashboard | Leve | Leve | Tornar o botão/atalho de sincronização com Google Calendar mais evidente na tela principal |
| 5 | Atalho para edição de perfil ausente na tela inicial | Dashboard | Leve | Leve | Adicionar atalho de edição de perfil acessível diretamente pelo Dashboard |
| 6 | Não é possível criar plantões dentro de uma escala | Criar Plantão | Alto | Alto | Corrigir o fluxo de criação de plantão vinculado à escala |
| 7 | Não é possível alocar profissionais | Alocar Profissional | Alto | Alto | Corrigir a lógica de alocação; investigar se há dependência de horário ou estado da escala bloqueando a ação |
| 8 | Não é possível editar uma escala existente | Detalhar Escala | Alto | Alto | Criar e expor o fluxo de edição de escala (nome, setor e demais campos) |
| 9 | Feedback de exclusão de escala exibe mensagem incorreta ("escala criada com sucesso") | Minhas Escalas | Médio | Leve | Corrigir o toast/mensagem de retorno para refletir a ação realizada ("escala excluída com sucesso") |
| 10 | Operações de editar, excluir e publicar exigem entrar no detalhe; não há ações diretas na listagem | Minhas Escalas | Médio | Médio | Adicionar menu de ações inline na listagem (editar, excluir, publicar) sem necessidade de navegar para o detalhe |
