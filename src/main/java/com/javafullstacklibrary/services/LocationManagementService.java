package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.KeywordDAO;
import com.javafullstacklibrary.dao.LocationDAO;
import com.javafullstacklibrary.model.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class LocationManagementService {

    private final LocationDAO locationDAO;
    private final EntityManagerFactory emf;

    public LocationManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.locationDAO = new LocationDAO(em);
    }

    /**
     * Adds a new location.
     * @param location The location to add.
     * @return The saved location with generated ID.
     */
    public Location addLocation(Location location) {
        return locationDAO.save(location);
    }

    /**
     * Updates an existing location.
     * @param location The location with updated fields.
     * @return The updated location.
     */
    public Location updateLocation(Location location) {
        return locationDAO.save(location);
    }

    /**
     * Deletes a location by its ID.
     * @param id The ID of the location to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteLocationById(Integer id) {
        return locationDAO.deleteById(id);
    }

    /**
     * Finds a location by its ID.
     * @param id The location ID.
     * @return The found location or null if not found.
     */
    public Location findById(Integer id) {
        return locationDAO.findById(id);
    }

    /**
     * Gets a list of all unique floor strings from locations.
     * This method retrieves all locations and returns their floor values.
     * @return
     */
    public List<String> getAllFloorStrings() {
        List<Location> locations = locationDAO.findAll();
        List<String> floors = new java.util.ArrayList<>();
        for (Location location : locations) {
            String floor = location.getFloor();
            if (floor != null && !floors.contains(floor)) {
                floors.add(floor);
            }
        }
        return floors;
    }

    /**
     * Gets a list of all unique section strings from locations.
     * This method retrieves all locations and returns their section values.
     * @return List of all section strings.
     */
    public List<String> getAllSectionStrings() {
        List<Location> locations = locationDAO.findAll();
        List<String> sections = new java.util.ArrayList<>();
        for (Location location : locations) {
            String section = location.getSection();
            if (section != null && !sections.contains(section)) {
                sections.add(section);
            }
        }
        return sections;
    }

    /**
     * Gets a list of all unique shelf strings from locations.
     * This method retrieves all locations and returns their shelf values.
     * @return List of all shelf strings.
     */
    public List<String> getAllShelfStrings() {
        List<Location> locations = locationDAO.findAll();
        List<String> shelves = new java.util.ArrayList<>();
        for (Location location : locations) {
            String shelf = location.getShelf();
            if (shelf != null && !shelves.contains(shelf)) {
                shelves.add(shelf);
            }
        }
        return shelves;
    }

    /**
     * Gets a list of all unique position strings from locations.
     * This method retrieves all locations and returns their position values.
     * @return List of all position strings.
     */
    public List<String> getAllPositionStrings() {
        List<Location> locations = locationDAO.findAll();
        List<String> positions = new java.util.ArrayList<>();
        for (Location location : locations) {
            String position = location.getPosition();
            if (position != null && !positions.contains(position)) {
                positions.add(position);
            }
        }
        return positions;
    }

    /**
     * Finds all locations.
     * @return List of all locations.
     */
    public List<Location> findAll() {
        return locationDAO.findAll();
    }

    /**
     * Finds or creates a location by its fields.
     * @param floor The floor.
     * @param section The section.
     * @param shelf The shelf.
     * @param position The position.
     * @return The found or newly created location.
     */
    public Location findOrCreate(String floor, String section, String shelf, String position) {
        Location location = locationDAO.findByFields(floor, section, shelf, position);
        if (location == null) {
            location = new Location(floor, section, shelf, position);
            location = locationDAO.save(location);
        }
        return location;
    }
}
