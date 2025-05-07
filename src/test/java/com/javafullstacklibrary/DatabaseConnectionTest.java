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

import java.util.Map;

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
    // Global dictionary for test parameters
    static final Map<String, Object> testParams = Map.ofEntries(
        Map.entry("itemType", "book"),
        Map.entry("identifier", "1234567890"),
        Map.entry("identifier2", "1234567890123"),
        Map.entry("title", "Test Item"),
        Map.entry("publisher", "Test Publisher"),
        Map.entry("ageLimit", (short) 18),
        Map.entry("countryOfProduction", "Test Country"),
        Map.entry("floor", "1"),
        Map.entry("section", "A"),
        Map.entry("shelf", "Shelf 1"),
        Map.entry("position", "Position 1"),
        Map.entry("language", "English")
    );

    @Test // Marks this method as a test case
    public void testDatabaseConnection() {
        // Create EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();

        // Variables to track persisted entities
        Location location = null;
        Language language = null;
        Item item = null;

        try {
            // Begin a transaction for the entire test
            em.getTransaction().begin();

            // Check if the Location already exists
            debugPrint("Checking if location already exists.");
            location = em.createQuery(
                    "SELECT l FROM Location l WHERE l.floor = :floor AND l.section = :section AND l.shelf = :shelf AND l.position = :position",
                    Location.class)
                .setParameter("floor", (String) testParams.get("floor"))
                .setParameter("section", (String) testParams.get("section"))
                .setParameter("shelf", (String) testParams.get("shelf"))
                .setParameter("position", (String) testParams.get("position"))
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (location == null) {
                debugPrint("Location not found, creating new one.");
                location = new Location();
                location.setFloor((String) testParams.get("floor"));
                location.setSection((String) testParams.get("section"));
                location.setShelf((String) testParams.get("shelf"));
                location.setPosition((String) testParams.get("position"));
                em.persist(location);
            } else {
                debugPrint("Location already exists, using existing one.");
            }

            // Check if the Language already exists
            debugPrint("Checking if language already exists.");
            language = em.createQuery("SELECT l FROM Language l WHERE l.language = :language", Language.class)
                    .setParameter("language", (String) testParams.get("language"))
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (language == null) {
                debugPrint("Language not found, creating new one.");
                language = new Language();
                language.setLanguage((String) testParams.get("language"));
                em.persist(language);
            } else {
                debugPrint("Language already exists, using existing one.");
            }

            // Create and persist Item
            item = new Item();
            debugPrint("Creating new item.");
            item.setType((String) testParams.get("itemType"));
            item.setIdentifier((String) testParams.get("identifier"));
            item.setIdentifier2((String) testParams.get("identifier2"));
            debugPrint("Created new item with identifier: " + item.getIdentifier());
            debugPrint("Created new item with identifier2: " + item.getIdentifier2());
            debugPrint("Setting title for the item: " + testParams.get("title"));
            item.setTitle((String) testParams.get("title"));
            item.setPublisher((String) testParams.get("publisher"));
            item.setAgeLimit((Short) testParams.get("ageLimit"));
            item.setCountryOfProduction((String) testParams.get("countryOfProduction"));
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
            assertEquals(testParams.get("title"), retrievedItem.getTitle()); // Check that the title matches

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
