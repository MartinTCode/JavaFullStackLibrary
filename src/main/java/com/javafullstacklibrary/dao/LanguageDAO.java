package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Language;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LanguageDAO {

    private final EntityManager entityManager;

    public LanguageDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing language.
     * @param language The language to save.
     * @return The saved language with generated ID (if new).
     */
    public Language save(Language language) {
        try {
            entityManager.getTransaction().begin();
            if (language.getId() == null) {
                entityManager.persist(language);
            } else {
                language = entityManager.merge(language);
            }
            entityManager.getTransaction().commit();
            return language;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes a language by its ID.
     * @param id The ID of the language to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Language language = findById(id);
        if (language != null) {
            delete(language);
            return true;
        }
        return false;
    }

    /**
     * Deletes a language.
     * @param language The language to delete.
     */
    public void delete(Language language) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(language)) {
                entityManager.remove(language);
            } else {
                entityManager.remove(entityManager.merge(language));
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
     * Finds a language by its ID.
     * @param id The language ID.
     * @return The found language or null if not found.
     */
    public Language findById(Integer id) {
        return entityManager.find(Language.class, id);
    }

    /**
     * Finds all languages.
     * @return List of all languages.
     */
    public List<Language> findAll() {
        return entityManager.createQuery("SELECT l FROM Language l", Language.class)
                .getResultList();
    }

    /**
     * Finds a language by its value.
     * @param languageValue The language string.
     * @return The found Language or null if not found.
     */
    public Language findByLanguage(String languageValue) {
        TypedQuery<Language> query = entityManager.createQuery(
                "SELECT l FROM Language l WHERE l.language = :languageValue", Language.class);
        query.setParameter("languageValue", languageValue);
        List<Language> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
