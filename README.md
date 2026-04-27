# Microservice System

This repository contains a Spring Boot microservice architecture with:

- `discovery-server` (Eureka) on port `8761`
- `config-server` (Spring Cloud Config) on port `8888`
- `api-gateway` on port `8080`
- `auth-service` on port `8083`
- `userservice` on port `8081`
- `orderservice` on port `8082`
- `notification-service` (Kafka consumer, exposed on `8084`)

## Runtime Flow

1. Services register with Eureka (`discovery-server`).
2. `api-gateway`, `userservice`, and `orderservice` pull central config from `config-server`.
3. Gateway routes:
   - `/auth/**` -> `auth-service`
   - `/users/**` -> `user-service`
   - `/orders/**` -> `order-service`
4. `orderservice` publishes order events to Kafka topic `order-created-topic`.
5. `notification-service` consumes the events.

## Dockerized Setup

The repo now includes:

- `Dockerfile`: reusable multi-stage build for any service
- `docker-compose.yml`: full stack orchestration
- `.dockerignore`: optimized build context

### Prerequisites

- Docker Engine with Compose plugin (`docker compose`)

### Start The Whole Project

```bash
docker compose up --build -d
```

### Check Running Services

```bash
docker compose ps
```

### View Logs

```bash
docker compose logs -f
```

### Stop Everything

```bash
docker compose down
```

### Stop And Remove Volumes

```bash
docker compose down -v
```

## Endpoints

- Eureka dashboard: `http://localhost:8761`
- Config server: `http://localhost:8888`
- API Gateway: `http://localhost:8080`
- Auth service direct: `http://localhost:8083`
- User service direct: `http://localhost:8081`
- Order service direct: `http://localhost:8082`
- Notification service direct: `http://localhost:8084`
- Kafka for host clients: `localhost:29092`
