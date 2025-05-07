-- Insert data into language table
INSERT INTO language (language) VALUES
('English'),
('Swedish'),
('French'),
('German'),
('Spanish');

-- Insert data into location table
INSERT INTO location (floor, section, shelf, position) VALUES
('1', 'A', '1', '1'),
('1', 'A', '1', '2'),
('1', 'B', '2', '1'),
('2', 'C', '3', '1'),
('2', 'D', '4', '2');

-- Insert data into creator table
INSERT INTO creator (f_name, l_name, dob) VALUES
('John', 'Doe', '1970-01-01'),
('Jane', 'Smith', '1980-02-02'),
('Emily', 'Johnson', '1990-03-03'),
('Michael', 'Brown', '1965-04-04'),
('Sarah', 'Davis', '1975-05-05');

-- Insert data into actor table
INSERT INTO actor (f_name, l_name, dob) VALUES
('Tom', 'Hanks', '1956-07-09'),
('Meryl', 'Streep', '1949-06-22'),
('Leonardo', 'DiCaprio', '1974-11-11'),
('Scarlett', 'Johansson', '1984-11-22'),
('Denzel', 'Washington', '1954-12-28');

-- Insert data into genre table
INSERT INTO genre (genre) VALUES
('Fiction'),
('Non-Fiction'),
('Science'),
('Drama'),
('Comedy'),
('Biology');

-- Insert data into keyword table
INSERT INTO keyword (keyword) VALUES
('Adventure'),
('Romance'),
('Thriller'),
('Biography'),
('Fantasy'),
('MIT');

-- Insert data into item table
INSERT INTO item (location_id, language_id, item_type, identifier, identifier2, title, publisher, age_limit, country_of_production) VALUES
(1, 1, 'book', '1234567890123', '9781234567890', 'Book Title 1', 'Publisher 1', 12, 'USA'),
(2, 2, 'book', '2234567890123', '9782234567890', 'Book Title 2', 'Publisher 2', 15, 'Sweden'),
(3, 3, 'course_litterature', '3234567890123', '9783234567890', 'Course Book 1', 'Publisher 3', 18, 'France'),
(4, 4, 'dvd', NULL, NULL, 'DVD Title 1', 'Publisher 4', NULL, 'Germany'),
(5, 5, 'journal', '5234567890123', NULL, 'Journal Title 1', 'Publisher 5', NULL, 'Spain');
-- Add 15 more items with varying attributes...

-- Insert data into item_copy table
INSERT INTO item_copy (item_id, barcode, is_reference, date_added, copy_status) VALUES
(1, 'X82DMJQ1', FALSE, '2025-01-01', 'available'),
(3, '7PTK3A94', FALSE, '2025-01-02', 'borrowed'),
(2, 'QW8Z4NME2L', FALSE, '2025-01-03', 'available'),
(3, 'KD9T7P6R', FALSE, '2025-01-04', 'available'),
(4, 'R5BX0Q29', FALSE, '2025-01-05', 'borrowed'),
(5, 'R5BX0Q30', TRUE, '2025-01-04', 'available');
-- Add more copies ensuring at least double the number of items and max 10% borrowed...

-- Insert data into user_profile table
INSERT INTO user_profile (user_type, f_name, l_name, phone, full_address) VALUES
('public', 'Alice', 'Wonderland', '1234567890', '123 Main St'),
('student', 'Bob', 'Builder', '0987654321', '456 Elm St'),
('researcher', 'Charlie', 'Brown', '1122334455', '789 Oak St');
-- Add more profiles ensuring only for user_role = 'borrower'...

-- Insert data into user table
INSERT INTO library_user (profile_id, ssn, u_name, p_hashed_bcrypt, email, user_role) VALUES
(NULL, NULL, 'admin1', 'hashed_password1', 'admin1@example.com', 'admin'),
(NULL, NULL, 'librarian1', 'hashed_password2', 'librarian1@example.com', 'librarian'),
(1, '123456789012', NULL, 'hashed_password3', 'borrower1@example.com', 'borrower'),
(2, '234567890123', NULL, 'hashed_password4', 'borrower2@example.com', 'borrower'),
(3, '345678901234', NULL, 'hashed_password5', 'borrower3@example.com', 'borrower');
-- Add more users ensuring no more than 50% have loans...

-- Insert data into loan table
INSERT INTO loan (item_copy_id, user_id, start_date, return_date, returned_date) VALUES
(2, 3, '2025-04-01', '2025-04-14', NULL), -- Course literature, max 14 days
(5, 4, '2025-04-01', '2025-04-07', NULL); -- DVD, max 7 days
-- Add more loans ensuring constraints on loan durations and user limits...

-- TODO: add testdata for junction tables:

- Link items to creators (e.g., authors for books, course literature)
INSERT INTO item_creator (item_id, creator_id) VALUES
(1, 1), -- Book Title 1 by John Doe
(2, 2), -- Book Title 2 by Jane Smith
(3, 3), -- Course Book 1 by Emily Johnson
(4, 4), -- DVD Title 1 by Michael Brown (director)
(5, 5); -- Journal Title 1 by Sarah Davis

-- Link items to actors (e.g., actors for DVDs)
INSERT INTO item_actor (item_id, actor_id) VALUES
(4, 1), -- DVD Title 1 featuring Tom Hanks
(4, 2), -- DVD Title 1 featuring Meryl Streep
(4, 3); -- DVD Title 1 featuring Leonardo DiCaprio

-- Link items to genres
INSERT INTO item_genre (item_id, genre_id) VALUES
(1, 1), -- Book Title 1 is Fiction
(2, 2), -- Book Title 2 is Non-Fiction
(3, 3), -- Course Book 1 is Science
(4, 4), -- DVD Title 1 is Drama
(5, 6); -- Journal Title 1 is Biology

-- Link items to keywords
INSERT INTO item_keyword (item_id, keyword_id) VALUES
(1, 1), -- Book Title 1 tagged with Adventure
(2, 2), -- Book Title 2 tagged with Romance
(3, 3), -- Course Book 1 tagged with Thriller
(4, 4), -- DVD Title 1 tagged with Biography
(5, 6); -- Journal Title 1 tagged with MIT

-- Link items to locations
INSERT INTO item_location (item_id, location_id) VALUES
(1, 1), -- Book Title 1 located at location 1
(2, 2), -- Book Title 2 located at location 2
(3, 3), -- Course Book 1 located at location 3
(4, 4), -- DVD Title 1 located at location 4
(5, 5); -- Journal Title 1 located at location 5

-- Link items to languages (arbirtrary values)
INSERT INTO item_language (item_id, language_id) VALUES
(1, 1), -- Book Title 1 in English
(2, 2), -- Book Title 2 in Swedish
(3, 3), -- Course Book 1 in French
(4, 4), -- DVD Title 1 in German
(5, 5); -- Journal Title 1 in Spanish