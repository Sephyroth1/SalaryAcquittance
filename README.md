# Veritas

> **Enterprise Payroll & Compliance Platform with Audit Intelligence**

Veritas is a full-stack payroll and compliance management system designed to help organizations automate employee payroll processing, generate salary acquittance reports, and maintain complete audit trails for accountability and regulatory compliance.

Built with **Spring Boot**, **React**, and **PostgreSQL**, Veritas focuses on enterprise-grade architecture principles such as **separation of concerns**, **role-based access control**, **audit logging through AOP**, and **secure authentication**.

---

## Table of Contents

- [Overview](#overview)
- [Core Features](#core-features)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Authentication & Authorization](#authentication--authorization)
- [Audit Logging](#audit-logging)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Docker Setup](#docker-setup)
- [Testing Strategy](#testing-strategy)
- [Deployment](#deployment)
- [Current Development Status](#current-development-status)
- [Future Enhancements](#future-enhancements)
- [Architecture Decisions](#architecture-decisions)
- [Contributing](#contributing)
- [License](#license)

---

# Overview

Veritas is designed to streamline payroll operations while ensuring transparency and compliance.

The system enables administrators and HR personnel to:

- Manage employee records
- Configure salary structures
- Process payroll efficiently
- Generate salary acquittance reports
- Maintain immutable audit logs
- Enforce secure access using role-based permissions

Unlike traditional payroll systems focused solely on calculations, Veritas emphasizes **traceability**, ensuring every sensitive action can be attributed to a specific user at a specific time.

---

# Core Features

## Employee Management

- Create employee profiles
- Update employee information
- View employee details
- Delete employee records
- Search employees by department, designation, or name
- Maintain employee salary history

---

## Payroll Management

- Automated salary processing
- Configurable salary structures
- Gross salary calculations
- Deduction handling
- Net salary computation
- Historical payroll records

---

## Salary Components

Supports configurable salary components including:

- Basic Salary
- House Rent Allowance (HRA)
- Dearness Allowance (DA)
- Special Allowances
- Bonuses
- Deductions
- Other Compensation Components

---

## Acquittance Report Generation

Generate professional payroll reports in multiple formats:

- PDF Reports
- Excel Exports
- Historical Report Retrieval
- Employee-Specific Reports
- Monthly Payroll Statements

---

## Authentication & Security

- JWT Authentication
- Password Encryption using BCrypt
- Secure Session Management
- Role-Based Access Control (RBAC)
- Endpoint Protection
- Input Validation

---

## Audit & Compliance

Comprehensive audit tracking using Spring AOP.

Captures:

- User performing the action
- Timestamp of the event
- Entity affected
- Action executed
- Previous values (where applicable)
- Updated values

Example:

```text
HR_MANAGER updated salary details for Employee #105

Before:
Basic Salary: ₹45,000

After:
Basic Salary: ₹50,000

Timestamp:
2026-06-13 10:42:18
```

---

# System Architecture

```text
                           ┌────────────────────┐
                           │     React UI       │
                           │ (Vite + Tailwind)  │
                           └─────────┬──────────┘
                                     │
                               REST API Calls
                                     │
                           ┌─────────▼──────────┐
                           │   Spring Boot API  │
                           └─────────┬──────────┘
                                     │
      ┌──────────────────────────────┼──────────────────────────────┐
      │                              │                              │
┌─────▼─────┐               ┌────────▼───────┐             ┌────────▼──────┐
│ Security  │               │ Business Logic │             │ Audit Logging │
│   Layer   │               │    Services    │             │   (Spring AOP)│
└─────┬─────┘               └────────┬───────┘             └────────┬──────┘
      │                               │                              │
      └───────────────────────────────┼──────────────────────────────┘
                                      │
                               ┌──────▼──────┐
                               │ PostgreSQL  │
                               │  Database   │
                               └─────────────┘
```

---

# Technology Stack

| Layer | Technology |
|---------|------------|
| Frontend | React 18 |
| Frontend Build Tool | Vite |
| Frontend Styling | Tailwind CSS |
| HTTP Client | Axios |
| Backend | Spring Boot 3 |
| Security | Spring Security |
| Authentication | JWT |
| ORM | Spring Data JPA |
| Database | PostgreSQL |
| Audit Logging | Spring AOP |
| Validation | Jakarta Validation |
| Build Tool | Maven |
| Testing | JUnit 5 |
| Mocking | Mockito |
| Integration Testing | Testcontainers |
| PDF Generation | OpenPDF |
| Excel Export | Apache POI |
| Containerization | Docker |
| Frontend Deployment | Vercel |
| Backend Deployment | Render / Railway / AWS |

---

# Project Structure

```text
veritas/
│
├── backend/
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── security/
│   │   ├── audit/
│   │   ├── exception/
│   │   ├── config/
│   │   └── util/
│   │
│   ├── src/main/resources/
│   │   └── application.properties
│   │
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── hooks/
│   │   ├── context/
│   │   ├── services/
│   │   └── utils/
│   │
│   └── package.json
│
├── docker-compose.yml
├── README.md
└── LICENSE
```

---

# Database Design

## Users

```text
users
------
id
username
email
password
role
enabled
created_at
updated_at
```

---

## Employees

```text
employees
-----------
id
employee_code
first_name
last_name
email
department
designation
employment_type
date_of_joining
created_at
updated_at
```

---

## Salary Components

```text
salary_components
-----------------
id
employee_id
basic_salary
hra
da
special_allowances
deductions
effective_from
created_at
updated_at
```

---

## Payroll Transactions

```text
payroll_transactions
--------------------
id
employee_id
pay_period
gross_salary
total_deductions
net_salary
processed_by
processed_at
status
```

---

## Acquittance Reports

```text
acquittance_reports
-------------------
id
employee_id
payroll_transaction_id
report_type
generated_by
generated_at
file_path
```

---

## Audit Logs

```text
audit_logs
-----------
id
user_id
action
entity_name
entity_id
old_value
new_value
timestamp
details
```

---

# Authentication & Authorization

Veritas implements JWT-based authentication with Role-Based Access Control.

Supported roles:

```text
ADMIN
HR_MANAGER
EMPLOYEE
AUDITOR
```

Permissions include:

| Feature | ADMIN | HR_MANAGER | EMPLOYEE | AUDITOR |
|----------|--------|-------------|-----------|----------|
| Employee Management | ✓ | ✓ | ✗ | ✗ |
| Payroll Processing | ✓ | ✓ | ✗ | ✗ |
| Report Generation | ✓ | ✓ | ✓ | ✗ |
| Audit Log Access | ✓ | ✗ | ✗ | ✓ |
| User Management | ✓ | ✗ | ✗ | ✗ |

---

# Audit Logging

Audit functionality is implemented using Spring AOP.

Example annotation:

```java
@Auditable(action = "PROCESS_PAYROLL")
public void processPayroll(...) {
    ...
}
```

Aspect responsibilities:

- Capture execution metadata
- Persist audit information
- Minimize intrusion into business logic
- Ensure maintainability

---

# API Endpoints

## Authentication

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
```

---

## Employees

```http
GET    /api/employees
GET    /api/employees/{id}
POST   /api/employees
PUT    /api/employees/{id}
DELETE /api/employees/{id}
```

---

## Payroll

```http
POST /api/payroll/process
GET  /api/payroll/history/{employeeId}
GET  /api/payroll/{id}
```

---

## Reports

```http
POST /api/reports/generate
GET  /api/reports/download/{id}
GET  /api/reports/history
```

---

## Audit Logs

```http
GET /api/audit/logs
GET /api/audit/logs/{userId}
```

---

# Getting Started

## Prerequisites

Install the following tools:

- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Maven 3.8+
- Docker (Optional)
- Git

---

# Backend Setup

Clone the repository:

```bash
git clone https://github.com/yourusername/veritas.git

cd veritas/backend
```

Install dependencies:

```bash
./mvnw clean install
```

Configure environment variables:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/veritas
spring.datasource.username=postgres
spring.datasource.password=password

jwt.secret=your-secret-key
jwt.expiration=86400000
```

Run the application:

```bash
./mvnw spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

# Frontend Setup

Navigate to frontend:

```bash
cd ../frontend
```

Install dependencies:

```bash
npm install
```

Create environment file:

```env
VITE_API_URL=http://localhost:8080/api
```

Start development server:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# Docker Setup

Build and run services:

```bash
docker compose up --build
```

Stop services:

```bash
docker compose down
```

---

# Testing Strategy

## Unit Testing

Frameworks:

- JUnit 5
- Mockito

Coverage Targets:

```text
Service Layer    : 80%
Controller Layer : 70%
Overall Coverage : 75%
```

---

## Integration Testing

Uses:

```text
Testcontainers
PostgreSQL Containers
```

Focus Areas:

- Repository Testing
- Security Testing
- API Testing

---

# Deployment

## Backend

Supported platforms:

- Render
- Railway
- AWS EC2
- Docker Hosts

---

## Frontend

Recommended platforms:

- Vercel
- Netlify

---

## Database

Recommended managed PostgreSQL providers:

- Neon
- Supabase
- Railway PostgreSQL

---

# Current Development Status

```text
Backend
--------
[ ] JWT Authentication
[ ] Role-Based Access Control
[ ] Employee CRUD
[ ] Salary Engine
[ ] Payroll Processing
[ ] Audit Logging
[ ] PDF Generation
[ ] Excel Export
[ ] Unit Testing
[ ] Integration Testing

Frontend
---------
[ ] Authentication UI
[ ] Dashboard
[ ] Employee Management Screens
[ ] Payroll Interface
[ ] Report Center
[ ] Audit Dashboard

Deployment
-----------
[ ] Docker Setup
[ ] CI/CD Pipeline
[ ] Cloud Deployment
```

---

# Future Enhancements

## HR Features

- Leave Management
- Attendance Tracking
- Employee Self-Service Portal
- Document Management

---

## Payroll Enhancements

- Tax Computation
- Multi-Currency Support
- Attendance-Based Payroll
- Payroll Approval Workflows

---

## Compliance

- E-Signatures
- Compliance Reporting
- Data Export Requests
- Data Retention Policies

---

## Analytics

- Payroll Dashboards
- Department Cost Analysis
- Trend Reporting
- Custom Insights

---

# Architecture Decisions

## Why Spring Boot?

- Mature ecosystem
- Strong security support
- Enterprise readiness
- Excellent testing capabilities

---

## Why React?

- Component-based architecture
- Fast development cycle
- Large ecosystem
- Excellent integration with REST APIs

---

## Why PostgreSQL?

- Reliability
- ACID compliance
- Advanced querying capabilities
- Strong community support

---

## Why Spring AOP?

- Separation of concerns
- Cleaner business logic
- Reusable audit functionality
- Easier maintenance

---

# Contributing

Contributions are welcome.

Steps:

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/amazing-feature
```

3. Commit your changes

```bash
git commit -m "Add amazing feature"
```

4. Push the branch

```bash
git push origin feature/amazing-feature
```

5. Open a Pull Request

---

# License

This project is licensed under the MIT License.

See the `LICENSE` file for additional details.

---

# Author

**Veritas**

Enterprise Payroll & Compliance Platform with Audit Intelligence.

Built to demonstrate modern enterprise software development practices including secure authentication, auditability, scalable architecture, and maintainable code design.
