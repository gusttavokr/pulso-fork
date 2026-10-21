# Relatório Final da Avaliação Heurística

## 1. Introdução

O presente relatório descreve o processo de aplicação da técnica Eureca na análise por inspeção da interface gráfica do sistema Pulso. Diferentemente de abordagem original da técnica, que avalia protótipos de baixa ou média fidelidade, nesse estudo a técnica foi aplicada diretamente sobre o site já implementado. O objetivo da aplicação da técnica foi identificar problemas de usabilidade, inconsistências de design e possíveis melhorias na interação do usuário com o sistema, com base nas heurísticas da Lista Eureca (Matos & Freire).

## 2. Equipe Avaliadora

A avaliação foi conduzida pelos membros da equipe de PDS.

- Asaph Arruda
- Flávio Matias
- Gustavo Maia
- Ícaro Gabriel
- Robson Alencastro

Todos os participantes possuem familiaridade com princípios de design e desenvolvimento de interfaces, o que constribuiu para análises mais precisas e técnicas.

## 3. Metodologia

A técnica Eureca é uma variação estruturada da avaliação heurística, que busca sistematizar o processo de identificação e classificação de problemas de usabilidade em interfaces digitais. No caso da interface Pulso, as etapas foram adaptadas para o contexto do sistema já implementado.

**Etapas realizadas:**

### 3.1 Definição do escopo:

Foi definido que a análise abrangeria as principais seções do sistema Pulso, incluindo os casos de uso: 

- Problemas gerais;
- Autenticação;
- Gerenciar escala (CRUD);
- Gerenciar plantão (CRUD);
- Alocar profissional em plantão;
- Consultar plantões abertos;
- Pegar plantão;
- Dashboard.

### 3.2 Seleção dos avaliadores:

Todos os avaliadores do grupo foram previamente apresentados à técnica Eureca e aos critérios de heurísita utlizados.

### 3.3 Avaliação individual

Cada avaliador navegou pelo sistema e registrou individualmente os problemas de usabilidade observados, de acordo com as heurísticas predefinidas.

- [Avaliação individual de Asaph](doc\usabilidade\heuristica\heuristica-individual\heuristica-asaph.md)
- [Avaliação individual de Flávio da Silva](doc\usabilidade\heuristica\heuristica-individual\heuristica-flavio.md)
- [Avaliação individual de Gustavo Maia](doc\usabilidade\heuristica\heuristica-individual\heuristica-gustavo.md)
- [Avaliação individual de ícaro Gabriel](doc\usabilidade\heuristica\heuristica-individual\heuristica-icaro.md)
- [Avaliação individual de Robson Alencastro](heuristica-individual/heuristica-robson/heuristica-robson.md)

### 3.4 Reunião de consolidação

Após as análises individuais, foi realizado uma reunião por vídeo conferência para tornar unânime a classificação de problemas quanto à severidade e frequência.

### 3.5 Documentação dos resultados

Os problemas identificados foram compilados em um relatório conjunto, com sugestões de melhoria para cada um deles.

## 4. Avaliação em Grupo

### Problemas Gerais (Sistema)

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 1 | **Inconsistência de Tema nos Inputs:** Devido à herança do tema do navegador, alguns campos de texto aparecem escuros em modais claros, dificultando a leitura e quebrando a estética. | FM6–Consistência interna e FM11–Contraste | Forçar via CSS que os inputs ignorem o tema do SO ou implementar suporte oficial a Dark Mode. | Média | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/1acc716e-58d0-4f50-b6f4-466ecb7a2082" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 2 | **Responvidade:** Todo o sistema não está preparado para lidar com diferentes tamanhos de tela. | PD3 – Responsividade | Implementar design responsivo consistente em todo o sistema, garantindo adaptação adequada a diferentes resoluções e dispositivos, com foco na legibilidade e usabilidade da escala. | Alto | Grande |

<img width="600" alt="image-1" src="https://github.com/user-attachments/assets/f1f3d2f5-8b11-45f3-a895-38d3f634f2eb" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 3 | **Sidebar:** Alguns itens do menu lateral parecem disponíveis (Instituições, Calendário, Trocas e repasses, Relatórios), mas não levam a fluxos claros. Isso reduz previsibilidade da navegação. | AF1 - Funcionalidade | Exibir apenas itens com rota funcional por perfil. Se uma área não estiver pronta, ocultar ou sinalizar como "Em desenvolvimento" sem redirecionamento silencioso. | Alta | Moderado |

