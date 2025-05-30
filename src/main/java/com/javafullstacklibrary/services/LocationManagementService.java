package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.LocationDAO;
import com.javafullstacklibrary.model.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class LocationManagementService implements AutoCloseable {

    private final LocationDAO locationDAO;
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public LocationManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        this.em = emf.createEntityManager();
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
     * Gets a map of all location attributes (floors, sections, shelves, positions).
     * This method retrieves all locations and returns their attributes in a map.
     * @return Map with keys "floors", "sections", "shelves", "positions" and their corresponding observable lists.
     */
    public Map< String, ObservableList<String> > getLocationDetails() {
        // Get all Locations <-- List<Location> locations = locationDAO.findAll();

        // Retrieve all locations from the DAO
        List<Location> locations = locationDAO.findAll();

        // Create a map to hold the attributes
        Map< String, ObservableList<String> > locationsAttributesList = new HashMap<>();

        // Create Sets to hold unique values
        Set<String> floorsSet = new HashSet<>();
        Set<String> sectionsSet = new HashSet<>();
        Set<String> shelvesSet = new HashSet<>();
        Set<String> positionsSet = new HashSet<>();

        // Populate the Sets with unique values from locations
        for (Location l: locations) {
            floorsSet.add(l.getFloor());
            sectionsSet.add(l.getSection());
            shelvesSet.add(l.getShelf());
            positionsSet.add(l.getPosition());
        }

        // Convert Sets to Lists
        List<String> floors = new ArrayList<>(floorsSet);
        List<String> sections = new ArrayList<>(sectionsSet);
        List<String> shelves = new ArrayList<>(shelvesSet);
        List<String> positions = new ArrayList<>(positionsSet);
        // now we have all the attributes in lists of all locations
        locationsAttributesList.put("floors", FXCollections.observableArrayList(floors));
        locationsAttributesList.put("sections", FXCollections.observableArrayList(sections));
        locationsAttributesList.put("shelves", FXCollections.observableArrayList(shelves));
        locationsAttributesList.put("positions", FXCollections.observableArrayList(positions));
        
        return locationsAttributesList;
    }

    /**
     * Finds all locations.
     * @return List of all locations.
     */
    public List<Location> findAll() {
        return locationDAO.findAll();
    }

    public Location findByFields(String floor, String section, String shelf, String position) {
        return locationDAO.findByFields(floor, section, shelf, position);
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
