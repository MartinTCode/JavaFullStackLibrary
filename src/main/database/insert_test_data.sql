INSERT INTO language (language) VALUES
('English'), ('French'), ('Spanish'), ('German'), ('Japanese'),
('Mandarin'), ('Italian'), ('Arabic'), ('Russian'), ('Portuguese');

INSERT INTO location (floor, section, shelf, position) VALUES
('1', 'A', 'S1', 'P1'), ('1', 'A', 'S2', 'P2'), ('1', 'B', 'S1', 'P3'),
('2', 'C', 'S3', 'P1'), ('2', 'D', 'S4', 'P2'), ('3', 'E', 'S5', 'P3'),
('3', 'F', 'S6', 'P1'), ('4', 'G', 'S7', 'P2'), ('4', 'H', 'S8', 'P3'),
('5', 'I', 'S9', 'P4');

INSERT INTO creator (f_name, l_name, dob) VALUES
('Jane', 'Austen', '1775-12-16'), ('George', 'Orwell', '1903-06-25'),
('Mark', 'Twain', '1835-11-30'), ('J.K.', 'Rowling', '1965-07-31'),
('Stephen', 'King', '1947-09-21'), ('Isaac', 'Asimov', '1920-01-02'),
('Agatha', 'Christie', '1890-09-15'), ('Ernest', 'Hemingway', '1899-07-21'),
('Leo', 'Tolstoy', '1828-09-09'), ('F. Scott', 'Fitzgerald', '1896-09-24');

INSERT INTO actor (f_name, l_name, dob) VALUES
('Keanu', 'Reeves', '1964-09-02'),
('Carrie-Anne', 'Moss', '1967-08-21'),
('Chihiro', 'Ogino', '1990-01-01'),
('Marlon', 'Brando', '1924-04-03'),
('Matthew', 'McConaughey', '1969-11-04');

INSERT INTO genre (genre) VALUES
('Fiction'), ('Science Fiction'), ('Mystery'), ('Fantasy'), ('Biography'),
('History'), ('Romance'), ('Horror'), ('Drama'), ('Comedy');

INSERT INTO keyword (keyword) VALUES
('magic'), ('war'), ('future'), ('detective'), ('space'),
('love'), ('family'), ('friendship'), ('revenge'), ('freedom');

INSERT INTO item (location_id, language_id, type, identifier, identifier2, title, publisher, age_limit, country_of_production) VALUES
(1, 1, 'Book', '9780141439518', '9780141439518', 'Pride and Prejudice', 'Penguin', 12, 'UK'),
(2, 1, 'Book', '9780451524935', '9780451524935', '1984', 'Signet', 14, 'UK'),
(3, 1, 'Book', '9780142437179', '9780142437179', 'Adventures of Huckleberry Finn', 'Penguin', 12, 'USA'),
(4, 1, 'Book', '9780439136365', '9780439136365', 'Harry Potter and the Prisoner of Azkaban', 'Bloomsbury', 10, 'UK'),
(5, 1, 'Book', '9781501142970', '9781501142970', 'The Institute', 'Scribner', 16, 'USA'),
(6, 1, 'Book', '9780553293357', '9780553293357', 'Foundation', 'Bantam', 13, 'USA'),
(7, 1, 'Book', '9780007119356', '9780007119356', 'Murder on the Orient Express', 'HarperCollins', 12, 'UK'),
(8, 1, 'Book', '9780684801223', '9780684801223', 'The Old Man and the Sea', 'Scribner', 12, 'USA'),
(9, 1, 'Book', '9781853260629', '9781853260629', 'War and Peace', 'Wordsworth', 16, 'Russia'),
(10, 1, 'Book', '9780743273565', '9780743273565', 'The Great Gatsby', 'Scribner', 14, 'USA'),
(11, 1, 'Dvd', 'IMDB001', NULL, 'Inception', 'Warner Bros.', 13, 'USA'),
(12, 1, 'Dvd', 'IMDB002', NULL, 'The Matrix', 'Warner Bros.', 16, 'USA'),
(13, 2, 'Dvd', 'IMDB003', NULL, 'Spirited Away', 'Studio Ghibli', 10, 'Japan'),
(14, 3, 'Dvd', 'IMDB004', NULL, 'The Godfather', 'Paramount', 18, 'USA'),
(15, 4, 'Dvd', 'IMDB005', NULL, 'Interstellar', 'Paramount', 13, 'USA');

