package sample.PrefStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

public class PrefReader extends PrefSettings{
    public ArrayList<String> readValues() {
        Preferences preferences = Preferences.userNodeForPackage(PrefSettings.class);
        String[] defaults = {"4","8","0"};
        ArrayList<String> settings = new ArrayList<>();
        settings.add(preferences.get(variableCount,"4"));
        settings.add(preferences.get(constantCount,"8"));
        settings.add(preferences.get(isRepeat,"0"));
        if(settings.size() != 3 ){
            settings.clear();
            Collections.addAll(settings, defaults);
        }
        else{
            for(int i = 0; i < settings.size(); i++){
                try{
                    if(Integer.parseInt(settings.get(i)) < Integer.parseInt(defaults[i])){
                        settings.set(i,defaults[i]);
                    }
                }
                catch(NumberFormatException e){
                    settings.set(i,defaults[i]);
                }
            }
        }
        return settings;
    }
}
