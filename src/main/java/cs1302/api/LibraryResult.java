package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a result from the Open Library API. Used by Gson to create
 * an object from JSON response body.
 */
public class LibraryResult {
    @SerializedName("first_sentence")
    String[] firstSentence;
    String title;
    @SerializedName("author_name")
    String authorName[];
    @SerializedName("first_publish_year")
    String firstPublishYear;
} // LibraryResult
