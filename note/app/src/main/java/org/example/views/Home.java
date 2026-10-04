package org.example.views;

import java.util.function.Consumer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.components.NoteCard;
import org.example.components.TitleBar;
import org.example.database.DatabaseManager;
import org.example.database.NoteDAO;
import org.example.models.Note;
import org.example.styles.StyleHelper;
import org.example.windows.NoteEditor;

public class Home {

    private static final ObservableList<Note> masterNoteList = FXCollections.observableArrayList(
        note -> new Observable[] { 
            note.updatedAtProperty(),
            note.titleProperty(),
            note.contentProperty(),
            note.colorProperty()
        }
    );

    public static void homePage(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);

        DatabaseManager.initializeDatabase();
        masterNoteList.setAll(NoteDAO.getAllNotes());

        Consumer<Note> deleteHandler = note -> {
            masterNoteList.remove(note);
            NoteDAO.delete(note.getId());
        };

        masterNoteList.addListener((ListChangeListener<Note>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Note note : change.getAddedSubList()) {
                        NoteDAO.saveOrUpdate(note);
                    }
                }
                if (change.wasUpdated()) {
                    int index = change.getFrom();
                    if (index >= 0 && index < masterNoteList.size()) {
                        NoteDAO.saveOrUpdate(masterNoteList.get(index));
                    }
                }
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search notes...");
        searchField.setStyle(StyleHelper.SEARCH_FIELD);

        FilteredList<Note> filteredNotes = new FilteredList<>(masterNoteList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredNotes.setPredicate(note -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase().trim();
                boolean matchesTitle = note.getTitle() != null && note.getTitle().toLowerCase().contains(lowerCaseFilter);
                boolean matchesContent = note.getContent() != null && note.getContent().toLowerCase().contains(lowerCaseFilter);
                return matchesTitle || matchesContent;
            });
        });

        SortedList<Note> sortedNotes = new SortedList<>(filteredNotes, (note1, note2) -> {
            if (note1.getUpdatedAt() == null || note2.getUpdatedAt() == null) return 0;
            return note2.getUpdatedAt().compareTo(note1.getUpdatedAt());
        });

        VBox notesContainer = new VBox(10);
        notesContainer.setPadding(new Insets(15, 0, 15, 0));

        Runnable renderCards = () -> {
            notesContainer.getChildren().clear();
            for (Note note : sortedNotes) {
                notesContainer.getChildren().add(new NoteCard(note, deleteHandler));
            }
        };

        sortedNotes.addListener((ListChangeListener<Note>) c -> renderCards.run());

        VBox headerBox = new VBox(10);
        TitleBar titleBar = new TitleBar(stage, "BitStream Notes", () -> {
            Note newNote = new Note("Untitled Note", "New note description...");
            masterNoteList.add(0, newNote);
            NoteDAO.saveOrUpdate(newNote);
            NoteEditor.display(newNote, deleteHandler);
        });

        VBox searchPadding = new VBox(searchField);
        searchPadding.setPadding(new Insets(10, 15, 0, 15));
        headerBox.getChildren().addAll(titleBar, searchPadding);

        ScrollPane scrollPane = new ScrollPane(notesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStylesheets().add(StyleHelper.TRANSPARENT_SCROLL_PANE);

        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(scrollPane);
        root.setStyle(StyleHelper.ROOT_CONTAINER);

        if (masterNoteList.isEmpty()) {
            Note welcomeNote = new Note("Welcome Note", "Welcome to BitStream Notes! Try editing or searching.");
            masterNoteList.add(welcomeNote);
            NoteDAO.saveOrUpdate(welcomeNote);
        }

        renderCards.run();

        Scene scene = new Scene(root, 320, 640);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
}