package com.javafullstacklibrary.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;


@Entity
@DiscriminatorValue("dvd")
public class DVD extends Item {
    
    // No-arg constructor required by JPA
    public DVD() {
        super();
        // Initialize collections to prevent NullPointerExceptions
        setKeywords(new HashSet<>());
        setDirectors(new HashSet<>());
        setGenres(new HashSet<>());
        setActors(new HashSet<>());
    }
    
    // Full constructor that maps to the parent constructor
    public DVD(Location location, 
                Language language,
                Set<Keyword> keywords,
                Set<Creator> directors,
                Set<Actor> actors,
                Set<Genre> genres,
                String imdbc,
                String title,
                String studio,
                Short ageLimit,
                String countryOfProduction

                ) {

        // Validate required m2m attributes is done before calling the parent constructor
        super(location, language, 
            // m2m attributes
            keywords, 
            directors, 
            actors, 
            genres, 
            // other attributes
            imdbc,
                // no id2 for dvd:s
                null, 
            title, studio, 
            ageLimit, countryOfProduction);
    }
    
    // DVD-specific getter/setter that provides domain-specific naming
    public String getIMDBC() {
        return getIdentifier();
    }
    
    public void setIMDBC(String imdbc) {
        setIdentifier(imdbc);
    }

    // DVD-specific getters and setters for inherited properties
    
    // Title related methods
    public String getTitle() {
        return super.getTitle();
    }
    
    public void setTitle(String title) {
        super.setTitle(title);
    }
    
    // Publisher related methods
    public String getStudio() {
        return super.getPublisher();
    }
    
    public void setStudio(String studio) {
        super.setPublisher(studio);
    }
    
    // Location related methods
    public Location getShelfLocation() {
        return super.getLocation();
    }
    
    public void setShelfLocation(Location location) {
        super.setLocation(location);
    }
    
    // Language related methods
    public Language getLanguage() {
        return super.getLanguage();
    }
    
    public void setLanguage(Language language) {
        super.setLanguage(language);
    }
    
    // Keywords related methods
    public Set<Keyword> getKeywords() {
        return super.getKeywords();
    }
    
    public void setKeywords(Set<Keyword> keywords) {
        super.setKeywords(keywords);
    }
    
    public void addKeyword(Keyword keyword) {
        super.getKeywords().add(keyword);
    }
    
    public void removeKeyword(Keyword keyword) {
        super.getKeywords().remove(keyword);
    }
    
    // Director related methods (replacing incorrect author methods)
    public Set<Creator> getDirectors() {
        return super.getCreators();
    }
    
    public void setDirectors(Set<Creator> directors) {
        super.setCreators(directors);
    }
    
    public void addDirector(Creator director) {
        super.getCreators().add(director);
    }
    
    public void removeDirector(Creator director) {
        super.getCreators().remove(director);
    }
    
    // Actor related methods (missing in the original)
    public Set<Actor> getActors() {
        return super.getActors();
    }
    
    public void setActors(Set<Actor> actors) {
        super.setActors(actors);
    }
    
    public void addActor(Actor actor) {
        super.getActors().add(actor);
    }
    
    public void removeActor(Actor actor) {
        super.getActors().remove(actor);
    }
    
    // Genre related methods
    public Set<Genre> getGenres() {
        return super.getGenres();
    }
    
    public void setGenres(Set<Genre> genres) {
        super.setGenres(genres);
    }
    
    public void addGenre(Genre genre) {
        super.getGenres().add(genre);
    }
    
    public void removeGenre(Genre genre) {
        super.getGenres().remove(genre);
    }
    
    // Age limit and country of production methods (missing in the original)
    public Short getAgeLimit() {
        return super.getAgeLimit();
    }
    
    public void setAgeLimit(Short ageLimit) {
        super.setAgeLimit(ageLimit);
    }
    
    public String getCountryOfProduction() {
        return super.getCountryOfProduction();
    }
    
    public void setCountryOfProduction(String countryOfProduction) {
        super.setCountryOfProduction(countryOfProduction);
    }
    
    // Override equals and hashCode to ensure correct behavior in Sets, Maps, and JPA operations.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DVD)) return false;
        if (!super.equals(o)) return false;

        DVD dvd = (DVD) o;

        // Compare specific fields for DVD
        return getIMDBC() != null ? getIMDBC().equals(dvd.getIMDBC()) : dvd.getIMDBC() == null;
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getIMDBC() != null ? getIMDBC().hashCode() : 0);
        return result;
    }

}