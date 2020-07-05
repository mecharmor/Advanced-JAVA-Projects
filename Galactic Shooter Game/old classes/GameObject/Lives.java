package GameObject;

import Helpers.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Lives extends GameObject implements Drawable {

    int offSetX;
    int offSetY;

    public Lives(int x, int y, int vx, int vy, int angle, String img, int offSetX, int offSetY) {
        super(x,y,vx,vy,angle,img);
        this.offSetX = offSetX;
        this.offSetY = offSetY;
    }
    public void publishToGameManager(GameManager gm) {
        gm.addDrawable(this);
    }
    public boolean isDrawable() {
        return this.isDrawable;
    }
    public void subscribe(GameObject go) {
        x = go.x + offSetX;
        y = go.y + offSetY;
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }
}