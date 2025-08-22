# 🍔 API Restaurant Rank

API RESTful para classificar e rankear restaurantes com base em avaliações de usuários. O projeto foi desenvolvido com foco em boas práticas de engenharia de software, utilizando padrões de design e arquitetura modular para garantir um código limpo.

---

### 💻 Tecnologias Utilizadas

* **Java 17**: Linguagem de programação principal.
* **Spring Boot**: Framework para o desenvolvimento rápido da API.
* **Spring Data JPA**: Para a persistência de dados e interação com o banco de dados.
* **Banco de Dados**: H2 apenas para testes.
* **Maven**: Gerenciador de dependências.

---

### 🚀 Arquitetura e Padrões de Design

A arquitetura do projeto foi desenhada para seguir os princípios **SOLID**, com destaque para:

* **Princípio da Responsabilidade Única (SRP)**: Cada classe tem uma única e bem definida responsabilidade. Por exemplo, a classe `ReviewServiceImpl` é responsável apenas por gerenciar as avaliações (CRUD), enquanto o `NotifyRestaurantService` é responsável por orquestrar a notificação do padrão `Observer`.
* **Princípio Aberto/Fechado (OCP)**: O sistema é aberto para extensão e fechado para modificação. Novas estratégias de cálculo de tags podem ser adicionadas criando-se novas classes que implementam a interface `RestaurantTagStrategy`, sem a necessidade de alterar o código existente.

Os seguintes padrões de design foram aplicados:

* **Padrão Observer**: Usado para atualizar automaticamente a tag (rank) de um restaurante sempre que uma nova avaliação é criada, excluída ou atualizada. O `RestaurantEntity` atua como o **Subject**, e o `TagUpdaterObserver` como o **Observer**.
* **Padrão Strategy**: Utilizado para encapsular a lógica de cálculo da tag do restaurante. A interface `RestaurantTagStrategy` permite que o sistema utilize diferentes algoritmos de cálculo de forma dinâmica, como a `AverageTagStrategy` (cálculo por média simples).
---

### 📋 Funcionalidades da API

A API oferece os seguintes endpoints para gerenciamento de usuários, restaurantes e avaliações:

#### **Gerenciamento de Usuários**

* `POST /users`: Cria um novo usuário.
* `GET /users/page/{page}`: Retorna uma lista paginada de todos os usuários.
* `GET /users/{id}`: Retorna um usuário específico pelo ID.
* `PUT /users/{id}`: Atualiza um usuário existente (completo).
* `PATCH /users/{id}`: Atualiza parcialmente um usuário.
* `DELETE /users/{id}`: Exclui um usuário.

#### **Gerenciamento de Restaurantes**

* `GET /restaurants`: Lista todos os restaurantes.
* `POST /restaurants`: Cria um novo restaurante.
* `GET /restaurants/{id}`: Busca um restaurante por ID.
* `PUT /restaurants/{id}`: Atualiza um restaurante.
* `DELETE /restaurants/{id}`: Exclui um restaurante.

#### **Gerenciamento de Avaliações (Reviews)**

* `POST /reviews`: Cria uma nova avaliação para um restaurante.
* `GET /reviews`: Lista todas as avaliações.
* `GET /reviews/{id}`: Busca uma avaliação por ID.
* `GET /reviews/restaurant/{id}`: Busca avaliações de um restaurante específico.
* `PUT /reviews/{id}`: Atualiza uma avaliação.
* `DELETE /reviews/{id}`: Exclui uma avaliação.

---

### ⚙️ Instalação e Execução

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/joaovitor-codes/API-RestaurantRank.git
    cd API-RestaurantRank
    ```

A aplicação será iniciada em `http://localhost:8080`.
