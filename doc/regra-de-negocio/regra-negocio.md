# Regras de Negócio

**Histórico de Alterações**

| Versão | Autor         | Data        |
| :----: | :------------ | :---------- |
|   1.0  | Flavio Matias | 08/out/2025 |



| Nº | Descrição  |
|----| :--------: |
| **RN01** | Apenas líderes de escala podem criar e alterar escalas e plantões. |
| **RN02** | Uma escala deve ter no mínimo 3 caracteres no nome. |
| **RN03** | Uma escala não pode ser criada com data no passado. |
| **RN04** | A primeira escala de uma instituição não pode usar template. |
| **RN05** | Uma escala não pode ser criada se a instituição não tiver horários registrados. |
| **RN06** | Uma escala não pode ser publicada se não possuir plantões vinculados. |
| **RN07** | Se a escala for criada com a opção “Usar template”, ela replicará a última escala criada para o mesmo setor. |
| **RN08** | Uma escala só pode ser excluída se ainda não tiver sido publicada. |
| **RN09** | Uma escala deve ser criada para um setor da instituição que o líder tem vínculo. |
| **RN10** | Ao criar uma escala são gerados plantões *defaults* com datas e horários estabelecidos pela instituição comportando até 1 profissional - futuramente o líder de escala pode alterar esses dados. |
| **RN11** | Ao atualizar a escala não é possível alterar o setor, o mês de referência e nem o líder da escala. |
| **RN12** | Somente líderes de escala podem criar e alterar plantões.                                                                                                                                       |
| **RN13** | Um plantão só pode ser criado dentro do horário fixo da instituição.                                                                                                                            |
| **RN14** | Um plantão não pode ser criado com data no passado.                                                                                                                                             |
| **RN15** | Um médico CLT não pode ser alocado a um plantão caso sua carga horária tenha ultrapassado 44 horas semanais.                                                                                    |
| **RN16** | Um plantão não pode ser excluído ou atualizado caso ele já esteja em andamento.                                                                                                                 |
| **RN17** | Um vínculo deve estar associado exclusivamente a um profissional ou a um líder de escala.                                                                                                       |
| **RN18** | Todo profissional e líder de escala devem estar associados a uma pessoa previamente cadastrada.                                                                                                 |
| **RN19** | As entidades Líder de Escala, Profissional e Formação devem estar obrigatoriamente associadas a uma Pessoa, não sendo permitido o seu cadastro, alteração ou existência sem essa associação.    |
| **RN20** | Os plantões de uma escala publicada só podem ser alterados com pelo menos 7 dias de antecedência.                                                                                               |
| **RN21** | Um plantão criado não pode ser movido de uma escala para outra.                                                                                                                                 |
| **RN22** | Plantões pertencentes a uma escala publicada não podem ser excluídos.                                                                                                                           |
| **RN23** | Um plantão só pode ser criado com pelo menos 7 dias de antecedência.                                                                                                                            |
| **RN24** | Um plantão só pode ser excluído se não possuir alocações do tipo PEGA.                                                                                                                          |
| **RN27** | Só é possível realizar troca de alocação de um plantão futuro.                                                                                                                                  |
| **RN28** | Só é possível recusar ou aceitar uma troca com status PENDENTE.                                                                                                                                 |
| **RN29** | Trocas confirmadas ou recusadas não podem ser excluídas.                                                                                                                                        |
| **RN30** | Só é possível pegar um plantão de uma escala publicada.                                                                                                                                         |
| **RN31** | Só é possível pegar um plantão de um setor ao qual o profissional pertença.                                                                                                                     |
| **RN32** | Só é possível pegar um plantão enquanto houver vagas disponíveis.                                                                                                                               |
| **RN33** | Só é possível pegar plantões de datas futuras.                                                                                                                                                  |
| **RN34** | Só profissionais podem pegar plantões.                                                                                                                                                          |
| **RN35** | Não é permitido mover uma alocação de plantão diretamente pela atualização.                                                                                                                     |
| **RN36** | Não é permitido remover o profissional de uma alocação diretamente pela atualização.                                                                                                            |
| **RN37** | O ano de formação de um profissional deve ser anterior ou igual ao ano atual.                                                                                                                   |
| **RN38** | O profissional deve possuir pelo menos 18 anos de idade.                                                                                                                                        |
| **RN39** | Um plantão não pode ser criado com mesmo horário, dia e setor de outro plantão existente.                                                                                                       |
| **RN40** | Um plantão deve ter horarios de entrada e saída dentro do limite do mês de referência de sua escala.                                                                                            |
| **RN41** | Não é possível publicar uma escala já publicada.                                                                                                                                                |
| **RN42** | Não é permitido atualizar os campos profissional de origem e alocação de origem após a criação da troca.                                                                                        |
| **RN43** | Somente o Profissional de Origem pode atualizar os campos profissional de destino e comentário da troca e enquanto a troca não foi aceita.                                                      |
| **RN44** | O campo alocação de destino só pode ser atualizado pelo profissional de destino, e apenas dentro de um prazo máximo de 3 (três) dias após a confirmação da troca.                               |
| **RN45** | O líder de escala deve possuir a mesma especialidade do setor ao qual a escala pertence.                                                                                                        |
| **RN46** | Uma escala publicada não pode ser despublicada.                                                                                                                                                 |
| **RN47** | Um profissional com cargo de líder de escala não pode ser atualizado como profissional comum.                                                                                                   |
| **RN48** | Para criar uma escala, devem ser exibidos apenas as instituições que o líder de escala logado tem vínculo. |
| **RN49** | Para criar uma nova escala, depois de selecionada a instituição deve ser exibido apenas os setores dessa instituição. |
| **RN50** | Listagem de escalas para líderes de escala mostram as escalas cujo líder logado criou. |
| **RN51** | Listagem de escalas para instituições mostram as escalas cujo setor é da instituição logada. |
| **RN52** | Profissionais não têm acesso a listagem de escalas. |
| **RN53** | A hora de saída de uma plantão deve ser depois de sua hora de entrada. |
| **RN54** | A data de estimativa de pagamento não pode ser no passado. |
| **RN55** | Para criar um vínculo o profissional tem que ter em sua lista de especialidades uma especialidade que se encontra em algum dos setores da instituição. |
| **RN56** | Os tipos de vínculo de um profissional e uma instituição são CPNJ ou CLT. |
| **RN57** | Para vincular um líder de escala a uma institução é necessário ele estar vinculado como profissional na mesma. |
| **RN58** | Um profissional só pode pegar um plantão que ainda está para acontecer. |
| **RN59** | Um profissional só pode pegar um plantão de uma escala publicada. |
| **RN60** | Um profissional só pode pegar um plantão se estiver vinculado a instituição do plantão. |
| **RN61** | Um profissional só pode pegar um plantão se em sua lista de especialidades tiver a especialidade do setor do plantão. |
| **RN62** | Um plantão só pode ser considerado aberto se: for um plantão que ainda está para acontecer, se for de uma escala publicada, se o profissinal logado estiver vinculado à instituição e se na lista de especialidades do profissional tiver uma especialidade do setor do plantão. |
| **RN63** | Um profissional pode ser alocado a um plantão se: estiver vinculado a instituição do plantão e na listagem de especialidades tiver a especialidade do setor do plantão. |