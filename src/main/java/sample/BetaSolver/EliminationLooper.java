package sample.BetaSolver;

import sample.GameStack.ColorComplex;

public class EliminationLooper extends NNestedForLoop<Void,Boolean>{
    public EliminationLooper(){

    }
    public EliminationLooper(Tensor<Boolean> arr, int numberOfDimensions, int depthOfDimensions, boolean isRepeatAllowed){
        super(arr,numberOfDimensions,depthOfDimensions,isRepeatAllowed);
    }

    public void loop(){
        while(!stop){

            loopBody();

            loopIterator();

        }
    }
    public Void getResult(){
        Void temp = null;
        return temp;
    }

    public void reset(int[] guess, ColorComplex guessComplex){
        reset();
        this.guess = guess;
        this.guessComplex = guessComplex;
        System.out.println();
    }
    protected void loopBody(){
        if(arr.getAtPosition(currentDimensionPositions)){
            ColorComplex c = ColorComplex.colorCount(currentDimensionPositions,guess);
            //System.out.println(currentDimensionPositions[0] + "  " + currentDimensionPositions[1] + "  " );
            //System.out.println(ColorComplex.colorCompare(c,guessComplex));
            arr.setAtPosition(currentDimensionPositions,ColorComplex.colorCompare(c,guessComplex));
        }
    }

    private int[] guess;
    ColorComplex guessComplex;
}
