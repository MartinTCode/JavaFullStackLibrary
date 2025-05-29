package com.javafullstacklibrary.model;

import java.util.Map;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "user_profile")
public class BorrowerProfile {

    @Transient
    private static final Map<String, Integer> userRole2MaxLoans = Map.of("public", 3, "student", 5, "researcher", 10, "university employee", 15);

    // contructor required by JPA
    public BorrowerProfile() {
    }

    // Constructor for convenience
    public BorrowerProfile(String firstName, String lastName, String phone, String address, String profileType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.profileType = profileType;
        this.user_loan_limit = userRole2MaxLoans.getOrDefault(profileType, 0);
        if (this.user_loan_limit == 0) {
            throw new IllegalArgumentException("Invalid profile type: " + profileType);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer id;
    
    // Non-owning side of the relationship
    // A UserProfile is linked to exactly one LibraryUser
    @OneToOne(mappedBy = "borrowerProfile") // All entries in borrowerProfile are linked to a LibraryUser
    private LibraryUser libraryUser;
    
    @Column(name = "f_name")
    private String firstName;
    
    @Column(name = "l_name")
    private String lastName;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "profile_type")
    @Check(constraints = "profile_type IN ('public', 'student', 'researcher', 'university employee')")
    private String profileType;

    @Transient
    private  int user_loan_limit;
    
    // Getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public LibraryUser getLibraryUser() {
        return libraryUser;
    }
    
    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getProfileType() {
        return profileType;
    }
    
    public void setProfileType(String profileType) {
        this.profileType = profileType;
        // Update the loan limit when profile type changes
        this.user_loan_limit = userRole2MaxLoans.getOrDefault(profileType, 0);
    }

    public int getMaxLoansForRole() {
        if (profileType == null) {
            throw new IllegalStateException("Profile type is not set. Cannot determine max loans.");
        } else {
            System.out.println("Profile type: " + profileType);
            System.out.println("Max loans for role: " + userRole2MaxLoans.getOrDefault(profileType, 0));
        }
        // Calculate dynamically if not set yet (for entities loaded from database)
        if (user_loan_limit == 0 && profileType != null) {
            user_loan_limit = userRole2MaxLoans.getOrDefault(profileType, 0);
        }
        return user_loan_limit;
    }
}
