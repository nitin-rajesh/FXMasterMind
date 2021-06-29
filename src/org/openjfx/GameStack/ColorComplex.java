package org.openjfx.GameStack;

public class ColorComplex {
    int reds;
    int whites;
    int redScan(int[] answer, int[] currentEntry){
        reds = 0;
        for(int i = 0; i < answer.length; i++){
            if(answer[i] == currentEntry[i]){
                reds++;
            }
        }
        return reds;
    }
    int whiteScan(int[] answer, int[] currentEntry){
        whites = 0;
        boolean[] ansMarked = new boolean[answer.length];
        for (int k = 0; k < currentEntry.length; k++) {
            for (int i = 0; i < answer.length; i++) {
                if(ansMarked[i])
                    continue;
                if (answer[i] == currentEntry[k] && !ansMarked[i]) {
                    whites++;
                    ansMarked[i] = true;
                    break;
                }
            }
        }
        whites -= redScan(answer,currentEntry);
        return whites;
    }
    ColorComplex(){
        reds = 0;
        whites = 0;
    }
    public static ColorComplex colorCount(int[] answer, int[] guess){
        ColorComplex temp = new ColorComplex();
        temp.reds = temp.redScan(answer,guess);
        temp.whites = temp.whiteScan(answer,guess);
        return temp;
    }
}
