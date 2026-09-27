package org.example.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.models.Note;

public class NoteEditor {
    
    public static void display(Note note) {
        Stage noteStage = new Stage();
        noteStage.setTitle(note.getTitle());

        TextArea textArea = new TextArea(note.getContent());
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-size: 14px;");

        BorderPane layout = new BorderPane();
        layout.setCenter(textArea);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 300);
        noteStage.setScene(scene);
        noteStage.show();
    }
}