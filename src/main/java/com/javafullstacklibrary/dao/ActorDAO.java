package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Actor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ActorDAO {

    private final EntityManager entityManager;

    public ActorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing actor.
     * @param actor The actor to save.
     * @return The saved actor with generated ID (if new).
     */
    public Actor save(Actor actor) {
        try {
            entityManager.getTransaction().begin();
            if (actor.getId() == null) {
                entityManager.persist(actor);
            } else {
                actor = entityManager.merge(actor);
            }
            entityManager.getTransaction().commit();
            return actor;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes an actor by its ID.
     * @param id The ID of the actor to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Actor actor = findById(id);
        if (actor != null) {
            delete(actor);
            return true;
        }
        return false;
    }

    /**
     * Deletes an actor.
     * @param actor The actor to delete.
     */
    public void delete(Actor actor) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(actor)) {
                entityManager.remove(actor);
            } else {
                entityManager.remove(entityManager.merge(actor));
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
     * Finds an actor by its ID.
     * @param id The actor ID.
     * @return The found actor or null if not found.
     */
    public Actor findById(Integer id) {
        return entityManager.find(Actor.class, id);
    }

    /**
     * Finds all actors.
     * @return List of all actors.
     */
    public List<Actor> findAll() {
        return entityManager.createQuery("SELECT a FROM Actor a", Actor.class)
                .getResultList();
    }

    /**
     * Finds an actor by first and last name.
     * @param firstName The actor's first name.
     * @param lastName The actor's last name.
     * @return The found actor or null if not found.
     */
    public Actor findByName(String firstName, String lastName) {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a WHERE a.firstName = :firstName AND a.lastName = :lastName",
                Actor.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        List<Actor> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
