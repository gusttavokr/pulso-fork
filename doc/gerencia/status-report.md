# Status Report - Pulso

| Campo | Valor |
|-------|-------|
| Nome do projeto | Pulso |
| Gerente de projetos | Ícaro Gabriel |
| Patrocinador | --- |
| Data do relatório | 01/06/2026 |
| Período de reporte | 01/06 - 20/07/2026 |
| Status geral | Amarelo |

## 1 Resumo executivo

O projeto Pulso segue em desenvolvimento e já disponibiliza funcionalidades que apoiam a gestão e o preenchimento de escalas médicas. Atualmente, gestores podem criar escalas e disponibilizar plantões, enquanto médicos conseguem visualizar oportunidades disponíveis e assumir plantões diretamente pela plataforma.

Essas entregas representam um avanço importante para a centralização e organização do processo de gestão de plantões, reduzindo a necessidade de controles manuais e facilitando a comunicação entre gestores e profissionais de saúde.

Para as próximas etapas, estão previstas funcionalidades que ampliarão a autonomia dos usuários e a eficiência operacional do sistema, incluindo gerenciamento de perfil, notificações, calendário pessoal e recursos voltados à troca de plantões entre profissionais.

## 2 Indicadores-chave do desempenho (KPIs)

| KPI | Meta | Atual | Status | Tendência |
|-----|------|-------|--------|-----------|
| Desempenho do cronograma (SPI) | 100% | 53% | 🟡 | ↑ |
| Desempenho de custos (CPI) | --- | --- | --- | --- |
| Conclusão do escopo | 100% | 36% | 🟡 | ↑ |
| Qualidade | Alta | Média | 🟡 | → |
| Satisfação das partes interessadas | --- | --- | --- | --- |
| Exposição a riscos | Baixa | Alta | 🔴 | → |

## 3 Atividades concluídas no período

| Atividade                         | Entrega Esperada                                 | Data Prevista | Comentários |
| --------------------------------- | ------------------------------------------------ | ------------- | ----------- |
| Implementação de CD do Backend    | Pipeline de deploy contínuo do backend           | 20/06/2026    | ---         |
| Implementação de CD do Frontend   | Pipeline de deploy contínuo do frontend          | 25/06/2026    | ---         |
| Avaliação Empírica                | Relatório de avaliação empírica                  | 29/06/2026    | ---         |
| Testes de API                     | Relatório de testes de integração e API          | 22/06/2026    | ---         |
| Testes Não Funcionais             | Relatório de desempenho, segurança e usabilidade | 25/06/2026    | ---         |
| Cobertura de Testes               | Relatório de cobertura de testes funcionais      | 07/06/2026    | ---         |
| Plano de Gestão de Riscos         | Documento de gestão de riscos                    | 11/06/2026    | ---         |
| Visibilidade de Progresso e Valor | Documento de acompanhamento do projeto           | 11/06/2026    | ---         |
| Encerramento e Lições Aprendidas  | Documento final do projeto                       | 13/06/2026    | ---         |

## 4 Atividades em Andamento

| Atividade                                      | % Concluído | Conclusão Prevista | Problemas / Impedimentos                                                                           |
| ---------------------------------------------- | ----------- | ------------------ | -------------------------------------------------------------------------------------------------- |
| Implementação de CD do Backend                 | 100%        | 20/06/2026         | Dependência dos conteúdos e práticas abordados na disciplina de Sistemas Corporativos.             |
| Implementação de CD do Frontend                | 100%        | 25/06/2026         | Dependência dos conteúdos e práticas abordados na disciplina de Sistemas Corporativos.             |
| Avaliação Empírica                             | 100%        | 29/06/2026         | Dificuldade de acesso a usuários representativos para realização de uma avaliação mais abrangente. |
| Testes de API                                  | 100%        | 22/06/2026         | Dependência dos conteúdos e técnicas abordados na disciplina de Testes de Software.                |
| Testes Não Funcionais                          | 100%        | 25/06/2026         | Dependência dos conteúdos e técnicas abordados na disciplina de Testes de Software.                |
| Cobertura de Testes                            | 100%        | 07/06/2026         | Falta de testes escritos focados na interface.                                                     |
| Documento de Plano de Gestão de Riscos         | 100%        | 11/06/2026         | Atividade ainda não iniciada devido à priorização de outras entregas do projeto.                   |
| Documento de Visibilidade de Progresso e Valor | 100%        | 11/06/2026         | Documento em fase final de elaboração.                                                             |
| Documento de Encerramento e Lições Aprendidas  | 100%        | 13/06/2026         | Previsto para execução nas etapas finais do projeto.                                               |

## 5 Atividades para o Próximo Período

| Atividade                                                                  | Responsável               | Data-Alvo | Dependências                                                                |
| -------------------------------------------------------------------------- | ------------------------- | --------- | --------------------------------------------------------------------------- |
| Implementação do módulo de troca de plantões                               | Equipe de Desenvolvimento | ---       | Conclusão e estabilização das funcionalidades atuais de escalas e plantões. |
| Implementação da abertura de solicitações de troca de plantão              | Equipe de Desenvolvimento | ---       | Definição das regras de negócio para trocas.                                |
| Implementação da indicação de substituto para troca de plantão             | Equipe de Desenvolvimento | ---       | Modelagem do fluxo de aprovação e substituição de profissionais.            |
| Implementação da aceitação voluntária de plantões em troca                 | Equipe de Desenvolvimento | ---       | Conclusão do módulo de trocas.                                              |
| Implementação do envio direto de solicitações de troca entre profissionais | Equipe de Desenvolvimento | ---       | Definição dos fluxos de notificação e aprovação.                            |
| Implementação da aprovação e rejeição de solicitações de troca             | Equipe de Desenvolvimento | ---       | Conclusão das funcionalidades de solicitação de troca.                      |
| Implementação da visualização de perfil do usuário                         | Equipe de Desenvolvimento | ---       | Disponibilização dos dados cadastrais do profissional.                      |
| Implementação da edição de perfil do usuário                               | Equipe de Desenvolvimento | ---       | Definição das informações editáveis e validações necessárias.               |

