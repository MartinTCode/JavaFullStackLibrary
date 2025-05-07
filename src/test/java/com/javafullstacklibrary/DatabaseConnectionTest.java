package com.javafullstacklibrary;

// Importing all model classes from the package
import com.javafullstacklibrary.model.*;

// Importing necessary packages for JPA annotations
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

     // testparameters:
     static final boolean debug_flg = true; // Set to true to enable debug messages
     static final String language_set = "English";
     static final String item_title = "Test Item";



    @Test // Marks this method as a test case
    public void testDatabaseConnection() {
        // Create EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();

        // Variables to track persisted entities
        Location location = null;
        // Check if the language already exists
        Language language = em.createQuery("SELECT l FROM Language l WHERE l.language = :language", Language.class)
                            .setParameter("language", language_set)
                            .getResultStream()
                            .findFirst()
                            .orElse(null);
        Item item = null;

        try {
            // Begin a transaction for the entire test
            em.getTransaction().begin();

            // Create and persist Location
            debugPrint("Creating new location.");
            location = new Location();
            location.setFloor("1");
            location.setSection("A");
            location.setShelf("Shelf 1");
            location.setPosition("Position 1");
            em.persist(location);

            // Create and persist Language if not already exists
            if (language == null) {
                debugPrint("Language not found, creating new one.");
                language = new Language();
                language.setLanguage(language_set);
                em.persist(language);
            } else {
                debugPrint("Language already exists, using existing one.");
            }

            // Create and persist Item
            item = new Item();
            debugPrint("Creating new item.");
            item.setType("book");
            item.setIdentifier("1234567890");
            item.setIdentifier2("1234567890123");
            debugPrint("Created new item with identifier: " + item.getIdentifier());
            debugPrint("Created new item with identifier2: " + item.getIdentifier2());
            debugPrint("Setting title for the item:" + item_title);
            item.setTitle(item_title);
            item.setPublisher("Test Publisher");
            item.setAgeLimit((short) 18);
            item.setCountryOfProduction("Test Country");
            debugPrint("Setting FK:s location and language for the item.");
            item.setLocation(location); // Set foreign key
            item.setLanguage(language); // Set foreign key
            em.persist(item);

            // Commit the transaction to persist the data
            debugPrint("Committing transaction.");
            em.getTransaction().commit();

            // Verify that the item was saved successfully
            debugPrint("Verifying that the item was saved successfully.");
            assertNotNull(item.getItemId()); // Check that the ID is not null (it was generated)

            // Retrieve the item from the database
            debugPrint("Retrieving the item from the database.");
            Item retrievedItem = em.find(Item.class, item.getItemId());
            debugPrint("Retrieved item: " + retrievedItem.toString());
            assertNotNull(retrievedItem); // Ensure the item was retrieved
            debugPrint("Retrieved item ID: " + retrievedItem.getTitle());
            assertEquals(item_title, retrievedItem.getTitle()); // Check that the title matches

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                debugPrint("Transaction failed, rolling back.");
                em.getTransaction().rollback(); // Rollback the transaction if it is active
            }
            fail("Transaction failed and was rolled back: " + e.getMessage());
        } finally {
            // Rollback the test data
            debugPrint("Cleaning up test data.");
            // FIXME: cannot delete due to no ON DELETE CASCADE in DB. Needs to be fixed in DB.
            // TODO: Modularize this code to a separate method
            if (item != null || location != null || language != null) {
                em.getTransaction().begin(); // Start a new transaction for cleanup
        
                // Remove the test data in reverse order of dependencies
                if (item != null) {
                    debugPrint("Removing item.");
                    item = em.find(Item.class, item.getItemId()); // Fetch the managed entity
                    if (item != null) {
                        em.remove(item);
                    }
                }
                if (location != null) {
                    debugPrint("Removing location.");
                    location = em.find(Location.class, location.getLocationId()); // Fetch the managed entity
                    if (location != null) {
                        em.remove(location);
                    }
                }
                if (language != null) {
                    debugPrint("Removing language.");
                    language = em.find(Language.class, language.getLanguageId()); // Fetch the managed entity
                    if (language != null) {
                        em.remove(language);
                    }
                }
        
                em.getTransaction().commit(); // Commit the cleanup transaction
            }
            // Close the EntityManager and EntityManagerFactory
            debugPrint("Closing EntityManager and EntityManagerFactory.");
            em.close();
            emf.close();
        }
    }

    private void debugPrint(String message) {
        if (debug_flg) {
            System.out.println(message);
        }
    }
}
