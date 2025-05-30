package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemCopyDAO;
import com.javafullstacklibrary.dao.LoanDAO;

import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.LibraryUser;
import com.javafullstacklibrary.model.Loan;
import com.javafullstacklibrary.utils.PendingTransactionManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ReturnValidationService {
    
    private final ItemCopyDAO itemCopyDAO;
    private final LoanDAO loanDAO;
    private final EntityManagerFactory emf;
    
    public ReturnValidationService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.itemCopyDAO = new ItemCopyDAO(em);
        this.loanDAO = new LoanDAO(em);
    }

    /**
     * Checks if a LibraryUser has any items to return.
     * 
     * @param libraryUser The LibraryUser to check
     * @return true if the user has items to return, false otherwise
     */
    public boolean hasItemsToReturn(LibraryUser libraryUser) {
        long activeLoans = loanDAO.countActiveLoansByUser(libraryUser);
        return activeLoans > 0;
    }

    /**
     * Overloaded method to check if a LibraryUser has items to return excluding
     * items that are already in the pending returns list.
     * 
     * @param libraryUser The LibraryUser to check
     * @param returnList The PendingTransactionManager containing pending returns
     * @return true if the user has more items to return, false otherwise
     */
    public boolean hasItemsToReturn(LibraryUser libraryUser, PendingTransactionManager returnList) {
        long activeLoans = loanDAO.countActiveLoansByUser(libraryUser);
        return (activeLoans - returnList.getPending().size()) > 0;
    }
    
    /**
     * Validates a barcode for return eligibility
     * @param barcode The barcode to validate
     * @param libraryUser The user attempting to return the item
     * @return ValidationResult containing either ItemCopy on success or error message on failure
     */
    public ValidationResult<ItemCopy> validateBarcodeForReturn(String barcode, LibraryUser libraryUser) {
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
        
        // Check if the item is currently on loan
        if (itemCopyDAO.isAvailable(itemCopy.getId())) {
            return ValidationResult.failure("Item is not currently on loan");
        }
        
        // Check if the item is loaned to this specific user
        Optional<Loan> activeLoan = loanDAO.findActiveLoanByItemCopy(itemCopy);
        if (activeLoan.isEmpty()) {
            return ValidationResult.failure("No active loan found for this item");
        }
        
        if (!activeLoan.get().getLibraryUser().getId().equals(libraryUser.getId())) {
            return ValidationResult.failure("This item is not loaned to you");
        }
        
        // If all validations pass, return the item copy
        return ValidationResult.success(itemCopy);
    }

    /**
     * Gets all items currently on loan to a specific user
     * @param libraryUser The user to get loaned items for
     * @return List of ItemCopy objects currently on loan to the user
     */
    public List<ItemCopy> getItemsOnLoanToUser(LibraryUser libraryUser) {
        // Use existing method: findOverdueItemCopies() exists, but we need active loans
        // We need to extract ItemCopy from the existing findActiveLoansByUser method
        return loanDAO.findActiveLoansByUser(libraryUser).stream()
                .map(Loan::getItemCopy)
                .toList();
    }

    /**
     * Checks if an item is overdue for a specific user
     * @param itemCopy The item to check
     * @param libraryUser The user who has the item
     * @return true if the item is overdue, false otherwise
     */
    public boolean isItemOverdue(ItemCopy itemCopy, LibraryUser libraryUser) {
        // Use existing findOverdueLoansByUser method and check if itemCopy is in the list
        List<Loan> overdueLoans = loanDAO.findOverdueLoansByUser(libraryUser);
        return overdueLoans.stream()
                .anyMatch(loan -> loan.getItemCopy().getId().equals(itemCopy.getId()));
    }

    /**
     * Processes the return of multiple borrowed items by updating their loan records.
     * This method processes each item copy in the list and marks them as returned.
     *
     * @param itemCopies List of ItemCopy objects being returned
     * @return List of successfully processed ItemCopy objects
     * @throws IllegalArgumentException if itemCopies list is null or empty
     */
    public List<ItemCopy> processReturns(List<ItemCopy> itemCopies) {
        if (itemCopies == null || itemCopies.isEmpty()) {
            throw new IllegalArgumentException("ItemCopies list cannot be null or empty");
        }

        List<ItemCopy> successfullyProcessed = new ArrayList<>();
        
        for (ItemCopy itemCopy : itemCopies) {
            try {
                // Use the existing processReturn method from LoanDAO
                loanDAO.processReturn(itemCopy);
                successfullyProcessed.add(itemCopy);
                System.out.println("Successfully processed return for: " + itemCopy.getBarcode());
            } catch (Exception e) {
                System.err.println("Failed to process return for " + itemCopy.getBarcode() + ": " + e.getMessage());
                // Continue processing other items even if one fails
            }
        }
        
        return successfullyProcessed;
    }

    /**
     * Processes the return of a single borrowed item by updating its loan record.
     * This method finds the active loan for the given item copy and marks it as returned.
     *
     * @param itemCopy the ItemCopy being returned
     * @throws IllegalArgumentException if itemCopy is null
     * @throws IllegalStateException if no active loan is found for the given ItemCopy
     */
    public void processReturn(ItemCopy itemCopy) {
        loanDAO.processReturn(itemCopy);
    }
}