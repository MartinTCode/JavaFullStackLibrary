package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.CreatorDAO;
import com.javafullstacklibrary.model.Creator;

import java.util.List;

public class CreatorManagementService {

    private final CreatorDAO creatorDAO;

    public CreatorManagementService(CreatorDAO creatorDAO) {
        this.creatorDAO = creatorDAO;
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
    public Creator findOrCreate(String firstName, String lastName) {
        Creator creator = creatorDAO.findByName(firstName, lastName);
        if (creator == null) {
            creator = new Creator(firstName, lastName);
            creator = creatorDAO.save(creator);
        }
        return creator;
    }

    /**
     * Finds or creates a creator by a single full name string.
     * @param fullName The creator's full name (e.g., "Jane Doe").
     * @return The found or newly created creator.
     */
    public Creator findOrCreateByFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return null;
        }
        String[] parts = fullName.trim().split("\\s+", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";
        return findOrCreate(firstName, lastName);
    }
}
