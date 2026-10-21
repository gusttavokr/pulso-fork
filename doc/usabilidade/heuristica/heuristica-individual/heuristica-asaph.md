# Avaliação por inspeção com lista Eureca

Este documento apresenta a avaliação individual das interfaces do sistema Pulso, realizada pelo avaliador Robson, com base nas heurísticas Eureca (Matos & Freire).

Foram analisadas as interfaces relacionadas aos casos de uso: gerenciar escala, gerenciar plantão, alocar profissional em plantão, consultar plantões abertos e pegar plantão.

# CDU: Gerenciar Escala


| Nº | Problema Identificado                                               | Heurística Violada     | Sugestão de Correção                   | Severidade | Esforço |
| -- | ------------------------------------------------------------------- | ---------------------- | -------------------------------------- | ---------- | ------- |
| 01 | Não é possível visualizar o conteúdo corretamente (baixo contraste) | FM11 + AC4 – Contraste | Ajustar cores para melhor legibilidade | Crítica    | Baixo   |

<img width="600" alt="image" src="https://github.com/user-attachments/assets/67e195df-43b0-42ad-8966-e34045ec71bf" />

---

| Nº | Problema Identificado                                 | Heurística Violada   | Sugestão de Correção                    | Severidade | Esforço |
| -- | ----------------------------------------------------- | -------------------- | --------------------------------------- | ---------- | ------- |
| 02 | Interface não responsiva, difícil visualizar a escala | PD3 – Responsividade | Ajustar layout para dispositivos móveis | Alta       | Médio   |

<img width="600" alt="image-1" src="https://github.com/user-attachments/assets/f1f3d2f5-8b11-45f3-a895-38d3f634f2eb" />

---

| Nº | Problema Identificado                           | Heurística Violada       | Sugestão de Correção                   | Severidade | Esforço |
| -- | ----------------------------------------------- | ------------------------ | -------------------------------------- | ---------- | ------- |
| 03 | Permite publicar escala já publicada sem alerta | AF9 – Prevenção de erros | Bloquear ação ou solicitar confirmação | Alta       | Baixo   |

<img width="600" alt="image-2" src="https://github.com/user-attachments/assets/c604c89e-6d05-44fe-995d-ab69ad5324a3" />
<img width="600" alt="image-3" src="https://github.com/user-attachments/assets/be908977-6e60-4573-a134-3102b2810110" />

---

| Nº | Problema Identificado         | Heurística Violada   | Sugestão de Correção                  | Severidade | Esforço |
| -- | ----------------------------- | -------------------- | ------------------------------------- | ---------- | ------- |
| 04 | Não é possível limpar filtros | AF1 – Funcionalidade | Implementar botão de reset de filtros | Alta       | Baixo   |

<img width="600" alt="image-23" src="https://github.com/user-attachments/assets/14c274d3-2f90-47a6-a420-7df98fe3e179" />

---

| Nº | Problema Identificado                | Heurística Violada   | Sugestão de Correção            | Severidade | Esforço |
| -- | ------------------------------------ | -------------------- | ------------------------------- | ---------- | ------- |
| 05 | Botão de excluir escala não funciona | AF1 – Funcionalidade | Corrigir integração com backend | Crítica    | Médio   |

<img width="600" alt="image-24" src="https://github.com/user-attachments/assets/65bc7fb6-ea3b-4010-8480-ec0df32fbfdb" />

---

# CDU: Consultar Plantões Abertos


| Nº | Problema Identificado                  | Heurística Violada | Sugestão de Correção           | Severidade | Esforço |
| -- | -------------------------------------- | ------------------ | ------------------------------ | ---------- | ------- |
| 06 | Ícones de alerta sem significado claro | CO2 – Feedback     | Adicionar descrição ou legenda | Alta       | Baixo   |

<img width="600" alt="image-4" src="https://github.com/user-attachments/assets/a181e170-912a-4d94-8986-b93d25c2d9fe" />

---


| Nº | Problema Identificado                    | Heurística Violada | Sugestão de Correção              | Severidade | Esforço |
| -- | ---------------------------------------- | ------------------ | --------------------------------- | ---------- | ------- |
| 07 | Endereço parece link, mas não é clicável | CO3 – Affordance   | Tornar clicável ou remover estilo | Média      | Baixo   |

