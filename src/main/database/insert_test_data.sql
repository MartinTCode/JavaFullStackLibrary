INSERT INTO creator (f_name, l_name, dob) 
VALUES 
    ('Jane', 'Austen', '1775-12-16'),
    ('George', 'Orwell', '1903-06-25'),
    ('Mary', 'Shelley', '1797-08-30'),
    ('Isaac', 'Asimov', '1920-01-02'),
    ('J.K.', 'Rowling', '1965-07-31'),
    ('Mark', 'Twain', '1835-11-30'),
    ('Agatha', 'Christie', '1890-09-15'),
    ('Ernest', 'Hemingway', '1899-07-21'),
    ('Haruki', 'Murakami', '1949-01-12'),
    ('Margaret', 'Atwood', '1939-11-18');


INSERT INTO item (type, identifier, identifier2, title, publisher, age_limit, country_of_production)
VALUES
    ('Book', '9780141439518', '0141439513', 'Pride and Prejudice', 'Penguin Classics', 12, 'United Kingdom'),
    ('Book', '9780451524935', '0451524934', '1984', 'Signet Classics', 14, 'United Kingdom'),
    ('Book', '9780141439471', '0141439475', 'Frankenstein', 'Penguin Classics', 12, 'United Kingdom'),
    ('Book', '9780553293357', '0553293354', 'Foundation', 'Spectra', 12, 'United States'),
    ('Book', '9780747532743', '0747532745', 'Harry Potter and the Philosopher''s Stone', 'Bloomsbury', 10, 'United Kingdom'),
    ('Book', '9780486280615', '0486280616', 'Adventures of Huckleberry Finn', 'Dover Publications', 12, 'United States'),
    ('Book', '9780062073488', '0062073486', 'Murder on the Orient Express', 'HarperCollins', 12, 'United Kingdom'),
    ('Book', '9780684801223', '0684801221', 'The Old Man and the Sea', 'Scribner', 12, 'United States'),
    ('Book', '9780099448822', '0099448823', 'Kafka on the Shore', 'Vintage', 16, 'Japan'),
    ('Book', '9780385490818', '038549081X', 'The Handmaid''s Tale', 'Anchor', 16, 'Canada'),
    ('Book', '9780140449136', '0140449132', 'Crime and Punishment', 'Penguin Classics', 16, 'Russia'),
    ('Book', '9780141439600', '0141439602', 'Wuthering Heights', 'Penguin Classics', 14, 'United Kingdom'),
    ('Book', '9780141182803', '0141182806', 'Brave New World', 'Penguin Books', 14, 'United Kingdom'),
    ('Book', '9780142437230', '0142437239', 'Moby-Dick', 'Penguin Classics', 14, 'United States'),
    ('Book', '9780316769488', '0316769487', 'The Catcher in the Rye', 'Little, Brown and Company', 14, 'United States'),
    ('Book', '9780679783268', '0679783261', 'To Kill a Mockingbird', 'Vintage', 12, 'United States'),
    ('Book', '9780141442464', '0141442468', 'Great Expectations', 'Penguin Classics', 12, 'United Kingdom'),
    ('Book', '9780451532084', '0451532082', 'Dracula', 'Signet Classics', 14, 'United Kingdom'),
    ('Book', '9780061120084', '0061120081', 'Fahrenheit 451', 'Harper Perennial', 14, 'United States'),
    ('Book', '9780141182537', '0141182539', 'The Trial', 'Penguin Books', 16, 'Germany'
);

INSERT INTO item_creator (item_id, creator_id)
VALUES
  (1, 1),   -- Pride and Prejudice → Jane Austen
  (2, 2),   -- 1984 → George Orwell
  (3, 3),   -- Frankenstein → Mary Shelley
  (4, 4),   -- Foundation → Isaac Asimov
  (5, 5),   -- Harry Potter → J.K. Rowling
  (6, 6),   -- Huckleberry Finn → Mark Twain
  (7, 7),   -- Murder on the Orient Express → Agatha Christie
  (8, 8),   -- Old Man and the Sea → Hemingway
  (9, 9),   -- Kafka on the Shore → Haruki Murakami
  (10, 10); -- The Handmaid's Tale → Margaret Atwood

