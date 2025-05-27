package com.javafullstacklibrary.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Integer id;

    @Column(name = "f_name", nullable = false)
    private String firstName;

    @Column(name = "l_name", nullable = false)
    private String lastName;

    @Column(name = "dob")
    private LocalDate dob;

    @ManyToMany(mappedBy = "actors")
    private Set<Item> items;

    public Actor() {}

    public Actor(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    // Getters and setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Transient // Derived field, not stored in the database
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Set<Item> getItems() { return items; }
    public void setItems(Set<Item> items) { this.items = items; }

    // Override equals and hashCode to ensure correct behavior in Sets, Maps, and JPA operations.
    // This uses firstName, lastName, and dob as a composite business key, so that
    // different Actor instances with the same identifying fields are treated as equal.
    // This is important for collection operations and for comparing detached/persisted entities.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(firstName, actor.firstName) &&
            Objects.equals(lastName, actor.lastName) &&
            Objects.equals(dob, actor.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dob);
    }
}
