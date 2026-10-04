package org.example.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.models.Note;
import org.example.windows.NoteEditor;

public class NoteCard extends VBox {
    public NoteCard(Note note) {
        super(5);
        setPadding(new Insets(10, 15, 10, 15));

        Label title = new Label();
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333333;");
        // Bind label directly to the Note title property
        title.textProperty().bind(note.titleProperty());

        Label preview = new Label();
        preview.setStyle("-fx-text-fill: #777777; -fx-font-size: 12px;");
        // Bind preview directly to the Note content property
        preview.textProperty().bind(note.contentProperty());

        getChildren().addAll(title, preview);

        setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1); -fx-cursor: hand;"
        );

        setOnMouseEntered(e -> setStyle("-fx-background-color: #769ff6; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 5, 0, 0, 2); -fx-cursor: hand;"));
        setOnMouseExited(e -> setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1); -fx-cursor: hand;"));

        setOnMouseClicked(e -> NoteEditor.display(note));
    }
}