<img width="600" alt="01-na2-menu" src="https://github.com/user-attachments/assets/632b121e-f19e-403b-866a-c1d304e84a4d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 4 | **Tela de carregamento:** Não há feedback visual de carregamento (spinner/loader visível). Cria a escala rápido em rede local, porém em ambiente de produção é uma requisição sujeita a influências externas, como a latência do usuário, logo pode ocorrer de o usuário clicar em criar escala e passar segundos sem obter um feedback. Vale salientar que esse problema se aplica a qualquer ponto que utilize requisições HTTPs na aplicação, inclusive, esse delay é bem perceptível no login. | CO2 - Feedback adequado | Adicionar spinner/loader animado ao lado do texto ou desabilitar o botão com mudança visual (opacity, ícone de carregamento). | Baixa | Leve |

<img width="600" alt="Image" src="https://github.com/user-attachments/assets/55a9544d-e878-4765-8679-fdb55c531fe5" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 5 | **Breadcrumb: ** O breadcrumb de navegação não possui `aria-current="page"` ou indicação visual clara da página ativa, dificultando a orientação de usuários que usam leitores de tela. | AC2 - Navegação por teclado | Adicionar `aria-current="page"` ao item ativo do breadcrumb e destacar visualmente com cor ou negrito. Garantir que cada nível tenha label semântico. | Baixa | Leve |

<img width="600" alt="Image" src="https://github.com/user-attachments/assets/b087f8e5-55f0-4404-b2ab-012fde9f506f" />
---

### CDU 01 - Autenticação/Cadastro

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 6  | Ao digitar um dado errado não aparece nada, após alguma ação que aparece "Erro inesperado" | CO2 - Feedback Adequado | Deveria ser "Dado incorreto" | Média | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/ed06f2ec-653a-4d94-b74f-7b2b238b731a" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 7 | Durante a tentativa de login, quando há erro de conexão ou timeout na comunicação com a API (ex.: "net::ERR_EMPTY_RESPONSE"), não há feedback visual claro indicando que a requisição falhou ou está sendo processada, deixando o usuário sem saber se deve tentar novamente. | CO2 - Feedback adequado | Adicionar estado de carregamento visível durante o login (spinner/loader) e exibir mensagem de erro clara quando a requisição falha (ex.: "Erro de conexão. Por favor, tente novamente"). | Média | Moderado |

<img width="600" alt="10-co2-timeout-feedback" src="https://github.com/user-attachments/assets/efd52b34-49dd-452e-856c-0784189deb36" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 8 | O ícone de toggle para mostrar/ocultar senha utiliza um elemento `<span>` com `(click)` ao invés de um `<button>` semântico, prejudicando a navegação por teclado (Tab, Enter) e deixando foco visual pouco evidente para leitores de tela. | AC2 - Navegação por teclado | Converter o `<span>` para `<button>` com `aria-label="Mostrar/ocultar senha"` e garantir foco visível com `:focus-visible` em CSS. Ativar com Enter e Espaço. | Alta | Leve |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/3e94d0c8-8357-4c92-9825-ba542c3bed98" />

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/128c99b4-97a4-4084-aac1-6ef4808612f7" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 9 | O campo de e-mail no login não exibe placeholder ou label associado indicando o formato esperado (ex.: "usuario@email.com"). Usuário pode tentar inserir dados em formato incorreto. | CO3 - Affordance; <br> AF9 - Prevenção de erros | Adicionar `<label>` associada com `for="email"` ou placeholder descritivo. Validar e exibir mensagem de erro se formato for inválido (ex.: "E-mail inválido"). | Baixa | Leve |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/d785cae7-d4ab-4c43-b85a-fa370f2c7f81" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 10 | Qualquer profissional autenticado consegue deletar plantões criados por outros, independentemente de permissão ou role no sistema. Não há validação de propriedade ou papel (liderança) no backend antes de permitir DELETE. | AF1 - Funcionalidade; CO3 - Affordance | Implementar verificação de autorização: apenas o criador do plantão ou administrador pode deletar. No frontend, remover/desabilitar botão de lixeira para profissionais sem permissão. Retornar erro 403 se tentarem deletar sem autorização. | Alta | Grande |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/97b71cc7-0670-49f5-8940-4c1bf3d468c9" />
<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/b6693442-c115-42dc-b937-e004a42fadf0" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 11 | Na etapa 1 do cadastro (Registrar Profissional/Instituição), o usuário pode tentar confirmar sem selecionar nenhuma opção (Profissional ou Instituição), sem receber feedback visual claramente indicando que uma escolha é obrigatória antes de continuar. | AF9 - Prevenção de erros; <br> CO2 - Feedback Adequado | Desabilitar visualmente o botão "Confirmar" quando nenhuma opção está selecionada, mantendo ele ativo apenas após uma seleção. Alternativamente, destacar a seleção como obrigatória com um asterisco ou instrução clara. | Baixa | Leve |

