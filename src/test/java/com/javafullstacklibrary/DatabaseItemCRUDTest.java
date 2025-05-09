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

import static com.javafullstacklibrary.utils.TestUtils.*;
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
        cleanupTestData(em, createdItemIds, createdLocationIds, createdLanguageIds);
        em.close();
        emf.close();

        logger.debug("Test data cleaned up and EntityManager closed.");
    }

    /**
     * Tests creating a new Location or fetching an existing one.
     * It verifies that the location is created successfully and has the expected ID.
     */
    @Test
    @DisplayName("Test Creating New Location Or Fetching Existing Location")
    public void testCreateLocation() {
        Location location = initializeOrFetchLocation(em, ITEM_TEST_PARAMS, createdLocationIds);
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
        Language language = initializeOrFetchLanguage(em, ITEM_TEST_PARAMS, createdLanguageIds);
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
        Location location = initializeOrFetchLocation(em, ITEM_TEST_PARAMS, createdLocationIds);
        Language language = initializeOrFetchLanguage(em, ITEM_TEST_PARAMS, createdLanguageIds);
        Item item = createAndPersistItem(em, ITEM_TEST_PARAMS, location, language, createdItemIds);
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
        Location location = initializeOrFetchLocation(em, ITEM_TEST_PARAMS, createdLocationIds);
        Language language = initializeOrFetchLanguage(em, ITEM_TEST_PARAMS, createdLanguageIds);
        Item item = createAndPersistItem(em, ITEM_TEST_PARAMS, location, language, createdItemIds);
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
}
