# Execução dos Testes de Carga com k6

## Pré-requisitos

Antes de executar os testes, é necessário possuir:

* Docker instalado e em execução;
* Arquivo de teste `teste-carga-escala.js`;
* A aplicação alvo disponível e acessível pela máquina que executará o teste.

## Acessando a Pasta dos Testes

Abra um terminal e navegue até o diretório onde o script está localizado.

Exemplo:

```powershell
cd k6
```

## Executando o Teste

Utilize o comando abaixo para executar o script através da imagem oficial do k6:

```powershell
docker run --rm -i -v "${PWD}:/k6" grafana/k6 run /k6/teste-carga-escala.js
```

### Descrição dos parâmetros

| Parâmetro                       | Descrição                                                           |
| ------------------------------- | ------------------------------------------------------------------- |
| `docker run`                    | Executa um novo contêiner Docker                                    |
| `--rm`                          | Remove o contêiner após a execução                                  |
| `-i`                            | Mantém a entrada padrão aberta                                      |
| `-v "${PWD}:/k6"`               | Compartilha a pasta atual com o diretório `/k6` dentro do contêiner |
| `grafana/k6`                    | Imagem oficial do k6                                                |
| `run /k6/teste-carga-escala.js` | Executa o script de teste especificado                              |

## Resultados

Ao final da execução, o k6 exibirá no terminal:

* Quantidade de requisições realizadas;
* Taxa de falhas;
* Tempo médio de resposta;
* Percentis de desempenho (P90, P95, P99);
* Métricas de rede;
* Resultado dos thresholds configurados;
* Estatísticas das verificações realizadas durante o teste.
