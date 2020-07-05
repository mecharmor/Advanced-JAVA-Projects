package Tables;

import GameObject.Animation;
import Manager.GameManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.imageio.ImageIO.read;

public class AnimationTable{

    private static HashMap<String, ArrayList<BufferedImage>> animationTable;
    private static final String path = "resources/animations/";
    private static GameManager gm;

    public static void init(GameManager gm){
        AnimationTable.gm = gm;
        animationTable = new HashMap<>();
        //Read in resources
        try{
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles){
                String fileName = file.getName();
                String name = fileName.substring(0,fileName.lastIndexOf('.'));
                animationTable.put(name, splitImage(read(new File(path + fileName)), name));
            }
        }catch(IOException e) {
            System.out.println("[AnimationTable.java->init] Could not read file");
        }
    }
    private static ArrayList<BufferedImage> splitImage(BufferedImage img, String name){
        ArrayList<BufferedImage> splitImage = new ArrayList<>();
        if(name.contains("_")){
            //Get Dimensions
            int frameCount = Integer.parseInt(name.substring(name.indexOf("_")+1));
            int frameWidth = img.getWidth()/frameCount;
            int frameHeight = img.getHeight();

            for(int i = 0; i < frameCount; i++){
                splitImage.add(img.getSubimage(i*frameWidth, 0, frameWidth, frameHeight));
            }
            return splitImage;
        }
            throw new RuntimeException();
    }
    public static void playAnimation(int x, int y, String name, int timeOfAnimationInMs){
        gm.addDrawable(new Animation(x,y,name,timeOfAnimationInMs));
    }
}
