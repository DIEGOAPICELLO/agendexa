
# 🗓️ AgendaPro - Plataforma de Agendamento como Serviço (SaaS)

API backend desenvolvida com Java + Spring Boot, voltada para sistemas de agendamento em clínicas e consultórios. Projeto robusto, seguro e escalável, com estrutura preparada para múltiplos clientes (multi-tenant).

---

## ✨ Funcionalidades
- ✅ Autenticação com JWT
- ✅ Registro e login de usuários (ADMIN, SPECIALIST, PATIENT)
- ✅ Criação e listagem de agendamentos
- ✅ Validação de conflitos de horário
- ✅ Documentação via Swagger UI

---

## 🛠️ Tecnologias Utilizadas
- **Java 17**, **Spring Boot 3**
- Spring Security, JWT, Spring Data JPA
- PostgreSQL 15+
- Springdoc OpenAPI (Swagger)
- Lombok, Maven
- Docker & Docker Compose

---

## 🗂️ Estrutura do Projeto

```
src/main/java/br/com/seusistema/agendamento/
├── config/        # Configurações do Spring
├── controller/    # Camada REST (entrada)
├── dto/           # Objetos de transferência de dados
├── entity/        # Entidades JPA
├── repository/    # Interfaces com o banco (JPA)
├── service/       # Regras de negócio
├── security/      # Segurança com JWT e filtros
```

---

## 🚀 Como Executar o Projeto

### ✅ Requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 15+
- Docker (opcional)

### ▶️ Execução Local

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/agendamento-api.git
cd agendamento-api
```

2. Configure o banco no `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agendamento_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute:
```bash
mvn spring-boot:run
```

### 🐳 Execução com Docker
```bash
docker-compose up --build
```

Acesse: [http://localhost:8080](http://localhost:8080)

---

## 🔌 Endpoints Principais

| Método | Endpoint                                | Protegido | Descrição                       |
|--------|-----------------------------------------|-----------|---------------------------------|
| POST   | `/api/auth/register`                    | ❌        | Registro de usuário             |
| POST   | `/api/auth/login`                       | ❌        | Login e geração do token JWT    |
| POST   | `/api/appointments`                     | ✅        | Criar novo agendamento          |
| GET    | `/api/appointments/my-appointments`     | ✅        | Listar agendamentos do usuário  |

📄 Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔐 Autenticação JWT
Após o login, um token JWT é gerado. Para acessar endpoints protegidos, envie no header:

```
Authorization: Bearer <seu_token>
```

---

## 🗺️ Roadmap
- [ ] Cancelamento de agendamentos
- [ ] Multi-Tenant (schema por cliente)
- [ ] Interface com React
- [ ] Endpoints administrativos
- [ ] Testes automatizados e CI/CD

---

## 🤝 Contribuindo

1. Faça um fork
2. Crie uma branch: `git checkout -b feature/NovaFuncionalidade`
3. Commit: `git commit -m "Adiciona nova funcionalidade"`
4. Push: `git push origin feature/NovaFuncionalidade`
5. Abra um Pull Request

---

Feito com 💙 por [Seu Nome ou GitHub](https://github.com/seu-usuario)
