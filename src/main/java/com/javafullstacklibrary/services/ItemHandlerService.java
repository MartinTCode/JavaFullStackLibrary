package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemDAO;
import com.javafullstacklibrary.model.*;

import java.util.List;
import java.util.Set;

public class ItemHandlerService {

    private final ItemDAO itemDAO;

    public ItemHandlerService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    /**
     * Adds a new item to the library. The itemType argument determines which subclass to create.
     * For "Book" and "CourseLitterature", actors should be empty.
     * For "Journal", genres and actors should be empty.
     * For "DVD", both creators and actors are used.
     */
    public Item addItem(
            String itemType,
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
        Item item = null;
        switch (itemType.toLowerCase()) {
            case "book":
                item = new Book(location, language, keywords, creators, genres, identifier1, identifier2, title, publisher);
                break;
            case "courselitterature":
                item = new CourseLitterature(location, language, keywords, creators, genres, identifier1, identifier2, title, publisher);
                break;
            case "journal":
                item = new Journal(location, language, keywords, creators, identifier1, title, publisher);
                break;
            case "dvd":
                item = new DVD(location, language, keywords, creators, actors, genres, identifier1, title, publisher, ageLimit, countryOfProduction);
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
        return itemDAO.save(item);
    }

    /**
     * Updates an existing item. Only non-null arguments will be updated.
     */
    public Item updateItem(
            Integer itemId,
            String title,
            String publisher,
            Location location,
            Language language,
            Set<Keyword> keywords,
            Set<Creator> creators,
            Set<Actor> actors,
            Set<Genre> genres,
            String identifier1,
            String identifier2,
            Short ageLimit,
            String countryOfProduction
    ) {
        Item item = itemDAO.findById(itemId);
        if (item == null) {
            return null;
        }
        if (title != null) item.setTitle(title);
        if (publisher != null) item.setPublisher(publisher);
        if (location != null) item.setLocation(location);
        if (language != null) item.setLanguage(language);
        if (keywords != null) item.setKeywords(keywords);
        if (creators != null) item.setCreators(creators);
        if (actors != null) item.setActors(actors);
        if (genres != null) item.setGenres(genres);
        if (identifier1 != null) item.setIdentifier(identifier1);
        if (identifier2 != null) item.setIdentifier2(identifier2);
        if (ageLimit != null) item.setAgeLimit(ageLimit);
        if (countryOfProduction != null) item.setCountryOfProduction(countryOfProduction);
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
     * Finds an item by its ID.
     * @param id The item ID.
     * @return The found item or null if not found.
     */
    public Item findItemById(Integer id) {
        return itemDAO.findById(id);
    }

    /**
     * Returns all items.
     * @return List of all items.
     */
    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }
}
