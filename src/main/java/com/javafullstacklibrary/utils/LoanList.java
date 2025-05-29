package com.javafullstacklibrary.utils;

import java.util.ArrayList;
import java.util.List;

import com.javafullstacklibrary.model.ItemCopy;
/**
 * Singleton class to manage the list of pending loans for a borrower.
 * This class provides methods to add, remove, and retrieve pending loans.
 */
public class LoanList {
    private static LoanList instance;
    private List<ItemCopy> pendingLoans;

    private LoanList() {
        this.pendingLoans = new ArrayList<>();
    }

    public static LoanList getInstance() {
        if (instance == null) {
            instance = new LoanList();
        }
        return instance;
    }

    public void addItemToLoan(ItemCopy itemCopy) {
        if (!pendingLoans.contains(itemCopy)) {
            pendingLoans.add(itemCopy);
        }
    }

    public void removeItemFromLoan(ItemCopy itemCopy) {
        pendingLoans.remove(itemCopy);
    }

    public List<ItemCopy> getPendingLoans() {
        return new ArrayList<>(pendingLoans);
    }

    public void clearPendingLoans() {
        pendingLoans.clear();
    }

    public int getLoanCount() {
        return pendingLoans.size();
    }
}
