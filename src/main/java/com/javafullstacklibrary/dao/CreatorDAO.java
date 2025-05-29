package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Creator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CreatorDAO {

    private final EntityManager entityManager;

    public CreatorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing creator.
     * @param creator The creator to save.
     * @return The saved creator with generated ID (if new).
     */
    public Creator save(Creator creator) {
        try {
            entityManager.getTransaction().begin();
            if (creator.getId() == null) {
                entityManager.persist(creator);
            } else {
                creator = entityManager.merge(creator);
            }
            entityManager.getTransaction().commit();
            return creator;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes a creator by its ID.
     * @param id The ID of the creator to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Creator creator = findById(id);
        if (creator != null) {
            delete(creator);
            return true;
        }
        return false;
    }

    /**
     * Deletes a creator.
     * @param creator The creator to delete.
     */
    public void delete(Creator creator) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(creator)) {
                entityManager.remove(creator);
            } else {
                entityManager.remove(entityManager.merge(creator));
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
     * Finds a creator by its ID.
     * @param id The creator ID.
     * @return The found creator or null if not found.
     */
    public Creator findById(Integer id) {
        return entityManager.find(Creator.class, id);
    }

    /**
     * Finds all creators.
     * @return List of all creators.
     */
    public List<Creator> findAll() {
        return entityManager.createQuery("SELECT c FROM Creator c", Creator.class)
                .getResultList();
    }

    /**
     * Finds a creator by first and last name.
     * @param firstName The creator's first name.
     * @param lastName The creator's last name.
     * @return The found creator or null if not found.
     */
    public Creator findByName(String firstName, String lastName) {
        TypedQuery<Creator> query = entityManager.createQuery(
                "SELECT c FROM Creator c WHERE c.firstName = :firstName AND c.lastName = :lastName",
                Creator.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        List<Creator> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
