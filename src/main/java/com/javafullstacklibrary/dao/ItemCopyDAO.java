package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ItemCopyDAO {
    
    private EntityManager entityManager;
    
    public ItemCopyDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public ItemCopy save(ItemCopy itemCopy) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ItemCopy result;
            if (itemCopy.getId() == null) {
                entityManager.persist(itemCopy);
                result = itemCopy;
            } else {
                result = entityManager.merge(itemCopy);
            }
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public Optional<ItemCopy> findById(Integer id) {
        ItemCopy itemCopy = entityManager.find(ItemCopy.class, id);
        return Optional.ofNullable(itemCopy);
    }
    
    public Optional<ItemCopy> findByBarcode(String barcode) {
        try {
            TypedQuery<ItemCopy> query = entityManager.createQuery(
                "SELECT ic FROM ItemCopy ic WHERE ic.barcode = :barcode", ItemCopy.class);
            query.setParameter("barcode", barcode);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<ItemCopy> findAll() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic", ItemCopy.class);
        return query.getResultList();
    }
    
    public List<ItemCopy> findByItemId(Integer itemId) {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.item.id = :itemId", ItemCopy.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }
    
    public List<ItemCopy> findByItem(Item item) {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.item = :item", ItemCopy.class);
        query.setParameter("item", item);
        return query.getResultList();
    }
    
    public List<ItemCopy> findReferenceItems() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.isReference = true", ItemCopy.class);
        return query.getResultList();
    }
    
    public List<ItemCopy> findNonReferenceItems() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.isReference = false", ItemCopy.class);
        return query.getResultList();
    }
    
    public List<ItemCopy> findAvailableItems() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT ic FROM ItemCopy ic WHERE ic.isReference = false " +
            "AND ic.id NOT IN (SELECT l.itemCopy.id FROM Loan l WHERE l.returnedDate IS NULL)", 
            ItemCopy.class);
        return query.getResultList();
    }
    
    public List<ItemCopy> findItemsOnLoan() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT DISTINCT ic FROM ItemCopy ic JOIN ic.loans l WHERE l.returnedDate IS NULL", 
            ItemCopy.class);
        return query.getResultList();
    }
    
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ItemCopy itemCopy = entityManager.find(ItemCopy.class, id);
            if (itemCopy != null) {
                entityManager.remove(itemCopy);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public void delete(ItemCopy itemCopy) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(itemCopy)) {
                entityManager.remove(itemCopy);
            } else {
                entityManager.remove(entityManager.merge(itemCopy));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public boolean existsById(Integer id) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(ic) FROM ItemCopy ic WHERE ic.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }
    
    public boolean existsByBarcode(String barcode) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(ic) FROM ItemCopy ic WHERE ic.barcode = :barcode", Long.class);
        query.setParameter("barcode", barcode);
        return query.getSingleResult() > 0;
    }
    
    public boolean isAvailable(Integer itemCopyId) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.itemCopy.id = :itemCopyId AND l.returnedDate IS NULL", 
            Long.class);
        query.setParameter("itemCopyId", itemCopyId);
        return query.getSingleResult() == 0;
    }
    
    public long countByItem(Item item) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(ic) FROM ItemCopy ic WHERE ic.item = :item", Long.class);
        query.setParameter("item", item);
        return query.getSingleResult();
    }
    
    public long countAvailableByItem(Item item) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(ic) FROM ItemCopy ic WHERE ic.item = :item " +
            "AND ic.isReference = false " +
            "AND ic.id NOT IN (SELECT l.itemCopy.id FROM Loan l WHERE l.returnedDate IS NULL)", 
            Long.class);
        query.setParameter("item", item);
        return query.getSingleResult();
    }
}