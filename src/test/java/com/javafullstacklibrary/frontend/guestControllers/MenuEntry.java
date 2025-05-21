package com.javafullstacklibrary.frontend.guestControllers;

public class MenuEntry {
    private final String buttonId;
    private final String fieldId;
    private final boolean transitionStep;

    // Constructor for MenuEntry
    public MenuEntry(String buttonId, String fieldId) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
        this.transitionStep = false;
    }

    // Overloaded constructor for MenuEntry with transitionStep
    // This constructor allows you to specify if the entry is a transition step
    public MenuEntry(String buttonId, String fieldId, boolean transitionStep) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
        this.transitionStep = transitionStep;
    }

    public boolean isTransitionStep() {
        return transitionStep;
    }

    public String getButtonId() {
        return buttonId;
    }

    public String getFieldId() {
        return fieldId;
    }
    
}
