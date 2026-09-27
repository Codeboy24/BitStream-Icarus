package org.example.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class TitleBar extends ToolBar {
    private double[] offset = new double[2];

    public TitleBar(Stage stage, String titleText, Runnable onPlusClicked) {
        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 4 0 0 10;");

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
        closeButton.setOnAction(event -> stage.close());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Only add the "+" button if a runnable action was provided
        if (onPlusClicked != null) {
            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
            plusButton.setOnAction(event -> onPlusClicked.run());
            getItems().addAll(plusButton, titleLabel, spacer, closeButton);
        } else {
            getItems().addAll(titleLabel, spacer, closeButton);
        }

        setStyle("-fx-background-color: transparent; -fx-border-color: #dddddd; -fx-border-width: 0 0 1 0;");

        // Drag Logic
        setOnMousePressed(event -> {
            offset[0] = event.getSceneX();
            offset[1] = event.getSceneY();
        });
        setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - offset[0]);
            stage.setY(event.getScreenY() - offset[1]);
        });
    }
}