<img width="600" alt="07-af9-selecao-obrigatoria" src="https://github.com/user-attachments/assets/ff04f208-3dde-4732-950f-fd5ce2e8c012" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 12 | Na etapa 2 do cadastro (Registrar Profissional/Instituição), a validação da Data de nascimento não exibe mensagem de erro quando o campo é deixado vazio, enquanto todos os outros campos obrigatórios (Nome, E-mail, Telefone, Senha) exibem feedback de erro consistente. | CO2 - Feedback adequado | Uniformizar a validação de todos os campos obrigatórios, exibindo mensagem de erro padronizada também para Data de nascimento quando não preenchida. | Baixa | Leve |

<img width="600" alt="08-co2-validacao-inconsistente" src="https://github.com/user-attachments/assets/21b7bde6-401c-4c9f-826c-3f62cd9ccb34" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 13 | Na etapa 2 do cadastro, o campo "Confirmar senha" é obrigatório, porém não exibe mensagem de erro quando deixado vazio, diferente dos demais campos que mostram feedback claro. Isso cria inconsistência de validação visual. | CO2 - Feedback adequado | Adicionar validação visual de erro para o campo "Confirmar senha" seguindo o mesmo padrão dos outros campos obrigatórios. | Baixa | Leve |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/76dd1a9c-e0e8-41b2-9349-1b7f2ccaf11d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 14 | Na etapa 1 do cadastro, as opções de seleção (Profissional/Instituição) utilizam `<div>` com `(click)` ao invés de `<button>` ou `<input type="radio">` semânticos, prejudicando acessibilidade por teclado e leitores de tela. | AC2 - Navegação por teclado | Trocar os `<div>` por `<input type="radio">` com `<label>` associadas, ou converter em `<button>` com `aria-pressed` e semântica de toggle. Garantir navegação por Tab. | Alta | Moderado |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/03828acc-6d87-4525-8d06-6c8581a9680a" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 15 | Na etapa 3 do cadastro (Upload de foto), o campo de upload utiliza um `<div>` com `(click)="fu.choose()"` ao invés de um `<button>`, dificultando acesso por teclado e não aparecendo como elemento interativo para tecnologias assistivas. | AC2 - Navegação por teclado | Envolver o `<p-fileUpload>` em um `<button>` semântico ou usar `<label>` associada com `aria-label="Enviar foto de perfil"`. Garantir ativação por Enter/Espaço. | Alta | Moderado |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/3d222320-e13c-4030-ac53-3e9fa44108ba" />

---

### CDU 02 - Gerenciar escala

| Nº | Problema Identificado                           | Heurística Violada       | Sugestão de Correção                   | Gravidade | Esforço |
| -- | ----------------------------------------------- | ------------------------ | -------------------------------------- | ---------- | ------- |
| 16 | Permite publicar escala já publicada sem alerta | AF1 - Funcionalidade; <br> AF9 – Prevenção de erros; | Bloquear ação ou solicitar confirmação | Alta       | Baixo   |

<img width="600" alt="image-2" src="https://github.com/user-attachments/assets/c604c89e-6d05-44fe-995d-ab69ad5324a3" />
<img width="600" alt="image-3" src="https://github.com/user-attachments/assets/be908977-6e60-4573-a134-3102b2810110" />

---

| Nº | Problema Identificado         | Heurística Violada   | Sugestão de Correção                  | Gravidade | Esforço |
| -- | ----------------------------- | -------------------- | ------------------------------------- | ---------- | ------- |
| 17 | Não é possível limpar filtros | AF1 – Funcionalidade | Implementar botão de reset de filtros | Alta       | Baixo   |

<img width="600" alt="image-23" src="https://github.com/user-attachments/assets/14c274d3-2f90-47a6-a420-7df98fe3e179" />

---

| Nº | Problema Identificado                | Heurística Violada   | Sugestão de Correção            | Gravidade | Esforço |
| -- | ------------------------------------ | -------------------- | ------------------------------- | ---------- | ------- |
| 18 | Botão de excluir escala não funciona | AF1 – Funcionalidade | Analisar o usuário autenticado e dar a permissão com o controller, em usuário que não pode, remover o ícone. | Alta    | Grande   |

