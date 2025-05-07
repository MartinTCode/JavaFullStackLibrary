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

## General Requirements

The system shall:

- Store data using a relational database.

- Appropriately implement inheritance and polymorphism.

- Handle exceptions to provide clear and meaningful feedback to users.

- Offer a graphical user interface (GUI).

---

### Database Requirements

The database shall support:

#### Library Item Types and Attributes

There are different types of item that are stored in the database. Each item type has a limit on it's loan time. This functionality is managed in the backend rather than directly in the database. Their limitations are however shown here.

##### loan time limitations

###### Course litterature

Maximum 14 days.

###### Books (excl. Course litterature)

Maximum 30 days.

###### DVD:s

Maximum 7 days.

###### Journals and Reference Copies

Cannot be borrowed.

#### Book Data

- Title

- Author Name

- ISBN

- Keywords

- Type of Book:
  
  - Course literature
  
  - Book

- Reference copy indicator

- Publisher

- Language (e.g., English)

#### Movie Data

- Title

- Producer Name

- Keywords

- Genre

- Age Limit

- Country of Production

- Actor Name

- Publisher

#### Journal Data

- Not borrowable

- ISSN

- Title

- Publisher

- Language

- Keywords

### Other Database Requirements

- Keyword search support and optimization

- Store identifier (e.g., barcode) for items

## User Types (with distinct loan functionalities)

Each user type defines the limit on number of concurrent loans that it can have. *The number of max loans is arbitrary, but to prove functionality we have imagined some feasible limitations.* This functionality is managed in the backend rather than directly in the database. Their limitations are however shown here.

- Public User
  - Max number of concurrent loans: 3

- Student User
  - Max number of concurrent loans: 5

- Research User
  - Max number of concurrent loans: 10

- University Employee User
  - Max number of concurrent loans: 15

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

## Sign-in Database Functionality

- Distinguish user types:
  
  - Superuser (Boss)
  
  - Librarian
  
  - Borrower

- Store username

- Store password (initially raw text; hashed if time permits)

- Store contact details for password reset.
