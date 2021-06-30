package sample.SolverStack;

import sample.GameStack.GameRecord;

import java.util.Arrays;

public class InfinitySolver extends MiniSolver{
    boolean[] solutionSet;
    int sizeOfSolutionSet;

    public InfinitySolver(GameRecord copyRec) {
        super(copyRec);
        int sizeOfSolutionSet = 1;
        for(int i = 0; i < solvable.numberOfColumns; i++){
            sizeOfSolutionSet *= solvable.numberOfColors;
        }

        solutionSet = new boolean[sizeOfSolutionSet];
        System.out.println(sizeOfSolutionSet);
        for(int i = 0; i < sizeOfSolutionSet; i++){
            if(!solvable.isRepeat){
                if(checkForRepetitions(i)){
                    solutionSet[i] = false;
                }
            }
            solutionSet[i] = true;
        }
    }

    int getIndexValue(int[] guess){
        int val = 0;
        for(int i = 0; i < guess.length; i++){
            guess[i]--;
            val += guess[i];
            val *= 10;
        }
        return val/10;
    }
    int[] getArrayEntry(int guess){
        String strArr = Integer.toString(guess);
        int[] arr = new int[solvable.numberOfColumns];
        for(int i = 0; i < strArr.length(); i++){
            arr[i] = Integer.parseInt(String.valueOf(strArr.charAt(i))) + 1;
        }
        return arr;
    }

    boolean checkForRepetitions(int value){
        while(value > 0){
            int digit = value % 10;
            int temp = value/10;
            while(temp > 0){
                if(digit == temp % 10){
                    return true;
                }
                temp /= 10;
            }
            value /= 10;
        }
        return false;
    }

    void eliminateSolutions(int lastRowAnswered){
        firstPosInSolutionSet[0] = -1;
        //System.out.println(solvable);
        for(int i = 0; i < solutionSet.length; i++){
            //System.out.print(0);
            if(!solutionSet[i]){
                //System.out.print(0);
                continue;
            }
            //System.out.print(1);
            int[] guess= getArrayEntry(i);
            System.out.println(Arrays.toString(guess));
            if(!colorCompare(lastRowAnswered,guess)){
                solutionSet[i] = false;
            } else if(firstPosInSolutionSet[0] == -1){
                int[] temp = getArrayEntry(i);
                System.arraycopy(temp,0,firstPosInSolutionSet,0,firstPosInSolutionSet.length);
            } else {
            }
        }
    }
}
