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
    ('Margaret', 'Atwood', '1939-11-18'
);

INSERT INTO item (type, identifier, identifier2, title, publisher, age_limit, country_of_production)
VALUES
    ('Book', '9780141439518', '0141439513', 'Pride and Prejudice', 'Penguin Classics', 12, 'United Kingdom'),
    ('Book', '9780451524935', '0451524934', '1984', 'Signet Classics', 14, 'United Kingdom'),
    ('Book', '9780141439471', '0141439475', 'Frankenstein', 'Penguin Classics', 12, 'United Kingdom'),
    ('Book', '9780553293357', '0553293354', 'Foundation', 'Spectra', 12, 'United States'),
    ('Book', '9780747532743', '0747532745', 'Harry Potter and the Philosopher\'s Stone', 'Bloomsbury', 10, 'United Kingdom'),
    ('Book', '9780486280615', '0486280616', 'Adventures of Huckleberry Finn', 'Dover Publications', 12, 'United States'),
    ('Book', '9780062073488', '0062073486', 'Murder on the Orient Express', 'HarperCollins', 12, 'United Kingdom'),
    ('Book', '9780684801223', '0684801221', 'The Old Man and the Sea', 'Scribner', 12, 'United States'),
    ('Book', '9780099448822', '0099448823', 'Kafka on the Shore', 'Vintage', 16, 'Japan'),
    ('Book', '9780385490818', '038549081X', 'The Handmaid\'s Tale', 'Anchor', 16, 'Canada'),
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
  (10, 10 -- The Handmaid's Tale → Margaret Atwood
);