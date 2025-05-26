package com.javafullstacklibrary.model.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.javafullstacklibrary.model.ItemTestParams;
import com.javafullstacklibrary.model.Language;

import jakarta.persistence.EntityManager;

import java.util.List;

public class LanguageTestUtils {

    /**
     * This method finds or creates a new language in the database.
     * It sets the properties of the language based on the provided parameters.
     *
     * @param em The EntityManager to use for database operations
     * @param params The ItemTestParams object containing language parameters
     * @param createdLanguageIds The list of IDs of created languages
     * @return The created or found Language object
     */
    public static Language findOrCreateLanguage(EntityManager em, ItemTestParams params, List<Integer> createdLanguageIds) {
        Language language = em.createQuery(
                "SELECT l FROM Language l WHERE l.language = :language",
                Language.class)
            .setParameter("language", params.language())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (language == null) {
            language = new Language();
            language.setLanguage(params.language());
            em.persist(language);
            createdLanguageIds.add(language.getId());
        }
        return language;
    }


    /**
     * This method initializes or fetches a language from the database.
     * It ensures that the language is not null and returns it.
     *
     * @param em The EntityManager to use for database operations
     * @param params The ItemTestParams object containing language parameters
     * @param createdLanguageIds The list of IDs of created languages
     * @return The initialized or fetched Language object
     */
    public static Language initializeOrFetchLanguage(EntityManager em, ItemTestParams params, List<Integer> createdLanguageIds) {
        Language language = findOrCreateLanguage(em, params, createdLanguageIds);
        assertNotNull(language, "Language should not be null.");
        return language;
    }
    
}
