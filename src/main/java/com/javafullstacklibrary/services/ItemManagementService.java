package com.javafullstacklibrary.services;

import java.util.Set;

import com.javafullstacklibrary.dao.ItemDAO;
import com.javafullstacklibrary.model.Actor;
import com.javafullstacklibrary.model.Book;
import com.javafullstacklibrary.model.CourseLitterature;
import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.DVD;
import com.javafullstacklibrary.model.Genre;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.Journal;
import com.javafullstacklibrary.model.Keyword;
import com.javafullstacklibrary.model.Language;
import com.javafullstacklibrary.model.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ItemManagementService {

    private final EntityManagerFactory emf;
    private final ItemDAO itemDAO;
    

    /**
     * Constructor that initializes the EntityManagerFactory and GenreDAO.
     */
    public ItemManagementService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.itemDAO = new ItemDAO(em);
    }

    /**
     * Creates and adds a new item.
     * @param item The item to add.
     * @return The saved item with generated ID.
     */
    public Item addItem(Item item) {
        return itemDAO.save(item);
    }

    /**
     * Modifies an existing item.
     * @param item The item with updated fields.
     * @return The updated item.
     */
    public Item updateItem(Item item) {
        return itemDAO.save(item);
    }

    /**
     * Deletes an item by its ID.
     * @param id The ID of the item to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteItemById(Integer id) {
        return itemDAO.deleteById(id);
    }

    /**
     * Deletes an item by entity.
     * @param item The item to delete.
     */
    public void deleteItem(Item item) {
        itemDAO.delete(item);
    }

    /**
     * Creates and adds a new item of the correct type.
     * @param type The type of item ("book", "journal", "dvd", "course_litterature")
     * @param location The location
     * @param language The language
     * @param keywords Set of keywords
     * @param creators Set of creators (authors or directors)
     * @param actors Set of actors (for DVD)
     * @param genres Set of genres (for Book, DVD, CourseLitterature)
     * @param identifier1 ISBN-13, ISSN, IMDBC, etc.
     * @param identifier2 ISBN-10 (for Book/CourseLitterature), null otherwise
     * @param title The title
     * @param publisher Publisher or studio
     * @param ageLimit Age limit (for DVD)
     * @param countryOfProduction Country of production (for DVD)
     * @return The saved item
     */
    public Item createItem(
            String type,
            Location location,
            Language language,
            Set<Keyword> keywords,
            Set<Creator> creators,
            Set<Actor> actors,
            Set<Genre> genres,
            String identifier1,
            String identifier2,
            String title,
            String publisher,
            Short ageLimit,
            String countryOfProduction
    ) {
        Item item;
        switch (type.toLowerCase()) {
            case "book":
                item = new Book(location, language, keywords, creators, genres, identifier1, identifier2, title, publisher);
                break;
            case "journal":
                item = new Journal(location, language, keywords, creators, identifier1, title, publisher);
                break;
            case "dvd":
                item = new DVD(location, language, keywords, creators, actors, genres, identifier1, title, publisher, ageLimit, countryOfProduction);
                break;
            case "course_litterature":
                item = new CourseLitterature(location, language, keywords, creators, genres, identifier1, identifier2, title, publisher);
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }
        itemDAO.save(item);

        return item;
    }
 
}
