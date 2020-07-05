package GameObject;

import Helpers.GameManager;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DestructibleWall extends GameObject implements Collidable, Drawable{

    private HealthBar healthBar;

    public DestructibleWall(int x, int y, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"DestructibleWall_40x40",0,drawable,collidable);
        healthBar = new HealthBar(x,y,"bonusHealthBar_40x5");
    }
    public DestructibleWall(int x, int y, String img, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,img,0,drawable,collidable);
        healthBar = new HealthBar(x,y,"bonusHealthBar_40x5");
    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
        gm.addCollidable(this);
    }
    public void subscribe(GameObject go){}

    public void handleCollision(Collidable obj){

        if(obj instanceof Bullet){
            if(health < 1){
                isDrawable = false;
                isCollidable = false;
            }
            health -= 60;
            healthBar.subscribe(this);
        }
    }

    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,x,y,null);
        healthBar.drawImage(g);
    }


}
