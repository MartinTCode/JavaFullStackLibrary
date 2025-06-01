package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemCopyDAO;
import com.javafullstacklibrary.dao.LoanDAO;

import com.javafullstacklibrary.model.Item;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Journal;
import com.javafullstacklibrary.model.LibraryUser;
import com.javafullstacklibrary.utils.PendingTransactionManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanValidationService implements AutoCloseable {
    
    private final ItemCopyDAO itemCopyDAO;
    private final LoanDAO loanDAO;
    private final EntityManagerFactory emf;
    private final EntityManager em;
    
    public LoanValidationService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        this.em = emf.createEntityManager();
        this.itemCopyDAO = new ItemCopyDAO(em);
        this.loanDAO = new LoanDAO(em);
    }

    // Check if user can loan another item copy based on the number of their current loans and 
    // the maximum allowed loans for their user type
    /**
     * Checks if a LibraryUser can loan more items based on their current loans and max allowed loans.
     * 
     * @param libraryUser The LibraryUser to check
     * @return true if the user can loan more items, false otherwise
     */
    public boolean canLoanMore(LibraryUser libraryUser) {
        int maxLoans = loanDAO.getMaxLoansByUser(libraryUser);
        int currentLoans = Math.toIntExact(loanDAO.countActiveLoansByUser(libraryUser));
        return currentLoans < maxLoans;
    }

    /**
     * Overloaded method to check if a LibraryUser can loan more items based on their current loans
     * and the pending loans in the LoanList.
     * 
     * @param libraryUser The LibraryUser to check
     * @param loanList The LoanList containing pending loans
     * @return true if the user can loan more items, false otherwise
     */
    public boolean canLoanMore(LibraryUser libraryUser, PendingTransactionManager loanList) {
        int maxLoans = loanDAO.getMaxLoansByUser(libraryUser);
        int currentLoans = Math.toIntExact(loanDAO.countActiveLoansByUser(libraryUser));
        return (currentLoans + loanList.getPending().size()) <= maxLoans;
    }
    
    /**
     * Validates a barcode for loan eligibility
     * @param barcode The barcode to validate
     * @return ValidationResult containing either ItemCopy on success or error message on failure
     */
    public ValidationResult<ItemCopy> validateBarcodeForLoan(String barcode) {
        // Check for empty barcode
        if (barcode == null || barcode.trim().isEmpty()) {
            return ValidationResult.failure("Barcode cannot be empty");
        }
        
        // Find the item copy by barcode
        Optional<ItemCopy> itemCopyOpt = itemCopyDAO.findByBarcode(barcode.trim());
        
        // Check if barcode not found
        if (itemCopyOpt.isEmpty()) {
            return ValidationResult.failure("Item with barcode '" + barcode + "' not found");
        }
        
        ItemCopy itemCopy = itemCopyOpt.get();
        
        // Check if it's a reference copy
        if (itemCopy.getIsReference()) {
            return ValidationResult.failure("Reference items cannot be loaned");
        }
        
        // Check if it's a journal
        if (itemCopy.getItem() instanceof Journal) {
            return ValidationResult.failure("Journals cannot be loaned");
        }
        
        // Check if the item is already on loan
        if (!itemCopyDAO.isAvailable(itemCopy.getId())) {
            return ValidationResult.failure("Item is already on loan");
        }
        
        // If all validations pass, return the item copy
        return ValidationResult.success(itemCopy);
    }

    /**
     * Validates if the user can confirm all pending loans without exceeding their limit
     * @param libraryUser The user requesting the loans
     * @param pendingTransactionManager The pending transaction manager containing items to loan
     * @return ValidationResult indicating success or failure with appropriate message
     */
    public ValidationResult<Void> validateLoanConfirmation(LibraryUser libraryUser, PendingTransactionManager pendingTransactionManager) {
        if (pendingTransactionManager.getPending().isEmpty()) {
            return ValidationResult.failure("No items selected for loan");
        }
        
        int maxLoans = loanDAO.getMaxLoansByUser(libraryUser);
        int currentActiveLoans = Math.toIntExact(loanDAO.countActiveLoansByUser(libraryUser));
        int pendingCount = pendingTransactionManager.getPendingCount();
        
        if (maxLoans != -1 && (currentActiveLoans + pendingCount) > maxLoans) {
            return ValidationResult.failure(String.format(
                "Cannot confirm loans: would exceed maximum limit of %d loans (current: %d, pending: %d)", 
                maxLoans, currentActiveLoans, pendingCount));
        }
        
        return ValidationResult.success(null);
    }

    /**
     * Validates if a user can add one more item to their pending loans
     * @param libraryUser The user requesting to add an item
     * @param pendingTransactionManager The pending transaction manager
     * @return ValidationResult indicating success or failure
     */
    public ValidationResult<Void> validateAddToPending(LibraryUser libraryUser, PendingTransactionManager pendingTransactionManager) {
        int maxLoans = loanDAO.getMaxLoansByUser(libraryUser);
        int currentActiveLoans = Math.toIntExact(loanDAO.countActiveLoansByUser(libraryUser));
        int pendingCount = pendingTransactionManager.getPendingCount();
        
        if (maxLoans != -1 && (currentActiveLoans + pendingCount + 1) > maxLoans) {
            return ValidationResult.failure(String.format(
                "You have reached the maximum number of loans allowed for your user type (%d)", maxLoans));
        }
        
        return ValidationResult.success(null);
    }
    
    /**
     * Processes a list of pending loans by creating loan records in the database
     * @param itemCopies List of ItemCopy objects to loan
     * @param libraryUser The user taking out the loans
     * @return ValidationResult indicating success or failure with appropriate message
     */
    public ValidationResult<Void> processLoans(List<ItemCopy> itemCopies, LibraryUser libraryUser) {
        if (itemCopies == null || itemCopies.isEmpty()) {
            return ValidationResult.failure("No items to process for loan");
        }
        
        if (libraryUser == null) {
            return ValidationResult.failure("Library user is required for processing loans");
        }
        
        try {
            // Process each loan
            for (ItemCopy itemCopy : itemCopies) {
                LocalDate returnDate = getReturnDateByItemCopy(itemCopy);
                loanDAO.save(itemCopy, libraryUser, returnDate);
            }
            
            return ValidationResult.success(null);
        } catch (IllegalStateException e) {
            return ValidationResult.failure("Loan processing failed: " + e.getMessage());
        } catch (Exception e) {
            return ValidationResult.failure("An error occurred while processing loans");
        }
    }

    /**
     * Calculates the return date for an item copy based on its maximum loan time
     * @param itemCopy The ItemCopy to calculate return date for
     * @return LocalDate representing when the item should be returned
     */
    private LocalDate getReturnDateByItemCopy(ItemCopy itemCopy) {
        Item item = itemCopy.getItem();
        int daysToAdd = item.getMaxLoanTimeDays();
        return LocalDate.now().plusDays(daysToAdd);
    }
    
    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
