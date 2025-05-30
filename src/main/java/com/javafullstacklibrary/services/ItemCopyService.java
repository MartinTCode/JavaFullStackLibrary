package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemCopyDAO;
import com.javafullstacklibrary.dao.ItemDAO;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class ItemCopyService {
    private final ItemCopyDAO itemCopyDAO;
    private final EntityManagerFactory emf;
    
    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public ItemCopyService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.itemCopyDAO = new ItemCopyDAO(em);
    }
    
    public ItemCopy createItemCopy(Item item, String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Barcode cannot be null or empty");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        
        ItemCopy itemCopy = new ItemCopy();
        itemCopy.setBarcode(barcode);
        itemCopy.setItem(item);
        return itemCopyDAO.save(itemCopy);
    }
    
    public ItemCopy updateItemCopy(ItemCopy itemCopy) {
        if (itemCopy.getId() == null) {
            throw new IllegalArgumentException("Item copy ID cannot be null for update operation");
        }
        
        Optional<ItemCopy> existingCopy = itemCopyDAO.findById(itemCopy.getId());
        if (existingCopy.isEmpty()) {
            throw new IllegalArgumentException("Item copy not found with ID: " + itemCopy.getId());
        }
        
        if (itemCopy.getBarcode() != null) {
            Optional<ItemCopy> copyWithBarcode = itemCopyDAO.findByBarcode(itemCopy.getBarcode());
            if (copyWithBarcode.isPresent() && !copyWithBarcode.get().getId().equals(itemCopy.getId())) {
                throw new IllegalArgumentException("Another item copy already exists with this barcode");
            }
        }
        
        return itemCopyDAO.save(itemCopy);
    }
    
    public Optional<ItemCopy> findById(Integer id) {
        return itemCopyDAO.findById(id);
    }
    
    public Optional<ItemCopy> findByBarcode(String barcode) {
        return itemCopyDAO.findByBarcode(barcode);
    }
    
    public List<ItemCopy> findAll() {
        return itemCopyDAO.findAll();
    }
    
    public List<ItemCopy> findByItem(Item item) {
        return itemCopyDAO.findByItem(item);
    }
    
    public List<ItemCopy> findByItemId(Integer itemId) {
        return itemCopyDAO.findByItemId(itemId);
    }
    
    public List<ItemCopy> findReferenceItems() {
        return itemCopyDAO.findReferenceItems();
    }
    
    public List<ItemCopy> findNonReferenceItems() {
        return itemCopyDAO.findNonReferenceItems();
    }
    
    public List<ItemCopy> findAvailableItems() {
        return itemCopyDAO.findAvailableItems();
    }
    
    public List<ItemCopy> findItemsOnLoan() {
        return itemCopyDAO.findItemsOnLoan();
    }
    
    public void deleteItemCopy(Integer id) {
        if (!itemCopyDAO.existsById(id)) {
            throw new IllegalArgumentException("Item copy not found with ID: " + id);
        }
        
        if (!itemCopyDAO.isAvailable(id)) {
            throw new IllegalStateException("Cannot delete item copy that is currently on loan");
        }
        
        itemCopyDAO.deleteById(id);
    }
    
    public boolean isAvailable(Integer itemCopyId) {
        if (!itemCopyDAO.existsById(itemCopyId)) {
            throw new IllegalArgumentException("Item copy not found with ID: " + itemCopyId);
        }
        return itemCopyDAO.isAvailable(itemCopyId);
    }
    
    public long getNumberOfCopies(Item item) {
        return itemCopyDAO.countByItem(item);
    }
    
    public long getNumberOfAvailableCopies(Item item) {
        return itemCopyDAO.countAvailableByItem(item);
    }

    public boolean validateNewBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false; // Barcode cannot be null or empty
        }
        if (itemCopyDAO.existsByBarcode(barcode)) {
            return false; // Barcode already exists
        }
        return true; // Valid new barcode
    }
}
