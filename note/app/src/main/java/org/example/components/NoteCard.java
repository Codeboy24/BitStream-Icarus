package org.example.components;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.models.Note;
import org.example.styles.StyleHelper;
import org.example.windows.NoteEditor;

public class NoteCard extends VBox {
    public NoteCard(Note note, Consumer<Note> onDelete) {
        super(5);
        setPadding(new Insets(10, 15, 10, 15));

        Label title = new Label();
        title.setStyle(StyleHelper.NOTE_CARD_TITLE);
        title.textProperty().bind(note.titleProperty());

        Label preview = new Label();
        preview.setStyle(StyleHelper.NOTE_CARD_PREVIEW);
        preview.textProperty().bind(note.contentProperty());

        getChildren().addAll(title, preview);

        Runnable applyTheme = () -> setStyle(StyleHelper.getCardStyle(note.getColor(), false));
        applyTheme.run();

        note.colorProperty().addListener((obs, oldVal, newVal) -> applyTheme.run());

        setOnMouseEntered(e -> setStyle(StyleHelper.getCardStyle(note.getColor(), true)));
        setOnMouseExited(e -> setStyle(StyleHelper.getCardStyle(note.getColor(), false)));

        setOnMouseClicked(e -> NoteEditor.display(note, onDelete));
    }
}