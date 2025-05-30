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

public class SignInUserController {

    @FXML
    private Pane mainPane;

    // access input fields
    @FXML
    private TextField ssnField;

    @FXML
    private TextField passwordField; // PasswordField for security

    public void initialize() {
        // Hardwire test data for development
        prefillTestData();
    }
    
    /**
     * Prefills the form with test data for development purposes
     */
    private void prefillTestData() {
        ssnField.setText("19991212-1234");
        passwordField.setText("hashed_password3");
    }

    @FXML
    private void clickedHomeMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"Home");
    }

    @FXML
    private void clickedSearchMenuGuest() {
        MenuNavigationHelper.menuClickGuest(mainPane,"Search");
    }

    @FXML
    private void clickedSignInButton() {
        String loginInput = ssnField.getText();
        String password = passwordField.getText();

        // Validate SSN format first before attempting authentication
        if (!isValidSSNFormat(loginInput)) {
            showErrorAlert("Invalid SSN Format", 
                "SSN must be in the format 'yyyymmdd-xxxx' (e.g., 19991212-1234)");
            highlightField(ssnField);
            return;
        }
        
        // Use try-with-resources for AuthenticationService
        try (AuthenticationService authService = new AuthenticationService()) {
            AuthenticationResult result = authService.authenticate(loginInput, password);
            
            if (result.isSuccess()) {
                // Store user in session
                UserSession.setCurrentUser(result.getUser());
                
                // Login successful - navigate based on user role
                String userRole = result.getUserRole();
                String userName = result.getUser().getUsername() != null ? result.getUser().getUsername() : result.getUser().getSsn();

                System.out.println("User logged in successfully: " + userName + 
                                 " (Role: " + userRole + ")");
                
                navigateToUserHome(userRole);
                
            } else {
                // This shouldn't happen since we're using exceptions now, but keeping for safety
                showErrorAlert("Login Failed", result.getMessage());
                clearFields();
            }
            
        } catch (ValidationException e) {
            handleValidationException(e);
        } catch (Exception e) {
            // Handle any unexpected system errors
            System.err.println("Unexpected error during login: " + e.getMessage());
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
            
            // Create a comprehensive error message
            StringBuilder errorMessage = new StringBuilder();
            
            // Handle specific field errors
            if (fieldErrors.containsKey("loginInput")) {
                errorMessage.append("SSN Error: ").append(fieldErrors.get("loginInput")).append("\n");
                highlightField(ssnField);
            }
            
            if (fieldErrors.containsKey("password")) {
                errorMessage.append("Password Error: ").append(fieldErrors.get("password")).append("\n");
                highlightField(passwordField);
            }
            
            if (fieldErrors.containsKey("credentials")) {
                errorMessage.append(fieldErrors.get("credentials")).append("\n");
                highlightField(ssnField);
                highlightField(passwordField);
            }
            
            if (fieldErrors.containsKey("system")) {
                errorMessage.append("System Error: ").append(fieldErrors.get("system")).append("\n");
            }
            
            // Show the error alert
            showErrorAlert("Login Failed", errorMessage.toString().trim());
            
        } else {
            // General validation error
            showErrorAlert("Validation Error", e.getMessage());
        }
        
        // Clear sensitive fields but keep login input for user convenience
        passwordField.clear();
        // Refill test data for development convenience
        prefillTestData();
    }
    
    /**
     * Highlights a field to indicate an error (you can customize this styling)
     */
    private void highlightField(TextField field) {
        // Add error styling - you can customize this based on your CSS
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        
        // Remove the highlight after a few seconds or when user starts typing
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.setStyle(""); // Remove error styling when user starts typing
        });
    }
    
    private void navigateToUserHome(String userRole) {
        try {
            switch (userRole) {
                case "borrower":
                    MenuNavigationHelper.menuClickBorrower(mainPane, "Home");
                    break;
                case "librarian":
                    MenuNavigationHelper.menuClickLibrarian(mainPane, "Home");
                    break;
                /* Not implemented in current version due to time constraints
                case "admin":
                    MenuNavigationHelper.menuClickAdmin(mainPane, "Home");
                    break;
                */
                default:
                    showErrorAlert("Navigation Error", "Unknown user role: " + userRole);
            }
        } catch (Exception e) {
            System.err.println("Navigation error: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Navigation Error", "Failed to navigate to home page. Please try again.");
        }
    }

    @FXML
    private void clickedfForgotPasswordText() {
        showInfoAlert("Not Implemented", "Password recovery functionality is not available in this version.");
    }

    @FXML
    private void clickedStaffButton() {
        MenuNavigationHelper.menuClickGuest(mainPane,"SignInStaff");
    }

        /**
     * Validates SSN format: yyyymmdd-xxxx
     * @param ssn The SSN string to validate
     * @return true if format is valid, false otherwise
     */
    private boolean isValidSSNFormat(String ssn) {
        if (ssn == null || ssn.trim().isEmpty()) {
            return false;
        }
        
        // Check if it matches the pattern: 8 digits, hyphen, 4 digits
        if (!ssn.matches("\\d{8}-\\d{4}")) {
            return false;
        }
        
        // Additional validation: check if the date part is reasonable
        String datePart = ssn.substring(0, 8);
        try {
            int year = Integer.parseInt(datePart.substring(0, 4));
            int month = Integer.parseInt(datePart.substring(4, 6));
            int day = Integer.parseInt(datePart.substring(6, 8));
            
            // Basic date validation
            if (year < 1900 || year > 2100) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
        ssnField.clear();
        passwordField.clear();
        // Remove any error styling
        ssnField.setStyle("");
        passwordField.setStyle("");
    }
}