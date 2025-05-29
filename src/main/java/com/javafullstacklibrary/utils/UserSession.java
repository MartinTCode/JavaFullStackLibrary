package com.javafullstacklibrary.utils;

import com.javafullstacklibrary.model.LibraryUser;

/**
 * UserSession  signleton class to manage the current user session in the library system.
 * This class provides methods to set, get, and check the current user,
 * as well as to log out the user and retrieve user details.
 */
public class UserSession {
    private static LibraryUser currentUser;
    
    public static void setCurrentUser(LibraryUser user) {
        currentUser = user;
    }
    
    public static LibraryUser getCurrentUser() {
        return currentUser;
    }
    
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public static void logout() {
        currentUser = null;
    }
    
    public static String getCurrentUserRole() {
        return currentUser != null ? currentUser.getUserRole() : null;
    }
    
    public static Integer getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }
    
    public static String getCurrentUsername() {
        return currentUser.getUsername() != null ? currentUser.getUsername() : currentUser.getSsn();
    }
}