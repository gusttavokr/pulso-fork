# Termo de Abertura do Projeto

**Resultado do processo:** Iniciar projeto ou fase | Domínio de Governança

**Nome do projeto:** Pulso — Plantões Unificados para Logística de Saúde Organizada
**Data:** 18/05/2026

**Gestor do projeto:** Flavio Matias
**Patrocinador:** IFRN — Instituto Federal do Rio Grande do Norte (Campus Central)

**Departamento:** Saúde
**Prioridade:** Médio

**Abordagem de Entrega:** Ágil

**Versão:** 1.0

---

## 1. Objetivo

Este documento autoriza formalmente a existência do projeto **Pulso** e confere ao gestor do projeto a autoridade para alocar os recursos da equipe acadêmica nas atividades planejadas. Ele estabelece a conexão entre a iniciativa e os objetivos estratégicos do contexto em que está inserida: a melhoria da gestão de plantões médicos em instituições de saúde, partindo de um cenário acadêmico de validação e evoluindo, em fases futuras, para adoção em ambiente real.

O Pulso busca substituir práticas fragmentadas (planilhas, grupos de mensagens, escalas impressas) por uma plataforma centralizada que organiza escalas, plantões, trocas e repasses, com foco em líderes de escala, profissionais de saúde CLT e PJ, e administração hospitalar.

---

## 2. Análise de Viabilidade e Alinhamento Estratégico

### Justificativa de Negócio e Alinhamento Estratégico

A viabilidade do Pulso se apoia em três pilares: técnico, mercadológico e humano.

**Pilar técnico:** o amadurecimento de frameworks modernos (Spring Boot, Angular), serviços de nuvem e práticas de DevOps tornam plenamente factível a construção de uma plataforma web responsiva, segura e escalável dentro do prazo acadêmico definido. A infraestrutura inicial é leve e pode evoluir conforme o produto cresce, sendo coberta, em modelo de negócio futuro, por **assinaturas de funcionalidades premium** pagas por instituições e cooperativas, sem onerar o profissional individual.

**Pilar mercadológico:** existe demanda real e mal atendida. Hospitais ainda recorrem a **escalas impressas afixadas em paredes**, planilhas dispersas e grupos informais de WhatsApp para organizar plantões, o que gera esquecimento de compromissos, falhas de cobertura, trocas mal alinhadas e retrabalho. As soluções concorrentes mapeadas (Shosp, CMG Escala, Conecta Saúde, Pega Plantão, entre outras) apresentam lacunas claras em **usabilidade da interface** e em **funcionalidades específicas** como trocas dinâmicas, feed de oportunidades personalizado, integração com Google Calendar e suporte aos diferentes regimes (CLT/PJ). O Pulso se posiciona para preencher essas lacunas.

**Pilar humano:** a equipe está engajada e validou a demanda em entrevistas com médicos e líderes de escala, que demonstraram alto interesse e incentivaram a continuidade do projeto.

**Benefícios esperados:** redução de falhas operacionais, ganho de tempo para líderes de escala, maior previsibilidade de jornada para profissionais, e — futuramente — capacidade de gerar dados consolidados que apoiem decisões gerenciais em hospitais e cooperativas.

---

## 3. Descrição e Contexto do Projeto

**Situação atual:** o projeto encontra-se em fase de desenvolvimento ativo, com módulos do backend e frontend já implementados (gerenciamento de escalas, plantões, trocas, repasses, integração com Google Calendar) e cobertura crescente dos casos de uso prioritários. A documentação técnica e funcional vem sendo produzida em paralelo, e parte significativa dos artefatos arquiteturais já foi entregue nas etapas anteriores das disciplinas de PDS Web e PDS Distribuído.

**Motivação:** entrevistas conduzidas com médicos plantonistas e líderes de escala confirmaram a dor central — a desorganização das escalas e a comunicação fragmentada — e validaram positivamente a proposta de valor do Pulso. Esse retorno reforçou a motivação da equipe e direcionou as decisões de escopo das iterações.

**Estado futuro desejado:** o próximo grande passo é disponibilizar um **MVP estável em ambiente de homologação** para validação empírica com os profissionais que participaram das entrevistas iniciais. A partir dessa validação, o produto evolui em duas frentes: (i) busca de parcerias com **grandes cooperativas médicas (como a Unimed)** e hospitais regionais para piloto real; (ii) expansão progressiva do escopo de profissionais atendidos, indo além de médicos para abranger **outras categorias da área da saúde** (enfermagem, fisioterapia, técnicos, entre outros).

