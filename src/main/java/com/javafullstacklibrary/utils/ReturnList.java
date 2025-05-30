package com.javafullstacklibrary.utils;

import java.util.ArrayList;
import java.util.List;

import com.javafullstacklibrary.model.ItemCopy;

/**
 * Singleton class to manage the list of pending returns for a borrower.
 * This class provides methods to add, remove, and retrieve pending returns.
 */
public class ReturnList {
    private static ReturnList instance;
    private List<ItemCopy> pendingReturns;

    private ReturnList() {
        this.pendingReturns = new ArrayList<>();
    }

    public static ReturnList getInstance() {
        if (instance == null) {
            instance = new ReturnList();
        }
        return instance;
    }

    public void addItemToReturn(ItemCopy itemCopy) {
        if (!pendingReturns.contains(itemCopy)) {
            pendingReturns.add(itemCopy);
        }
    }

    public void removeItemFromReturn(ItemCopy itemCopy) {
        pendingReturns.remove(itemCopy);
    }

    public List<ItemCopy> getPendingReturns() {
        return new ArrayList<>(pendingReturns);
    }

    public void clearPendingReturns() {
        pendingReturns.clear();
    }

    public int getReturnCount() {
        return pendingReturns.size();
    }
}
