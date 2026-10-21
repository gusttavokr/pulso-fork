# 🚀 Subindo o Projeto

### 1️⃣ Pré-requisito

Tenha [**Docker**](https://www.docker.com/products/docker-desktop/) e **Docker Compose** instalados.

### 2️⃣ Subir tudo de uma vez

```bash
docker compose up
```

### 3️⃣ Subir apenas um serviço específico

Escolha os que deseja subir:

* `pulso_web` → Frontend Angular
* `pulso_api` → Backend Spring Boot
* `pulso_db` → Banco de dados PostgreSQL

```bash
docker compose up pulso_web
docker compose up pulso_api pulso_db
```

### 4️⃣ URLs de acesso

* **Frontend Angular:** [http://localhost:4200](http://localhost:4200)
* **Backend API:** [http://localhost:8080](http://localhost:8080)
* **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
