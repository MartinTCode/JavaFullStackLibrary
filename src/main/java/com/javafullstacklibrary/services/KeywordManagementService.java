package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.KeywordDAO;
import com.javafullstacklibrary.model.Keyword;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KeywordManagementService implements AutoCloseable {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final KeywordDAO keywordDAO;

    /**
     * Constructor that initializes the EntityManagerFactory and KeywordDAO.
     */
    public KeywordManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        this.em = emf.createEntityManager();
        this.keywordDAO = new KeywordDAO(em);
    }

    /**
     * Adds a new keyword.
     * @param keyword The keyword to add.
     * @return The saved keyword with generated ID.
     */
    public Keyword addKeyword(Keyword keyword) {
        return keywordDAO.save(keyword);
    }

    /**
     * Updates an existing keyword.
     * @param keyword The keyword with updated fields.
     * @return The updated keyword.
     */
    public Keyword updateKeyword(Keyword keyword) {
        return keywordDAO.save(keyword);
    }

    /**
     * Deletes a keyword by its ID.
     * @param id The ID of the keyword to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteKeywordById(Integer id) {
        return keywordDAO.deleteById(id);
    }

    /**
     * Finds a keyword by its ID.
     * @param id The keyword ID.
     * @return The found keyword or null if not found.
     */
    public Keyword findById(Integer id) {
        return keywordDAO.findById(id);
    }

    /**
     * Finds all keywords.
     * @return List of all keywords.
     */
    public List<Keyword> findAll() {
        return keywordDAO.findAll();
    }

    /**
     * Gets a list of all keyword strings.
     * This method retrieves all keywords and returns their string representations.
     * @return Observable List of all keyword strings.
     */
    public ObservableList<String> getAllStrings() {
        List<Keyword> keywords = findAll();
        List<String> keywordStrings = new java.util.ArrayList<>();
        for (Keyword keyword : keywords) {
            keywordStrings.add(keyword.getKeyword());
        }
        ObservableList<String> observableKeywordStrings = FXCollections.observableArrayList(keywordStrings);

        return observableKeywordStrings;
    }

    /**
     * Finds keyword by its value.
     * @param keywordValue The keyword string.
     * @return The found keyword or null if not found.
     */
    public Keyword findByName(String keywordValue) {
        Keyword keyword = keywordDAO.findByKeyword(keywordValue);
        if (keyword == null) {
            return null; // Return null if not found, could add creation logic here if needed
        }
        return keyword;
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
