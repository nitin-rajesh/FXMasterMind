package sample.GameStack;
import java.lang.Math;

class GameRecord {
    int currentTurn;
    int[] currentEntry;
    int[] answer;
    int iterator;
    int redCount;
    int whiteCount;
    public boolean isScam;
    boolean victory;
    boolean isRepeat;
    GameRecord(int variableCount, int constantCount, boolean isRepeat){
        this.isRepeat = isRepeat;
        generateAnswer(variableCount,constantCount,isRepeat);
        victory = false;
        isScam = false;
        iterator = 0;
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
        for (int k : currentEntry) {
            for (int i : answer) {
                if (i == k) {
                    whiteCount++;
                    break;
                }
            }
        }
        whiteCount -= redScan();
        return whiteCount;
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
