# Documento de Visão

## Histórico de Revisões

| Data                |  Versão             |          Descrição  |  Autores            |
| :-----------------: | :-----------------: | :-----------------: | :-----------------: |
| 26/04/2025 | 1.0 | Versão inicial do sistema | [Asaph Arruda](https://github.com/ArrudaAsaph), [Flavio Matias](https://github.com/FlavioMatias), [George Silva](https://github.com/silva-george), [Gustavo Maia](https://github.com/gusttavokr), [Icaro Gabriel](https://github.com/IcaroGabrielIcaro) |
| 15/05/2025 | 2.0 | Atualização dos requisitos funcionais | [Flavio Matias](https://github.com/FlavioMatias), [Icaro Gabriel](https://github.com/IcaroGabrielIcaro) |
| 19/09/2025 | 3.0 | Segunda versão do sistema | [Flavio Matias](https://github.com/FlavioMatias), [Icaro Gabriel](https://github.com/IcaroGabrielIcaro) |

## 1. Objetivo do projeto

Nascido da urgência em substituir o caos por eficiência, o Pulso surge para transformar a gestão de plantões e horários nas instituições de saúde. Em um cenário marcado por falhas de comunicação, buscas desgastantes por informações e líderes de escalas correndo contra o tempo para preencher escalas, a plataforma propõe uma gestão precisa, humana e centralizada, eliminando inconsistências e garantindo que a operação reflita um propósito maior: o cuidado.

Mais do que organizar horários, o Pulso moderniza a relação entre gestão e médicos por meio de uma solução digital estruturada em módulos complementares: o do líder de escala, que cria e gerencia escalas mensais, define plantões fixos e envia oportunidades com segurança e alertas automáticos contra violações trabalhistas; e o do profissional, que acessa facilmente suas escalas, confirma presença, recebe notificações de novos plantões alinhados às suas preferências e conta com ferramentas para trocas e repasses. Afinal, a saúde não se improvisa — constrói-se com planejamento, tecnologia e uma ponte entre o caos diário e a excelência no atendimento.

## 2. Descrição do problema

| **Categoria**       | **Descrição**                                                                                     |  
|----------------------|---------------------------------------------------------------------------------------------------|  
| **Problema**         | Descentralização e desorganização das informações sobre escalas de trabalho de profissionais da saúde, com falta de clareza em plantões, jornadas, locais e carga horária. Ferramentas fragmentadas (WhatsApp, planilhas, folhas impressas) ampliam ruídos na comunicação e geram lacunas entre profissionais. |  
| **Afeta**            | Médicos, líderes de escala, equipes hospitalares e indiretamente os pacientes.                 |  
| **Impacta**          | Aumenta o estresse dos profissionais, gera retrabalho, falhas operacionais (trocas mal alinhadas, dúvidas sobre escalas) e riscos à qualidade do atendimento. |  
| **Solução**          | Plataforma inteligente que centraliza todas as informações de plantões (horários, trocas, escalas) em um único ambiente, com comunicação integrada, avisos automáticos, sugestões de escalas e interface simplificada. Oferece acesso rápido via dispositivos móveis e integração com Google Agenda, reduzindo a sobrecarga mental e operacional. |  

## 3. Descrição dos usuários

| **Nome** | **Descrição** | **Responsabilidade** |  
|----------|---------------|----------------------|  
| **RH** | Setor administrativo responsável pela habilitação do uso da plataforma, gestão de acessos e vinculação dos profissionais | - Realizar cadastro dos líderes de escala;<br> - Vincular profissionais parceiros e cooperados;<br> - Gerenciar permissões e acessos conforme papéis;<br> - Garantir que apenas usuários autorizados utilizem as principais funcionalidades da plataforma;<br> - Gerenciar o perfil do hospital na plataforma |
| **Líder de Escala** | Profissional responsável pela criação e gestão das escalas mensais, definição de plantões fixos, publicação de oportunidades e garantia do cumprimento das normas trabalhistas. | - Criar e ajustar escalas de acordo com demandas e disponibilidades;<br> - Inserir plantões vagos e disponibilizá-los como oportunidades;<br> - Monitorar cumprimento de jornadas e carga horária. |  
| **Profissional de Saúde** | Profissional (CLT, PJ) que atua diretamente nos plantões, buscando praticidade no acesso às informações e autonomia na gestão de sua agenda. | - Consultar escalas e plantões atribuídos ou disponíveis;<br> - Demonstrar interesse, confirmar presença e solicitar trocas ou repasses;<br> - Definir locais de preferência ou bloqueio;<br> - Receber notificações personalizadas de novas oportunidades;<br> - Acompanhar relatórios, agenda e histórico de plantões. |  

**Observação:**  
*Todos os usuários enfrentam desafios com ferramentas desconexas (grupos de mensagens, planilhas), mas buscam eficiência e clareza. A plataforma unifica suas necessidades: o RH garante governança e controle, o líder de escala prioriza gestão estratégica e conformidade, e o profissional valoriza acesso rápido, autonomia e redução de burocracia.*  

## 4. Descrição do ambiente dos usuários

O sistema será utilizado em dois principais contextos operacionais: ambientes administrativos de gestão hospitalar e ambientes assistenciais onde atuam médicos e outros profissionais da saúde. Os líderes de escala geralmente já acessam o sistema a partir de computadores desktop ou notebooks, localizados em salas administrativas dentro de hospitais, clínicas ou unidades de saúde e assim deve se manter por padrão. Esse público costuma dispor de conectividade estável, acesso a outros sistemas corporativos e um ambiente mais propício ao uso prolongado de plataformas web. Em alguns casos, também poderão acessar o sistema remotamente, especialmente durante reuniões virtuais ou em regime de trabalho à distância (home office), demandando uma interface compatível com tablets e laptops.

Por sua vez, os profissionais da saúde, como médicos plantonistas, tendem a acessar o sistema por meio de dispositivos móveis, como smartphones ou tablets, muitas vezes durante o turno de trabalho ou entre atendimentos. O ambiente é dinâmico e com limitações de tempo, atenção e infraestrutura de rede, o que exige uma interface otimizada para acesso rápido, fluido e responsivo. O sistema precisa garantir comunicação efetiva mesmo em cenários de conectividade limitada, sendo fundamental a integração com canais como WhatsApp para envio de alertas e confirmações. Além disso, muitos desses profissionais possuem nível variável de familiaridade com tecnologias digitais, o que reforça a necessidade de uma experiência intuitiva, direta e centrada no usuário. Não excluindo a possibilidade de acesso via desktop ou notebooks sejam eles pessoais ou institucionais por meio da plataforma web.

## 5. Principais necessidades dos usuários

Oslíderes de escala necessitam de ferramentas robustas e integradas para garantir o funcionamento contínuo das escalas e a fluidez da comunicação com suas equipes. Entre suas principais necessidades estão o controle eficiente das escalas, com recursos para criação, edição e visualização integrada; agilidade na comunicação com os profissionais de saúde por meio de notificações automatizadas; e transparência nas movimentações de plantão, como trocas, substituições e registros de ponto. Também é essencial o acompanhamento em tempo real do banco de horas e metas institucionais, bem como o acesso a relatórios gerenciais e indicadores que subsidiem decisões estratégicas. A automação na distribuição de plantões considerando disponibilidades dos profissionais é outro fator crítico para reduzir conflitos e otimizar a cobertura de turnos. Por fim, líderes de escala precisam de ferramentas colaborativas, eficiêntes e claras para melhor desempenho em suas funções básicas.

Já os médicos buscam principalmente autonomia, clareza e organização no gerenciamento de suas rotinas de trabalho. Valorizam a possibilidade de escolher oportunidades em um feed personalizado, com informações completas sobre local, horários, pagamento, benefícios e formas de contato. Podem registrar preferências de regiões ou raio de atuação, restringir ofertas distantes, marcar locais favoritos e manter uma lista pessoal de instituições onde não desejam atuar. A plataforma também oferece praticidade para solicitar trocas de turno com colegas, comunicar-se diretamente com líderes de escala, confirmar interesse em plantões e receber notificações automáticas sobre novas vagas compatíveis com seu perfil ou mudanças na escala. Transparência no controle da jornada de trabalho é assegurada pelo cálculo de ganhos líquidos e acompanhamento do banco de horas, enquanto a agenda pessoal evita sobreposições de compromissos em múltiplas instituições. Recursos como histórico de plantões e comparativos gamificados entre colegas completam um ecossistema pensado para tornar a experiência mais funcional, confiável e humanizada.

## 6. Alternativas concorrentes
Nosso projeto tem como concorrentes o Shosp, o CMG Escala, o Conecta Saúde, o Beep Saúde, e o Pega Plantão. Diante disso, analisaremos os pontos fortes e fracos de cada um desses aplicativos e iremos reutilizar de funcionalidade.

| **Aplicativo** | **Descrição** | **Pontos Fortes** | **Pontos Fracos** |
|----------------|---------------|-------------------|-------------------|
| **Shosp**  | Plataforma de gestão hospitalar que inclui módulos de escalas médicas. | Solução completa para hospitais; integração com outras áreas administrativas. | Complexo e caro para equipes menores; excesso de funções para quem quer apenas gerenciar plantões. |
| **CMG Escala** | Sistema de gestão de escalas médicas, voltado para hospitais e clínicas. | Especializado em escalas; gestão específica para área médica. | Interface pouco intuitiva; pouca flexibilidade para trocas dinâmicas de plantão. |
| **Conecta Saúde** | Aplicativo para gestão de escalas e comunicação entre profissionais de saúde. | Boa comunicação integrada; usabilidade simplificada. | Limitado em customizações de escalas complexas ou dinâmicas de troca de plantões. |
| **Beep Saúde (interno)** | Plataforma interna da Beep Saúde para gestão das escalas dos seus próprios profissionais. | Ótima experiência de usuário para uso interno; escalas claras e organizadas. | Não disponível para o mercado geral; solução fechada. |
| **Pega Plantão** | Aplicativo focado na organização, troca e disponibilização de plantões médicos de forma rápida e fácil. | Foco específico em plantões; facilita trocas, comunicação e disponibilização; integração com WhatsApp. | Ainda em processo de expansão de funcionalidades; depende de boa adesão dos usuários para sucesso. |
| **Whitebook** | Aplicativo médico da PEBMED com suporte à decisão clínica, incluindo gerenciador de plantões e calculadora de honorários. | Conteúdo baseado em evidências científicas; mais de 3 mil tópicos; suporte offline; ferramentas práticas. | Foco principal em conteúdo clínico; recursos de gestão de plantões podem não ser tão robustos quanto soluções dedicadas. |
| **CFM** | Plataforma do Conselho Federal de Medicina com informações técnicas e científicas para médicos. | Acesso a mais de 50 mil conteúdos digitais; integração com CRM Virtual e Prescrição Eletrônica. | Não é uma ferramenta de gestão de escalas; foco em suporte à decisão clínica e atualização profissional. |
| **Coopmed** |Cooperativa médica que oferece serviços de gestão para profissionais de saúde. |Suporte personalizado; foco em cooperados; serviços administrativos e jurídicos. | Disponibilidade limitada a membros da cooperativa; não é uma plataforma pública de gestão de escalas. |

**Observação:**  
*O **Pega Plantão** surge como uma solução mais direta para o que médicos e equipes realmente precisam no dia a dia: **organizar, disponibilizar e negociar plantões** de maneira prática, sem a complexidade ou o excesso de funções que outros sistemas trazem.*

## 7. Visão geral do produto

O sistema tem como foco ser uma solução digital integrada e moderna que organize e facilite a administração de escalas em instituições públicas e privadas de saúde. Seu principal objetivo é otimizar a rotina dos líderes de escala e profissionais de saúde plantonistas, promovendo uma comunicação eficiente, maior transparência na gestão de horários e decisões mais assertivas no processo de alocação de plantões.

A plataforma é composta por módulos distintos, voltados aos diferentes perfis de usuários: RH, líderes de escala e profissionais de saúde. Cada módulo contempla funcionalidades específicas, mas todos se integram para garantir fluidez, confiabilidade e alinhamento das operações.

Para o RH, o sistema disponibiliza ferramentas de governança e administração de acessos, permitindo o cadastro de líderes de escala, a vinculação de profissionais parceiros (empresas privadas) e cooperados (cooperativas), além do gerenciamento de permissões. Esse módulo garante que apenas usuários autorizados tenham acesso ao sistema, assegurando confiabilidade e organização desde a base da operação.

Para os líderes de escala, o sistema oferece recursos como a criação e edição de escalas mensais, definição de plantões fixos, publicação de oportunidades, acompanhamento de jornadas e alertas automáticos em caso de violações trabalhistas. Esses recursos permitem uma administração proativa, ágil e em conformidade com normas, reduzindo erros e retrabalhos.

Já os profissionais de saúde contam com um ambiente digital que centraliza sua agenda de plantões, acesso a novas oportunidades alinhadas a preferências e locais de atuação, confirmação de presença, pedidos e aceite de trocas e repasses. Tal módulo promove autonomia e organização pessoal, respeitando individualidades e proporcionando maior equilíbrio entre vida profissional e pessoal.

O sistema também contempla funcionalidades de personalização e automação, como notificações inteligentes, preferências de locais e filtros de oportunidades, além de relatórios e indicadores de desempenho. A combinação desses elementos garante uma gestão moderna, colaborativa e humanizada dos plantões, reduzindo conflitos de escala, fortalecendo o vínculo entre RH, líderes e profissionais, e promovendo eficiência operacional nas instituições de saúde.

## 8. Requisitos Funcionais

| **Código** | **Ator** | **Nome** | **Descrição** | **Prioridade** |
| :--------: | :------: | :------: | :-----------: | :------------: |
| **RF01** | Médico PJ | Feed de Plantões Disponíveis | O médico pode visualizar plantões disponíveis em um feed dinâmico, filtrados por localização, raio de interesse e hospitais favoritos, com destaque para plantões urgentes. | Alta |
| **RF02** | Médico PJ | Interesse em Plantão | O médico pode demonstrar interesse em um plantão de outro médico específico com apenas um clique, facilitando a seleção pelo líder de escala. | Alta |
| **RF03** | Médico PJ / CLT | Lista Pessoal de Plantões | O médico tem acesso à sua agenda de plantões já confirmados, organizada em lista ou calendário. | Alta |
| **RF04** | Médico PJ | Repassar Plantão | O médico pode disponibilizar um plantão previamente assumido para que outros médicos o peguem, podendo inclusive oferecer bonificação como incentivo. | Alta |
| **RF05** | Médico PJ / CLT | Troca de Plantão entre Médicos | Um médico pode negociar diretamente com outro colega a troca de plantões. Após acordo, a troca é registrada no sistema e o líder de escala é apenas notificado. | Alta |
| **RF06** | Médico PJ / CLT | Confirmação de Presença | O médico pode confirmar sua presença no início do plantão pelo sistema, registrando data e horário (entrada e saída). | Alta |
| **RF07** | Médico PJ | Intituições Favoritas e Bloqueadas | O médico pode marcar instituições ou regiões como favoritos e bloquear locais onde não deseja atuar, ajustando o feed de oportunidades. | Média |
| **RF08** | Médico PJ / CLT | Calendário Pessoal | O médico possui um calendário que exibe visualmente seus plantões confirmados e anotações, podendo exportar automaticamente seus plantões confirmados para o Google Agenda. | Média |
| **RF09** | Médico PJ / CLT | Relatórios e Estatísticas | O médico pode acessar relatórios simples sobre seus plantões, incluindo quantidade realizada, valores a receber e comparativos mensais, com opção de gamificação entre colegas. | Média |
| **RF10** | Médico PJ / CLT | Acesso a Escalas | O médico pode visualizar a escala completa de plantões organizada pelo líder de escala, incluindo plantões próprios, de colegas e os que ainda estão disponíveis para repasse ou troca. | Alta |
| **RF11** | Líder de Escala | Criação e Edição de Escalas | O líder de escala pode criar e organizar escalas de plantão, definir horários, atribuir médicos e editar informações de plantões já cadastrados. | Alta |
| **RF12** | Líder de Escala | Templates de Escalas | O líder de escala pode criar e reutilizar templates de escalas (como 6h, 8h, 8h45, 12h) para facilitar a organização mensal. | Baixa |
| **RF13** | Líder de Escala | Envio de Oportunidades | O líder de escala pode enviar oportunidades de plantão para médicos PJ conforme perfil e regras da instituição. | Alta |
| **RF14** | Líder de Escala | Lista de Médicos Parceiros | O líder de escala mantém uma lista de médicos parceiros, podendo priorizar o envio de oportunidades apenas para eles. | Baixa |
| **RF15** | Líder de Escala| Visualização de Cobertura | O líder de escala pode visualizar a ocupação das escalas, identificar setores com déficit de médicos e acompanhar a distribuição dos plantões. | Alta |
| **RF16** | RH | Vinculação de Profissionais | O RH pode cadastrar e vincular profissionais de empresas privadas e cooperativas médicas para participação em escalas. | Alta |
| **RF17** | RH | Controle de Permissões | O RH pode definir os níveis de permissão dos usuários apontando como profissional ou líder de escala. | Alta |
| **RF18** | Sistema | Notificações e Alertas | O sistema envia notificações sobre eventos importantes, como novos plantões disponíveis, atribuição, trocas ou cancelamentos, garantindo que médicos e líderes de escala fiquem sempre atualizados. | Alta |
| **RF19** | Médico PJ / CLT | Perfis de Usuário |    Cada usuário possui um perfil contendo informações pessoais e profissionais (nome, especialidade, ano de formação, Pix). Líderes de escala podem acessar os perfis dos médicos colaboradores. | Baixa |


## 9. Requisitos não-funcionais

| Código  | Nome                         | Descrição                                                                                                                                             | Categoria         | Classificação  |
|:-------:|:----------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------:|:--------------:|
| RNF01   | Responsividade                | O sistema deve ser totalmente responsivo, garantindo usabilidade adequada em dispositivos móveis, tablets e desktops.                                 | Usabilidade       | Obrigatório    |
| RNF02   | Interface Intuitiva           | A interface deve ser simples, direta e de fácil compreensão para minimizar curva de aprendizado.                                                      | Usabilidade       | Obrigatório    |
| RNF03   | Proteção de Dados Sensíveis   | Dados pessoais, médicos e de plantões devem ser armazenados e transmitidos de forma segura, seguindo práticas de segurança modernas, como criptografia e autenticação.| Segurança         | Obrigatório    |
| RNF04   | Padronização de Ambiente      | O sistema deve ser dockerizado para padronizar o ambiente de desenvolvimento, testes e produção.                                                      | Infraestrutura    | Obrigatório    |
| RNF05   | Compatibilidade Cross-Browser | O sistema deve funcionar corretamente nos navegadores mais utilizados (Chrome, Firefox, Edge, Safari).                                                | Usabilidade       | Obrigatório    |
| RNF06   | Mensagens de Erro padronizadas   | O sistema deve apresentar mensagens de erro claras e orientadas ao usuário, sem exibir informações técnicas como stacktraces. Exemplo: "Erro ao salvar plantão. Tente novamente ou contate o suporte." | Usabilidade       | Obrigatório    |

## 10. Stack de Tecnologias

| Categoria             | Tecnologia      | Descrição                                                                                                                                                        |
|------------------------|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backend**            | java           | Linguagem principal escolhida por sua robustez, segurança, eficiencia e ampla adoção no mercado corporativo, sendo base para o framework utilizado. |
| **Framework Backend**  | Spring Boot    | Framework web robusto, corporativo e com ferramentas avançadas que ajudarão a garantir segurança e eficiencia por ser um sistema na area de saude. |
| **Banco de Dados**     | PostgreSQL     | Banco de dados relacional robusto, selecionado para lidar com grandes volumes de dados, transações críticas e escalabilidade futura, superando limitações do SQLite. |
| **Frontend**           | Angular        | framework voltada à criação de interfaces dinâmicas, responsivas e integração com o backend, escolhido por sua robustez e boa arquitetura.|
| **Infraestrutura**     | Docker         | Plataforma de conteinerização utilizada para padronizar ambientes, isolar dependências e facilitar o desenvolvimento, testes e deploys consistentes no futuro. |
