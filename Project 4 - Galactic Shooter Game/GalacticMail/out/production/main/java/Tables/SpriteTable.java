package Tables;

import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static javax.imageio.ImageIO.read;

public class SpriteTable{

    private static HashMap<String, BufferedImage> spriteTable;
    private static final String path = "resources/sprites/";

    public static void init(){
        spriteTable = new HashMap<>();
        //Read in resources
        try{
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles){
                String name = file.getName();
                spriteTable.put(name.substring(0,name.lastIndexOf('.')), read(new File(path + name)));
            }
        }catch(IOException e) {
            System.out.println("[SpriteTable.java->init] Could not read file");
        }
    }
    public static BufferedImage getBufferedImage(String key){
        if(spriteTable.containsKey(key)){
            return spriteTable.get(key);
        }else{
            return (new BufferedImage(40,40, BufferedImage.TYPE_INT_RGB));
        }
    }
    public static ArrayList<BufferedImage> getImgStates(String key){
        ArrayList<BufferedImage> anim = new ArrayList<>();
        if(spriteTable.containsKey(key) && key.contains("_")){
            BufferedImage img = spriteTable.get(key);
            int frameCount = Integer.parseInt(key.substring(key.indexOf("_")+1));
            if(frameCount <= 0)
                    frameCount = 1;
            int frameWidth = img.getWidth()/frameCount;
            int frameHeight = img.getHeight();
            //Ex: frames = 8
            for(int i = 0; i < frameCount; i++){
                anim.add(img.getSubimage(i*frameWidth, 0, frameWidth, frameHeight));
            }
        }else{
            anim.add(new BufferedImage(40,40, BufferedImage.TYPE_INT_RGB));
        }
        return anim;
    }
    public static BufferedImage getRandomBufferedImage(String key){
        if(spriteTable.containsKey(key) && key.contains("_")){
            ArrayList<BufferedImage> listOfKey = getImgStates(key);
            if(listOfKey.size() <= 1){
                return listOfKey.get(0);
            }
            Random rand = new Random();
            return listOfKey.get(rand.nextInt(listOfKey.size()-1));
        }
        return (new BufferedImage(40,40, BufferedImage.TYPE_INT_RGB));
    }
    public static BufferedImage getRandomBufferedImage(ArrayList<BufferedImage> images){
        Random rand = new Random();
        return images.get(rand.nextInt(images.size()-1));
    }
}
