package sample.BetaSolver;

public class InitLooper extends NNestedForLoop<Void,Boolean> {
    public InitLooper(){

    }
    public InitLooper(Tensor<Boolean> arr, int numberOfDimensions, int depthOfDimensions, boolean isRepeatAllowed){
        super(arr,numberOfDimensions,depthOfDimensions,isRepeatAllowed);
        this.arr = arr;
        this.numberOfDimensions = numberOfDimensions;
        this.depthOfDimensions = depthOfDimensions;
        currentDimensionPositions = new int[numberOfDimensions];
        for(int i = 0;i<numberOfDimensions;++i){
            currentDimensionPositions[i] = 0;
        }
        this.isRepeatAllowed = isRepeatAllowed;
        stop = false;
    }
    public void loop(){
        while(!stop){

            loopBody();

            loopIterator();
        }
    }
    public Void getResult(){
        Void obj = null;
        return obj;
    }
    protected void loopBody(){
        if(!isRepeatAllowed && isAnyDimensionSame()){

            arr.setAtPosition(currentDimensionPositions,false);
        }
        else {
            arr.setAtPosition(currentDimensionPositions,true);
        }
    }
}