<img width="600" alt="image-24" src="https://github.com/user-attachments/assets/65bc7fb6-ea3b-4010-8480-ec0df32fbfdb" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 19 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual especialidade cada escala faz referência (principalmente por ter um filtro de especialidades) | CO3 – Affordance; <br> CO4 – Metáfora | Adicionar ícones assim como existem nos plantões | Média | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/150eae7d-f695-4eae-a681-d3aed3d2e716" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 20 | Os passos do modal de criação (1, 2, 3) não possuem rótulos descritivos. | FM1–Visibilidade | Adicionar nomes às etapas (ex: 1. Identificação, 2. Horários). | Média | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/973ca133-63e1-468e-bae4-86f83413e4e5" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 21 | Ausência de botão "Cancelar" explícito nos modais, dependendo apenas do "X" de fechar. | AF8–Cancelamento de ações | Incluir botão "Cancelar" ao lado de "Próximo" ou "Salvar". | Leve | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/01f132f0-3855-40f9-b713-633c828e7313" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 22 | O ícone de rascunho é igual ao de publicado. | CO4 - Metáfora | Colocar um ícone de [lápis](https://lucide.dev/icons/pencil-line) | Baixa | Leve |

<img width="600" alt="3" src="https://github.com/user-attachments/assets/eea3136d-79ba-46f6-93b7-bbcd6ed7b623" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 23 | Ao excluir uma escala pela listagem o retorno é de criação. | CO2 - Feedback Adequado | Adaptar a mensagem conforme o POST | Alta | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/a7f5c278-f565-4796-93a2-af12a7636df8" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 24 | Não é possível detalhar a escala clicando em cima da escala ou no nome, apenas no botão. | AF2 - Flexibilidade de ação | Referenciar o link do plantão na tag do card ou no título e no botão. | Baixo | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/62d98437-8a6c-45ce-ad01-3eadba77914a" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 25 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual mês ela faz referência (para o mês ser indicado ele deve ser escrito no nome da escala) | FM1 – Visibilidade; <br> CO3 – Affordance; <br> CO4 – Metáfora | Junto ao nome da escala colocar o mês automaticamente e não ter que escrever no nome da escala. | Média | Baixo |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/e7511062-9530-4ec5-95a3-c0e3baf39f18" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 26 | Não é possível fechar modal de criar escala simplesmente clicando fora do modal | AF2 - Flexibilidade de ação | Ao clicar em qualquer parte fora do modal ele deve fechar automaticamente | Leve | Baixo |

<img width="600" src="https://github.com/user-attachments/assets/e2aec56a-93b7-442d-b573-e9497de134de" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 27 | Depois de criado uma escala, se for criar outra o modal de criação inicia no passo 2 | AF1– Funcionalidade | Sempre que o formulário estiver vázio, iniciar pelo passo 1 | Alta | Baixo |

<img width="600" alt="5" src="https://github.com/user-attachments/assets/144adc2d-ba0d-4894-89dd-ae3897bf4743" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 28 | Ícones e cores para os status das escalas estão diferentes nas telas de listagem e de detalhamento | FM6- Consistência interna | Usar mesmo padrão na listagem de escalas | Leve | Baixo |

<img width="600" alt="6 2" src="https://github.com/user-attachments/assets/778b9ec4-9697-4077-baba-d0cf659f7a38" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 29 | No detalhamento de escalas não há ícone indicando qual especialidade a escala faz referência | CO3 – Affordance; <br> CO4 – Metáfora | Adicionar ícone de especialidades a escala faz referência | Leve | Baixo |

<img width="600" alt="7" src="https://github.com/user-attachments/assets/83bf5d57-2a86-416d-8183-f3c895149225" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 30 | Não há seta de voltar para tela anterior pela tela de detalhamento de escala | AF2 - Flexibilidade de ação | Adicionar botão de voltar na parte superior esquerda da tela | Alta | Médio |

<img width="600" src="https://github.com/user-attachments/assets/6b41943e-adc5-4584-b92e-5c7de7e04f17" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 31 | No modal de exclusão de escala, os rótulos dos botões geram dúvida sobre qual ação confirma a exclusão e qual ação cancela. | AF8 - Cancelamento de ações | Diferenciar visualmente e textualmente as ações de cancelar e excluir, mantendo linguagem direta e inequívoca. | Baixa | Leve |

