package cs1302.api;

import cs1302.api.LibraryResult;

/**
 * Represents a response from the Open Library search API. Used to create
 * an object from JSON response body.
 */
public class LibraryResponse {
    LibraryResult[] books;
} // LibraryResponse
