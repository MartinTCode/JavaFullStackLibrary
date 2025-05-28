package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LocationDAO {

    private final EntityManager entityManager;

    public LocationDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing location.
     * @param location The location to save.
     * @return The saved location with generated ID (if new).
     */
    public Location save(Location location) {
        try {
            entityManager.getTransaction().begin();
            if (location.getId() == null) {
                entityManager.persist(location);
            } else {
                location = entityManager.merge(location);
            }
            entityManager.getTransaction().commit();
            return location;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes a location by its ID.
     * @param id The ID of the location to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Location location = findById(id);
        if (location != null) {
            delete(location);
            return true;
        }
        return false;
    }

    /**
     * Deletes a location.
     * @param location The location to delete.
     */
    public void delete(Location location) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(location)) {
                entityManager.remove(location);
            } else {
                entityManager.remove(entityManager.merge(location));
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
     * Finds a location by its ID.
     * @param id The location ID.
     * @return The found location or null if not found.
     */
    public Location findById(Integer id) {
        return entityManager.find(Location.class, id);
    }

    /**
     * Finds all locations.
     * @return List of all locations.
     */
    public List<Location> findAll() {
        return entityManager.createQuery("SELECT l FROM Location l", Location.class)
                .getResultList();
    }

    /**
     * Finds a location by its fields.
     * @param floor The floor.
     * @param section The section.
     * @param shelf The shelf.
     * @param position The position.
     * @return The found location or null if not found.
     */
    public Location findByFields(String floor, String section, String shelf, String position) {
        TypedQuery<Location> query = entityManager.createQuery(
            "SELECT l FROM Location l WHERE l.floor = :floor AND l.section = :section AND l.shelf = :shelf AND l.position = :position",
            Location.class
        );
        query.setParameter("floor", floor);
        query.setParameter("section", section);
        query.setParameter("shelf", shelf);
        query.setParameter("position", position);
        List<Location> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
