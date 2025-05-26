package com.javafullstacklibrary.model;

import java.time.LocalDate;

// Record class to hold item_copy test parameters
// This class is used to define the parameters for testing item_copy creation
// in the library system.
public record ItemCopyTestParams(
    String barcode, // Barcode of the item copy
    Item item, // The item associated with this copy
    Boolean isReference, // Indicates if the copy is a reference copy
    LocalDate dateAdded // Date when the copy was added to the system
) {}