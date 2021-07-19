package sample.PrefStack;

import java.util.prefs.Preferences;

public class PrefWriter extends PrefSettings{
    @Override
    public void writeValues(String varCount, String constCount, String isRep) {
        Preferences preferences = Preferences.userNodeForPackage(PrefSettings.class);
        try{
            int varC = Integer.parseInt(varCount);
            int constC = Integer.parseInt(constCount);
            varC = (varC/2)*2;
            if(isRep.equals("0") && (varC > constC))
                constC = varC;
            else {
                constC = (constC/2)*2;
            }
            varCount = Integer.toString(varC);
            constCount = Integer.toString(constC);
        }catch (NumberFormatException ignored){}

        preferences.put(variableCount,varCount);
        preferences.put(constantCount,constCount);
        preferences.put(isRepeat,isRep);
    }
}