**Iniciativas anteriores:** o Pulso é a continuidade do trabalho iniciado em 2025 pela equipe, com versões anteriores do documento de visão (v1.0 em abril/2025, v2.0 em maio/2025 e v3.0 em setembro/2025) e entregas formais nas disciplinas de PDS Web e PDS Distribuído.

---

## 4. Objetivos SMART e critérios de sucesso

| Objetivo | Critérios de sucesso | Método de medição | Alvo |
|----------|---------------------|-------------------|------|
| Disponibilizar MVP funcional para validação empírica | MVP cobrindo os CDUs prioritários (Gerenciar Escala, Publicar Escala, Pegar Plantão, Consultar Plantões Abertos, Trocas e Repasses) em ambiente de homologação | Checklist de CDUs implementados e testados ponta a ponta | 100% dos CDUs prioritários até a validação empírica planejada |
| Validar a solução com usuários reais | Profissionais de saúde e líderes de escala executando fluxos completos sem bloqueios críticos | Sessões guiadas de testes com profissionais entrevistados + coleta estruturada de feedback | Mínimo de 5 profissionais e 2 líderes de escala participando das sessões |
| Conquistar a primeira adoção institucional regional | Pelo menos 1 hospital ou clínica regional utilizando o Pulso como ferramenta oficial de gestão de plantões | Termo de adesão / contrato de uso assinado | 1 instituição adotando até o encerramento da etapa 2 do PDS Corporativo |
| Estabelecer base para expansão | Apresentação do produto a cooperativas e hospitais de médio porte | Número de reuniões comerciais ou acadêmicas com instituições | Pelo menos 3 instituições contatadas formalmente |
| Atingir, na maturidade do produto, escala nacional | Adoção por múltiplos hospitais e cooperativas em diferentes regiões | Número de instituições ativas na plataforma | Meta de longo prazo, fora do horizonte deste termo |

---

## 5. Visão geral do escopo

### No Escopo:

- **Gestão de escalas mensais** pelos líderes de escala (criação, edição, publicação, uso de templates).
- **Gestão de plantões** vinculados a escalas, incluindo definição de horários, vagas e setores.
- **Pegar plantão**: profissionais visualizam plantões abertos e se alocam, respeitando vínculos institucionais e especialidades.
- **Trocas e repasses de plantão** entre profissionais, com aceite, recusa e notificação ao líder.
- **Feed de plantões disponíveis** com filtros por preferência, localização e instituição favorita.
- **Confirmação de presença** com registro de entrada e saída.
- **Notificações e alertas** automáticos para eventos relevantes (novos plantões, trocas, mudanças).
- **Integração com Google Calendar** para sincronização da agenda pessoal do profissional.
- **Gestão de vínculos** profissional-instituição e papéis (RH, líder de escala, profissional CLT, profissional PJ).
- **Gestão de setores** e especialidades por instituição.
- **Relatórios e estatísticas** básicas de plantões realizados.
- **Documentação técnica e funcional** (visão, casos de uso, regras de negócio, arquitetura, API via Swagger).

### Fora do Escopo:

- **Funcionalidades de pagamento integrado** ao sistema (cálculo de honorários, repasse financeiro, integração bancária ou PIX automatizado).
- **Profissionais fora da área da saúde** — o foco se mantém em profissionais médicos e, em expansão posterior, demais áreas assistenciais; categorias administrativas, técnicas não-assistenciais ou de outras indústrias estão fora.
- **Regras individuais customizadas por organização** — o sistema oferece um conjunto padrão de regras de negócio; personalizações específicas por hospital não estão contempladas nesta versão.
- **Eventos sem ligação direta a plantões** (reuniões clínicas, treinamentos, congressos, agenda particular não-plantonista).
- **Prontuário eletrônico e dados clínicos de pacientes** — o Pulso é uma ferramenta de logística de plantões, não um sistema assistencial.
- **Telemedicina, prescrição eletrônica e suporte à decisão clínica.**
- **Integração com sistemas hospitalares legados (HIS, ERP hospitalar)** — pode ser avaliada em fases futuras.
- **Aplicativos nativos para iOS e Android** — a entrega inicial é uma plataforma web responsiva.

---

## 6. Marcos importantes e datas-chave

| # | Marco | Data-alvo | Fase/Domínio | Status |
|---|--------|-----------|--------------|--------|
| 1 | Entrega do PDS Web — Etapa 1 | 06/11/2025 | Execução / Entrega Acadêmica | Concluída |
| 2 | Entrega do PDS Web — Etapa 2 | 09/02/2026 | Execução / Entrega Acadêmica | Concluída |
| 3 | Entrega do PDS Distribuído — Etapa 1 | 06/11/2025 | Execução / Entrega Acadêmica | Concluída |
| 4 | Entrega do PDS Distribuído — Etapa 2 | 09/02/2026 | Execução / Entrega Acadêmica | Concluída |
| 5 | Entrega do PDS Corporativo — Etapa 1 | 21/05/2026 | Execução / Entrega Acadêmica | Concluída |
| 6 | Validação empírica com usuários (médicos e líderes de escala) | A planejar | Validação / Homologação | Concluída |
| 7 | Entrega do PDS Corporativo — Etapa 2 | A planejar | Encerramento / Entrega Final | Planejada |

