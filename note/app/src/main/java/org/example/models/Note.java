package org.example.models;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    private final StringProperty title;
    private final StringProperty content;
    private final StringProperty color; // e.g., #ffffff, #fff3cd, #d1e7dd, #f8d7da, #cff4fc
    private final ObjectProperty<LocalDateTime> createdAt;
    private final ObjectProperty<LocalDateTime> updatedAt;

    public Note(String title, String content) {
        this(title, content, "#ffffff");
    }

    public Note(String title, String content, String color) {
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
        this.color = new SimpleStringProperty(color);
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = new SimpleObjectProperty<>(now);
        this.updatedAt = new SimpleObjectProperty<>(now);
    }

    // Title
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { 
        this.title.set(title);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty titleProperty() { return title; }

    // Content
    public String getContent() { return content.get(); }
    public void setContent(String content) { 
        this.content.set(content);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty contentProperty() { return content; }

    // Color Theme
    public String getColor() { return color.get(); }
    public void setColor(String color) { 
        this.color.set(color);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty colorProperty() { return color; }

    // Timestamps
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt.set(updatedAt); }
    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }
}