package com.javafullstacklibrary.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.HashSet;// Importing HashSet for Set implementation


@Entity
@DiscriminatorValue("journal")
public class Journal extends Item {
    
    // No-arg constructor required by JPA
    public Journal() {
        super();
        // Initialize collections to prevent NullPointerExceptions
        setKeywords(new HashSet<>());
        setAuthors(new HashSet<>());
        //setGenres(new HashSet<>());
        //setActors(new HashSet<>());
    }
    
    // Full constructor that maps to the parent constructor
    public Journal(Location location, 
                Language language,
                Set<Keyword> keywords,
                Set<Creator> authors,
                String issn, 
                String title,
                String publisher
                ) {

        // Validate required m2m attributes is done before calling the parent constructor
        super(location, language, 
            // m2m attributes (actor and genre not used in Journal)
            keywords, 
            authors, 
            new HashSet<>(), // Empty set for actors (not used for journal)
            new HashSet<>(), // Empty set for genres (not used for journals)
            // other attributes
            issn, 
            // no id2 for journal
            null, 
            title, publisher, 
            // no ageLimit nor country of production for journal.
            null, null);
    }
    
    // Book-specific getter/setter that provides domain-specific naming
    public String getISSN() {
        return getIdentifier();
    }
    
    public void setISSN(String issn) {
        setIdentifier(issn);
    }
    

    // Book-specific getters and setters for inherited properties
    
    // Title related methods
    public String getTitle() {
        return super.getTitle();
    }
    
    public void setTitle(String title) {
        super.setTitle(title);
    }
    
    // Publisher related methods
    public String getPublisher() {
        return super.getPublisher();
    }
    
    public void setPublisher(String publisher) {
        super.setPublisher(publisher);
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
    
    // Creator related methods (for authors of books)
    public Set<Creator> getAuthors() {
        return super.getCreators();
    }
    
    public void setAuthors(Set<Creator> authors) {
        super.setCreators(authors);
    }
    
    public void addAuthor(Creator author) {
        super.getCreators().add(author);
    }
    
    public void removeAuthor(Creator author) {
        super.getCreators().remove(author);
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
    
    // Override equals and hashCode to ensure correct behavior in Sets, Maps, and JPA operations.
    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Journal)) return false;
    if (!super.equals(o)) return false;
    Journal journal = (Journal) o;
    return getISSN() != null ? getISSN().equals(journal.getISSN()) : journal.getISSN() == null;
    }
    @Override
    public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (getISSN() != null ? getISSN().hashCode() : 0);
    return result;
    }

}