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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is a test class for verifying the database connection.
 * It uses Spring Boot's testing framework to load the application context
 * and perform database operations.
 */
public class DatabaseItemCRUDTest {
    /**
     * This test method verifies that the application can connect to the database
     * and perform basic CRUD operations.
     */


    private List<Integer> createdItemIds = new ArrayList<>();
    private List<Integer> createdLocationIds = new ArrayList<>();
    private List<Integer> createdLanguageIds = new ArrayList<>();

    // testparameters:
    static final boolean debug_flg = true; // Set to true to enable debug messages
    // Global dictionary for test parameters
    static final Map<String, Object> testParams = Map.ofEntries(
        Map.entry("itemType", "book"),
        Map.entry("identifier", "1234567890"),
        Map.entry("identifier2", "1234567890123"),
        Map.entry("title", "Test Item"),
        Map.entry("title_updated", "Test Item Updated"),
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
            debugPrint(">>>>Created item with ID: " + location.getLocationId());

            // Check or create Language
            Language language = findOrCreateLanguage(em);
            debugPrint(">>>>Created item with ID: " + language.getLanguageId());

            // Create and persist Item
            Item item = createAndPersistItem(em, location, language);
            debugPrint(">>>>Created item with ID: " + item.getItemId());

            // Commit the transaction
            debugPrint("Committing transaction.");
            em.getTransaction().commit();

            // TESTCASE GROUP 1: Verify the item was saved successfully
            verifyItem(em, item);

            // Update the title of the item
            em.getTransaction().begin();
            updateItemTitle(em, item.getItemId(), (String) testParams.get("title_updated"));
            em.getTransaction().commit();

            // TESTCASE GROUP 2: Fetch and verify the updated item
            verifyUpdatedItem(em, item.getItemId());

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

    /**
     * This method finds or creates a location in the database.
     * It checks if a location with the specified parameters already exists,
     * and if not, it creates a new one.
     *
     * @param em The EntityManager to use for database operations
     * @return The found or created Location object
     */
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
            createdLocationIds.add(location.getLocationId()); // Store the created location ID for cleanup
            debugPrint("Created new item with identifier: " + location.getLocationId());
        } else {
            debugPrint("Location already exists, using existing one.");
        }
        return location;
    }

    /**
     * This method finds or creates a language in the database.
     * It checks if a language with the specified parameters already exists,
     * and if not, it creates a new one.
     *
     * @param em The EntityManager to use for database operations
     * @return The found or created Language object
     */
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
            createdLanguageIds.add(language.getLanguageId()); // Store the created language ID for cleanup
        } else {
            debugPrint("Language already exists, using existing one.");
        }
        return language;
    }

    /**
     * This method creates and persists a new item in the database.
     * It sets the properties of the item based on the provided parameters.
     *
     * @param em The EntityManager to use for database operations
     * @param location The Location object associated with the item
     * @param language The Language object associated with the item
     * @return The created Item object
     */
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
        createdItemIds.add(item.getItemId()); // Store the created item ID for cleanup
        debugPrint("Created new item with identifier: " + item.getIdentifier());
        return item;
    }

    /**
     * Verifies that the item was saved successfully in the database.
     * It checks that the item exists and that its title matches the expected value.
     *
     * @param em The EntityManager to use for database operations
     * @param item The Item object to verify
     */
    private void verifyItem(EntityManager em, Item item) {
        debugPrint("Verifying that the item was saved successfully.");
        // Check if the item exists in the database
        assertNotNull(item.getItemId());
        Item updatedItem = em.find(Item.class, item.getItemId());
        // Check if the item was saved successfully
        assertNotNull(updatedItem);

        // Check if the title matches the expected value
        assertEquals(testParams.get("title"), updatedItem.getTitle());
        debugPrint("Verified item: " + updatedItem.getTitle());
    }

    /**
     * Updates the title of an item in the database.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the item to update
     * @param newTitle The new title to set for the item
     */
    private void updateItemTitle(EntityManager em, Integer itemId, String newTitle) {
        debugPrint("Updating title of item with ID: " + itemId + " to: " + newTitle);
        int updatedCount = em.createQuery("UPDATE Item i SET i.title = :newTitle WHERE i.itemId = :itemId")
            .setParameter("newTitle", newTitle)
            .setParameter("itemId", itemId)
            .executeUpdate();
        debugPrint("Updated " + updatedCount + " record(s) in the Item table.");
    }

    /**
     * Verifies that the updated item has the correct title.
     * It fetches the item from the database and checks that its title matches the updated value.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the item to verify
     */
    private void verifyUpdatedItem(EntityManager em, Integer itemId) {
        debugPrint("Fetching updated item with ID: " + itemId);
        Item updatedItem = em.find(Item.class, itemId);
        assertNotNull(updatedItem, "Updated item should not be null.");

        // Refresh the entity to ensure it has the latest state from the database (not cached value)
        em.refresh(updatedItem);

        assertEquals(testParams.get("title_updated"), updatedItem.getTitle(), "The updated title does not match.");
        debugPrint("Verified updated item title: " + updatedItem.getTitle());
    }

    /**
     * Cleans up test data created during the test by deleting items, locations, and languages.
     * It ensures that only the data created during the test is removed from the database.
     *
     * @param em The EntityManager to use for database operations
     */
    private void cleanupTestData(EntityManager em) {
        debugPrint("Cleaning up test data.");
        em.getTransaction().begin();

        // Delete items created during the test
        if (!createdItemIds.isEmpty()) {
            debugPrint("Deleting items with IDs: " + createdItemIds);
            int deletedItems = em.createQuery("DELETE FROM Item i WHERE i.itemId IN :ids")
                .setParameter("ids", createdItemIds)
                .executeUpdate();
            debugPrint("Deleted " + deletedItems + " records from the Item table.");
        }
        
        // Delete locations created during the test
        if (!createdLocationIds.isEmpty()) {
            debugPrint("Deleting locations with IDs: " + createdLocationIds);
            int deletedLocations = em.createQuery("DELETE FROM Location l WHERE l.locationId IN :ids")
                .setParameter("ids", createdLocationIds)
                .executeUpdate();
            debugPrint("Deleted " + deletedLocations + " records from the Location table.");
        }

        // Delete languages created during the test
        if (!createdLanguageIds.isEmpty()) {
            debugPrint("Deleting languages with IDs: " + createdLanguageIds);
            int deletedLanguages = em.createQuery("DELETE FROM Language l WHERE l.languageId IN :ids")
                .setParameter("ids", createdLanguageIds)
                .executeUpdate();
            debugPrint("Deleted " + deletedLanguages + " records from the Language table.");
        }
        em.getTransaction().commit();
        debugPrint("Test data cleaned up.");
    }

    /**
     * Prints debug messages to the console if debugging is enabled.
     *
     * @param message The message to print
     */
    private void debugPrint(String message) {
        if (debug_flg) {
            System.out.println(message);
        }
    }
}
