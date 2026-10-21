# Relatório de Teste de Carga - API Pulso com Grafana K6

## Resumo

Foi executado um teste de carga utilizando **1000 usuários virtuais (VUs)**, totalizando **500 iterações concluídas** e **575 interrompidas**. O objetivo foi avaliar o comportamento da API sob alta concorrência.

### Resultado Geral

O sistema apresentou degradação significativa de desempenho durante o teste, com alta taxa de falhas e tempos de resposta muito acima dos limites estabelecidos.

## Critérios de Aceitação (Thresholds)

| Métrica                 | Limite   | Resultado | Status |
| ----------------------- | -------- | --------- | ------ |
| Tempo médio de resposta | < 500 ms | 52,88 s   | ❌      |
| Percentil 95            | < 1 s    | 59,99 s   | ❌      |
| Percentil 99            | < 2 s    | 59,99 s   | ❌      |
| Taxa de falha           | < 1%     | 87,82%    | ❌      |

## Resultados dos Cenários Testados

| Verificação            | Sucesso | Falha | Taxa de Sucesso |
| ---------------------- | ------: | ----: | --------------: |
| Cadastro de usuário    |     102 |   973 |              9% |
| Login do líder         |      58 |   442 |             11% |
| Token recebido         |      61 |   439 |             12% |
| Criação de escala      |      36 |    25 |             59% |
| Detalhamento de escala |      12 |    24 |             33% |
| Listagem de escalas    |       0 |    36 |              0% |
| Retorno de dados       |       0 |    36 |              0% |

## Métricas HTTP

| Métrica                 | Resultado |
| ----------------------- | --------- |
| Requisições realizadas  | 1.708     |
| Taxa de falhas          | 87,82%    |
| Tempo médio de resposta | 52,88 s   |
| Tempo mínimo            | 186,62 ms |
| Mediana                 | 59,99 s   |
| Tempo máximo            | 60 s      |
| P90                     | 59,99 s   |
| P95                     | 59,99 s   |

### Requisições com resposta esperada

| Métrica      | Resultado |
| ------------ | --------- |
| Tempo médio  | 1,58 s    |
| Tempo mínimo | 186,62 ms |
| Mediana      | 1,26 s    |
| Tempo máximo | 4,05 s    |
| P90          | 3,68 s    |
| P95          | 3,76 s    |

## Métricas de Execução

| Métrica                   | Resultado  |
| ------------------------- | ---------- |
| Iterações concluídas      | 500        |
| Duração média da iteração | 1 min 51 s |
| Menor duração             | 1 min 1 s  |
| Mediana                   | 2 min      |
| Maior duração             | 2 min 5 s  |
| VUs máximos configurados  | 1000       |

## Métricas de Rede

| Métrica         | Resultado |
| --------------- | --------- |
| Dados recebidos | 480 kB    |
| Dados enviados  | 899 kB    |

## Conclusão

O teste demonstrou que a aplicação não suportou a carga configurada de 1000 usuários virtuais simultâneos. Todos os thresholds definidos foram violados, especialmente os relacionados ao tempo de resposta e à taxa de falhas.

Os resultados indicam a necessidade de investigação dos gargalos de desempenho, incluindo análise de banco de dados, processamento da aplicação, utilização de recursos do servidor e possíveis limitações de infraestrutura antes de uma nova execução do teste.
