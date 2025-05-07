-- This script creates a PostgreSQL database and its tables for a library system.
-- It includes tables for languages, locations, creators, actors, genres, keywords, items,
-- item copies, user profiles, users, loans, and junction tables for many-to-many relationships.
-- The script also includes constraints to ensure data integrity and relationships between tables.
-- The script is designed to be run in a PostgreSQL environment.

-- TODO: ON DELETE POLCIES
-- TODO: ON UPDATE POLICIES

--CREATE DATABASE library;

-- truncate all tables and reset their SERIALS. Needed because next procedure does not reset the SERIALs (although it should).
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT schemaname, tablename 
        FROM pg_tables 
        WHERE schemaname NOT IN ('pg_catalog', 'information_schema')
    ) LOOP
        EXECUTE format('TRUNCATE TABLE %I.%I RESTART IDENTITY CASCADE', r.schemaname, r.tablename);
    END LOOP;
END $$;


-- DROP ALL TABLES FIRST SO THAT WE CAN RECREATE THEM
-- This is a PostgreSQL specific command to drop all tables in the current database.
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT schemaname, tablename 
        FROM pg_tables 
        WHERE schemaname NOT IN 
        ('pg_catalog', 'information_schema')
    ) LOOP
        EXECUTE format('DROP TABLE IF EXISTS %I.%I CASCADE', r.schemaname, r.tablename);
    END LOOP;
END $$;

CREATE TABLE language (
    language_id SERIAL PRIMARY KEY,
    language VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE location (
    location_id SERIAL PRIMARY KEY,
    floor VARCHAR(10) NOT NULL,
    section VARCHAR(50) NOT NULL,
    shelf VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    -- The location must be unique for each item copy
    -- This means that no two item copies can be in the same location at the same time.
    CONSTRAINT unique_location_attributes UNIQUE (
        floor, section, shelf, position)
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
    genre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE keyword (
    keyword_id SERIAL PRIMARY KEY,
    keyword VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE item (
    item_id SERIAL PRIMARY KEY,
    location_id INT REFERENCES location(location_id),
    language_id INT NOT NULL REFERENCES language(language_id),
    item_type VARCHAR(20) NOT NULL CHECK (
        item_type IN ('book', 'course_litterature', 'dvd', 'journal')
        ),
    identifier VARCHAR(17),       -- e.g., ISBN-10, ISSN, IMDBC
    identifier2 VARCHAR(13),      -- e.g., ISBN-13
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(100),
    age_limit SMALLINT,
    country_of_production VARCHAR(100),
    CONSTRAINT identifier_constraints CHECK (
        -- For 'book' or 'course_litterature', at least one of identifier or identifier2 must be NOT NULL
        (
            -- For 'book' or 'course_litterature', identifier or identifier2 must be NOT NULL
            item_type IN ('book', 'course_litterature') 
            AND 
            (
                identifier IS NOT NULL 
                OR identifier2 IS NOT NULL
            )
        ) 
        OR
        -- For 'journal', identifier must be NOT NULL
        (item_type = 'journal' AND identifier IS NOT NULL) 
        OR
        -- For 'dvd', both identifier and identifier2 can be NULL
        (item_type = 'dvd')
    )
);

CREATE TABLE item_copy (
    item_copy_id SERIAL PRIMARY KEY,
    barcode VARCHAR(20) NOT NULL UNIQUE,
    item_id INT NOT NULL REFERENCES item(item_id),
    is_reference BOOLEAN NOT NULL,
    date_added DATE NOT NULL,
    copy_status VARCHAR(10) NOT NULL DEFAULT 'available' CHECK (
        copy_status IN ('available', 'borrowed')
        )
);

CREATE TABLE user_profile (
    profile_id SERIAL PRIMARY KEY,
    user_type VARCHAR(20) NOT NULL CHECK (
        -- The user_type can be one of the following: public, student, researcher, university_employee
        user_type IN ('public', 'student', 'researcher', 'university_employee')
        ),
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15),
    full_address VARCHAR(255)
);

CREATE TABLE library_user (
    user_id SERIAL PRIMARY KEY, 
    profile_id INT REFERENCES user_profile(profile_id),
    ssn VARCHAR(12) UNIQUE, -- Social Security Number, used as user name for user_role borrower only
    u_name VARCHAR(50) UNIQUE, -- User name for every user_role except for borrower.
    p_hashed_bcrypt VARCHAR(255) NOT NULL, -- Password hashed with bcrypt
    email VARCHAR(100) NOT NULL UNIQUE,
    user_role VARCHAR(20) NOT NULL CHECK (
        -- The user_role can be one of the following: admin, librarian, borrower
        user_role IN ('admin', 'librarian', 'borrower')
        ),
    CONSTRAINT borrower_constraints CHECK (
        (
            -- For 'borrower', ssn must be NOT NULL and u_name must be NULL
            user_role = 'borrower' 
            AND ssn IS NOT NULL 
            AND u_name IS NULL 
            AND profile_id IS NOT NULL
        ) 
        OR
        (
            -- For 'admin' or 'librarian', ssn must be NULL and u_name must be NOT NULL
            user_role != 'borrower' 
            AND u_name IS NOT NULL 
            AND ssn IS NULL)
    )
);

CREATE TABLE loan (
    loan_id SERIAL PRIMARY KEY,
    item_copy_id INT NOT NULL REFERENCES item_copy(item_copy_id),
    user_id INT NOT NULL REFERENCES library_user(user_id),
    start_date DATE NOT NULL,
    return_date DATE NOT NULL CHECK (return_date > start_date),
    -- The returned_date can be NULL if the item is not yet returned
    returned_date DATE
);

-- JUNCTION TABLES
-- These tables are used to create many-to-many relationships between items and other entities.
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
