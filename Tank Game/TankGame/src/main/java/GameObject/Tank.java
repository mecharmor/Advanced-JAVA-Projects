package GameObject;

import Helpers.GameManager;
import Tables.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Tank extends GameObject implements Collidable, Drawable, Observable{
    /***
     * Flag
     */
    boolean gameOver;
    /***
     * KeyPress Detection
     */
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    /***
     * Child Game Objects
     */
    //Child Game Objects
    ArrayList<Bullet> bulletShots;
    ArrayList<Lives> livesCount;
    private HealthBar healthBar;
    private boolean canShoot;

    public Tank(int x, int y, int vx, int vy, int angle, String img) {
        super(x, y, vx, vy, angle, img, 1, true, true, 4, 100);
        this.healthBar = new HealthBar(x,y,"healthBar_40x5");
        this.bulletShots = new ArrayList<>();
        this.canShoot = true;
        livesCount = new ArrayList<>();
        for(int i = 0; i < this.lives; i++){
            livesCount.add(new Lives(x, y, vx, vy, angle, "life_10x10",i*15 , -(this.img.getHeight() / 3)));
        }
        this.gameOver = false;
    }
    public void publishToGameManager(GameManager gm){
        super.gm = gm;
        gm.addCollidable(this);
        gm.addDrawable(this);
        gm.addObservable(this);
    }
    public void subscribe(GameObject gm){}
    public void publish() {
        this.healthBar.subscribe(this);
        for(Lives live : this.livesCount){
            live.subscribe(this);
        }
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed(){ this.ShootPressed = false;}
    public void toggleShootPressed(){ this.ShootPressed = true;}

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && this.canShoot){//Enter is pressed so shoot bullet
           this.shoot();
        }
        //update all bullets
        for(Bullet bullet : bulletShots){
            bullet.update();
        }
        updateHitBox();
        publish();
    }
    private void shoot(){
        double angleRadians = Math.toRadians(this.angle);
        //Bullet blt = new Bullet(x+this.img.getWidth()+2,y+this.img.getHeight()/2,vx,vy,angle,"bullet",true,true);
        //Bullet blt = new Bullet((int) Math.round((x+this.img.getWidth()+2)/Math.cos(angleRadians)), y+this.img.getHeight()/2,vx,vy,angle,"bullet",true,true);
        Bullet blt = new Bullet(40,x+this.img.getWidth()/2,y+this.img.getHeight()/2 ,vx,vy,angle,"bullet",true,true);
        blt.publishToGameManager(gm);
        bulletShots.add(blt);
        this.unToggleShootPressed();
        this.canShoot = false;
        //Timer Delay
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                canShootAgain();
            }
        }, 500);
    }
    private void canShootAgain(){
        this.canShoot = true;
    }

    public void handleCollision(Collidable obj){
        int bulletDamage = 25;
        //if obj.isDamage -> do something
        //else stop moving?
        if(obj instanceof  Bullet){
            if(health > bulletDamage || livesCount.size() > 1){
                health -= bulletDamage;
                if(health < 1){
                    this.livesCount.remove(0);
                    health = 100;
                }
            }else{
                this.gameOver = true;
                this.isCollidable = false;
                this.isDrawable = false;
                this.canShoot = false;
            }
        }else if(obj instanceof LivesBoost){
            int tempX = this.livesCount.size()*15;
            this.livesCount.add(new Lives(x, y, vx, vy, angle, "life_10x10",tempX , -(this.img.getHeight() / 3)));
        }else{
            moveBackwards(2);
        }
    }
    public boolean GameOver(){
        return this.gameOver;
    }

    public int screenX(){
        /***
         * Note: Need to Algorithmize this process
         */
        int min = ConfigTable.getConfig("SCREEN_WIDTH")/4;//320
        int max = ConfigTable.getConfig("MAP_WIDTH")- ConfigTable.getConfig("SCREEN_WIDTH")/2;//1920 - 640 - 1280
        if(super.x <= min) {
            return 0;
        }else if((x-min) >= max){
        return max;
        }
        return super.x-min;
    }
    public int screenY(){
        int min = ConfigTable.getConfig("SCREEN_HEIGHT")/4;
        int max = ConfigTable.getConfig("MAP_HEIGHT")- ConfigTable.getConfig("SCREEN_HEIGHT");//1920 - 640 - 1280
        if(super.y <= min) {
            return 0;
        }else if((super.y-min) >= max){
            return max;
        }
        return super.y-min;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        this.healthBar.drawImage(g);
        for(Lives live : this.livesCount){
            live.drawImage(g);
        }
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

}
