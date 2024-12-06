package cs1302.api;

/**
 * Represents a result from the Open Library API. Used by Gson to create
 * an object from JSON response body.
 */
public class LibraryResult {
    @SerializedName("first_sentence");
    String firstSentence;
    String[] subject;
    String title;
    @SerializedName("first_publish_year");
    String firstPublishYear;
} // LibraryResult
