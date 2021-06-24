package sample.GameStack;
import java.lang.Math;

class GameRecord {
    int currentTurn;
    int currentEntry[];
    int answer[];
    int iterator;
    int redCount;
    int whiteCount;
    public boolean isScam;
    boolean victory;
    GameRecord(int variableCount, int constantCount, boolean noRepeat){
        generateAnswer(variableCount,constantCount,noRepeat);
        victory = false;
        isScam = false;
        iterator = 0;
    }
    void generateAnswer(int variableCount, int constantCount, boolean noRepeat){
        answer = new int[variableCount];
        currentEntry = new int[variableCount];
        for(int i = 0; i < variableCount; i++){
            int digit;
            digit = (int)Math.floor(Math.random()*constantCount + 1);
            answer[i] = digit;
            if(noRepeat){
                for(int j = 0; j < i; j++){
                    if(digit == answer[j]){
                        i--;
                    }
                }
            }
        }
    }
    int redScan(){
        redCount = 0;
        victory = false;
        for(int i = 0; i < answer.length; i++){
            if(answer[i] == currentEntry[i]){
                redCount++;
            }
        }
        if(redCount == answer.length)
            victory = true;
        return redCount;
    }
    int whiteScan(){
        whiteCount = 0;
        for(int i = 0; i < answer.length; i++){
            for(int j = 0; j < currentEntry.length; j++){
                if(currentEntry[j] == answer[i]){
                    whiteCount++;
                }
            }
        }
        whiteCount -= redScan();
        return whiteCount;
    }
    void setCurrentEntry(int[] currentEntry1){
        for(int i = 0; i < currentEntry.length; i++){
            currentEntry[i] = currentEntry1[i];
        }
        currentTurn++;
    }
    void appendEntry(int currentVal){
        if(iterator == currentEntry.length){
            iterator = 0;
            currentTurn++;
        }
        currentEntry[iterator] = currentVal;
        iterator++;
    }
}
