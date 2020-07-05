package GameObject;

import Manager.GameManager;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MoonBase extends GameObject implements Drawable, Collidable, Flag{

    public MoonBase(int x, int y, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"Bases_8",0,drawable,collidable);
        //Set img to random image for diversity
        this.img = SpriteTable.getRandomBufferedImage(this.imgStates);
    }
    public void stop(){}
    public void publishToGameManager(GameManager gm){
        super.gm = gm;
        gm.addCollidable(this);
        gm.addDrawable(this);
    }
    public void subscribe(GameObject go){
        //If this object is a child to another object then you can sync coordinates
    }
    public void handleCollision(Collidable obj){

        if(obj instanceof Ship){
            this.img = SpriteTable.getBufferedImage("Bases_1");
            this.isCollidable = false;
            throwFlag();
        }
    }
    public void throwFlag(){
        super.gm.throwFlag();
    }
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

}
