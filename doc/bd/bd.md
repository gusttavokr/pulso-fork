# Modelo de Dados

## Diagrama ER
<img width="1751" height="1021" alt="pulsodistribuido drawio (10)" src="https://github.com/user-attachments/assets/9aa20c49-2fca-4400-9c12-c87b999f33e5" />

## Modelo Relacional
<img width="5499" height="2601" alt="erdplus" src="https://github.com/user-attachments/assets/b0f9fd78-de35-496a-87f8-6fbec15b414a" />

## Dicionário de Dados


---

### Tabela: Notificação

**Descrição** : Representa mensagens enviadas a usuários do sistema, como alertas de escala, confirmações de plantões ou avisos de troca.

| Colunas            | Descrição                                           | Tipo de Dado         | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------ | --------------------------------------------------- | -------------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_notificacao** | Identificador único da notificação                  | \[int]               |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| tipo               | Tipo da notificação                                 | \[TipoNotificacao]   |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| titulo             | Título visível da notificação                       | \[varchar]           | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| conteudo           | Conteúdo da notificação                             | \[json]              |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| status             | Status da notificação (ex: “pendente”, “lida”)      | \[StatusNotificacao] |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| data_criacao       | Data e hora em que a notificação foi criada         | \[timestamp]         |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| data_leitura       | Data guardando o registro de leitura da notificação | \[timestamp]         |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| id_destinatario    | Referência ao usuário destinatário                  | \[int]               |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Usuario(id_usuario) |
| id_remetente       | Referência ao usuário remetente                     | \[int]               |         | ☑    | ☐  | ☑  | ☐      | ☐        |         | FK → Usuario(id_usuario) |

---

### Tabela : Usuário

**Descrição** : Representa contas de acesso ao sistema, usadas tanto por pessoas quanto por instituições. Contém dados de autenticação e permissões.

| Colunas        | Descrição                                                  | Tipo de Dado | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| -------------- | ---------------------------------------------------------- | ------------ | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_usuario** | Identificador único do usuário                             | \[int]       |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| matricula      | Matrícula específica para realização do login do usuário   | \[char]      | 14      | ☐    | ☐  | ☐  | ☑      | ☐        |         | Not null |
| senha          | Senha para realização do login do usuário                  | \[varchar]   | 255     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| ultimo_login   | Data guardando o ultimo login do usuário                   | \[timestamp] |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| ativo          | Confirmador se é um usuário ativo no sistema               | \[booelan]   |         | ☐    | ☐  | ☐  | ☐      | ☐        | false   | Not null |
| criado_em      | Data guardando quando a conta de usuário foi criada        | \[timestanp] |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| atualizado_em  | Data guardando o último registro de atualização do usuário | \[timestamp] |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |

---

### Tabela: Profissional

**Descrição** : Representa indivíduos que atuam como profissionais de saúde cadastrados no sistema, podendo assumir vínculos em diferentes instituições.

| Colunas             | Descrição                                           | Tipo de Dado     | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------- | --------------------------------------------------- | ---------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_profissional** | Identificador único do profissional                 | \[int]           |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| nome                | Nome completo do profissional                       | \[varchar]       | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| email               | E-mail de contato do profissional                   | \[varchar]       | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| telefone            | Telefone de contato do profissional                 | \[varchar]       | 14      | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| foto_perfil         | Caminho ou URL da foto de perfil do profissional    | \[varchar]       | 255     | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| ano_formacao        | Ano de formação do profissional                     | \[timestamp]     |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| pix                 | Chave Pix para pagamentos                           | \[varchar]       | 50      | ☑    | ☐  | ☐  | ☑      | ☐        |         |          |
| tipo_pix            | Tipo da chave Pix (ex: CPF, e-mail, telefone, etc.) | \[TipoPix]       |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| crm                 | Registro profissional (CRM)                         | \[varchar]       | 8       | ☐    | ☐  | ☐  | ☑      | ☐        |         | Not null |
| data_nasc           | Data de nascimento do profissional                  | \[timestamp]     |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| especialidades      | Lista de especialidades associadas                  | \[Especialidade] |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| eh_lider_escala     | Indica se o profissional atua como líder de escala  | \[boolean]       |         | ☐    | ☐  | ☐  | ☐      | ☐        | False   | Not null |
| id_usuario          | Referência ao usuário vinculado ao profissional     | \[int]           |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Usuario(id_usuario) |

