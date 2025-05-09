package com.javafullstacklibrary.utils;

import com.javafullstacklibrary.ItemTestParams;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;
import jakarta.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemTestUtils {

     /**
     * This method creates and persists a new item in the database.
     * It sets the properties of the item based on the provided parameters.
     *
     * @param em The EntityManager to use for database operations
     * @param location The Location object associated with the item
     * @param language The Language object associated with the item
     * @return The created Item object
     */
    public static Item createAndPersistItem(EntityManager em, ItemTestParams params, Location location, Language language, List<Integer> createdItemIds) {
        Item item = new Item();
        item.setType(params.itemType());
        item.setIdentifier(params.identifier());
        item.setIdentifier2(params.identifier2());
        item.setTitle(params.title());
        item.setPublisher(params.publisher());
        item.setAgeLimit(params.ageLimit());
        item.setCountryOfProduction(params.countryOfProduction());
        item.setLocation(location);
        item.setLanguage(language);
        em.persist(item);
        createdItemIds.add(item.getId());
        return item;
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
