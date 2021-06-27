package sample.SolverAI;


abstract class NNestedForLoop<ReturnType,ArrayType> {
    public NNestedForLoop(){

    }
    public NNestedForLoop(Tensor<ArrayType> arr,int numberOfDimensions,int depthOfDimensions, boolean isRepeatAllowed){
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
    public abstract void loop();
    public abstract ReturnType getResult();
    public static void reset(){
        for(int i = 0;i<numberOfDimensions;++i){
            currentDimensionPositions[i] = 0;
        }
        stop = false;
    }

    protected Tensor<ArrayType> arr;
    static int numberOfDimensions;
    int depthOfDimensions;
    boolean isRepeatAllowed;
    static int[] currentDimensionPositions;
    static boolean stop;

    boolean isAnyDimensionSame(){
        for(int i = 0;i<numberOfDimensions;++i){
            for(int j = i+1;j<numberOfDimensions;++j){
                if(currentDimensionPositions[i]==currentDimensionPositions[j]){
                    return true;
                }
            }
        }
        return false;
    }
    abstract void loopBody();
    void loopIterator(){
        int i;
        for(i = numberOfDimensions-1;i>=0;--i){
            ++currentDimensionPositions[i];
            if(currentDimensionPositions[i]>=depthOfDimensions){
                currentDimensionPositions[i] = currentDimensionPositions[i] % depthOfDimensions;

            } else {
                break;
            }
        }
        if (i < 0){
            //System.out.println("here");
            stop = true;
        }
    }
}
