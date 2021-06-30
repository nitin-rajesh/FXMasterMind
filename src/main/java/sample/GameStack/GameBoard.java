package sample.GameStack;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class GameBoard{
    //GameBoard class handles variables which draw the board

    VBox grid;
    int numberOfGuesses;
    int variableCount;
    int constantCount;
    Button[] buttons;
    ToolBar[] entryBar;
    Text[] answerTexts;
    Button[] optionButtons;
    Rectangle[][] boxes;
    Text infoText;

    public VBox drawBoxGrid(){
        //Group group = new Group();
        grid = new VBox();
        for(int i = 0; i < numberOfGuesses ; i++){
            GridPane group = new GridPane();
            for(int j = 0; j < variableCount; j++){
                boxes[j][i].setArcWidth(10);   //rounded corners
                boxes[j][i].setArcHeight(10);
                boxes[j][i].setStroke(Color.TRANSPARENT);
                group.add(boxes[j][i],j%(variableCount/2),(j<variableCount/2)?0:1,1,1);
            }
            grid.getChildren().add(group);
        }
        return grid;
    }

    public VBox drawTextFields(){
        //Group group = new Group();
        VBox textGrid = new VBox();
        for(int i = 0; i < numberOfGuesses; i++){
            answerTexts[i].setFont(Font.font("Monaco",20));
            textGrid.getChildren().add(answerTexts[i]);
        }
        return textGrid;

    }

    public HBox drawGuessBar(){
        GridPane buttonPane = new GridPane();
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setPrefSize(constantCount>10?35:50,50);
            buttonPane.add(buttons[i],i%(constantCount/2),(i<constantCount/2)?0:1);
        }
        HBox bar = new HBox();
        bar.getChildren().add(buttonPane);
        return bar;
    }

    public ButtonBar drawOptionBar(){
        ButtonBar optionBar = new ButtonBar();
        for (Button optionButton : optionButtons) {
            optionBar.getButtons().add(optionButton);
        }
        optionBar.setPadding(new Insets(5,5,5,5));
        return optionBar;
    }
}
