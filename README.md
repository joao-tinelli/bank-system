# 🏦 JoaoBank - Microservices Banking System

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Ready-326ce5)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple)

**JoaoBank** is a comprehensive cloud-native banking system built using the **Spring Boot** ecosystem. It demonstrates a fully distributed microservices architecture, implementing patterns such as Service Discovery, Centralized Configuration, API Gateway routing, Event-Driven Communication, and Circuit Breakers.

This project is containerized using **Docker** and includes manifests for deployment on **Kubernetes**.

---

## 🏗️ Architecture Overview

The system is composed of multiple independent microservices that interact via REST APIs and asynchronous messaging.

### 🔌 Infrastructure Services
* **Config Server:** Centralized configuration management backed by a Git repository.
* **Eureka Server:** Service Discovery and Registration (Client-side load balancing).
* **API Gateway:** Edge server handling routing, SSL termination, and security (OAuth2 Resource Server).
* **Keycloak:** Identity and Access Management (OIDC/OAuth2 Provider).
* **RabbitMQ:** Message broker for asynchronous event-driven communication.
* **Redis:** In-memory data store used for Rate Limiting.

### 💼 Business Microservices
* **Accounts Service:** Manages customer bank accounts.
* **Loans Service:** Manages loan processing and status.
* **Cards Service:** Manages credit/debit card data.
* **Message Service:** A consumer service that listens for events (like `communication-sent`) to trigger simulated emails/SMS.

### 💾 Data Persistence
* **MySQL:** Dedicated databases for Accounts, Loans, and Cards services (`accountsdb`, `loansdb`, `cardsdb`).
* **Separation of Concerns:** Each microservice owns its own database schema.

---

## 🛠️ Technology Stack

* **Core:** Java 17+, Spring Boot 3.2+
* **Spring Cloud:** Config, Netflix Eureka, Gateway, OpenFeign, Stream (RabbitMQ), Function.
* **Resilience:** Resilience4j (Circuit Breakers, Rate Limiters, Retry).
* **Security:** Spring Security, OAuth2 Resource Server, Keycloak 26.
* **Database:** MySQL 8.0, H2 (optional).
* **DevOps:** Docker, Docker Compose, Kubernetes (Minikube/Kind).
* **Observability:** Spring Boot Actuator.

---

## 📂 Project Structure

This project follows a **Mono-repo** structure:

```text
bank-system/
├── accounts/           # 📦 Business Logic: Accounts
├── cards/              # 📦 Business Logic: Cards
├── loans/              # 📦 Business Logic: Loans
├── message/            # 📨 Consumer: Async Notifications
├── gatewayserver/      # 🚪 Edge: API Gateway
├── eurekaserver/       # 🔍 Infra: Service Discovery
├── configserver/       # ⚙️ Infra: Central Config
├── docker-compose/     # 🐳 Orchestration: Local Dev
├── kubernetes/         # ☸️ Orchestration: Production (K8s)
└── Microservices.postman_collection.json # 🚀 API Test Collection
```

## 🚀 Getting Started (Docker Compose)
* The easiest way to run the entire system is via Docker Compose.

**Prerequisites**
* Docker & Docker Compose installed.
* Java 17+ & Maven(to build JARs).

**1. Build the Microservices**
* Navigate to the root folder and build the images using Maven (Google Jib is present on pom.xml):

```bash
mvn clean compile jib:dockerBuild
```

**2. Run the Infrastructure & Services**
* Navigate to the docker-compose folder:

```bash
docker-compose up -d
```

**3. Usage**
* **Keycloak:** Access http://localhost:7080 (User/Pass: admin/admin)
* **Eureka Dashboard:** http://localhost:8070
* **RabbitMQ Admin:** http://localhost:15672
* **API Gateway:** All API requests should go through http://localhost:8072

## 📲 API Testing (Postman)
This project includes a Postman Collection (Microservices.postman_collection.json) to help you test the APIs easily.

**1. Import Collection**
* Open Postman.
* Click Import > File > Select Microservices.postman_collection.json.

**2. Authentication Flows** 
The collection is pre-configured with the following OAuth2 flows for Keycloak:
* Client Credentials: Used for machine-to-machine communication.
   * Client ID: joaobank-callcenter-cc
   * Client Secret: (Pre-configured in collection)
* Authorization Code: Used for user login simulation.
   * Client ID: joaobank-callcenter-ac.

## ☸️ Kubernetes Deployment
* The project includes a kubernetes/ folder with manifests for deploying to a local cluster (Minikube, Kind, or Docker Desktop).

```bash
### Apply Infrastructure (DBs, RabbitMQ, Redis, ConfigMaps)
kubectl apply -f kubernetes/infrastructure/

### Apply Platform Services (Config, Eureka)
kubectl apply -f kubernetes/platform/

### Apply Business Applications
kubectl apply -f kubernetes/apps/
```

## 🧩 Key Patterns Implemented
**1. DRY Configuration** (common-config.yml)
* We use Docker Compose extends functionality to maintain a common-config.yml. This file centralizes shared environment variables, health checks, and resource limits, preventing code duplication across services.

**2. Circuit Breaker** (Resilience4j)
* The Accounts service implements a Circuit Breaker pattern. If the database or a dependent service fails:
* Failure Threshold: 50%
* Wait Duration: 10s (Open State)
* It degrades gracefully instead of cascading failures.

**3. Event-Driven Architecture**
* We use Spring Cloud Stream with Functional Programming (java.util.function).
* Accounts service emits a communication-sent event.
* RabbitMQ routes the message.
* Message service consumes the event via email|sms function composition.

## 📬 Contact
Created by Joao Tinelli.