<img width="600" alt="image-5" src="https://github.com/user-attachments/assets/86da2d93-c6b5-483d-8d9e-e94c68d451ef" />

---

| Nº | Problema Identificado                | Heurística Violada      | Sugestão de Correção  | Severidade | Esforço |
| -- | ------------------------------------ | ----------------------- | --------------------- | ---------- | ------- |
| 08 | Lista com muitos itens sem paginação | NA5 – Mínimo de cliques | Implementar paginação | Alta       | Médio   |

<img width="600" alt="image-6" src="https://github.com/user-attachments/assets/757977e3-94d8-45f1-89af-dd958b6c618e" />

---

| Nº | Problema Identificado             | Heurística Violada   | Sugestão de Correção    | Severidade | Esforço |
| -- | --------------------------------- | -------------------- | ----------------------- | ---------- | ------- |
| 09 | Botão limpar filtros não funciona | AF1 – Funcionalidade | Corrigir funcionalidade | Alta       | Baixo   |

<img width="600" alt="image-7" src="https://github.com/user-attachments/assets/2277477f-9ef9-45fb-b2c5-d2582828fcf8" />

---

# CDU: Gerenciar Plantão


| Nº | Problema Identificado                                   | Heurística Violada | Sugestão de Correção | Severidade | Esforço |
| -- | ------------------------------------------------------- | ------------------ | -------------------- | ---------- | ------- |
| 10 | Permite datas inválidas (passado e fim antes do início) | AF10 – Restrições  | Validar datas        | Crítica    | Baixo   |

<img width="600" alt="image-8" src="https://github.com/user-attachments/assets/942091c9-d18e-444e-8f18-e6441b7c0981" />

---

| Nº | Problema Identificado                 | Heurística Violada | Sugestão de Correção    | Severidade | Esforço |
| -- | ------------------------------------- | ------------------ | ----------------------- | ---------- | ------- |
| 11 | Não há feedback ao erro no formulário | CO2 – Feedback     | Exibir mensagem de erro | Crítica    | Baixo   |

<img width="600" alt="image-9" src="https://github.com/user-attachments/assets/8eb06b0f-24c3-4d0b-95d5-37fd556c0e66" />

---

| Nº | Problema Identificado               | Heurística Violada | Sugestão de Correção     | Severidade | Esforço |
| -- | ----------------------------------- | ------------------ | ------------------------ | ---------- | ------- |
| 12 | Calendário permite datas no passado | AF10 – Restrições  | Bloquear datas inválidas | Alta       | Baixo   |

<img width="600" alt="image-20" src="https://github.com/user-attachments/assets/fa5c6b3e-0227-47ee-84b8-babd73cf3ec4" />

---

| Nº | Problema Identificado                 | Heurística Violada | Sugestão de Correção         | Severidade | Esforço |
| -- | ------------------------------------- | ------------------ | ---------------------------- | ---------- | ------- |
| 13 | Campos obrigatórios não são indicados | CO3 – Affordance   | Destacar campos obrigatórios | Alta       | Baixo   |

<img width="600" alt="image-21" src="https://github.com/user-attachments/assets/cb2ae66e-ad67-4377-86d3-723ca9ba0f9f" />

---

| Nº | Problema Identificado    | Heurística Violada   | Sugestão de Correção | Severidade | Esforço |
| -- | ------------------------ | -------------------- | -------------------- | ---------- | ------- |
| 14 | Interface não responsiva | PD3 – Responsividade | Ajustar layout       | Alta       | Médio   |

<img width="600" alt="image-22" src="https://github.com/user-attachments/assets/bb785785-e433-49c1-a1a4-ceda922bfe65" />

---

# CDU: Alocar Profissional em Plantão

| Nº | Problema Identificado     | Heurística Violada | Sugestão de Correção                | Severidade | Esforço |
| -- | ------------------------- | ------------------ | ----------------------------------- | ---------- | ------- |
| 15 | Não exibe dados do médico | CO2 – Feedback     | Mostrar informações do profissional | Alta       | Baixo   |

<img width="600" alt="image-15" src="https://github.com/user-attachments/assets/67bd1164-72bd-4a60-9136-bcb72ba1b6b0" />

---

