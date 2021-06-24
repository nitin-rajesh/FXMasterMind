package sample.GameStack;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;


public class GameTime extends GameBoard{
    // GameTime class handles variables during gameplay

    public GameTime(int variableCount, int constantCount){
        this.variableCount = variableCount;
        this.constantCount = constantCount;
        entryBar = new ToolBar[2];
        numberOfGuesses = 2*variableCount + (constantCount - 8)*2;
        initAnswerTexts();
        initGuessButtons();
        initOptionButtons();
        initBoxes();

    }
    private void initAnswerTexts(){
        answerTexts = new Text[numberOfGuesses];
        String filler = "";
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
        }
    }
    private void initOptionButtons(){
        List<String> tempList = Arrays.asList("Clear","AI","Answer","Exit");
        optionButtons = new Button[4];
        for(int i = 0; i < tempList.size(); i++){
            optionButtons[i] = new Button(tempList.get(i));
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
}
