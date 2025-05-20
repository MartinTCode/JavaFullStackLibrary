package com.javafullstacklibrary.model;

import jakarta.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    @Column(nullable = false, length = 10)
    private String floor;

    @Column(nullable = false, length = 50)
    private String section;

    @Column(nullable = false, length = 50)
    private String shelf;

    @Column(nullable = false, length = 50)
    private String position;

    // No-arg constructor required by JPA
    public Location() {
    }

    // Optional: constructor for convenience (no id as it is auto-generated)
    public Location(String floor, String section, String shelf, String position) {
        this.floor = floor;
        this.section = section;
        this.shelf = shelf;
        this.position = position;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}