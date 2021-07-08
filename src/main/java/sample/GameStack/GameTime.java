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


public class GameTime extends GameBoard {
    // GameTime class handles variables during gameplay
    Thread initSolver;
    Thread initGuesser;
    MiniSolver aiSolver;
    GameRecord GameInProgress;
    boolean endOfGame;
    boolean isAI;
    boolean showClues;
    String clueArray;
    public GameTime(int varCount, int constCount, boolean isRepeat){
        endOfGame = false;
        isAI = false;
        showClues = false;
        varCount = (varCount/2)*2;
        if(!isRepeat && (varCount > constCount))
            constCount = varCount;
        else {
            constCount = (constCount/2)*2;
        }
        GameInProgress = new GameRecord(varCount,constCount,isRepeat);
        initSolver = new Thread(new Runnable() {
            @Override
            public void run() {
                aiSolver = null;
            }
        });
        if(isWithinLimits())
            switch (varCount) {
                case 8 -> initSolver = new Thread(() -> aiSolver = new OctaSolver(GameInProgress));
                case 6 -> initSolver = new Thread(() -> aiSolver = new HexaSolver(GameInProgress));
                case 4 -> initSolver = new Thread(() -> aiSolver = new QuattroSolver(GameInProgress));
            }
        //System.out.println(varCount);
        initSolver.start();
        //System.out.println(initSolver.isAlive());
        entryBar = new ToolBar[2];
        numberOfGuesses = GameInProgress.numberOfGuesses;
        variableCount = varCount;
        constantCount = constCount;
        initAnswerTexts();
        initGuessButtons();
        initOptionButtons();
        initBoxes();

    }

    boolean isWithinLimits(){
        if(GameInProgress.numberOfColumns > 8)
            return false;
        if(GameInProgress.numberOfColumns == 8 && GameInProgress.numberOfColors > 10)
            return false;
        if(GameInProgress.numberOfColumns == 6 && GameInProgress.numberOfColors > 22)
            return false;
        if(GameInProgress.numberOfColumns == 4 && GameInProgress.numberOfColors > 100)
            return false;
        return true;
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
            buttons[i].setOnAction(event -> addEntry(finalI));
        }
    }
    private void initOptionButtons(){
        List<String> tempList = Arrays.asList("Clear","AI","Clue");
        if(!isWithinLimits()){
            tempList = Arrays.asList("Clear");
        }
        optionButtons = new Button[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            optionButtons[i] = new Button(tempList.get(i));
            switch (i){
                case 0:
                    optionButtons[i].setOnAction(event -> {
                        if(!endOfGame) {
                            String filler = " ";
                            for (int j = 0; j < GameInProgress.numberOfColumns; j++) {
                                filler = filler.concat("*  ");
                            }
                            answerTexts[GameInProgress.currentTurn].setText(filler);
                            GameInProgress.iterator = 0;
                        }
                    });
                    break;

                case 1:
                    optionButtons[i].setOnAction(event -> {
                        if(!endOfGame && !initSolver.isAlive()) {
                            resetGame();
                            isAI = true;
                            //MiniSolver instanceSolver = theRightSolver(GameInProgress.numberOfColumns);
                            for (int i1 = 0; i1 < GameInProgress.numberOfGuesses - 1; i1++) {
                            int[] guess = aiSolver.rowGuesser();
                            for (int entry : guess) {
                                addEntry(entry - 1);
                            }
                            if (GameInProgress.victory)
                                break;
                            }
                        }
                    });
                    break;
                case 2:
                    optionButtons[i].setOnAction(event -> {
                        showClues = true;
                        String temp = "Clues from the AI";
                        for(int k = 0; k < infoText.getText().length() - temp.length(); k++)
                            temp = temp.concat(" ");
                        temp = temp.concat("\nshow up here");
                        infoText.setText(temp);
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
            if(showClues && GameInProgress.currentTurn > 0 && !initSolver.isAlive())
                infoText.setText(clueArray + "\nTry this");
            if (GameInProgress.iterator == GameInProgress.numberOfColumns) {
                //System.out.println(aiSolver.rowGuesser(GameInProgress.currentTurn));
                if(!isAI && isWithinLimits() && !initSolver.isAlive()){
                    if(initGuesser == null || !initGuesser.isAlive()) {
                        initGuesser = new Thread(() -> {
                            clueArray = Arrays.toString(aiSolver.rowGuesser(GameInProgress.currentTurn));
                            //System.out.println(clueArray);
                            if (showClues)
                                infoText.setText(clueArray + "\nTry this");
                        });
                        initGuesser.start();
                    }
                }
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

                    alert.setAlertType(Alert.AlertType.INFORMATION);
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
        if(GameInProgress.iterator > 0 && GameInProgress.iterator != GameInProgress.numberOfColumns){
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

        for(int i = 0; i < numberOfGuesses ; i++){
            for(int j = 0; j < GameInProgress.numberOfColumns; j++){
                boxes[j][i].setFill(Color.GRAY);
            }
        }

        GameInProgress.resetEntry();
    }
}