INSERT INTO copy (item_id, is_reference, date_added, status) VALUES
(1, FALSE, '2023-01-01', 'available'),
(2, TRUE, '2023-01-15', 'available'),
(3, FALSE, '2023-02-10', 'available'),
(4, FALSE, '2023-03-05', 'available'),
(5, FALSE, '2023-03-20', 'available'),
(6, TRUE, '2023-04-10', 'available'),
(7, FALSE, '2023-05-05', 'available'),
(8, FALSE, '2023-06-01', 'available'),
(9, TRUE, '2023-06-15', 'available'),
(10, FALSE, '2023-07-01', 'available'),
(11, FALSE, '2024-01-01', 'available'),
(12, FALSE, '2024-01-10', 'available'),
(13, TRUE, '2024-02-01', 'available'),
(14, FALSE, '2024-02-15', 'available'),
(15, FALSE, '2024-03-01', 'available');

INSERT INTO user_profile (borrower_type, f_name, l_name, phone, address) VALUES
('student', 'Alice', 'Johnson', '555-1234', '123 Main St'),
('faculty', 'Bob', 'Smith', '555-2345', '234 Oak Ave'),
('public', 'Charlie', 'Brown', '555-3456', '345 Pine Rd'),
('student', 'Diana', 'Prince', '555-4567', '456 Maple St'),
('faculty', 'Ethan', 'Hunt', '555-5678', '567 Birch Ln'),
('public', 'Fiona', 'Gallagher', '555-6789', '678 Cedar St'),
('student', 'George', 'Martin', '555-7890', '789 Walnut Ave'),
('faculty', 'Helen', 'Mirren', '555-8901', '890 Elm St'),
('public', 'Ian', 'McKellen', '555-9012', '901 Spruce Blvd'),
('student', 'Jane', 'Doe', '555-0123', '012 Poplar Dr');

INSERT INTO user (profile_id, ssn, u_name, p_hashed_bcrypt, email, role) VALUES
(1, '123-45-6789', 'alicej', 'hashed_pw1', 'alice@example.com', 'borrower'),
(2, '234-56-7890', 'bobsmith', 'hashed_pw2', 'bob@example.com', 'borrower'),
(3, '345-67-8901', 'charlieb', 'hashed_pw3', 'charlie@example.com', 'borrower'),
(4, '456-78-9012', 'dianap', 'hashed_pw4', 'diana@example.com', 'borrower'),
(5, '567-89-0123', 'ethanh', 'hashed_pw5', 'ethan@example.com', 'borrower'),
(6, '678-90-1234', 'fionag', 'hashed_pw6', 'fiona@example.com', 'borrower'),
(7, '789-01-2345', 'georgem', 'hashed_pw7', 'george@example.com', 'borrower'),
(8, '890-12-3456', 'helenm', 'hashed_pw8', 'helen@example.com', 'admin'),
(9, '901-23-4567', 'ianm', 'hashed_pw9', 'ian@example.com', 'borrower'),
(10, '012-34-5678', 'janed', 'hashed_pw10', 'jane@example.com', 'borrower');

INSERT INTO item_creator (item_id, creator_id) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
(6, 6), (7, 7), (8, 8), (9, 9), (10, 10),
(11, 2), (12, 3), (13, 4), (14, 5), (15, 2);

INSERT INTO item_actor (item_id, actor_id) VALUES 
(11, 3), (12, 11), (12, 12), (13, 13), (14, 14), (15, 15); 

INSERT INTO item_genre (item_id, genre_id) VALUES
(1, 7), (2, 1), (3, 1), (4, 4), (5, 8),
(6, 2), (7, 3), (8, 1), (9, 6), (10, 1),
(11, 2), (12, 2), (13, 4), (14, 9), (15, 2);

INSERT INTO item_keyword (item_id, keyword_id) VALUES
(1, 6), (2, 10), (3, 2), (4, 1), (5, 9),
(6, 5), (7, 4), (8, 7), (9, 2), (10, 8),
(11, 3), (12, 10), (13, 1), (14, 9), (15, 5);