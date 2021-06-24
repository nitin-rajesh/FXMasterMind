package sample.GameStack;

import javafx.scene.control.*;


public class GameTime extends GameBoard{
    // GameTime class handles variables during gameplay

    public GameTime(int variableCount, int constantCount){
        index = 0;
        entryBar = new ToolBar[2];
        buttons = new ToggleButton[constantCount];
        this.variableCount = variableCount;
        this.constantCount = constantCount;
        sceneWidth = 27*variableCount + 320;
        numberOfGuesses = 2*variableCount + (constantCount - 8)*2;
        sceneHeight = (numberOfGuesses+1)*120;
    }
}
