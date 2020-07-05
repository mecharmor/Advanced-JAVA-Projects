package Tables;
import java.util.HashMap;

public class ConfigTable {

    private static HashMap<String, Integer> configTable;

    public static void init(){
        configTable = new HashMap<>();

        configTable.put("SCREEN_WIDTH", 1040);//1040/40= 26
        configTable.put("SCREEN_HEIGHT", 680);//680/40 = 17
        configTable.put("GAME_SCORE", 100);

        configTable.put("Tile_Size", 40);

    }

    public static int getConfig(String key){
        return configTable.get(key);
    }
}
