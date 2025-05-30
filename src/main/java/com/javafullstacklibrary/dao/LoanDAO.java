package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.Loan;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.LibraryUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoanDAO {
    
    private EntityManager entityManager;
    
    public LoanDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public Loan save(ItemCopy itemCopy, LibraryUser libraryUser, LocalDate returnDate) {
        if (libraryUser == null) {
        throw new IllegalArgumentException("LibraryUser is required for creating a loan");
        }
        if (itemCopy == null) {
            throw new IllegalArgumentException("ItemCopy is required for creating a loan");
        }
        
        // Check if item is already on loan
        if (hasActiveLoan(itemCopy)) {
            throw new IllegalStateException("ItemCopy is already on active loan");
        }
        
        // Check if user has reached their loan limit
        long currentActiveLoans = countActiveLoansByUser(libraryUser);
        int maxLoans = getMaxLoansByUser(libraryUser);
        if (maxLoans != -1 && currentActiveLoans >= maxLoans) {
            throw new IllegalStateException("User has reached maximum loan limit of " + maxLoans);
        }
        
        LocalDate startDate = LocalDate.now();
        
        Loan loan = new Loan(itemCopy, libraryUser, startDate, returnDate);
        
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(loan);
            transaction.commit();
            return loan;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Loan replaceLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        if (loan.getId() == null) {
            throw new IllegalArgumentException("Cannot update loan without ID. Use createLoan() for new loans.");
        }
        if (loan.getLibraryUser() == null) {
            throw new IllegalArgumentException("Loan must have a LibraryUser");
        }
        if (loan.getItemCopy() == null) {
            throw new IllegalArgumentException("Loan must have an ItemCopy");
        }
        
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Loan result = entityManager.merge(loan);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public Optional<Loan> findById(Integer id) {
        Loan loan = entityManager.find(Loan.class, id);
        return Optional.ofNullable(loan);
    }
    
    public List<Loan> findAll() {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l", Loan.class);
        return query.getResultList();
    }
    
    public List<Loan> findByLibraryUser(LibraryUser libraryUser) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.libraryUser = :libraryUser", Loan.class);
        query.setParameter("libraryUser", libraryUser);
        return query.getResultList();
    }
    
    public List<Loan> findByLibraryUserId(Integer libraryUserId) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.libraryUser.id = :libraryUserId", Loan.class);
        query.setParameter("libraryUserId", libraryUserId);
        return query.getResultList();
    }
    
    public List<Loan> findByItemCopy(ItemCopy itemCopy) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.itemCopy = :itemCopy", Loan.class);
        query.setParameter("itemCopy", itemCopy);
        return query.getResultList();
    }
    
    public List<Loan> findByItemCopyId(Integer itemCopyId) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.itemCopy.id = :itemCopyId", Loan.class);
        query.setParameter("itemCopyId", itemCopyId);
        return query.getResultList();
    }
    
    public List<Loan> findActiveLoans() {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnedDate IS NULL", Loan.class);
        return query.getResultList();
    }
    
    public List<Loan> findReturnedLoans() {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnedDate IS NOT NULL", Loan.class);
        return query.getResultList();
    }
    
    public List<Loan> findActiveLoansByUser(LibraryUser libraryUser) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.libraryUser = :libraryUser AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("libraryUser", libraryUser);
        return query.getResultList();
    }
    
    public List<Loan> findActiveLoansByUserId(Integer libraryUserId) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.libraryUser.id = :libraryUserId AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("libraryUserId", libraryUserId);
        return query.getResultList();
    }
    
    public Optional<Loan> findActiveLoanByItemCopy(ItemCopy itemCopy) {
        try {
            TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT l FROM Loan l WHERE l.itemCopy = :itemCopy AND l.returnedDate IS NULL", 
                Loan.class);
            query.setParameter("itemCopy", itemCopy);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<Loan> findOverdueLoans() {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnDate < :currentDate AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("currentDate", LocalDate.now());
        return query.getResultList();
    }
    
    public List<ItemCopy> findOverdueItemCopies() {
        TypedQuery<ItemCopy> query = entityManager.createQuery(
            "SELECT l.itemCopy FROM Loan l WHERE l.returnDate < :currentDate AND l.returnedDate IS NULL", 
            ItemCopy.class);
        query.setParameter("currentDate", LocalDate.now());
        return query.getResultList();
    }
    
    public List<Loan> findOverdueLoansByUser(LibraryUser libraryUser) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.libraryUser = :libraryUser " +
            "AND l.returnDate < :currentDate AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("libraryUser", libraryUser);
        query.setParameter("currentDate", LocalDate.now());
        return query.getResultList();
    }
    
    public List<Loan> findLoansDueToday() {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnDate = :today AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("today", LocalDate.now());
        return query.getResultList();
    }
    
    public List<Loan> findLoansDueBetween(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnDate BETWEEN :startDate AND :endDate " +
            "AND l.returnedDate IS NULL", 
            Loan.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    public List<Loan> findLoansStartedBetween(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.startDate BETWEEN :startDate AND :endDate", 
            Loan.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    public List<Loan> findLoansReturnedBetween(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Loan> query = entityManager.createQuery(
            "SELECT l FROM Loan l WHERE l.returnedDate BETWEEN :startDate AND :endDate", 
            Loan.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Loan loan = entityManager.find(Loan.class, id);
            if (loan != null) {
                entityManager.remove(loan);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public void delete(Loan loan) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(loan)) {
                entityManager.remove(loan);
            } else {
                entityManager.remove(entityManager.merge(loan));
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
            "SELECT COUNT(l) FROM Loan l WHERE l.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }
    
    public boolean hasActiveLoan(ItemCopy itemCopy) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.itemCopy = :itemCopy AND l.returnedDate IS NULL", 
            Long.class);
        query.setParameter("itemCopy", itemCopy);
        return query.getSingleResult() > 0;
    }
    
    public long countActiveLoans() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.returnedDate IS NULL", Long.class);
        return query.getSingleResult();
    }
    
    public long countActiveLoansByUser(LibraryUser libraryUser) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.libraryUser = :libraryUser AND l.returnedDate IS NULL", 
            Long.class);
        query.setParameter("libraryUser", libraryUser);
        return query.getSingleResult();
    }

    public int getMaxLoansByUser(LibraryUser libraryUser) {
        Map<String, Integer> userRole2MaxLoans = Map.of("public", 3, "student", 5, "researcher", 10, "university employee", 15);
        // get the profileType from the libraryUser's BorrowerProfile
        TypedQuery<String> query = entityManager.createQuery(
            "SELECT l.profileType FROM BorrowerProfile l WHERE l.libraryUser = :libraryUser", 
            String.class);
        query.setParameter("libraryUser", libraryUser);
        System.out.println("LibraryUser profileType: " + query.getSingleResult());
        // fetch max loans from final map with the profileType
        Integer maxLoans = userRole2MaxLoans.get(query.getSingleResult());
        return maxLoans != null ? maxLoans : -1; // Return -1 if no max loans found
    }
    
    public long countOverdueLoans() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.returnDate < :currentDate AND l.returnedDate IS NULL", 
            Long.class);
        query.setParameter("currentDate", LocalDate.now());
        return query.getSingleResult();
    }
    
    public long countOverdueLoansByUser(LibraryUser libraryUser) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(l) FROM Loan l WHERE l.libraryUser = :libraryUser " +
            "AND l.returnDate < :currentDate AND l.returnedDate IS NULL", 
            Long.class);
        query.setParameter("libraryUser", libraryUser);
        query.setParameter("currentDate", LocalDate.now());
        return query.getSingleResult();
    }
    
    /**
     * Processes the return of a borrowed item by updating its loan record.
     * This method finds the active loan for the given item copy and marks it as returned
     * with the current date.
     *
     * @param itemCopy the ItemCopy being returned
     * @throws IllegalArgumentException if itemCopy is null
     * @throws IllegalStateException if no active loan is found for the given ItemCopy
     * @throws RuntimeException if there's an error during the database transaction
     */
    public void processReturn(ItemCopy itemCopy) {
        if (itemCopy == null) {
            throw new IllegalArgumentException("ItemCopy is required for processing return");
        }

        // Find the active loan for this item copy
        Optional<Loan> activeLoan = findActiveLoanByItemCopy(itemCopy);
        
        if (activeLoan.isEmpty()) {
            throw new IllegalStateException("No active loan found for ItemCopy with barcode: " + itemCopy.getBarcode());
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            
            // Update the loan with return date
            Loan loan = activeLoan.get();
            loan.setReturnedDate(LocalDate.now());
            
            // Merge the updated loan
            entityManager.merge(loan);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}