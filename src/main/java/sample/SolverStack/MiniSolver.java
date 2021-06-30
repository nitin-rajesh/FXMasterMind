package sample.SolverStack;

import sample.GameStack.ColorComplex;
import sample.GameStack.GameRecord;

import java.util.Arrays;

abstract public class MiniSolver {
    boolean[][][][] solutionSet;
    int[] firstPosInSolutionSet;
    GameRecord solvable;
    public MiniSolver(GameRecord copyRec){
        solvable = new GameRecord(copyRec);
        firstPosInSolutionSet = new int[copyRec.numberOfColumns];
        for(int i = 0; i < firstPosInSolutionSet.length; i++){
            firstPosInSolutionSet[i] = -1;
        }
    }
    abstract void eliminateSolutions(int lastRowAnswered);

    public int[] rowGuesser(int row){
        if(row==0){
            int[] guess = {1,2,3,4};
            solvable.appendGuess(guess);
            return guess;
        }
        eliminateSolutions(row - 1);
        solvable.appendGuess(firstPosInSolutionSet);
        for( int i: firstPosInSolutionSet){
            System.out.print(i);
        }
        System.out.println();
        return firstPosInSolutionSet;
    }

    boolean colorCompare(int row, int[] possibleAnswer){
        ColorComplex c = ColorComplex.colorCount(possibleAnswer,solvable.board[row]);
        //System.out.println("row = " + row + ", possibleAnswer = " + Arrays.toString(possibleAnswer));
        //System.out.println(c.reds + ":" + c.whites + "::" + solvable.countReds(row) + solvable.countWhites(row));
        return(c.reds == solvable.countReds(row) && c.whites == solvable.countWhites(row));
    }
}

