package com.javafullstacklibrary.utils;

import java.util.ArrayList;
import java.util.List;

import com.javafullstacklibrary.model.ItemCopy;
/**
 * Singleton class to manage the list of pending objects for transactions such as loans and returns.
 * This class provides methods to add, remove, and retrieve pending items.
 */
public class PendingTransactionManager {
    private static PendingTransactionManager instance;
    private List<ItemCopy> pendingList;

    private PendingTransactionManager() {
        this.pendingList = new ArrayList<>();
    }

    public static PendingTransactionManager getInstance() {
        if (instance == null) {
            instance = new PendingTransactionManager();
        }
        return instance;
    }

    public void addItemToPending(ItemCopy itemCopy) {
        if (!pendingList.contains(itemCopy)) {
            pendingList.add(itemCopy);
        }
    }

    public void removeItemFromPending(ItemCopy itemCopy) {
        pendingList.remove(itemCopy);
    }

    public List<ItemCopy> getPending() {
        return new ArrayList<>(pendingList);
    }

    public void clearPending() {
        pendingList.clear();
    }

    public int getPendingCount() {
        return pendingList.size();
    }
}
