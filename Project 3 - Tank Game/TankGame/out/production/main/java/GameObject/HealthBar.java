package GameObject;

import Helpers.GameManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthBar extends GameObject implements Drawable {

        BufferedImage variableHealthBar;

        public HealthBar(int x, int y,String img){
            this(x,y,img,true);
        }
        public HealthBar(int x, int y, String img, boolean drawable){
            super(x,y,0,0,0,img,1,drawable,false);
        }
        public void publishToGameManager(GameManager gm){
            gm.addDrawable(this);
        }

        public void subscribe(GameObject go){
            x = go.x;
            y = go.img.getHeight() + go.img.getHeight()/3 + go.y;
            if(go.health >= 0)
            this.health = go.health;
            this.isDrawable = go.isDrawable;
        }
        @Override
        public String toString() {
            return "x=" + x + ", y=" + y + ", angle=" + angle;
        }

        public void drawImage(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, x,y, null);
            if(health < 100){
                BufferedImage offSet = new BufferedImage((int)Math.round(this.img.getWidth()-this.img.getWidth()*(this.health/100.0)),this.img.getHeight(), BufferedImage.TYPE_INT_RGB);
                g2d.drawImage(offSet, x,y, null);
            }
        }
}
