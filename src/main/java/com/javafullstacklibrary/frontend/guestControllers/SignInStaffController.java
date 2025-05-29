package com.javafullstacklibrary.frontend.guestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.javafullstacklibrary.exception.ValidationException;
import com.javafullstacklibrary.services.AuthenticationService;
import com.javafullstacklibrary.services.AuthenticationService.AuthenticationResult;
import com.javafullstacklibrary.utils.MenuNavigationHelper;
import com.javafullstacklibrary.utils.UserSession;

import java.util.Map;

public class SignInStaffController {

    @FXML
    private Pane mainPane;

    // access input fields
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private AuthenticationService authService;

    public void initialize() {
        // Initialize the authentication service
        this.authService = new AuthenticationService();
        
        // Hardwire test data for development
        prefillTestData();
    }
    
    /**
     * Prefills the form with test data for development purposes
     */
    private void prefillTestData() {
        usernameField.setText("librarian1");
        passwordField.setText("hashed_password2");
    }

    @FXML
    private void clickedHomeMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "Home");
    }

    @FXML
    private void clickedSearchMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane, "Search");
    }

    @FXML
    private void clickedSignInButtonAdmin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (username == null || username.trim().isEmpty()) {
            showErrorAlert("Invalid Input", "Username cannot be empty");
            highlightField(usernameField);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            showErrorAlert("Invalid Input", "Password cannot be empty");
            highlightField(passwordField);
            return;
        }
        
        try {
            // Use the authentication service
            AuthenticationResult result = authService.authenticate(username, password);
            
            if (result.isSuccess()) {
                String userRole = result.getUserRole();
                
                // Check if user is actually an admin
                if (!"admin".equals(userRole)) {
                    showErrorAlert("Access Denied", "You do not have admin privileges");
                    clearFields();
                    return;
                }
                
                // Store user in session
                UserSession.setCurrentUser(result.getUser());
                
                System.out.println("Admin logged in successfully: " + result.getUser().getUsername() + 
                                 " (Role: " + userRole + ")");
                
                // Navigate to admin home (not implemented yet)
                showInfoAlert("Not Implemented", "Admin interface is not available in this version.");
                
            } else {
                showErrorAlert("Login Failed", result.getMessage());
                clearFields();
            }
            
        } catch (ValidationException e) {
            handleValidationException(e);
        } catch (Exception e) {
            System.err.println("Unexpected error during admin login: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("System Error", "An unexpected error occurred. Please try again later.");
            clearFields();
        }
    }

    @FXML
    private void clickedSignInButtonLibrarian() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (username == null || username.trim().isEmpty()) {
            showErrorAlert("Invalid Input", "Username cannot be empty");
            highlightField(usernameField);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            showErrorAlert("Invalid Input", "Password cannot be empty");
            highlightField(passwordField);
            return;
        }
        
        try {
            // Use the authentication service
            AuthenticationResult result = authService.authenticate(username, password);
            
            if (result.isSuccess()) {
                String userRole = result.getUserRole();
                
                // Check if user is actually a librarian
                if (!"librarian".equals(userRole)) {
                    showErrorAlert("Access Denied", "You do not have librarian privileges");
                    clearFields();
                    return;
                }
                
                // Store user in session
                UserSession.setCurrentUser(result.getUser());
                
                System.out.println("Librarian logged in successfully: " + result.getUser().getUsername() + 
                                 " (Role: " + userRole + ")");
                
                // Navigate to librarian home
                MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
                
            } else {
                showErrorAlert("Login Failed", result.getMessage());
                clearFields();
            }
            
        } catch (ValidationException e) {
            handleValidationException(e);
        } catch (Exception e) {
            System.err.println("Unexpected error during librarian login: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("System Error", "An unexpected error occurred. Please try again later.");
            clearFields();
        }
    }
    
    /**
     * Handles ValidationException by showing appropriate error messages
     */
    private void handleValidationException(ValidationException e) {
        if (e.hasFieldErrors()) {
            Map<String, String> fieldErrors = e.getFieldErrors();
            
            StringBuilder errorMessage = new StringBuilder();
            
            // Handle specific field errors
            if (fieldErrors.containsKey("loginInput")) {
                errorMessage.append("Username Error: ").append(fieldErrors.get("loginInput")).append("\n");
                highlightField(usernameField);
            }
            
            if (fieldErrors.containsKey("password")) {
                errorMessage.append("Password Error: ").append(fieldErrors.get("password")).append("\n");
                highlightField(passwordField);
            }
            
            if (fieldErrors.containsKey("credentials")) {
                errorMessage.append(fieldErrors.get("credentials")).append("\n");
                highlightField(usernameField);
                highlightField(passwordField);
            }
            
            if (fieldErrors.containsKey("system")) {
                errorMessage.append("System Error: ").append(fieldErrors.get("system")).append("\n");
            }
            
            showErrorAlert("Login Failed", errorMessage.toString().trim());
            
        } else {
            showErrorAlert("Validation Error", e.getMessage());
        }
        
        // Clear sensitive fields but keep username for user convenience
        passwordField.clear();
        // Refill test data for development convenience
        prefillTestData();
    }
    
    /**
     * Highlights a field to indicate an error
     */
    private void highlightField(TextField field) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        
        // Remove the highlight when user starts typing
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.setStyle("");
        });
    }

    @FXML
    private void clickedfForgotPasswordText() {
        showInfoAlert("Not Implemented", "Password recovery functionality is not available in this version.");
    }

    @FXML
    private void clickedUserButton() {
        MenuNavigationHelper.menuClickGuest(mainPane, "SignInUser");
    }
    
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        // Remove any error styling
        usernameField.setStyle("");
        passwordField.setStyle("");
    }
}
