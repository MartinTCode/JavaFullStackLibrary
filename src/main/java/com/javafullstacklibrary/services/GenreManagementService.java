package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.GenreDAO;
import com.javafullstacklibrary.model.Genre;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GenreManagementService {
    private final EntityManagerFactory emf;
    private final GenreDAO genreDAO;
    

    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public GenreManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.genreDAO = new GenreDAO(em);
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
     * Gets a list of all genre strings.
     * This method retrieves all genres from the database
     * and returns their string representations.
     * @return Observable List of all genre strings.
     */
    public ObservableList<String> getAllStrings() {
        List<Genre> genres = genreDAO.findAll();
        List<String> genreStrings = new java.util.ArrayList<>();
        for (Genre genre : genres) {
            genreStrings.add(genre.getGenre());
        }
        ObservableList<String> observableGenreStrings = FXCollections.observableArrayList(genreStrings);

        return observableGenreStrings;
    }

    /**
     * Finds a genre by its name.
     * This method is used to retrieve a genre based on its name.
     * @param genreName
     * @return Genre object if found, null otherwise.
     */
    public Genre findByName(String genreName) {
        Genre genre = genreDAO.findByGenre(genreName);
        if (genre == null) {
            return null; // Return null if the genre is not found, could add creation logic here if needed
        }
        return genre;
    }
}
