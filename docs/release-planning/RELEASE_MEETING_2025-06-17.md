# 🧭 Release Planning Meeting – 2025-06-17

## 🎯 Goal
Define the next development targets needed to move from the current **pre-alpha** (`milestone/poc`) stage to a formal **alpha release** candidate.

---

## 🧪 Current Status

- ✅ Pre-alpha milestone tagged as `v0.1.0-prealpha`
- ✅ Core workflows implemented (login, search, borrow, return, basic CRUD)
- ✅ Demo video and documentation completed
- ❌ Not yet production-stable or complete in user-facing areas

---

## 🚀 Features Required for Alpha

The following features must be implemented before tagging an alpha release:

### 🔧 UI & Error Handling
- Replace all pop-up error messages with **inline field-level feedback**
- Show clearly which field is invalid and why

### 👤 Borrower Functionality
- View **active loans** with overdue information per item
- Access and edit **own account information**
- Change **own password** from within borrower interface

### 🧑‍💼 Librarian Functionality
- Add **new users** from librarian panel
- Modify existing users via **SSN-based search**
- Access and update **own account information**
- Change **own password**

### 🏗️ Code Architecture
- Implement a **Factory Design Pattern** to handle creation of:
  - `Book`, `DVD`, `Journal`, and `CourseLitterature`
- Refactor codebase to replace manual instantiation with factory logic

### 🧪 Testing
- Extend existing TestFX UI tests to cover:
  - **Librarian menu flows**
  - (Currently only guest and borrower views are tested)

### 📄 Documentation
- Update documentation to reflect:
  - New user flows
  - Factory pattern integration
  - UI changes in error handling
  - Updated UML or PlantUML class diagrams (if applicable)

---

## ✅ Branch Planning

Suggested new feature branches:

- `feature/borrower-active-loans`
- `feature/borrower-account-management`
- `feature/librarian-add-borrower`
- `feature/librarian-modify-borrower`
- `feature/librarian-account-info`
- `feature/password-update`
- `feature/ui-error-handling-refactor`
- `feature/factory-item-creation`
- `feature/librarian-ui-testing`
- `docs/update-for-alpha`

---

## 📅 Target Milestone: `release/1.0.0-alpha`

Once these are completed, merged, and tested, we will tag an `alpha` release and prepare a feature freeze for RC1.

