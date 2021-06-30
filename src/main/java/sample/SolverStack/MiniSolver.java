package sample.SolverStack;

import sample.GameStack.ColorComplex;
import sample.GameStack.GameRecord;

import java.util.Arrays;

public class MiniSolver {
    boolean[][][][] solutionSet;
    int[] firstPosInSolutionSet;
    GameRecord solvable;
    public MiniSolver(GameRecord copyRec){
        solvable = new GameRecord(copyRec);
        solutionSet = new boolean[copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors];
        firstPosInSolutionSet = new int[copyRec.numberOfColumns];
        for(int i = 0; i < firstPosInSolutionSet.length; i++){
            firstPosInSolutionSet[i] = -1;
        }

        for(int i = 0; i < solvable.numberOfColors; ++i){
            for(int j = 0; j < solvable.numberOfColors; ++j){
                for(int k = 0; k < solvable.numberOfColors; ++k){
                    for(int l = 0; l < solvable.numberOfColors; ++l){
                        if(i == j||i == k||i == l||j == k||j == l||k == l){
                            solutionSet[i][j][k][l] = false;
                        } else {
                            solutionSet[i][j][k][l] = true;
                        }
                    }
                }
            }
        }
    }

    public boolean rowGuesser(int row){
        //cout<<"Psst guess this:";
        if(row==0){
            int[] guess = {1,2,3,4};
            solvable.appendGuess(guess);
            return solvable.victory;
        }
        eliminateSolutions(row - 1);
        solvable.appendGuess(firstPosInSolutionSet);
        for( int i: firstPosInSolutionSet){
            System.out.print(i);
        }
        System.out.println();
        return solvable.victory;
    }

    void eliminateSolutions(int lastRowAnswered){
        firstPosInSolutionSet[0] = -1;
        System.out.println(solvable);
        for(int i = 0; i < solvable.numberOfColors; ++i){
            for(int j = 0; j < solvable.numberOfColors; ++j){
                for(int k = 0; k < solvable.numberOfColors; ++k){
                    for(int l = 0; l < solvable.numberOfColors; ++l){
                        if(!solutionSet[i][j][k][l]){
                            continue;
                        }
                        int[] guess= {i+1,j+1,k+1,l+1};
                        if(!colorCompare(lastRowAnswered,guess)){
                            solutionSet[i][j][k][l] = false;
                        } else if(firstPosInSolutionSet[0] == -1){
                            firstPosInSolutionSet[0] = i + 1;
                            firstPosInSolutionSet[1] = j + 1;
                            firstPosInSolutionSet[2] = k + 1;
                            firstPosInSolutionSet[3] = l + 1;
                        } else {
                        }
                    }
                }
            }
        }
    }

    boolean colorCompare(int row, int[] possibleAnswer){
        ColorComplex c = ColorComplex.colorCount(possibleAnswer,solvable.board[row]);
        //System.out.println("row = " + row + ", possibleAnswer = " + Arrays.toString(possibleAnswer));
        //System.out.println(c.reds + ":" + c.whites + "::" + solvable.countReds(row) + solvable.countWhites(row));
        return(c.reds == solvable.countReds(row) && c.whites == solvable.countWhites(row));
    }
}
