package org.example.views;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.example.controllers.StudentHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModifyStudent {

    public void openModifyWindow(StudentTable tableUI) {
        Stage modifyWindow = new Stage();
        modifyWindow.setTitle("Modify Student Details");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label heading = new Label("Enter Student ID to Edit");

        TextField idInput = new TextField();
        idInput.setPromptText("Student ID");

        Button searchBtn = new Button("Search");

        // Fields for editing (hidden/empty until search is done)
        TextField nameInput = new TextField();
        nameInput.setPromptText("Student Name");

        TextField programInput = new TextField();
        programInput.setPromptText("Student Program");

        TextField yearInput = new TextField();
        yearInput.setPromptText("Student Year");

        Button saveBtn = new Button("Save Changes");
        saveBtn.setDisable(true); // disabled until a student is found

        final int[] currentId = {-1}; // holds the found student's ID

        searchBtn.setOnAction(e -> {
            int id;
            try {
                id = Integer.parseInt(idInput.getText());
            } catch (NumberFormatException ex) {
                heading.setText("Please enter a valid numeric ID");
                return;
            }

            Connection conn = StudentHandler.initDB();
            if (conn != null) {
                Map<String, Object> student = StudentHandler.getStudentById(conn, id);

                if (student == null) {
                    heading.setText("No student found with that ID");
                    saveBtn.setDisable(true);
                } else {
                    heading.setText("Editing: " + student.get("name"));
                    nameInput.setText((String) student.get("name"));
                    programInput.setText((String) student.get("program"));
                    yearInput.setText(String.valueOf(student.get("year")));
                    currentId[0] = id;
                    saveBtn.setDisable(false);
                }

                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveBtn.setOnAction(e -> {
            String name = nameInput.getText();
            String program = programInput.getText();
            int year;

            try {
                year = Integer.parseInt(yearInput.getText());
            } catch (NumberFormatException ex) {
                heading.setText("Please enter a valid integer for year");
                return;
            }

            Connection conn = StudentHandler.initDB();
            if (conn != null) {
                boolean success = StudentHandler.updateStudent(conn, currentId[0], name, year, program);

                if (success) {
                    System.out.println("Student updated successfully!");
                    tableUI.refreshTable();
                    modifyWindow.close();
                } else {
                    heading.setText("Update failed. Try again.");
                }

                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        layout.getChildren().addAll(heading, idInput, searchBtn, nameInput, programInput, yearInput, saveBtn);

        Scene scene = new Scene(layout, 400, 400);
        modifyWindow.setScene(scene);
        modifyWindow.show();
    }
}