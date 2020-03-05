package Helpers;

import GameObject.Drawable;
import GameObject.Observable;
import Tables.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * The Paint Manager is in charge of taking all gameObjects from GameManager that are Drawable and drawing them
 */
public class PaintManager {

    private GameManager gm;
    private BufferedImage canvas;
    private Graphics2D buffer;
    /**
     * Mini Map Controls
     */
    private final double mmScale = 0.15;
    private final double upDownMiniMap = 0.1;

    public PaintManager(GameManager gm){
        this.gm = gm;//Save reference to GameManager to mess with
    }
    /**
     * This Method Draws all drawable game objects
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.canvas = new BufferedImage(ConfigTable.getConfig("MAP_WIDTH"),ConfigTable.getConfig("MAP_HEIGHT"), BufferedImage.TYPE_INT_RGB);
        buffer = canvas.createGraphics();

        try{
            for(Drawable dr : gm.getDrawables()){
                if(dr.isDrawable()){
                    dr.drawImage(buffer);
                }
            }
        }catch(ConcurrentModificationException e){
        System.out.println("[PaintManager->paint()] ArrayList (Drawable) was modified during iteration");
        }
        //Expecting only 2 observers
        ArrayList<Observable> observers = gm.getObservables();
        //Grab Screens as Images
        BufferedImage left = canvas.getSubimage(observers.get(0).screenX(),observers.get(0).screenY(), ConfigTable.getConfig("SCREEN_WIDTH")/2,ConfigTable.getConfig("SCREEN_HEIGHT"));
        BufferedImage right = canvas.getSubimage(observers.get(1).screenX(),observers.get(1).screenY(), ConfigTable.getConfig("SCREEN_WIDTH")/2,ConfigTable.getConfig("SCREEN_HEIGHT"));
        //Paint Screens
        g2.drawImage(left,0,0,null);
        g2.drawImage(right,ConfigTable.getConfig("SCREEN_WIDTH")/2,0,null);
        //Center Divider - Black Bar
        g2.drawImage(new BufferedImage(4,ConfigTable.getConfig("SCREEN_HEIGHT"), BufferedImage.TYPE_INT_RGB), ConfigTable.getConfig("SCREEN_WIDTH")/2 -2,0, null);
        //Paint MiniMap
        g2.scale(mmScale,mmScale);
        //Position in center of screen
        g2.drawImage(canvas,this.miniMapX() ,this.miniMapY(),null);
        g2.dispose();

        //Not sure why everything is A-ok here. This piece of code is what merges the cache arraylists to the main ones. so iterators can be used
        //gm.syncCachedObjects();
    }

    /***
     * These Two miniMap coordinate helpers take the scaled down coordinates and scales them up so calculations can be made.
     * The returned value is the adjusted coordinates relative to the SCREEN.
     * Note: The Scale of the miniMap can be tweaked with : mmScale [0-1]
     * Note: the Mini Map can be Moved Up and Down by changing: upDownMiniMap [0-1]
     */
    private int miniMapX(){
        int screenCenterX = (int) Math.round((ConfigTable.getConfig("SCREEN_WIDTH")/mmScale))/2;//Minimize error
        int halfWidth =  ConfigTable.getConfig("MAP_WIDTH")/2;

        return screenCenterX-halfWidth;
    }
    private int miniMapY(){
        int screenBottomY = (int) Math.round((ConfigTable.getConfig("SCREEN_HEIGHT")/mmScale));//Minimize error
        int height = (int) Math.round(ConfigTable.getConfig("MAP_HEIGHT") + ConfigTable.getConfig("MAP_HEIGHT")*upDownMiniMap);

        return screenBottomY-height;
    }




}
