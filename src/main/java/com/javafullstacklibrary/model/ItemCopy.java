package com.javafullstacklibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "item_copy")
public class ItemCopy {

    /**
     * Represents a copy of an item in the library system.
     * Each ItemCopy is associated with a specific Item and has a unique barcode.
     * It can be marked as a reference item, which means it cannot be checked out.
     */
    

    // #region Attributes
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_copy_id")
    private Integer id;
    
    @Column(name = "barcode", nullable = false, unique = true, length = 20)
    private String barcode;
    
    @Column(name = "is_reference", nullable = false)
    private boolean isReference;

    @Column(name = "date_added", nullable = false)
    private LocalDate dateAdded;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false) // Foreign key to item table
    private Item item;

    @OneToMany(mappedBy = "itemCopy")
    private List<Loan> loans;
    
    // #endregion
    
    // #region Constructors
    
    // No-arg constructor required by JPA
    public ItemCopy() {
    }
    
    // Constructor with all fields except ID
    public ItemCopy(String barcode, Item item, Boolean isReference, LocalDate dateAdded) {
        this.barcode = barcode;
        this.item = item;
        this.isReference = isReference;
        this.dateAdded = dateAdded;
    }
    
    // #endregion
    
    // #region Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Boolean getIsReference() {
        return isReference;
    }
    
    public void setIsReference(Boolean isReference) {
        this.isReference = isReference;
    }
    
    public LocalDate getDateAdded() {
        return dateAdded;
    }
    
    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    // #endregion
}