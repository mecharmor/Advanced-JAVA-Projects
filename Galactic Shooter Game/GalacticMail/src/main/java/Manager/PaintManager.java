package Manager;

import GameObject.Drawable;
import GameObject.GameObject;
import GameObject.Observable;
import Tables.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * The Paint Manager is in charge of taking all gameObjects from GameManager that are Drawable and drawing them
 */
public class PaintManager implements Manager{

    private ArrayList<Drawable> drawables;
    private ArrayList<Drawable> drawablesCache;
    private ArrayList<Drawable> drawablesOfTypeObservable;

    private BufferedImage canvas;
    private Graphics2D buffer;

    public PaintManager(){

        this.drawables = new ArrayList<>();
        this.drawablesOfTypeObservable = new ArrayList<>();
        this.drawablesCache = new ArrayList<>();
    }
    public void stop(){
        for(Drawable drawable : drawables){
            ((GameObject)drawable).stop();
        }
        drawables.clear();
        drawablesCache.clear();
        drawablesOfTypeObservable.clear();
    }
    /**
     * This Method Draws ALL drawable game objects
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.canvas = new BufferedImage(ConfigTable.getConfig("SCREEN_WIDTH"),ConfigTable.getConfig("SCREEN_HEIGHT"), BufferedImage.TYPE_INT_RGB);
        buffer = canvas.createGraphics();

        for(int i = 0; i < drawables.size(); i++) {
            if (drawables.get(i).isDrawable()) {
                drawables.get(i).drawImage(buffer);
            }
        }
        //Draw Observables on the Top most layer
        for(int i = 0; i < drawablesOfTypeObservable.size(); i++){
            if(drawablesOfTypeObservable.get(i).isDrawable()){
                drawablesOfTypeObservable.get(i).drawImage(buffer);
            }
        }

        //Position in center of screen
        g2.drawImage(canvas,0 ,0,null);
        g2.dispose();
    }
    public void syncCachedObjects(){
        if(!drawablesCache.isEmpty()){
            drawables.addAll(drawablesCache);
            drawablesCache.clear();
        }
    }
    public void addDrawable(Drawable dr){
        if(dr instanceof Observable){
            this.drawablesOfTypeObservable.add(dr);
        }else{
            this.drawablesCache.add(dr);
        }
    }

    public ArrayList<Drawable> getDrawables(){
        return this.drawables;
    }



}
