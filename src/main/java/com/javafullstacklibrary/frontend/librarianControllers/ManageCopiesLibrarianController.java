package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.model.Item;

// MenuNavigationHelper keyword: ManageCopies via buttonClickLibrarian(Pane mainPane, String buttonAction, Item item)

public class ManageCopiesLibrarianController  {
    private final Item itemToModify;

    public ManageCopiesLibrarianController() {
        this.itemToModify = null; // No specific item to manage
        // Default constructor logic here
        // Initialize the controller without any specific item
    }
    
    public ManageCopiesLibrarianController(Item item) {
        this.itemToModify = item; // Specific item to manage copies
        // Constructor logic here
        // Initialize the controller with the item to manage copies
    }
}
