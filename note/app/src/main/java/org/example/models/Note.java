package org.example.models;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    private final StringProperty title;
    private final StringProperty content;
    private final ObjectProperty<LocalDateTime> createdAt;
    private final ObjectProperty<LocalDateTime> updatedAt;

    public Note(String title, String content) {
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = new SimpleObjectProperty<>(now);
        this.updatedAt = new SimpleObjectProperty<>(now);
    }

    // Title Property
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { 
        this.title.set(title);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty titleProperty() { return title; }

    // Content Property
    public String getContent() { return content.get(); }
    public void setContent(String content) { 
        this.content.set(content);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty contentProperty() { return content; }

    // Timestamps
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt.set(updatedAt); }
    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }
}