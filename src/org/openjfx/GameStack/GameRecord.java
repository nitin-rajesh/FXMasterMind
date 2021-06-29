package org.openjfx.GameStack;
import java.lang.Math;
import java.util.ArrayList;

class GameRecord extends ColorComplex{
    ColorComplex c;
    int currentTurn;
    int[] currentEntry;
    int[] answer;
    int numberOfColumns;
    int numberOfColors;
    int iterator;
    public boolean isScam;
    boolean victory;
    boolean isRepeat;
    ArrayList<int[]> board;
    GameRecord(int variableCount, int constantCount, boolean isRepeat){

        this.numberOfColumns = variableCount;
        this.numberOfColors = constantCount;
        this.isRepeat = isRepeat;
        generateAnswer(variableCount,constantCount,isRepeat);
        victory = false;
        isScam = false;
        iterator = 0;
        c = new ColorComplex();
        board = new ArrayList<>();
    }
    GameRecord(GameRecord instance){
        this.isRepeat = instance.isRepeat;
        this.numberOfColors = instance.numberOfColors;
        this.numberOfColumns = instance.numberOfColumns;
        this.answer = instance.answer;
        victory = false;
        isScam = false;
        iterator = 0;
        c = new ColorComplex();
        board = new ArrayList<>();
    }
    void generateAnswer(int variableCount, int constantCount, boolean isRepeat){
        answer = new int[variableCount];
        currentEntry = new int[variableCount];
        for(int i = 0; i < variableCount; i++){
            int digit;
            digit = (int)Math.floor(Math.random()*constantCount + 1);
            answer[i] = digit;
            for(int j = 0; j < i; j++){
                if(digit == answer[j]){
                    if(!isRepeat){
                        i--;
                    }
                }
            }
        }
    }
    int countReds(){
        c.reds = c.redScan(answer,currentEntry);
        if(c.redScan(answer,currentEntry) == answer.length)
            victory = true;
        return c.reds;
    }
    int countWhites(){

        return c.whiteScan(answer,currentEntry);
    }
    void appendEntry(int currentVal){
        if(iterator == currentEntry.length){
            board.add(currentEntry);
            iterator = 0;
            currentTurn++;
        }
        currentEntry[iterator] = currentVal;
        iterator++;
    }
    void resetEntry(){
        victory = false;
        isScam = false;
        iterator = 0;
        currentTurn = 0;
    }
}
