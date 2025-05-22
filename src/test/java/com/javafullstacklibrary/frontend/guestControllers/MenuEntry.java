package com.javafullstacklibrary.frontend.guestControllers;

import java.util.List;

public class MenuEntry {
    private final String buttonId;
    private final String fieldId;
    private final boolean isTransition;
    private final List<MenuEntry> transitionSteps;

    // Constructor for MenuEntry
    public MenuEntry(String buttonId, String fieldId) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
        this.isTransition = false;
        this.transitionSteps = null;
    }

    // Overloaded constructor for MenuEntry with isTransition
    public MenuEntry(String buttonId, String fieldId, boolean isTransition) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
        this.isTransition = isTransition;
        this.transitionSteps = null;
    }

    // Overloaded constructor for MenuEntry with isTransition and transitionStep
    // This constructor allows you to specify if the entry is a transition step
    public MenuEntry(String buttonId, String fieldId, boolean isTransition, List<MenuEntry> transitionSteps) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
        this.isTransition = isTransition;
        this.transitionSteps = transitionSteps;
    }

    public boolean isTransition() {
        return isTransition;
    }

    public List<MenuEntry> getTransitionSteps() {
        return transitionSteps;
    }

    public String getButtonId() {
        return buttonId;
    }

    public String getFieldId() {
        return fieldId;
    }

     // Method to add a transition step
     public void addTransitionStep(MenuEntry menuEntry) throws IllegalStateException {
        if (menuEntry != null) {
            if (!isTransition) {
                throw new IllegalStateException("Cannot add transition steps to a non-transition entry.");
            }
            transitionSteps.add(menuEntry);
        }
    }
    
}
