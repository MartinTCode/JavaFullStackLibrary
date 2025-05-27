package com.javafullstacklibrary.model;

// Importing necessary packages for JPA annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable; // Importing JoinTable for many-to-many relationship


import java.util.ArrayList; // Importing ArrayList for one-to-many relationship
import java.util.List; // Importing List for one-to-many relationship
import java.util.Map;

import jakarta.persistence.CascadeType;
import java.util.Set; // Importing Set for many-to-many relationship

// Importing necessary packages for JPA inheritance and discriminator column
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;

@Entity
// Specifying inheritance strategy and discriminator column for polymorphic behavior
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Item {

    // #region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id") // Maps to the SQL column name
    private Integer id;

    /* Handled by the discriminator column instead
    
    @Column(name = "item_type", nullable = false, length = 50) // Matches SQL: VARCHAR(50) NOT NULL
    private String type;
    */

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

    // #endregion

    // #region One-to-Many Relationships Attributes

        @OneToMany(
        mappedBy = "item",
        // PERSIST: allows for saving copies when saving an item
        // MERGE: allows for updating copies when updating an item
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<ItemCopy> copies = new ArrayList<>();

    // #endregion

    // #region Many-to-one Relationships Attributes

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false) // Foreign key to location table
    private Location location;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false) // Foreign key to language table
    private Language language;

    // #endregion

    // #region Many-to-Many Relationships Attributes

    // m2m relatioship with Keyword
    // defining the join table "item_keyword" with join columns
    @ManyToMany
    @JoinTable(
        name = "item_keyword",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    // Many-to-many relationship with Keyword, "Set" is used to avoid duplicates
    private Set<Keyword> keywords;

    @ManyToMany
    @JoinTable(
        name = "item_creator",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    private Set<Creator> creators;

    @ManyToMany
    @JoinTable(
        name = "item_actor",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;

    @ManyToMany
    @JoinTable(
        name = "item_genre",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    // #endregion

    // #region Constructors

    // No-arg constructor required by JPA
    public Item() {
    }

    // Optional: constructor for convenience (without id)
    public Item(Location location, Language language,
                Set<Keyword> keywords, // m2m relationship (keyword shouldn't be duplicated in item)
                Set<Creator> creators, // m2m relationship (creator shouldn't be duplicated in item)
                Set<Actor> actors, // m2m relationship (actor shouldn't be duplicated in item)
                Set<Genre> genres, // m2m relationship (genre shouldn't be duplicated in item)
                //String type, handled by discriminator column
                String identifier,
                String identifier2, String title,
                String publisher, Short ageLimit,
                String countryOfProduction) {
        this.location = location;
        this.language = language;
        this.keywords = keywords;
        this.creators = creators;
        this.actors = actors;
        this.genres = genres;
        // this.type = type; handled by discriminator column
        this.identifier = identifier;
        this.identifier2 = identifier2;
        this.title = title;
        this.publisher = publisher;
        this.ageLimit = ageLimit;
        this.countryOfProduction = countryOfProduction;
    }

    public abstract Map<String, String> getParameterMap(); 
        // This method should be implemented by subclasses to return a map of parameters
        // specific to the item type, e.g., Book, Journal, DVD, etc.
        // The map should contain parameter names and their corresponding values.
        

    // #endregion

    // #region Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /* // Abstracted to discriminator column and iherited classes
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    */
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


    // #endregion

    // #region Getters and setters for one-to-many relationships (& elementary helpers)

    public List<ItemCopy> getCopies() {
    return copies;
    }

    public void setCopies(List<ItemCopy> copies) {
        this.copies = copies;
    }

    // Helper method to add a copy to the item
    public void addCopy(ItemCopy copy) {
        this.copies.add(copy);
        copy.setItem(this); // Set the item reference in the copy
    }

    // Helper method to remove a copy from the item (stays in item_copy!)
    public void removeCopy(ItemCopy copy) {
        this.copies.remove(copy);
        copy.setItem(null); // Clear the item reference in the copy
    }

    // #endregion

    // #region Getters and setters for many-to-many relationships

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Set<Creator> getCreators() {
        return creators;
    }

    public void setCreators(Set<Creator> creators) {
        this.creators = creators;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    // #endregion

    // #region Object overrides
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Item item = (Item) o;
        
        // First compare by ID if available (for database-persisted entities)
        if (id != null && item.id != null) {
            return id.equals(item.id);
        }
        
        // If ID is not available (not persisted yet), compare key fields
        if (identifier != null ? !identifier.equals(item.identifier) : item.identifier != null) return false;
        if (identifier2 != null ? !identifier2.equals(item.identifier2) : item.identifier2 != null) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false; 
        return publisher != null ? publisher.equals(item.publisher) : item.publisher == null;
    }
    
    @Override
    public int hashCode() {
        // Use ID if available for consistent hashing
        if (id != null) {
            return id.hashCode();
        }
        
        // Otherwise use combination of key fields
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (identifier2 != null ? identifier2.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }
    
    // #endregion
}