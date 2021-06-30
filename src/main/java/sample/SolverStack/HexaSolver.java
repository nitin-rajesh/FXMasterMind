package sample.SolverStack;

import sample.GameStack.GameRecord;

public class HexaSolver extends MiniSolver{
    boolean[][][][][][] solutionSet;

    public HexaSolver(GameRecord copyRec) {
        super(copyRec);
        solutionSet = new boolean[copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors];
        for (int i = 0; i < solvable.numberOfColors; ++i) {
            for (int j = 0; j < solvable.numberOfColors; ++j) {
                for (int k = 0; k < solvable.numberOfColors; ++k) {
                    for (int l = 0; l < solvable.numberOfColors; ++l) {
                        for (int m = 0; m < solvable.numberOfColors; ++m) {
                            for (int n = 0; n < solvable.numberOfColors; ++n) {
                                if (checkForRepetition(i,j,k,l,m,n) && !solvable.isRepeat) {
                                    solutionSet[i][j][k][l][m][n] = false;
                                } else {
                                    solutionSet[i][j][k][l][m][n] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    boolean checkForRepetition(int i,int j, int k, int l, int m, int n){
        int[] checkArray = {i,j,k,l,m,n};
        for(int x = 0; x < checkArray.length; x++){
            for(int y = 0; y < x; y++){
                if(checkArray[x] == checkArray[y]){
                    return true;
                }
            }
        }
        return false;
    }

    public int[] rowGuesser(int row){
        if(row==0){
            int[] guess = {1,2,3,4,5,6};
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

    void eliminateSolutions(int lastRowAnswered){
        firstPosInSolutionSet[0] = -1;
        //System.out.println(solvable);
        for (int i = 0; i < solvable.numberOfColors; ++i) {
            for (int j = 0; j < solvable.numberOfColors; ++j) {
                for (int k = 0; k < solvable.numberOfColors; ++k) {
                    for (int l = 0; l < solvable.numberOfColors; ++l) {
                        for (int m = 0; m < solvable.numberOfColors; ++m) {
                            for (int n = 0; n < solvable.numberOfColors; ++n) {
                                if (!solutionSet[i][j][k][l][m][n]) {
                                    continue;
                                }
                                int[] guess = {i + 1, j + 1, k + 1, l + 1, m + 1, n + 1};
                                if (!colorCompare(lastRowAnswered, guess)) {
                                    solutionSet[i][j][k][l][m][n] = false;
                                } else if (firstPosInSolutionSet[0] == -1) {
                                    firstPosInSolutionSet[0] = i + 1;
                                    firstPosInSolutionSet[1] = j + 1;
                                    firstPosInSolutionSet[2] = k + 1;
                                    firstPosInSolutionSet[3] = l + 1;
                                    firstPosInSolutionSet[4] = m + 1;
                                    firstPosInSolutionSet[5] = n + 1;
                                } else {
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
