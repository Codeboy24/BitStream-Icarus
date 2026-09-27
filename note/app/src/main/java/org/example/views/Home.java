package org.example.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.components.NoteCard;
import org.example.models.Note;

public class Home {

    public static void homePage(Stage stage) {
        Button plusButton = new Button("+");
        plusButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        ToolBar toolBar = new ToolBar(plusButton);

        VBox notesList = new VBox(10); 
        notesList.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(notesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Use the new Note object and NoteCard component
        plusButton.setOnAction(event -> {
            Note newNote = new Note("Untitled Note", "This is a short overview of the contents...");
            notesList.getChildren().add(0, new NoteCard(newNote)); 
        });

        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("BitStream Notes");
        stage.setScene(scene);
        stage.show();
    }
}