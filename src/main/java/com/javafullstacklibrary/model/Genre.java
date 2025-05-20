package com.javafullstacklibrary.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private Set<Item> items;

    public Genre() {}

    public Genre(String genre) {
        this.genre = genre;
    }

    // Getters and setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Set<Item> getItems() { return items; }
    public void setItems(Set<Item> items) { this.items = items; }

    // Override equals and hashCode to ensure correct behavior in Sets, Maps, and JPA operations.
    // This allows different Genre instances with the same 'genre' value to be treated as equal,
    // which is important for collection operations and for comparing detached/persisted entities.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return Objects.equals(genre, genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre);
    }
}
