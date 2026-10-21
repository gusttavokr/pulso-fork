# CDU05. Gerenciar Vínculos

- **Ator principal**: RH da Instituição  
- **Atores secundários**: Sistema  
- **Resumo**: Permite que o RH gerencie os vínculos dos médicos, caso esta exija um vínculo obrigatório, e líderes de escala com a instituição. 
- **Pré-condição**: O RH está autenticado no sistema e médicos e líderes de escala devidamente cadastrados.  
- **Pós-condição**: O vínculo de médicos ou líderes de escala com a instituição é definida.

## Fluxo Principal

| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1 - Acessa a área "Criar novo vínculo". | |
| | 2 - Exibe a lista de médicos e líderes de escala cadastrados que não estão vinculados a instituição. |
| 3 - Seleciona médicos ou líderes de interesse e informa o tipo de vínculo. | |
| | 4 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN17**, **RN55**, **RN56** e **RN57** resgata informações dos selecionados e cria vínculos com os tipos informados. |
| 5 - Visualiza listagem de vinculados com os selecionados adicionados. | |

---

## Fluxos Alternativos

### Fluxo Alternativo I – [Atualizar vínculo]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.1 - Seleciona opção de atualizar em vínculo específico e informa novos dados. | |
| | 1.2 - Valida as [Regras de Negócio](../../regra-de-negocio/regra-negocio.md): **RN17**, **RN55**, **RN56** e **RN57** resgata vínculo selecionado, confere dados e atualiza informações. |
| 1.3 - Visualiza listagem de vinculados com vínculo selecionado atualizado. | |

### Fluxo Alternativo II – [Excluir vínculo]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.a - Seleciona opção de excluir em vínculo específico. | |
| | 1.b - Resgata vínculo selecionado e o apaga da base de dados. |
| 1.c - Visualiza listagem de vinculados agora sem o vínculo excluído. | |

### Fluxo Alternativo III – [Lista vínculos]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.aa - Seleciona opção de "Listagem de vinculados". | |
| | 1.ab - Resgata usuário logado, instituição, vínculos, profissionais e líderes de escala relacionados a ele. |
| 1.ac - Visualiza listagem de vinculados. | |

### Fluxo Alternativo IV – [Instituição sem vínculo obrigatório]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| | 1.ab.1 - Resgata usuário logado, instituição, vínculos e líderes de escala relacionados a ele. |
| 1.ab.2 - Visualiza listagem de líderes vinculados. | |

---

## Fluxos Exceção

### Fluxo Exceção I – [Ação em vínculo inexistente]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 1.1.1 - Realiza ação em vínculo inexistente | |
| | 1.1.2. Exibe mensagem de erro: *"Não foi possível encontrar vínculo na base de dados."* |

## Fluxo Exceção II - [Criação de vínculo inválido]
| Ações do Ator | Ações do Sistema |
|---------------|------------------|
| 3.1 - Seleciona médicos e líderes de forma incorreta (ambos ou nenhum selecionado). | |
| | 3.2 - Bloqueia criação e exibe mensagem relacionada à [**RN17**](../../regra-de-negocio/regra-negocio.md). |
