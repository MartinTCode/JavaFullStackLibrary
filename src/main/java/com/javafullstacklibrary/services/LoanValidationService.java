package com.javafullstacklibrary.services;

import com.javafullstacklibrary.dao.ItemCopyDAO;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Journal;
import com.javafullstacklibrary.model.BorrowerProfile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Optional;

public class LoanValidationService {
    
    private final ItemCopyDAO itemCopyDAO;
    private final EntityManagerFactory emf;
    
    public LoanValidationService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();
        this.itemCopyDAO = new ItemCopyDAO(em);
    }

    // Check if user can loan another item copy based on the number of their current loans and 
    // the maximum allowed loans for their user type
    public boolean canLoanMore(BorrowerProfile borrowerProfile) {
        int maxLoans = borrowerProfile.getMaxLoansForRole();
        int currentLoans = borrowerProfile.getLibraryUser().getLoans().size();
        return currentLoans < maxLoans;
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
}
