package org.example.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.components.TitleBar;
import org.example.models.Note;

public class NoteEditor {
    
    public static void display(Note note) {
        Stage noteStage = new Stage();
        // 1. Remove standard square OS borders
        noteStage.initStyle(StageStyle.TRANSPARENT);

        // 2. Reuse the custom TitleBar (pass null for the runnable)
        TitleBar titleBar = new TitleBar(noteStage, note.getTitle(), null);

        TextArea textArea = new TextArea(note.getContent());
        textArea.setWrapText(true);
        // Make the text area background transparent so it doesn't overlap your rounded corners
        textArea.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-control-inner-background: transparent;");

        BorderPane layout = new BorderPane();
        layout.setTop(titleBar);
        layout.setCenter(textArea);
        layout.setPadding(new Insets(0, 10, 10, 10)); // Pad the text area away from the edges

        // 3. Apply the rounded corners to the main container
        layout.setStyle(
            "-fx-background-color: #f3f3f3; " +
            "-fx-background-radius: 12; " + 
            "-fx-border-radius: 12; " +     
            "-fx-border-color: #cccccc; " +
            "-fx-border-width: 1;"
        );

        // 4. Make the scene transparent
        Scene scene = new Scene(layout, 400, 300);
        scene.setFill(Color.TRANSPARENT);

        noteStage.setScene(scene);
        noteStage.show();
    }
}