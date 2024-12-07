package cs1302.api;

import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.control.Alert;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import cs1302.api.BookInfo;
import cs1302.api.AnimeResponse;
import cs1302.api.LibraryResponse;
import cs1302.api.Anime;
import cs1302.api.LibraryResult;

import java.util.ArrayList;

/**
 * Gives book recommendations based on the anime input by the user.
 */
public class ApiApp extends Application {

    /** HTTP Client.*/
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

    /** Google {@code Gson} object for parsing JSON.*/
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public static final String OPEN_LIBRARY_API = "https://openlibrary.org/search.json";

    public static final String JIKAN_API = "https://api.jikan.moe/v4/anime";


    Stage stage;
    Scene scene;
    VBox root;
    HBox hbox;
    Button search;
    TextField query;
    BookInfo b1;
    BookInfo b2;
    BookInfo b3;
    BookInfo b4;
    BookInfo b5;
    Label instructions;

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox(22);
        hbox = new HBox(8);
        search = new Button("Find books");
        query = new TextField("anime name");
        b1 = new BookInfo();
        b2 = new BookInfo();
        b3 = new BookInfo();
        b4 = new BookInfo();
        b5 = new BookInfo();
        instructions = new Label("Type in an anime name, then press the button for book recs.");
    } // ApiApp


    /** {@inheritDoc} */
    @Override
    public void init() {
        search.setOnAction(ae -> runInNewThread(() -> this.findBooks()));
    } // init


    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        this.stage = stage;

        // demonstrate how to load local asset using "file:resources/"
        /*Image bannerImage = new Image("file:resources/readme-banner.png");
        ImageView banner = new ImageView(bannerImage);
        banner.setPreserveRatio(true);
        banner.setFitWidth(640);

        // some labels to display information
        Label notice = new Label("Modify the starter code to suit your needs.");

        // setup scene
        root.getChildren().addAll(banner, notice);*/
        HBox.setHgrow(query, Priority.ALWAYS);
        HBox.setHgrow(search, Priority.ALWAYS);
        hbox.getChildren().addAll(query, search);
        root.getChildren().addAll(hbox,instructions, b1, b2, b3, b4, b5);
        scene = new Scene(root, 1000, 675);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

    boolean callSuccess;

    /**
     * Method to find books based on user inputted anime.
     */
    public void findBooks() {
        Platform.runLater(() -> this.search.setDisable(true));
        this.jikanCall();
        if (callSuccess) {
            this.openLibraryCall();
        } else {
            this.displayError();
            Platform.runLater(() -> this.search.setDisable(false));
        } // if-else
        Platform.runLater(() -> this.search.setDisable(false));
        genre = new String[3];
    } // findBooks

    String[] genre = new String[3];

    /**
     * Jikan API call.
     */
    public void jikanCall() {
        try {
            String animeName = URLEncoder.encode(this.query.getText(), StandardCharsets.UTF_8);
            String query = String.format("?q=%s", animeName);
            String url = JIKAN_API + query;

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                Platform.runLater(() -> this.displayError());
                Platform.runLater(() -> this.search.setDisable(false));
                callSuccess = false;
                return;
            } // if

            String jsonResponse = response.body();

            AnimeResponse responseObject = GSON.<AnimeResponse>fromJson(jsonResponse,
                                                                        AnimeResponse.class);
            if (responseObject.data != null && !responseObject.data.isEmpty()) {
                Anime firstAnime = responseObject.data.get(0);
                if (firstAnime.genres != null) {
                    for (int i = 0; i < Math.min(firstAnime.genres.length, genre.length); i++) {
                        genre[i] = firstAnime.genres[i].name;
                    } // for
                } // if
            } // if
            if (genre[0] == null) {
                Platform.runLater(() -> this.displayError());
                Platform.runLater(() -> this.search.setDisable(false));
                callSuccess = false;
                return;
            } // if
        } catch (IOException | InterruptedException ie) {
            this.displayError();
            Platform.runLater(() -> this.search.setDisable(false));
        } // try
        callSuccess = true;
    } // jikanCall

    /**
     * Open Library API call.
     */
    public void openLibraryCall() {
        try {
            StringBuilder queryBuilder = new StringBuilder("?q=");
            for (int i = 0; i < genre.length; i++) {
                if (genre[i] != null) {
                    if (queryBuilder.length() > 3) {
                        queryBuilder.append("+OR+");
                    } // if
                    queryBuilder.append(URLEncoder.encode(genre[i], StandardCharsets.UTF_8));
                } // if
            } // for
            queryBuilder.append("&limit=5");
            String url = OPEN_LIBRARY_API + queryBuilder.toString();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                Platform.runLater(() -> this.displayError());
                Platform.runLater(() -> this.search.setDisable(false));
                callSuccess = false;
                return;
            } // if

            String jsonResponse = response.body();

            LibraryResponse responseObject = GSON.<LibraryResponse>fromJson(jsonResponse,
                                                                            LibraryResponse.class);

            ArrayList<BookInfo> bookFields = new ArrayList<>();
            bookFields.add(this.b1);
            bookFields.add(this.b2);
            bookFields.add(this.b3);
            bookFields.add(this.b4);
            bookFields.add(this.b5);

            for (int i = 0; i < Math.min(bookFields.size(), responseObject.docs.length); i++) {
                final int index = i;
                LibraryResult current = responseObject.docs[i];
                String firstSentence = "";
                if (current.firstSentence != null) {
                    firstSentence = current.firstSentence[0];
                } else {
                    firstSentence = "No first sentence available";
                } // if
                final String firstSentenceFinal = firstSentence;
                Platform.runLater(() -> bookFields.get(index).setLabels(current.title,
                                                                    current.authorName[0],
                                                                    current.firstPublishYear,
                                                                    firstSentenceFinal));
            } // for
        } catch (IOException | InterruptedException ie) {
            Platform.runLater(() -> this.displayError());
            Platform.runLater(() -> this.search.setDisable(false));
        } // try

    } // openLibraryCall

    /**
     * Displays an error message.
     */
    public void displayError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Anime: " + query.getText());
        alert.setContentText("Couldn't find results for this search.");
        alert.showAndWait();
    } // displayError

    /**
     * Runs a given task in a new thread.
     *
     * @param task the task the thread handles.
     */
    public void runInNewThread(Runnable task) {
        Thread t1 = new Thread(task);
        t1.setDaemon(true);
        t1.start();
    }

} // ApiApp
