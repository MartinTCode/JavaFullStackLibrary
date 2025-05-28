package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.CreatorDAO;
import com.javafullstacklibrary.dao.GenreDAO;
import com.javafullstacklibrary.model.Creator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CreatorManagementService {

    private final EntityManagerFactory emf;
    private final CreatorDAO creatorDAO;
    

    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public CreatorManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.creatorDAO = new CreatorDAO(em);
    }

    /**
     * Adds a new creator.
     * @param creator The creator to add.
     * @return The saved creator with generated ID.
     */
    public Creator addCreator(Creator creator) {
        return creatorDAO.save(creator);
    }

    /**
     * Updates an existing creator.
     * @param creator The creator with updated fields.
     * @return The updated creator.
     */
    public Creator updateCreator(Creator creator) {
        return creatorDAO.save(creator);
    }

    /**
     * Deletes a creator by its ID.
     * @param id The ID of the creator to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteCreatorById(Integer id) {
        return creatorDAO.deleteById(id);
    }

    /**
     * Finds a creator by its ID.
     * @param id The creator ID.
     * @return The found creator or null if not found.
     */
    public Creator findById(Integer id) {
        return creatorDAO.findById(id);
    }

    /**
     * Get list of all creators' full names.
     * @return Observable List of all creators' full names.
     */
    public ObservableList<String> getAllFullNames() {
        List<Creator> creators = creatorDAO.findAll();
        List<String> fullNames = new java.util.ArrayList<>();
        for (Creator creator : creators) {
            String fullName = (creator.getFirstName() + " " + creator.getLastName()).trim();
            fullNames.add(fullName);
        }
        ObservableList<String> observableFullNames = FXCollections.observableArrayList(fullNames);

        return observableFullNames;
    }

    /**
     * Finds all creators.
     * @return List of all creators.
     */
    public List<Creator> findAll() {
        return creatorDAO.findAll();
    }

    /**
     * Finds or creates a creator by first and last name.
     * @param firstName The creator's first name.
     * @param lastName The creator's last name.
     * @return The found or newly created creator.
     */
    public Creator findByName(String firstName, String lastName) {
        Creator creator = creatorDAO.findByName(firstName, lastName);
        if (creator == null) {
            return null;
        }
        return creator;
    }

    /**
     * Finds or creates a creator by a single full name string.
     * @param fullName The creator's full name (e.g., "Jane Doe").
     * @return 
     */
    public Creator findByFullName(String fullName) {
        String[] parts = fullName.trim().split("\\s+", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";
        Creator creator = creatorDAO.findByName(firstName, lastName);
        if (creator == null) {
            return null; // Creator not found, could add creation logic here if needed
        }
        return creator;
    }
}
