package GameObject;

import Helpers.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 * @author anthony-pc
 */
public class Bullet extends GameObject implements Collidable, Drawable{

    public Bullet(int radius, int x, int y, int vx, int vy, int angle, String img, boolean drawable, boolean collidable) {
        super(x,y,vx,vy,angle,img,4,drawable,collidable);
        //Offset bullets by a bigger radius
        this.x += (int) Math.round(radius * Math.cos(Math.toRadians(angle)));
        this.y += (int) Math.round(radius * Math.sin(Math.toRadians(angle)));
    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
        gm.addCollidable(this);
    }
    public void update() {
        if(borderHit){
            this.isDrawable = false;
            this.isCollidable = false;
        }else{
            moveForwards();
            updateHitBox();
        }
    }
    public void subscribe(GameObject go){}

    public void handleCollision(Collidable obj){
        this.isDrawable = false;
        this.isCollidable = false;
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }
}

