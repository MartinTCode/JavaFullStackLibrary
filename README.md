# feature/database-setup

This branch establishes the initial backend connection to a local PostgreSQL database using raw JPA and Hibernate, without Spring Data JPA.  
It was created to verify the configuration and functionality of the persistence layer using `persistence.xml` and direct usage of `EntityManager`.

## Purpose
- Validate PostgreSQL connectivity through JPA + Hibernate.
- Confirm that all required Maven dependencies are correctly added and configured.
- Set up and test basic persistence using manually managed `EntityManager`.

## Scope
- Configuration via `persistence.xml` in `META-INF/`.
- Use of `Persistence.createEntityManagerFactory(...)` to obtain `EntityManager` instances.
- Simple JPA entity class annotated with `@Entity`.
- Minimal working test storing and retrieving data from the local PostgreSQL database.
- Seed data inserted manually to verify data round-trip.

## Dependencies
- PostgreSQL JDBC driver
- JPA API
- Hibernate ORM Core

## Notes
- This branch is **complete** and was used as a base for the more advanced work in `feature/database-implementation`.
- No business logic, service layers, or advanced entity relationships were included — the focus was entirely on infrastructure setup and raw JPA interaction.
