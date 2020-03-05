package GameObject;

import Manager.GameManager;
import Tables.AnimationTable;
import Tables.ConfigTable;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Asteroid extends GameObject implements Drawable, Collidable {

    private Timer moveTimer;

    public Asteroid(int x, int y, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"Asteroid_5",1,drawable,collidable);
        //Set img to random image for diversity
        this.img = SpriteTable.getRandomBufferedImage(this.imgStates);
        this.angle = (new Random()).nextInt(359);//Set random direction

        moveForwardAuto();
    }
    @Override
    protected void checkBorder(){
        if(x <= 0){
            x--;
        }else if(x >= ConfigTable.getConfig("SCREEN_WIDTH")){
            x++;
        }
        if(y <= 0){
            y--;
        }else if(y >= ConfigTable.getConfig("SCREEN_HEIGHT")){
            y++;
        }
        if (x < (0-this.img.getWidth())) {
            x = ConfigTable.getConfig("SCREEN_WIDTH");
        }
        if (x > ConfigTable.getConfig("SCREEN_WIDTH")) {
            x = 0-this.img.getWidth();
        }
        if (y < (0-img.getHeight())) {
            y = ConfigTable.getConfig("SCREEN_HEIGHT");
        }
        if (y > ConfigTable.getConfig("SCREEN_HEIGHT")) {
            y = 0-this.img.getHeight();
        }
    }
    private void moveForwardAuto(){
        moveTimer = new Timer();
        moveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                moveForwards((new Random()).nextInt(2)/1000.0);
            }
        }, 0,50);
    }
    public void stop(){
        moveTimer.cancel();
    }
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
            AnimationTable.playAnimation(this.x, this.y, "Explosion_7", 2000);
            this.isDrawable = false;
            this.isCollidable = false;
        }
    }
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
