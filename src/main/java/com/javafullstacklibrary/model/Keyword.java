package com.javafullstacklibrary.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Integer id;

    @Column(nullable = false)
    private String keyword;

    // m2m relationship where Item is the owner (see keywords field in Item class)
    @ManyToMany(mappedBy = "keywords")
    private Set<Item> items;

    // No-arg constructor required by JPA
    public Keyword() {
    }

    // Optional: constructor for convenience
    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    // Optional: full constructor (no id as it is auto-generated)
    public Keyword(String keyword, Set<Item> items) {
        this.keyword = keyword;
        this.items = items;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public Set<Item> getItems() { return items; }
    public void setItems(Set<Item> items) { this.items = items; }

    // Override equals and hashCode to ensure correct behavior in Sets, Maps, and JPA operations.
    // This allows different Keyword instances with the same 'keyword' value to be treated as equal,
    // which is important for collection operations and for comparing detached/persisted entities.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword1 = (Keyword) o;
        return Objects.equals(keyword, keyword1.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword);
    }
}