package org.example.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.models.Note;
import org.example.windows.NoteEditor;

public class NoteCard extends VBox {
    public NoteCard(Note note) {
        super(5); // 5px spacing between elements
        setPadding(new Insets(10, 15, 10, 15));

        Label title = new Label(note.getTitle());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333333;");

        Label preview = new Label(note.getContent());
        preview.setStyle("-fx-text-fill: #777777; -fx-font-size: 12px;");

        getChildren().addAll(title, preview);

        // Styling the card
        setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 5; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1); -fx-cursor: hand;"
        );

        // Hover effects and Click actions
        setOnMouseEntered(e -> setStyle("-fx-background-color: #fafafa; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 5, 0, 0, 2); -fx-cursor: hand;"));
        setOnMouseExited(e -> setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1); -fx-cursor: hand;"));
        
        setOnMouseClicked(e -> NoteEditor.display(note));
    }
}