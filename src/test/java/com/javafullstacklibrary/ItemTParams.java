package com.javafullstacklibrary;

// Record class to hold item test parameters
// This class is used to define the parameters for testing item creation
// in the library system.
public record ItemTParams(
    String itemType,
    String identifier,
    String identifier2,
    String title,
    String titleUpdated,
    String publisher,
    short ageLimit,
    String countryOfProduction,
    String floor,
    String section,
    String shelf,
    String position,
    String language
) {}