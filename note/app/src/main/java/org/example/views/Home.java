package org.example.views;

import javafx.collections.FXCollections;
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
import org.example.models.Note;

public class Home {

    private static final ObservableList<Note> masterNoteList = FXCollections.observableArrayList();

    public static void homePage(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);

        // 1. Search Bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search notes...");
        searchField.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 8; " +
            "-fx-border-radius: 8; " +
            "-fx-border-color: #dddddd; " +
            "-fx-padding: 8 12; " +
            "-fx-font-size: 13px;"
        );

        // 2. Filtered List for Search
        FilteredList<Note> filteredNotes = new FilteredList<>(masterNoteList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredNotes.setPredicate(note -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                boolean matchesTitle = note.getTitle() != null && note.getTitle().toLowerCase().contains(lowerCaseFilter);
                boolean matchesContent = note.getContent() != null && note.getContent().toLowerCase().contains(lowerCaseFilter);
                return matchesTitle || matchesContent;
            });
        });

        // 3. Sorted List (Most recently updated notes float to the top)
        SortedList<Note> sortedNotes = new SortedList<>(filteredNotes, (note1, note2) -> {
            if (note1.getUpdatedAt() == null || note2.getUpdatedAt() == null) return 0;
            return note2.getUpdatedAt().compareTo(note1.getUpdatedAt()); // Descending order
        });

        // 4. UI Layout for Note Cards
        VBox notesContainer = new VBox(10);
        notesContainer.setPadding(new Insets(15, 0, 15, 0));

        Runnable renderCards = () -> {
            notesContainer.getChildren().clear();
            for (Note note : sortedNotes) {
                NoteCard card = new NoteCard(note);
                note.updatedAtProperty().addListener((obs, oldVal, newVal) -> {
                    masterNoteList.set(masterNoteList.indexOf(note), note); // Refresh position on edit
                });
                notesContainer.getChildren().add(card);
            }
        };

        sortedNotes.addListener((javafx.collections.ListChangeListener<Note>) c -> renderCards.run());

        // Top Header
        VBox headerBox = new VBox(10);
        
        TitleBar titleBar = new TitleBar(stage, "BitStream Notes", () -> {
            Note newNote = new Note("Untitled Note", "This is a short overview of the contents...");
            masterNoteList.add(0, newNote);
        });

        VBox searchPadding = new VBox(searchField);
        searchPadding.setPadding(new Insets(10, 15, 0, 15));

        headerBox.getChildren().addAll(titleBar, searchPadding);

        ScrollPane scrollPane = new ScrollPane(notesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        BorderPane root = new BorderPane();
        root.setTop(headerBox);
        root.setCenter(scrollPane);

        root.setStyle(
            "-fx-background-color: #f3f3f3; " +
            "-fx-background-radius: 12; " + 
            "-fx-border-radius: 12; " +     
            "-fx-border-color: #cccccc; " +
            "-fx-border-width: 1;"
        );

        if (masterNoteList.isEmpty()) {
            masterNoteList.add(new Note("Welcome Note", "Welcome to BitStream Notes! Try editing or searching."));
            masterNoteList.add(new Note("Project Setup", "Gradle, JavaFX, and real-time bindings are fully configured."));
        }

        renderCards.run();

        Scene scene = new Scene(root, 320, 640);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
}