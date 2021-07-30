package sample.GameStack;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

public class GameBoard{
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
    protected GameRecord GameInProgress;

    GameBoard(int varCount, int constCount, boolean isRepeat){
        varCount = (varCount/2)*2;
        if(!isRepeat && (varCount > constCount))
            constCount = varCount;
        else {
            constCount = (constCount/2)*2;
        }
        variableCount = varCount;
        constantCount = constCount;
        GameInProgress = new GameRecord(variableCount,constantCount,isRepeat);
        numberOfGuesses = GameInProgress.numberOfGuesses;
        initAnswerTexts();
        initGuessButtons();
        initBoxes();
    }

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
        String temp = "   ";
        for(int i = 0; i < variableCount; i++)
            temp = temp.concat("   ");
        temp += " ";
        infoText = new Text(temp);
        infoText.setFont(Font.font("Monaco",10));
        bar.getChildren().add(buttonPane);
        bar.getChildren().add(infoText);
        bar.setSpacing(20);
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

    private void initAnswerTexts(){
        answerTexts = new Text[numberOfGuesses];
        String filler = " ";
        for(int j = 0; j < variableCount; j++){
            filler = filler.concat("*  ");
        }
        for(int i = 0; i < numberOfGuesses; i++){
            answerTexts[i] = new Text();
            answerTexts[i].setText(filler);
        }
    }

    private void initBoxes(){
        boxes = new Rectangle[variableCount][numberOfGuesses];
        for(int i = 0; i < numberOfGuesses ; i++){
            for(int j = 0; j < variableCount; j++){
                boxes[j][i] = new Rectangle(50,50, Color.GRAY);
            }
        }
    }
    private void initGuessButtons(){
        buttons = new Button[constantCount];
        for(int i = 0; i < constantCount; i++){
            buttons[i] = new Button();
        }
    }
    public void showAnswerPopUp(){
        Alert alert = new Alert((Alert.AlertType.NONE));
        alert.setAlertType(Alert.AlertType.INFORMATION);
        String answerStr = "";
        for(int k = 0; k < GameInProgress.numberOfColumns; k++){
            answerStr = answerStr.concat(GameInProgress.answer[k] + " ");
        }
        alert.setTitle("Shhh...");
        alert.setContentText("Answer: " + answerStr);
        alert.showAndWait();
        GameInProgress.isScam = true;
    }

    public void showEndOfGameMessage(boolean isVictory, boolean isAI, boolean showClues){
        Alert alert = new Alert((Alert.AlertType.NONE));

        alert.setAlertType(Alert.AlertType.INFORMATION);
        if(isVictory){
            if(isAI){
                alert.setTitle("Guess what");
                alert.setContentText("I WIN !!");
            }
            else if (GameInProgress.isScam) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Sus");
                alert.setContentText("Breach detected");
            }
            else if(showClues){
                alert.setTitle("You won!");
                alert.setContentText("You're welcome for the help :)");
            }

            else {
                alert.setTitle("Congratulations");
                alert.setContentText("You WIN !!");
            }
        } else {
            String answerStr = "";
            for (int x = 0; x < GameInProgress.numberOfColumns; x++) {
                answerStr = answerStr.concat(GameInProgress.answer[x] + " ");
            }
            alert.setTitle("Uh oh...");
            alert.setContentText("Out of turns :(\n Answer- " + answerStr);
        }
        alert.showAndWait();
    }

    public int getVariableCount(){
        return variableCount;
    }

    void resetGame(){
        String filler = " ";
        for(int j = 0; j < GameInProgress.numberOfColumns; j++){
            filler = filler.concat("*  ");
        }
        for(int i = 0; i < numberOfGuesses; i++){
            answerTexts[i].setText(filler);
        }

        for(int i = 0; i < numberOfGuesses ; i++){
            for(int j = 0; j < GameInProgress.numberOfColumns; j++){
                boxes[j][i].setFill(Color.GRAY);
            }
        }
        GameInProgress.resetEntry();
    }
}
