# Functional Requirements

The system must support the following functions:

- **Add**
  
  - Objects
  
  - Users
  
  - Items
  
  - Authors and directors

- **Modify Objects**

- **Remove Objects**

- **Search Objects**

- **Manage Borrowing**
  
  - Handle differences in loan time based on item type
  
  - Deny borrowing of Journals and reference copies

- **Manage Returns**

- **Manage Reservations**

- **User Login**

- **Receipt Generation**

- **Display Overdue Items**
  
  - Abstracted into an email client (functionality not implemented in current version)

- **Manage UI Views**
  
  - Client
  
  - Non-logged in
  
  - Staff
  
  - Superuser

---

# General Requirements

The system shall:

- Store data using a relational database.

- Appropriately implement inheritance and polymorphism.

- Handle exceptions to provide clear and meaningful feedback to users.

- Offer a graphical user interface (GUI).

---

# Database Requirements

The database shall support:

## Book Data

- Title

- Author Name

- ISBN

- Keywords

- Type of Book:
  
  - Course literature
  
  - Book

- Reference copy indicator

- Publisher

- Language (e.g., English → `eng`)

## Movie Data

- Title

- Producer Name

- Keywords

- Genre

- Age Limit

- Country of Production

- Actor Name

- Publisher

## Journal Data

- Not borrowable

- ISSN

- Title

- Publisher

- Language

- Keywords

## Other Database Requirements

- Keyword search support and optimization

- Store identifier (e.g., barcode) for items

### User Types (with distinct loan functionalities)

- Public User

- Student User

- Research User

- University Employee User

## Entities in Library Database

- borrower 

- loan 

- reservation 

- copy 

- item 

- item_creator (junction) 

- creator 

- actor 

- item_actor (junction) 

- genre 

- item_genre (junction) 

- location 

- keyword 

- item_keyword (junction)

---

# Sign-in Database Functionality

- Distinguish user types:
  
  - Superuser (Boss)
  
  - Librarian
  
  - Borrower

- Store username

- Store password (initially raw text; hashed if time permits)

- Store contact details for password reset.
