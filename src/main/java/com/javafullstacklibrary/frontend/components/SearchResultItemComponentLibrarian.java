package com.javafullstacklibrary.frontend.components;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.hibernate.Hibernate;

import java.util.Map;

/**
 * Component to display search result items in a standardized format.
 */
public class SearchResultItemComponentLibrarian extends VBox {
    
    private final Item item;
    
    /**
     * Creates a search result component for an item.
     * 
     * @param item The item to display
     */
    public SearchResultItemComponentLibrarian(Item item) {
        this.item = item;
        
        // Set up styling
        setPadding(new Insets(10));
        setSpacing(5);
        getStyleClass().add("search-result-item");
        setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        // Add hover effect
        setOnMouseEntered(e -> {
            setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f0f0f0;");
            setCursor(Cursor.HAND);
        });
        
        setOnMouseExited(e -> {
            setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        });
        
        // Add click handler that uses MenuNavigationHelper
        setOnMouseClicked(event -> {
            if (item.getParameterMap() == null || !item.getParameterMap().containsKey("type")) {
                // Default to ModifyBook if no type is specified
                MenuNavigationHelper.buttonClickLibrarian((Pane) getScene().getRoot(), "ModifyBook");
                return;
            }

            String itemType = item.getParameterMap().get("type");
            String viewAction = switch (itemType.toLowerCase()) {
                case "journal" -> "ModifyJournal";
                case "dvd" -> "ModifyDvd";
                case "course_litterature" -> "ModifyCourseLit";
                case "book" -> "ModifyBook";
                default -> "ModifyBook"; // fallback to book if un 56known type
            };

            MenuNavigationHelper.buttonClickLibrarian((Pane) getScene().getRoot(), viewAction);
        });


        // Title with bold font
        //titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        StringBuilder titleBuilder = new StringBuilder(item.getTitle());

        Map<String, String> paramMap = item.getParameterMap();
        if (paramMap != null && paramMap.containsKey("type")) {
        titleBuilder.append(" (").append(paramMap.get("type")).append(")");
        }

        // Create and style the title label
        Label titleLabel = new Label(titleBuilder.toString());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        // Type of item
        //Label typeLabel = new Label("Type: " + item.getType());

        // Add creator information if available
        Label creatorLabel = null;
        if (item.getCreators() != null && Hibernate.isInitialized(item.getCreators()) && 
            !item.getCreators().isEmpty()) {
            
            StringBuilder creatorBuilder = new StringBuilder("by ");
            boolean isFirst = true;
            
            for (var creator : item.getCreators()) {
                if (!isFirst) {
                    creatorBuilder.append(", ");
                }
                creatorBuilder.append(creator.getFullName());
                isFirst = false;
            }
            
            creatorLabel = new Label(creatorBuilder.toString());
            creatorLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            creatorLabel.setStyle("-fx-font-style: italic;");
        }
        
        // Add additional item details
        StringBuilder detailsBuilder = new StringBuilder();
        
        if (item.getPublisher() != null && !item.getPublisher().isEmpty()) {
            detailsBuilder.append("Publisher: ").append(item.getPublisher());
        }
        
        if (item.getIdentifier() != null && !item.getIdentifier().isEmpty()) {
            if (detailsBuilder.length() > 0) {
                detailsBuilder.append(" | ");
            }
            detailsBuilder.append("ID: ").append(item.getIdentifier());
        }
        
        if (item.getLanguage() != null) {
            if (detailsBuilder.length() > 0) {
                detailsBuilder.append(" | ");
            }
            detailsBuilder.append("Language: ").append(item.getLanguage().getLanguage());
        }
        
        Label detailsLabel = new Label(detailsBuilder.toString());
        
        // Location information if available
        String locationText = "Location: ";
        if (item.getLocation() != null) {
            locationText += "Floor " + item.getLocation().getFloor() + 
                          ", Section " + item.getLocation().getSection() + 
                          ", Shelf " + item.getLocation().getShelf();
        } else {
            locationText += "Not specified";
        }
        Label locationLabel = new Label(locationText);
        
        // Add information about availability - safely handling potential lazy initialization
        int totalCopies = 0;
        
        // Safely check if the collection is initialized and available
        if (item.getCopies() != null && Hibernate.isInitialized(item.getCopies())) {
            totalCopies = item.getCopies().size();
        }
        
        Label availabilityLabel = new Label("Copies available: " + totalCopies);
        // Add all components to the VBox in the correct order
        getChildren().add(titleLabel);
        if (creatorLabel != null) {
            getChildren().add(creatorLabel);
        }
        getChildren().addAll(//TypeLabel, 
        detailsLabel, locationLabel, availabilityLabel);
    }
    
    /**
     * @return The item represented by this component
     */
    public Item getItem() {
        return item;
    }
}