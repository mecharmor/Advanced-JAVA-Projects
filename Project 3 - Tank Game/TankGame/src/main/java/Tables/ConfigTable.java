package Tables;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static javax.imageio.ImageIO.read;

public class ConfigTable {

    private static HashMap<String, Integer> configTable;

    public static void init(){
        configTable = new HashMap<>();

        configTable.put("SCREEN_WIDTH", 1280);//32
        configTable.put("SCREEN_HEIGHT", 960);//30

        configTable.put("MAP_WIDTH", 1920);
        configTable.put("MAP_HEIGHT", 1080);

        configTable.put("Tile_Size", 40);

        //Read config.txt file to set screen size and other random attributes

    }

    public static int getConfig(String key){
        return configTable.get(key);
    }
}
