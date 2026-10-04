package org.example.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.components.TitleBar;
import org.example.models.Note;
import org.example.styles.StyleHelper;

public class NoteEditor {

    private static final String[] PRESET_COLORS = {
        "#ffffff", // White
        "#fff3cd", // Yellow
        "#d1e7dd", // Green
        "#f8d7da", // Pink
        "#cff4fc"  // Blue
    };

    public static void display(Note note) {
        Stage noteStage = new Stage();
        noteStage.initStyle(StageStyle.TRANSPARENT);

        TitleBar titleBar = new TitleBar(noteStage, note.getTitle(), null);
        BorderPane layout = new BorderPane();

        TextArea textArea = new TextArea(note.getContent());
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-size: 14px; -fx-text-fill: #222222;");

        // Helper method to apply theme across layout AND internal text area
        Runnable updateTheme = () -> {
            String currentColor = note.getColor();
            layout.setStyle(StyleHelper.getEditorContainerStyle(currentColor));
            
            // Re-apply stylesheet to force JavaFX .content background to match
            textArea.getStylesheets().clear();
            textArea.getStylesheets().add(StyleHelper.getTransparentTextAreaStylesheet(currentColor));
        };

        // Color Palette Row
        HBox colorPalette = new HBox(8);
        colorPalette.setAlignment(Pos.CENTER_LEFT);
        colorPalette.setPadding(new Insets(5, 0, 5, 0));

        for (String hexColor : PRESET_COLORS) {
            Button colorBtn = new Button();
            colorBtn.setPrefSize(18, 18);
            colorBtn.setStyle(
                "-fx-background-color: " + hexColor + "; " +
                "-fx-background-radius: 9; " +
                "-fx-border-color: #aaaaaa; " +
                "-fx-border-radius: 9; " +
                "-fx-cursor: hand;"
            );
            colorBtn.setOnAction(e -> {
                note.setColor(hexColor);
                updateTheme.run();
            });
            colorPalette.getChildren().add(colorBtn);
        }

        TextField titleField = new TextField(note.getTitle());
        titleField.setStyle(StyleHelper.EDITOR_TITLE_VALID);
        titleField.setPromptText("Note Title...");

        // Input Listeners
        titleField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                titleField.setStyle(StyleHelper.EDITOR_TITLE_INVALID);
            } else {
                titleField.setStyle(StyleHelper.EDITOR_TITLE_VALID);
                note.setTitle(newVal.trim());
            }
        });

        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            note.setContent(newVal != null ? newVal.trim() : "");
        });

        noteStage.setOnHiding(e -> {
            if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
                note.setTitle("Untitled Note");
            }
        });

        VBox editorBody = new VBox(5, colorPalette, titleField, textArea);
        editorBody.setPadding(new Insets(10));

        layout.setTop(titleBar);
        layout.setCenter(editorBody);

        // Apply initial color theme
        updateTheme.run();

        Scene scene = new Scene(layout, 400, 300);
        scene.setFill(Color.TRANSPARENT);

        noteStage.setScene(scene);
        noteStage.show();
    }
}