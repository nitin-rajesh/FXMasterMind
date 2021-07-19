package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.GameStack.GameTime;
import sample.PrefStack.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;

public class Controller implements EventListener {
    Stage stage;
    Stage helpStage = new Stage();
    GameTime instance;
    int buffer = 0;
    boolean acceptKeyStroke = false;
    CheckBox boolRepBox = new CheckBox();
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        //System.out.println("Start");
        PrefSettings reader = new PrefReader();
        ArrayList<String> settings = reader.readValues();
        instance = new GameTime(Integer.parseInt(settings.get(0)),Integer.parseInt((settings.get(1))), !settings.get(2).equals("0"));
        AnchorPane root = new AnchorPane();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        BorderPane borderPane = new BorderPane();   //Main border pane to align all the widgets
        ScrollPane playArea = new ScrollPane();
        HBox playWidgets = new HBox();
        HBox.setHgrow(playWidgets,Priority.ALWAYS);
        //Grid of squares
        VBox grid = instance.drawBoxGrid();
        grid.setSpacing(5);
        grid.setPadding(new Insets(5, 5, 0, 5));
        playArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        playArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        playArea.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);
        //Grid of text boxes for guess status
        VBox textGrid = instance.drawTextFields();
        textGrid.setSpacing(81);
        textGrid.setPadding(new Insets(52,20,20,20));
        playWidgets.getChildren().addAll(textGrid,grid);
        //Add widgets to scroll pane
        playArea.setContent(playWidgets);
        //Grid of guess buttons
        HBox bar = instance.drawGuessBar();
        ToolBar baseBar = new ToolBar();
        //add button grid to base toolbar
        baseBar.getItems().add(bar);
        borderPane.setBottom(baseBar);
        //add option bar to top
        ButtonBar upperMenu = instance.drawOptionBar();
        Button exitButton = new Button("Exit");
        Alert alert = new Alert((Alert.AlertType.NONE));
        exitButton.setOnAction(event -> {
            if(!instance.isEndOfGame()){
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
            }else{
                try {
                    start(event);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        upperMenu.getButtons().add(exitButton);
        borderPane.setTop(upperMenu);
        borderPane.setCenter(playArea);
        //add the border pane to the anchor
        root.getChildren().add(borderPane);
        //initialise the scene
        Scene scene = new Scene(root, baseBar.getPrefWidth(), root.getPrefHeight());
        scene.addEventHandler(KeyEvent.KEY_PRESSED,(keyEvent) -> {
            //System.out.println(keyEvent.getCode().toString());
            if(keyEvent.isAltDown() && keyEvent.isShiftDown() && keyEvent.getCode().toString().equals("A")){
                instance.showAnswerPopUp();
            }
            //System.out.println(keyEvent.getCode().toString());
            if(keyEvent.getCode().toString().equals("BACK_SPACE")){
                instance.backSpace();
            }
            if(keyEvent.isAltDown()) {
                try{
                    if(Integer.parseInt(settings.get(1)) >= buffer*10 + Integer.parseInt(keyEvent.getCode().getChar())){
                        buffer *= 10;
                        buffer += Integer.parseInt(keyEvent.getCode().getChar());
                        //System.out.println("Val" + buffer);
                            instance.addPseudoEntry(buffer);
                    }
                }
                catch (NumberFormatException ignored) {
                }
                acceptKeyStroke = false;
            }
            else{
                try {
                    instance.addEntry(Integer.parseInt(keyEvent.getCode().getChar()) - 1);
                } catch (NumberFormatException ignored) {
                }
            }
            if(instance.isEndOfGame()){
                if(keyEvent.getCode().toString().equals("ESCAPE")){
                    try {
                        start(scene);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode().toString().equals("ALT")) {
                try {
                    instance.addEntry(buffer - 1);
                } catch (NumberFormatException ignored) {
                }
                buffer = 0;
            }
            acceptKeyStroke = true;
        });
        stage.setScene(scene);
        stage.show();
    }

    public void start(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home_page.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("FXMasterMind");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void start(Scene scene) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home_page.fxml")));
        stage = (Stage) (scene.getWindow());
        stage.setTitle("FXMasterMind");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void configPage(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/config_page.fxml")));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Configure game");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void helpPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/help_menu_p1.fxml")));
        helpStage.setTitle("Help page");
        Scene scene = new Scene(root);
        helpStage.setScene(scene);
        helpStage.show();
    }
}