package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Genre;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GenreDAO {

    private final EntityManager entityManager;

    public GenreDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a new or updates an existing genre.
     * @param genre The genre to save.
     * @return The saved genre with generated ID (if new).
     */
    public Genre save(Genre genre) {
        try {
            entityManager.getTransaction().begin();
            if (genre.getId() == null) {
                entityManager.persist(genre);
            } else {
                genre = entityManager.merge(genre);
            }
            entityManager.getTransaction().commit();
            return genre;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Deletes a genre by its ID.
     * @param id The ID of the genre to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteById(Integer id) {
        Genre genre = findById(id);
        if (genre != null) {
            delete(genre);
            return true;
        }
        return false;
    }

    /**
     * Deletes a genre.
     * @param genre The genre to delete.
     */
    public void delete(Genre genre) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.contains(genre)) {
                entityManager.remove(genre);
            } else {
                entityManager.remove(entityManager.merge(genre));
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
     * Finds a genre by its ID.
     * @param id The genre ID.
     * @return The found genre or null if not found.
     */
    public Genre findById(Integer id) {
        return entityManager.find(Genre.class, id);
    }

    /**
     * Finds all genres.
     * @return List of all genres.
     */
    public List<Genre> findAll() {
        return entityManager.createQuery("SELECT g FROM Genre g", Genre.class)
                .getResultList();
    }

    /**
     * Finds a genre by its value.
     * @param genreValue The genre string.
     * @return The found Genre or null if not found.
     */
    public Genre findByGenre(String genreValue) {
        TypedQuery<Genre> query = entityManager.createQuery(
                "SELECT g FROM Genre g WHERE g.genre = :genreValue", Genre.class);
        query.setParameter("genreValue", genreValue);
        List<Genre> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
