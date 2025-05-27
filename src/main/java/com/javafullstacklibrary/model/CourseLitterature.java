package com.javafullstacklibrary.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;// Importing HashSet for Set implementation
import java.util.Map;


@Entity
@DiscriminatorValue("course_litterature")
public class CourseLitterature extends Item {

    private static final Map<String, String> PARAMETER_NAMES = new HashMap<String, String>();
    private static boolean PARAMETERIZED = false;
    
    // No-arg constructor required by JPA
    public CourseLitterature() {
        super();
        // Initialize collections to prevent NullPointerExceptions
        setKeywords(new HashSet<>());
        setAuthors(new HashSet<>());
        setGenres(new HashSet<>());
        //setActors(new HashSet<>());

        createParameterMap();
    }
    
    // Full constructor that maps to the parent constructor
    public CourseLitterature(Location location, 
                Language language,
                Set<Keyword> keywords,
                Set<Creator> creators,
                Set<Genre> genres,
                String isbn13,
                String isbn10, 
                String title,
                String publisher
                ) {

        // Validate required m2m attributes is done before calling the parent constructor
        super(location, language, 
            // m2m attributes (actor not used in Book)
            keywords, 
            creators, 
            new HashSet<>(), // Empty set for actors (not used for books)
            genres, 
            // other attributes
            isbn13, isbn10, title, publisher, 
            // no ageLimit nor country of production for book.
            null, null);

        createParameterMap();
    }

    private static final void createParameterMap(){
    if (PARAMETERIZED) {
            return; // Avoid re-creating the map if already done
        }
        PARAMETER_NAMES.put("type", "CourseLitterature");
        PARAMETER_NAMES.put("location", "location");
        PARAMETER_NAMES.put("language", "language");
        PARAMETER_NAMES.put("keywords", "keywords");
        PARAMETER_NAMES.put("creators", "authors");
        PARAMETER_NAMES.put("actors",null);
        PARAMETER_NAMES.put("genres", "genres");
        PARAMETER_NAMES.put("identifier1", "ISBN-13");
        PARAMETER_NAMES.put("identifier2", "ISBN-10");
        PARAMETER_NAMES.put("title", "title");
        PARAMETER_NAMES.put("publisher", "publisher");
        PARAMETER_NAMES.put("ageLimit", null);
        PARAMETER_NAMES.put("countryOfProduction", null);
        PARAMETERIZED = true; // Set to true to prevent re-creations
    }
    
    @Override
    public Map<String, String> getParameterMap() {
        return PARAMETER_NAMES;
    }
    
    // Book-specific getter/setter that provides domain-specific naming
    public String getISBN13() {
        return getIdentifier();
    }
    
    public void setISBN13(String isbn13) {
        setIdentifier(isbn13);
    }
    
    public String getISBN10() {
        return getIdentifier2();
    }
    
    public void setISBN10(String isbn10) {
        setIdentifier2(isbn10);
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
        if (!(o instanceof CourseLitterature)) return false; // Changed from Book to CourseLitterature
        if (!super.equals(o)) return false;
        CourseLitterature courseLit = (CourseLitterature) o; // Changed from Book to CourseLitterature
        return getISBN13().equals(courseLit.getISBN13()) &&
               getISBN10().equals(courseLit.getISBN10());
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getISBN13().hashCode();
        result = 31 * result + getISBN10().hashCode();
        return result;
    }

}