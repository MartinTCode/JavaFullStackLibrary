package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ActorDAO;
import com.javafullstacklibrary.model.Actor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ActorManagementService {

    private final EntityManagerFactory emf;
    private final ActorDAO actorDAO;
    

    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public ActorManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.actorDAO = new ActorDAO(em);
    }

    /**
     * Updates an existing actor.
     * @param actor The actor with updated fields.
     * @return The updated actor.
     */
    public Actor updateActor(Actor actor) {
        return actorDAO.save(actor);
    }

    /**
     * Deletes an actor by its ID.
     * @param id The ID of the actor to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteActorById(Integer id) {
        return actorDAO.deleteById(id);
    }

    /**
     * Finds an actor by its ID.
     * @param id The actor ID.
     * @return The found actor or null if not found.
     */
    public Actor findById(Integer id) {
        return actorDAO.findById(id);
    }

    /**
     * Get list of all actors full names.
     * @return Observable List of all actors' full names.
     */
    public ObservableList<String> getAllActorsFullNames() {
        List<Actor> actors = findAll();
        List<String> fullNames = new java.util.ArrayList<>();
        for (Actor actor : actors) {
            fullNames.add(actor.getFullName());
        }
        ObservableList<String> observableFullNames = FXCollections.observableArrayList(fullNames);
        return observableFullNames;
    }

    /**
     * Finds all actors.
     * @return List of all actors.
     */
    public List<Actor> findAll() {
        return actorDAO.findAll();
    }

    /**
     * Finds or creates an actor by first and last name.
     * @param firstName The actor's first name.
     * @param lastName The actor's last name.
     * @return The found or newly created actor.
     */
    public Actor findByNames(String firstName, String lastName) {
        Actor actor = actorDAO.findByName(firstName, lastName);
        if (actor == null) {
            actor = new Actor();
            actor.setFirstName(firstName);
            actor.setLastName(lastName);
            actor = actorDAO.save(actor);
        }
        return actor;
    }

    /**
     * Finds or creates an actor by a single full name string.
     * @param fullName The actor's full name (e.g., "John Smith").
     * @return The found or newly created actor.
     */
    public Actor findByFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return null;
        }
        String[] parts = fullName.trim().split("\\s+", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";
        Actor actor = actorDAO.findByName(firstName, lastName);
        if (actor == null) {
            return null; // If the actor is not found, return null. Could add creation logic here if needed.
        }
        return actor;
    }
}
