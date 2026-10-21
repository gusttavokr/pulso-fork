# Pulso: Plantões Unificados para Logística de Saúde Organizada
<img width="4096" height="1027" alt="Banner" src="https://github.com/user-attachments/assets/4506b9f2-8028-4565-a84b-f72772e88c77" />

<br>

## Sobre o Projeto

O **Pulso** é um sistema web desenvolvido para **gestão e organização de escalas de plantões** no setor público de saúde.  
Seu propósito é **centralizar, simplificar e otimizar** a rotina de gestores e profissionais, garantindo:

- Eficiência e confiabilidade na comunicação entre equipes.  
- Controle unificado de horários e trocas de plantão.  
- Interface moderna, intuitiva e responsiva.  

O Pulso busca ser uma **plataforma inteligente** que conecte pessoas, processos e informações — tudo em um só lugar.

## Equipe de Desenvolvimento

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/f5e0366b-bc5e-432d-811d-3645e25ad7a6" width="120"/><br>
      <a href="https://github.com/ArrudaAsaph"><b>Asaph Arruda</b></a><br>
       Desenvolvedor
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/a3facdf3-862c-49a6-b561-91e73b0ef68d" width="120"/><br>
      <a href="https://github.com/FlavioMatias"><b>Flavio Matias</b></a><br>
       Desenvolvedor
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/31d8fd8f-698d-4430-99be-64a4f83e86af" width="120"/><br>
      <a href="https://github.com/gusttavokr"><b>Gustavo Maia</b></a><br>
      Desenvolvedor
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/27ee87e1-a16f-49a3-b518-c55652cad297" width="120"/><br>
      <a href="https://github.com/IcaroGabrielIcaro"><b>Icaro Gabriel</b></a><br>
      Desenvolvedor
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/5806984b-de88-4e43-8e65-6e87da3ad227" width="120"/><br>      
      <a href="https://github.com/Alencast"><b>Robson Alencastro</b></a><br>
      Desenvolvedor
    </td>
  </tr>
</table>

## Horários de Reuniões

- **Dias:** Terças-feiras  
- **Horário:** 9h e 18h30 às 20h  
- **Local:** Canais de voz via Discord ou presencialmente no IFRN - Campus Central

## Documentação

