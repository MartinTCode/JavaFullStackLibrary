package com.javafullstacklibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer id;
    
    @OneToOne (optional = false)
    @JoinColumn(name = "library_user_id", nullable = false, unique = true)
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
    private String profileType;
    
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
    }
}
