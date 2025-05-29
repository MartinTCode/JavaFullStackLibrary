package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.exception.ValidationException;
import com.javafullstacklibrary.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @throws ValidationException if validation fails
     * @throws RuntimeException if any other error occurs during save
     */
    public Item save(Item item) {
        try {
            entityManager.getTransaction().begin();
            
            if (item.getId() == null) {
                validateIdentifierUnique(item); // Validate before persisting
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item); // No valitation of unique identifiers for updates since they will already exist
            }
            
            entityManager.getTransaction().commit();
            return item;
        } catch (ValidationException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e; // Re-throw validation exceptions as-is
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save item", e);
        }
    }

    public Map<String, String> getParameterMap(Item item) {
        return item.getParameterMap();
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
        
        // Explicitly join the copies collection to fetch it eagerly
        root.fetch("copies", jakarta.persistence.criteria.JoinType.LEFT);
        
        // Combine predicates with OR (any match is a hit)
        query.where(cb.or(predicates.toArray(new Predicate[0])));
        
        // Remove duplicates (since we're joining multiple tables)
        query.distinct(true);
        
        List<Item> results = entityManager.createQuery(query).getResultList();
        
        // Initialize other collections that might be needed
        for (Item item : results) {
            if (item.getCopies() != null) {
                org.hibernate.Hibernate.initialize(item.getCopies());
            }
            if (item.getCreators() != null) {
                org.hibernate.Hibernate.initialize(item.getCreators());
            }
            if (item.getKeywords() != null) {
                org.hibernate.Hibernate.initialize(item.getKeywords());
            }
            if (item.getGenres() != null) {
                org.hibernate.Hibernate.initialize(item.getGenres());
            }
            if (item.getActors() != null) {
                org.hibernate.Hibernate.initialize(item.getActors());
            }
            if (item.getLanguage() != null) {
                org.hibernate.Hibernate.initialize(item.getLanguage());
            }
            // Initialize other collections as needed
        }
        
        return results;
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
    
        // Create a subquery to get distinct item IDs
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<Item> idRoot = idQuery.from(Item.class);
        
        // Create predicates for each field to search (same as in search method)
        List<Predicate> predicates = new ArrayList<>();
        
        // Search in title
        predicates.add(cb.like(cb.lower(idRoot.get("title")), searchTerm));
        
        // Search in item's related entities
        // Keywords
        Join<Item, Keyword> keywordJoin = idRoot.join("keywords", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(keywordJoin.get("keyword")), searchTerm));
        
        // Creators
        Join<Item, Creator> creatorJoin = idRoot.join("creators", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(creatorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(creatorJoin.get("lastName")), searchTerm)
        ));
        
        // Genres
        Join<Item, Genre> genreJoin = idRoot.join("genres", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(genreJoin.get("genre")), searchTerm));
        
        // Actors
        Join<Item, Actor> actorJoin = idRoot.join("actors", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.or(
            cb.like(cb.lower(actorJoin.get("firstName")), searchTerm),
            cb.like(cb.lower(actorJoin.get("lastName")), searchTerm)
        ));
        
        // Language
        Join<Item, Language> languageJoin = idRoot.join("language", jakarta.persistence.criteria.JoinType.LEFT);
        predicates.add(cb.like(cb.lower(languageJoin.get("language")), searchTerm));
        
        // Combine predicates with OR (any match is a hit)
        idQuery.where(cb.or(predicates.toArray(new Predicate[0])));
        
        // Select only IDs and make them distinct
        idQuery.select(idRoot.get("id")).distinct(true);
        
        // Now count the results from the subquery
        List<Integer> distinctIds = entityManager.createQuery(idQuery).getResultList();
        return distinctIds.size();
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

    // #region validation methods
    /**
     * Validates that the identifier and identifier2 fields of an item are unique
     * within the same item type (e.g., Book, Journal, etc.)
     * 
     * @param item The item to validate
     * @throws ValidationException if validation fails
     */
    private void validateIdentifierUnique(Item item) {
        Map<String, String> errors = new HashMap<>();

        // check that at least one identifier is set
        if (item.getIdentifier() == null || item.getIdentifier().trim().isEmpty()) {
            if (item.getIdentifier2() == null || item.getIdentifier2().isEmpty()) {
                errors.put("identifier", "At least one identifier must be set");
            }
        }
        if (item.getIdentifier2() == null || item.getIdentifier2().isEmpty()) {
            if (item.getIdentifier() == null || item.getIdentifier().isEmpty()) {
                errors.put("identifier2", "At least one identifier must be set");
            }
        }
        
        // Check identifier uniqueness within the same item type
        if (item.getIdentifier() != null && !item.getIdentifier().isEmpty()) {
            String query = "SELECT COUNT(i) FROM Item i WHERE i.identifier = :identifier AND i.id <> :id AND TYPE(i) = :itemType";
            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("identifier", item.getIdentifier())
                    .setParameter("id", item.getId() != null ? item.getId() : -1)
                    .setParameter("itemType", item.getClass())
                    .getSingleResult();
            if (count > 0) {
                errors.put("identifier", "This identifier already exists for " + item.getClass().getSimpleName() + " items");
            }
        }
        
        // Check identifier2 uniqueness within the same item type
        if (item.getIdentifier2() != null && !item.getIdentifier2().isEmpty()) {
            String query = "SELECT COUNT(i) FROM Item i WHERE i.identifier2 = :identifier2 AND i.id <> :id AND TYPE(i) = :itemType";
            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("identifier2", item.getIdentifier2())
                    .setParameter("id", item.getId() != null ? item.getId() : -1)
                    .setParameter("itemType", item.getClass())
                    .getSingleResult();
            if (count > 0) {
                errors.put("identifier2", "This identifier already exists for " + item.getClass().getSimpleName() + " items");
            }
        }
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title cannot be empty", errors);
        }
        if (item.getLanguage() == null) {
            throw new ValidationException("Language cannot be empty", errors);
        }
        if (item.getCreators() == null || item.getCreators().isEmpty()) {
            throw new ValidationException("At least one creator must be specified", errors);
        }
        if (item.getKeywords() == null || item.getKeywords().isEmpty()) {
            throw new ValidationException("At least one keyword must be specified", errors);
        }
        if (item.getGenres() == null || item.getGenres().isEmpty()) {
            throw new ValidationException("At least one genre must be specified", errors);
        }
        
        // Throw exception if any validation errors found
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors);
        }
    }
    // #endregion
}