package com.javafullstacklibrary.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.Check;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "library_user")
public class LibraryUser {

    // No-arg constructor required by JPA
    public LibraryUser() {
    }
    
    // Constructor for convenience - automatically hashes raw password
    public LibraryUser(String ssn, String username, String rawPassword, String email, String userRole) {
        this.ssn = ssn;
        this.username = username;
        this.passwordHash = hashPassword(rawPassword); // Hash the raw password immediately
        this.email = email;
        this.userRole = userRole;
        this.rawPassword = rawPassword; // Store raw password for development convenience, but not persisted in the database
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    
    @Column(name = "ssn", length = 12, unique = true)
    private String ssn;
    
    @Column(name = "u_name", length = 50, unique = true)
    private String username;
    
    @Column(name = "p_hashed_bcrypt", length = 255, nullable = false)
    private String passwordHash;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "user_role", length = 20, nullable = false)
    @Check(constraints = "user_role IN ('admin', 'librarian', 'borrower')")
    private String userRole;

    @Transient
    // This field is not persisted in the database, only to be used during development or testing. Remove in production.
    private String rawPassword;
    
    // One LibraryUser must have one UserProfile (should not have for admin/librarian)
    // Owning side of the relationship
    // If a LibraryUser is deleted, the UserProfile is also deleted
    // orphanRemoval = true means that if the UserProfile is no longer referenced by any LibraryUser, it will be deleted
    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true) 
    @JoinColumn(name = "profile_id")
    private BorrowerProfile borrowerProfile;
        
    // One LibraryUser can have multiple loans
    @OneToMany(mappedBy = "libraryUser")
    private List<Loan> loans;

    // Password utility methods 

    public String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (rawPassword.length() > 255) {
            throw new IllegalArgumentException("Password cannot be longer than 255 characters");
        }
        // Use BCrypt to hash the password with a work factor of 12
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }
    
    public boolean verifyPassword(String rawPassword) {
        if (rawPassword == null || this.passwordHash == null) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), this.passwordHash);
        return result.verified;
    }

    
    
    // Getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getSsn() {
        return ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRawPassword() {
        return rawPassword;
    }
    
    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUserRole() {
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public List<Loan> getLoans() {
        return loans;
    }
    
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
    
    public BorrowerProfile getBorrowerProfile() {
        return borrowerProfile;
    }
    
    public void setBorrowerProfile(BorrowerProfile borrowerProfile) {
        this.borrowerProfile = borrowerProfile;
    }
}
