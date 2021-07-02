package sample.BetaSolver;

public class FindNextPositionLooper extends NNestedForLoop<int[],Boolean>{
    public FindNextPositionLooper(Tensor<Boolean> arr, int numberOfDimensions, int depthOfDimensions, boolean isRepeatAllowed, boolean randomStart){
        super(arr,numberOfDimensions,depthOfDimensions,isRepeatAllowed);
        this.randomStart = randomStart;
        this.internalCurrentDimensionPositions = new int[numberOfDimensions];
    }
    public FindNextPositionLooper(){
        internalCurrentDimensionPositions = null;
    }
    public void loop(){
        if(randomStart){
            generateRandomStart();
        }
        else {
            for(int i = 0; i < numberOfDimensions; i++){
                internalCurrentDimensionPositions[i] = i + 1;
            }
        }
        while (!stop){

            loopBody();

            loopIterator();
        }
    }
    public int[] getResult(){
        return internalCurrentDimensionPositions;
    }
    public void loopBody(){
        int i;
        if(arr.getAtPosition(internalCurrentDimensionPositions)){
            stop = true;
            return ;
        }
        for(i = numberOfDimensions-1;i >= 0; i--){
            internalCurrentDimensionPositions[i]++;
            if(internalCurrentDimensionPositions[i] >= depthOfDimensions){
                internalCurrentDimensionPositions[i] = internalCurrentDimensionPositions[i] % depthOfDimensions;
            } else{
                break;
            }
        }
    }
    boolean randomStart;
    int[] internalCurrentDimensionPositions;
    void generateRandomStart(){
        if(!isRepeatAllowed && depthOfDimensions < numberOfDimensions){
            System.out.println("Error");
            return;
        }
        for(int i = 0; i < numberOfDimensions; i++){
            internalCurrentDimensionPositions[i] = (int)Math.floor(Math.random()*depthOfDimensions + 1);

            if(!isRepeatAllowed){
                for(int j = 0; j < i;j++){
                    if(internalCurrentDimensionPositions[j] == internalCurrentDimensionPositions[i]){
                        internalCurrentDimensionPositions[i]++;
                        j = -1;
                    }
                }
            }
        }
        //System.out.println(answer[i]);
    }
}
