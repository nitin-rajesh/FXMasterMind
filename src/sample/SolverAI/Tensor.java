package sample.SolverAI;

import java.util.ArrayList;

public class Tensor<T> {
    Tensor(){
        dimensions = null;
        data = null;
    }
    Tensor(int numberOfDimensions, int[] dimensions){
        this.numberOfDimensions = numberOfDimensions;
        this.dimensions = new int[numberOfDimensions];
        int num = 1;
        for(int i = 0; i < numberOfDimensions; i++){
            this.dimensions[i] = dimensions[i];
            num *= dimensions[i];
        }
        this.data = new ArrayList<>(num);
        this.actualSize = num;
    }
    public Tensor(int numberOfDimensions, int depthOfDimensions) {
        this.numberOfDimensions = numberOfDimensions;
        this.dimensions = new int[numberOfDimensions];
        int num = 1;//number of elements.
        for (int i = 0; i < numberOfDimensions; ++i) {
            this.dimensions[i] = depthOfDimensions;
            num *= depthOfDimensions;
        }
        this.data = new ArrayList<>(num);
        this.actualSize = num;
    }

    T getAtPosition(int[] pos){
        int actualPosition = pos[numberOfDimensions - 1];
        int multiplier = dimensions[numberOfDimensions - 1];
        for(int i = numberOfDimensions - 2; i >=0; i--){
            actualPosition += multiplier * pos[i];
            multiplier *= dimensions[i];
        }
        return data.get(actualPosition);
    }
    void setAtPosition(int[] pos, T val){
        System.out.println(numberOfDimensions);
        int actualPosition = pos[numberOfDimensions - 1];
        int multiplier = dimensions[numberOfDimensions - 1];
        for(int i = numberOfDimensions - 2; i >=0; i--){
            actualPosition += multiplier * pos[i];
            multiplier *= dimensions[i];
        }
        data.set(actualPosition,val);
    }
    private ArrayList<T> data;
    int[] dimensions;
    int numberOfDimensions;
    int actualSize;
}
