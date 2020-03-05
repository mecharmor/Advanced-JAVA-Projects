package GameObject;

import Helpers.GameManager;

import java.awt.*;

public class LivesBoost extends GameObject implements Drawable, Collidable {

    public LivesBoost(int x, int y, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"livesBoost_40x40",0,drawable,collidable);
    }

    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
        gm.addCollidable(this);
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x,y, null);
    }
}
