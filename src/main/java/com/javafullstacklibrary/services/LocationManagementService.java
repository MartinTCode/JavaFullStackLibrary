package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.LocationDAO;
import com.javafullstacklibrary.model.Location;

import java.util.List;

public class LocationManagementService {

    private final LocationDAO locationDAO;

    public LocationManagementService(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
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