---

## 7. Organização e Governança do Projeto

A estrutura de governança do Pulso é adaptada ao contexto **acadêmico** do projeto, com papéis inspirados em práticas de gerência de projetos e adequados ao tamanho da equipe. O caminho de escalonamento padrão é: **Líder Técnico → Gestor de Projeto → Patrocinador (IFRN, via docentes orientadores)**.

| Papel | Nome | Responsabilidades | Nível de autoridade |
|-------|------|------------------|---------------------|
| Patrocinador do projeto | IFRN — Campus Central (via docentes orientadores) | Avaliação acadêmica, validação metodológica, direcionamento e apoio institucional | Aprovar |
| Gestor de projeto / Líder de Infraestrutura e Operações | Flavio Matias | Coordenação geral, planejamento, infraestrutura, ambiente de desenvolvimento e operações (DevOps, deploy, configuração de ambiente) | Gerenciar |
| Scrum Master | Ícaro Gabriel | Facilitação das cerimônias ágeis, remoção de impedimentos, acompanhamento das iterações e da saúde do time | Gerenciar |
| Product Designer / Product Owner Técnico | Gustavo Maia | Design da experiência do usuário, definição visual do produto, refinamento e priorização do backlog junto à equipe | Consultar / Decidir |
| Líder de Frontend | Asaph Arruda | Desenvolvimento e manutenção da camada frontend (Angular), integração com o backend, qualidade da interface | Executar |
| Líder de Backend | Robson Alencastro | Desenvolvimento e manutenção da camada backend (Spring Boot), modelagem de dados, APIs e integrações | Executar |
| Principais partes interessadas | Médicos, líderes de escala, RH e instituições de saúde participantes das entrevistas e da validação | Fornecer feedback, validar fluxos e regras de negócio | Consultar |

---

## 8. Abordagem de Entrega

O projeto adota uma abordagem **ágil**, organizada em iterações semanais conforme planejamento das iterações registrado na documentação do projeto. A escolha se justifica pelo fato de o produto evoluir junto com a validação dos usuários: requisitos amadurecem a cada entrevista, cada homologação e cada entrega bimestral acadêmica, exigindo um modelo que absorva mudanças sem travar o cronograma. A equipe é pequena, presencial e remota (Discord + IFRN), o que favorece ciclos curtos, feedback constante e revisão cruzada.

| Fator | Avaliação | Implicação da abordagem |
|-------|-----------|-------------------------|
| Estabilidade dos Requisitos | Em evolução | Backlog priorizado por iteração; refinamento contínuo com base nas validações e nas entrevistas com profissionais da saúde |
| Disponibilidade das partes interessadas | Médio | Validações pontuais com médicos e líderes de escala; reuniões frequentes com docentes orientadores; reforça necessidade de registro claro das decisões |
| Experiência em equipe | Misto | Time em formação acadêmica com domínios diferentes de stack; demanda revisão cruzada de código e pareamento em pontos críticos |
| Nível de risco | Médio | Riscos técnicos de stack nova e riscos de prazo competindo com outras disciplinas (ver Seção 10) |
| Frequência de entrega | Incremental | Entregas semanais por iteração, com marcos consolidados por bimestre e por etapa das disciplinas |

---

## 9. Pressupostos e Restrições

### Pressupostos:

- Que a infraestrutura de nuvem (AWS) e as ferramentas de código aberto (Spring Boot, Angular, Docker) permaneceriam estáveis e disponíveis sem custo direto durante todo o desenvolvimento acadêmico.
- Que o corpo docente orientador do IFRN manteria disponibilidade para validação metodológica e direcionamento ao longo das etapas do PDS Corporativo.
- Que os profissionais de saúde entrevistados na fase inicial estariam disponíveis para participar das sessões de validação empírica do MVP.
- Que a equipe de 5 integrantes se manteria estável (sem saída de membros) durante o ciclo do projeto.

### Restrições:

- Cronograma atrelado às datas de entrega das disciplinas de PDS Web, PDS Distribuído e PDS Corporativo — não sujeito a alteração unilateral pela equipe.
- Ausência de orçamento formal: projeto acadêmico, sem verba institucional dedicada (ver Seção 11).
- Escopo tecnológico restrito a soluções gratuitas/open source, sem contratação de serviços pagos de terceiros.
- Equipe distribuída entre trabalho presencial (IFRN) e remoto (Discord), sem infraestrutura física dedicada além dos laboratórios do campus.

