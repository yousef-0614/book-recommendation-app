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

/**
 * Gives book recommendations based on the anime input by the user.
 */
public class ApiApp extends Application {

    /** HTTP Client*/
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
    } // ApiApp

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
        root.getChildren().addAll(hbox, b1, b2, b3, b4, b5);
        scene = new Scene(root);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

} // ApiApp
