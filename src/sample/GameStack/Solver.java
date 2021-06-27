package sample.GameStack;
import sample.SolverAI.*;

import java.util.ArrayList;

public class Solver {
    public Solver(){
        guesses = new ArrayList<>();
    }
    Solver(int[] answer,int numberOfDimensions, int depthOfDimensions,boolean isRepeatAllowed, boolean useRandomStart){
        this.answer = answer;
        this.numberOfDimensions = numberOfDimensions;
        this.depthOfDimensions = depthOfDimensions;
        this.isRepeatAllowed = isRepeatAllowed;
        this.useRandomStart = useRandomStart;
        guesses = new ArrayList<>();
    }
    void solve(){
        Tensor<Boolean> possibleSolutions = new Tensor<>(numberOfDimensions,depthOfDimensions);
        ColourComplex answerComplex = ColourComplex.generateComplex(answer,answer,numberOfDimensions);
        int[] guess;
        InitLooper init = new InitLooper(possibleSolutions,numberOfDimensions,depthOfDimensions,isRepeatAllowed);
        init.loop();

        FindNextPositionLooper next = new FindNextPositionLooper(possibleSolutions,numberOfDimensions,
                depthOfDimensions,isRepeatAllowed,useRandomStart);
        EliminationLooper elim = new EliminationLooper(possibleSolutions,numberOfDimensions,
                depthOfDimensions,isRepeatAllowed);
        int count = 0;
        while(true){

            next.loop();
            guess = next.getResult();
            guesses.add(copy(guess));
            ColourComplex guessComplex = ColourComplex.generateComplex(answer,guess,numberOfDimensions);
            if(guessComplex == answerComplex){
                System.out.print("The Ans is: ");
                for(int i = 0;i < numberOfDimensions;++i){
                    System.out.println(guess[i]);
                }
                System.out.println("");

                System.out.println("Solved!");
                break;
            }

            next.reset();

            elim.reset(guess,guessComplex);
            elim.loop();

            ++count;
            if(count>=10){
                break;
            }

        }
        for(int i = 0;i<2;++i){
            int[] temp = new int[2];
            temp[0] = i;
            for(int j = 0;j<depthOfDimensions;++j){
                temp[1] = j;
                //System.out.println(possibleSolutions.getAtPosition(i));
            }

        }
    }
    ArrayList<int[]> guessGuesses(){
        return guesses;
    }

    private int[] answer;
    private ArrayList<int[]> guesses;
    private int numberOfDimensions;
    private int depthOfDimensions;
    private boolean isRepeatAllowed;
    private boolean useRandomStart;
    private int[] copy(int[] arr){
        int[] copied = new int[numberOfDimensions];
        for(int i = 0; i < numberOfDimensions; i++){
            copied[i] = arr[i];
        }
        return copied;
    }
}
