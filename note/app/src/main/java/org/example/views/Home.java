package org.example.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.components.NoteCard;
import org.example.components.TitleBar;
import org.example.models.Note;

public class Home {

    public static void homePage(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);

        VBox notesList = new VBox(10); 
        notesList.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(notesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        // Instantiate the custom TitleBar and pass in the add note logic
        TitleBar titleBar = new TitleBar(stage, "BitStream Notes", () -> {
            Note newNote = new Note("Untitled Note", "This is a short overview of the contents...");
            notesList.getChildren().add(0, new NoteCard(newNote)); 
        });

        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setCenter(scrollPane);

        root.setStyle(
            "-fx-background-color: #f3f3f3; " +
            "-fx-background-radius: 12; " + 
            "-fx-border-radius: 12; " +     
            "-fx-border-color: #cccccc; " +
            "-fx-border-width: 1;"
        );

        Scene scene = new Scene(root, 300, 640);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
}