# Ledger

A double-entry accounting ledger.

Money is kept in accounts. Every transfer is one journal entry with two
postings, a debit and a credit. Balances are never stored. They are computed
by summing postings, so the books always reconcile to zero.

## What it does

- Accounts hold money in one currency. Transfers move money between accounts
  as balanced journal entries.
- Balances are always computed, never stored.
- Transfers are safe under concurrency, so parallel transfers cannot overspend.
- Transfers are replay-safe. Each transfer carries a client-chosen reference.
  Sending the same reference again never moves the money twice.
- On startup the ledger creates equity and treasury accounts and writes the
  genesis entry, the first money in the system.

## Tech stack

- Java 21, Spring Boot 4.1
- PostgreSQL 18 via Docker Compose
- Flyway for schema migrations

## Running

1. Start the database:

   docker compose up -d

2. Start the application:

   ./mvnw spring-boot:run