- [Documentação Geral](https://github.com/tads-cnat/Pulso/tree/main/doc)   
- [Documento de Visão](https://github.com/tads-cnat/Pulso/blob/main/doc/visao/doc-visao.md)  
- [Casos de Uso (geral)](https://github.com/tads-cnat/Pulso/blob/main/doc/cdu/cdu.md)

## Casos de Uso Principais

- [Gerenciar Escala](https://github.com/tads-cnat/Pulso/blob/main/doc/cdu/cdu001_Gerenciar_escala/detalhamento.md)  
- [Publicar Escala](https://github.com/tads-cnat/Pulso/blob/main/doc/cdu/cdu003_Publicar_escala/detalhamento.md)  
- [Consultar Plantões Abertos](https://github.com/tads-cnat/Pulso/blob/main/doc/cdu/cdu009_Consultar_plant%C3%B5es_abertos/detalhamento.md)  
- [Pegar Plantão](https://github.com/tads-cnat/Pulso/blob/main/doc/cdu/cdu008_Pegar_plant%C3%A3o/detalhamento.md)

## Como Rodar o Projeto

[Manual de Execução do Projeto](doc/manual/manual.md)

## Entragas PDS Distribuído
Aqui são descrevidos os principais artefatos e implementações desenvolvidas no projeto, evidenciando as decisões arquiteturais, padrões adotados e funcionalidades construídas.

### Documento Arquitetural
O material arquitetural do sistema contém os seguintes diagramas:

- **Diagrama de Contexto**: visão macro do sistema e sua interação com atores e sistemas externos
- **Diagrama de Containers**: divisão da aplicação em partes executáveis (frontend, backend, banco de dados, APIs externas, etc.)
- **Diagrama de Componentes**: organização interna dos módulos e responsabilidades dentro do backend

Esses diagramas apresentam a estrutura geral da solução e a forma como os elementos do sistema se relacionam, encontrando-se disponíveis [aqui](/doc/componentes/componentes.md)

### Padrão de Projeto Implementado
Foi utilizado e implementado o padrão de projeto Builder, com o objetivo de facilitar a criação de objetos complexos de forma organizada e legível.

Implementação disponível [aqui](/backend/pulso/src/main/java/web/pulso/utils/EscalaBuilder.java)

O padrão é utilizado principalmente na construção de objetos relacionados à entidade Escala, centralizando a lógica de montagem e melhorando a manutenção do código.

### Uso de Consultas Complexas
O sistema faz uso de consultas que envolvem múltiplas tabelas para obter dados consolidados e prontos para exibição nas telas da aplicação.

Essas consultas são utilizadas para montar visões específicas e mais completas das entidades do sistema.

Repositórios com consultas complexas:
- [Escalas](/backend/pulso/src/main/java/web/pulso/repository/EscalaRepository.java)
- [Plantões](/backend/pulso/src/main/java/web/pulso/repository/PlantaoRepository.java)
- [Profissionais](/backend/pulso/src/main/java/web/pulso/repository/ProfissionalRepository.java)

### Uso de Filtragem de Dados
O projeto aplica diferentes estratégias de filtragem de dados conforme o contexto da regra de negócio.

#### Filtragem com Streams
Filtros aplicados diretamente em coleções Java utilizando Streams, normalmente após a obtenção dos dados do banco.

Utilizado em:
- [Escalas](/backend/pulso/src/main/java/web/pulso/service/EscalaService.java)
- [Plantões](/backend/pulso/src/main/java/web/pulso/service/PlantaoService.java)

#### Filtragem com Specification
Uso de Spring Data JPA Specification para construção de filtros dinâmicos diretamente na camada de persistência.

Utilizado em:
- [Instiuições](/backend/pulso/src/main/java/web/pulso/service/InstituicaoService.java)
- [Usuários](/backend/pulso/src/main/java/web/pulso/service/UsuarioService.java)

### Acesso à API Externa
O sistema possui integração com a API do Google Calendar, permitindo sincronização de informações relacionadas a escalas e plantões.

Implementação disponível [aqui](/backend/pulso/src/main/java/web/pulso/service/GoogleCalendarService.java)

Essa integração possibilita que eventos importantes do sistema, como o ato de pegar um plantão, também sejam refletidos no calendário do usuário.

### Documentação da API
A documentação interativa da API está disponível via Swagger no seguinte endereço: `http://localhost:8080/swagger-ui/index.html`

Por meio dessa interface é possível visualizar e testar os endpoints disponíveis no backend.

### Casos de Uso Implementados (Frontend)
As telas correspondentes aos casos de uso foram implementadas no frontend da aplicação.

Localização: [aqui](/frontend/pulso/src/app/pages)

Essas páginas permitem a interação do usuário com as principais funcionalidades do sistema.

### Detalhamento dos Casos de Uso e Regras de Negócio
A documentação funcional do sistema, com detalhamento dos fluxos e regras aplicadas, está organizada nos seguintes documentos:

#### Casos de Uso
- [Gerenciar Escala](/doc/cdu/cdu001_Gerenciar_escala/detalhamento.md)  
- [Publicar Escala](/doc/cdu/cdu003_Publicar_escala/detalhamento.md)  
- [Consultar Plantões Abertos](/doc/cdu/cdu009_Consultar_plant%C3%B5es_abertos/detalhamento.md)  
- [Pegar Plantão](/doc/cdu/cdu008_Pegar_plant%C3%A3o/detalhamento.md)

#### Regras de Negócio
As regras gerais que governam o comportamento do sistema estão documentadas [aqui](/doc/regra-de-negocio/regra-negocio.md)

Esse material descreve restrições, validações e condições que afetam o funcionamento das funcionalidades principais.
