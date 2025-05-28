package com.javafullstacklibrary.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Loan {
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
    
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    
    @Column(name = "returned_date")
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
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
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public Date getReturnedDate() {
        return returnedDate;
    }
    
    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
}
