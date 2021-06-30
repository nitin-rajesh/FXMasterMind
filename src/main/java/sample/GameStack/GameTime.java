package sample.GameStack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.SolverStack.*;

import java.util.Arrays;
import java.util.List;


public class GameTime extends GameBoard{
    // GameTime class handles variables during gameplay
    MiniSolver aiSolver;
    GameRecord GameInProgress;
    boolean endOfGame;
    boolean isAI;
    public GameTime(int varCount, int constCount, boolean isRepeat){
        endOfGame = false;
        isAI = false;
        varCount = (varCount/2)*2;
        if(!isRepeat && (varCount > constCount))
            constCount = varCount;
        else {
            constCount = (constCount/2)*2;
        }
        GameInProgress = new GameRecord(varCount,constCount,isRepeat);
        aiSolver = new OctaSolver(GameInProgress);
        entryBar = new ToolBar[2];
        numberOfGuesses = GameInProgress.numberOfGuesses;
        variableCount = varCount;
        constantCount = constCount;
        initAnswerTexts();
        initGuessButtons();
        initOptionButtons();
        initBoxes();
    }

    private void initAnswerTexts(){
        answerTexts = new Text[numberOfGuesses];
        String filler = " ";
        for(int j = 0; j < GameInProgress.numberOfColumns; j++){
            filler = filler.concat("*  ");
        }
        for(int i = 0; i < numberOfGuesses; i++){
            answerTexts[i] = new Text();
            answerTexts[i].setText(filler);
        }
    }
    private void initGuessButtons(){
        buttons = new Button[GameInProgress.numberOfColors];
        for(int i = 0; i < GameInProgress.numberOfColors; i++){
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
        List<String> tempList = Arrays.asList("Clear","Answer","AI solve");
        optionButtons = new Button[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            if(i == 2 && ((GameInProgress.numberOfColumns >= 8 && GameInProgress.numberOfColors > 10)|| GameInProgress.numberOfColors > 16))
                break;
            optionButtons[i] = new Button(tempList.get(i));
            switch (i){
                case 0:
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String filler = " ";
                            for(int j = 0; j < GameInProgress.numberOfColumns; j++){
                                filler = filler.concat("*  ");
                            }
                            answerTexts[GameInProgress.currentTurn].setText(filler);
                            GameInProgress.iterator = 0;
                        }
                    });
                    break;

                case 1:
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            String answerStr = "";
                            for(int k = 0; k < GameInProgress.numberOfColumns; k++){
                                answerStr = answerStr.concat(Integer.toString(GameInProgress.answer[k]) + " ");
                            }
                            alert.setTitle("Shhh...");
                            alert.setContentText("Answer: " + answerStr);
                            alert.showAndWait();
                            GameInProgress.isScam = true;
                        }
                    });
                    break;
                case 2:
                    optionButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            resetGame();
                            isAI = true;
                            //MiniSolver aiSolver = new QuattroSolver(GameInProgress);
                            for(int i = 0; i < GameInProgress.numberOfGuesses - 1; i++){
                                int[] guess = aiSolver.rowGuesser(i);
                                for(int entry: guess){
                                    addEntry(entry - 1);
                                }
                                if(GameInProgress.victory)
                                    break;
                            }
                        }
                    });
                    break;
            }
        }
    }
    private void initBoxes(){
        boxes = new Rectangle[GameInProgress.numberOfColumns][numberOfGuesses];
        for(int i = 0; i < numberOfGuesses ; i++){
            for(int j = 0; j < GameInProgress.numberOfColumns; j++){
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
            StringBuilder temp = new StringBuilder();
            int k;
            //Output formatting statements
            for(k = 0; k < GameInProgress.iterator; k++){
                if(k > 0){
                    temp.append(GameInProgress.currentEntry[k] > 9 ? " " : "  ");
                }
                else{
                    temp.append(GameInProgress.currentEntry[k] > 9 ? "" : " ");
                }
                temp.append(String.valueOf(GameInProgress.currentEntry[k]));
            }
            while(k < GameInProgress.numberOfColumns){
                temp.append("  *");
                k++;
            }
            temp.append("  ");
            answerTexts[GameInProgress.currentTurn].setText(temp.toString());
            if (GameInProgress.iterator == GameInProgress.numberOfColumns) {
                System.out.println(Arrays.toString(aiSolver.rowGuesser(GameInProgress.currentTurn)));
                int redCount = GameInProgress.countReds();
                int whiteCount = GameInProgress.countWhites();
                for (k = 0; k < redCount; k++) {
                    boxes[k][GameInProgress.currentTurn].setFill(Color.RED);
                }
                for (; k < redCount + whiteCount; k++) {
                    boxes[k][GameInProgress.currentTurn].setFill(Color.LIGHTGRAY);
                }
                if (GameInProgress.victory) {
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    if (GameInProgress.isScam) {
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setTitle("Sus");
                        alert.setContentText("Breach detected");
                    } else {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        if(isAI){
                            alert.setTitle("Guess what");
                            alert.setContentText("I WIN !!");
                        }
                        else {
                            alert.setTitle("Congratulations");
                            alert.setContentText("You WIN !!");
                        }
                    }
                    endOfGame = true;
                    alert.showAndWait();
                } else if (GameInProgress.currentTurn == numberOfGuesses - 1) {
                    endOfGame = true;
                    Alert alert = new Alert((Alert.AlertType.NONE));
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    String answerStr = "";
                    for (int x = 0; x < GameInProgress.numberOfColumns; x++) {
                        answerStr = answerStr.concat(GameInProgress.answer[x] + " ");
                    }
                    alert.setTitle("Uh oh...");
                    alert.setContentText("Out of turns :(\n Answer- " + answerStr);
                    alert.showAndWait();
                }
            }
        }
    }
    public void backSpace(){
        String temp = "";
        int k;
        if(GameInProgress.iterator > 0){
            if(GameInProgress.iterator > 1) {
                for (k = 0; k < GameInProgress.iterator - 1; k++) {
                    if (k > 0) {
                        temp += GameInProgress.currentEntry[k] > 9 ? " " : "  ";
                    } else {
                        temp += GameInProgress.currentEntry[k] > 9 ? "" : " ";
                    }
                    temp += String.valueOf(GameInProgress.currentEntry[k]);
                }
                while (k < GameInProgress.numberOfColumns) {
                    temp += "  *";
                    k++;
                }
                temp += "  ";
            }
            else {
                temp = " ";
                for(int i = 0; i < GameInProgress.numberOfColumns; i++)
                    temp += "*  ";
            }
            answerTexts[GameInProgress.currentTurn].setText(temp);
            GameInProgress.iterator--;

        }
    }
    public void addPseudoEntry(int finalI){
        String temp = "";
        int k;
        if(!endOfGame) {
            //Output formatting statements
            GameInProgress.appendEntry(finalI);
            for (k = 0; k < GameInProgress.iterator; k++) {
                if (k > 0) {
                    temp += GameInProgress.currentEntry[k] > 9 ? " " : "  ";
                } else {
                    temp += GameInProgress.currentEntry[k] > 9 ? "" : " ";
                }
                temp += String.valueOf(GameInProgress.currentEntry[k]);
            }
            while (k < GameInProgress.numberOfColumns) {
                temp += "  *";
                k++;
            }
            temp += "  ";
            GameInProgress.iterator--;
            answerTexts[GameInProgress.currentTurn].setText(temp);
        }
    }
    void resetGame(){
        String filler = " ";
        for(int j = 0; j < GameInProgress.numberOfColumns; j++){
            filler = filler.concat("*  ");
        }
        for(int i = 0; i < numberOfGuesses; i++){
            answerTexts[i].setText(filler);
        }

        for(int i = 0; i < GameInProgress.numberOfColors; i++){
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

        GameInProgress.resetEntry();
    }
}
