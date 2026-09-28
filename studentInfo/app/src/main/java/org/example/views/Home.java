package org.example.views;

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
