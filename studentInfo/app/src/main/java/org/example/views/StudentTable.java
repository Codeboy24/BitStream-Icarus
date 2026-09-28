package org.example.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.models.Student;
import org.example.controllers.StudentHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StudentTable {

  private ObservableList<Student> studentData = FXCollections.observableArrayList();

  public VBox createTable() {
    TableView<Student> table = new TableView<>();

    TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    idColumn.setPrefWidth(50);

    TableColumn<Student, String> nameColumn = new TableColumn<>("Student Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    nameColumn.setPrefWidth(200);

    TableColumn<Student, Integer> yearColumn = new TableColumn<>("Year");
    yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

    TableColumn<Student, String> programColumn = new TableColumn<>("Program");
    programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
    programColumn.setPrefWidth(150);

    table.getColumns().addAll(idColumn, nameColumn, yearColumn, programColumn);
    table.setItems(studentData);

    refreshTable();

    return new VBox(table);
  }

  public void refreshTable() {
    studentData.clear();
    Connection conn = StudentHandler.initDB();
    if (conn != null) {
      List<Map<String, Object>> dbStudents = StudentHandler.getAllStudents(conn);

      for (Map<String, Object> row : dbStudents) {
        int id = (int) row.get("id");
        String name = (String) row.get("name");
        int year = (int) row.get("year");
        String program = (String) row.get("program");

        studentData.add(new Student(id, name, year, program));
      }

      try {
        conn.close();
      } catch (SQLException e) {
        System.out.println("Error closing connection: " + e.getMessage());
      }
    }
  }
}