## 6 Riscos e Problemas

| ID | Descrição                                                                                                                               | Tipo     | Severidade | Responsável               | Mitigação / Resolução                                                                          |
| -- | --------------------------------------------------------------------------------------------------------------------------------------- | -------- | ---------- | ------------------------- | ---------------------------------------------------------------------------------------------- |
| R1 | Dependência dos conteúdos das disciplinas de Sistemas Corporativos e Testes de Software para execução de atividades técnicas previstas. | Risco    | M          | Equipe de Desenvolvimento | Antecipar estudos dos conteúdos necessários e buscar materiais complementares quando possível. |
| R2 | Dificuldade de obtenção de participantes para a realização da avaliação empírica do sistema.                                            | Risco    | M          | Equipe de Desenvolvimento | Buscar voluntários entre colegas, professores e profissionais da área da saúde.                |
| R3 | Atraso na elaboração da documentação obrigatória do projeto.                                                                            | Problema | M          | Equipe de Desenvolvimento | Definir responsáveis e acompanhar semanalmente o progresso das entregas documentais.           |
| R4 | Complexidade das regras de negócio relacionadas à troca de plantões, podendo gerar retrabalho ou atrasos na implementação.              | Risco    | H          | Equipe de Desenvolvimento | Detalhar previamente os fluxos e validar as regras antes da implementação.                     |
| R5 | Necessidade de melhorias na arquitetura da aplicação, validações de negócio e requisitos de segurança antes da implantação em produção. | Problema | H          | Equipe de Desenvolvimento | Realizar revisões técnicas, testes e correções antes da entrega final do sistema.              |
| R6 | Cronograma com prazos reduzidos, podendo causar sobrecarga da equipe e impactar a qualidade das entregas. | Risco | M | Equipe de Desenvolvimento | Dividir atividades em etapas menores, planejar antecipadamente e acompanhar a evolução das tarefas. |
| R7 | Sobrecarga decorrente da execução simultânea de múltiplas disciplinas acadêmicas, comprometendo o andamento do projeto. | Risco | H | Equipe de Desenvolvimento | Realizar planejamento semanal integrado e priorizar atividades críticas do projeto. |
| R8 | Complexidade das regras e requisitos da área da saúde, podendo gerar falhas que comprometam a confiabilidade do sistema. | Risco | H | Equipe de Desenvolvimento | Validar requisitos com profissionais da saúde, revisar fluxos críticos e realizar verificações frequentes com especialistas. |
| R9 | Problemas de comunicação entre os membros da equipe, resultando em desalinhamento, retrabalho e perda de produtividade. | Problema | M | Equipe de Desenvolvimento | Realizar reuniões periódicas, manter atas compartilhadas e definir claramente as responsabilidades de cada integrante. |

## 7 Valor Entregue

Durante o período reportado, foram disponibilizadas as funcionalidades centrais do sistema Pulso relacionadas ao gerenciamento de escalas e plantões. Atualmente, gestores podem criar escalas e disponibilizar plantões, enquanto profissionais podem visualizar oportunidades disponíveis e assumir plantões diretamente pela plataforma.

Essas funcionalidades permitem validar o fluxo principal de negócio do sistema, reduzindo processos manuais e servindo como base para a implementação das próximas funcionalidades previstas para o projeto.

Além disso, a equipe consolidou a arquitetura inicial da aplicação e estabeleceu a estrutura necessária para a evolução do produto ao longo do semestre.

## 8 Métricas de Sustentabilidade

| Dimensão                        | Situação Atual                                                                                                                                                                                       |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Sustentabilidade Social         | O sistema busca melhorar a organização das escalas médicas, contribuindo para uma distribuição mais transparente dos plantões e facilitando o acesso dos profissionais às oportunidades de trabalho. |
| Sustentabilidade Econômica      | A utilização de tecnologias gratuitas e de código aberto reduz custos de desenvolvimento e manutenção, tornando a solução mais acessível para futuras implantações.                                  |
| Sustentabilidade Ambiental      | A digitalização do processo de gestão de escalas reduz a necessidade de documentos impressos e controles manuais em papel.                                                                           |
| Sustentabilidade Organizacional | A centralização das informações em uma única plataforma contribui para maior controle, rastreabilidade e eficiência na gestão dos plantões.                                                          |
| Sustentabilidade Técnica        | A adoção de uma arquitetura modular favorece a manutenção e evolução contínua do sistema, aumentando sua vida útil e reduzindo a necessidade de retrabalho.                                          |

## 9 Informações Adicionais

* O projeto encontra-se em fase ativa de desenvolvimento, com as funcionalidades principais implementadas e em processo de validação.
* As próximas entregas concentram-se principalmente na implementação do módulo de trocas de plantão, funcionalidades de perfil do usuário e batida de ponto.
* Foram identificadas oportunidades de melhoria relacionadas à arquitetura da aplicação, validações de negócio e requisitos de segurança e interface, que serão tratadas antes da conclusão do projeto.
* A equipe acompanha continuamente os riscos identificados e realizará revisões periódicas do planejamento até o encerramento do semestre.
