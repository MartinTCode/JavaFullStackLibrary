package com.javafullstacklibrary.model.utils;

// Utility class for fetching or creating entities in the database
import java.util.function.Supplier;

import jakarta.persistence.EntityManager;

public class EntityFetchOrCreateUtils {

    // Generic helper for get-or-create by attribute
    public static <T> T getOrCreateEntity(EntityManager em, Class<T> entityClass, String attributeName, Object value, Supplier<T> creator) {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + attributeName + " = :val";
        return em.createQuery(jpql, entityClass)
                .setParameter("val", value)
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    T entity = creator.get();
                    em.persist(entity);
                    return entity;
                });
    }
}
