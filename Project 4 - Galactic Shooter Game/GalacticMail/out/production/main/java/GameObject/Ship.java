package GameObject;

import Manager.GameManager;
import Tables.AnimationTable;
import Tables.AudioTable;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Ship extends GameObject implements Drawable, Collidable, Observable {

    private final int moveForwardTimerLoops = 60;

    private Timer timer_points;
    private Timer timerMoveForward;
    private int timerMoveForwardloopsLeft;
    private boolean gameOver;

    public Ship(int x, int y, boolean drawable, boolean collidable) {
        super(x,y,0,0,0,"Flying_1",1,drawable,collidable);
        imgStates = SpriteTable.getImgStates("Flying_2");
        this.gameOver = false;
        this.isMovable = true;
        timerMoveForwardloopsLeft = moveForwardTimerLoops;
    }

    public Ship(int x, int y, int vx, int vy, int angle, String img) {
        super(x, y, vx, vy, angle, img, 1, true, true, 4, 100);
    }
    public void publishToGameManager(GameManager gm){
        super.gm = gm;
        gm.addCollidable(this);
        gm.addDrawable(this);
        gm.addObservable(this);
    }
    public void subscribe(GameObject go){
        //If this object is a child to another object then you can sync coordinates
    }
    public void publish(){
        //If any child objects then add here
    }
    public void stop(){
        if(timer_points != null){
            timer_points.cancel();
        }
        if(timerMoveForward != null){
            timerMoveForward.cancel();
        }
    }
    public void update(){
        if(this.isMovable){
            if (this.UpPressed) {
                this.moveForwards();
            }
            if (this.DownPressed) {
                this.moveBackwards();
            }
            updateHitBox();
            publish();
        }
        if (this.LeftPressed) {
            if(timerMoveForwardloopsLeft < moveForwardTimerLoops){
                this.rotateLeft((new Random().nextInt(5)));
            }else{
                this.rotateLeft();
            }
        }
        if (this.RightPressed) {
            if(timerMoveForwardloopsLeft < moveForwardTimerLoops){
                this.rotateRight((new Random().nextInt(5)));
            }else {
                this.rotateRight();
            }
        }
        //Do when landed on Moonbase
        if(this.ShootPressed && !this.isMovable){
            this.timer_points.cancel();
            this.isMovable = true;
            launchRocket();
        }
    }
    private void launchRocket(){
        timerMoveForward = new Timer();
        img = imgStates.get(1);
        timerMoveForward.schedule(new TimerTask() {
            @Override
            public void run() {
                launchRocketHelper();
            }
        }, 0,10);
    }
    private void launchRocketHelper(){
        if(this.isMovable){
            if(timerMoveForwardloopsLeft > 0){
                moveForwards();
                timerMoveForwardloopsLeft--;
            }else{
                img = imgStates.get(0);
                timerMoveForward.cancel();
                timerMoveForwardloopsLeft = moveForwardTimerLoops;
            }
        }else{
            timerMoveForwardloopsLeft = moveForwardTimerLoops;
            img = imgStates.get(0);
            timerMoveForward.cancel();
        }
    }
    public void handleCollision(Collidable obj){
        if(obj instanceof Asteroid){
            AudioTable.playOnce("Explosion");
            AnimationTable.playAnimation(this.x, this.y, "Explosion_7", 1000);
            this.isDrawable = false;
            this.isMovable = false;
            //End game after 1.5 seconds
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    gameOver = true;
                }
            }, 1500);
        }else if(obj instanceof MoonBase){
            this.timer_points = new Timer();
            if(this.isMovable){
                super.gm.addGameScore(100);
                this.isMovable = false;
                this.x = ((MoonBase) obj).getX();
                this.y = ((MoonBase) obj).getY();
            }
            timer_points.schedule(new TimerTask() {
                @Override
                public void run() {
                    deductGamePoints();
                }
            }, 500,500);
        }
    }
    private void deductGamePoints(){
        super.gm.addGameScore(-4);
    }

    public boolean GameOver(){return this.gameOver;}
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
    @Override
    public void toggleUpPressed() {
        this.img = this.imgStates.get(1);
        this.UpPressed = true;
    }
    @Override
    public void toggleDownPressed() {
        //Nothing
        this.DownPressed = false;
    }
    @Override
    public void toggleRightPressed() {
        //Animate Engines left boost
        this.RightPressed = true;
    }
    @Override
    public void toggleLeftPressed() {
        //Animate Engines right boost
        this.LeftPressed = true;
    }
    @Override
    public void unToggleUpPressed() {
        this.img = this.imgStates.get(0);
        this.UpPressed = false;
    }
    @Override
    public void unToggleDownPressed() {
        this.DownPressed = false;
    }
    @Override
    public void unToggleRightPressed() {
        //Normal Image
        this.RightPressed = false;
    }
    @Override
    public void unToggleLeftPressed() {
        //Normal image
        this.LeftPressed = false;
    }
    @Override
    public void unToggleShootPressed(){
        //launch
        this.ShootPressed = false;
    }
    @Override
    public void toggleShootPressed(){ this.ShootPressed = true;}
}
