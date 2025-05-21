package com.javafullstacklibrary.frontend.guestControllers;

public class MenuEntry {
    private final String buttonId;
    private final String fieldId;

    public MenuEntry(String buttonId, String fieldId) {
        this.buttonId = buttonId;
        this.fieldId = fieldId;
    }

    public String getButtonId() {
        return buttonId;
    }

    public String getFieldId() {
        return fieldId;
    }
    
}
