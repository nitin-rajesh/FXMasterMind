package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        PrefSettings reader = new PrefReader();
        ArrayList<String> settings = reader.readValues();
        instance = new GameTime(Integer.parseInt(settings.get(0)),Integer.parseInt((settings.get(1))), !settings.get(2).equals("0"));
        AnchorPane root = new AnchorPane();
        BorderPane borderPane = new BorderPane();   //Main border pane to align all the widgets
        ScrollPane playArea = new ScrollPane();
        BorderPane playWidgets = new BorderPane();
        //Grid of squares
        VBox boxGrid = instance.drawBoxGrid();
        boxGrid.setSpacing(5);
        boxGrid.setPadding(new Insets(5, 5, 0, 5));
        playArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        playArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        playArea.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);
        //Grid of text boxes for guess status
        VBox textGrid = instance.drawTextFields();
        Separator gap = new Separator();
        textGrid.setSpacing(81);
        textGrid.setPadding(new Insets(52,20,20,20));
        playWidgets.setLeft(textGrid);
        playWidgets.setRight(boxGrid);
        playWidgets.setCenter(gap);
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
            instance.endGame();
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

        //setting the scene
        stage.setScene(scene);
        stage.hide();
        gap.setPrefWidth(scene.getWidth() - textGrid.getWidth() - boxGrid.getWidth() - 15);
        scene.widthProperty().addListener((observableValue, number, t1) -> {
            baseBar.setPrefWidth((double)observableValue.getValue());
            gap.setPrefWidth(gap.getPrefWidth() + t1.doubleValue() - number.doubleValue());
        });

        scene.heightProperty().addListener((observableValue, number, t1)->
            playArea.setPrefHeight(playArea.getHeight() + t1.doubleValue() - number.doubleValue())
        );
        stage.setTitle("FXMasterMind");
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
    public void helpPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/help_menu_p1.fxml")));
        Parent root = loader.load();
        helpStage.setTitle("Help page");
        Scene scene = new Scene(root);
        sample.HelpMenu helpMenu = loader.getController();
        scene.widthProperty().addListener((observableValue, number, t1) ->
            helpMenu.baseBar.setPrefWidth(t1.doubleValue())
        );
        scene.heightProperty().addListener((observableValue, number, t1) ->
            helpMenu.baseBar.setLayoutY(t1.doubleValue() - helpMenu.baseBar.getHeight())
        );
        helpStage.setScene(scene);
        helpStage.show();
    }
}