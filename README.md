#  Event-Driven B2B Payment Gateway

> Scalable payment processing system using Kafka for asynchronous, fault-tolerant transactions.

---

## Problem

Traditional payment systems:

*  Block on synchronous processing
*  Fail under high load
*  Lack retry mechanisms

This system uses **event-driven architecture** to ensure scalability and reliability.

---

##  Architecture

Client → REST API → Service Layer
          ↓
     Kafka Producer → Topic
          ↓
     Kafka Consumer → DB (Postgres)
          ↓
     Redis Cache (state)

---

## ⚙️ Tech Stack

* Spring Boot (backend)
* Apache Kafka (event streaming)
* PostgreSQL (persistence)
* Redis (caching)
* Docker Compose (infra)

---

## 🔥 Key Features

### Event-Driven Processing

* Decouples API from transaction execution
* Enables horizontal scalability

###  Idempotency Handling

* Prevents duplicate transactions

###  Payment State Machine

* INITIATED → PROCESSING → SUCCESS / FAILED

###  Fault Tolerance

* Retry mechanism
* Dead Letter Queue (DLQ)

---

##  Impact

* ⚡ Improved system scalability under load
*  Reliable retry handling for failed transactions
*  Decoupled architecture for microservices expansion

---

## 🧪 Example API

```bash
POST /api/payments
{
  "amount": 5000
}
```

---

##  Design Decisions

| Decision            | Why                              |
| ------------------- | -------------------------------- |
| Kafka               | High-throughput async processing |
| Event-driven design | Scalability + resilience         |
| Redis               | Fast state lookup                |
| Idempotency keys    | Prevent duplicates               |

---

##  Future Improvements

* Distributed tracing (Zipkin)
* Rate limiting
* Fraud detection module
* Multi-region deployment

---

##  Author

Harsh Raj
Backend Systems | Distributed Systems Enthusiast
