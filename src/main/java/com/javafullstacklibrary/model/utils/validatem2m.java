package com.javafullstacklibrary.model.utils;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Actor;

import java.util.Set;

/**
 * Utility class for validating collections in many-to-many relationships.
 * This class provides methods to validate collections of keywords, creators, genres, and actors.
 * It ensures that none of the collections are null, throwing an IllegalArgumentException if any are.
 * It also provides overloaded methods for cases where some collections may not be needed.
 */
public class validatem2m {

    // Method to validate collections for many-to-many relationships
    public static void validateCollections(Set<Keyword> keywords, Set<Creator> creators, Set<Genre> genres, Set<Actor> actors) {
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords collection cannot be null");
        }
        if (creators == null) {
            throw new IllegalArgumentException("Creators collection cannot be null");
        }
        if (genres == null) {
            throw new IllegalArgumentException("Genres collection cannot be null");
        }
        if (actors == null) {
            throw new IllegalArgumentException("Actors collection cannot be null");
        }
    }

    // Overloaded method for cases where actors are not needed
    public static void validateCollections(Set<Keyword> keywords, Set<Creator> creators, Set<Genre> genres) {
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords collection cannot be null");
        }
        if (creators == null) {
            throw new IllegalArgumentException("Creators collection cannot be null");
        }
        if (genres == null) {
            throw new IllegalArgumentException("Genres collection cannot be null");
        }
    }

    // Overloaded method for cases where genres and actors are not needed
    public static void validateCollections(Set<Keyword> keywords, Set<Creator> creators) {
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords collection cannot be null");
        }
        if (creators == null) {
            throw new IllegalArgumentException("Creators collection cannot be null");
        }
    }
}
