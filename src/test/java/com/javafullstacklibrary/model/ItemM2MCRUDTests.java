package com.javafullstacklibrary.model;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

import static com.javafullstacklibrary.model.utils.LanguageTestUtils.initializeOrFetchLanguage;
import static com.javafullstacklibrary.model.utils.LocationTestUtils.initializeOrFetchLocation;
import static com.javafullstacklibrary.model.utils.EntityCleanupUtils.cleanupTestData;
import static com.javafullstacklibrary.model.utils.EntityFetchOrCreateUtils.getOrCreateEntity;

public class ItemM2MCRUDTests {

    private static final Logger logger = LoggerFactory.getLogger(ItemM2MCRUDTests.class);

    private static EntityManagerFactory emf;
    private EntityManager em;

    private Language language;
    private Location location;

    private List<Integer> createdItemIds = new ArrayList<>();
    private List<Integer> createdLocationIds = new ArrayList<>();
    private List<Integer> createdLanguageIds = new ArrayList<>();

    private static final ItemTestParams ITEM_TEST_PARAMS = new ItemTestParams(
            "book", "123", "456", "Test Book", "Test Book", "Test Publisher", (short) 0, "Test Country",
            "1", "A", "1", "1", "English"
    );

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        logger.debug("EntityManagerFactory initialized.");
    }

    @AfterAll
    static void close() {
        emf.close();
        logger.debug("EntityManagerFactory closed.");
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        logger.debug("EntityManager created and transaction started.");

        language = initializeOrFetchLanguage(em, ITEM_TEST_PARAMS, createdLanguageIds);
        location = initializeOrFetchLocation(em, ITEM_TEST_PARAMS, createdLocationIds);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
            logger.debug("Transaction rolled back.");
        }
        cleanupTestData(em, createdItemIds, createdLocationIds, createdLanguageIds);
        em.close();
        logger.debug("Test data cleaned up and EntityManager closed.");
    }

    @Test
    void testCreateItemWithAllM2M() {
        logger.debug("Running testCreateItemWithAllM2M...");

        // Arrange: Prepare all related entities (ensure unique firstName for each Actor)
        Keyword k1 = getOrCreateEntity(em, Keyword.class, "keyword", "Adventure", () -> new Keyword("Adventure"));
        Keyword k2 = getOrCreateEntity(em, Keyword.class, "keyword", "Fantasy", () -> new Keyword("Fantasy"));
        Creator c1 = getOrCreateEntity(em, Creator.class, "firstName", "John", () -> new Creator("John", "Doe", LocalDate.of(1970, 1, 1)));
        Creator c2 = getOrCreateEntity(em, Creator.class, "firstName", "Jane", () -> new Creator("Jane", "Smith", LocalDate.of(1980, 2, 2)));
        Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "ActorOne", () -> new Actor("ActorOne", "One", LocalDate.of(1990, 3, 3)));
        Actor a2 = getOrCreateEntity(em, Actor.class, "firstName", "ActorTwo", () -> new Actor("ActorTwo", "Two", LocalDate.of(1991, 4, 4)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));
        Genre g2 = getOrCreateEntity(em, Genre.class, "genre", "Adventure", () -> new Genre("Adventure"));

        Item item = new Item();
        item.setTitle("Test Book");
        item.setType("book");
        item.setLanguage(language);
        item.setLocation(location);
        item.setIdentifier("123");
        item.setIdentifier2("456");
        item.setKeywords(new HashSet<>(Set.of(k1, k2)));
        item.setCreators(new HashSet<>(Set.of(c1, c2)));
        item.setActors(new HashSet<>(Set.of(a1, a2)));
        item.setGenres(new HashSet<>(Set.of(g1, g2)));

        // Act: Persist the item and clear the persistence context
        em.persist(item);
        em.flush();
        createdItemIds.add(item.getId());
        em.clear();

        // Assert: Fetch and verify all M2M relationships
        Item found = em.find(Item.class, item.getId());
        Assertions.assertEquals(2, found.getKeywords().size());
        Assertions.assertEquals(2, found.getCreators().size());
        Assertions.assertEquals(2, found.getActors().size());
        Assertions.assertEquals(2, found.getGenres().size());
        logger.debug("testCreateItemWithAllM2M completed successfully.");
    }

    @Test
    void testAddAndRemoveAllM2M() {
        logger.debug("Running testAddAndRemoveAllM2M...");

        // Arrange: Prepare related entities and a new item
        Keyword k1 = getOrCreateEntity(em, Keyword.class, "keyword", "Adventure", () -> new Keyword("Adventure"));
        Creator c1 = getOrCreateEntity(em, Creator.class, "firstName", "John", () -> new Creator("John", "Doe", LocalDate.of(1970, 1, 1)));
        Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "Actor", () -> new Actor("Actor", "One", LocalDate.of(1990, 3, 3)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));

        Item item = new Item();
        item.setTitle("Test Book");
        item.setType("book");
        item.setLanguage(language);
        item.setLocation(location);
        item.setIdentifier("123");
        item.setIdentifier2("456");
        item.setKeywords(new HashSet<>());
        item.setCreators(new HashSet<>());
        item.setActors(new HashSet<>());
        item.setGenres(new HashSet<>());
        em.persist(item);
        createdItemIds.add(item.getId());

        // Act: Add all M2M relationships, then remove them
        item.getKeywords().add(k1);
        item.getCreators().add(c1);
        item.getActors().add(a1);
        item.getGenres().add(g1);
        em.merge(item);
        em.flush();
        em.clear();

        // Assert: Verify all relationships were added
        Item found = em.find(Item.class, item.getId());
        Assertions.assertTrue(found.getKeywords().contains(k1));
        Assertions.assertTrue(found.getCreators().contains(c1));
        Assertions.assertTrue(found.getActors().contains(a1));
        Assertions.assertTrue(found.getGenres().contains(g1));

        // Act: Remove all M2M relationships and update
        found.getKeywords().remove(k1);
        found.getCreators().remove(c1);
        found.getActors().remove(a1);
        found.getGenres().remove(g1);
        em.merge(found);
        em.flush();
        em.clear();

        // Assert: Verify all relationships were removed
        Item found2 = em.find(Item.class, item.getId());
        Assertions.assertTrue(found2.getKeywords().isEmpty());
        Assertions.assertTrue(found2.getCreators().isEmpty());
        Assertions.assertTrue(found2.getActors().isEmpty());
        Assertions.assertTrue(found2.getGenres().isEmpty());
        logger.debug("testAddAndRemoveAllM2M completed successfully.");
    }

    @Test
    void testClearAllM2M() {
        logger.debug("Running testClearAllM2M...");

        // Arrange: Prepare related entities and a new item with all M2M relationships
        Keyword k1 = getOrCreateEntity(em, Keyword.class, "keyword", "Adventure", () -> new Keyword("Adventure"));
        Creator c1 = getOrCreateEntity(em, Creator.class, "firstName", "John", () -> new Creator("John", "Doe", LocalDate.of(1970, 1, 1)));
        Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "Actor", () -> new Actor("Actor", "One", LocalDate.of(1990, 3, 3)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));

        Item item = new Item();
        item.setTitle("Test Book");
        item.setType("book");
        item.setLanguage(language);
        item.setLocation(location);
        item.setIdentifier("123");
        item.setIdentifier2("456");
        item.setKeywords(new HashSet<>(Set.of(k1)));
        item.setCreators(new HashSet<>(Set.of(c1)));
        item.setActors(new HashSet<>(Set.of(a1)));
        item.setGenres(new HashSet<>(Set.of(g1)));
        em.persist(item);
        createdItemIds.add(item.getId());

        // Act: Clear all M2M relationships and update
        item.getKeywords().clear();
        item.getCreators().clear();
        item.getActors().clear();
        item.getGenres().clear();
        em.merge(item);
        em.flush();
        em.clear();

        // Assert: Verify all relationships are cleared
        Item found = em.find(Item.class, item.getId());
        Assertions.assertTrue(found.getKeywords().isEmpty());
        Assertions.assertTrue(found.getCreators().isEmpty());
        Assertions.assertTrue(found.getActors().isEmpty());
        Assertions.assertTrue(found.getGenres().isEmpty());
        logger.debug("testClearAllM2M completed successfully.");
    }
}