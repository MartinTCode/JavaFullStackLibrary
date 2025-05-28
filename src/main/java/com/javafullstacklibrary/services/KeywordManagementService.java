package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.KeywordDAO;
import com.javafullstacklibrary.model.Keyword;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class KeywordManagementService {

    private final EntityManagerFactory emf;
    private final KeywordDAO keywordDAO;

    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public KeywordManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
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
     * @return List of all keyword strings.
     */
    public List<String> getAllStrings() {
        List<Keyword> keywords = findAll();
        List<String> keywordStrings = new java.util.ArrayList<>();
        for (Keyword keyword : keywords) {
            keywordStrings.add(keyword.getKeyword());
        }
        return keywordStrings;
    }

    /**
     * Finds or creates a keyword by its value.
     * @param keywordValue The keyword string.
     * @return The found or newly created keyword.
     */
    public Keyword findOrCreate(String keywordValue) {
        Keyword keyword = keywordDAO.findByKeyword(keywordValue);
        if (keyword == null) {
            keyword = new Keyword(keywordValue);
            keyword = keywordDAO.save(keyword);
        }
        return keyword;
    }
}
