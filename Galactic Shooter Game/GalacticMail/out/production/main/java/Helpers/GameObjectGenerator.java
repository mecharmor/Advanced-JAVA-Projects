package Helpers;

import GameObject.*;
import GameObject.Observable;
import Manager.GameManager;
import Tables.ConfigTable;
import Tables.GameObjectReflectionTable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameObjectGenerator {

    private BufferedReader br;
    private GameManager gm;
    private final String path = "resources/levels/";
    private Queue<String> levels;

    private GameController tempControllerReference;

    public GameObjectGenerator(GameManager gm){
        this.gm = gm;
        levels = new LinkedList<>();

        //Populate Queue with all found levels
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles){
            String fileName = file.getName();
            if(fileName.substring(fileName.lastIndexOf('.')+1).equals("csv")){
                this.levels.add(fileName);//Ex: test.csv
            }else{
                System.out.println("[GameObjectGenerator->Constructor] Non CSV file ignored (" + fileName + ")");
            }
        }
    }
    /***
     * @return amount of Flag Objects generated
     * ALL objects that contain a flag must notify if the flag has been thrown yet.
     * (This will signify when the next level should be loaded)
     */
    public int loadNextLevel(){
        if(!levels.isEmpty()){
            String lvl = levels.remove();
            JOptionPane.showMessageDialog(null,"Level " + lvl.substring(0,lvl.lastIndexOf('.')) + "!");
            return generateMapOfObjects(lvl);
        }else{
               this.gm.setGameOver(true);
        }
        return 0;
    }
    /***
     * @param name - file name containing the extension .csv will be passed in and read as a grid
     * @return - during Object generation, this method will return the amount of Flag objects generated
     */
    private int generateMapOfObjects(String name){
        int TILE_SIZE =  ConfigTable.getConfig("Tile_Size");
        int flagsGenerated = 0;

        try{
            br = new BufferedReader(new FileReader(path + name));

            //File Parsing Begins
            String currentLine;
            int j = 0;
            while((currentLine = this.br.readLine()) != null){

                String[] splitLine = currentLine.split(",");

                for(int i =0; i < splitLine.length; i++) {
                    if(!splitLine[i].equals("0")){
                            Class obj = Class.forName("GameObject." + GameObjectReflectionTable.getObject(splitLine[i]));
                            GameObject newObj = (GameObject) obj.getConstructor(int.class, int.class, boolean.class, boolean.class).newInstance(TILE_SIZE*i, TILE_SIZE*j, true,true);
                            //Special Case for generating Observer
                            if(newObj instanceof Observable){
                                createObserverControlHelper(newObj, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
                                //tempControllerReference = new GameController(newObj, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
                            }
                            if(newObj instanceof Flag){
                                flagsGenerated++;
                            }
                            newObj.publishToGameManager(gm);
                    }
                }
                j++;
            }
        }catch(IOException e){
            System.out.println("File Could Not Be Found: " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("[GameObjectGenerator->publishToGameManager] Class was Not Found!" + e.getMessage());
        }catch(Exception e){
            System.out.println("[GameObjectGenerator] No method found for constructor");
        }
        return flagsGenerated;
    }
    private void createObserverControlHelper(GameObject go, int forward, int back, int left, int right, int shoot){
        //Create Controller that controls that specific game Object
        gm.addObserverControls(new GameController(go, forward, back, left, right, shoot));
    }
}
