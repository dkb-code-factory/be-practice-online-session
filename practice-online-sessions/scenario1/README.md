# Scenario 1

## Goal

Practice extending an existing API in a way that exercises REST design, query parameters, and business logic.

## Existing functionality

The application currently supports:
- `POST /transactions`
- `GET /transactions`

## Task

Extend the API so clients can analyze the stored transactions more effectively.

A good next step would be to add one or both of the following:
- filtering on `GET /transactions` using query parameters such as transaction type,
- a summary endpoint that returns aggregated values such as total income, total expenses, and balance.

You can decide the exact API shape, as long as it is consistent and easy to explain.
