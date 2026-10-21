# Relatório Individual da Avaliação Empírica

| **Projeto** | **Modalidade** | **Ferramentas Utilizadas**      | **Data**       |
|-------------|----------------|---------------------------------|----------------|
| Pulso       | Presencial     | Bloco de Notas, Github, VS Code | **26/06/2026** |

O presente documento, produzido por [Robson Alves](https://github.com/Alencast), apresenta o relatório de avaliação empírica para a plataforma Pulso. Além de analisar o comportamento dos sujeitos do sistema, o objetivo é extrair potenciais melhorias e defeitos presentes no sistema. 

Por fim, é importante que a cada resposta do sujeito, perguntar se houve dificuldades, se tem sugestões, e sempre detalhar nas respostas descritas por "RS:".

## 1. Perfil do Sujeito
- Nome: Paula Barros
- Perfil Classificado:
  - [x] Apoiador
  - [ ] Indiferente
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
RS: Algo que remete a área da saúde, mas não consegui identificar exatamente do que se trata.
#### 2. Primeiro iremos fazer o seu cadastro. Para onde vamos?
RS: Na opção de criar conta, em "Não possuo uma conta".
#### 3. Agora vamos iniciar o cadastro, qual perfil devemos escolher? (Após a resposta, esperar o usuário se cadastrar.)
RS: Profissional.
#### 4. Agora, basta entrar no sistema! 
RS: Colocar email e senha na tela de login inicial.
#### 5. O que achou do login do sistema? Alguma dificuldade?
RS: Inicialmente não.

---

### Tarefa 2 - Médico
Neste cenário, iremos investigar a usabilidade do sujeito no que se trata as funcionalidades dos médicos. Portanto, primeiras impressões do Dashboard, ver e pegar plantões, ver escalas.

1. Peça pro usuário deslogar e se logar com as credenciais:

```markdown
- e-mail: lider1@pulso.dev
- senha: Senha123`
```

#### 1. Agora estamos no painel do médico. Consegue me explicar o que vê na tela? (Aqui, o objetivo é entender os potenciais comportamentos ao entrar na tela inicial)
RS: Nome de usuário, foto de perfil, especialidade médica no topo da tela. Tem um menu no canto com várias opções de funcionalidades e um dashboard no meio com métricas pertinentes.
#### 2. Se eu quiser sincronizar o meu Google Calendário, como faço isso? (Explicar previamente o objetivo da funcionalidade, sem dizer como fazer)
RS: Tem um botão chamado "Conectar Google Calendário" na lateral, imagino que seja ali.
#### 3. Precisamos pegar novos plantões para trabalhar! Para onde iremos?
RS: Meu palpite seria clicar em "Plantões" no menu do canto.
#### 4. Se quisermos filtrar os plantões, por exemplo: por especialidade, por preço ou data, como fazemos isso?
RS: Em "Meus Plantões" vi algumas opções de filtro.
#### 5. Agora, como você faria para ver informações mais detalhadas do plantão?
RS: Tem uma opção de detalhar aqui.
#### 6. Você consegue me explicar as informações presentes no plantão?
RS: Tem os horários, datas e o local. E a especialidade também.
#### 7. Se você quiser ver a Escala desse Plantão, como você faria? (Explicar previamente o conceito)
RS: Em ver escala? ou é voltando naquele menu lá do começo?
#### 8. Agora, pegue seu DOIS primeiros plantões!
RS: Demorei a achar porque precisa clicar em detalhes antes, pra depois pegar o plantão, podia ser mais direto.
#### 9. Antes de finalizarmos, como você faria para ver todos os seus plantões?
RS: Voltando naquela lista lá de meus plantões.
#### 10. Por fim, você sentiu falta de alguma informação relevante até o momento?
RS: Tive dificuldade em pegar o primeiro plantão, fiquei procurando mas precisava ver os detalhes antes de pegar.

--- 

### Tarefa 3 - Líder de Escala
Agora iremos simular o cenário de um líder de escala. Em seguida, explique a diferença do Líder de Escala para um Médico, seus poderes de gerenciar escalas, plantões e alocação de profissionais.

#### 1. Agora você tem algumas tarefas. Crie TRÊS escalas:
- Uma para criar plantões e publicar, chamada de "Escala 1"
- Uma para ser excluída, usando TEMPLATE, chamada de "Escala 2"
- Outra para deixar em rascunho, chamada de "Escala 3"
RS:
#### 2. Navegue para as minhas escalas e filtre por uma especialidade.
RS: Tem que apertar no botão lá embaixo depois que escolher o filtro?
#### 3. Selecione a "Escala 1"
RS: Em detalhar
#### 4. Crie 2 plantões na "Escala 1"
RS: Marquei tudo mas no final ficou dando erro pra criar, não sei o que fiz de errado.
#### 5. Aloque DOIS profissionais. (Explique o conceito alocar profissionais.)
RS: Nos detalhes do plantão dá pra alocar, achei muito escondido e nada intuitivo.
#### 6. Publique a "Escala 1", e me diga o que seria esse publicar.
RS: Só fui na escala e cliquei em publicar. Publicar seria disponibilizar a escala?
#### 7. Agora, quero que você edite a "Escala 3", mude o nome e o setor.
RS: Muito confuso, vou precisar de ajuda.
#### 8. Antes de finalizarmos, quero que você remova um dos profissionais do plantão.
RS: Não consegui.
#### 9. Por fim, exclua a "Escala 2".
RS: Só clicar no botão de excluir.
#### 10. Você sentiu alguma dificuldade em entender o fluxo? Alguma informação te confundiu?
RS: Sim, muitas! Achei o fluxo confuso e nada intuitivo, muitas vezes precisei parar de procurar por bastante tempo onde fazia tal coisa, precisei de ajuda várias vezes. O fluxo de juntar profissional no plantão e na escala é muito complicado.

## 3. Conclusões do Sujeito
### 1. Como você avalia a facilidade de realizar as tarefas propostas no sistema?
- [ ] Muito difícil
- [x] Difícil
- [ ] Regular
- [ ] Fácil
- [ ] Muito fácil

### 2. De modo geral, quão satisfeito você se sentiu ao usar este sistema?
- [ ] Muito insatisfeito
- [ ] Insatisfeito
- [x] Indiferente
- [ ] Satisfeito
- [ ] Muito satisfeito

### 3. As informações na tela estavam claras e fáceis de entender?
- [ ] Nada claras
- [x] Pouco claras
- [ ] Razoável
- [ ] Claras
- [ ] Muito claras

### 4. O que você mais gostou no sistema?
Apesar de ser confusa em alguns pontos, achei a interface bem bonita

### 5. O que você menos gostou no sistema?
Não é nada intuitiva, foi muito dificil conseguir criar profissional, plantão, escala e juntar depois, também tem muita coisa que não funciona, a gente clica e não leva a lugar algum ou não exibe nenhum aviso.

### 6. Problemas Encontrados

| **Nº** | **Problema**                                                                                                                  | **Tela**                  | **Gravidade** | **Esforço** | **Sugestão**                                                                                             |
| ------ | ----------------------------------------------------------------------------------------------------------------------------- | ------------------------- | ------------- | ----------- | -------------------------------------------------------------------------------------------------------- |
| 1      | Finalizar a ação de pegar um plantão exige abrir os detalhes antes, tornando o fluxo pouco intuitivo.                         | Plantões                  | Médio         | Leve        | Permitir pegar o plantão diretamente pela listagem ou destacar melhor a ação dentro do card.             |
| 2      | Fluxo para criação de plantões apresentou erro sem explicar a causa, impedindo o usuário de concluir a tarefa.                | Criação de Plantões       | Grave         | Moderado    | Exibir mensagens de validação claras indicando quais campos estão incorretos ou ausentes.                |
| 3      | Processo de alocar profissionais é difícil de localizar e compreender.                                                        | Detalhes do Plantão       | Grave         | Moderado    | Tornar a opção de alocação mais visível e simplificar o fluxo de associação de profissionais.            |
| 4      | Edição de escalas (alterar nome e setor) foi considerada muito confusa e exigiu ajuda do avaliador.                           | Edição de Escalas         | Grave         | Moderado    | Reorganizar a tela de edição e destacar as ações principais com melhor hierarquia visual.                |
| 5      | Remover um profissional do plantão não foi encontrado pelo usuário.                                                           | Gerenciamento do Plantão  | Grave         | Moderado    | Disponibilizar um botão de remoção visível e consistente com as demais ações de gerenciamento.           |
| 6      | Diversas ações não apresentam feedback quando falham ou não levam a outra tela, causando incerteza sobre o estado do sistema. | Sistema (geral)           | Grave         | Grande      | Implementar mensagens de sucesso, erro e carregamento para todas as operações relevantes.                |
| 7      | Fluxo de gerenciamento entre escalas, plantões e profissionais foi considerado complexo e pouco intuitivo.                    | Módulo do Líder de Escala | Grave         | Grande      | Simplificar o fluxo, reduzir etapas e tornar a sequência de ações mais linear.                           |
| 8      | A tela inicial não comunica claramente o propósito do sistema para novos usuários.                                            | Tela Inicial              | Baixo         | Leve        | Adicionar uma descrição breve ou elementos visuais que expliquem rapidamente a finalidade da plataforma. |