<img width="600" alt="05-af8-exclusao" src="https://github.com/user-attachments/assets/f8df2eb4-c897-464a-80b5-5255b74cfb27" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 32 | Ao listar escalas criadas, não há paginação ou scroll infinito claramente indicado. Usuários podem não perceber que existem mais escalas abaixo, levando a incompletude da visualização. | PU4 - Redução do esforço cognitivo | Implementar paginação explícita com números de página ou botão "Carregar mais" com feedback claro. Mostrar total de escalas e posição atual (ex.: "Mostrando 1-10 de 45"). | Baixa | Moderado |

<img width="600" alt="Image" src="https://github.com/user-attachments/assets/22eee42d-787a-4688-82fd-4becf5c9626c" />

---

### CDU 03 - Gerenciar plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 33 | Permite datas inválidas (passado e fim antes do início) | AF10 – Restrições  | Validar datas | Alto | Baixo |

<img width="600" alt="image-8" src="https://github.com/user-attachments/assets/942091c9-d18e-444e-8f18-e6441b7c0981" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 34 | Não é explicado o motivo da não-criação do plantão, apenas que houve um erro. | CO2 - Feedback adequado | Explicar o motivo do erro se houver. | Moderado | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/00227a66-637c-4ea0-b771-b63c3e1e6b36" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 35 | Calendário permite datas no passado | AF10 – Restrições | Bloquear datas inválidas | Alto | Baixo   |

<img width="600" alt="image-20" src="https://github.com/user-attachments/assets/fa5c6b3e-0227-47ee-84b8-babd73cf3ec4" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 36 | Campos obrigatórios não são indicados | CO3 – Affordance | Destacar campos obrigatórios | Alto | Baixo |

<img width="600" alt="image-21" src="https://github.com/user-attachments/assets/cb2ae66e-ad67-4377-86d3-723ca9ba0f9f" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 37 | As opções de especialidades não possuem ordenação e nem busca por escrita. | FM4 - Ordenação da informação <br> AF5 - Autopreenchimento | Utilizar componente de input ordenado e autopreenchido do PrimeNG | Médio | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/124421a4-7481-4248-b20d-f13354a1bde1" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 38 | É possível criar um plantão com 1 minuto de duração. | AF9 - Prevenção de Erros <br> CO2 - Feedback Adequado | Deve gerar um erro exigindo uma diferença mínima. | Alta | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/69c2a808-686c-4162-b25f-f2f866777eae" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 39 | A entrada de "Quantidade de profissionais" não segue o padrão das outras entradas. Além disso, diversos componentes do Pulso no modo escuro estão pretos. | FM6 - Consistência Interna | Testar em outros navegadores/dispositivos | Médio | Leve |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/6e663321-f399-4938-a702-03af904850bb" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 40 | O cursor não indica que o plantão é clicável. | CO2 - Feedback Adequado | Atualizar o plantão com hover. | Baixo | Leve |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/8aa88b5c-8afe-43b1-b101-09aacbe40b09" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 41 | É possível criar um plantão em uma escala já publicada. | AF1 - Funcionalidade | Bloquear botão se escala já publicada | Alta | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/26d38e61-8196-42fb-8c89-da5ca899a072" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 42 | Na listagem dos plantões, não tem como saber qual ícone está relacionado a qual especialidade pela filtragem | CO4 – Metáfora | Adicionar os ícones da especialidade junto ao filtro | Médio | Baixo |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/6079a0de-655a-4779-8732-6c25ee364808" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 43 | Não existe uma forma de ordenar plantões na listagem dos plantões | FM4- Ordenação da informação | Adicionar campo de ordenação junto dos filtros | Média | Alto |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/afa21b8d-c67e-4b7d-87bd-5f3067b796a8" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 44 | No detalhamento do plantão, o ícone de profissionais não está coerente | CO4 – Metáfora | Trocar o ícone por um que faça sentido | Baixo | Leve |

<img width="600" alt="4" src="https://github.com/user-attachments/assets/313b9e7b-1aa3-44e1-9845-283f5cf03bfe" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 45 | Na criação/atualização de plantão, não existe botão para voltar passos assim como na tela de criação de escala | FM6- Consistência interna | Adicionar o botão de voltar semelhante ao já existente no criar escala | Baixa | Leve |

