# Distributed B2B Payment Gateway Simulation

##  Motivation
In high-concurrency enterprise environments, a monolithic architecture fails under the load of simultaneous financial transactions. This project simulates a distributed, microservices-based payment gateway, focusing heavily on data integrity (ACID compliance), fault tolerance, and asynchronous event processing to prevent system-wide cascading failures.

##  Architecture & Tech Stack
* **Framework:** Java 17 / Spring Boot 3.x
* **Databases:** PostgreSQL (Relational persistence) & Redis (In-memory caching)
* **Message Broker:** Apache Kafka
* **Security:** Spring Security (Stateless JWT authentication)

## ⚙️ System Design Mechanics

### 1. The Saga Pattern (Choreography)
To maintain consistency across distributed microservices without locking the entire database (Two-Phase Commit), this system uses a Choreography Saga pattern via Kafka.
* The `Transaction-Service` initiates a payment and publishes a `PAYMENT_PENDING` event.
* The `Ledger-Service` consumes the event, attempts the database write, and publishes either a `PAYMENT_SUCCESS` or `PAYMENT_FAILED` event.
* If a failure occurs, compensating transactions are automatically triggered to rollback states, ensuring financial ledgers are never out of sync.

### 2. High-Concurrency Mitigation
* **Redis Caching:** API rate limiting and session validations are checked against Redis in sub-millisecond times, preventing the PostgreSQL database from bottlenecking during traffic spikes.
* **Idempotency:** Core endpoints utilize idempotency keys. If a client network times out and retries a payment request, the system references the key to prevent double-charging the account.

##  Service Topology
| Service | Port | Responsibility |
| :--- | :--- | :--- |
| `api-gateway` | 8080 | Routes external traffic, handles rate limiting |
| `auth-service` | 8081 | JWT issuance and RBAC verification |
| `transaction-service` | 8082 | Core routing, idempotency checks, Kafka producer |
| `ledger-service` | 8083 | ACID database writes, Kafka consumer |

##  Local Execution
The entire infrastructure, including Zookeeper and Kafka brokers, is containerized for local testing.

```bash
# Build the microservices
mvn clean install

# Deploy infrastructure and services
docker-compose up -d

🗺 Future Scope
Implement an orchestration-based Saga (using an engine like Camunda or Temporal) to improve transaction observability over the current choreography model.

Note: Due to the nature of the simulated financial algorithms, the core routing logic .java files are restricted from public view. Architectural schemas, database ERDs, and Kafka topic configurations are available upon request for technical interviews.
