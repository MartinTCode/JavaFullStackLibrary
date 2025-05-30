package com.javafullstacklibrary.frontend.librarianControllers;

import com.javafullstacklibrary.model.Creator;
import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.services.ItemCopyService;
import com.javafullstacklibrary.utils.MenuNavigationHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

// MenuNavigationHelper keyword: ManageCopies via buttonClickLibrarian(Pane mainPane, String buttonAction, Item item)

public class ManageCopiesLibrarianController {
    private final Item itemToModify;

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

    //checkbox for reference item copies
    @FXML
    private CheckBox checkBoxIsReference;

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
        
        // Load existing copies for the item
        loadItemCopies();
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
            setStatusLabel("Please enter a barcode.");
            return;
        }

        // Get the reference status from the checkbox
        boolean isReference = checkBoxIsReference.isSelected();
        
        try (ItemCopyService itemCopyService = new ItemCopyService()) {
            if (itemCopyService.validateNewBarcode(barcode)) {
                itemCopyService.createItemCopy(itemToModify, barcode, isReference);
                setStatusLabel("Item copy added successfully.");
                barcodeField.clear();

                // Reset the checkbox to unchecked after adding
                checkBoxIsReference.setSelected(false);
                
                // Refresh the item copies display
                loadItemCopies();
            } else {
                setStatusLabel("Invalid barcode. Please try again.");
            }
        } catch (Exception e) {
            setStatusLabel("Error adding item copy: " + e.getMessage());
        }
    }

    /**
     * Load existing item copies for the current item into the UI
     */
    private void loadItemCopies() {
        ItemCopyContainer.getChildren().clear();
        
        if (itemToModify == null) {
            return;
        }
        
        try (ItemCopyService itemCopyService = new ItemCopyService()) {
            List<ItemCopy> itemCopies = itemCopyService.findByItem(itemToModify);
            
            if (itemCopies.isEmpty()) {
                Label noItemsLabel = new Label("No copies found for this item.");
                noItemsLabel.getStyleClass().add("no-items-label");
                ItemCopyContainer.getChildren().add(noItemsLabel);
            } else {
                for (ItemCopy itemCopy : itemCopies) {
                    addItemCopyToContainer(itemCopy, itemCopyService);
                }
            }
        } catch (Exception e) {
            setStatusLabel("Error loading item copies: " + e.getMessage());
        }
    }

    /**
     * Adds an item copy to the container for display
     */
    private void addItemCopyToContainer(ItemCopy itemCopy, ItemCopyService itemCopyService) {
        HBox itemContainer = new HBox();
        itemContainer.getStyleClass().add("item-copy-container");
        itemContainer.setSpacing(10.0);
        
        // Create barcode label
        Label barcodeLabel = new Label("Barcode: " + itemCopy.getBarcode());
        barcodeLabel.getStyleClass().add("item-copy-barcode");
        
        // Create status label
        String status = itemCopyService.isAvailable(itemCopy.getId()) ? "Available" : "On Loan";
        Label statusItemLabel = new Label("Status: " + status);
        statusItemLabel.getStyleClass().add("item-copy-status");
        if (status.equals("Available")) {
            statusItemLabel.getStyleClass().add("status-available");
        } else {
            statusItemLabel.getStyleClass().add("status-on-loan");
        }
        
        // Create reference status label
        String referenceStatus = itemCopy.getIsReference() ? "Reference Copy" : "Loanable Copy";
        Label referenceLabel = new Label(referenceStatus);
        referenceLabel.getStyleClass().add("item-copy-reference");
        if (itemCopy.getIsReference()) {
            referenceLabel.getStyleClass().add("reference-item");
        } else {
            referenceLabel.getStyleClass().add("regular-item");
        }
        
        // Spacer to push delete button to the right
        Label spacer = new Label();
        spacer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        // Create delete button
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> {
            // Show confirmation dialog before deleting
            if (showDeleteConfirmationDialog(itemCopy.getBarcode())) {
                try (ItemCopyService deleteService = new ItemCopyService()) {
                    if (deleteService.isAvailable(itemCopy.getId())) {
                        deleteService.deleteItemCopy(itemCopy.getId());
                        ItemCopyContainer.getChildren().remove(itemContainer);
                        setStatusLabel("Item copy deleted successfully.");
                    } else {
                        setStatusLabel("Cannot delete item copy that is currently on loan.");
                    }
                } catch (Exception ex) {
                    setStatusLabel("Error deleting item copy: " + ex.getMessage());
                }
            }
        });
        
        // Add all elements to the container (including the new reference label)
        itemContainer.getChildren().addAll(barcodeLabel, statusItemLabel, referenceLabel, spacer, deleteButton);
        ItemCopyContainer.getChildren().add(itemContainer);
    }

    /**
     * Shows a confirmation dialog for deleting an item copy
     * @param barcode The barcode of the item copy to be deleted
     * @return true if user confirms deletion, false otherwise
     */
    private boolean showDeleteConfirmationDialog(String barcode) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Item Copy");
        alert.setContentText("Are you sure you want to delete the item copy with barcode: " + barcode + "?\n\n" +
                            "This action is irreversible and cannot be undone.");
        
        // Customize button text
        ButtonType confirmButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }

    private void setLabelsForItem(Item item) {
        // Set labels based on the provided item
        if (item != null) {
            itemTitleText.setText(item.getTitle());
            itemTypeText.setText("ItemType: " + item.getClass().getSimpleName());
            
            // Get first creator's full name if available
            String creatorName = "No creator";
            if (item.getCreators() != null && !item.getCreators().isEmpty()) {
                Creator firstCreator = item.getCreators().iterator().next();
                creatorName = firstCreator.getFullName();
            }
            
            itemCreatorText.setText("Creator: " + creatorName);
            itemIdText.setText("ItemId: " + String.valueOf(item.getId()));
        } else {
            itemTitleText.setText("No item selected");
            itemTypeText.setText("");
            itemCreatorText.setText("");
            itemIdText.setText("");
        }
    }

    private void setStatusLabel(String message) {
        // Display a status message in the status label
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }
}