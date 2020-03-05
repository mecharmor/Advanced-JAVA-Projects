package GameObject;

import Manager.GameManager;
import Tables.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {
    /***
     * Do NOT call init if the game object is contained within another game object. This will cause Errors and unexpected results
     */
    public abstract void publishToGameManager(GameManager gm);//Adds itself to the GameManager ArrayLists
    public abstract void subscribe(GameObject go);//Think of this as a linker between parent and child GameObjects [Allows Coordinates to be Synced]
    public abstract void stop();
    /***
     * Components
     */
    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected int angle;
    protected Rectangle hitBox;
    protected boolean borderHit;
    protected int speed;
    protected int health;
    protected int lives;
    /***
     * KeyPress Detection
     */
    protected boolean UpPressed;
    protected boolean DownPressed;
    protected boolean RightPressed;
    protected boolean LeftPressed;
    protected boolean ShootPressed;
    /***
     * References
     */
    protected  GameManager gm;
    protected BufferedImage img;
    protected ArrayList<BufferedImage> imgStates;
    /***
     * State of Object
     */
    protected boolean isCollidable;
    protected boolean isDrawable;
    protected boolean isMovable;
    /***
     * Constants
     */
    protected final int R = 2;
    protected final int ROTATIONSPEED = 2;
    /***
     * Default Values for Constructor:
     * x = half screen width
     * y = half screen height
     * vx = 0
     * vy = 0
     * angle = 0
     * img = wall_40x40
     * speed = 1
     * drawable = true
     * collidable = false
     * lives = 4
     * health = 100
     * hitBox = width and height of wall_40x40
     */
    GameObject(int x, int y, String img){
        this(x,y,0,0,0, img);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img){
        this(x,y,vx,vy,angle, img, 1);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img, int speed){
        this(x,y,vx,vy,angle,img,speed, true);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img, int speed, boolean drawable){
        this(x,y,vx,vy,angle,img,speed,drawable,false);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img, int speed, boolean drawable, boolean collidable){
        this(x,y,vx,vy,angle,img,speed,drawable,collidable,4);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img, int speed, boolean drawable, boolean collidable, int lives){
        this(x,y,vx,vy,angle,img,speed,drawable,collidable,lives, 100);
    }
    GameObject(int x, int y, int vx, int vy, int angle, String img, int speed, boolean drawable, boolean collidable, int lives, int health){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.imgStates = SpriteTable.getImgStates(img);
        this.img = this.imgStates.get(0);
        this.speed = speed;
        this.isDrawable = drawable;
        this.isCollidable = collidable;
        this.lives = lives;
        this.health = health;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
    }
        protected void checkBorder() {
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
    protected void rotateLeft() {
        this.rotateLeft(1);
    }
    protected void rotateLeft(int multiplier) {
        this.angle -= speed*this.ROTATIONSPEED*multiplier;
        this.updateHitBox();
    }
    protected void rotateRight() {
        this.rotateRight(1);
    }
    protected void rotateRight(int multiplier) {
        this.angle += speed*this.ROTATIONSPEED*multiplier;
        this.updateHitBox();
    }
    protected void moveBackwards() {
        moveBackwards(1);
    }
    protected void moveBackwards(int multiplier){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= multiplier*speed*vx;
        y -= multiplier*speed*vy;
        checkBorder();
        this.updateHitBox();
    }
    protected void moveForwards() {
        this.moveForwards(1);
    }
    //Overloaded Method
    protected void moveForwards(double multiplier) {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += speed*vx*multiplier;
        y += speed*vy*multiplier;
        checkBorder();
        this.updateHitBox();
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    protected void updateHitBox(){
        hitBox.setLocation(x,y);
    }

    public boolean isDrawable(){
        return this.isDrawable;
    }
    public boolean isCollidable(){
        return this.isCollidable;
    }
    public Rectangle getBounds(){
        return this.hitBox.getBounds();
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
}
