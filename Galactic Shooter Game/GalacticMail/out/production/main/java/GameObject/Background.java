package GameObject;

import Manager.GameManager;

import java.awt.*;

public class Background extends GameObject implements Drawable{

    public Background(){
        super(0,0,"Background_1");
    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
    }

    public void subscribe(GameObject go){}
    public boolean isDrawable(){
        return isDrawable;
    }
    public void stop(){}

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, 0,0, null);
    }
}
