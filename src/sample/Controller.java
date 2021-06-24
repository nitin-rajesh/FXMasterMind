package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.GameStack.GameTime;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Stage stage;
    private Scene scene;
    ArrayList<Rectangle> GuessBoxes;
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        GameTime instance = new GameTime(4,8);
        AnchorPane root = new AnchorPane();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        BorderPane borderPane = new BorderPane();   //Main border pane to align all the widgets
        //Grid of squares
        VBox grid = instance.initBoxes();
        grid.setSpacing(5);
        grid.setPadding(new Insets(5, 5, 0, 5));
        borderPane.setRight(grid);
        //Grid of guess buttons
        HBox bar = instance.initGuessBar();
        ToolBar baseBar = new ToolBar();
        BorderPane baseAligner = new BorderPane();
        baseAligner.setLeft(bar);
        baseAligner.setRight(instance.initOptionBar());
        baseBar.getItems().add(baseAligner);
        borderPane.setBottom(baseBar);
        //Grid of text boxes for guess status
        VBox textGrid = instance.initTextFields();
        textGrid.setSpacing(81);
        textGrid.setPadding(new Insets(52,20,20,20));
        borderPane.setLeft(textGrid);
        //add the border pane to the anchor
        root.getChildren().add(borderPane);
        //initialise the scene
        scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setScene(scene);
        stage.show();
    }
}