---

### Tabela: Instituição

**Descrição** : Representa organizações de saúde, como hospitais, clínicas ou cooperativas, que oferecem especialidades, setores e escalas de plantões.

| Colunas            | Descrição                                                          | Tipo de Dado | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------ | ------------------------------------------------------------------ | ------------ | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_instituição** | Identificador único da instituição                                 | \[int]       |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| endereco           | Endereço completo da instituição                                   | \[varchar]   | 255     | ☐    | ☐  | ☐  | ☑      | ☐        |         | Not null |
| cnpj               | Cadastro Nacional de Pessoa Jurídica da instituição                | \[varchar]   | 14      | ☐    | ☐  | ☐  | ☑      | ☐        |         | Not null |
| exige_vinculo      | Indica se o profissional precisa possuir vínculo formal para atuar | \[boolean]   |         | ☐    | ☐  | ☐  | ☐      | ☐        | False   | Not null |
| nome               | Nome oficial ou fantasia da instituição                            | \[varchar]   | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| email              | Endereço de e-mail institucional para contato                      | \[varchar]   | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| telefone           | Número de telefone para contato institucional                      | \[varchar]   | 14      | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| foto_perfil        | Caminho ou URL da imagem de perfil da instituição                  | \[varchar]   | 255     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| id_usuario         | Referência ao usuário responsável pela gestão da instituição       | \[int]       |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Usuario(id_usuario) |

---

### Tabela: Setor

**Descrição** : Representa divisões internas de uma instituição dentro de uma especialidade, utilizadas para organização de escalas e plantões.

| Colunas            | Descrição                                          | Tipo de Dado     | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------ | -------------------------------------------------- | ---------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_setor**       | Identificador único do setor                       | \[int]           |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| nome               | Nome identificador do setor dentro da instituição  | \[varchar]       | 100     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| especialidade      | Especialidade médica à qual o setor está associado | \[Especialidade] |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| id_instituicao     | Referência à instituição à qual o setor pertence   | \[int]           |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Instituicao(id_instituicao) |

---

### Tabela: Escala

**Descrição** : Representa um conjunto organizado de plantões dentro de um período específico, associado a um setor e gerido por um líder de escala.

| Colunas              | Descrição                                                     | Tipo de Dado    | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| -------------------- | ------------------------------------------------------------- | --------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_escala**        | Identificador único da escala                                 | \[int]          |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| nome                 | Nome descritivo da escala                                     | \[varchar]      | 150     | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| mes_referencia       | Mês e ano de referência da escala                             | \[timestamp]    |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| status               | Situação atual da escala (ex: rascunho, publicada, encerrada) | \[StatusEscala] |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| publicada_em         | Data e hora de publicação da escala                           | \[timestamp]    |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| data_criacao         | Data e hora de criação do registro da escala                  | \[timestamp]    |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| data_aualizacao      | Data e hora da última atualização da escala                   | \[timestamp]    |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| id_prof_lider_escala | Referência ao profissional líder responsável pela escala      | \[int]          |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Profissional(id_profissional) |
| id_setor             | Referência ao setor ao qual a escala pertence                 | \[int]          |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Setor(id_setor) |

---

### Tabela: Plantão

**Descrição** : Representa um turno de atendimento médico com data e duração definidas, pertencente a uma escala e associado a um ou mais profissionais.

| Colunas              | Descrição                                                  | Tipo de Dado      | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| -------------------- | ---------------------------------------------------------- | ----------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_plantao**       | Identificador único do plantao                             | \[int]            |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| hora_entrada         | Data e hora de início do plantão                           | \[timestamp]      |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| hora_saida           | Data e hora de término do plantão                          | \[timestamp]      |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| estimativa_valor     | Valor estimado a ser pago pelo plantão                     | \[numeric]        |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| forma_pagamento      | Forma prevista de pagamento do plantão                     | \[FormaPagamento] |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| estimativa_pagamento | Data estimada para pagamento do plantão                    | \[timestamp]      |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| comentario           | Observações adicionais sobre o plantão                     | \[text]           |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| qtd_profissionais    | Número máximo de profissionais que podem assumir o plantão | \[int]            |         | ☐    | ☐  | ☐  | ☐      | ☐        | 1       | Not null |
| data_criacao         | Data e hora de criação do registro do plantão              | \[timestamp]      |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| data_atualizacao     | Data e hora da última atualização do registro do plantão   | \[timestamp]      |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| id_escala            | Referência à escala à qual o plantão pertence              | \[int]            |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Escala(id_escala) |

