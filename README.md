# JavaFullStackLibrary

A full-stack library management system with a graphical desktop UI (JavaFX), PostgreSQL database, and layered Java backend using JPA + Hibernate.

---

![Status](https://img.shields.io/badge/status-pre--alpha-yellow)
![Java](https://img.shields.io/badge/java-21-blue)
![License](https://img.shields.io/github/license/MartinTCode/JavaFullStackLibrary)

---

## 📌 Project Status

This `main` branch is currently **inactive** and reserved for future production-ready releases.

The codebase and all active development are maintained in other branches.

---

## 📦 Pre-alpha Release

A proof-of-concept version is available on the [`milestone/poc`](../../tree/milestone/poc) branch.

🎥 [Watch the demo video](https://github.com/MartinTCode/JavaFullStackLibrary/releases/download/v0.1.0-prealpha/javafullstacklibrary_demo_compressed.mp4)  
📄 [Read the release notes](../../releases/tag/v0.1.0-prealpha)

🧭 For details on upcoming goals and planned features, see the [Release Planning Meeting – 2025-06-17](https://github.com/MartinTCode/JavaFullStackLibrary/blob/dev/docs/release-planning/RELEASE_MEETING_2025-06-17.md).


---

## 🏗️ Architectural Overview

The system follows a multi-layered architecture built on the **MVC** design pattern, extended with:

- A dedicated **Service Layer** to separate business logic from UI
- A **DAO Layer** (Data Access Object) for clean database interaction
- Utility classes and **Singletons** for shared session and navigation handling

This structure improves modularity and testability, allowing for isolated maintenance and future expansion (e.g., switching out database technology with minimal changes to core logic).

📷 [View Login Architecture Diagram](https://github.com/MartinTCode/JavaFullStackLibrary/blob/dev/docs/plantuml/output_pngs/UserLoginClassDiagram.png)

---

## 🌱 Branching Structure

Development is organized around a disciplined feature-branch workflow. Features were implemented incrementally and consolidated into `dev`, with completed branches archived under the `retired/feature/...` namespace to keep the repo clean.

Commits and branch names document progress transparently.

### 🏁 Development Timeline Summary (Chronological)

> 📁 **Note:** Completed feature branches are moved under `retired/feature/` to reflect archival status.

- 🛠️ `feature/maven_integration` → Initial setup and build configuration
- 📐 `docs/requirements-and-design` → Planning, mockups, and system design
- 🧩 `feature/javafx-view-switching` & `feature/css-implementation` → GUI interaction base
- 🎯 `feature/guestViews/contextSharing` & `feature/frontend-guestViews` → Core guest UI development
- 🧪 `feature/frontend-guestViews-testfx-integration` → GUI testing with TestFX
- 🔁 `feature/frontend-borrowerviews` → Borrower flows (now retired)
- 👩‍🏫 `feature/frontend-librarianviews` → Librarian interface
- 🔎 `feature/search-query-integration` → Dynamic keyword-based search
- 🔐 `feature/login-implementation` → Role-based authentication
- 📚 `feature/item-management`, `feature/itemcopy-functionality` → Item and copy handling
- 💳 `feature/loan-functionality`, `feature/returnLoan-functionality` → Lending workflows
- ⏰ `feature/overdue-functionality` → Overdue loan tracking
- 🛢️ `feature/database-setup`, `feature/database-implementation` → DAO and PostgreSQL connectivity
- ✅ Consolidated into [`milestone/poc`](../../tree/milestone/poc)
- 🚧 Ongoing development centralized in [`dev`](../../tree/dev)

📂 View [all branches](../../branches) for archived features and historical context.

---

## 🧭 Project Overview

```
JavaFullStackLibrary/
 ├── docs/                 📁 Design docs, mockups, diagrams, specifications
 ├── src/
 │   ├── main/
 │   │   ├── java/         📦 Java code: model, DAO, services, controllers
 │   │   ├── resources/    🎨 FXML views, CSS, icons, config
 │   │   └── database/     🛢️ SQL setup scripts
 │   └── test/             🧪 Unit tests and test utilities
 ├── archive/              📦 Legacy mockups and sample data
 ├── pom.xml               ⚙️  Maven project config
 └── README.md             📘 Branch-specific project info
```

---

## 📂 Repo Navigation

- 📁 [`/docs`](../../tree/milestone/poc/docs): UI mockups, models, diagrams, specs
- 🧪 [`/src/test`](../../tree/milestone/poc/src/test): Unit tests and test utilities
- 🛠️ [`/src/main`](../../tree/milestone/poc/src/main): Full application codebase
- 🗂️ [`/release-planning`](../../tree/milestone/poc/release-planning): Planning notes and roadmaps

---

Stay tuned for future production releases on this branch.
