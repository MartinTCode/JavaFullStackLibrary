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

public class BookM2MCRUDTests {

    private static final Logger logger = LoggerFactory.getLogger(BookM2MCRUDTests.class);

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
        //Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "ActorOne", () -> new Actor("ActorOne", "One", LocalDate.of(1990, 3, 3)));
        //Actor a2 = getOrCreateEntity(em, Actor.class, "firstName", "ActorTwo", () -> new Actor("ActorTwo", "Two", LocalDate.of(1991, 4, 4)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));
        Genre g2 = getOrCreateEntity(em, Genre.class, "genre", "Adventure", () -> new Genre("Adventure"));

        // Create a Book instead of abstract Item
        Book book = new Book();
        book.setTitle("Test Book");
        book.setISBN13("9781234567890"); // Use book-specific method
        book.setISBN10("1234567890");    // Use book-specific method
        book.setLanguage(language);
        book.setLocation(location);
        book.setPublisher("Test Publisher");
        book.setKeywords(new HashSet<>(Set.of(k1, k2)));
        book.setAuthors(new HashSet<>(Set.of(c1, c2))); // Use book-specific method
        //book.setActors(new HashSet<>(Set.of(a1, a2)));
        book.setGenres(new HashSet<>(Set.of(g1, g2)));

        // Act: Persist the book and clear the persistence context
        em.persist(book);
        em.flush();
        createdItemIds.add(book.getId());
        em.clear();

        // Assert: Fetch and verify all M2M relationships
        Book found = em.find(Book.class, book.getId());
        Assertions.assertEquals(2, found.getKeywords().size());
        Assertions.assertEquals(2, found.getAuthors().size()); // Use book-specific method
        //Assertions.assertEquals(2, found.getActors().size());
        Assertions.assertEquals(2, found.getGenres().size());
        logger.debug("testCreateItemWithAllM2M completed successfully.");
    }

    @Test
    void testAddAndRemoveAllM2M() {
        logger.debug("Running testAddAndRemoveAllM2M...");

        // Arrange: Prepare related entities and a new book
        Keyword k1 = getOrCreateEntity(em, Keyword.class, "keyword", "Adventure", () -> new Keyword("Adventure"));
        Creator c1 = getOrCreateEntity(em, Creator.class, "firstName", "John", () -> new Creator("John", "Doe", LocalDate.of(1970, 1, 1)));
        //Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "Actor", () -> new Actor("Actor", "One", LocalDate.of(1990, 3, 3)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));

        // Create a Book instead of abstract Item
        Book book = new Book();
        book.setTitle("Test Book");
        book.setISBN13("9781234567891"); // Use book-specific method
        book.setISBN10("1234567891");    // Use book-specific method
        book.setLanguage(language);
        book.setLocation(location);
        book.setPublisher("Test Publisher");
        book.setKeywords(new HashSet<>());
        //book.setAuthors(new HashSet<>()); // Use book-specific method
        book.setActors(new HashSet<>());
        book.setGenres(new HashSet<>());
        em.persist(book);
        createdItemIds.add(book.getId());

        // Act: Add all M2M relationships, then remove them
        book.getKeywords().add(k1);
        book.getAuthors().add(c1); // Use book-specific method
        //book.getActors().add(a1);
        book.getGenres().add(g1);
        em.merge(book);
        em.flush();
        em.clear();

        // Assert: Verify all relationships were added
        Book found = em.find(Book.class, book.getId());
        Assertions.assertTrue(found.getKeywords().contains(k1));
        Assertions.assertTrue(found.getAuthors().contains(c1)); // Use book-specific method
        //Assertions.assertTrue(found.getActors().contains(a1));
        Assertions.assertTrue(found.getGenres().contains(g1));

        // Act: Remove all M2M relationships and update
        found.getKeywords().remove(k1);
        found.getAuthors().remove(c1); // Use book-specific method
        //found.getActors().remove(a1);
        found.getGenres().remove(g1);
        em.merge(found);
        em.flush();
        em.clear();

        // Assert: Verify all relationships were removed
        Book found2 = em.find(Book.class, book.getId());
        Assertions.assertTrue(found2.getKeywords().isEmpty());
        Assertions.assertTrue(found2.getAuthors().isEmpty()); // Use book-specific method
        //Assertions.assertTrue(found2.getActors().isEmpty());
        Assertions.assertTrue(found2.getGenres().isEmpty());
        logger.debug("testAddAndRemoveAllM2M completed successfully.");
    }

    @Test
    void testClearAllM2M() {
        logger.debug("Running testClearAllM2M...");

        // Arrange: Prepare related entities and a new Book with all M2M relationships
        Keyword k1 = getOrCreateEntity(em, Keyword.class, "keyword", "Adventure", () -> new Keyword("Adventure"));
        Creator c1 = getOrCreateEntity(em, Creator.class, "firstName", "John", () -> new Creator("John", "Doe", LocalDate.of(1970, 1, 1)));
        //Actor a1 = getOrCreateEntity(em, Actor.class, "firstName", "Actor", () -> new Actor("Actor", "One", LocalDate.of(1990, 3, 3)));
        Genre g1 = getOrCreateEntity(em, Genre.class, "genre", "Fiction", () -> new Genre("Fiction"));

        // Create a Book instead of abstract Item
        Book book = new Book();
        book.setTitle("Test Book");
        book.setISBN13("9781234567890"); // Use book-specific method
        book.setISBN10("1234567890");    // Use book-specific method
        book.setLanguage(language);
        book.setLocation(location);
        book.setPublisher("Test Publisher");
        book.setKeywords(new HashSet<>(Set.of(k1)));
        book.setAuthors(new HashSet<>(Set.of(c1))); // Use book-specific method for creators
        //book.setActors(new HashSet<>(Set.of(a1)));
        book.setGenres(new HashSet<>(Set.of(g1)));
        em.persist(book);
        createdItemIds.add(book.getId());

        // Act: Clear all M2M relationships and update
        book.getKeywords().clear();
        book.getAuthors().clear();       // Use book-specific method
        //book.getActors().clear();
        book.getGenres().clear();
        em.merge(book);
        em.flush();
        em.clear();

        // Assert: Verify all relationships are cleared
        Book found = em.find(Book.class, book.getId());
        Assertions.assertTrue(found.getKeywords().isEmpty());
        Assertions.assertTrue(found.getAuthors().isEmpty());  // Use book-specific method
        //Assertions.assertTrue(found.getActors().isEmpty());
        Assertions.assertTrue(found.getGenres().isEmpty());
        logger.debug("testClearAllM2M completed successfully.");
    }
}