---

## 10. Riscos de alto nível

A análise detalhada está no Registro de Riscos do projeto. Os principais riscos consolidados aqui são:

| Risco | Probabilidade | Impacto | Estratégia de Resposta | Proprietário |
|-------|---------------|---------|------------------------|--------------|
| **Complexidade da área da saúde** — falhas no sistema podem impactar decisões operacionais críticas e a aceitação do produto | Média | Alto | Mitigar — entrevistas com profissionais, validação de requisitos com especialistas, revisão dos fluxos críticos antes de cada entrega | Gestor de Projeto |
| **Sobrecarga por múltiplas disciplinas simultâneas** — acúmulo de demandas acadêmicas concorrendo com as do Pulso | Alta | Médio | Mitigar — planejamento semanal integrado, priorização de atividades críticas, replanejamento do cronograma quando necessário | Scrum Master |
| **Adaptação e estabilidade em novas tecnologias (Spring e Angular)** — risco de dificuldades técnicas, problemas de integração e configuração de ambiente | Média | Médio | Mitigar — estudo direcionado, ambiente padronizado via Docker, revisão periódica de dependências, consultas a docentes especialistas | Líder de Infraestrutura |
| **Saída inesperada de membro da equipe** — perda de um integrante compromete entregas atribuídas | Baixa | Alto | Mitigar — documentação das atividades, colaboração cruzada, compartilhamento de responsabilidades; contingência: redistribuir tarefas e postergar funcionalidades menos críticas | Gestor de Projeto |
| **Entregas com prazos reduzidos** — cronogramas curtos geram sobrecarga e queda de qualidade | Média | Médio | Mitigar — divisão em etapas menores, planejamento antecipado; contingência: negociar prazos e redefinir prioridades com docentes | Scrum Master |
| **Comunicação ineficaz na equipe** — divergências de entendimento causam retrabalho | Média | Médio | Mitigar — reuniões semanais, atas compartilhadas, definição clara de responsabilidades; sessões de alinhamento emergenciais quando necessário | Scrum Master |
| **Falta de aprovação de stakeholders** — decisões sem validação formal geram retrabalho | Baixa | Médio | Mitigar — envolver stakeholders nas decisões relevantes, registrar reuniões e justificativas | Gestor de Projeto |

---

## 11. Resumo do Orçamento

| Categoria de custo | Valor estimado | Notas |
|--------------------|----------------|-------|
| Trabalho (Interno) | R$ 0,00 | Projeto acadêmico, sem remuneração |
| Mão de obra (externa/contratada) | R$ 0,00 | Não há previsão de contratação externa |
| Materiais e equipamentos | R$ 0,00 | Equipamentos próprios dos integrantes / infraestrutura do IFRN |
| Software e Licenças | R$ 0,00 | Stack planejada 100% open source (Spring Boot, Angular, Docker) |
| Reserva de Contingência | — | Não aplicável — sem orçamento formal previsto |
| **TOTAL** | **R$ 0,00** | Projeto sem orçamento financeiro dedicado; custos limitados a tempo da equipe acadêmica |

---

## 12. Considerações sobre sustentabilidade

O Pulso é planejado desde a concepção para se apoiar em tecnologias de código aberto (Spring Boot, Angular, Docker) e arquitetura modular, reduzindo barreiras de custo e facilitando manutenção e expansão por futuras equipes, sem depender de licenciamento proprietário.

No aspecto social, o projeto busca gerar impacto na rotina de instituições de saúde por meio de uma gestão de plantões mais organizada, com potencial de reduzir falhas de comunicação e melhorar a distribuição de profissionais — fatores relevantes em ambientes onde tempo e responsabilidade são críticos.

Do ponto de vista de governança de dados, o Pulso prevê o tratamento de informações de vínculo profissional-instituição, horários e escalas de forma rastreável e segura, sem lidar com dados clínicos de pacientes (fora de escopo, conforme Seção 5), o que reduz a superfície de risco relacionada a dados sensíveis de saúde.

---

## 13. Aprovações

| Papel | Nome | Assinatura | Data |
|-------|------|------------|------|
| Patrocinador do projeto | IFRN — Campus Central | *(pendente)* | 18/05/2026 |
| Gestor de projeto | Flavio Matias | *aprovado* | 18/05/2026 |
| Principais partes interessadas | Médicos, líderes de escala e RH participantes das entrevistas | *(pendente)* | 18/05/2026 |
