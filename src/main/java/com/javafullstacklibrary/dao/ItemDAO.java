package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private final EntityManager entityManager;
    
    public ItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    // #region Basic CRUD Operations
    
    /**
     * Saves a new or updates an existing item
     * @param item The item to save
     * @return The saved item with generated ID (if new)
     */
    public Item save(Item item) {
        try {
            entityManager.getTransaction().begin();
            
            if (item.getId() == null) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            
            entityManager.getTransaction().commit();
            return item;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    /**
     * Finds an item by its ID
     * @param id The item ID
     * @return The found item or null if not found
     */
    public Item findById(Integer id) {
        return entityManager.find(Item.class, id);
    }
    
    /**
     * Finds all items regardless of type
     * @return List of all items
     */
    public List<Item> findAll() {
        return entityManager.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }
    
    /**
     * Deletes an item
     * @param item The item to delete
     */
    public void delete(Item item) {
        try {
            entityManager.getTransaction().begin();
            
            if (entityManager.contains(item)) {
                entityManager.remove(item);
            } else {
                entityManager.remove(entityManager.merge(item));
            }
            
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    /**
     * Deletes an item by its ID
     * @param id The ID of the item to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Integer id) {
        Item item = findById(id);
        if (item != null) {
            delete(item);
            return true;
        }
        return false;
    }
    
    // #endregion
    
    // #region Search Method
    
    /**
     * Flexible search method that looks for matches in title, keywords, creators, 
     * genres, actors, and language
     * 
     * @param searchTerm The term to search for
     * @return List of items matching the search criteria, preserving their concrete types
     */
    public List<Item> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Normalize search term
        searchTerm = "%" + searchTerm.toLowerCase() + "%";
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        
        // Create predicates for each field to search
        List<Predicate> predicates = new ArrayList<>();
        
        // Search in title
        predicates.add(cb.like(cb.lower(root.get("title")), searchTerm));
        
        // Search in item's related entities
        // Keywords
        Join<Item, Keyword> keywordJoin = root.join("keywords", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(keywordJoin.get("keyword")), searchTerm));
        
        // Creators
        Join<Item, Creator> creatorJoin = root.join("creators", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(creatorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(creatorJoin.get("lastName")), searchTerm)
        ));
        
        // Genres
        Join<Item, Genre> genreJoin = root.join("genres", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(genreJoin.get("genre")), searchTerm));
        
        // Actors
        Join<Item, Actor> actorJoin = root.join("actors", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(actorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(actorJoin.get("lastName")), searchTerm)
        ));
        
        // Language
        Join<Item, Language> languageJoin = root.join("language", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(languageJoin.get("language")), searchTerm));
        
        // Combine predicates with OR (any match is a hit)
        query.where(cb.or(predicates.toArray(new Predicate[0])));
        
        // Remove duplicates (since we're joining multiple tables)
        query.distinct(true);
        
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Counts the number of items matching the search term in title, keywords, creators, 
     * genres, actors, and language
     * 
     * @param searchTerm The term to search for
     * @return The number of distinct items that match the search criteria
     */
    public int searchMatchCount(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return 0;
        }
        
        // Normalize search term
        searchTerm = "%" + searchTerm.toLowerCase() + "%";
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Item> root = query.from(Item.class);
        
        // Create predicates for each field to search
        List<Predicate> predicates = new ArrayList<>();
        
        // Search in title
        predicates.add(cb.like(cb.lower(root.get("title")), searchTerm));
        
        // Search in item's related entities
        // Keywords
        Join<Item, Keyword> keywordJoin = root.join("keywords", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(keywordJoin.get("keyword")), searchTerm));
        
        // Creators
        Join<Item, Creator> creatorJoin = root.join("creators", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(creatorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(creatorJoin.get("lastName")), searchTerm)
        ));
        
        // Genres
        Join<Item, Genre> genreJoin = root.join("genres", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(genreJoin.get("genre")), searchTerm));
        
        // Actors
        Join<Item, Actor> actorJoin = root.join("actors", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(actorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(actorJoin.get("lastName")), searchTerm)
        ));
        
        // Language
        Join<Item, Language> languageJoin = root.join("language", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(languageJoin.get("language")), searchTerm));
        
        // Combine predicates with OR (any match is a hit)
        query.where(cb.or(predicates.toArray(new Predicate[0])));
        
        // Remove duplicates (since we're joining multiple tables)
        query.distinct(true);
        
        // Count the results instead of returning the entities
        query.select(cb.count(root));
        
        return entityManager.createQuery(query).getSingleResult().intValue();
    }
    
    // #endregion
    
    // #region Copy-related Methods
    
    /**
     * Gets all copies of an item
     * @param itemId The item ID
     * @return List of copies for the item
     */
    public List<ItemCopy> findCopiesByItemId(Integer itemId) {
        Item item = findById(itemId);
        if (item == null) {
            return new ArrayList<>();
        }
        return item.getCopies();
    }
    
    /**
     * Adds a new copy to an item
     * @param itemId The item ID
     * @return The added copy, or null if item not found
     */
    public ItemCopy addCopyToItem(Integer itemId) {
        try {
            entityManager.getTransaction().begin();
            
            Item item = findById(itemId);
            if (item == null) {
                entityManager.getTransaction().rollback();
                return null;
            }
            
            ItemCopy copy = new ItemCopy();
            copy.setItem(item);
            copy.setDateAdded(java.time.LocalDate.now());
            copy.setBarcode(generateBarcode()); // You'll need to implement this method
            copy.setIsReference(false); // Default to non-reference copy
            
            entityManager.persist(copy);
            item.getCopies().add(copy);
            
            entityManager.getTransaction().commit();
            return copy;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

        /**
     * Generates a unique barcode for a new item copy
     * @return A new unique barcode
     */
    private String generateBarcode() {
        // Simple implementation - you might want to create a more sophisticated algorithm
        return "LIB-" + System.currentTimeMillis();
    }
    
    // #endregion
}