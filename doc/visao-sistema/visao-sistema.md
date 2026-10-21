# Visão Geral do Sistema

Este documento descreve o funcionamento geral do sistema, suas entidades e a relação direta entre os Casos de Uso (CDUs) e os componentes envolvidos. Também apresenta a justificativa de existência de cada funcionalidade, explicando seu papel dentro do fluxo operacional.

---

## Entidade: RH (Recursos Humanos)

### Funções Principais

#### CDU006 – Gerenciar Vínculos [🗂️](../cdu/cdu006_Gerenciar_vínculos/detalhamento.md)
- **Descrição:** Permite ao RH criar, atualizar, excluir e listar vínculos entre a instituição e os profissionais (médicos e líderes de escala).
- **Entidades Envolvidas:** `Instituição`, `Médico`, `Líder de Escala`, `Vínculo`.
- **Justificativa:** Essencial para determinar quais profissionais estão associados à instituição, garantindo que apenas pessoas autorizadas possam atuar nas escalas e setores correspondentes.

#### CDU010 – Cadastrar Líderes de Escala [🗂️](../cdu/cdu010_Cadastrar_líderes_de_escala/detalhamento.md)
- **Descrição:** Permite o cadastro de líderes de escala, que podem já possuir perfil de médico ou serem criados como novos usuários.
- **Entidades Envolvidas:** `Usuário`, `Pessoa`, `Líder de Escala`, `Instituição`.
- **Justificativa:** Garante que cada instituição tenha líderes habilitados a gerenciar suas escalas, mesmo que estes não possuam vínculo prévio como médico.

#### CDU011 – Gerenciar Setores [🗂️](../cdu/cdu011_Gerenciar_setores/detalhamento.md)
- **Descrição:** Permite ao RH criar, atualizar, excluir e listar setores da instituição, cada um associado a uma especialidade específica.
- **Entidades Envolvidas:** `Instituição`, `Setor`, `Especialidade`.
- **Justificativa:** Organiza a estrutura interna da instituição, separando os setores conforme as especialidades oferecidas, o que reflete diretamente na criação de escalas por área.

---

## Entidade: Líder de Escala

### Funções Principais

#### CDU001 – Gerenciar Escala [🗂️](../cdu/cdu001_Gerenciar_escala/detalhamento.md)
- **Descrição:** Permite criar, atualizar, excluir, detalhar e listar escalas de um setor. Cada escala é vinculada a um mês e a um setor específico.
- **Entidades Envolvidas:** `Escala`, `Setor`, `Plantão`, `Instituição`, `Líder de Escala`.
- **Justificativa:** Centraliza o planejamento mensal dos plantões, automatizando a criação de plantões com base nos horários definidos pela instituição.

#### CDU002 – Gerenciar Plantão [🗂️](../cdu/cdu002_Gerenciar_plantão/detalhamento.md)
- **Descrição:** Permite ao líder criar (quando necessário), atualizar, excluir, detalhar e listar plantões. Normalmente, os plantões são gerados automaticamente pela criação da escala.
- **Entidades Envolvidas:** `Plantão`, `Escala`, `Alocação`.
- **Justificativa:** Mantém o controle detalhado dos turnos de trabalho, assegurando a coerência entre os horários e os profissionais alocados.

#### CDU003 – Publicar Escala [🗂️](../cdu/cdu003_Publicar_escala/detalhamento.md)
- **Descrição:** Transforma uma escala em rascunho para o status de publicada, tornando seus plantões visíveis aos médicos.
- **Entidades Envolvidas:** `Escala`, `Plantão`, `Médico`.
- **Justificativa:** Define o momento em que a escala passa a ser pública, permitindo que os médicos consultem e se candidatem aos plantões disponíveis.

---

## Entidade: Médico (Profissional)

### Funções Principais

#### CDU005 – Consultar Escalas [🗂️](../cdu/cdu005_Consultar_escalas/detalhamento.md)
- **Descrição:** Permite ao médico visualizar detalhes das escalas dos plantões disponíveis ou em que participa.
- **Entidades Envolvidas:** `Escala`, `Plantão`, `Médico`.
- **Justificativa:** Facilita o acompanhamento das escalas e a organização pessoal do médico.

#### CDU007 – Consultar Plantões [🗂️](../cdu/cdu007_Consultar_plantões/detalhamento.md)
- **Descrição:** Permite detalhar informações específicas de um plantão, incluindo repasses e trocas associadas.
- **Entidades Envolvidas:** `Plantão`, `Alocação`, `Troca`.
- **Justificativa:** Garante transparência nas informações sobre os plantões em que o médico está envolvido, inclusive em transações de troca.

