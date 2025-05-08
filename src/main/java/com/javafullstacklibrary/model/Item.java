package com.javafullstacklibrary.model;

// Importing necessary packages for JPA annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id") // Maps to the SQL column name
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false) // Foreign key to location table
    private Location location;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false) // Foreign key to language table
    private Language language;

    @Column(name = "item_type", nullable = false, length = 50) // Matches SQL: VARCHAR(50) NOT NULL
    private String type;

    @Column(length = 17) // Matches SQL: VARCHAR(17)
    private String identifier;

    @Column(length = 13) // Matches SQL: VARCHAR(13)
    private String identifier2;

    @Column(nullable = false, length = 255) // Matches SQL: VARCHAR(255) NOT NULL
    private String title;

    @Column(length = 100) // Matches SQL: VARCHAR(100)
    private String publisher;

    @Column(name = "age_limit") // Matches SQL: SMALLINT
    private Short ageLimit;

    @Column(name = "country_of_production", length = 100) // Matches SQL: VARCHAR(100)
    private String countryOfProduction;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier2() {
        return identifier2;
    }

    public void setIdentifier2(String identifier2) {
        this.identifier2 = identifier2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Short getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Short ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getCountryOfProduction() {
        return countryOfProduction;
    }

    public void setCountryOfProduction(String countryOfProduction) {
        this.countryOfProduction = countryOfProduction;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}