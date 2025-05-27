package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemDAO;
import com.javafullstacklibrary.model.Item;

public class ItemModifyerService {

    private final ItemDAO itemDAO;

    public ItemModifyerService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    /**
     * Creates and adds a new item.
     * @param item The item to add.
     * @return The saved item with generated ID.
     */
    public Item Item(Item item) {
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
}
