package com.javafullstacklibrary.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "library_user")
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_user_id")
    private Integer id;
    
    @Column(name = "ssn", length = 12, unique = true)
    private String ssn;
    
    @Column(name = "l_name", length = 100)
    private String lastName;
    
    @Column(name = "f_name", length = 100)
    private String firstName;
    
    @Column(name = "p_hashed_encrypt", length = 60, unique = true)
    private String passwordHash;
    
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
    @Column(name = "user_role", length = 20)
    private String userRole;
    
    @OneToMany(mappedBy = "libraryUser")
    private List<Loan> loans;
    
    @OneToOne(mappedBy = "libraryUser", cascade = CascadeType.ALL)
    private UserProfile userProfile;
    
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
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    
    public UserProfile getUserProfile() {
        return userProfile;
    }
    
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
