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

-- Creates a new database named 'library' and connects to it
CREATE DATABASE library;
\c library