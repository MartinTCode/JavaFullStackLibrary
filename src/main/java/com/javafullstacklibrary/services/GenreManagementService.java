package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.GenreDAO;
import com.javafullstacklibrary.model.Genre;

import java.util.List;

public class GenreManagementService {

    private final GenreDAO genreDAO;

    public GenreManagementService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    /**
     * Adds a new genre.
     * @param genre The genre to add.
     * @return The saved genre with generated ID.
     */
    public Genre addGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    /**
     * Updates an existing genre.
     * @param genre The genre with updated fields.
     * @return The updated genre.
     */
    public Genre updateGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    /**
     * Deletes a genre by its ID.
     * @param id The ID of the genre to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteGenreById(Integer id) {
        return genreDAO.deleteById(id);
    }

    /**
     * Finds a genre by its ID.
     * @param id The genre ID.
     * @return The found genre or null if not found.
     */
    public Genre findById(Integer id) {
        return genreDAO.findById(id);
    }

    /**
     * Finds all genres.
     * @return List of all genres.
     */
    public List<Genre> findAll() {
        return genreDAO.findAll();
    }

    /**
     * Finds or creates a genre by its value.
     * @param genreValue The genre string.
     * @return The found or newly created genre.
     */
    public Genre findOrCreate(String genreValue) {
        Genre genre = genreDAO.findByGenre(genreValue);
        if (genre == null) {
            genre = new Genre(genreValue);
            genre = genreDAO.save(genre);
        }
        return genre;
    }
}