#### CDU009 – Consultar Plantões Abertos [🗂️](../cdu/cdu009_Consultar_plantões_abertos/detalhamento.md)
- **Descrição:** Exibe plantões disponíveis para alocação, tanto provenientes de escalas publicadas quanto de repasses.
- **Entidades Envolvidas:** `Plantão`, `Escala`, `Troca`.
- **Justificativa:** Permite que médicos encontrem oportunidades disponíveis, otimizando a ocupação dos plantões.

#### CDU008 – Pegar Plantão [🗂️](../cdu/cdu008_Pegar_plantão/detalhamento.md)
- **Descrição:** Ação de o médico se alocar em um plantão aberto ou assumir o plantão de outro profissional via troca.
- **Entidades Envolvidas:** `Alocação`, `Plantão`, `Médico`, `Troca`.
- **Justificativa:** Viabiliza a autoalocação e as trocas, garantindo flexibilidade e cobertura contínua dos turnos.

#### CDU012 – Gerenciar Trocas e Repasses de Plantão [🗂️](../cdu/cdu012_Gerenciar_trocas_e_repasses_de_plantão/detalhamento.md)
- **Descrição:** Permite criar, atualizar, detalhar e excluir trocas de plantões entre médicos, diferenciando trocas diretas e repasses.
- **Entidades Envolvidas:** `Troca`, `Plantão`, `Médico`, `Alocação`.
- **Justificativa:** Favorece a autonomia entre os profissionais, permitindo reorganizações e compensações sem necessidade de intervenção administrativa.

#### CDU013 – Responder Solicitação de Troca [🗂️](../cdu/cdu013_Responder_solicitação_de_troca/detalhamento.md)
- **Descrição:** Permite ao médico receptor aceitar ou recusar uma troca de plantão proposta.
- **Entidades Envolvidas:** `Troca`, `Plantão`, `Alocação`, `Médico`.
- **Justificativa:** Estabelece controle sobre o aceite de trocas e mantém a rastreabilidade das alterações de escala.

---

## Entidade: Todos os Usuários

### Funções Principais

#### CDU004 – Consultar Notificações e Alertas [🗂️](../cdu/cdu004_Consultar_notificações_e_alertas/detalhamento.md)
- **Descrição:** Permite listar e detalhar notificações sobre eventos relevantes (criação de escalas, trocas, atualizações, exclusões, etc.).
- **Entidades Envolvidas:** `Notificação`, `Usuário`.
- **Justificativa:** Mantém todos os usuários informados em tempo real sobre atividades do sistema, evitando desinformação e conflitos operacionais.

---

## Relação entre CDUs e Entidades

| CDU    | Caso de Uso                      | Entidades Relacionadas                                          |
|--------|----------------------------------|-----------------------------------------------------------------|
| CDU006 | Gerenciar Vínculos               | Instituição, Médico, Líder de Escala, Vínculo                   |
| CDU010 | Cadastrar Líderes de Escala      | Usuário, Pessoa, Líder de Escala, Instituição                   |
| CDU011 | Gerenciar Setores                | Instituição, Setor, Especialidade                               |
| CDU001 | Gerenciar Escala                 | Escala, Setor, Plantão, Líder de Escala, Alocação, Profissional |
| CDU002 | Gerenciar Plantão                | Plantão, Escala, Alocação, Profisssional                        |
| CDU003 | Publicar Escala                  | Escala                                                          |
| CDU005 | Consultar Escalas                | Escala, Setor, Plantão, Líder de Escala, Alocação, Profissional |
| CDU007 | Consultar Plantões               | Plantão, Alocação, Troca, Profissional, Líder de Escala         |
| CDU009 | Consultar Plantões Abertos       | Plantão, Escala, Troca, Profissional, Líder de Escala, Alocação |
| CDU008 | Pegar Plantão                    | Alocação, Plantão, Profissional, Troca                          |
| CDU012 | Gerenciar Trocas e Repasses      | Troca, Plantão, Profissional, Alocação                          |
| CDU013 | Responder Solicitação de Troca   | Troca, Plantão, Alocação, Profissional                          |
| CDU004 | Consultar Notificações e Alertas | Notificação, Usuário                                            |

---

## Considerações Finais

O sistema foi projetado para abranger toda a dinâmica de gestão de escalas e plantões médicos, proporcionando:
- **Eficiência administrativa** através da automação de processos repetitivos.
- **Autonomia dos profissionais**, permitindo autogestão de plantões e trocas.
- **Transparência nas relações** entre instituições, líderes e médicos.
- **Integração informacional** com notificações e status em tempo real.
