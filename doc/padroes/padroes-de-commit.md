# 📝 Padrao de Commits com Icones - Pulso

## 🎯 Objetivo
Definir padrao para mensagens de commit utilizando **tipos + icones**, garantindo clareza, rastreabilidade e consistencia no historico do repositorio.

---

## 📌 Regras Gerais
- Mensagens **sempre em portugues**, sem acento.  
- Sempre usar **tempo presente** (ex: "adiciona", "corrige", "remove").  
- A mensagem deve ser **curta, clara e objetiva**.  
- Usar **letras minusculas** em todo o commit.  
- Commits devem refletir **apenas uma alteracao logica por vez**.  
- Não faça git add . (adicione arquivo por vez)

---

## ✅ Estrutura de um Commit

### Commit Curto (titulo)
Commit direto e objetivo:
```bash
git commit -m ":sparkles: feat: adiciona cadastro de clientes"
```

### Commit Longo (titulo + descricao detalhada)
Commit com explicacao complementar:
```bash
git commit -m ":bug: fix: corrige erro de autenticacao" \
-m "Esse bug ocorria porque a funcao de validacao nao tratava emails em maiusculo. Agora todos os emails sao convertidos para minusculo antes da verificacao."
```
---

---

## 🎨 Tipos de Commit

- **feat:** quando adiciona uma nova funcionalidade.  
- **fix:** quando corrige um bug.  
- **docs:** alteracoes em documentacao (arquivos `.md`, comentarios de codigo).  
- **style:** alteracoes que nao mudam a logica, apenas formato (indentacao, espacamento, padrao de codigo).  
- **refactor:** melhorias de codigo que nao alteram comportamento externo.  
- **test:** adicao ou ajuste de testes.  
- **chore:** tarefas diversas como atualizacao de dependencias, configuracoes, scripts.  
- **perf:** alteracoes que melhoram desempenho.  

---

## 📖 Regras Complementares
- Nunca usar mensagens vagas como: *"ajustes"*, *"update"*, *"mudancas"*.  
- Sempre detalhar o **que foi feito** e nao o **como**.  
- Quando necessario, complementar com descricao mais longa usando quebra de linha:

---

## ✨ feat
### Curto
```bash
git commit -m ":sparkles: feat: adiciona validacao de cpf no cadastro de usuario" 
```


### Longo
```bash
git commit -m ":sparkles: feat: adiciona validacao de cpf no cadastro de usuario" \
-m "Implementada validacao de CPF no fluxo de cadastro para evitar registros invalidos. A regra utiliza a funcao de validacao padrao do sistema."
```

## 🐛 fix
### Curto
```bash
git commit -m ":bug: fix: corrige erro de null pointer em hospitalservice" 
```

### Longo
```bash
git commit -m ":bug: fix: corrige erro de null pointer em hospitalservice" \
-m "O erro ocorria quando a lista de medicos estava vazia e nao era tratada. Agora e feita a verificacao antes do processamento."
```

## 📚 docs
### Curto
```bash
git commit -m ":books: docs: atualiza padroes de projeto no readme"
```
### Longo
```bash
git commit -m ":books: docs: atualiza padroes de projeto no readme" \
-m "Foram adicionados os padroes de nomenclatura de pacotes e organizacao de diretorios para maior clareza."
```

## ♻️ refactor
### Curto
```bash
git commit -m ":recycle: refactor: melhora logica de agendamento de plantao"
```
### Longo
```bash
git commit -m ":recycle: refactor: melhora logica de agendamento de plantao" \
-m "Simplificada a logica de verificacao de disponibilidade, reduzindo a complexidade ciclomática e melhorando a legibilidade."
```

## 🔧 chore
### Curto
```bash
git commit -m ":wrench:chore: atualiza versao do spring boot para 3.2.0"

```
### Longo
```bash
git commit -m ":wrench: chore: atualiza versao do spring boot para 3.2.0" \
-m "Atualizada a dependencia do Spring Boot de 3.1.1 para 3.2.0, trazendo correcoes de bugs e melhorias de performance."

```


---
## Icones

---

## 🎨 Tipos de Commits com Icones Mais Usados

| Icone | Tipo       | Quando usar | Exemplo |
|-------|-----------|-------------|---------|
| ✨ :sparkles: | **feat** | Nova funcionalidade | `✨ feat: adiciona endpoint de criacao de hospital` |
| 🐛 :bug: | **fix** | Correcoes de bugs | `🐛 fix: corrige erro de null pointer em hospitalservice` |
| 📚 :books: | **docs** | Documentacao | `📚 docs: atualiza guia de instalacao` |
| 🎉 :tada: | **init** | Commit inicial | `🎉 init: estrutura inicial do projeto pulso` |
| 🔧 :wrench: | **chore** | Configuracoes e tarefas auxiliares | `🔧 chore: ajusta configuracao do spring security` |
| 🚀 :rocket: | **deploy** | Preparar ou fazer deploy | `🚀 chore: configura pipeline de deploy para producao` |
| 💄 :lipstick: | **style** | Estilizacao de interface | `💄 style: melhora cores do formulario de login` |
| ♻️ :recycle: | **refactor** | Refatoracao sem mudar comportamento | `♻️ refactor: simplifica logica de validacao de cpf` |
| ⚡ :zap: | **perf** | Melhorias de performance | `⚡ perf: otimiza consulta de escala por medico` |
| 🧪 :test_tube: | **test** | Criacao ou alteracao de testes | `🧪 test: adiciona testes unitarios para usuarioservice` |
| 🗑️ :wastebasket: | **remove** | Remocao de arquivos ou codigos | `🗑️ remove: deleta classe nao utilizada hospitalmapper` |
| ➕ :heavy_plus_sign: | **build** | Adicao de dependencia | `➕ build: adiciona dependencia lombok no projeto` |
| ➖ :heavy_minus_sign: | **build** | Remocao de dependencia | `➖ build: remove dependencia antiga do gson` |
| 🚚 :truck: | **chore** | Mover ou renomear arquivos | `🚚 chore: move classes de excecoes para novo pacote` |

---

## ✅ Status
Este padrao de commits com icones deve ser seguido por toda a equipe no desenvolvimento do **Pulso**.  
Commits fora do padrao podem ser rejeitados em code review.