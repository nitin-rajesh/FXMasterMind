package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.GameStack.GameTime;

import java.io.IOException;

public class Controller {
    Parent root;
    Stage stage;
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        GameTime instance = new GameTime(4,8);
        AnchorPane root = new AnchorPane();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        BorderPane borderPane = new BorderPane();   //Main border pane to align all the widgets
        ScrollPane playArea = new ScrollPane();
        BorderPane playWidgets = new BorderPane();
        //Grid of squares
        VBox grid = instance.drawBoxGrid();
        grid.setSpacing(5);
        grid.setPadding(new Insets(5, 5, 0, 5));
        playWidgets.setRight(grid);
        playArea.setContent(playWidgets);
        playArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        playArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        playArea.setPrefHeight(600);
        //Grid of text boxes for guess status
        VBox textGrid = instance.drawTextFields();
        textGrid.setSpacing(81);
        textGrid.setPadding(new Insets(52,20,20,20));
        playWidgets.setLeft(textGrid);
        //Grid of guess buttons
        HBox bar = instance.drawGuessBar();
        ToolBar baseBar = new ToolBar();
        //add button grid to base toolbar
        baseBar.getItems().add(bar);
        borderPane.setBottom(baseBar);
        ButtonBar upperMenu = instance.drawOptionBar();
        Button exitButton = new Button("Exit");
        Alert alert = new Alert((Alert.AlertType.NONE));
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("End game?");
                alert.setContentText("Do you want to end this game?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK){
                    try {
                        start(event);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        upperMenu.getButtons().add(exitButton);
        borderPane.setTop(upperMenu);
        borderPane.setCenter(playArea);
        //add the border pane to the anchor
        root.getChildren().add(borderPane);
        //initialise the scene
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setScene(scene);
        stage.show();
    }

    public void start(ActionEvent e) throws Exception{
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
