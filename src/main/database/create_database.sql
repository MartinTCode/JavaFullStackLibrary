CREATE DATABASE library;

CREATE TABLE language (
    language_id SERIAL PRIMARY KEY,
    language VARCHAR(100) NOT NULL
);

CREATE TABLE location (
    location_id SERIAL PRIMARY KEY,
    floor VARCHAR(10),
    section VARCHAR(50),
    shelf VARCHAR(50),
    position VARCHAR(50)
);

CREATE TABLE creator (
    creator_id SERIAL PRIMARY KEY,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    dob DATE
);

CREATE TABLE actor (
    actor_id SERIAL PRIMARY KEY,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    dob DATE
);

CREATE TABLE genre (
    genre_id SERIAL PRIMARY KEY,
    genre VARCHAR(100) NOT NULL
);

CREATE TABLE keyword (
    keyword_id SERIAL PRIMARY KEY,
    keyword VARCHAR(100) NOT NULL
);

CREATE TABLE item (
    item_id SERIAL PRIMARY KEY,
    location_id INT REFERENCES location(location_id),
    language_id INT REFERENCES language(language_id),
    type VARCHAR(50) NOT NULL,
    identifier VARCHAR(17),       -- e.g., ISBN-10, ISSN, IMDBC
    identifier2 VARCHAR(13),      -- e.g., ISBN-13
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(100),
    age_limit SMALLINT,
    country_of_production VARCHAR(100)
);

CREATE TYPE copy_status AS ENUM ('available', 'borrowed', 'reserved', 'lost');

CREATE TABLE copy (
    barcode SERIAL PRIMARY KEY,
    item_id INT REFERENCES item(item_id),
    is_reference BOOLEAN,
    date_added DATE,
    status copy_status
);

CREATE TABLE user_profile (
	profile_id SERIAL PRIMARY KEY,
	borrower_type VARCHAR,
	f_name VARCHAR,
	l_name VARCHAR,
	phone VARCHAR,
	address VARCHAR
);

CREATE TABLE user (
	user_id SERIAL PRIMARY KEY, 
	profile_id INT NOT NULL REFERENCES user_profile(profile_id),
	ssn VARCHAR (12), 
	u_name VARCHAR, 
	p_hashed_bcrypt VARCHAR,
	email VARCHAR,
	role TEXT
);

CREATE TABLE loan (
	loan_id SERIAL PRIMARY KEY,
	barcode VARCHAR REFERENCES copy(barcode),
	user_id INT REFERENCES user(user_id),
	start_date DATE,
	return_date DATE,
	returned_date DATE
);
	

CREATE TABLE item_creator (
    item_id INT NOT NULL REFERENCES item(item_id),
    creator_id INT NOT NULL REFERENCES creator(creator_id),
    PRIMARY KEY (item_id, creator_id)
);

CREATE TABLE item_actor (
    item_id INT NOT NULL REFERENCES item(item_id),
    actor_id INT NOT NULL REFERENCES actor(actor_id),
    PRIMARY KEY (item_id, actor_id)
);

CREATE TABLE item_genre (
    item_id INT NOT NULL REFERENCES item(item_id),
    genre_id INT NOT NULL REFERENCES genre(genre_id),
    PRIMARY KEY (item_id, genre_id)
);

CREATE TABLE item_keyword (
    item_id INT NOT NULL REFERENCES item(item_id),
    keyword_id INT NOT NULL REFERENCES keyword(keyword_id),
    PRIMARY KEY (item_id, keyword_id)
);