---

### Tabela: Troca

**Descrição** : Representa operações de repasse ou troca de plantões entre profissionais, podendo envolver um ou mais plantões e participantes.

| Colunas                 | Descrição                                                            | Tipo de Dado   | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ----------------------- | -------------------------------------------------------------------- | -------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_troca**            | Identificador único da troca                                         | \[int]         |         | ☐    | ☑  | ☐  | ☐      | ☑        |         | Not null |
| comentario              | Comentário explicando o motivo ou detalhes da troca                  | \[text]        |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| tipo                    | Tipo da troca (por exemplo, simples, dupla)                          | \[TipoTroca]   |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| status                  | Status atual da troca (pendente, aceita, recusada, etc.)             | \[StatusTroca] |         | ☐    | ☐  | ☐  | ☐      | ☐        |         | Not null |
| data_solicitacao        | Data e hora em que a solicitação de troca foi criada                 | \[timestamp    |         | ☐    | ☐  | ☐  | ☐      | ☐        | today   | Not null |
| data_resposta           | Data e hora da resposta à solicitação de troca (quando houver)       | \[timestamp]   |         | ☑    | ☐  | ☐  | ☐      | ☐        |         |          |
| id_profissional_origem  | Profissional que iniciou a solicitação de troca                      | \[int]         |         | ☐    | ☐  | c  | ☐      | ☐        |         | FK → Profissional(id_profissional) |
| id_alocacao_origem      | Alocação pertencente ao profissional que solicitou a troca           | \[int]         |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Alocacao(id_alocacao) |
| id_profissional_destino | Profissional requisitado para aceitar ou negar a troca               | \[int]         |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Profissional(id_profissional) |
| id_alocacao_destino     | Alocação oferecida pelo profissional de destino em troca             | \[int]         |         | ☐    | ☐  | ☑  | ☐      | ☐        |         | FK → Alocacao(id_alocacao) |

---

### Tabela: Profissional_Plantao_Combinado

**Descrição** : Representa os profissionais que se candidataram (ou combinaram) para um determinado plantão, antes da confirmação final.

| Colunas                 | Descrição                                  | Tipo de Dado   | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------- | ---------------------------------------------- | -------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_plantao**      | Chave estrangeira referenciando o plantão      | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |
| **id_profissional** | Chave estrangeira referenciando o profissional | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |

---

### Tabela: Profissional_Plantao_Comfirmado

**Descrição** : Representa os profissionais confirmados em um plantão específico (ou seja, aqueles cuja alocação foi aceita/efetivada).

| Colunas                 | Descrição                                  | Tipo de Dado   | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------- | ---------------------------------------------- | -------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_plantao**      | Chave estrangeira referenciando o plantão      | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |
| **id_profissional** | Chave estrangeira referenciando o profissional | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |

---

### Tabela: Profissional_Instituicao_Clt

**Descrição** : Relaciona profissionais contratados via CLT com as instituições onde atuam.

| Colunas                 | Descrição                                  | Tipo de Dado   | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------- | ---------------------------------------------- | -------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_instituicao**  | Chave estrangeira referenciando a instituição  | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |
| **id_profissional** | Chave estrangeira referenciando o profissional | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |

---

### Tabela: Profissional_Instituicao_Pj

**Descrição** : Relaciona profissionais prestadores de serviço (PJ) com as instituições onde atuam.

| Colunas                 | Descrição                                  | Tipo de Dado   | Tamanho | Null | PK | FK | Unique | Identity | Default | Check    |
| ------------------- | ---------------------------------------------- | -------------- | ------- | ---- | -- | -- | ------ | -------- | ------- | -------- |
| **id_instituicao**  | Chave estrangeira referenciando a instituição  | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |
| **id_profissional** | Chave estrangeira referenciando o profissional | \[int]         |         | ☐    | ☑  | ☑  | ☐      | ☐      |          | Not null |
