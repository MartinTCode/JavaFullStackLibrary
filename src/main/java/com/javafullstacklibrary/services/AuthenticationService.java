package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.LibraryUserDAO;
import com.javafullstacklibrary.exception.ValidationException;
import com.javafullstacklibrary.model.LibraryUser;
import com.javafullstacklibrary.utils.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthenticationService {
    
    private final EntityManagerFactory emf;
    private final LibraryUserDAO userDAO;
    
    public AuthenticationService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.userDAO = new LibraryUserDAO(em);
    }
    
    /**
     * Authenticates a user and manages the user session
     * 
     * @param loginInput The SSN (for borrowers) or username (for staff)
     * @param password The raw password
     * @return AuthenticationResult containing success status and user info
     * @throws ValidationException if input validation fails
     */
    public AuthenticationResult authenticate(String loginInput, String password) throws ValidationException {
        // Input validation with field-specific errors
        Map<String, String> validationErrors = new HashMap<>();
        
        if (loginInput == null || loginInput.trim().isEmpty()) {
            validationErrors.put("loginInput", "SSN or username cannot be empty");
        }
        
        if (password == null || password.isEmpty()) {
            validationErrors.put("password", "Password cannot be empty");
        }
        
        // Additional validation rules
        if (loginInput != null && loginInput.trim().length() < 3) {
            validationErrors.put("loginInput", "Login input must be at least 3 characters long");
        }
        
        if (password != null && password.length() < 1) {
            validationErrors.put("password", "Password cannot be empty");
        }
        
        // If we have validation errors, throw ValidationException
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Login validation failed", validationErrors);
        }
        
        try {
            // Normalize the login input (handle SSN format conversion)
            String normalizedLoginInput = normalizeLoginInput(loginInput.trim());
            
            // Attempt authentication with normalized input
            Optional<LibraryUser> authenticatedUser = userDAO.authenticate(normalizedLoginInput, password);
            
            if (authenticatedUser.isPresent()) {
                LibraryUser user = authenticatedUser.get();
                
                // Set user session
                UserSession.setCurrentUser(user);
                
                return AuthenticationResult.success(user, "Login successful");
            } else {
                // Authentication failed - throw as validation error
                throw new ValidationException("credentials", "Invalid SSN/username or password. Please check your credentials and try again.");
            }
            
        } catch (ValidationException e) {
            // Re-throw validation exceptions
            throw e;
        } catch (Exception e) {
            System.err.println("Authentication system error: " + e.getMessage());
            e.printStackTrace();
            // Wrap system errors in a validation exception
            throw new ValidationException("system", "System error occurred during login. Please try again later.");
        }
    }
    
    /**
     * Normalizes login input to match database format
     * - Removes dash from SSN format (YYYYMMDD-XXXX -> YYYYMMDDXXXX)
     * - Leaves usernames unchanged
     * 
     * @param loginInput The raw login input
     * @return Normalized login input for database lookup
     */
    private String normalizeLoginInput(String loginInput) {
        if (loginInput == null) {
            return null;
        }
        
        // Check if it's an SSN format (YYYYMMDD-XXXX)
        if (loginInput.matches("\\d{8}-\\d{4}")) {
            // Remove the dash for database lookup
            String normalizedSSN = loginInput.replace("-", "");
            System.out.println("Normalized SSN: " + loginInput + " -> " + normalizedSSN);
            return normalizedSSN;
        }
        
        // For usernames or other formats, return as-is
        return loginInput;
    }

    /**
     * Initializes test data passwords by converting placeholder passwords to properly hashed ones.
     * This method should be called once during application setup to migrate test data
     * from placeholder passwords (like "hashed_password1") to properly BCrypt hashed passwords.
     * 
     * @return Number of users whose passwords were initialized
     * @throws ValidationException if initialization fails
     */
    public int initializeTestDataHash() throws ValidationException {
        try {
            int updatedCount = userDAO.initializeAllPasswords();
            System.out.println("Successfully initialized passwords for " + updatedCount + " users");
            return updatedCount;
        } catch (Exception e) {
            System.err.println("Error initializing test data passwords: " + e.getMessage());
            e.printStackTrace();
            throw new ValidationException("system", "Failed to initialize test data passwords: " + e.getMessage());
        }
    }
    
    /**
     * Checks how many users still have placeholder passwords that need initialization.
     * 
     * @return Number of users with placeholder passwords
     * @throws ValidationException if check fails
     */
    public long countUsersWithPlaceholderPasswords() throws ValidationException {
        try {
            return userDAO.countUsersWithPlaceholderPasswords();
        } catch (Exception e) {
            System.err.println("Error checking placeholder passwords: " + e.getMessage());
            e.printStackTrace();
            throw new ValidationException("system", "Failed to check placeholder passwords: " + e.getMessage());
        }
    }
    
    /**
     * Initializes test data and verifies the process completed successfully.
     * This is a convenience method that initializes passwords and provides feedback.
     * 
     * @return true if initialization was successful, false if no users needed initialization
     * @throws ValidationException if initialization fails
     */
    public boolean initializeAndVerifyTestData() throws ValidationException {
        try {
            // Check how many users need initialization
            long usersNeedingInit = countUsersWithPlaceholderPasswords();
            
            if (usersNeedingInit == 0) {
                System.out.println("No users need password initialization - all passwords are already properly hashed");
                return false;
            }
            
            System.out.println("Found " + usersNeedingInit + " users needing password initialization");
            
            // Initialize the passwords
            int updatedCount = initializeTestDataHash();
            
            // Verify all passwords are now properly hashed
            long remainingUsers = countUsersWithPlaceholderPasswords();
            
            if (remainingUsers == 0) {
                System.out.println("Password initialization completed successfully!");
                System.out.println("All " + updatedCount + " users now have properly hashed passwords");
                return true;
            } else {
                System.err.println("Warning: " + remainingUsers + " users still have placeholder passwords");
                return false;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error during test data initialization: " + e.getMessage());
            e.printStackTrace();
            throw new ValidationException("system", "Unexpected error during test data initialization: " + e.getMessage());
        }
    }

    
    /**
     * Validates login input format
     * 
     * @param loginInput The input to validate
     * @throws ValidationException if validation fails
     */
    public void validateLoginInput(String loginInput) throws ValidationException {
        Map<String, String> errors = new HashMap<>();
        
        if (loginInput == null || loginInput.trim().isEmpty()) {
            errors.put("loginInput", "Login field cannot be empty");
        } else {
            String trimmed = loginInput.trim();
            
            // Check if it could be an SSN (Swedish format: YYYYMMDD-XXXX)
            if (trimmed.matches("\\d{8}-\\d{4}")) {
                // Valid SSN format - will be normalized for database lookup
                return;
            }
            
            // Check if it could be a username (alphanumeric, 3-50 chars)
            if (trimmed.matches("^[a-zA-Z0-9_]{3,50}$")) {
                // Valid username format
                return;
            }
            
            // Check if it could be an SSN without dash (already normalized)
            if (trimmed.matches("\\d{12}")) {
                // Valid normalized SSN format
                return;
            }
            
            errors.put("loginInput", "Please enter a valid SSN (YYYYMMDD-XXXX) or username");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException("Input validation failed", errors);
        }
    }
    
    /**
     * Validates password input
     * 
     * @param password The password to validate
     * @throws ValidationException if validation fails
     */
    public void validatePassword(String password) throws ValidationException {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("password", "Password cannot be empty");
        }
        
        if (password.length() > 255) {
            throw new ValidationException("password", "Password cannot be longer than 255 characters");
        }
    }
    
    /**
     * Logs out the current user
     */
    public void logout() {
        UserSession.logout();
    }
    
    /**
     * Checks if a user is currently logged in
     */
    public boolean isUserLoggedIn() {
        return UserSession.isLoggedIn();
    }
    
    /**
     * Gets the current logged-in user
     */
    public LibraryUser getCurrentUser() {
        return UserSession.getCurrentUser();
    }
    
    /**
     * Initializes passwords for test data (should be called once during setup)
     */
    public int initializeTestPasswords() throws ValidationException {
        try {
            return userDAO.initializeAllPasswords();
        } catch (Exception e) {
            System.err.println("Error initializing passwords: " + e.getMessage());
            e.printStackTrace();
            throw new ValidationException("system", "Failed to initialize test passwords: " + e.getMessage());
        }
    }
    
    /**
     * Result class to encapsulate authentication results
     */
    public static class AuthenticationResult {
        private final boolean success;
        private final String message;
        private final LibraryUser user;
        
        private AuthenticationResult(boolean success, String message, LibraryUser user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
        
        public static AuthenticationResult success(LibraryUser user, String message) {
            return new AuthenticationResult(true, message, user);
        }
        
        public static AuthenticationResult failure(String message) {
            return new AuthenticationResult(false, message, null);
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public LibraryUser getUser() {
            return user;
        }
        
        public String getUserRole() {
            return user != null ? user.getUserRole() : null;
        }
    }
}