<img width="600" alt="5" src="https://github.com/user-attachments/assets/5db99b63-f5a5-4316-a903-256da7ac790d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 46 | No detalhamento do plantão, o alinhamento do ícone da especialidade está incorreto | FM9–Alinhamento | Deixar mais centralizado o ícone | Baixo | Leve |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/6feb9271-a766-47d8-9bfa-ef6e5480e995" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 47 | O filtro de data não traz plantões que estão indo de um dia para o outro | AF1– Funcionalidade | Ajustar filto de plantão corretamente no backend | Alto | Alto |

<img width="600" alt="0 1" src="https://github.com/user-attachments/assets/ff1eb170-9dd5-4434-a3b8-2e920abd966a" />

<img width="600" alt="0 2" src="https://github.com/user-attachments/assets/cd0ce13d-166e-424b-b96c-9e36f841145c" />

---

### CDU 04 - Alocar profissional em plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 48 | **Texto invisível por conflito de tema:** Quando o navegador está em modo escuro, as informações dos profissionais (nome e contato) mudam para a cor branca, mas o fundo do card permanece branco, eliminando o contraste. | FM11–Contraste e FM6–Consistência Interna | Forçar cores fixas (hardcoded) para os textos e fundos desses cards ou utilizar uma meta tag para obrigar o modal a renderizar apenas em `light mode`. | Alta | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/d1d4d8eb-889d-4b4b-904c-73a8e5f2725e" />
<img width="600" alt="image" src="https://github.com/user-attachments/assets/06c8b10a-9b79-4ee2-8c51-42ba53a82d09" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 49 | Botão remover sem padrão visual | FM6 – Consistência interna | Usar cor vermelha    | Baixa      | Baixo   |

<img width="600" alt="image-16" src="https://github.com/user-attachments/assets/c0ba12df-2a57-4f16-90fb-973a07599676" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 50 | Ausência de feedback quando a busca não retorna resultados. | CO2–Feedback adequado | Exibir mensagem "Nenhum profissional encontrado". | Média | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/9630def6-4730-4ba1-9de3-505153148e7f" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 51 | Ao fechar modal de alocação de profissional sem confirmar o profissional, os profissionais que tinham sido marcados não são salvos | AF4- Recuperação de dados perdidos | Salvar a lista de profissionais marcados | Médio | Médio |

<img width="600" alt="5 1" src="https://github.com/user-attachments/assets/403766db-039a-41cc-ad97-184bb3234521" />
<img width="600" alt="5 2" src="https://github.com/user-attachments/assets/8175bbc4-a5aa-4a81-a3ef-b9b242c2199f" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 52 | O botão de remover está ativo mesmo não tendo selecionado nenhum profissional. | AF1 - Funcionalidade | Verificar se não há profissionais alocados e mudar estado do botão. | Médio | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/4f559f33-5710-4832-af86-a34f86e9ebbc" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 53 | A busca por profissionais não lista os profissionais existentes. | AF1 - Funcionalidade | Trocar as sugestões pelos profissionais disponíveis | Alto | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/efb220e3-c2ef-49bf-bf3a-c662037f09c4" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 54 | Ao confirmar alocação dos profissionais para detalhar o plantão, o usuário é direcionado para a página principal. | AF1 - Funcionalidade | Atualizar rota do botão | Médio | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/dfdb8a4e-174f-4543-8800-4fba7bcecdd5" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 55 | O ícone da lixeira cinza indica que não é possível remover o profissional (está desabilitado), mas é possível pois a escala ainda está em rascunho. | CO3 - Affordance | Atualizar estado do botão de acordo com status da escala. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/d355b51e-1d10-4db7-8e96-bae8ee90c2a9" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 56 | Depois de selecionado um profissional não é mais possível deseleciona-lo | AF2- Flexibilidade de ação | Adicionar um x para fechar detalhamento do profissional | Baixa | Leve |

<img width="600" alt="3" src="https://github.com/user-attachments/assets/8b24e5cd-cea3-47da-820e-0e4cc8be5f4d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 57 | Não é possível fechar o modal de alocar profissional a plantão clicando fora da área do modal | AF2- Flexibilidade de ação | Adicionar função que ao clicar na área externa do modal, ele fecha | Baixo | Leve |

<img width="600" src="https://github.com/user-attachments/assets/5f536c70-0de1-4b2b-b080-6dbb784fcaf5" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 58 | Em caso de um profissional ter um e-mail grande, o email sai da área do card | FM9–Alinhamento | Se um e-mail ultrapassar uma quantidade x de caracteres, adiciona "..." no final | Baixa | Leve |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/e53da717-9be2-4f21-8fee-f53e5011b641" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 59 | Em caso de um profissional ter um nome grande, o nome faz o card ficar ocupando uma área maior | FM9–Alinhamento | Se um nome ultrapassar uma quantidade x de caracteres, adiciona "..." no final | Baixa | Leve |

