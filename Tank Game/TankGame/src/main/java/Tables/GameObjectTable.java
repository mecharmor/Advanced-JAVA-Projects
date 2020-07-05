package Tables;

import java.util.HashMap;

public class GameObjectTable {

    private static HashMap<String, String> gameObjectTable;

    public static void init(){
        gameObjectTable = new HashMap<>();

        //gameObjectTable.put(0, ""); - Blank Tile?
        gameObjectTable.put("1", "IndestructibleWall");
        gameObjectTable.put("2", "DestructibleWall");
        gameObjectTable.put("3", "LivesBoost");
        //gameObjectTable.put("3", "Tank");
    }
    public static String getObject(String key){
        return gameObjectTable.get(key);
    }

    /**
     * Use Reflection once the name is received so the Game Object can be instantiated
     */
}
