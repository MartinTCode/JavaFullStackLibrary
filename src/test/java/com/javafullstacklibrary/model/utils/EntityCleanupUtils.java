package com.javafullstacklibrary.model.utils;

import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Utility class for cleaning up test data from the database.
 */
public class EntityCleanupUtils {

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

    
}
