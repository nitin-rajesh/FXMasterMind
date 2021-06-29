package org.openjfx.GameStack;

public class MiniSolver extends GameRecord{
    boolean[][][][] solutionSet;
    int[] firstPosInSolutionSet;
    MiniSolver(GameRecord copyRec){
        super(copyRec);
        solutionSet = new boolean[8][8][8][8];
        firstPosInSolutionSet = new int[4];
        for(int i: firstPosInSolutionSet)
            i = -1;
    }

    void rowGuesser(int row){

    }
    void eliminateSolutions(int lastRowAnswered){

    }
    boolean colorCompare(int row, int i, int j, int k, int l){
        return false;
    }
}
