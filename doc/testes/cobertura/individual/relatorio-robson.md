
# Relatório de Testes Manuais - Robson

## Histórico de Revisões
| **Data**   | **Versão** | **Descrição**   | **Autor** | 
| ---------- | ---------- | --------------- | --------- |
| 04/06/2026 | 1.0        | Primeira versão | Robson

## RF01 - Feed de Plantões Disponíveis/Abertos (CDU-002 e CDU-007)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1  | Plantões Abertos | Consultar Listagem | Listagem com plantões abertos | abertos: true <br> profissional: true | lista(PlantaoSimple, PlantaoSimple, ...) | lista(PlantaoSimple, PlantaoSimple, ...) | Passou | Retorno validando a RN62 |
| 2  | Plantões Abertos | Consultar Listagem | Listagem sem plantões disponíveis | abertos: true <br> profissional: true <br> (sem dados no backend) | "Nenhum plantão encontrado." | "Nenhum plantão encontrado." | Passou | Exibiu o empty state `lucide-frown` |
| 3  | Plantões Abertos | Filtrar Plantões | Filtrar por Data específica | abertos: true <br> data: "01/09/2026" | lista(PlantaoSimple) <br> onde data = "01/09/2026" | lista(PlantaoSimple) | Passou | - |
| 4  | Plantões Abertos | Filtrar Plantões | Filtrar por Especialidade | abertos: true <br> especialidade: "Clínica Geral" | lista(PlantaoSimple) <br> onde especialidade = "Clínica Geral" | lista(PlantaoSimple) | Passou | - |
| 5  | Plantões Abertos | Filtrar Plantões | Filtrar por Preço Mínimo e Máximo | abertos: true <br> precoMin: 1000 <br> precoMax: 2000 | lista(PlantaoSimple) <br> onde estimativaValor >= 1000 e <= 2000 | lista(PlantaoSimple) | Passou | - |
| 6  | Plantões Abertos | Filtrar Plantões | Filtrar por Horário | abertos: true <br> horariosInicio: ["07:00"] <br> horariosFim: ["19:00"] | lista(PlantaoSimple) <br> onde horario = 07:00 - 19:00 | lista(PlantaoSimple) | Passou | - |
| 7  | Plantões Abertos | Filtrar Plantões | Filtrar por Categoria (Novo Plantão) | abertos: true <br> ehNovoPlantao: true | lista(PlantaoSimple) <br> onde ehNovoPlantao = true | lista(PlantaoSimple) | Passou | - |
| 8  | Plantões Abertos | Limpar Filtros | Acionar a limpeza de filtros | abertos: true <br> evento: limparFiltros | lista(PlantaoSimple, PlantaoSimple, ...) recarregando busca inicial | lista(PlantaoSimple, PlantaoSimple, ...) | Passou | - |


## RF08 - Calendário Pessoal (CDU-012)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1 | Sidebar | Conectar Google Calendar | Realizar autenticação com Google Calendar | Método: GET/POST <br> Endpoint: /api/... <br> Payload: { ... } | Retorna com a URL/token/dados necessários pra realizar a autenticação do Google | Retorno com URL/token/dados necessários para autenticação Google | Passou |  |
| 2 | Sidebar | Conectar Google Calendar | Concluir autenticação Google | Código OAuth retornado pelo Google <br> Conta Google válida selecionada | Integração vinculada ao usuário autenticado | Integração vinculada ao usuário autenticado | Passou | Status alterado para conectado e os dados do calendário são mostrados no sidebar |
| 3 | Sidebar | Desconectar Google Calendar | Remover integração ativa | Método: DELETE/POST <br> Endpoint: /api/... | Integração removida e status atualizado | Integração removida e status atualizado | Não passou | Apesar de a ação ter sido realizada, o botão de conexão NÃO voltou a ser exibido, mas deveria, então o teste falhou |

