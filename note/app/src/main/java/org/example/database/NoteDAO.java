package org.example.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.example.models.Note;

public class NoteDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes ORDER BY updated_at DESC";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String color = rs.getString("color");
                String createdAtStr = rs.getString("created_at");
                String updatedAtStr = rs.getString("updated_at");

                LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, FORMATTER);
                LocalDateTime updatedAt = LocalDateTime.parse(updatedAtStr, FORMATTER);

                Note note = new Note(id, title, content, color, createdAt, updatedAt);
                notes.add(note);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching notes: " + e.getMessage());
        }

        return notes;
    }

    public static void saveOrUpdate(Note note) {
        String sql = "INSERT INTO notes(id, title, content, color, created_at, updated_at) " +
                     "VALUES(?, ?, ?, ?, ?, ?) " +
                     "ON CONFLICT(id) DO UPDATE SET " +
                     "title = excluded.title, " +
                     "content = excluded.content, " +
                     "color = excluded.color, " +
                     "updated_at = excluded.updated_at";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, note.getId());
            pstmt.setString(2, note.getTitle());
            pstmt.setString(3, note.getContent());
            pstmt.setString(4, note.getColor());
            pstmt.setString(5, note.getCreatedAt().format(FORMATTER));
            pstmt.setString(6, note.getUpdatedAt().format(FORMATTER));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving note: " + e.getMessage());
        }
    }

    public static void delete(String noteId) {
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, noteId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting note: " + e.getMessage());
        }
    }
}