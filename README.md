# JoaoBank Microservices Project

This project is a hands-on implementation of a microservices-based banking system, built to learn and demonstrate key patterns of the Spring Cloud ecosystem. The application is fully containerized using Docker and Docker Compose.

**Project Status:** 🚧 **Learning & Development** 🚧
This system was built as a learning exercise. The services and patterns implemented (e.g., specific Resilience4j configs, security) are for demonstration purposes.

## 🏛️ Architecture

This project follows a standard, cloud-native microservices architecture:

* **API Gateway (`gatewayserver`):** The single entry-point for all client requests. It handles routing to downstream services and can enforce cross-cutting concerns like rate limiting (via Redis) and security.
* **Service Discovery (`eurekaserver`):** All microservices register themselves with the Eureka server, allowing them to find and communicate with each other using service names instead of hardcoded IP addresses.
* **Config Server (`configserver`):** Provides centralized configuration for all services. It pulls configuration files from an external [Git repository](https://github.com/joao-tinelli/joaobank-config), allowing for dynamic configuration updates without service restarts (via Spring Cloud Bus).
* **Business Services (`accounts`, `loans`, `cards`):** These are the core domain services. Each has its own dedicated MySQL database, following the **Database-per-Service** pattern.
* **Message Broker (`rabbit`):** Used for asynchronous communication between services and for broadcasting configuration updates via Spring Cloud Bus.
* **Caching/Rate Limiting (`redis`):** Used by the API Gateway, primarily for rate limiting.

## 🛠️ Technologies Used

* **Java 17+**
* **Spring Boot 3**
* **Spring Cloud:**
    * Spring Cloud Gateway
    * Spring Cloud Config
    * Spring Cloud Eureka
    * Spring Cloud Bus (with RabbitMQ)
    * Spring Cloud OpenFeign
* **Resilience & Monitoring:**
    * Resilience4j (Circuit Breaker, Retry, Rate Limiter)
    * Spring Boot Actuator (for health checks)
* **Database & Messaging:**
    * MySQL (Database-per-service)
    * RabbitMQ
    * Redis
* **Containerization:**
    * Docker & Docker Compose

---

## ✨ Core Features & Patterns

* **Centralized Configuration:** All service configurations are externalized to a [central Git repository](https://github.com/joao-tinelli/joaobank-config) and managed by the `configserver`.
* **Service Discovery:** Services automatically register with `eurekaserver` and discover each other.
* **API Gateway:** `gatewayserver` acts as the single facade, routing requests to the appropriate services.
* **Resilience:** The `accounts` service demonstrates resilience patterns like:
    * **Circuit Breaker:** To stop cascading failures.
    * **Retry:** To automatically retry failed requests.
    * **Rate Limiter:** To control the flow of requests.
* **Health Checks:** All services (including databases and RabbitMQ) have `healthcheck` definitions in Docker Compose, ensuring a proper startup order using `depends_on: ... condition: service_healthy`.
* **Reusable Configuration:** A `common-config.yml` file is used to abstract and share common Docker configurations (like networking, health checks, and base service settings) across all services.

---

## 🚀 Getting Started

### Prerequisites

* [Docker](https://www.docker.com/products/docker-desktop/)
* [Docker Compose](https://docs.docker.com/compose/install/) (v2+)
* Git (for cloning)

### Running the Application

This project is configured to pull pre-built images from [Docker Hub](https://hub.docker.com/u/joaotinelli) which are set up to work with the public configuration repository.

1.  **Clone this repository:**
    ```sh
    git clone <your-repository-url>
    cd bank-system
    ```

2.  **Start all services with Docker Compose:**
    ```sh
    docker-compose up -d
    ```

    This command will:
    * Pull all necessary images (`mysql`, `redis`, `rabbitmq`, and all `joaotinelli/*` custom service images).
    * Create a dedicated bridge network (`joaobank`).
    * Start all services in the correct order as defined by the `depends_on` and `healthcheck` conditions.

3.  **Monitor the logs (optional):**
    To see the startup logs and ensure all services are healthy:
    ```sh
    docker-compose logs -f
    ```

4.  **Shutting Down:**
    To stop and remove all containers, networks, and volumes:
    ```sh
    docker-compose down -v
    ```

---

## 🌐 Service Endpoints

Once running, you can access the following key endpoints from your browser or API client:

| Service | URL (Access Point) | Description |
| :--- | :--- | :--- |
| **API Gateway** | `http://localhost:8072/` | **Main entry point.** All API routes (e.g., `/accounts`, `/loans`) are proxied through here. |
| **Eureka Server** | `http://localhost:8070/` | Service discovery dashboard. |
| **RabbitMQ** | `http://localhost:15672/` | Admin dashboard (login: `guest` / `guest`). |
| **Config Server** | `http://localhost:8071/` | Config server endpoint (e.g., `.../accounts/default`). |
| **Accounts DB** | `localhost:3306` | (Host-exposed port for `accountsdb`) |
| **Loans DB** | `localhost:3307` | (Host-exposed port for `loansdb`) |
| **Cards DB** | `localhost:3308` | (Host-exposed port for `cardsdb`) |
