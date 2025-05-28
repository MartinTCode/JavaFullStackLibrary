package com.javafullstacklibrary.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan")
public class Loan {

    // Empty constructor required by JPA
    public Loan() {
    }

    // Constructor with all fields except ID
    public Loan(ItemCopy itemCopy, LibraryUser libraryUser, LocalDate startDate, LocalDate returnDate) {
        this.itemCopy = itemCopy;
        this.libraryUser = libraryUser;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer id;
    
    // Many Loans can be associated with one ItemCopy and 
    // must have at least one LibraryUser
    @ManyToOne (optional = false)
    @JoinColumn(name = "item_copy_id", nullable = false)
    private ItemCopy itemCopy;
    
    // Many Loans can be associated with one LibraryUser and
    // must have at least one LibraryUser
    @ManyToOne (optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LibraryUser libraryUser;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    
    @Column(name = "returned_date")
    private LocalDate returnedDate;
    
    // Getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public ItemCopy getItemCopy() {
        return itemCopy;
    }
    
    public void setItemCopy(ItemCopy itemCopy) {
        this.itemCopy = itemCopy;
    }
    
    public LibraryUser getLibraryUser() {
        return libraryUser;
    }
    
    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public LocalDate getReturnedDate() {
        return returnedDate;
    }
    
    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
