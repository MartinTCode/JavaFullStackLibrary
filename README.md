### 📦 Pre-alpha Release – Core Functionality Implemented

This release marks the completion of the proof-of-concept for the JavaFullStackLibrary system — a multi-layered desktop application designed for managing physical library resources.

Key implemented features:
- Graphical desktop UI with JavaFX
- MVC + Service + DAO layered architecture
- Full PostgreSQL integration via JPA + Hibernate
- Role-based login (guest, borrower, librarian)
- Borrowing, returning, and loan validation workflows
- Keyword search for books, journals, and films
- Exception handling with user-friendly feedback, although some through pop-ups

Limitations in this version:
- Reservation system and advanced user management not included
- No email integration or filter-based search yet
- Admin role and certain permission flows are placeholders
- Exception handling still using pop-ups in some instances. Should use interactive use field highlighting and in-scene text prompts.

This version serves as a stable base for upcoming feature freeze, polish, and release planning toward 1.0.

📝 Database dump and UML-diagrams (class, database, use-case and architecture) included in the repo.
📁 See `/release-planning/RELEASE_MEETING_2025-06-17.md` for future roadmap.

---

🎥 **Demo Video**

A 10-minute walkthrough of the pre-alpha system, showcasing implemented features, user flows, and the graphical interface.

[Download demo video](https://github.com/MartinTCode/JavaFullStackLibrary/releases/download/v0.1.0-prealpha/javafullstacklibrary_demo_compressed.mp4)

---

## 📁 Project Structure

See `/release-planning/RELEASE_MEETING_2025-06-17.md` for roadmap and planning notes.

To test locally:
- Java 21 + Maven 4.0.0
- PostgreSQL 16 (DB name: `library`, user: `library_user`, pass: `library_pass`)
- Run `insert_test_data.sql` to populate **database**

