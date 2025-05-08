package com.javafullstacklibrary;

import com.javafullstacklibrary.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD test suite for verifying database connectivity and operations.
 * Focused on the Item entity and its associated Location and Language entities.
 * Demonstrates creation, retrieval, update, and cleanup processes in the library system.
 */
public class DatabaseItemCRUDTest {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseItemCRUDTest.class);

    // Manages the persistence context and performs CRUD operations on database entities.
    private EntityManagerFactory emf;
    private EntityManager em;

    // Lists to track IDs of created entities for cleanup after tests.
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

    /**
     * Sets up the EntityManager and starts a transaction before each test.
     */
    @BeforeEach
    public void setUp() {
        logger.debug("Setting up EntityManager and starting a transaction.");
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    /**
     * Cleans up test data and closes the EntityManager and EntityManagerFactory after each test.
     */
    @AfterEach
    public void tearDown() {
        logger.debug("Cleaning up test data and closing EntityManager.");
        // Rollback any uncommitted changes to maintain a clean database state.
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        cleanupTestData(em);
        em.close();
        emf.close();

        logger.debug("Test data cleaned up and EntityManager closed.");
    }

    // Helper methods for shared setup
    private Location initalizeOrFetchLocation() {
        Location location = findOrCreateLocation(em);
        assertNotNull(location, "Location should not be null.");
        logger.debug("Setup location with ID: {}", location.getId());
        return location;
    }

    private Language initalizeOrFetchLanguage() {
        Language language = findOrCreateLanguage(em);
        assertNotNull(language, "Language should not be null.");
        logger.debug("Setup language with ID: {}", language.getId());
        return language;
    }

    /**
     * Tests creating a new Location or fetching an existing one.
     * It verifies that the location is created successfully and has the expected ID.
     */
    @Test
    @DisplayName("Test Creating New Location Or Fetching Existing Location")
    public void testCreateLocation() {
        Location location = initalizeOrFetchLocation();
        assertNotNull(location.getId(), "Location ID should not be null.");
        logger.debug("Test for creating location passed.");
    }

    /**
     * Tests creating a new Language or fetching an existing one.
     * It verifies that the language is created successfully and has the expected ID.
     */
    @Test
    @DisplayName("Test Creating New Language Or Fetching Existing Language")
    public void testCreateLanguage() {
        Language language = initalizeOrFetchLanguage();
        assertNotNull(language.getId(), "Language ID should not be null.");
        logger.debug("Test for creating language passed.");
    }

    /**
     * Tests creating a new Item using the Location and Language.
     * It verifies that the item is created successfully and has the expected title.
     */
    @Test
    @DisplayName("Test Creating New Item using Location and Language")
    public void testCreateItem() {
        Location location = initalizeOrFetchLocation();
        Language language = initalizeOrFetchLanguage();
        Item item = createAndPersistItem(em, location, language);
        assertNotNull(item.getId(), "Item ID should not be null.");
        verifyItemTitle(em, item.getId(), ITEM_TEST_PARAMS.title());
        logger.debug("Test for creating item passed.");
    }

    /**
     * Tests updating the title of an existing Item.
     * It verifies that the item's title is updated successfully and matches the expected value.
     */
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
        logger.debug("Test for updating item title passed.");
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
            logger.debug("Deleting {}s with IDs: {}", entityName, ids);
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
        logger.debug("Cleaning up test data.");
        em.getTransaction().begin();
        cleanupEntities(em, "Item", createdItemIds);
        cleanupEntities(em, "Location", createdLocationIds);
        cleanupEntities(em, "Language", createdLanguageIds);
        em.getTransaction().commit();
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
        logger.debug("Checking if location already exists.");
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
            logger.warn("Location not found, creating new one.");
            location = new Location();
            location.setFloor(ITEM_TEST_PARAMS.floor());
            location.setSection(ITEM_TEST_PARAMS.section());
            location.setShelf(ITEM_TEST_PARAMS.shelf());
            location.setPosition(ITEM_TEST_PARAMS.position());
            em.persist(location);
            createdLocationIds.add(location.getId());
        } else {
            logger.debug("Location already exists, using existing one.");
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
        logger.debug("Checking if language already exists.");
        Language language = em.createQuery(
                "SELECT l FROM Language l WHERE l.language = :language",
                Language.class)
            .setParameter("language", ITEM_TEST_PARAMS.language())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (language == null) {
            logger.warn("Language not found, creating new one.");
            language = new Language();
            language.setLanguage(ITEM_TEST_PARAMS.language());
            em.persist(language);
            createdLanguageIds.add(language.getId());
        } else {
            logger.debug("Language already exists, using existing one.");
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
        logger.debug("Creating new item.");
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
        logger.debug("Created new item with identifier: {}", item.getIdentifier());
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
        logger.debug("Updating title of item with ID: {} to: {}", itemId, newTitle);
        int updatedCount = em.createQuery("UPDATE Item i SET i.title = :newTitle WHERE i.id = :itemId")
            .setParameter("newTitle", newTitle)
            .setParameter("itemId", itemId)
            .executeUpdate();
        logger.debug("Updated {} record(s) in the Item table.", updatedCount);
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
        logger.debug("Fetching item with ID: {}", itemId);
        Item item = em.find(Item.class, itemId);
        assertNotNull(item, "Item should not be null.");
        em.refresh(item);
        assertEquals(expectedTitle, item.getTitle(), "The item's title does not match the expected value.");
        logger.debug("Verified item title: {}", item.getTitle());
    }
}
