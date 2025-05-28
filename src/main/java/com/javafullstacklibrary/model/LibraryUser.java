package com.javafullstacklibrary.model;

import java.util.List;
import java.util.Map;

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


@Entity
@Table(name = "library_user")
public class LibraryUser {

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
    @Check(constraints = "user_role IN ('public', 'student', 'researcher', 'university employee')")
    private String userRole;

    
    
    // One LibraryUser must have one UserProfile (should not have for admin/librarian)
    // Owning side of the relationship
    // If a LibraryUser is deleted, the UserProfile is also deleted
    @OneToOne(optional = true, cascade = CascadeType.ALL) 
    @JoinColumn(name = "profile_id")
    private BorrowerProfile userProfile;
        
    // One LibraryUser can have multiple loans
    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

    // No-arg constructor required by JPA
    public LibraryUser() {
    }
    // Constructor for convenience
    public LibraryUser(String ssn, String username, String passwordHash, String email, String userRole) {
        this.ssn = ssn;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.userRole = userRole;
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
    
    public BorrowerProfile getUserProfile() {
        return userProfile;
    }
    
    public void setUserProfile(BorrowerProfile userProfile) {
        this.userProfile = userProfile;
    }
}
