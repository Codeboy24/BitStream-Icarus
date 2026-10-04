package org.example.models;

import java.time.LocalDateTime;
import java.util.UUID;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    private final String id;
    private final StringProperty title;
    private final StringProperty content;
    private final StringProperty color;
    private final ObjectProperty<LocalDateTime> createdAt;
    private final ObjectProperty<LocalDateTime> updatedAt;

    public Note(String title, String content) {
        this(UUID.randomUUID().toString(), title, content, "#ffffff", LocalDateTime.now(), LocalDateTime.now());
    }

    public Note(String id, String title, String content, String color, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
        this.color = new SimpleStringProperty(color);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
        this.updatedAt = new SimpleObjectProperty<>(updatedAt);
    }

    public String getId() { return id; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { 
        this.title.set(title);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty titleProperty() { return title; }

    public String getContent() { return content.get(); }
    public void setContent(String content) { 
        this.content.set(content);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty contentProperty() { return content; }

    public String getColor() { return color.get(); }
    public void setColor(String color) { 
        this.color.set(color);
        setUpdatedAt(LocalDateTime.now());
    }
    public StringProperty colorProperty() { return color; }

    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt.set(updatedAt); }
    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }
}