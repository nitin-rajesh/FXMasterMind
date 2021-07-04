package sample.SolverStack;

import sample.GameStack.ColorComplex;
import sample.GameStack.GameRecord;

import java.util.Arrays;

public class OctaSolver extends MiniSolver{
    boolean[][][][][][][][] solutionSet;

    public OctaSolver(GameRecord copyRec) {
        super(copyRec);
        solutionSet = new boolean[copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors][copyRec.numberOfColors];
        for (int i = 0; i < solvable.numberOfColors; ++i) {
            for (int j = 0; j < solvable.numberOfColors; ++j) {
                for (int k = 0; k < solvable.numberOfColors; ++k) {
                    for (int l = 0; l < solvable.numberOfColors; ++l) {
                        for (int m = 0; m < solvable.numberOfColors; ++m) {
                            for (int n = 0; n < solvable.numberOfColors; ++n) {
                                for (int o = 0; o < solvable.numberOfColors; ++o) {
                                    for (int p = 0; p < solvable.numberOfColors; ++p) {
                                        if (checkForRepetition(i,j,k,l,m,n,o,p) && !solvable.isRepeat) {
                                            solutionSet[i][j][k][l][m][n][o][p] = false;
                                        } else {
                                            solutionSet[i][j][k][l][m][n][o][p] = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    boolean checkForRepetition(int i,int j, int k, int l, int m, int n, int o, int p){
        int[] checkArray = {i,j,k,l,m,n,o,p};
        for(int x = 0; x < checkArray.length; x++){
            for(int y = 0; y < x; y++){
                if(checkArray[x] == checkArray[y]){
                    return true;
                }
            }
        }
        return false;
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
                                for (int o = 0; o < solvable.numberOfColors; ++o) {
                                    for (int p = 0; p < solvable.numberOfColors; ++p) {
                                        if(!solutionSet[i][j][k][l][m][n][o][p]){
                                            continue;
                                        }
                                        int[] guess= {i+1,j+1,k+1,l+1,m+1,n+1,o+1,p+1};
                                        if(!colorCompare(lastRowAnswered,guess)){
                                            solutionSet[i][j][k][l][m][n][o][p] = false;
                                        } else if (firstPosInSolutionSet[0] == -1) {
                                            firstPosInSolutionSet[0] = i + 1;
                                            firstPosInSolutionSet[1] = j + 1;
                                            firstPosInSolutionSet[2] = k + 1;
                                            firstPosInSolutionSet[3] = l + 1;
                                            firstPosInSolutionSet[4] = m + 1;
                                            firstPosInSolutionSet[5] = n + 1;
                                            firstPosInSolutionSet[6] = o + 1;
                                            firstPosInSolutionSet[7] = p + 1;
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
    }
}
