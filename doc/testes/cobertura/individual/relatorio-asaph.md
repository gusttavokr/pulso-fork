# Relatório de Testes Manuais - Asaph

## Histórico de Revisões
| **Data**   | **Versão** | **Descrição**   | **Autor** |
| ---------- | ---------- | --------------- | --------- |
| 07/06/2026 | 1.0        | Primeira versão | Asaph |

## RF02 - Interesse em Plantão (CDU-006)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1 | Detalhe do Plantão | Consultar oportunidade | Médico visualiza os dados de um plantão aberto antes de demonstrar interesse | idPlantao: 1 <br> usuario: profissional <br> plantão aberto: true | Exibir dados do plantão, instituição, líder, especialidade, data, horário, pagamento e profissionais alocados | Dados do plantão exibidos corretamente | Passou | Valida o início do fluxo do CDU-006 |
| 2 | Detalhe do Plantão | Demonstrar interesse | Médico aciona "Pegar plantão" em plantão aberto | idPlantao: 1 <br> idProfissional: profissional logado | Alocação criada e plantão atualizado com o profissional relacionado | Plantão atualizado e modal de sucesso exibido | Passou | Retorno validando RN58, RN59, RN60 e RN61 |
| 3 | Detalhe do Plantão | Demonstrar interesse | Médico tenta pegar plantão de especialidade incompatível | idPlantao: 2 <br> profissional.especialidades não contém especialidade do plantão | Exibir mensagem de erro e não criar alocação | Modal/feedback de erro exibido e alocação não criada | Passou | Valida regra de compatibilidade de especialidade |
| 4 | Detalhe do Plantão | Demonstrar interesse | Médico já relacionado ao plantão acessa a tela | idPlantao: 1 <br> profissional já alocado ou relacionado | Botão "Pegar plantão" não deve ser exibido | Botão "Pegar plantão" não foi exibido | Passou | Evita interesse duplicado |
| 5 | Detalhe do Plantão | Pegar plantão de repasse | Médico aceita um plantão originado de repasse | idPlantao: 3 <br> tipoTroca: SIMPLES <br> troca ativa: true | Trocar profissional da alocação original e confirmar a troca | Plantão atualizado com novo profissional e status da troca confirmado | Passou | Fluxo alternativo do CDU-006 |
| 6 | Detalhe do Plantão | Tratamento de erro | Médico tenta pegar plantão inexistente | idPlantao: 9999 | Exibir erro informando que o plantão não foi encontrado | Feedback de erro exibido | Passou | Valida fluxo de exceção de plantão não encontrado |

## RF13 - Envio de Oportunidades

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1 | Detalhe da Escala | Publicar escala | Líder publica uma escala com plantões cadastrados | idEscala: 1 <br> publicada: true | Escala alterada para PUBLICADA e oportunidades disponibilizadas para médicos elegíveis | Escala publicada e modal de sucesso exibido | Passou | Ação realizada pelo endpoint de publicação da escala |
| 2 | Detalhe da Escala | Enviar oportunidades | Publicação de escala com plantões abertos | statusEscala: RASCUNHO <br> plantoes: lista(PlantaoEscalaSimple) | Plantões ficam disponíveis para consulta no feed de plantões abertos | Plantões publicados ficaram disponíveis para profissionais | Passou | Relaciona envio de oportunidade com disponibilização no feed |
| 3 | Detalhe da Escala | Bloqueio por perfil | Profissional acessa detalhe de escala publicada | usuario: profissional | Botão "Publicar" não deve ser exibido | Botão "Publicar" não foi exibido | Passou | Apenas líder de escala pode enviar/publicar oportunidades |
| 4 | Detalhe da Escala | Erro ao publicar | Líder tenta publicar escala com falha de backend | idEscala: inválido ou indisponível | Exibir modal de erro e manter a escala sem alteração | Modal "Erro ao publicar escala" exibido | Passou | Feedback orienta tentar novamente ou contatar suporte |
| 5 | Plantões Abertos | Receber oportunidade | Médico PJ consulta oportunidades após publicação | abertos: true <br> profissional: true | lista(PlantaoSimple) com plantões publicados compatíveis | lista(PlantaoSimple) exibida no feed | Passou | Complementa o fluxo RF13 -> RF01/RF02 |

## RF15 - Visualização de Cobertura (CDU-001)

| ID | Tela/Módulo | Funcionalidade | Cenário de Teste | Entrada/Dados Utilizados | Resultado Esperado | Resultado Obtido | Status | Observações |
|----|-------------|----------------|------------------|--------------------------|-------------------|------------------|--------|-------------|
| 1 | Detalhe da Escala | Visualizar escala | Líder acessa a tela de uma escala existente | idEscala: 1 | Exibir dados da escala, instituição, líder, setor, especialidade, status e plantões | Dados da escala exibidos corretamente | Passou | Valida detalhamento da escala no CDU-001 |
| 2 | Detalhe da Escala | Visualizar cobertura semanal | Calendário renderiza plantões por dia da semana | mesReferencia: "2026-09-01" <br> plantoes: lista(PlantaoEscalaSimple) | Plantões agrupados no dia correspondente à horaEntrada | Plantões exibidos nos dias corretos do calendário | Passou | Cobertura visual por semana |
| 3 | Detalhe da Escala | Navegar semanas | Líder usa setas para trocar a semana do calendário | evento: previousWeek/nextWeek | Calendário deve navegar entre semanas do mês sem perder os plantões | Navegação semanal funcionando | Passou | Limites impedem avançar antes/depois das semanas do mês |
| 4 | Detalhe da Escala | Ver ocupação de plantão | Líder seleciona um plantão no calendário | idPlantao: 1 | Popover com resumo do plantão e opção de ver detalhes | Popover aberto com dados do plantão | Passou | Permite identificar rapidamente cobertura do plantão |
| 5 | Detalhe do Plantão | Acompanhar ocupação | Líder visualiza quantidade de profissionais alocados no plantão | quantidadeProfissionais: 2 <br> profissionaisConfirmados/Combinados/TrocaDestino | Exibir contador de profissionais alocados no formato atual/total | Contador exibido, exemplo: 1/2 | Passou | Ajuda a identificar déficit de médicos |
| 6 | Detalhe da Escala | Identificar escala sem plantões | Escala sem plantões cadastrados é aberta | idEscala: 4 <br> plantoes: [] | Calendário exibido sem cards de plantão | Calendário exibido sem cards | Passou | Estado válido para escala ainda em montagem |
| 7 | Detalhe da Escala | Criar plantão para cobrir déficit | Líder aciona "Criar plantão" a partir da escala | idEscala: 1 <br> usuario: líder | Redirecionar para criação de plantão com escala vinculada no contexto | Tela de criação aberta com contexto da escala | Passou | Apoia correção de déficit identificado na cobertura |
