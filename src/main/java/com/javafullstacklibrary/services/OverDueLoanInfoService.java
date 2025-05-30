package com.javafullstacklibrary.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import com.javafullstacklibrary.dao.LoanDAO;
import com.javafullstacklibrary.model.ItemCopy;
import com.javafullstacklibrary.model.Loan;
import com.javafullstacklibrary.model.LibraryUser;
import com.javafullstacklibrary.model.BorrowerProfile;
import com.javafullstacklibrary.model.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class OverDueLoanInfoService implements AutoCloseable {

    private final LoanDAO loanDAO;
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public OverDueLoanInfoService() {
        this.emf = Persistence.createEntityManagerFactory("libraryPU");
        this.em = emf.createEntityManager();
        this.loanDAO = new LoanDAO(em);
    }

    /**
     * Retrieves a list of overdue loans.
     * @return List of overdue loans
     */
    private List<Loan> getOverDueLoans() {
        return loanDAO.findOverdueLoans();
    }

    /**
     * Returns overdue loan information as a list of maps for easy consumption.
     * @return List of maps containing overdue loan details
     */
    public List<Map<String, String>> getOverDueLoansDict() {
        return getOverDueLoans().stream()
            .map((Loan loan) -> {  // Add explicit type for lambda parameter
                ItemCopy itemCopy = loan.getItemCopy();
                Item item = itemCopy.getItem(); 
                LibraryUser loanOwner = loan.getLibraryUser();
                BorrowerProfile borrowerProfile = loanOwner.getBorrowerProfile();

                return Map.of(
                    "title", item != null ? item.getTitle() : "N/A",
                    "itemType", item != null ? item.getClass().getSimpleName() : "N/A",
                    "barcode", itemCopy != null ? itemCopy.getBarcode() : "N/A",
                    "ssn", loanOwner != null ? loanOwner.getSsn() : "N/A",
                    "email", loanOwner != null ? loanOwner.getEmail() : "N/A",
                    "firstName", borrowerProfile != null ? borrowerProfile.getFirstName() : "N/A",
                    "lastName", borrowerProfile != null ? borrowerProfile.getLastName() : "N/A",
                    "dateBorrowed", loan.getStartDate().toString(),
                    "dueDate", loan.getReturnDate().toString(),
                    "daysOverdue", loan.getReturnedDate() != null ? "-1" : 
                        String.valueOf(Math.max(0, ChronoUnit.DAYS.between(loan.getReturnDate(), LocalDate.now())))
                );
            })
            .toList();
    }

// ...existing code...

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