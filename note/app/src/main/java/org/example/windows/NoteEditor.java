package org.example.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.components.TitleBar;
import org.example.models.Note;

public class NoteEditor {

    public static void display(Note note) {
        Stage noteStage = new Stage();
        noteStage.initStyle(StageStyle.TRANSPARENT);

        TitleBar titleBar = new TitleBar(noteStage, note.getTitle(), null);

        // Title input field
        TextField titleField = new TextField(note.getTitle());
        titleField.setStyle(
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-background-color: transparent; " +
            "-fx-prompt-text-fill: #a0a0a0;"
        );
        titleField.setPromptText("Note Title...");
        note.titleProperty().bindBidirectional(titleField.textProperty());

        // Content text area
        TextArea textArea = new TextArea(note.getContent());
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-control-inner-background: transparent;");
        note.contentProperty().bindBidirectional(textArea.textProperty());

        VBox editorBody = new VBox(5, titleField, textArea);
        editorBody.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(titleBar);
        layout.setCenter(editorBody);

        layout.setStyle(
            "-fx-background-color: #f3f3f3; " +
            "-fx-background-radius: 12; " +
            "-fx-border-radius: 12; " +
            "-fx-border-color: #cccccc; " +
            "-fx-border-width: 1;"
        );

        Scene scene = new Scene(layout, 400, 300);
        scene.setFill(Color.TRANSPARENT);

        // Unbind bidirectional bindings when closing to prevent memory leaks
        noteStage.setOnCloseRequest(e -> {
            note.titleProperty().unbindBidirectional(titleField.textProperty());
            note.contentProperty().unbindBidirectional(textArea.textProperty());
        });

        noteStage.setScene(scene);
        noteStage.show();
    }
}