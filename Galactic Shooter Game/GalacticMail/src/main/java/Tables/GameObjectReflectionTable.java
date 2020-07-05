package Tables;

import java.util.HashMap;

public class GameObjectReflectionTable {
    /***
     * This Class is Solely used for Reflection in GameObjectGenerator
     */
    private static HashMap<String, String> gameObjectTable;

    public static void init(){
        gameObjectTable = new HashMap<>();
        //ID -> Class Name
        gameObjectTable.put("1", "Asteroid");
        gameObjectTable.put("2", "MoonBase");
        gameObjectTable.put("3", "Ship");
    }
    public static String getObject(String key){
        return gameObjectTable.get(key);
    }
}
