package GameObject;

import Helpers.GameManager;
import Tables.ConfigTable;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject implements Drawable{
    /***
     * Resources
     */
    private BufferedImage background;
    private final int spacing = 40;//width or height of square image
    private int columns;
    private int rows;

    public Background(){
        super();//Set drawable to true
        //Amount of
        columns = ConfigTable.getConfig("MAP_WIDTH") / spacing;//48
        rows = ConfigTable.getConfig("MAP_HEIGHT") / spacing;//27

        this.generateBackground();

    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
    }

    public void subscribe(GameObject go){}

    private void generateBackground(){

        String border = "wall_40x40";
        String fill = "grass_40x40";

        background = new BufferedImage(ConfigTable.getConfig("MAP_WIDTH"),ConfigTable.getConfig("MAP_HEIGHT"), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = background.createGraphics();

        //Paint (L)eft & Paint (R)ight
        for(int i = 0; i < this.rows; i++){
            //L
            g2d.drawImage(SpriteTable.getBufferedImage(border), 0, i*spacing, null);
            //R
            g2d.drawImage(SpriteTable.getBufferedImage(border), (this.columns-1)*spacing, i*spacing, null);
        }
        //Paint (T)op & (B)ottom
        for(int j = 0; j < this.columns; j++){
            //T
            g2d.drawImage(SpriteTable.getBufferedImage(border), j*spacing, 0, null);
            //B
            g2d.drawImage(SpriteTable.getBufferedImage(border), j*spacing, (this.rows-1)*spacing, null);
        }
        //Paint Inner
        for(int i = 1; i < this.rows-1; i++){
            for(int j = 1; j < this.columns-1; j++){
                g2d.drawImage(SpriteTable.getBufferedImage(fill), j*spacing, i*spacing, null);
            }
        }
    }
    public boolean isDrawable(){
        return isDrawable;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0,0, null);
    }

    //private addDrawable()


}
