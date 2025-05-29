package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Keyword;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class KeywordDAO {

    private final EntityManager entityManager;

    public KeywordDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing keyword.
     * @param keyword The keyword to save.
     * @return The saved keyword with generated ID (if new).
     */
    public Keyword save(Keyword keyword) {
        try {
            entityManager.getTransaction().begin();
            if (keyword.getId() == null) {
                entityManager.persist(keyword);
            } else {
                keyword = entityManager.merge(keyword);
            }
            entityManager.getTransaction().commit();
            return keyword;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes a keyword by its ID.
     * @param id The ID of the keyword to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Keyword keyword = findById(id);
        if (keyword != null) {
            delete(keyword);
            return true;
        }
        return false;
    }

    /**
     * Deletes a keyword.
     * @param keyword The keyword to delete.
     */
    public void delete(Keyword keyword) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(keyword)) {
                entityManager.remove(keyword);
            } else {
                entityManager.remove(entityManager.merge(keyword));
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Finds a keyword by its ID.
     * @param id The keyword ID.
     * @return The found keyword or null if not found.
     */
    public Keyword findById(Integer id) {
        return entityManager.find(Keyword.class, id);
    }

    /**
     * Finds all keywords.
     * @return List of all keywords.
     */
    public List<Keyword> findAll() {
        return entityManager.createQuery("SELECT k FROM Keyword k", Keyword.class)
                .getResultList();
    }

    /**
     * Finds a keyword by its value.
     * @param keywordValue The keyword string.
     * @return The found Keyword or null if not found.
     */
    public Keyword findByKeyword(String keywordValue) {
        TypedQuery<Keyword> query = entityManager.createQuery(
                "SELECT k FROM Keyword k WHERE k.keyword = :keywordValue", Keyword.class);
        query.setParameter("keywordValue", keywordValue);
        List<Keyword> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
