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

        try {
            // Begin a transaction for the entire test
            em.getTransaction().begin();

            // Check or create Location
            Location location = findOrCreateLocation(em);

            // Check or create Language
            Language language = findOrCreateLanguage(em);

            // Create and persist Item
            Item item = createAndPersistItem(em, location, language);

            // Commit the transaction
            debugPrint("Committing transaction.");
            em.getTransaction().commit();

            // Verify the item was saved successfully
            verifyItem(em, item);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                debugPrint("Transaction failed, rolling back.");
                em.getTransaction().rollback();
            }
            fail("Transaction failed and was rolled back: " + e.getMessage());
        } finally {
            cleanupTestData(em);
            em.close();
            emf.close();
        }
    }

    private Location findOrCreateLocation(EntityManager em) {
        debugPrint("Checking if location already exists.");
        Location location = em.createQuery(
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
        return location;
    }

    private Language findOrCreateLanguage(EntityManager em) {
        debugPrint("Checking if language already exists.");
        Language language = em.createQuery(
                "SELECT l FROM Language l WHERE l.language = :language",
                Language.class)
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
        return language;
    }

    private Item createAndPersistItem(EntityManager em, Location location, Language language) {
        debugPrint("Creating new item.");
        Item item = new Item();
        item.setType((String) testParams.get("itemType"));
        item.setIdentifier((String) testParams.get("identifier"));
        item.setIdentifier2((String) testParams.get("identifier2"));
        item.setTitle((String) testParams.get("title"));
        item.setPublisher((String) testParams.get("publisher"));
        item.setAgeLimit((Short) testParams.get("ageLimit"));
        item.setCountryOfProduction((String) testParams.get("countryOfProduction"));
        item.setLocation(location);
        item.setLanguage(language);
        em.persist(item);
        debugPrint("Created new item with identifier: " + item.getIdentifier());
        return item;
    }

    private void verifyItem(EntityManager em, Item item) {
        debugPrint("Verifying that the item was saved successfully.");
        assertNotNull(item.getItemId());
        Item retrievedItem = em.find(Item.class, item.getItemId());
        assertNotNull(retrievedItem);
        assertEquals(testParams.get("title"), retrievedItem.getTitle());
        debugPrint("Verified item: " + retrievedItem.toString());
    }

    private void cleanupTestData(EntityManager em) {
        debugPrint("Cleaning up test data.");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Item").executeUpdate();
        em.createQuery("DELETE FROM Location").executeUpdate();
        em.createQuery("DELETE FROM Language").executeUpdate();
        em.getTransaction().commit();
    }

    private void debugPrint(String message) {
        if (debug_flg) {
            System.out.println(message);
        }
    }
}
