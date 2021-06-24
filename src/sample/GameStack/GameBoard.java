package sample.GameStack;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

class GameBoard{
    //GameBoard class handles variables which draw the board

    VBox grid;
    int sceneWidth;
    int sceneHeight;
    int numberOfGuesses;
    int variableCount;
    int constantCount;
    int index;
    ToggleButton[] buttons;
    ToolBar[] entryBar;
    public VBox initBoxes(){
        Rectangle tempRect;
        //Group group = new Group();
        grid = new VBox();
        for(int i = 0; i < numberOfGuesses ; i++){
            GridPane group = new GridPane();
            for(int j = 0; j < variableCount; j++){
                tempRect = new Rectangle(50,50, Color.GRAY);
                tempRect.setArcWidth(10);   //rounded corners
                tempRect.setArcHeight(10);
                tempRect.setStroke(Color.TRANSPARENT);
                group.add(tempRect,j%(variableCount/2),(j<variableCount/2)?0:1,1,1);
            }
            grid.getChildren().add(group);
        }
        return grid;
    }

    public VBox initTextFields(){
        //Group group = new Group();
        VBox textGrid = new VBox();
        String filler = new String();
        for(int j = 0; j < variableCount; j++){
            filler += "*  ";
        }
        for(int i = 0; i < numberOfGuesses; i++){
            Text tempLabel = new Text(filler);
            tempLabel.setFont(Font.font("Monaco",20));
            textGrid.getChildren().add(tempLabel);
        }
        return textGrid;

    }

    public HBox initGuessBar(){
        GridPane buttonPane = new GridPane();
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new ToggleButton(Integer.toString(i + 1));
            buttons[i].setPrefSize(constantCount>10?35:50,50);
            buttonPane.add(buttons[i],i%(constantCount/2),(i<constantCount/2)?0:1);
        }
        buttonPane.setPrefWidth(sceneWidth - 40);
        HBox bar = new HBox();
        bar.getChildren().add(buttonPane);
        index++;
        return bar;
    }

    public GridPane initOptionBar(){
        GridPane options = new GridPane();
        GridPane subOptions = new GridPane();
        ToggleButton backButton = new ToggleButton("Exit");
        ToggleButton clearButton = new ToggleButton("Undo");
        ToggleButton aiButton = new ToggleButton("AI");
        backButton.setPrefSize(50,50);
        clearButton.setPrefSize(55,100);
        aiButton.setPrefSize(50,50);
        subOptions.add(aiButton,0,0);
        subOptions.add(backButton,0,1);
        subOptions.setVgap(5);
        options.add(clearButton,0,0);
        options.add(subOptions,1,0);
        options.setHgap(10);
        return options;
    }
    public int getSceneHeight() {
        return sceneHeight;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public int getConstantCount() {
        return constantCount;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public int getVariableCount() {
        return variableCount;
    }

}
