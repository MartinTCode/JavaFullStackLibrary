package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.services.ItemCopyService;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// MenuNavigationHelper keyword: ManageCopies via buttonClickLibrarian(Pane mainPane, String buttonAction, Item item)

public class ManageCopiesLibrarianController  {
    private final Item itemToModify;

    // Service for managing item copies
    ItemCopyService itemCopyService = new ItemCopyService();

    // Main container
    @FXML
    private BorderPane mainPane;

    // Top menu navigation icons
    @FXML
    private ImageView homeMenuLibrarian;
    @FXML
    private ImageView searchMenuLibrarian;
    @FXML
    private ImageView overdueMenuLibrarian;
    @FXML
    private ImageView libraryMenuLibrarian;
    @FXML
    private ImageView usersMenuLibrarian;
    @FXML
    private ImageView accountMenuLibrarian;
    @FXML
    private ImageView signOutMenuLibrarian;

    // Content labels
    @FXML
    private Label subMenuTitle;
    @FXML
    private Label itemTitleText;
    @FXML
    private Label itemTypeText;
    @FXML
    private Label itemCreatorText;
    @FXML
    private Label itemIdText;

    // Search/Add functionality
    @FXML
    private TextField barcodeField;
    @FXML
    private Button addLoanButton;
    @FXML
    private Label statusLabel;

    // Results display
    @FXML
    private ScrollPane ItemCopyScrollPane;
    @FXML
    private VBox ItemCopyContainer;

    // Constructor for initializing the controller
    @FXML
    private void initialize(){
        // Reset the status label visibility and text
        statusLabel.setVisible(false);
        statusLabel.setText("");
        
        // Initialize the controller with the item to modify if provided
        setLabelsForItem(itemToModify);
    }

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

    // --- Top Menu Navigation Handlers ---

    @FXML
    private void clickedHomeMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenyLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Search");
    }

    @FXML
    private void clickedOverdueMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Overdue");
    }

    @FXML
    private void clickedManageUsersMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "ManageUsers");
    }

    @FXML
    private void clickedAccountMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "Account");
    }

    @FXML
    private void clickedSignOutMenuLibrarian(MouseEvent event) {
        MenuNavigationHelper.menuClickLibrarian(mainPane, "SignOut");
    }

    @FXML
    private void clickedAddCopyButton() {
        String barcode = barcodeField.getText().trim();
        if (barcode.isEmpty()) {
            setStatusLable("Please enter a barcode.");
            return;
        }
        // 
        if (itemCopyService.validateNewBarcode(barcode)) {
            try {
               itemCopyService.createItemCopy(itemToModify, barcode);
                setStatusLable("Item copy added successfully.");
                barcodeField.clear();

            } catch (Exception e) {
                setStatusLable("Error adding item copy: " + e.getMessage());
            }
        } else {
            setStatusLable("Invalid barcode. Please try again.");
        }
    }

    private void setLabelsForItem(Item item) {
        // Set labels based on the provided item
        if (item != null) {
            itemTitleText.setText(item.getTitle());
            itemTypeText.setText(item.getClass().getSimpleName());
            
            // Get first creator's full name if available
            String creatorName = "No creator";
            if (item.getCreators() != null && !item.getCreators().isEmpty()) {
                Creator firstCreator = item.getCreators().iterator().next();
                creatorName = firstCreator.getFullName();
            }
            
            itemCreatorText.setText(creatorName);
            itemIdText.setText("ItemId: " + String.valueOf(item.getId()));
        } else {
            itemTitleText.setText("No item selected");
            itemTypeText.setText("");
            itemCreatorText.setText("");
            itemIdText.setText("");
        }
    }

    private void setStatusLable(String message) {
        // Display a status message in the status label
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }
}