package com.javafullstacklibrary.services;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.dao.ItemDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * Service class for querying items from the database.
 */
public class ItemQueryService {
    private final EntityManagerFactory emf;
    private final ItemDAO itemDAO;
    
    /**
     * Constructor that initializes the EntityManagerFactory and ItemDAO.
     */
    public ItemQueryService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.itemDAO = new ItemDAO(em);
    }
    
    /**
     * Searches for items based on a query string.
     * Uses ItemDAO's search method which searches in multiple fields.
     *
     * @param query The search query
     * @param offset Pagination offset
     * @param limit Maximum number of results to return
     * @return List of matching Item objects
     */
    public List<Item> searchItems(String query, int offset, int limit) {
        List<Item> allResults = itemDAO.search(query);
        
        // Manual pagination since ItemDAO.search doesn't support it directly
        int endIndex = Math.min(offset + limit, allResults.size());
        if (offset >= allResults.size()) {
            return List.of(); // Empty list if offset is beyond results
        }
        
        return allResults.subList(offset, endIndex);
    }
    
    /**
     * Counts total number of items matching a search query.
     *
     * @param query The search query
     * @return Total count of matching items
     */
    public long countSearchResults(String query) {
        return itemDAO.searchMatchCount(query);
    }
    
    /**
     * Closes resources held by this service.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}