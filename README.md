# AcquitFlow - HR Payroll Management System

> A comprehensive HR payroll management system for automating salary acquittance generation, employee management, and audit-trail tracking.

AcquitFlow is a full-stack web application designed for organizations to efficiently manage employee payroll, generate compliant salary acquittance reports, and maintain secure records with complete audit trails using **Spring Boot AOP** for transaction logging.

---

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Setup Instructions](#-setup-instructions)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Deployment](#-deployment)
- [Future Improvements](#-future-improvements)

---

## ✨ Features

### Core HR Functionality
- **Employee Management**
  - CRUD operations for employee profiles (name, department, designation, salary components)
  - Role-based employee classification (full-time, part-time, contract)
  - Department and designation management

- **Salary Management**
  - Configurable salary components (basic, HRA, DA, allowances, deductions)
  - Automated salary calculations based on employee records
  - Support for multiple pay periods (monthly, quarterly)

- **Acquittance Report Generation**
  - Automated generation of salary acquittance reports for any pay period
  - Professional PDF export for compliance documentation
  - Excel export for accounting integration
  - Customizable report templates

- **Audit & Compliance**
  - Complete audit trail of all payroll transactions using **Spring Boot AOP**
  - Logging format: `<user> downloaded salary acquittance for <employee> on <date>`
  - Admin-accessible audit logs for compliance and dispute resolution
  - Transaction timestamp tracking for regulatory requirements

- **Search & Filtering**
  - Find reports by employee name, department, or date range
  - Quick access to historical payroll records
  - Employee-wise salary history

---

## 🏗️ System Architecture

### High-Level Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Frontend (React)                      │
│         (Vite + Tailwind CSS - Production Ready)        │
└────────────────────────┬────────────────────────────────┘
                         │
                    HTTP / REST
                         │
┌────────────────────────▼────────────────────────────────┐
│              Spring Boot REST API                        │
├─────────────────────────────────────────────────────────┤
│  Controllers (Employee, Salary, Acquittance, Audit)    │
│  Services (Business Logic)                              │
│  Spring Boot AOP (Audit Logging)                        │
│  Authentication & Authorization Layer                   │
└────────────────────────┬────────────────────────────────┘
                         │
                    JDBC / JPA
                         │
┌────────────────────────▼────────────────────────────────┐
│           PostgreSQL Database                           │
│  (Employees, Salary Components, Transactions, Logs)    │
└─────────────────────────────────────────────────────────┘
```

### Data Flow for Acquittance Generation

```
1. Admin selects pay period
   ↓
2. System retrieves employee data from DB
   ↓
3. Calculate salary (basic + components - deductions)
   ↓
4. Generate acquittance report (PDF/Excel)
   ↓
5. Log action via AOP: "<user> generated acquittance for <employee>"
   ↓
6. Return report to user
```

---

## 💻 Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Frontend** | React 18+ | Dynamic UI, real-time form handling |
| | Vite | Fast build tool & dev server |
| | Tailwind CSS | Responsive styling |
| | Axios | HTTP client for API calls |
| **Backend** | Spring Boot 3.x | REST API, business logic |
| | Spring Data JPA | ORM for database operations |
| | Spring AOP | Audit logging (Before/After annotations) |
| | JWT Auth | Secure user authentication |
| **Database** | PostgreSQL 14+ | Relational data persistence |
| **Deployment** | Docker | Containerization |
| | Render / AWS | Cloud hosting |

---

## 📋 Prerequisites

- **Java 17+** (for Spring Boot)
- **Node.js 16+** (for React frontend)
- **PostgreSQL 14+** (database)
- **Maven 3.8+** (for building Java project)
- **Git** (for cloning repository)

---

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Sephyroth1/SalaryAcquittance.git
cd SalaryAcquittance
```

### 2. Backend Setup (Spring Boot)

```bash
cd server/salaryacquittanceapi

# Install dependencies
./mvnw clean install

# Configure application.properties (database credentials)
# Edit src/main/resources/application.properties
# Set:
# spring.datasource.url=jdbc:postgresql://localhost:5432/acquitflow
# spring.datasource.username=<your_username>
# spring.datasource.password=<your_password>

# Run the application
./mvnw spring-boot:run
```

Backend will run on: **http://localhost:8080**

### 3. Frontend Setup (React)

```bash
cd ../../client

# Install dependencies
npm install

# Create .env file
echo "VITE_API_URL=http://localhost:8080/api" > .env

# Run development server
npm run dev
```

Frontend will run on: **http://localhost:5173**

### 4. Database Setup

```bash
# Create PostgreSQL database
createdb acquitflow

# Run migrations (if present)
# SQL schema will be auto-generated by Spring Boot JPA
```

---

## 📡 API Documentation

### Authentication
```
POST /api/auth/login
  Body: { "username": "admin", "password": "password" }
  Returns: { "token": "jwt_token", "userId": 1 }
```

### Employee Endpoints
```
GET    /api/employees              - List all employees
POST   /api/employees              - Create new employee
GET    /api/employees/{id}         - Get employee details
PUT    /api/employees/{id}         - Update employee
DELETE /api/employees/{id}         - Delete employee
```

### Salary Endpoints
```
POST   /api/salary/calculate       - Calculate salary for period
GET    /api/salary/history/{employeeId} - Get salary history
```

### Acquittance Endpoints
```
POST   /api/acquittance/generate   - Generate report
  Body: { "employeeId": 1, "month": "2024-01", "format": "pdf" }
  Returns: PDF file

GET    /api/acquittance/list       - List all reports
GET    /api/acquittance/{id}       - Get specific report
```

### Audit Log Endpoints (Admin Only)
```
GET    /api/audit/logs             - View all audit logs
GET    /api/audit/logs/{userId}    - View user-specific logs
```

**Note:** Audit logs are automatically generated via Spring AOP @Before and @After annotations on controller methods.

---

## 🗄️ Database Schema

### Key Tables

**employees**
```sql
├── id (PK)
├── name VARCHAR
├── email VARCHAR (UNIQUE)
├── department VARCHAR
├── designation VARCHAR
├── salary_component_id (FK)
├── role ENUM (EMPLOYEE, ADMIN)
├── created_at TIMESTAMP
└── updated_at TIMESTAMP
```

**salary_components**
```sql
├── id (PK)
├── employee_id (FK)
├── basic_salary DECIMAL
├── hra DECIMAL
├── da DECIMAL
├── other_allowances DECIMAL
├── deductions DECIMAL
└── effective_from DATE
```

**acquittance_reports**
```sql
├── id (PK)
├── employee_id (FK)
├── pay_period VARCHAR (2024-01)
├── gross_salary DECIMAL
├── net_salary DECIMAL
├── generated_at TIMESTAMP
├── generated_by (FK to users)
└── file_path VARCHAR
```

**audit_logs** (Auto-generated by AOP)
```sql
├── id (PK)
├── user_id (FK)
├── action VARCHAR ("downloaded salary acquittance", etc.)
├── employee_id (FK)
├── timestamp TIMESTAMP
└── details JSON
```

---

## 🔐 Security Features

- **JWT-based Authentication**: Secure token-based user sessions
- **Role-Based Access Control (RBAC)**: Employees can only view their own data; admins have full access
- **Spring AOP Audit Logging**: Every sensitive action (downloads, calculations) is logged with user, timestamp, and details
- **CORS Configuration**: Properly configured for production deployment
- **Input Validation**: All API inputs validated on backend
- **SQL Injection Prevention**: JPA parameterized queries

---

## 📦 Deployment

### Using Docker

```bash
# Build Docker image
docker build -t acquitflow:latest .

# Run container
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://db:5432/acquitflow \
  -e DB_USER=postgres \
  -e DB_PASSWORD=password \
  acquitflow:latest
```

### Deploy to Render

1. Create a new Web Service on Render
2. Connect your GitHub repository
3. Set environment variables (database URL, credentials)
4. Deploy

### Deploy Frontend to Vercel

```bash
npm install -g vercel
vercel deploy
```

---

## 🔄 Current Status

- ✅ **Employee Management**: Full CRUD operations
- ✅ **Salary Calculations**: Automated based on components
- ✅ **Report Generation**: PDF/Excel export
- ✅ **Audit Logging**: Spring AOP implementation
- 🔄 **Authentication**: Basic implementation (to be refactored with JWT + role-based access)
- 🔄 **CORS Configuration**: Fix for production (currently causing issues)
- 🔄 **Error Handling**: Comprehensive error responses (in progress)
- 🔄 **Unit Tests**: Improve coverage (target: 70%+)

---

## 🛠️ Future Improvements

1. **Payment Integration**
   - Razorpay/Stripe integration for salary disbursement
   - Transaction tracking and reconciliation

2. **Advanced Features**
   - Multi-currency support
   - Tax calculations (TDS, income tax)
   - Attendance-based salary deductions
   - Leave management integration

3. **Scalability**
   - Caching layer (Redis) for frequently accessed data
   - Database indexing optimization
   - Async processing for bulk report generation

4. **Analytics & Reporting**
   - Dashboard with payroll metrics
   - Year-end tax summaries
   - Departmental salary analytics

5. **Compliance**
   - E-signature support for acquittance reports
   - Compliance audit reports for statutory requirements
   - GDPR-compliant data export/deletion

---

## 📚 Learning & Architecture Decisions

### Why Spring Boot AOP for Audit Logging?
- **Separation of Concerns**: Audit logic decoupled from business logic
- **Reusability**: Single @Aspect can log multiple controller methods
- **Maintainability**: Easy to add/remove logging without changing core code
- **Performance**: Aspect weaving only for marked methods

### Example AOP Implementation
```java
@Aspect
@Component
public class AuditLoggingAspect {
    
    @Before("@annotation(com.acquitflow.annotations.Auditable)")
    public void auditBefore(JoinPoint joinPoint) {
        // Log before action
    }
    
    @AfterReturning("@annotation(com.acquitflow.annotations.Auditable)")
    public void auditAfter(JoinPoint joinPoint) {
        // Log after successful action
    }
}
```

---

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

---

## 📞 Contact & Support

For issues, questions, or suggestions, please open a GitHub issue or contact the maintainer.

**GitHub**: [Sephyroth1](https://github.com/Sephyroth1)

---

## 🙏 Acknowledgments

Built during an internal college-level hackathon. This project demonstrates:
- Full-stack web development (Spring Boot + React)
- Enterprise architecture patterns (AOP, RBAC)
- Database design for payroll systems
- Audit and compliance mechanisms
