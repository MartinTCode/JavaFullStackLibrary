package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.LibraryUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryUserDAO {

    private EntityManager entityManager;

    public LibraryUserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LibraryUser save(LibraryUser user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            LibraryUser result;
            if (user.getId() == null) {
                entityManager.persist(user);
                result = user;
            } else {
                result = entityManager.merge(user);
            }
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Optional<LibraryUser> findById(Integer id) {
        LibraryUser user = entityManager.find(LibraryUser.class, id);
        return Optional.ofNullable(user);
    }

    public List<LibraryUser> findAll() {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT u FROM LibraryUser u", LibraryUser.class);
        return query.getResultList();
    }

    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            LibraryUser user = entityManager.find(LibraryUser.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(LibraryUser user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(user)) {
                entityManager.remove(user);
            } else {
                entityManager.remove(entityManager.merge(user));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public boolean existsById(Integer id) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM LibraryUser u WHERE u.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public Optional<LibraryUser> findByUsername(String username) {
        try {
            TypedQuery<LibraryUser> query = entityManager.createQuery(
                "SELECT u FROM LibraryUser u WHERE u.username = :username", LibraryUser.class);
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<LibraryUser> findByEmail(String email) {
        try {
            TypedQuery<LibraryUser> query = entityManager.createQuery(
                "SELECT u FROM LibraryUser u WHERE u.email = :email", LibraryUser.class);
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<LibraryUser> findBySsn(String ssn) {
        try {
            TypedQuery<LibraryUser> query = entityManager.createQuery(
                "SELECT u FROM LibraryUser u WHERE u.ssn = :ssn", LibraryUser.class);
            query.setParameter("ssn", ssn);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<LibraryUser> findByUserRole(String userRole) {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT u FROM LibraryUser u WHERE u.userRole = :userRole", LibraryUser.class);
        query.setParameter("userRole", userRole);
        return query.getResultList();
    }

    public List<LibraryUser> findBorrowers() {
        return findByUserRole("borrower");
    }

    public List<LibraryUser> findLibrarians() {
        return findByUserRole("librarian");
    }

    public List<LibraryUser> findAdmins() {
        return findByUserRole("admin");
    }

    public List<LibraryUser> findUsersWithProfile() {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT u FROM LibraryUser u WHERE u.borrowerProfile IS NOT NULL", LibraryUser.class);
        return query.getResultList();
    }

    public List<LibraryUser> findUsersWithoutProfile() {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT u FROM LibraryUser u WHERE u.borrowerProfile IS NULL", LibraryUser.class);
        return query.getResultList();
    }

    public List<LibraryUser> findUsersWithActiveLoans() {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT DISTINCT u FROM LibraryUser u JOIN u.loans l WHERE l.returnedDate IS NULL", 
            LibraryUser.class);
        return query.getResultList();
    }

    public List<LibraryUser> findUsersWithOverdueLoans() {
        TypedQuery<LibraryUser> query = entityManager.createQuery(
            "SELECT DISTINCT u FROM LibraryUser u JOIN u.loans l " +
            "WHERE l.returnedDate IS NULL AND l.returnDate < :currentDate", 
            LibraryUser.class);
        query.setParameter("currentDate", LocalDate.now());
        return query.getResultList();
    }

    public boolean existsByUsername(String username) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM LibraryUser u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM LibraryUser u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }

    public boolean existsBySsn(String ssn) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM LibraryUser u WHERE u.ssn = :ssn", Long.class);
        query.setParameter("ssn", ssn);
        return query.getSingleResult() > 0;
    }

    /**
     * Initializes password hashes for all users who have placeholder passwords.
     * Moves placeholder passwords from passwordHash to rawPassword field and 
     * properly hashes them using BCrypt.
     * 
     * This method should be called once to migrate test data from placeholder 
     * passwords to properly hashed passwords.
     * 
     * @return Number of users whose passwords were initialized
     */
    public int initializeAllPasswords() {
        EntityTransaction transaction = entityManager.getTransaction();
        int updatedCount = 0;
        
        try {
            transaction.begin();
            
            // Find all users who have placeholder passwords (not properly hashed)
            TypedQuery<LibraryUser> query = entityManager.createQuery(
                "SELECT u FROM LibraryUser u WHERE u.passwordHash LIKE 'hashed_password%'", 
                LibraryUser.class);
            List<LibraryUser> usersToUpdate = query.getResultList();
            
            for (LibraryUser user : usersToUpdate) {
                // Move the placeholder password to rawPassword field
                String placeholderPassword = user.getPasswordHash();
                user.setRawPassword(placeholderPassword);
                
                // Hash the placeholder password and store it properly
                String hashedPassword = user.hashPassword(placeholderPassword);
                user.setPasswordHash(hashedPassword);
                
                // Merge the updated user
                entityManager.merge(user);
                updatedCount++;
            }
            
            transaction.commit();
            return updatedCount;
            
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to initialize passwords: " + e.getMessage(), e);
        }
    }

    /**
     * Checks how many users still have placeholder passwords that need initialization.
     * 
     * @return Number of users with placeholder passwords
     */
    public long countUsersWithPlaceholderPasswords() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM LibraryUser u WHERE u.passwordHash LIKE 'hashed_password%'", 
            Long.class);
        return query.getSingleResult();
    }
    
    /**
     * Authenticates a user based on their role:
     * - For borrowers: uses SSN as username
     * - For admin/librarian: uses username field
     * 
     * @param loginUsername The username provided for login
     * @param rawPassword The raw password to verify
     * @return Optional containing the authenticated user, or empty if authentication fails
     */
    public Optional<LibraryUser> authenticate(String loginUsername, String rawPassword) {
        if (loginUsername == null || rawPassword == null) {
            return Optional.empty();
        }
        
        // First try to find user by SSN (for borrowers)
        Optional<LibraryUser> userBySsn = findBySsn(loginUsername);
        if (userBySsn.isPresent()) {
            LibraryUser user = userBySsn.get();
            if ("borrower".equals(user.getUserRole()) && user.verifyPassword(rawPassword)) {
                return Optional.of(user);
            }
        }
        
        // Then try to find user by username (for admin/librarian)
        Optional<LibraryUser> userByUsername = findByUsername(loginUsername);
        if (userByUsername.isPresent()) {
            LibraryUser user = userByUsername.get();
            if (("admin".equals(user.getUserRole()) || "librarian".equals(user.getUserRole())) 
                && user.verifyPassword(rawPassword)) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Alternative authenticate method that's more explicit about the logic
     * 
     * @param loginUsername The username provided for login
     * @param rawPassword The raw password to verify
     * @return Optional containing the authenticated user, or empty if authentication fails
     */
    public Optional<LibraryUser> authenticateAlternative(String loginUsername, String rawPassword) {
        if (loginUsername == null || rawPassword == null) {
            return Optional.empty();
        }
        
        // Try to authenticate as borrower (using SSN)
        Optional<LibraryUser> borrower = findBySsn(loginUsername);
        if (borrower.isPresent() && "borrower".equals(borrower.get().getUserRole())) {
            if (borrower.get().verifyPassword(rawPassword)) {
                return borrower;
            }
        }
        
        // Try to authenticate as admin/librarian (using username)
        Optional<LibraryUser> staff = findByUsername(loginUsername);
        if (staff.isPresent() && 
            ("admin".equals(staff.get().getUserRole()) || "librarian".equals(staff.get().getUserRole()))) {
            if (staff.get().verifyPassword(rawPassword)) {
                return staff;
            }
        }
        
        return Optional.empty();
    }
}