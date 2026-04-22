# Distributed B2B Payment Gateway Simulation

## Motivation (Business Impact)

B2B payment systems must guarantee:

* No duplicate transactions
* Strong consistency
* Failure recovery

This system simulates a high-concurrency payment backend with strict correctness guarantees.

---

## System Architecture & Design Mechanics

### Key Decisions

#### Idempotency Handling

* Every request includes an idempotency key
* Stored in Redis
* Prevents duplicate processing

---

#### Saga Pattern (Choreography)

* No central orchestrator
* Services communicate via events

Why Choreography:

* No single point of failure
* Better scalability
* Event-driven alignment

---

#### Kafka as Event Backbone

* Event-driven communication
* Enables replay and recovery
* Decouples services

---

#### PostgreSQL for ACID

* Ensures transaction integrity
* Required for financial correctness

---

### Payment Flow

1. API receives request
2. Idempotency check (Redis)
3. Persist transaction (PostgreSQL)
4. Publish event (Kafka)
5. Downstream processing

---

## Tech Stack

* Java 17
* Spring Boot 3
* PostgreSQL
* Redis
* Apache Kafka
* JWT

---

## Service Topology

```
API Gateway
  ↓
Payment Service
  ↓
Redis (Idempotency)
  ↓
PostgreSQL

Kafka Topics:
  → payment.initiated
  → payment.completed
  → payment.failed

Consumers:
  → Ledger Service
  → Settlement Service
  → Notification Service
```

---

## Local Setup / Execution

### Prerequisites

* Java 17
* Docker

### Start Infrastructure

```bash
docker-compose up -d
```

### Run Service

```bash
./mvnw spring-boot:run
```

### API Example

```bash
POST /api/payments

Headers:
Idempotency-Key: unique_key
Authorization: Bearer <jwt>
```

---

## Future Scope / Known Limitations

### Limitations

* No distributed tracing
* No circuit breaker implementation
* Kafka exactly-once not fully configured

### Future Work

* OpenTelemetry tracing
* Resilience4j integration
* Retry + DLQ
* Fraud detection service
* Multi-region deployment

---

## Consultant Disclaimer

This implementation focuses on distributed transaction handling and system reliability. Proprietary settlement logic, fraud detection systems, and compliance rules are excluded due to enterprise IP constraints. Extended system design artifacts are available upon request.
