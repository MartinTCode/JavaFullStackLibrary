# System boundaries

### Functional boundaries
- Overdue loans are only shown in a list, no fine management or blocking of users.
- User accounts can only be created by the librian, for ID controll reasons. No independt user creation view.
- Librian users can only be created by superuser.
- Superusers can only be created by superusers.
- In order to help customers to make loans or reservations, the librarian will need to help the user via their logged in user view. Librarian does not have access to search or modify existing loans or reservations.
- No functionality for blocking users, this to keep the project scope within time bounds.
- Mail functionality is not implemented in this version of the program, although the theoretical final release version would have email integration.
- A copy of an item cannot be deleted directly via barcode as it is not a searchable value. This since the main search function should not be overloaded with too many searchable values. Librarian and Admin users thus need to search for an item, then find the barcode in list of barcodes in order to delete it, even if they have said book with barcode ready in hand. In future versions there would be a quick function for this such as scanning or entering barcode in order to delete a copy of an item directly. 