package sample.GameStack;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.SolverStack.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameTime extends GameBoard {
    // GameTime class handles variables during gameplay
    Thread initSolver;
    Thread initGuesser;
    MiniSolver aiSolver;
    boolean endOfGame;
    boolean isAI;
    boolean showClues;
    String clueArray;
    public GameTime(int varCount, int constCount, boolean isRepeat){
        super(varCount,constCount,isRepeat);
        endOfGame = false;
        isAI = false;
        showClues = false;

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
        initSolver.start();
        entryBar = new ToolBar[2];
        initGuessButtonFunction();
        initOptionButtonFunction();

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

    private void initGuessButtonFunction(){
        for(int i = 0; i < GameInProgress.numberOfColors; i++){
            buttons[i].setText(Integer.toString(i + 1));
            int finalI = i;
            buttons[i].setOnAction(event -> addEntry(finalI));
        }
    }
    private void initOptionButtonFunction(){
        List<String> tempList = Arrays.asList("Clear","AI","Clue");
        if(!isWithinLimits()){
            tempList = Collections.singletonList("Clear");
        }
        optionButtons = new Button[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            optionButtons[i] = new Button(tempList.get(i));
            switch (i) {
                case 0 -> optionButtons[i].setOnAction(event -> {
                    if (!endOfGame) {
                        String filler = " ";
                        for (int j = 0; j < GameInProgress.numberOfColumns; j++) {
                            filler = filler.concat("*  ");
                        }
                        answerTexts[GameInProgress.currentTurn].setText(filler);
                        GameInProgress.iterator = 0;
                    }
                });
                case 1 -> optionButtons[i].setOnAction(event -> {
                    if (!endOfGame && !initSolver.isAlive()) {
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
                case 2 -> optionButtons[i].setOnAction(event -> {
                    showClues = true;
                    String temp = "Clues from the AI";
                    for (int k = 0; k < infoText.getText().length() - temp.length(); k++)
                        temp = temp.concat(" ");
                    temp = temp.concat("\nshow up here");
                    infoText.setText(temp);
                });
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
                temp.append(GameInProgress.currentEntry[k]);
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
                if (GameInProgress.victory || GameInProgress.currentTurn == numberOfGuesses - 1) {
                    endOfGame = true;
                    showEndOfGameMessage(GameInProgress.victory, isAI, showClues);
                }
            }
        }
    }
    public void backSpace(){
        StringBuilder temp = new StringBuilder();
        int k;
        if(GameInProgress.iterator > 0 && GameInProgress.iterator != GameInProgress.numberOfColumns){
            if(GameInProgress.iterator > 1) {
                for (k = 0; k < GameInProgress.iterator - 1; k++) {
                    if (k > 0) {
                        temp.append(GameInProgress.currentEntry[k] > 9 ? " " : "  ");
                    } else {
                        temp.append(GameInProgress.currentEntry[k] > 9 ? "" : " ");
                    }
                    temp.append(GameInProgress.currentEntry[k]);
                }
                while (k < GameInProgress.numberOfColumns) {
                    temp.append("  *");
                    k++;
                }
                temp.append("  ");
            }
            else {
                temp = new StringBuilder(" ");
                temp.append("*  ".repeat(Math.max(0, GameInProgress.numberOfColumns)));
            }
            answerTexts[GameInProgress.currentTurn].setText(temp.toString());
            GameInProgress.iterator--;

        }
    }
    public void addPseudoEntry(int finalI){
        StringBuilder temp = new StringBuilder();
        int k;
        if(!endOfGame) {
            //Output formatting statements
            GameInProgress.appendEntry(finalI);
            for (k = 0; k < GameInProgress.iterator; k++) {
                if (k > 0) {
                    temp.append(GameInProgress.currentEntry[k] > 9 ? " " : "  ");
                } else {
                    temp.append(GameInProgress.currentEntry[k] > 9 ? "" : " ");
                }
                temp.append(GameInProgress.currentEntry[k]);
            }
            while (k < GameInProgress.numberOfColumns) {
                temp.append("  *");
                k++;
            }
            temp.append("  ");
            GameInProgress.iterator--;
            answerTexts[GameInProgress.currentTurn].setText(temp.toString());
        }
    }
}
