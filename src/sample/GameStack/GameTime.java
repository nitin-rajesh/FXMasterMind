package sample.GameStack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.Controller;

import java.util.Arrays;
import java.util.List;


public class GameTime extends GameBoard{
    // GameTime class handles variables during gameplay
    GameRecord GameInProgress;

    public GameTime(int variableCount, int constantCount){
        this.variableCount = variableCount;
        this.constantCount = constantCount;
        entryBar = new ToolBar[2];
        numberOfGuesses = 2*variableCount + (constantCount - 8)*2;
        GameInProgress = new GameRecord(variableCount,constantCount,true);
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
                            System.out.println("You WIN!!");
                        }
                    }
                }
            });
        }
    }
    private void initOptionButtons(){
        List<String> tempList = Arrays.asList("Clear","AI","Answer","Exit");
        optionButtons = new Button[4];
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
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for (int j = 0; j < variableCount; j++)
                                System.out.println(GameInProgress.answer[j]);
                        }
                    });
                    break;
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
    public boolean isVictory(){
        return GameInProgress.victory;
    }
}
