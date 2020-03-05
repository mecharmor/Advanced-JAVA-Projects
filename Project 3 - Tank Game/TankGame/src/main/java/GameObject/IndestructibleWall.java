package GameObject;

import Helpers.GameManager;

import java.awt.*;

public class IndestructibleWall extends GameObject implements Drawable, Collidable {

    public IndestructibleWall(int x, int y,boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"IndestructibleWall_40x40",0,drawable,collidable);
    }
    public IndestructibleWall(int x, int y, String img, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,img,0,drawable,collidable);
    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
        gm.addCollidable(this);
    }
    public void subscribe(GameObject go){}

    public void handleCollision(Collidable obj){}

    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,x,y,null);
    }



}
