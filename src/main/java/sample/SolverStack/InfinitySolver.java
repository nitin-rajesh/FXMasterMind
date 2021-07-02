package sample.SolverStack;

import sample.GameStack.ColorComplex;
import sample.GameStack.GameRecord;
import sample.BetaSolver.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InfinitySolver extends MiniSolver{
    ArrayList<Integer> guesses;
    boolean useRandomStart;
    Tensor<Boolean> possibleSolutions;
    InitLooper init;
    FindNextPositionLooper next;
    EliminationLooper eliminator;
    public InfinitySolver(GameRecord copyRec){
        super(copyRec);
        possibleSolutions = new Tensor<>(solvable.numberOfColumns, solvable.numberOfColors);
        int[] guess = new int[solvable.numberOfColumns];
        init = new InitLooper(possibleSolutions,solvable.numberOfColumns,solvable.numberOfColors,solvable.isRepeat);
        init.loop();
        next = new FindNextPositionLooper(possibleSolutions,solvable.numberOfColumns,solvable.numberOfColors,solvable.isRepeat,false);
        eliminator = new EliminationLooper(possibleSolutions,solvable.numberOfColumns, solvable.numberOfColors, solvable.isRepeat);
    }
    void eliminateSolutions(int lastRowAnswered){

    }

    public int[] rowGuesser(int row){
        int[] guess = new int[solvable.numberOfColumns];
        for(int i = 0; i < 8; i++) {
            next.loop();
            guess = next.getResult();
            solvable.appendGuess(guess);
            ColorComplex guessComplex = new ColorComplex();
            guessComplex.reds = solvable.countReds();
            guessComplex.whites = solvable.countWhites();
            next.reset();
            eliminator.reset(guess, guessComplex);
            eliminator.loop();
            System.out.println(Arrays.toString(guess));
        }
        return guess;
    }

    boolean colorCompare(int row, int[] possibleAnswer){
        ColorComplex c = ColorComplex.colorCount(possibleAnswer,solvable.board[row]);
        //System.out.println("row = " + row + ", possibleAnswer = " + Arrays.toString(possibleAnswer));
        //System.out.println(c.reds + ":" + c.whites + "::" + solvable.countReds(row) + solvable.countWhites(row));
        return(c.reds == solvable.countReds(row) && c.whites == solvable.countWhites(row));
    }
}
