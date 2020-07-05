package Helpers;

import GameObject.GameObject;
import Tables.ConfigTable;
import Tables.GameObjectTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoader {

    //private ArrayList<ArrayList<Integer>> grid;
    private BufferedReader br;

    public void publishToGameManager(GameManager gm){
        int TILE_SIZE =  ConfigTable.getConfig("Tile_Size");

        try{
            br = new BufferedReader(new FileReader("resources/grid.csv"));

            String currentLine;
            int j = 0;
            while((currentLine = this.br.readLine()) != null){

            String[] splitLine = currentLine.split(",");

            for(int i =0; i < splitLine.length; i++){
                if(!splitLine[i].equals("0")){
                    Class obj = Class.forName("GameObject." + GameObjectTable.getObject(splitLine[i]));
                    GameObject newObj = (GameObject) obj.getConstructor(int.class, int.class, boolean.class, boolean.class).newInstance(TILE_SIZE*i, TILE_SIZE*j, true,true);
                    newObj.publishToGameManager(gm);
                }
            }
            j++;
            }
        }catch(IOException e){
            System.out.println("File Could Not Be Found: " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("[MapLoader->publishToGameManager] Class was Not Found!" + e.getMessage());
        }catch(Exception e){
            System.out.println("[MapLoader] No method found for constructor");
        }
    }

    /**
     * This Class Will Read A text file that is a Grid and instantiate all the GameObjects given the grid
     */


}
