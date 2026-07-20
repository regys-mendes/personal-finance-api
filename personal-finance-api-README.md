# Personal Finance API

> API REST para controle financeiro pessoal — gerenciamento de receitas e despesas com cálculo de saldo, validação de dados e documentação interativa. Desenvolvida com Spring Boot, JPA e PostgreSQL.

---

## 🔗 Live Demo

**Swagger UI (produção):**
[https://personal-finance-api-production-8f0b.up.railway.app/swagger-ui.html](https://personal-finance-api-production-8f0b.up.railway.app/swagger-ui.html)

A API está hospedada na Railway com banco PostgreSQL em produção — teste os endpoints diretamente no navegador, sem precisar instalar nada.

---

## 🚀 Technologies

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Bean Validation (Jakarta Validation)**
- **Springdoc OpenAPI (Swagger UI)**
- **Maven**
- **Deploy: Railway**

---

## 📋 Features

- CRUD completo de transações financeiras (receitas e despesas)
- Filtro de transações por tipo (RECEITA / DESPESA)
- **Cálculo de saldo total** — regra de negócio real, não apenas CRUD
- Validação de entrada com respostas de erro estruturadas por campo (`@NotBlank`, `@Positive`)
- Tratamento global de exceções com respostas HTTP padronizadas (404, 400)
- Documentação interativa via Swagger/OpenAPI
- Deploy em produção na Railway com PostgreSQL gerenciado

---

## 🏗️ Project Structure

```
src/main/java/com/regysmendes/personalfinance/
├── entities/
│   ├── Transaction.java
│   └── TransactionType.java
├── repository/
│   └── TransactionRepository.java
├── services/
│   └── TransactionService.java
├── resources/
│   └── TransactionResource.java
└── exceptions/
    ├── ObjectNotFoundException.java
    ├── StandardError.java
    ├── ValidationError.java
    ├── FieldMessage.java
    └── ResourceExceptionHandler.java
```

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/transactions` | List all transactions |
| GET | `/transactions/{id}` | Find transaction by ID |
| GET | `/transactions/type/{type}` | Filter transactions by type (RECEITA/DESPESA) |
| GET | `/transactions/balance` | Get total balance (income − expenses) |
| POST | `/transactions` | Create a new transaction |
| PUT | `/transactions/{id}` | Update a transaction |
| DELETE | `/transactions/{id}` | Delete a transaction |

---

## 📦 Request Examples

**Create a transaction**
```json
POST /transactions
{
  "description": "Salário mensal",
  "value": 3500.00,
  "date": "2026-07-20",
  "transactionType": "RECEITA"
}
```

**Validation error response**
```json
{
  "status": 400,
  "error": "Validation error",
  "errors": [
    { "fieldName": "description", "message": "não deve estar em branco" },
    { "fieldName": "value", "message": "deve ser maior que 0" }
  ]
}
```

**Get balance**
```
GET /transactions/balance
→ 1900.00
```

---

## ⚙️ How to Run Locally

**Prerequisites:** Java 21+, PostgreSQL, Maven

1. Clone the repository
```bash
git clone https://github.com/regys-mendes/personal-finance-api.git
```

2. Create a PostgreSQL database named `financedb`

3. Configure `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financedb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

4. Run the project
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`, with Swagger UI at `http://localhost:8080/swagger-ui.html`

---

## 👨‍💻 Author

**Regys Mendes**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/regys-mendes-10a87123a/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/regys-mendes)
