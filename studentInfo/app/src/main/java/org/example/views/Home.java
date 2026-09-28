package org.example.views;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.controllers.StudentHandler;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Home {
  public void homePage(Stage stage) {
    int ScreenWidth = 1080;
    int ScreenHeight = 900;

    // toolbar item
    Label label = new Label("totalStudent");
    label.getStyleClass().addAll("sub-heading", "text");
    Label StudetNumber = new Label("200");
    StudetNumber.getStyleClass().addAll("heading", "text");

    VBox studentInfoCard = new VBox();
    studentInfoCard.getStyleClass().addAll("displayCards", "addbtn");
    studentInfoCard.setPrefSize(ScreenWidth * 0.3, ScreenHeight * 0.2);
    studentInfoCard.setAlignment(Pos.CENTER);
    studentInfoCard.getChildren().addAll(label, StudetNumber);

    Image addimg = new Image(getClass().getResourceAsStream("images/add.png"));
    ImageView addico = new ImageView(addimg);
    addico.setFitWidth(40);
    addico.setFitHeight(40);
    addico.setPreserveRatio(true);
    VBox addBtn = new VBox();
    addBtn.getStyleClass().add("displayCards");
    addBtn.setPrefSize(ScreenWidth * 0.3, ScreenHeight * 0.2);
    addBtn.setAlignment(Pos.CENTER);
    addBtn.getChildren().addAll(addico);
    addBtn.setOnMouseClicked(event -> {
      Stage addStudentWindow = new Stage();
      addStudentWindow.setTitle("Add New Student");

      VBox layout = new VBox(15);
      layout.setAlignment(Pos.CENTER);
      layout.setPadding(new Insets(20));

      Label heading = new Label("Enter Student Details");

      TextField nameInput = new TextField();
      nameInput.setPromptText("Student Name");

      TextField programInput = new TextField();
      programInput.setPromptText("Student program");

      TextField yearInput = new TextField();
      yearInput.setPromptText("Student year");

      Button submitBtn = new Button("Create");
      submitBtn.setOnAction(e -> {
        String name = nameInput.getText();
        String program = programInput.getText();

        int year = 0;
        try {
          year = Integer.parseInt(yearInput.getText());
        } catch (NumberFormatException ex) {
          heading.setText("please enter interger for year");
          return;
        }

        Connection conn = StudentHandler.initDB();
        if (conn != null) {
          boolean success = StudentHandler.addStudent(conn, name, year, program);

          if (success) {
            System.out.println("Student added successfully!");
            nameInput.setText("");
            programInput.setText("");
            yearInput.setText("");
            addStudentWindow.close();
          }

          try {
            conn.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        }
      });

      layout.getChildren().addAll(heading, nameInput, programInput, yearInput, submitBtn);

      Scene addScene = new Scene(layout, 400, 300);
      addStudentWindow.setScene(addScene);

      addStudentWindow.show();
    });

    HBox toolbarContainer = new HBox(5);
    toolbarContainer.getStyleClass().add("toolbarContainer");
    toolbarContainer.setPrefSize(ScreenWidth, ScreenHeight * 0.1);
    toolbarContainer.getChildren().addAll(studentInfoCard, addBtn);

    BorderPane root = new BorderPane();
    root.setTop(toolbarContainer);

    Scene scene = new Scene(root, ScreenWidth, ScreenHeight);
    scene.getStylesheets().add(getClass().getResource("css/Home.css").toExternalForm());

    stage.setTitle("BitStream");
    stage.setScene(scene);
    stage.show();
  }
}
