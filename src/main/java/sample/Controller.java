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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.GameStack.GameTime;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class Controller implements EventListener {
    Stage stage;
    GameTime instance;
    int buffer = 0;
    boolean acceptKeyStroke = false;
    CheckBox boolRepBox = new CheckBox();
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        System.out.println("Start");
        ArrayList<String> settings = FileOps.readValues();
        instance = new GameTime(Integer.parseInt(settings.get(0)),Integer.parseInt((settings.get(1))), !settings.get(2).equals("0"));
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
            }
        });
        upperMenu.getButtons().add(exitButton);
        borderPane.setTop(upperMenu);
        borderPane.setCenter(playArea);
        //add the border pane to the anchor
        root.getChildren().add(borderPane);
        //initialise the scene
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        scene.addEventHandler(KeyEvent.KEY_PRESSED,(keyEvent) -> {
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
                catch (NumberFormatException exception) {
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
                if(keyEvent.getCode().toString() == "ESCAPE"){
                    try {
                        start(scene);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode().toString() == "ALT") {
                try {
                    instance.addEntry(buffer - 1);
                } catch (NumberFormatException exception) {
                }
                buffer = 0;
            }
            acceptKeyStroke = true;
        });
        stage.setScene(scene);
        stage.show();
    }

    public void start(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/home_page.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("FXMasterMind");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void start(Scene scene) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/home_page.fxml"));
        stage = (Stage) (scene.getWindow());
        stage.setTitle("FXMasterMind");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void configPage(ActionEvent e) throws Exception{
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        AnchorPane root = new AnchorPane();
        VBox Contents = new VBox();
        Contents.setPadding(new Insets(20,20,20,20));
        Contents.setSpacing(20);
        Text title = new Text();
        title.setText("Configure");
        title.setFont(Font.font("DIN Alternate", 24));
        Contents.getChildren().add(title);
        GridPane selectionGrid = new GridPane();
        ComboBox<String> varCountBox = new ComboBox<>();
        ComboBox<String> constCountBox = new ComboBox<>();
        varCountBox.getItems().addAll("4","6","8");
        constCountBox.getItems().addAll("8","10","12","16");
        boolRepBox = new CheckBox();
        boolRepBox.setText("Enable repetitions");
        varCountBox.setPrefSize(100,20);
        constCountBox.setPrefSize(100,20);
        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setPrefSize(50,30);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String varTemp= varCountBox.getSelectionModel().getSelectedItem();
                String constTemp = constCountBox.getSelectionModel().getSelectedItem();
                boolean isRep = boolRepBox.isSelected();

                try {
                    FileOps.writeValues(varTemp,constTemp,isRep);
                    start(event);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        ArrayList<String> settings = FileOps.readValues();
        selectionGrid.add(varCountBox,0,0);
        selectionGrid.add(constCountBox,1,0);
        selectionGrid.add(boolRepBox,0,1);
        selectionGrid.setVgap(20);
        Contents.getChildren().addAll(selectionGrid,saveButton);
        root.getChildren().add(Contents);
        varCountBox.getSelectionModel().select(settings.get(0));
        constCountBox.getSelectionModel().select((settings.get(1)));
        boolRepBox.setSelected(!settings.get(2).equals("0"));
        varCountBox.setEditable(true);
        constCountBox.setEditable(true);
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setScene(scene);
        stage.show();
    }
}