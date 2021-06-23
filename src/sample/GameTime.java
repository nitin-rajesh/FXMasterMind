package sample;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameTime {
    // GameTime class computes the variables necessary to draw the board
    ArrayList<Rectangle> progressBoxes;
    VBox grid;
    int sceneWidth;
    int sceneHeight;
    int numberOfGuesses;
    int variableCount;
    int constantCount;
    GameTime(int variableCount, int constantCount){
        this.variableCount = variableCount;
        this.constantCount = constantCount;
        sceneWidth = 27*variableCount + 320;
        numberOfGuesses = 8 /*2*variableCount + (constantCount - 8)*2;*/;
        sceneHeight = (numberOfGuesses+1)*120;
    }

    VBox initBoxes(){
        Rectangle tempRect;
        Line tempLine;
        progressBoxes = new ArrayList<>(0);
        //Group group = new Group();
        grid = new VBox();
        for(int i = 0; i < 2 * numberOfGuesses ; i++){
            GridPane group = new GridPane();
            for(int j = 0; j < variableCount; j++){
                tempRect = new Rectangle(50,50, Color.GRAY);
                tempRect.setArcWidth(10);   //rounded corners
                tempRect.setArcHeight(10);
                tempRect.setStroke(Color.TRANSPARENT);
                group.add(tempRect,j%(variableCount/2),(j<variableCount/2)?0:1,1,1);  //Each rectangle is stored in an Arraylist
                progressBoxes.add(tempRect); //Each rectangle is added to a group
            }
            grid.getChildren().add(group);
        }
        return grid;
    }

    HBox initGuessBar(int startVal, int endVal){

        ToggleButton[] buttons = new ToggleButton[endVal>startVal?endVal-startVal:startVal-endVal];
        ToolBar entryBar = new ToolBar();
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new ToggleButton(Integer.toString(i + (endVal>startVal?startVal:endVal)));
            buttons[i].setPrefSize(40,50);
            entryBar.getItems().add(buttons[i]);
        }
        entryBar.setPrefWidth(getSceneWidth());
        HBox bar = new HBox();
        bar.getChildren().add(entryBar);
        return bar;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public ArrayList<Rectangle> getGuessBoxes(int guessIndex) {
        ArrayList<Rectangle> guessBoxes = new ArrayList<>();
        for(int i = guessIndex * variableCount,j = 0;j < variableCount; i++,j++){
            guessBoxes.add(progressBoxes.get(i));
        }
        return guessBoxes;
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