| Nº | Problema Identificado           | Heurística Violada         | Sugestão de Correção | Severidade | Esforço |
| -- | ------------------------------- | -------------------------- | -------------------- | ---------- | ------- |
| 16 | Botão remover sem padrão visual | FM5 – Consistência externa | Usar cor vermelha    | Baixa      | Baixo   |

<img width="600" alt="image-16" src="https://github.com/user-attachments/assets/c0ba12df-2a57-4f16-90fb-973a07599676" />

---

| Nº | Problema Identificado                     | Heurística Violada | Sugestão de Correção      | Severidade | Esforço |
| -- | ----------------------------------------- | ------------------ | ------------------------- | ---------- | ------- |
| 17 | Busca não funciona e não retorna mensagem | AF1 + CO2          | Corrigir busca + feedback | Alta       | Médio   |

<img width="600" alt="image-17" src="https://github.com/user-attachments/assets/b4e5212a-5060-48cb-a6b0-1c45152b1fb9" />

---

| Nº | Problema Identificado                     | Heurística Violada | Sugestão de Correção  | Severidade | Esforço |
| -- | ----------------------------------------- | ------------------ | --------------------- | ---------- | ------- |
| 18 | Sai da tela sem confirmação e perde dados | PU1 + AF9          | Adicionar confirmação | Alta       | Baixo   |

<img width="600" alt="image-18" src="https://github.com/user-attachments/assets/ba08c0fb-21b4-4072-bea2-fb4c796f8e17" />

---

| Nº | Problema Identificado        | Heurística Violada | Sugestão de Correção  | Severidade | Esforço |
| -- | ---------------------------- | ------------------ | --------------------- | ---------- | ------- |
| 19 | Dashboard sem dados visíveis | CO2 – Feedback     | Corrigir renderização | Alta       | Médio   |

<img width="600" alt="image-19" src="https://github.com/user-attachments/assets/90cd0507-c591-4dd4-9d0d-013c8e249c56" />

---

# CDU: Pegar Plantão

| Nº | Problema Identificado         | Heurística Violada   | Sugestão de Correção | Severidade | Esforço |
| -- | ----------------------------- | -------------------- | -------------------- | ---------- | ------- |
| 20 | Não é possível limpar filtros | AF1 – Funcionalidade | Corrigir botão       | Alta       | Baixo   |

<img width="600" alt="image-10" src="https://github.com/user-attachments/assets/bd8ab69e-08a5-4ec4-9847-9f969a0d4676" />

---

| Nº | Problema Identificado        | Heurística Violada | Sugestão de Correção   | Severidade | Esforço |
| -- | ---------------------------- | ------------------ | ---------------------- | ---------- | ------- |
| 21 | Link visual que não funciona | CO3 – Affordance   | Corrigir comportamento | Média      | Baixo   |

<img width="600" alt="image-11" src="https://github.com/user-attachments/assets/02379e51-438b-401d-a795-0f05451788c2" />

---

| Nº | Problema Identificado | Heurística Violada      | Sugestão de Correção  | Severidade | Esforço |
| -- | --------------------- | ----------------------- | --------------------- | ---------- | ------- |
| 22 | Sem paginação         | NA5 – Mínimo de cliques | Implementar paginação | Alta       | Médio   |

<img width="600" alt="image-12" src="https://github.com/user-attachments/assets/58ac655b-f7f1-4a41-8a26-45147447e357" />

---

| Nº | Problema Identificado | Heurística Violada | Sugestão de Correção | Severidade | Esforço |
| -- | --------------------- | ------------------ | -------------------- | ---------- | ------- |
| 23 | Cards desalinhados    | FM9 – Alinhamento  | Ajustar layout       | Média      | Baixo   |

<img width="600" alt="image-13" src="https://github.com/user-attachments/assets/f5dc90a4-71b2-4669-9dee-f06398408d69" />

---

| Nº | Problema Identificado                                 | Heurística Violada | Sugestão de Correção | Severidade | Esforço |
| -- | ----------------------------------------------------- | ------------------ | -------------------- | ---------- | ------- |
| 24 | Informação irrelevante exibida (“Nenhuma observação”) | CO1 – Linguagem    | Ocultar quando vazio | Baixa      | Baixo   |

<img width="600" alt="image-14" src="https://github.com/user-attachments/assets/393c17b4-04c6-44b7-a747-6a85b0aa7215" />
