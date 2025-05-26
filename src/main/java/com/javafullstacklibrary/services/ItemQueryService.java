package com.javafullstacklibrary.services;

import com.javafullstacklibrary.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for querying items from the database.
 */
public class ItemQueryService {
    private final EntityManagerFactory emf;
    
    /**
     * Constructor that initializes the EntityManagerFactory.
     */
    public ItemQueryService() {
        // Assuming you have a persistence unit named "libraryPU" in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
    }
    
    /**
     * Searches for items based on a query string.
     * Searches in title, publisher, and identifiers.
     * Eagerly fetches related collections to avoid LazyInitializationException.
     *
     * @param query The search query
     * @param offset Pagination offset
     * @param limit Maximum number of results to return
     * @return List of matching Item objects with eagerly loaded collections
     */
    public List<Item> searchItems(String query, int offset, int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Item> cq = cb.createQuery(Item.class);
            Root<Item> root = cq.from(Item.class);
            
            // Eagerly fetch related collections to avoid LazyInitializationException
            root.fetch("copies", JoinType.LEFT);
            root.fetch("language", JoinType.LEFT);
            root.fetch("location", JoinType.LEFT);
            // Join creators for searching in creator names
            root.fetch("creators", JoinType.LEFT);
            var creators = root.join("creators", JoinType.LEFT);
            
            // Split the query into words for more flexible searching
            String[] keywords = query.toLowerCase().split("\\s+");
            List<Predicate> predicates = new ArrayList<>();
            
            for (String keyword : keywords) {
                // Search in title
                Predicate titlePredicate = cb.like(
                    cb.lower(root.get("title")), 
                    "%" + keyword + "%"
                );
                
                // Search in publisher
                Predicate publisherPredicate = cb.like(
                    cb.lower(root.get("publisher")), 
                    "%" + keyword + "%"
                );
                
                // Search in identifiers (ISBN, ISSN, etc.)
                Predicate identifierPredicate = cb.like(
                    cb.lower(root.get("identifier")), 
                    "%" + keyword + "%"
                );
                
                Predicate identifier2Predicate = cb.like(
                    cb.lower(root.get("identifier2")), 
                    "%" + keyword + "%"
                );

                // Search in creator's full name using concatenation
                Predicate creatorFullNamePredicate = cb.like(
                    cb.lower(cb.concat(cb.concat(creators.get("firstName"), " "), creators.get("lastName"))),
                    "%" + keyword + "%"
                );
                
                // Combine with OR for this keyword
                predicates.add(cb.or(
                    titlePredicate, 
                    publisherPredicate, 
                    identifierPredicate,
                    identifier2Predicate,
                    creatorFullNamePredicate
                ));
            }
            
            // All keywords must match (combine with AND)
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
            
            // Order by title for consistent results
            cq.orderBy(cb.asc(root.get("title")));
            
            // Create a distinct query to avoid duplicates due to fetch joins
            cq.distinct(true);
            
            TypedQuery<Item> query2 = em.createQuery(cq);
            query2.setFirstResult(offset);
            query2.setMaxResults(limit);
            
            return query2.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Counts total number of items matching a search query.
     *
     * @param query The search query
     * @return Total count of matching items
     */
    public long countSearchResults(String query) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Item> root = cq.from(Item.class);
            
            // Count query
            cq.select(cb.count(root));

            // Join creators for searching
            var creators = root.join("creators", JoinType.LEFT);
            
            // Split the query into words
            String[] keywords = query.toLowerCase().split("\\s+");
            List<Predicate> predicates = new ArrayList<>();
            
            for (String keyword : keywords) {
                // Same predicates as in searchItems
                Predicate titlePredicate = cb.like(
                    cb.lower(root.get("title")), 
                    "%" + keyword + "%"
                );
                
                Predicate publisherPredicate = cb.like(
                    cb.lower(root.get("publisher")), 
                    "%" + keyword + "%"
                );
                
                Predicate identifierPredicate = cb.like(
                    cb.lower(root.get("identifier")), 
                    "%" + keyword + "%"
                );
                
                Predicate identifier2Predicate = cb.like(
                    cb.lower(root.get("identifier2")), 
                    "%" + keyword + "%"
                );

                // Search in creator's full name using concatenation
            Predicate creatorFullNamePredicate = cb.like(
                cb.lower(cb.concat(cb.concat(creators.get("firstName"), " "), creators.get("lastName"))),
                "%" + keyword + "%"
            );
                
                predicates.add(cb.or(
                    titlePredicate, 
                    publisherPredicate, 
                    identifierPredicate,
                    identifier2Predicate,
                    creatorFullNamePredicate
                ));
            }
            
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
            
            return em.createQuery(cq).getSingleResult();
        } finally {
            em.close();
        }
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