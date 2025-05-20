package com.javafullstacklibrary.model;

// Importing necessary packages for JPA annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import java.util.Set; // Importing Set for many-to-many relationship
import jakarta.persistence.JoinTable; // Importing JoinTable for many-to-many relationship

@Entity
public class Item {

    // #region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id") // Maps to the SQL column name
    private Integer id;

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

    // #endregion

    // #region One-to-Many Relationships Attributes

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
                String type, String identifier,
                String identifier2, String title,
                String publisher, Short ageLimit,
                String countryOfProduction) {
        this.location = location;
        this.language = language;
        this.keywords = keywords;
        this.creators = creators;
        this.actors = actors;
        this.genres = genres;
        this.type = type;
        this.identifier = identifier;
        this.identifier2 = identifier2;
        this.title = title;
        this.publisher = publisher;
        this.ageLimit = ageLimit;
        this.countryOfProduction = countryOfProduction;
    }

    // #endregion

    // #region Getters and setters
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
}