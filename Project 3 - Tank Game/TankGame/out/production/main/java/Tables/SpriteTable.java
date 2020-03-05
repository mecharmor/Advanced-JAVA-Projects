package Tables;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

import static javax.imageio.ImageIO.read;

public class SpriteTable {

    private static HashMap<String, BufferedImage> spriteTable;
    private static BufferedImage test;

    public static void init(){
        spriteTable = new HashMap<>();
        try{
            //Add Sprites to this table for usage
            spriteTable.put("background", read(new File("resources/background.png")));
            spriteTable.put("tank1", read(new File("resources/tank1.png")));
            spriteTable.put("tank2", read(new File("resources/tank2.png")));
            spriteTable.put("bullet", read(new File("resources/bullet.png")));
            //Background Assetts
            spriteTable.put("grass_40x40", read(new File("resources/grass_40x40.png")));
            spriteTable.put("wall_40x40", read(new File("resources/wall_40x40.png")));
            spriteTable.put("healthBar_40x5", read(new File("resources/healthBar_40x5.png")));
            spriteTable.put("life_10x10", read(new File("resources/life_10x10.png")));
            spriteTable.put("bonusHealthBar_40x5", read(new File("resources/bonusHealthBar_40x5.png")));
            spriteTable.put("DestructibleWall_40x40", read(new File("resources/DestructibleWall_40x40.png")));
            spriteTable.put("IndestructibleWall_40x40", read(new File("resources/IndestructibleWall_40x40.png")));
            spriteTable.put("livesBoost_40x40", read(new File("resources/livesBoost_40x40.png")));


        }catch(IOException e) {
            System.out.println("[SpriteTable.java->init] Could not read file");
        }
    }

    public static BufferedImage getBufferedImage(String key){
        return spriteTable.get(key);
    }
}