INSERT INTO creator (f_name, l_name, dob)
VALUES
    ('Fyodor', 'Dostoevsky', '1821-11-11'),
    ('Emily', 'Brontë', '1818-07-30'),
    ('Aldous', 'Huxley', '1894-07-26'),
    ('Herman', 'Melville', '1819-08-01'),
    ('J.D.', 'Salinger', '1919-01-01'),
    ('Harper', 'Lee', '1926-04-28'),
    ('Charles', 'Dickens', '1812-02-07'),
    ('Bram', 'Stoker', '1847-11-08'),
    ('Ray', 'Bradbury', '1920-08-22'),
    ('Franz', 'Kafka', '1883-07-03');

INSERT INTO item_creator (item_id, creator_id)
VALUES
    (11, 11),  -- Crime and Punishment → Fyodor Dostoevsky
    (12, 12),  -- Wuthering Heights → Emily Brontë
    (13, 13),  -- Brave New World → Aldous Huxley
    (14, 14),  -- Moby-Dick → Herman Melville
    (15, 15),  -- The Catcher in the Rye → J.D. Salinger
    (16, 16),  -- To Kill a Mockingbird → Harper Lee
    (17, 17),  -- Great Expectations → Charles Dickens
    (18, 18),  -- Dracula → Bram Stoker
    (19, 19),  -- Fahrenheit 451 → Ray Bradbury
    (20, 20);  -- The Trial → Franz Kafka

-- add more books with same authors:

INSERT INTO item (type, identifier, identifier2, title, publisher, age_limit, country_of_production)
VALUES
    ('Book', '9780451526342', '0451526341', 'Animal Farm', 'Signet Classics', 12, 'United Kingdom'),
    ('Book', '9780156421171', '0156421178', 'Homage to Catalonia', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156262248', '015626224X', 'Down and Out in Paris and London', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156468992', '015646899X', 'Keep the Aspidistra Flying', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156767507', '0156767503', 'The Road to Wigan Pier', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156196253', '0156196255', 'Coming Up for Air', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156148504', '0156148501', 'Burmese Days', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780156185516', '0156185514', 'A Clergyman’s Daughter', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780151820436', '0151820430', 'Shooting an Elephant', 'Harvest Books', 14, 'United Kingdom'),
    ('Book', '9780060850524', '0060850523', 'Island', 'Harper Perennial', 14, 'United Kingdom'),
    ('Book', '9780099458180', '0099458181', 'Crome Yellow', 'Vintage', 14, 'United Kingdom'),
    ('Book', '9780099458210', '0099458211', 'Point Counter Point', 'Vintage', 14, 'United Kingdom'),
    ('Book', '9781564781404', '1564781409', 'Antic Hay', 'Dalkey Archive Press', 14, 'United Kingdom'),
    ('Book', '9781564781459', '156478145X', 'Eyeless in Gaza', 'Dalkey Archive Press', 14, 'United Kingdom'),
    ('Book', '9781564781442', '1564781441', 'After Many a Summer Dies the Swan', 'Dalkey Archive Press', 14, 'United Kingdom'),
    ('Book', '9780061729072', '0061729078', 'The Doors of Perception', 'Harper Perennial Modern Classics', 16, 'United Kingdom'),
    ('Book', '9781564781510', '1564781514', 'Time Must Have a Stop', 'Dalkey Archive Press', 14, 'United Kingdom'),
    ('Book', '9781564781503', '1564781506', 'Those Barren Leaves', 'Dalkey Archive Press', 14, 'United Kingdom'),
    ('Book', '9780060850524', '0060850523', 'Brave New World', 'Harper Perennial', 14, 'United Kingdom'),
    ('Book', '9780451524935', '0451524934', '1984', 'Signet Classics', 14, 'United Kingdom');

INSERT INTO item_creator (item_id, creator_id)
VALUES
    (21, 2),  -- Animal Farm
    (22, 2),  -- Homage to Catalonia
    (23, 2),  -- Down and Out in Paris and London
    (24, 2),  -- Keep the Aspidistra Flying
    (25, 2),  -- The Road to Wigan Pier
    (26, 2),  -- Coming Up for Air
    (27, 2),  -- Burmese Days
    (28, 2),  -- A Clergyman’s Daughter
    (29, 2),  -- Shooting an Elephant
    (30, 13), -- Island
    (31, 13), -- Crome Yellow
    (32, 13), -- Point Counter Point
    (33, 13), -- Antic Hay
    (34, 13), -- Eyeless in Gaza
    (35, 13), -- After Many a Summer Dies the Swan
    (36, 13), -- The Doors of Perception
    (37, 13), -- Time Must Have a Stop
    (38, 13), -- Those Barren Leaves
    (39, 13), -- Brave New World
    (40, 2);  -- 1984
