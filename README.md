# feature/search-query-integration

This feature branch integrates the frontend guest view with backend controller logic to enable item search functionality.  
It connects the UI search input to the backend through a RESTful endpoint, allowing users to query items stored in the database.

## Purpose
- Enable live communication between frontend and backend for search queries.
- Utilize the database implementation from `feature/database-implementation`.
- Leverage the frontend view components from `feature/frontend_guestViews`.

## Scope
- Query controller setup and endpoint exposure.
- Fetch logic on the frontend (e.g., using `fetch` or an API client).
- JSON response handling and result display in the guest view.

## Dependencies
- `feature/database-implementation`
- `feature/frontend_guestViews`

## Notes
- This branch does **not** handle advanced filters or pagination yet.
- The database must be seeded with sample items for search to return results.
