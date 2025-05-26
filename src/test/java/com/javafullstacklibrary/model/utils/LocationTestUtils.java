package com.javafullstacklibrary.model.utils;

import com.javafullstacklibrary.model.ItemTestParams;
import com.javafullstacklibrary.model.Location;

import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

public class LocationTestUtils {

    
    /**
     * This method finds or creates a new location in the database.
     * It sets the properties of the location based on the provided parameters.
     *
     * @param em The EntityManager to use for database operations
     * @param params The ItemTestParams object containing location parameters
     * @param createdLocationIds The list of IDs of created locations
     * @return The created or found Location object
     */
    public static Location findOrCreateLocation(EntityManager em, ItemTestParams params, List<Integer> createdLocationIds) {
        Location location = em.createQuery(
                "SELECT l FROM Location l WHERE l.floor = :floor AND l.section = :section AND l.shelf = :shelf AND l.position = :position",
                Location.class)
            .setParameter("floor", params.floor())
            .setParameter("section", params.section())
            .setParameter("shelf", params.shelf())
            .setParameter("position", params.position())
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (location == null) {
            location = new Location();
            location.setFloor(params.floor());
            location.setSection(params.section());
            location.setShelf(params.shelf());
            location.setPosition(params.position());
            em.persist(location);
            createdLocationIds.add(location.getId());
        }
        return location;
    }

    /**
     * This method initializes or fetches a location from the database.
     * It ensures that the location is not null and returns it.
     *
     * @param em The EntityManager to use for database operations
     * @param params The ItemTestParams object containing location parameters
     * @param createdLocationIds The list of IDs of created locations
     * @return The initialized or fetched Location object
     */
    public static Location initializeOrFetchLocation(EntityManager em, ItemTestParams params, List<Integer> createdLocationIds) {
        Location location = findOrCreateLocation(em, params, createdLocationIds);
        assertNotNull(location, "Location should not be null.");
        return location;
    }
    
}
