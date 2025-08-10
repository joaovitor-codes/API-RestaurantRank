# API-RestaurantRank
Sobre o Projeto:
Esta é uma API RESTful desenvolvida com Spring Boot, projetada para gerenciar um sistema de rank de restaurantes. A aplicação permite que usuários criem contas, adicionem restaurantes, e postem reviews e avaliações. O projeto inclui funcionalidades de CRUD, paginação e uma arquitetura orientada a eventos usando o padrão Observer para atualizações em tempo real.

Funcionalidades Principais:
Gerenciamento de Usuários: Endpoints completos para criar, ler, atualizar e deletar (CRUD) usuários.
Gerenciamento de Restaurantes: Endpoints para gerenciar restaurantes, incluindo a listagem por paginação.
Reviews e Avaliações: Funcionalidade para usuários darem notas e escreverem reviews para restaurantes.
Padrão Observer: Um sistema para notificar entidades (como talvez a média de um restaurante) quando novas reviews são adicionadas.
Paginação: Listagem de recursos com suporte a paginação para otimizar o desempenho.
Validação de Dados: Uso de anotações como @Valid, @NotBlank e @Email para garantir a integridade dos dados.

Tecnologias Utilizadas:
Linguagem: Java 17
Framework: Spring Boot 3.x
Persistência: Spring Data JPA
Banco de Dados: H2 (para ambiente de desenvolvimento) e PostgreSQL (recomendado para produção)
Build Tool: Maven
Documentação: Springdoc-OpenAPI (Swagger UI)
Outros: Lombok para reduzir código boilerplate

Como Rodar o Projeto:
Siga estes passos para configurar e rodar a aplicação em sua máquina local.

Pré-requisitos
Certifique-se de ter os seguintes softwares instalados:
Java JDK 17 ou superior
Maven 3.x

Uma IDE (IntelliJ IDEA, VS Code, Eclipse, etc.)

Configuração
Clone o repositório:
git clone https://github.com/seu-usuario/seu-repositorio.git

cd seu-repositorio
Configure o banco de dados no arquivo src/main/resources/application.properties. Por padrão, o projeto usa o banco de dados em memória H2. Se desejar usar PostgreSQL, atualize as configurações:

spring.datasource.url=jdbc:postgresql://localhost:5432/nomedobanco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

Execute a aplicação usando Maven:
mvn spring-boot:run

A API estará disponível em http://localhost:8080.

Documentação da API (Swagger)
Após iniciar a aplicação, você pode acessar a documentação completa da API através do Swagger UI no seu navegador:

http://localhost:8080/swagger-ui.html

Endpoints Principais
A seguir, estão alguns dos principais endpoints da API, com base nos exemplos discutidos:

Usuários (/users)
GET /users?page=0&size=10: Lista todos os usuários de forma paginada.
GET /users/{id}: Busca um usuário pelo ID.
POST /users: Cria um novo usuário.
PUT /users/{id}: Atualiza um usuário existente (requer o objeto completo).
DELETE /users/{id}: Deleta um usuário.
