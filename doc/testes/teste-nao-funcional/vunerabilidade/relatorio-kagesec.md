# Relatório de Teste de Segurança - API Pulso com KageSec

## Resumo

Foi executado um teste de segurança automatizado utilizando **KageSec** contra as principais rotas da API Pulso em ambiente local (`localhost:8080`). O objetivo foi avaliar configurações de segurança, exposição de caminhos sensíveis, políticas HTTP e possíveis falhas de controle de acesso.

Foram analisadas **8 rotas**, com profundidade de crawl **1**, sem uso de navegador headless, utilizando token Bearer para autenticação.

### Resultado Geral

O sistema apresentou fragilidades recorrentes de configuração de segurança em todas as rotas avaliadas. Os principais achados foram configuração permissiva de CORS com credenciais, ausência de headers de segurança, descoberta de caminho sensível e possíveis casos de recurso autenticado acessível sem autenticação.

## Critérios de Aceitação (Thresholds)

| Métrica                         | Limite            | Resultado | Status |
| ------------------------------- | ----------------- | --------- | ------ |
| Achados críticos                | 0                 | 0         | ✅      |
| Achados de severidade alta      | 0                 | 10        | ❌      |
| Achados de severidade média     | 0                 | 24        | ❌      |
| Rotas com CORS permissivo       | 0                 | 8         | ❌      |
| Rotas com headers ausentes      | 0                 | 8         | ❌      |
| Possível bypass de autenticação | 0                 | 2         | ❌      |

## Resultados dos Cenários Testados

| Verificação                                      | Sucesso | Falha | Taxa de Conformidade |
| ------------------------------------------------ | ------: | ----: | -------------------: |
| Ausência de achados críticos                     |       8 |     0 |                 100% |
| CORS sem reflexão de origem com credenciais      |       0 |     8 |                   0% |
| Content-Security-Policy presente                 |       0 |     8 |                   0% |
| Strict-Transport-Security presente               |       0 |     8 |                   0% |
| Referrer-Policy presente                         |       0 |     8 |                   0% |
| Permissions-Policy presente                      |       0 |     8 |                   0% |
| Ausência de caminho sensível descoberto          |       0 |     8 |                   0% |
| Recursos protegidos bloqueados sem autenticação  |       6 |     2 |                  75% |

## Resultados por Rota

| Rota                                  | Crítico | Alto | Médio | Baixo | Total |
| ------------------------------------- | ------: | ---: | ----: | ----: | ----: |
| `/api/v1/instituicoes`                |       0 |    2 |     3 |     3 |     8 |
| `/api/v1/usuarios`                    |       0 |    1 |     3 |     3 |     7 |
| `/api/v1/profissionais`               |       0 |    1 |     3 |     3 |     7 |
| `/api/v1/setores`                     |       0 |    2 |     3 |     3 |     8 |
| `/api/v1/escalas`                     |       0 |    1 |     3 |     3 |     7 |
| `/api/v1/plantoes`                    |       0 |    1 |     3 |     3 |     7 |
| `/api/v1/trocas`                      |       0 |    1 |     3 |     3 |     7 |
| `/api/v1/notificacoes/usuario/1`      |       0 |    1 |     3 |     3 |     7 |

## Métricas do Scan

| Métrica                  | Resultado |
| ------------------------ | --------- |
| Ferramenta utilizada     | KageSec   |
| Rotas avaliadas          | 8         |
| Páginas rastreadas       | 8         |
| Profundidade             | 1         |
| Execução com navegador   | Não       |
| Achados totais           | 58        |
| Achados críticos         | 0         |
| Achados altos            | 10        |
| Achados médios           | 24        |
| Achados baixos           | 24        |

### Achados por Tipo

| Achado                                               | Severidade | Ocorrências |
| ---------------------------------------------------- | ---------- | ----------: |
| CORS Misconfiguration - Origin Reflection with Credentials | Alta       |           8 |
| Potential Authentication Bypass - Authenticated Resource Accessible Without Auth | Alta | 2 |
| Hidden Path Discovered - HTTP 401                    | Média      |           8 |
| Missing Content-Security-Policy Header               | Média      |           8 |
| Missing HTTP Strict Transport Security (HSTS)         | Média      |           8 |
| DNSSEC Not Enabled                                   | Baixa      |           8 |
| Missing Referrer-Policy Header                       | Baixa      |           8 |
| Missing Permissions-Policy Header                    | Baixa      |           8 |

## Métricas de Execução

| Métrica                      | Resultado        |
| ---------------------------- | ---------------- |
| Início da execução           | 2026-06-22 21:27:25 |
| Fim da execução              | 2026-06-22 21:31:06 |
| Duração total aproximada     | 3 min 41 s       |
| Duração média por rota       | 22 s             |
| Rotas com scan concluído     | 8                |
| Rotas interrompidas          | 0                |

## Rotas Avaliadas

| Rota |
| ---- |
| `http://localhost:8080/api/v1/instituicoes` |
| `http://localhost:8080/api/v1/usuarios` |
| `http://localhost:8080/api/v1/profissionais` |
| `http://localhost:8080/api/v1/setores` |
| `http://localhost:8080/api/v1/escalas` |
| `http://localhost:8080/api/v1/plantoes` |
| `http://localhost:8080/api/v1/trocas` |
| `http://localhost:8080/api/v1/notificacoes/usuario/1` |

## Conclusão

O teste demonstrou que a API possui problemas relevantes de configuração de segurança. Embora nenhum achado crítico tenha sido identificado, todos os thresholds relacionados a severidade alta, média, CORS e headers de segurança foram violados.

O principal risco recorrente é a configuração de CORS refletindo origens arbitrárias com `Access-Control-Allow-Credentials: true`, o que pode permitir abuso em cenários com credenciais de usuário. Também foram identificadas ausências de headers importantes, como `Content-Security-Policy`, `Strict-Transport-Security`, `Referrer-Policy` e `Permissions-Policy`.

Recomenda-se corrigir prioritariamente a política de CORS, revisar os endpoints marcados com possível bypass de autenticação (`/api/v1/instituicoes` e `/api/v1/setores`) e adicionar os headers de segurança na configuração global da aplicação antes de uma nova execução do KageSec.
