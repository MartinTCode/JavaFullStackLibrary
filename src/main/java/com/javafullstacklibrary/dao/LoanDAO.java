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
    
    public Loan save(Loan loan) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Loan result;
            if (loan.getId() == null) {
                entityManager.persist(loan);
                result = loan;
            } else {
                result = entityManager.merge(loan);
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
}