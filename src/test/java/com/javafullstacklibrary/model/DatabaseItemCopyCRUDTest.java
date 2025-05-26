package com.javafullstacklibrary.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.javafullstacklibrary.model.utils.EntityCleanupUtils.*;
import static com.javafullstacklibrary.model.utils.ItemTestUtils.*;
import static com.javafullstacklibrary.model.utils.LanguageTestUtils.*;
import static com.javafullstacklibrary.model.utils.LocationTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD test suite for the ItemCopy entity.
 * Tests the creation, retrieval, update, and deletion of ItemCopy objects
 * and their relationship with the Item entity.
 */
public class DatabaseItemCopyCRUDTest {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseItemCopyCRUDTest.class);

    // Use static for EntityManagerFactory to share across all tests
    private static EntityManagerFactory emf;
    private EntityManager em;

    // Lists to track IDs of created entities for cleanup after tests
    private List<Integer> createdItemCopyIds = new ArrayList<>();
    private List<Integer> createdItemIds = new ArrayList<>();
    private List<Integer> createdLocationIds = new ArrayList<>();
    private List<Integer> createdLanguageIds = new ArrayList<>();

    // Test parameters for the Item
    static final ItemTestParams ITEM_TEST_PARAMS = new ItemTestParams(
        "book",
        "1234567890",
        "1234567890123",
        "Test Item for Copy",
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

    // Test parameters for the ItemCopy
    static final String TEST_BARCODE = "COPY12345";
    static final String UPDATED_BARCODE = "COPY98765";
    static final Boolean IS_REFERENCE = false;
    static final Boolean UPDATED_IS_REFERENCE = true;
    static final LocalDate DATE_ADDED = LocalDate.now();
    static final LocalDate UPDATED_DATE = LocalDate.now().plusDays(7);

    /**
     * Initializes the EntityManagerFactory once before all tests.
     */
    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
    }

    /**
     * Closes the EntityManagerFactory after all tests.
     */
    @AfterAll
    static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    /**
     * Sets up the EntityManager and starts a transaction before each test.
     */
    @BeforeEach
    public void setUp() {
        logger.debug("Setting up EntityManager and starting a transaction.");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    /**
     * Cleans up test data and closes the EntityManager after each test.
     */
    @AfterEach
    public void tearDown() {
        logger.debug("Cleaning up test data and closing EntityManager.");
        // Rollback any uncommitted changes to maintain a clean database state
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        
        // Clean up created ItemCopy entities first (due to foreign key constraints)
        if (!createdItemCopyIds.isEmpty()) {
            em.getTransaction().begin();
            for (Integer id : createdItemCopyIds) {
                ItemCopy itemCopy = em.find(ItemCopy.class, id);
                if (itemCopy != null) {
                    em.remove(itemCopy);
                }
            }
            em.getTransaction().commit();
        }
        
        // Clean up remaining entities
        cleanupTestData(em, createdItemIds, createdLocationIds, createdLanguageIds);
        em.close();
        logger.debug("Test data cleaned up and EntityManager closed.");
    }

    /**
     * Helper method to create an Item for testing ItemCopy
     */
    private Item createTestItem() {
        Location location = initializeOrFetchLocation(em, ITEM_TEST_PARAMS, createdLocationIds);
        Language language = initializeOrFetchLanguage(em, ITEM_TEST_PARAMS, createdLanguageIds);
        return createAndPersistItem(em, ITEM_TEST_PARAMS, location, language, createdItemIds);
    }

    /**
     * Helper method to create an ItemCopy for testing
     */
    private ItemCopy createTestItemCopy(Item item) {
        ItemCopy itemCopy = new ItemCopy(TEST_BARCODE, item, IS_REFERENCE, DATE_ADDED);
        em.persist(itemCopy);
        createdItemCopyIds.add(itemCopy.getId());
        return itemCopy;
    }

    /**
     * Tests creating a new ItemCopy.
     */
    @Test
    @DisplayName("Test Creating New ItemCopy")
    public void testCreateItemCopy() {
        // Create parent Item
        Item item = createTestItem();
        
        // Create ItemCopy
        ItemCopy itemCopy = createTestItemCopy(item);
        
        // Assert
        assertNotNull(itemCopy.getId(), "ItemCopy ID should not be null");
        assertEquals(TEST_BARCODE, itemCopy.getBarcode(), "Barcode should match");
        assertEquals(IS_REFERENCE, itemCopy.getIsReference(), "IsReference should match");
        assertEquals(DATE_ADDED, itemCopy.getDateAdded(), "DateAdded should match");
        assertEquals(item.getId(), itemCopy.getItem().getId(), "Item reference should match");
        
        logger.debug("Test for creating ItemCopy passed.");
    }

    /**
     * Tests retrieving an ItemCopy by ID.
     */
    @Test
    @DisplayName("Test Retrieving ItemCopy by ID")
    public void testRetrieveItemCopy() {
        // Create parent Item and ItemCopy
        Item item = createTestItem();
        ItemCopy createdCopy = createTestItemCopy(item);
        
        // Commit to ensure the entity is in the database
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for retrieval
        em.getTransaction().begin();
        
        // Retrieve the ItemCopy
        ItemCopy retrievedCopy = em.find(ItemCopy.class, createdCopy.getId());
        
        // Assert
        assertNotNull(retrievedCopy, "Retrieved ItemCopy should not be null");
        assertEquals(TEST_BARCODE, retrievedCopy.getBarcode(), "Barcode should match");
        assertEquals(IS_REFERENCE, retrievedCopy.getIsReference(), "IsReference should match");
        assertEquals(DATE_ADDED, retrievedCopy.getDateAdded(), "DateAdded should match");
        assertEquals(item.getId(), retrievedCopy.getItem().getId(), "Item reference should match");
        
        logger.debug("Test for retrieving ItemCopy passed.");
    }

    /**
     * Tests updating an ItemCopy's properties.
     */
    @Test
    @DisplayName("Test Updating ItemCopy")
    public void testUpdateItemCopy() {
        // Create parent Item and ItemCopy
        Item item = createTestItem();
        ItemCopy itemCopy = createTestItemCopy(item);
        
        // Commit to ensure the entity is in the database
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for update
        em.getTransaction().begin();
        
        // Retrieve and update the ItemCopy
        ItemCopy retrievedCopy = em.find(ItemCopy.class, itemCopy.getId());
        retrievedCopy.setBarcode(UPDATED_BARCODE);
        retrievedCopy.setIsReference(UPDATED_IS_REFERENCE);
        retrievedCopy.setDateAdded(UPDATED_DATE);
        
        em.merge(retrievedCopy);
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for verification
        em.getTransaction().begin();
        
        // Verify the updates
        ItemCopy updatedCopy = em.find(ItemCopy.class, itemCopy.getId());
        assertEquals(UPDATED_BARCODE, updatedCopy.getBarcode(), "Updated barcode should match");
        assertEquals(UPDATED_IS_REFERENCE, updatedCopy.getIsReference(), "Updated isReference should match");
        assertEquals(UPDATED_DATE, updatedCopy.getDateAdded(), "Updated dateAdded should match");
        
        logger.debug("Test for updating ItemCopy passed.");
    }

    /**
     * Tests deleting an ItemCopy.
     */
    @Test
    @DisplayName("Test Deleting ItemCopy")
    public void testDeleteItemCopy() {
        // Create parent Item and ItemCopy
        Item item = createTestItem();
        ItemCopy itemCopy = createTestItemCopy(item);
        Integer itemCopyId = itemCopy.getId();
        
        // Commit to ensure the entity is in the database
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for deletion
        em.getTransaction().begin();
        
        // Delete the ItemCopy
        ItemCopy copyToDelete = em.find(ItemCopy.class, itemCopyId);
        em.remove(copyToDelete);
        em.getTransaction().commit();
        
        // Remove from tracking list since we manually deleted it
        createdItemCopyIds.remove(itemCopyId);
        
        // Start new transaction for verification
        em.getTransaction().begin();
        
        // Verify the deletion
        ItemCopy deletedCopy = em.find(ItemCopy.class, itemCopyId);
        assertNull(deletedCopy, "ItemCopy should be deleted");
        
        logger.debug("Test for deleting ItemCopy passed.");
    }

    /**
     * Tests finding ItemCopy by barcode.
     */
    @Test
    @DisplayName("Test Finding ItemCopy by Barcode")
    public void testFindByBarcode() {
        // Create parent Item and ItemCopy
        Item item = createTestItem();
        ItemCopy itemCopy = createTestItemCopy(item);
        
        // Commit to ensure the entity is in the database
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for query
        em.getTransaction().begin();
        
        // Find ItemCopy by barcode
        TypedQuery<ItemCopy> query = em.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.barcode = :barcode", 
            ItemCopy.class);
        query.setParameter("barcode", TEST_BARCODE);
        ItemCopy foundCopy = query.getSingleResult();
        
        // Assert
        assertNotNull(foundCopy, "ItemCopy should be found by barcode");
        assertEquals(itemCopy.getId(), foundCopy.getId(), "Found ItemCopy ID should match");
        
        logger.debug("Test for finding ItemCopy by barcode passed.");
    }

    /**
     * Tests the relationship between Item and its copies.
     */
    @Test
    @DisplayName("Test Item-ItemCopy Relationship")
    public void testItemCopyRelationship() {
        // Create parent Item
        Item item = createTestItem();
        
        // Create multiple ItemCopies
        ItemCopy copy1 = new ItemCopy(TEST_BARCODE + "1", item, IS_REFERENCE, DATE_ADDED);
        ItemCopy copy2 = new ItemCopy(TEST_BARCODE + "2", item, UPDATED_IS_REFERENCE, UPDATED_DATE);
        
        // Add copies to item and persist
        item.addCopy(copy1);
        item.addCopy(copy2);
        em.persist(copy1);
        em.persist(copy2);
        createdItemCopyIds.add(copy1.getId());
        createdItemCopyIds.add(copy2.getId());
        
        // Commit to ensure the entities are in the database
        em.getTransaction().commit();
        em.clear(); // Clear persistence context
        
        // Start new transaction for verification
        em.getTransaction().begin();
        
        // Verify the relationship
        Item retrievedItem = em.find(Item.class, item.getId());
        assertNotNull(retrievedItem.getCopies(), "Item should have copies");
        assertEquals(2, retrievedItem.getCopies().size(), "Item should have 2 copies");
        
        // Verify the copies reference the correct item
        for (ItemCopy copy : retrievedItem.getCopies()) {
            assertEquals(item.getId(), copy.getItem().getId(), "Copy should reference the correct item");
        }
        
        logger.debug("Test for Item-ItemCopy relationship passed.");
    }
}