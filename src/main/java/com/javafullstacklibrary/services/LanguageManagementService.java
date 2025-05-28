package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.LanguageDAO;
import com.javafullstacklibrary.model.Language;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LanguageManagementService {

    private final LanguageDAO languageDAO;
    private final EntityManagerFactory emf;

    /**
     * Constructor that initializes the EntityManagerFactory and LanguageDAO.
     */
    public LanguageManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.languageDAO = new LanguageDAO(em);
    }

    /**
     * Adds a new language.
     * @param language The language to add.
     * @return The saved language with generated ID.
     */
    public Language addLanguage(Language language) {
        return languageDAO.save(language);
    }

    /**
     * Updates an existing language.
     * @param language The language with updated fields.
     * @return The updated language.
     */
    public Language updateLanguage(Language language) {
        return languageDAO.save(language);
    }

    /**
     * Deletes a language by its ID.
     * @param id The ID of the language to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteLanguageById(Integer id) {
        return languageDAO.deleteById(id);
    }

    /**
     * Finds a language by its ID.
     * @param id The language ID.
     * @return The found language or null if not found.
     */
    public Language findById(Integer id) {
        return languageDAO.findById(id);
    }

    /**
     * Gets a list of all language strings.
     * This method retrieves all languages and returns their string values.
     * @return Observable List of all language strings.
     */
    public ObservableList<String> getAllStrings(){
        List<Language> languages = findAll();
        List<String> languageValues = new java.util.ArrayList<>();
        for (Language language : languages) {
            languageValues.add(language.getLanguage());
        }
        ObservableList<String> observableLanguageValues = FXCollections.observableArrayList(languageValues);

        return observableLanguageValues;
    }

    /**
     * Finds all languages.
     * @return List of all languages.
     */
    public List<Language> findAll() {
        return languageDAO.findAll();
    }

    /**
     * Finds language by its value.
     * @param languageValue The language string.
     * @return The found language or null if not found.
     */
    public Language findByName(String languageValue) {
        Language language = languageDAO.findByLanguage(languageValue);
        if (language == null) {
            return null; // Return null if not found, could add creation logic here if needed
        }
        return language;
    }
}
