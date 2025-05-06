package com.javafullstacklibrary;

// Importing the Book entity class for testing
import com.javafullstacklibrary.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Importing JUnit 5's Test annotation for marking test methods
import org.junit.jupiter.api.Test;
// Importing JUnit 5's assertion methods for testing
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for verifying the database connection.
 * It uses Spring Boot's testing framework to load the application context
 * and perform database operations.
 */
public class DatabaseConnectionTest {
    /**
     * This test method verifies that the application can connect to the database
     * and perform basic CRUD operations.
     */
    @Test // Marks this method as a test case
    public void testDatabaseConnection() {
        // Create EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Create a new Item object
            Item item = new Item();
            item.setType("Book");
            item.setIdentifier("1234567890");
            item.setIdentifier2("1234567890123");
            item.setTitle("Test Item");
            item.setPublisher("Test Publisher");
            item.setAgeLimit((short) 18);
            item.setCountryOfProduction("Test Country");

            // Save the item to the database
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();

            // Verify that the item was saved successfully
            assertNotNull(item.getItemId()); // Check that the ID is not null (it was generated)

            // Retrieve the item from the database
            Item retrievedItem = em.find(Item.class, item.getItemId());
            assertNotNull(retrievedItem); // Ensure the item was retrieved
            assertEquals("Test Item", retrievedItem.getTitle()); // Check that the title matches

        } finally {
            // Close EntityManager and EntityManagerFactory
            em.close();
            emf.close();
        }
    }
}
