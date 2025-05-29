package com.javafullstacklibrary.utils;

import com.javafullstacklibrary.model.LibraryUser;

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
        return currentUser != null ? currentUser.getUsername() : null;
    }
}