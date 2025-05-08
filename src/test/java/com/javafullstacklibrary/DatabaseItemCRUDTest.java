package com.javafullstacklibrary;

import com.javafullstacklibrary.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains test cases for performing CRUD operations on items in the database.
 * It uses JUnit 5 for testing and Jakarta Persistence (JPA) for database interactions.
 * The tests include creating, updating, and verifying items in the library system.
 */
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

    static final boolean DEBUG_FLAG = true; // Set to true to enable debug messages

    // The test parameters for the item to be created
    static final ItemTestParams ITEM_TEST_PARAMS = new ItemTestParams(
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
    /**
     * This method is called after each test case.
     * It rolls back any uncommitted changes to maintain a clean database state
     * and closes the EntityManager and EntityManagerFactory.
     */
    public void tearDown() {
        debugPrint("Cleaning up test data and closing EntityManager.");
        // Rollback any uncommitted changes to maintain a clean database state.
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        cleanupTestData(em);
        em.close();
        emf.close();

        debugPrint("Test data cleaned up and EntityManager closed.");
    }

    // Helper methods for shared setup
    private Location initalizeOrFetchLocation() {
        Location location = findOrCreateLocation(em);
        assertNotNull(location, "Location should not be null.");
        debugPrint("Setup location with ID: " + location.getId());
        return location;
    }

    private Language initalizeOrFetchLanguage() {
        Language language = findOrCreateLanguage(em);
        assertNotNull(language, "Language should not be null.");
        debugPrint("Setup language with ID: " + language.getId());
        return language;
    }

    // Test for creating a Location
    @Test
    @DisplayName("Test Creating New Location Or Fetching Existing Location")
    public void testCreateLocation() {
        Location location = initalizeOrFetchLocation();
        assertNotNull(location.getId(), "Location ID should not be null.");
        debugPrint("Test for creating location passed.");
    }

    // Test for creating a Language
    @Test
    @DisplayName("Test Creating New Language Or Fetching Existing Language")
    public void testCreateLanguage() {
        Language language = initalizeOrFetchLanguage();
        assertNotNull(language.getId(), "Language ID should not be null.");
        debugPrint("Test for creating language passed.");
    }

    // Test for creating an Item
    @Test
    @DisplayName("Test Creating New Item using Location and Language")
    public void testCreateItem() {
        Location location = initalizeOrFetchLocation();
        Language language = initalizeOrFetchLanguage();
        Item item = createAndPersistItem(em, location, language);
        assertNotNull(item.getId(), "Item ID should not be null.");
        verifyItemTitle(em, item.getId(), ITEM_TEST_PARAMS.title());
        debugPrint("Test for creating item passed.");
    }

    // Test for updating an Item
    @Test
    public void testUpdateItemTitle() {
        // Arrange
        Location location = initalizeOrFetchLocation();
        Language language = initalizeOrFetchLanguage();
        Item item = createAndPersistItem(em, location, language);
        // Commit the transaction to persist the Item
        em.getTransaction().commit();

        // Act
        em.getTransaction().begin();
        updateItemTitle(em, item.getId(), ITEM_TEST_PARAMS.titleUpdated());
        em.getTransaction().commit();

        // Assert
        verifyItemTitle(em, item.getId(), ITEM_TEST_PARAMS.titleUpdated());
        debugPrint("Test for updating item title passed.");
    }

    /** 
     * This helper method is used to clean up entities from the database.
     * It deletes entities of the specified type based on their IDs.
     * 
     * @param em The EntityManager to use for database operations
     * @param entityName The name of the entity type to delete
     * @param ids The list of IDs of the entities to delete
     */
    private void cleanupEntities(EntityManager em, String entityName, List<Integer> ids) {
        if (!ids.isEmpty()) {
            debugPrint("Deleting " + entityName + "s with IDs: " + ids);
            em.createQuery("DELETE FROM " + entityName + " e WHERE e.id IN :ids")
                .setParameter("ids", ids)
                .executeUpdate();
        }
    }

    /**
     * This method cleans up test data from the database.
     * It deletes any items, locations, and languages that were created during the test.
     *
     * @param em The EntityManager to use for database operations
     */
    private void cleanupTestData(EntityManager em) {
        debugPrint("Cleaning up test data.");
        em.getTransaction().begin();
        cleanupEntities(em, "Item", createdItemIds);
        cleanupEntities(em, "Location", createdLocationIds);
        cleanupEntities(em, "Language", createdLanguageIds);
        em.getTransaction().commit();
    }

    // Debug print helper
    private void debugPrint(String message) {
        if (DEBUG_FLAG) {
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
            .setParameter("floor", ITEM_TEST_PARAMS.floor())
            .setParameter("section", ITEM_TEST_PARAMS.section())
            .setParameter("shelf", ITEM_TEST_PARAMS.shelf())
            .setParameter("position", ITEM_TEST_PARAMS.position())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (location == null) {
            debugPrint("Location not found, creating new one.");
            location = new Location();
            location.setFloor(ITEM_TEST_PARAMS.floor());
            location.setSection(ITEM_TEST_PARAMS.section());
            location.setShelf(ITEM_TEST_PARAMS.shelf());
            location.setPosition(ITEM_TEST_PARAMS.position());
            em.persist(location);
            createdLocationIds.add(location.getId());
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
            .setParameter("language", ITEM_TEST_PARAMS.language())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (language == null) {
            debugPrint("Language not found, creating new one.");
            language = new Language();
            language.setLanguage(ITEM_TEST_PARAMS.language());
            em.persist(language);
            createdLanguageIds.add(language.getId());
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
        item.setType(ITEM_TEST_PARAMS.itemType());
        item.setIdentifier(ITEM_TEST_PARAMS.identifier());
        item.setIdentifier2(ITEM_TEST_PARAMS.identifier2());
        item.setTitle(ITEM_TEST_PARAMS.title());
        item.setPublisher(ITEM_TEST_PARAMS.publisher());
        item.setAgeLimit(ITEM_TEST_PARAMS.ageLimit());
        item.setCountryOfProduction(ITEM_TEST_PARAMS.countryOfProduction());
        item.setLocation(location);
        item.setLanguage(language);
        em.persist(item);
        createdItemIds.add(item.getId());
        debugPrint("Created new item with identifier: " + item.getIdentifier());
        return item;
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
        int updatedCount = em.createQuery("UPDATE Item i SET i.title = :newTitle WHERE i.id = :itemId")
            .setParameter("newTitle", newTitle)
            .setParameter("itemId", itemId)
            .executeUpdate();
        debugPrint("Updated " + updatedCount + " record(s) in the Item table.");
    }

    /**
     * Verifies that the item has the correct title.
     * It fetches the item from the database and checks that its title matches the expected value.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the item to verify
     * @param expectedTitle The expected title of the item
     */
    private void verifyItemTitle(EntityManager em, Integer itemId, String expectedTitle) {
        debugPrint("Fetching item with ID: " + itemId);
        Item item = em.find(Item.class, itemId);
        assertNotNull(item, "Item should not be null.");
        em.refresh(item);
        assertEquals(expectedTitle, item.getTitle(), "The item's title does not match the expected value.");
        debugPrint("Verified item title: " + item.getTitle());
    }
}
