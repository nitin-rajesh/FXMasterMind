package sample.GameStack;

import java.util.ArrayList;

public class ColourComplex {
    public ColourComplex(){
        reds = whites = length = 0;
    }
    public ColourComplex(int[] ans, int[] trial, int size){
        boolean[] ansMarked = new boolean[size];
        boolean[] trialMarked = new boolean[size];
        for(int i = 0;i < size; ++i){
            ansMarked[i] = false;
            trialMarked[i] = false;
        }
        reds = 0;
        whites = 0;
        //check for reds first?
        for(int i = 0;i<size;++i){
            if(ans[i]==trial[i]){
                reds++;
                ansMarked[i] = trialMarked[i] = true;
            }
        }
        //Now check for whites?
        for(int i = 0;i<size;++i){
            for(int j = 0;j<size;++j){
                if(i == j){
                    continue;
                }
                if(trialMarked[i]){
                    break;
                }
                if(ansMarked[i]){
                    continue;
                }
                if(trial[i]==ans[j]){
                    ansMarked[i] = trialMarked[i] = true;
                    ++whites;
                }
            }
        }
        this.length = size;
    }
    public ColourComplex(ArrayList<Integer> ans, ArrayList<Integer> trial){
        int size = ans.size();
        boolean[] ansMarked = new boolean[size];
        boolean[] trialMarked = new boolean[size];
        for(int i = 0;i < size; ++i){
            ansMarked[i] = false;
            trialMarked[i] = false;
        }
        reds = 0;
        whites = 0;
        //check for reds first?
        for(int i = 0;i<size;++i){
            if(ans.get(i)==trial.get(i)){
                reds++;
                ansMarked[i] = trialMarked[i] = true;
            }
        }
        //Now check for whites?
        for(int i = 0;i<size;++i){
            for(int j = 0;j<size;++j){
                if(i == j){
                    continue;
                }
                if(trialMarked[i]){
                    break;
                }
                if(ansMarked[i]){
                    continue;
                }
                if(trial.get(i)==ans.get(j)){
                    ansMarked[i] = trialMarked[i] = true;
                    whites++;
                }
            }
        }
        this.length = size;
    }
    public static ColourComplex generateComplex(int[] ans, int[] trial, int size){
        ColourComplex c = new ColourComplex();
        boolean[] ansMarked = new boolean[size];
        boolean[] trialMarked = new boolean[size];
        for(int i = 0;i < size; ++i){
            ansMarked[i] = false;
            trialMarked[i] = false;
        }
        int reds = 0;
        int whites = 0;
        //check for reds first?
        for(int i = 0;i<size;++i){
            if(ans[i]==trial[i]){
                reds++;
                ansMarked[i] = trialMarked[i] = true;
            }
        }
        //Now check for whites?
        for(int i = 0;i<size;++i){
            for(int j = 0;j<size;++j){
                if(i == j){
                    continue;
                }
                if(trialMarked[i]){
                    break;
                }
                if(ansMarked[i]){
                    continue;
                }
                if(trial[i]==ans[j]){
                    ansMarked[i] = trialMarked[i] = true;
                    ++whites;
                }
            }
        }
        c.length = size;
        c.reds = reds;
        c.whites = whites;
        //cout<<reds<<" "<<whites<<endl;
        return c;
    }
    public static ColourComplex generateComplex(ArrayList<Integer> ans, ArrayList<Integer> trial){
        int size = ans.size();
        ColourComplex c = new ColourComplex();
        boolean[] ansMarked = new boolean[size];
        boolean[] trialMarked = new boolean[size];
        for(int i = 0;i < size; ++i){
            ansMarked[i] = false;
            trialMarked[i] = false;
        }
        int reds = 0;
        int whites = 0;
        //check for reds first?
        for(int i = 0;i<size;++i){
            if(ans.get(i)==trial.get(i)){
                reds++;
                ansMarked[i] = trialMarked[i] = true;
            }
        }
        //Now check for whites?
        for(int i = 0;i<size;++i){
            for(int j = 0;j<size;++j){
                if(i == j){
                    continue;
                }
                if(trialMarked[i]){
                    break;
                }
                if(ansMarked[i]){
                    continue;
                }
                if(trial.get(i)==ans.get(j)){
                    ansMarked[i] = trialMarked[i] = true;
                    ++whites;
                }
            }
        }
        c.length = size;
        c.reds = reds;
        c.whites = whites;
        //cout<<reds<<" "<<whites<<endl;
        return c;
    }
    boolean comparison(ColourComplex c){
        return (c.reds == reds && c.whites == whites && c.reds == length);
    }
    boolean isRight(){
        return reds==length;
    }

    private int reds;
    private int whites;
    private int length;
}
