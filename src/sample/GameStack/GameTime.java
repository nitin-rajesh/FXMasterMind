package sample.GameStack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class GameTime extends GameBoard{
    // GameTime class handles variables during gameplay
    GameRecord GameInProgress;
    boolean endOfGame;

    public GameTime(int varCount, int constCount, boolean isRepeat){
        endOfGame = false;
        this.variableCount = (varCount/2)*2;
        if(!isRepeat && (varCount > constCount))
            constCount = variableCount;
        else {
            constCount = (constCount/2)*2;
        }
        this.constantCount = constCount;
        entryBar = new ToolBar[2];
        numberOfGuesses = 2*variableCount + (constantCount - 8)*2 + (isRepeat?1:0);
        GameInProgress = new GameRecord(variableCount,constantCount,isRepeat);
        initAnswerTexts();
        initGuessButtons();
        initOptionButtons();
        initBoxes();
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
    private void initGuessButtons(){
        buttons = new Button[constantCount];
        for(int i = 0; i < constantCount; i++){
            buttons[i] = new Button();
            buttons[i].setText(Integer.toString(i + 1));
            int finalI = i;
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addEntry(finalI);
                }
            });
        }
    }
    private void initOptionButtons(){
        List<String> tempList = Arrays.asList("Clear","AI","Answer");
        optionButtons = new Button[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            optionButtons[i] = new Button(tempList.get(i));
            switch (i){
                case 0:
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String filler = " ";
                            for(int j = 0; j < variableCount; j++){
                                filler = filler.concat("*  ");
                            }
                            answerTexts[GameInProgress.currentTurn].setText(filler);
                            GameInProgress.iterator = 0;
                        }
                    });
                    break;

                case 2:
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            String answerStr = "";
                            for(int k = 0; k < variableCount; k++){
                                answerStr = answerStr.concat(Integer.toString(GameInProgress.answer[k]) + " ");
                            }
                            alert.setTitle("Shhh...");
                            alert.setContentText("Answer: " + answerStr);
                            alert.showAndWait();
                            GameInProgress.isScam = true;
                        }
                    });
            }
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
    public boolean isEndOfGame(){
        return endOfGame;
    }
    public void addEntry(int finalI){
        if(!endOfGame){
            GameInProgress.appendEntry(finalI + 1);
            //System.out.println(finalI + 1);
            String temp = "";
            int k;
            //Output formatting statements
            for(k = 0; k < GameInProgress.iterator; k++){
                if(k > 0){
                    temp += GameInProgress.currentEntry[k] > 9?" ":"  ";
                }
                else{
                    temp += GameInProgress.currentEntry[k] > 9?"":" ";
                }
                temp += String.valueOf(GameInProgress.currentEntry[k]);
            }
            while(k < variableCount){
                temp +="  *";
                k++;
            }
            temp += "  ";
            answerTexts[GameInProgress.currentTurn].setText(temp);
            int redCount = GameInProgress.redScan();
            int whiteCount = GameInProgress.whiteScan();
            if(GameInProgress.iterator == variableCount){
                for(k =0; k < redCount; k++){
                    boxes[k][GameInProgress.currentTurn].setFill(Color.RED);
                }
                for(;k < redCount + whiteCount;k++){
                    boxes[k][GameInProgress.currentTurn].setFill(Color.LIGHTGRAY);
                }
                if(GameInProgress.victory){
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    if(GameInProgress.isScam){
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setTitle("Sus");
                        alert.setContentText("Breach detected");
                    }
                    else {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Congratulations");
                        alert.setContentText("You WIN !!");
                    }
                    endOfGame = true;
                    alert.showAndWait();
                }
                else if(GameInProgress.currentTurn == numberOfGuesses - 1){
                    endOfGame = true;
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    String answerStr = "";
                    for(int x = 0; x < variableCount; x++){
                        answerStr = answerStr.concat(GameInProgress.answer[x] + " ");
                    }
                    alert.setTitle("Uh oh...");
                    alert.setContentText("Out of turns :(\n Answer- " + answerStr);
                    alert.showAndWait();
                }
            }
        }
    }
}
