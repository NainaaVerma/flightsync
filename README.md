# FlightSync ✈️

Flight prices change every few minutes and people miss deals
simply because they can't keep checking manually.

FlightSync solves this — set your price threshold once, forget it.
The system watches 24/7 and alerts you the moment price drops.

## How it works

Price engine generates flight prices every 5 seconds simulating
real market fluctuations — peak hours, off-peak, random changes.

Every price update is published to a Kafka topic. A consumer
listens, fetches last known price from Redis, compares, and
triggers an alert if threshold is breached.

## Tech Stack
- Java 17 + Spring Boot 3.2
- Apache Kafka — event streaming
- Redis — price history and caching
- Docker
- REST API — set your own alert threshold

## API
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/alerts/price/{from}/{to}` | Current price |
| POST | `/api/alerts/set` | Set your threshold |

## Run Locally
```bash
docker run -d --name kafka -p 9092:9092 apache/kafka
docker run -d --name redis -p 6379:6379 redis
./gradlew bootRun
```