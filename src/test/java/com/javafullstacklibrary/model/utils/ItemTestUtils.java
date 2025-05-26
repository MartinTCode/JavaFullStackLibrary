package com.javafullstacklibrary.model.utils;

import com.javafullstacklibrary.model.Book;
import com.javafullstacklibrary.model.ItemTestParams;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Genre;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemTestUtils {

     /**
     * This method creates and persists a new book in the database.
     * It sets the properties of the book based on the provided parameters.
     *
     * @param em The EntityManager to use for database operations
     * @param params The parameters for creating the book
     * @param location The Location object associated with the book
     * @param language The Language object associated with the book
     * @param createdItemIds List to track created item IDs for cleanup
     * @return The created Book object
     */
    public static Book createAndPersistItem(EntityManager em, ItemTestParams params, Location location, Language language, List<Integer> createdItemIds) {
        // Create a new Book using the no-args constructor
        Book book = new Book();
        
        // Set book-specific attributes
        book.setISBN13(params.identifier());
        book.setISBN10(params.identifier2());
        book.setTitle(params.title());
        book.setPublisher(params.publisher());
        book.setLanguage(language);
        book.setLocation(location);
        
        // Initialize empty collections for M2M relationships
        book.setKeywords(new HashSet<>());
        book.setAuthors(new HashSet<>());
        book.setGenres(new HashSet<>());
        
        // Persist the book and track its ID
        em.persist(book);
        createdItemIds.add(book.getId());
        return book;
    }

    /**
     * Verifies that the book has the correct title.
     * It fetches the book from the database and checks that its title matches the expected value.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the book to verify
     * @param expectedTitle The expected title of the bookgetType
     */
    public static void verifyItemTitle(EntityManager em, Integer itemId, String expectedTitle) {
        Book book = em.find(Book.class, itemId);
        assertNotNull(book, "Book should not be null.");
        em.refresh(book);
        assertEquals(expectedTitle, book.getTitle(), "The book's title does not match the expected value.");
    }

    /**
     * Updates the title of a book in the database.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the book to update
     * @param newTitle The new title to set for the book
     */
    public static void updateItemTitle(EntityManager em, Integer itemId, String newTitle) {
        em.createQuery("UPDATE Book b SET b.title = :newTitle WHERE b.id = :itemId")
            .setParameter("newTitle", newTitle)
            .setParameter("itemId", itemId)
            .executeUpdate();
    }
    
    /**
     * Creates a book with the full constructor.
     * 
     * @param em The EntityManager to use for database operations
     * @param params The parameters for creating the book
     * @param location The Location object associated with the book
     * @param language The Language object associated with the book
     * @param keywords Set of keywords to associate with the book
     * @param creators Set of creators (authors) to associate with the book
     * @param genres Set of genres to associate with the book
     * @param createdItemIds List to track created item IDs for cleanup
     * @return The created Book object
     */
    public static Book createAndPersistBookWithFullConstructor(
            EntityManager em, 
            ItemTestParams params, 
            Location location, 
            Language language,
            Set<Keyword> keywords,
            Set<Creator> creators,
            Set<Genre> genres,
            List<Integer> createdItemIds) {
        
        // Create a new Book using the full constructor
        Book book = new Book(
            location,
            language,
            keywords,
            creators,
            genres,
            params.identifier(),  // isbn13
            params.identifier2(), // isbn10
            params.title(),
            params.publisher()
        );
        
        // Persist the book and track its ID
        em.persist(book);
        createdItemIds.add(book.getId());
        return book;
    }
}
