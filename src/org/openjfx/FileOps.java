package org.openjfx;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOps {
    static void writeValues(String varCount, String constCount, boolean isRepeat) throws IOException {
        FileWriter file = new FileWriter("GameSettings.txt");
        file.write(varCount + '\n' + constCount + '\n' + (isRepeat?"1":"0"));
        file.close();
    }
    static ArrayList<String> readValues() throws IOException {
        File file = new File("GameSettings.txt");
        String[] defaults = {"4","8","0"};
        Scanner dataReader = new Scanner(file);
        ArrayList<String> settings = new ArrayList<>();
        while(dataReader.hasNextLine()){
            settings.add(dataReader.nextLine());
        }
        if(settings.size() != 3 ){
            settings.clear();
            for(String str: defaults){
                settings.add(str);
            }
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
