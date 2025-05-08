package com.javafullstacklibrary;

import com.javafullstacklibrary.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseItemCRUDTest {

    // EntityManager and EntityManagerFactory for database operations
    // These are used to manage the persistence context and perform CRUD operations
    // on the database entities.
    private EntityManagerFactory emf;
    private EntityManager em;

    // Lists to keep track of created item, location, and language IDs
    // These lists are used to store the IDs of the entities created during the test
    // so that they can be cleaned up after the test is completed.
    // This is important to avoid leaving test data in the database after the tests are run.
    private List<Integer> createdItemIds = new ArrayList<>();
    private List<Integer> createdLocationIds = new ArrayList<>();
    private List<Integer> createdLanguageIds = new ArrayList<>();

    static final boolean debug_flg = true; // Set to true to enable debug messages
    
    // The test parameters for the item to be created
    static final ItemTParams ItemTParams = new ItemTParams(
        "book",
        "1234567890",
        "1234567890123",
        "Test Item",
        "Test Item Updated",
        "Test Publisher",
        (short) 18,
        "Test Country",
        "1",
        "A",
        "Shelf 1",
        "Position 1",
        "English"
    );

    @BeforeEach
    public void setUp() {
        debugPrint("Setting up EntityManager and starting a transaction.");
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void tearDown() {
        debugPrint("Cleaning up test data and closing EntityManager.");
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        cleanupTestData(em);
        em.close();
        emf.close();
    }

    @Test
    public void testDatabaseConnection() {
        try {
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
            updateItemTitle(em, item.getItemId(), ItemTParams.titleUpdated());
            em.getTransaction().commit();

            // TESTCASE GROUP 2: Fetch and verify the updated item
            verifyUpdatedItem(em, item.getItemId());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                debugPrint("Transaction failed, rolling back.");
                em.getTransaction().rollback();
            }
            fail("Transaction failed and was rolled back: " + e.getMessage());
        }
    }

    private void cleanupTestData(EntityManager em) {
        debugPrint("Cleaning up test data.");
        em.getTransaction().begin();

        if (!createdItemIds.isEmpty()) {
            debugPrint("Deleting items with IDs: " + createdItemIds);
            em.createQuery("DELETE FROM Item i WHERE i.itemId IN :ids")
                .setParameter("ids", createdItemIds)
                .executeUpdate();
        }

        if (!createdLocationIds.isEmpty()) {
            debugPrint("Deleting locations with IDs: " + createdLocationIds);
            em.createQuery("DELETE FROM Location l WHERE l.locationId IN :ids")
                .setParameter("ids", createdLocationIds)
                .executeUpdate();
        }

        if (!createdLanguageIds.isEmpty()) {
            debugPrint("Deleting languages with IDs: " + createdLanguageIds);
            em.createQuery("DELETE FROM Language l WHERE l.languageId IN :ids")
                .setParameter("ids", createdLanguageIds)
                .executeUpdate();
        }

        em.getTransaction().commit();
    }

    private void debugPrint(String message) {
        if (debug_flg) {
            System.out.println(message);
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
            .setParameter("floor", ItemTParams.floor())
            .setParameter("section", ItemTParams.section())
            .setParameter("shelf", ItemTParams.shelf())
            .setParameter("position", ItemTParams.position())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (location == null) {
            debugPrint("Location not found, creating new one.");
            location = new Location();
            location.setFloor(ItemTParams.floor());
            location.setSection(ItemTParams.section());
            location.setShelf(ItemTParams.shelf());
            location.setPosition(ItemTParams.position());
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
            .setParameter("language", ItemTParams.language())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (language == null) {
            debugPrint("Language not found, creating new one.");
            language = new Language();
            language.setLanguage(ItemTParams.language());
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
        item.setType(ItemTParams.itemType());
        item.setIdentifier(ItemTParams.identifier());
        item.setIdentifier2(ItemTParams.identifier2());
        item.setTitle(ItemTParams.title());
        item.setPublisher(ItemTParams.publisher());
        item.setAgeLimit(ItemTParams.ageLimit());
        item.setCountryOfProduction(ItemTParams.countryOfProduction());
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
        assertEquals(ItemTParams.title(), updatedItem.getTitle());
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

        assertEquals(ItemTParams.titleUpdated(), updatedItem.getTitle(), "The updated title does not match.");
        debugPrint("Verified updated item title: " + updatedItem.getTitle());
    }
}
