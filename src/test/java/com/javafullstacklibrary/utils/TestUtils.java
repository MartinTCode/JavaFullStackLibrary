package com.javafullstacklibrary.utils;

import com.javafullstacklibrary.ItemTestParams;
import com.javafullstacklibrary.model.*;
import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

public class TestUtils {

    /** 
     * This helper method is used to clean up entities from the database.
     * It deletes entities of the specified type based on their IDs.
     * Requires that the PK field is named "id" in the entity class.
     * 
     * @param em The EntityManager to use for database operations
     * @param entityName The name of the entity type to delete
     * @param ids The list of IDs of the entities to delete
     */
    public static void cleanupEntities(EntityManager em, String entityName, List<Integer> ids) {
        if (!ids.isEmpty()) {
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
    public static void cleanupTestData(EntityManager em, List<Integer> itemIds, List<Integer> locationIds, List<Integer> languageIds) {
        em.getTransaction().begin();
        cleanupEntities(em, "Item", itemIds);
        cleanupEntities(em, "Location", locationIds);
        cleanupEntities(em, "Language", languageIds);
        em.getTransaction().commit();
    }

    public static Location findOrCreateLocation(EntityManager em, ItemTestParams params, List<Integer> createdLocationIds) {
        Location location = em.createQuery(
                "SELECT l FROM Location l WHERE l.floor = :floor AND l.section = :section AND l.shelf = :shelf AND l.position = :position",
                Location.class)
            .setParameter("floor", params.floor())
            .setParameter("section", params.section())
            .setParameter("shelf", params.shelf())
            .setParameter("position", params.position())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (location == null) {
            location = new Location();
            location.setFloor(params.floor());
            location.setSection(params.section());
            location.setShelf(params.shelf());
            location.setPosition(params.position());
            em.persist(location);
            createdLocationIds.add(location.getId());
        }
        return location;
    }

    public static Language findOrCreateLanguage(EntityManager em, ItemTestParams params, List<Integer> createdLanguageIds) {
        Language language = em.createQuery(
                "SELECT l FROM Language l WHERE l.language = :language",
                Language.class)
            .setParameter("language", params.language())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (language == null) {
            language = new Language();
            language.setLanguage(params.language());
            em.persist(language);
            createdLanguageIds.add(language.getId());
        }
        return language;
    }

    /**
     * Verifies that the item has the correct title.
     * It fetches the item from the database and checks that its title matches the expected value.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the item to verify
     * @param expectedTitle The expected title of the item
     */
    public static void verifyItemTitle(EntityManager em, Integer itemId, String expectedTitle) {
        Item item = em.find(Item.class, itemId);
        assertNotNull(item, "Item should not be null.");
        em.refresh(item);
        assertEquals(expectedTitle, item.getTitle(), "The item's title does not match the expected value.");
    }

    /**
     * Updates the title of an item in the database.
     *
     * @param em The EntityManager to use for database operations
     * @param itemId The ID of the item to update
     * @param newTitle The new title to set for the item
     */
    public static void updateItemTitle(EntityManager em, Integer itemId, String newTitle) {
        em.createQuery("UPDATE Item i SET i.title = :newTitle WHERE i.id = :itemId")
            .setParameter("newTitle", newTitle)
            .setParameter("itemId", itemId)
            .executeUpdate();
    }
}