# Documento de Cobertura de Testes — Requisitos e Código

## Requisitos

| ID | Requisito | CDU Relacionado | Status |
|----|-----------|-----------------|--------|
| RF01 | Feed de Plantões Disponíveis | CDU-007 | ✔️ |
| RF02 | Interesse em Plantão | CDU-006 | ✔️ |
| RF03 | Lista Pessoal de Plantões | CDU-002 | ✔️ |
| RF04 | Repassar Plantão | CDU-009 | ❌ |
| RF05 | Troca de Plantão entre Médicos | CDU-009, CDU-010 | ❌ |
| RF06 | Confirmação de Presença | --- | ❌ |
| RF07 | Instituições Favoritas e Bloqueadas | --- | ❌ |
| RF08 | Calendário Pessoal | CDU-012 | ✔️ |
| RF09 | Relatórios e Estatísticas | --- | ❌ |
| RF10 | Acesso a Escalas | CDU-001 | ✔️ |
| RF11 | Criação e Edição de Escalas | CDU-001 | ✔️ |
| RF12 | Templates de Escalas | CDU-001 | ❌ |
| RF13 | Envio de Oportunidades | CDU-003, CDU-011 | ✔️ |
| RF14 | Lista de Médicos Parceiros | --- | ✔️ |
| RF15 | Visualização de Cobertura | CDU-001 | ✔️ |
| RF16 | Vinculação de Profissionais | CDU-005 | ❌ |
| RF17 | Controle de Permissões | CDU-005 | ✔️ |
| RF18 | Notificações e Alertas | CDU-004 | ❌ |
| RF19 | Perfis de Usuário | --- | ✔️ |

---

# Cobertura de Requisitos Baseada nos CDUs

## Quantidade Total de Requisitos (RfT)

**RfT = 19**

Onde:

- **RfT**: Requisitos funcionais testáveis identificados.
- **Tp**: Requisitos contemplados por testes planejados.
- **Ti**: Requisitos contemplados por testes implementados (automatizados).
- **Tx**: Requisitos contemplados por testes executados.
- **Ts**: Requisitos contemplados por testes executados com sucesso.

## Métricas de Cobertura

| Métrica | Fórmula | Resultado |
|----------|----------|------------|
| Planejados | Tp / RfT | 47% |
| Implementados | Ti / RfT | 33% |
| Executados | Tx / RfT | 27% |
| Bem-sucedidos | Ts / RfT | 27% |

## Cálculo das Métricas de Cobertura

As métricas foram calculadas considerando os **19 Requisitos Funcionais Testáveis (RfT)** identificados no sistema.

- **Testes Planejados (Tp = 9):** nove requisitos possuíam casos de teste definidos, resultando em uma cobertura de **47%**.
- **Testes Implementados (Ti = 33):** sete requisitos foram testados ponta a ponta, resultando em uma cobertura de **33%**.
- **Testes Executados (Tx ≈ 27%):** embora diversos casos de teste tenham sido executados, a cobertura foi calculada considerando os fluxos efetivamente validados dos casos de uso. Apenas uma parcela dos fluxos previstos foi exercitada durante a execução dos testes.
- **Testes Bem-Sucedidos (Ts ≈ 27%):** a maior parte dos fluxos executados foi aprovada, porém vários fluxos previstos para os requisitos não foram testados, impedindo que os respectivos requisitos fossem considerados totalmente validados.

Os resultados demonstram que apenas uma parcela limitada das funcionalidades do sistema foi efetivamente avaliada. Apesar da execução de diversos casos de teste, a cobertura geral permaneceu reduzida devido à quantidade de fluxos e requisitos que não foram contemplados durante a validação.

---

# Cobertura de Código

| Métrica | Resultado |
|----------|------------|
| Cobertura de Instruções | 44% |
| Cobertura de Ramificações | 53% |
| Instruções Cobertas | 5.913 de 13.149 |
| Ramificações Cobertas | 512 de 955 |

---

# Observações

- Os cálculos foram realizados com base nos Casos de Uso (CDUs).
- A cobertura de requisitos indica o percentual de funcionalidades exercitadas pelos testes.
- A cobertura de código foi obtida por meio da ferramenta JaCoCo.
- Foram cobertas 44% das instruções e 53% das ramificações do sistema.
- Testes bem-sucedidos são aqueles executados sem falhas ou defeitos.
