# Relatório Geral da Avaliação Empírica

Esse documento consta a união dos documentos produzidos com base na avaliação empírica feita individualmente por cada membro do Pulso. Aqui, podemos compreender um resumo geral do material desenvolvido, decisões projetuais, dados quantitativos e as conclusões do grupo.

## Histórico de Revisões

| **Data**       | **Versão** | **Descrição**  | **Autor**                                     |
|----------------|------------|----------------|-----------------------------------------------|
| **19/06/2026** | 1.0        | Versão Inicial | [Gustavo Maia](https://github.com/gusttavokr) |
| **29/06/2026** | 2.0        | Versão Preenchida | [Gustavo Maia](https://github.com/gusttavokr) |

## 1. Decisões Projetuais

| **Nº** | **Problema** | **Onde foi detectado (Inspeção x Teste)** | **Gravidade** | **Esforço** | **Sugestão** |
|--------|--------------|-------------------------------------------|---------------|-------------|--------------|
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
| 11 | Botão "não possuo uma conta" está visualmente apagado, com baixo contraste | Autenticação | Médio | Leve | Aumentar destaque visual do link, equiparando com o botão de login com Google |
| 12 | Selecionar um perfil de cadastro não avança automaticamente para a próxima etapa | Cadastro | Leve | Leve | Avançar para a próxima etapa automaticamente ao selecionar o perfil, eliminando o clique extra em "continuar" |
| 13 | Não é possível pegar plantões pelo fluxo esperado | Plantões Abertos | Alto | Alto | Investigar e corrigir o fluxo de captura de plantão; adicionar botão de ação direta na listagem |
| 14 | Acesso ao Google Calendar não está claro na tela inicial | Dashboard | Leve | Leve | Tornar o botão/atalho de sincronização com Google Calendar mais evidente na tela principal |
| 15 | Atalho para edição de perfil ausente na tela inicial | Dashboard | Leve | Leve | Adicionar atalho de edição de perfil acessível diretamente pelo Dashboard |
| 16 | Não é possível criar plantões dentro de uma escala | Criar Plantão | Alto | Alto | Corrigir o fluxo de criação de plantão vinculado à escala |
| 17 | Não é possível alocar profissionais | Alocar Profissional | Alto | Alto | Corrigir a lógica de alocação; investigar se há dependência de horário ou estado da escala bloqueando a ação |
| 18 | Não é possível editar uma escala existente | Detalhar Escala | Alto | Alto | Criar e expor o fluxo de edição de escala (nome, setor e demais campos) |
| 19 | Feedback de exclusão de escala exibe mensagem incorreta ("escala criada com sucesso") | Minhas Escalas | Médio | Leve | Corrigir o toast/mensagem de retorno para refletir a ação realizada ("escala excluída com sucesso") |
| 20 | Operações de editar, excluir e publicar exigem entrar no detalhe; não há ações diretas na listagem | Minhas Escalas | Médio | Médio | Adicionar menu de ações inline na listagem (editar, excluir, publicar) sem necessidade de navegar para o detalhe |
| 21 | Próximos plantões | Dashboard | Baixo | Moderado | "Colocar já pensando no amanhã, precisamos pensar em 2 ou 3 plantões a mais. Já nos preparamos pro próximo mês. Não pensar só no próximo." |
| 22 | Filtro localização | Telas de listagem | Médio | Moderado | "Acho que a data deveria ter filtro por localização, as vezes posso focar em um só lugar." |
| 23 | Filtro de Data | Telas de listagem | Médio | Moderado | "Seria bom que a data fosse por período." |
| 24 | Ver escala | Detalhe Plantão | Grave | Baixo | "Eu só acho que eu poderia ver a escala antes de pegar o plantão.", deixar o botão com visual clicável. |
| 25 | Filtragem | Telas de listagem | Grave | Moderado | "Os filtros não estão funcionando", corrigir funcionalidade. |
| 26 | Criação de Plantão | Criar plantão | Grave | Moderado | "Não está criando os plantões", corrigir funcionalidade. |
| 27 | Edição de Escala | Detalhe Escala | Grave | Leve | "Não é possível editar. Deveria ter um ícone de um lápis, ou um botão." |
| 28 | Feedback Adequado | Erro | Grave | Moderado | Retornar mensagem identificando a causa do erro para erros simples. |
| 29 | Feedback sobre o Profissional | Alocação de Profissional | Baixo | Moderado | "Só acho que na alocação do profissional, deveria ter uma informação mais clara sobre a disponibilidade do profissional, nem todo mundo sabe ler um gráfico, e esse em específico não fala sobre a quantidade de plantões do profissional. Saber se ele está em outro setor, simplificar, ficar mais claro." |
| 30 | Tela de cadastro não mostra o nome do sistema | Autenticação | (Leve) | (Leve) | Adicionar junto aos corações o nome do sistema |
| 31 | Dados não são salvos quando volta e avança entre etapas | Cadastro | (Leve) | (Médio) | Adicionar váriaveis que passam entre as etapas sem perder os dados |
| 32 | Todos os inputs estão ruins com o modo escuro | Todas | (Leve) | (Leve) | Remover propriedade do modo escuro do componente pronto |
| 33 | Quando é a primeira vez entrando na tela inicial, os card não ficam bem formatados | Tela inicial | (Leve) | (Médio) | Usar mesmo booleano para a sidebar e para a organização dos cards |
| 34 | Sidebar não está mostrando "meus plantões" e "plantões abertos" | Tela inicial | (Alta) | (Leve) | Alterar visualização do modo escuro |
| 35 | O nome "plantões abertos" ainda não está intuitivo | Tela inicial | (Leve) | (Leve) | Buscar novas opções de nome para usar |
| 36 | Setor e especialidade não estão bem diferenciados, não dá pra saber quem é quem | Detalhe do plantão | (Média) | (Leve) | Adicionar texto escrito "Setor:" e "Especialidade:" |
| 37 | Depois de pegar um plantão, eu não consigo me desfazer dele | Meus plantões | (Alta) | (Alto) | Criar toda lógica de troca de plantão |
| 38 | Ao criar um plantão, não é possível ver aonde esse plantão ficou na escala enquanto eu estou criando, o calendário some | Criar plantão | (Leve) | (Alto) | Deixar criar escala como modal |
| 39 | Informações dos profisisonais não aparecem | Alocar profissional | (Alto) | (Leve) | Ajustar visualização no modo escuro do navegador |
| 40 | Não dá pra saber que não é possível remover um profissional porque a escala está publicada | Detalhar plantão | (Leve) | (Leve) | Adicionar informações da escala ao detalhe do plantão |
| 41 | Processo de alocar profissionais é difícil de localizar e compreender. | Detalhes do Plantão | Grave | Moderado | Tornar a opção de alocação mais visível e simplificar o fluxo de associação de profissionais. |
| 42 | Remover um profissional do plantão não foi encontrado pelo usuário. | Gerenciamento do Plantão | Grave | Moderado | Disponibilizar um botão de remoção visível e consistente com as demais ações de gerenciamento. |
| 43 | Fluxo de gerenciamento entre escalas, plantões e profissionais foi considerado complexo e pouco intuitivo. | Módulo do Líder de Escala | Grave | Grande | Simplificar o fluxo, reduzir etapas e tornar a sequência de ações mais linear. |
| 44 | A tela inicial não comunica claramente o propósito do sistema para novos usuários. | Tela Inicial | Baixo | Leve | Adicionar uma descrição breve ou elementos visuais que expliquem rapidamente a finalidade da plataforma. |
| 45 | **Responvidade:** Todo o sistema não está preparado para lidar com diferentes tamanhos de tela. | Inspeção | Alto | Grande | Implementar design responsivo consistente em todo o sistema, garantindo adaptação adequada a diferentes resoluções e dispositivos, com foco na legibilidade e usabilidade da escala. |
| 46 | **Sidebar:** Alguns itens do menu lateral parecem disponíveis (Instituições, Calendário, Trocas e repasses, Relatórios), mas não levam a fluxos claros. Isso reduz previsibilidade da navegação. | Inspeção | Alta | Moderado | Exibir apenas itens com rota funcional por perfil. Se uma área não estiver pronta, ocultar ou sinalizar como "Em desenvolvimento" sem redirecionamento silencioso. |
| 47 | **Breadcrumb:** O breadcrumb de navegação não possui `aria-current="page"` ou indicação visual clara da página ativa, dificultando a orientação de usuários que usam leitores de tela. | Inspeção | Baixa | Leve | Adicionar `aria-current="page"` ao item ativo do breadcrumb e destacar visualmente com cor ou negrito. Garantir que cada nível tenha label semântico. |
| 48 | O ícone de toggle para mostrar/ocultar senha utiliza um elemento `<span>` com `(click)` ao invés de um `<button>` semântico, prejudicando a navegação por teclado (Tab, Enter) e deixando foco visual pouco evidente para leitores de tela. | Inspeção | Alta | Leve | Converter o `<span>` para `<button>` com `aria-label="Mostrar/ocultar senha"` e garantir foco visível com `:focus-visible` em CSS. Ativar com Enter e Espaço. |
| 49 | O campo de e-mail no login não exibe placeholder ou label associado indicando o formato esperado (ex.: "usuario@email.com"). Usuário pode tentar inserir dados em formato incorreto. | Inspeção | Baixa | Leve | Adicionar `<label>` associada com `for="email"` ou placeholder descritivo. Validar e exibir mensagem de erro se formato for inválido (ex.: "E-mail inválido"). |
| 50 | Qualquer profissional autenticado consegue deletar plantões criados por outros, independentemente de permissão ou role no sistema. Não há validação de propriedade ou papel (liderança) no backend antes de permitir DELETE. | Inspeção | Alta | Grande | Implementar verificação de autorização: apenas o criador do plantão ou administrador pode deletar. No frontend, remover/desabilitar botão de lixeira para profissionais sem permissão. Retornar erro 403 se tentarem deletar sem autorização. |
| 51 | Na etapa 2 do cadastro (Registrar Profissional/Instituição), a validação da Data de nascimento não exibe mensagem de erro quando o campo é deixado vazio, enquanto todos os outros campos obrigatórios (Nome, E-mail, Telefone, Senha) exibem feedback de erro consistente. | Inspeção | Baixa | Leve | Uniformizar a validação de todos os campos obrigatórios, exibindo mensagem de erro padronizada também para Data de nascimento quando não preenchida. |
| 52 | Na etapa 2 do cadastro, o campo "Confirmar senha" é obrigatório, porém não exibe mensagem de erro quando deixado vazio, diferente dos demais campos que mostram feedback claro. Isso cria inconsistência de validação visual. | Inspeção | Baixa | Leve | Adicionar validação visual de erro para o campo "Confirmar senha" seguindo o mesmo padrão dos outros campos obrigatórios. |
| 53 | Na etapa 1 do cadastro, as opções de seleção (Profissional/Instituição) utilizam `<div>` com `(click)` ao invés de `<button>` ou `<input type="radio">` semânticos, prejudicando acessibilidade por teclado e leitores de tela. | Inspeção | Alta | Moderado | Trocar os `<div>` por `<input type="radio">` com `<label>` associadas, ou converter em `<button>` com `aria-pressed` e semântica de toggle. Garantir navegação por Tab. |
| 54 | Na etapa 3 do cadastro (Upload de foto), o campo de upload utiliza um `<div>` com `(click)="fu.choose()"` ao invés de um `<button>`, dificultando acesso por teclado e não aparecendo como elemento interativo para tecnologias assistivas. | Inspeção | Alta | Moderado | Envolver o `<p-fileUpload>` em um `<button>` semântico ou usar `<label>` associada com `aria-label="Enviar foto de perfil"`. Garantir ativação por Enter/Espaço. |
| 55 | Permite publicar escala já publicada sem alerta | Inspeção | Alta | Baixo | Bloquear ação ou solicitar confirmação |
| 56 | Botão de excluir escala não funciona | Inspeção | Alta | Grande | Analisar o usuário autenticado e dar a permissão com o controller, em usuário que não pode, remover o ícone. |
| 57 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual especialidade cada escala faz referência (principalmente por ter um filtro de especialidades) | Inspeção | Média | Baixo | Adicionar ícones assim como existem nos plantões |
| 58 | Os passos do modal de criação (1, 2, 3) não possuem rótulos descritivos. | Inspeção | Média | Baixo | Adicionar nomes às etapas (ex: 1. Identificação, 2. Horários). |
| 59 | Ausência de botão "Cancelar" explícito nos modais, dependendo apenas do "X" de fechar. | Inspeção | Leve | Baixo | Incluir botão "Cancelar" ao lado de "Próximo" ou "Salvar". |
| 60 | O ícone de rascunho é igual ao de publicado. | Inspeção | Baixa | Leve | Colocar um ícone de lápis |
| 61 | Não é possível detalhar a escala clicando em cima da escala ou no nome, apenas no botão. | Inspeção | Baixo | Moderado | Referenciar o link do plantão na tag do card ou no título e no botão. |
| 62 | Na listagem de escalas, não existe nos cards de escala nada indicando a qual mês ela faz referência (para o mês ser indicado ele deve ser escrito no nome da escala) | Inspeção | Média | Baixo | Junto ao nome da escala colocar o mês automaticamente e não ter que escrever no nome da escala. |
| 63 | Não é possível fechar modal de criar escala simplesmente clicando fora do modal | Inspeção | Leve | Baixo | Ao clicar em qualquer parte fora do modal ele deve fechar automaticamente |
| 64 | Depois de criado uma escala, se for criar outra o modal de criação inicia no passo 2 | Inspeção | Alta | Baixo | Sempre que o formulário estiver vázio, iniciar pelo passo 1 |
| 65 | Ícones e cores para os status das escalas estão diferentes nas telas de listagem e de detalhamento | Inspeção | Leve | Baixo | Usar mesmo padrão na listagem de escalas |
| 66 | No detalhamento de escalas não há ícone indicando qual especialidade a escala faz referência | Inspeção | Leve | Baixo | Adicionar ícone de especialidades a escala faz referência |
| 67 | Não há seta de voltar para tela anterior pela tela de detalhamento de escala | Inspeção | Alta | Médio | Adicionar botão de voltar na parte superior esquerda da tela |
| 68 | No modal de exclusão de escala, os rótulos dos botões geram dúvida sobre qual ação confirma a exclusão e qual ação cancela. | Inspeção | Baixa | Leve | Diferenciar visualmente e textualmente as ações de cancelar e excluir, mantendo linguagem direta e inequívoca. |
| 69 | Ao listar escalas criadas, não há paginação ou scroll infinito claramente indicado. Usuários podem não perceber que existem mais escalas abaixo, levando a incompletude da visualização. | Inspeção | Baixa | Moderado | Implementar paginação explícita com números de página ou botão "Carregar mais" com feedback claro. Mostrar total de escalas e posição atual (ex.: "Mostrando 1-10 de 45"). |
| 70 | Permite datas inválidas (passado e fim antes do início) | Inspeção | Alto | Baixo | Validar datas |
| 71 | Calendário permite datas no passado | Inspeção | Alto | Baixo | Bloquear datas inválidas |
| 72 | As opções de especialidades não possuem ordenação e nem busca por escrita. | Inspeção | Médio | Moderado | Utilizar componente de input ordenado e autopreenchido do PrimeNG |
| 73 | É possível criar um plantão com 1 minuto de duração. | Inspeção | Alta | Grande | Deve gerar um erro exigindo uma diferença mínima. |
| 74 | A entrada de "Quantidade de profissionais" não segue o padrão das outras entradas. Além disso, diversos componentes do Pulso no modo escuro estão pretos. | Inspeção | Médio | Leve | Testar em outros navegadores/dispositivos |
| 75 | O cursor não indica que o plantão é clicável. | Inspeção | Baixo | Leve | Atualizar o plantão com hover. |
| 76 | É possível criar um plantão em uma escala já publicada. | Inspeção | Alta | Moderado | Bloquear botão se escala já publicada |
| 77 | Na listagem dos plantões, não tem como saber qual ícone está relacionado a qual especialidade pela filtragem | Inspeção | Médio | Baixo | Adicionar os ícones da especialidade junto ao filtro |
| 78 | No detalhamento do plantão, o ícone de profissionais não está coerente | Inspeção | Baixo | Leve | Trocar o ícone por um que faça sentido |
| 79 | Na criação/atualização de plantão, não existe botão para voltar passos assim como na tela de criação de escala | Inspeção | Baixa | Leve | Adicionar o botão de voltar semelhante ao já existente no criar escala |
| 80 | No detalhamento do plantão, o alinhamento do ícone da especialidade está incorreto | Inspeção | Baixo | Leve | Deixar mais centralizado o ícone |
| 81 | Texto invisível por conflito de tema: Quando o navegador está em modo escuro, as informações dos profissionais (nome e contato) mudam para a cor branca, mas o fundo do card permanece branco, eliminando o contraste. | Inspeção | Alta | Baixo | Forçar cores fixas (hardcoded) para os textos e fundos desses cards ou utilizar uma meta tag para obrigar o modal a renderizar apenas em `light mode`. |
| 82 | Botão remover sem padrão visual | Inspeção | Baixa | Baixo | Usar cor vermelha |
| 83 | Ausência de feedback quando a busca não retorna resultados. | Inspeção | Média | Baixo | Exibir mensagem "Nenhum profissional encontrado". |
| 84 | Ao fechar modal de alocação de profissional sem confirmar o profissional, os profissionais que tinham sido marcados não são salvos | Inspeção | Médio | Médio | Salvar a lista de profissionais marcados |
| 85 | O botão de remover está ativo mesmo não tendo selecionado nenhum profissional. | Inspeção | Médio | Moderado | Verificar se não há profissionais alocados e mudar estado do botão. |
| 86 | Ao confirmar alocação dos profissionais para detalhar o plantão, o usuário é direcionado para a página principal. | Inspeção | Médio | Moderado | Atualizar rota do botão |
| 87 | O ícone da lixeira cinza indica que não é possível remover o profissional (está desabilitado), mas é possível pois a escala ainda está em rascunho. | Inspeção | Alto | Grande | Atualizar estado do botão de acordo com status da escala. |
| 88 | Depois de selecionado um profissional não é mais possível deseleciona-lo | Inspeção | Baixa | Leve | Adicionar um x para fechar detalhamento do profissional |
| 89 | Não é possível fechar o modal de alocar profissional a plantão clicando fora da área do modal | Inspeção | Baixo | Leve | Adicionar função que ao clicar na área externa do modal, ele fecha |
| 90 | Em caso de um profissional ter um e-mail grande, o email sai da área do card | Inspeção | Baixa | Leve | Se um e-mail ultrapassar uma quantidade x de caracteres, adiciona "..." no final |
| 91 | Em caso de um profissional ter um nome grande, o nome faz o card ficar ocupando uma área maior | Inspeção | Baixa | Leve | Se um nome ultrapassar uma quantidade x de caracteres, adiciona "..." no final |
| 92 | A listagem exibe todos os plantões em abertos | Inspeção | Médio | Moderado | Seria bom um paginator que limita a cada 20 plantões, por exemplo. |
| 93 | Filtro de especialidade em dropdown simples pode ser lento se a lista crescer. | Inspeção | Baixa | Médio | Utilizar seletor com busca interna. |
| 94 | Distinção de status baseada apenas em cor (prejudicial para daltônicos). | Inspeção | Média | Baixo | Adicionar rótulos de texto ou ícones distintos para cada status. |
| 95 | O ícone de urgência não varia de acordo com o tempo próximo do plantão aberto. | Inspeção | Alto | Grande | Atualizar estado do ícone conforme proximidade do dia do plantão. |
| 96 | O alinhamento do status do plantão nos cards não está adequada | Inspeção | Baixo | Leve | Adicionar a mesma distancia entre todos os elementos mais a direita do card |
| 97 | É possível tentar fazer o filtro de preços negativos | Inspeção | Baixo | Médio | Adicionar um limitador com uma mensagem de que não é possível filtrar plantões de custo negativo |
| 98 | Os cards de plantões abertos não exibem o número de vagas disponíveis claramente de forma destacada. Usuário precisa ler todo o texto para descobrir se ainda há posições. | Inspeção | Baixa | Leve | Destacar visualmente o número de vagas. |
| 99 | No card do plantão presente no detalhamento da escala o termo quando o plantão está completa não passa essa ideia | Inspeção | Baixo | Leve | Corrigir os termos para ficarem adequadas as suas situações |
| 100 | Ao conectar com Google Calendar, se a autorização falhar, não há mensagem clara explicando o motivo (permissão negada, conta inválida, erro de rede). Usuário fica sem saber por que não conseguiu conectar. | Inspeção | Baixa | Moderado | Exibir mensagem de erro específica. |
| 101 | Após conectar com sucesso ao Google Calendar, não há feedback visual confirmando a conexão ativa. | Inspeção | Baixa | Leve | Exibir mensagem de sucesso e status permanente da conexão. |
| 102 | Gráfico de carga horária não mostra o valor total de horas nominal. | Inspeção | Média | Médio | Exibir o total de horas previstas no centro ou legenda do gráfico. |
| 103 | Ícone status "livre" no card Carga Horaria Mensal não existe | Inspeção | Baixo | Leve | Adicionar ícone de relógio no quadradinho |
| 104 | Botão de detalhar plantão não redireciona para a tela de plantão marcado | Inspeção | Médio | Leve | Resgatar plantão verdadeiro do back para haver correto redirecionamento |
| 105 | Dados dos cards estão todos sem fazer sentido com os dados reais do sistema | Inspeção | Médio | Alto | Ajustar backend para entregar dados corretos |

## 2. Dados Quantitativos

| **Total de Problemas (Geral)** | **Total de Problemas (Inspeção)** | **Total de Problemas (Teste)** |
|--------------------------------|-----------------------------------|--------------------------------|
| **105** | **61** | **44** |

## 3. Conclusão

A avaliação do sistema foi conduzida por meio de duas abordagens: inspeção com base na Avaliação Heurística e testes de usabilidade com base na Avaliação Empírica com usuários, resultando na identificação de:
- 105 decisões projetuais
- 44 (Teste)
- 61 (Inspeção). 

Durante a consolidação dos resultados, problemas repetidos entre as duas técnicas foram unificados, priorizando os registros obtidos durante os testes por representarem dificuldades efetivamente observadas na interação dos usuários com o sistema. A combinação das duas metodologias permitiu identificar tanto falhas percebidas na experiência de uso quanto problemas de interface, acessibilidade, consistência e implementação que poderiam passar despercebidos pelos usuários finais. Dessa forma, o conjunto de decisões projetuais obtido fornece uma base sólida para o planejamento das melhorias do sistema, contribuindo para o aumento da usabilidade, acessibilidade, eficiência e qualidade geral da aplicação.
