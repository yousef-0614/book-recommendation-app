package cs1302.api;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Class representing a container for information about a book.
 */
public class BookInfo extends VBox {
    Label title;
    Label author;
    Label year;
    Label firstSentence;

    /**
     * Constructor.
     */
    public BookInfo() {
        this.setSpacing(8);
        title = new Label("Title: ");
        author = new Label("Author: ");
        year = new Label("Year: ");
        firstSentence = new Label("First Sentence: ");
        this.getChildren().addAll(title, author, year, firstSentence);
    } // constructor

    /**
     * Used to change the text of the labels.
     *
     * @param title to change the title label.
     * @param author to change the author label.
     * @param year to change the year label.
     * @param firstSentence to change the firstSentence label.
     */
    public void setLabels(String title, String author, String year, String firstSentence) {
        this.title.setText("Title: " + title);
        this.author.setText("Author: " + author);
        this.year.setText("Year: " + year);
        this.firstSentence.setText("First Sentence: " + firstSentence);
    } // setLabels

} // BookInfo