<img width="600" alt="7" src="https://github.com/user-attachments/assets/fbfc029d-d5e1-4cb3-b5dd-674e9cd9a9b3" />

---

### CDU 05 - Consultar Plantões Abertos

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 60 | A listagem exibe todos os plantões em abertos | PU4 - Redução do esforço cognitivo | Seria bom um paginator que limita a cada 20 plantões, por exemplo. | Médio | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/3e9c5dda-9262-438c-96f5-7b32e9197eb8" />

---

| Nº | Problema Identificado             | Heurística Violada   | Sugestão de Correção    | Severidade | Esforço |
| -- | --------------------------------- | -------------------- | ----------------------- | ---------- | ------- |
| 61 | Botão limpar filtros não funciona | AF1 – Funcionalidade | Corrigir funcionalidade | Alta       | Baixo   |

<img width="600" alt="image-7" src="https://github.com/user-attachments/assets/2277477f-9ef9-45fb-b2c5-d2582828fcf8" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 62 | Filtro de especialidade em dropdown simples pode ser lento se a lista crescer. | AF11–Digitação otimizada | Utilizar seletor com busca interna. | Baixa | Médio |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/87b2b37c-7109-488e-b6e5-96a37c33f06d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 63 | Distinção de status baseada apenas em cor (prejudicial para daltônicos). | FM11 – Contraste / FM12 - Paletas de Cor | Adicionar rótulos de texto ou ícones distintos para cada status. | Média | Baixo |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/eb8dada6-fd08-4f30-803a-038ece7549de" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 64 | Não é possível pegar o novo plantão aberto. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/acfd98c4-61d2-4043-aabb-ad45242841e3" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 65 | O ícone de urgência não varia de acordo com o tempo próximo do plantão aberto. | CO3 - Affordance | Atualizar estado do ícone conforme proximidade do dia do plantão. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/9fa8a4c7-363b-4ca9-b81e-a33cf8d0823d" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 66 | Não há ordenações como "Do menor pro maior preço" ou "Do mais caro pro mais barato". | FM4 - Ordenação da informação | Implementar essas ordenações | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/203404d8-f07c-4ab0-8f45-cd59412559b7" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 67 | O alinhamento do status do plantão nos cards não está adequada | FM9–Alinhamento | Adicionar a mesma distancia entre todos os elementos mais a direita do card | Baixo | Leve |

<img width="600" alt="4" src="https://github.com/user-attachments/assets/b0148264-aeb0-4a36-b5cf-f867c4525816" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 68 | É possível tentar fazer o filtro de preços negativos | AF9- Prevenção de erros | Adicionar um limitador com uma mensagem de que não é possível filtrar plantões de custo negativo | Baixo | Médio |

<img width="600" alt="6" src="https://github.com/user-attachments/assets/c3efb63e-33c4-4875-a55c-89d3b919a358" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 69 | Os cards de plantões abertos não exibem o número de vagas disponíveis claramente de forma destacada. Usuário precisa ler todo o texto para descobrir se ainda há posições. | CO3 - Affordance | Destacar visualmente o número de vagas (ex.: "3 vagas disponíveis" em negrito, com cor diferente). Incluir indicador visual (ex.: barra de progresso) se houver limite de candidatos. | Baixa | Leve |

<img width="600"  alt="Image" src="https://github.com/user-attachments/assets/b1a6c60e-2022-4f00-81f2-5e45bb5f06b2" />

--- 

### CDU 06 - Pegar plantão

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 70 | Não é possível pegar o plantão pelo detalhar plantão. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/7c5381a7-a0b6-4f5a-ba69-539390839d4e" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 71 | Mesmo com o plantão em aberto e com vaga sobrando, não é possível pegar o plantão no modal em detalhar escala. | AF1 - Funcionalidade | Inserir um botão para pegar o plantão de acordo com a disponibilidade. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/9f5691ec-750d-4f0a-bcc5-2c9e5d38ed32" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 72 | Um médico qualquer não deveria poder deletar um plantão. | AF1 - Funcionalidade; <br> CO3 - Affordance | Caso o profisisonal não seja líder, remover esse botão ou deixar ele desabilitado | Alto | Moderado |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/35428a56-1671-43a1-b9d2-40b11451b82c" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 73 | Não é possível pegar um plantão disponível para ser pego acessando pelo detalhamento da escala | AF1– Funcionalidade | Adicionar ao guard do plantão o detalhamento por meio da escala | Médio | Médio |

