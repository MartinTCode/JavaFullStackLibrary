CREATE DATABASE library;
USE library;

CREATE TABLE item (
    item_id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    identifier VARCHAR(17),       -- e.g., ISBN-10, ISSN, IMDBC
    identifier2 VARCHAR(13),      -- e.g., ISBN-13
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(100),
    age_limit SMALLINT,
    country_of_production VARCHAR(100)
);

CREATE TABLE creator (
    creator_id SERIAL PRIMARY KEY,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    dob DATE
);

CREATE TABLE item_creator (
    item_id INT NOT NULL,
    creator_id INT NOT NULL,
    PRIMARY KEY (item_id, creator_id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item(item_id),
    CONSTRAINT fk_creator FOREIGN KEY (creator_id) REFERENCES creator(creator_id)
);