<img width="600" alt="1 1" src="https://github.com/user-attachments/assets/6d2515a8-0656-43e4-82e3-05fba2c4fba1" />

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/e35eecc2-b49e-4197-bf8a-f22344c9514e" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 74 | No card do plantão presente no detalhamento da escala o termo quando o plantão está completa não passa essa ideia | CO1- Linguagem apropriada | Corrigir os termos para ficarem adequadas as suas situações | Baixo | Leve |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/b31bdefd-0564-4175-8434-3e7cb0a362b7" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 75 | A mensagem de erro não trás um feedback do que realmente aconteceu para ter dado erro | CO2- Feedback adequado | Passar as mensagens de erro tratadas do back para o modal | Médio | Alto |

<img width="600" alt="1 2" src="https://github.com/user-attachments/assets/74064c17-aca7-4c2b-9909-373d31af0841" />

---

### CDU 07 - Conectar com Google Calendar

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 76 | A tela de conexão com Google Calendar não explica de forma objetiva a finalidade e o escopo do uso dos dados antes da autorização. | PS2 - Responsabilidade com dados pessoais | Inserir texto de transparência junto ao botão de conexão, com finalidade, escopo de dados e link para política de privacidade da integração. | Alta | Leve |

<img width="600" alt="06-ps2-privacidade" src="https://github.com/user-attachments/assets/d579ef05-0285-4c2c-9610-7c40e96b984b" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 77 | Ao conectar com Google Calendar, se a autorização falhar, não há mensagem clara explicando o motivo (permissão negada, conta inválida, erro de rede). Usuário fica sem saber por que não conseguiu conectar. | CO2 - Feedback adequado | Exibir mensagem de erro específica (ex.: "Você negou o acesso ao Google Calendar" ou "Erro de conexão com Google. Tente novamente") com botão para retentar. | Baixa | Moderado |

<img width="600" alt="Image" src="https://github.com/user-attachments/assets/e39a957b-7149-4564-ac6c-64c80cad39a6" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 78 | Após conectar com sucesso ao Google Calendar, não há feedback visual confirmando a conexão ativa (ex.: ícone de check, mensagem de sucesso, ou status visível). Usuário não tem certeza se a integração está funcionando. | CO2 - Feedback adequado | Exibir mensagem de sucesso (ex.: "Conectado com sucesso ao Google Calendar") com ícone de confirmação. Mostrar status da conexão de forma permanente na página (ex.: "Status: Conectado"). | Baixa | Leve |

<img width="600" alt="Image" src="https://github.com/user-attachments/assets/30347d23-5bc8-4c85-b799-f969c69e1051" />

---

### CDU 08 - Dashboard

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 79 | Gráfico de carga horária não mostra o valor total de horas nominal. | FM1–Visibilidade | Exibir o total de horas previstas no centro ou legenda do gráfico. | Média | Médio |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/07c273ef-d0de-4832-8363-e31cb0f7f6ca" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 80 | Os dados métricos, próximo plantão e carga-horária mensal são dados fixos | AF1 - Funcionalidade | Implementar endpoint GET para retornar os dados reais. | Alto | Grande |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/88e3ce32-a9d0-40a5-b64f-72231590d6dd" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 81 | Ícone status "livre" no card Carga Horaria Mensal não existe | CO4–Metáfora | Adicionar ícone de relógio no quadradinho | Baixo | Leve |

<img width="600" alt="1" src="https://github.com/user-attachments/assets/4385d3e0-ef57-4204-a1b9-c412ef02cebc" />

---

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 82 | Botão de detalhar plantão não redireciona para a tela de plantão marcado | AF1– Funcionalidade | Resgatar plantão verdadeiro do back para haver correto redirecionamento | Médio | Leve |

<img width="600" alt="2" src="https://github.com/user-attachments/assets/66703ae5-6466-400b-9bbf-a895818e8f6f" />

--- 

| Nº | Problema | Princípios violados | Sugestão | Gravidade | Esforço |
|----|----------|---------------------|----------|-----------|---------|
| 83 | Dados dos cards estão todos sem fazer sentido com os dados reais do sistema | AF1– Funcionalidade | Ajustar backend para entregar dados corretos | Médio | Alto |

<img width="867" height="268" alt="3" src="https://github.com/user-attachments/assets/ce4e453d-2f1c-4d14-a8b0-47413e6c9636" />
