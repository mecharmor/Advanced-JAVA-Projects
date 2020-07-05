package GameObject;

import Helpers.GameManager;
import Tables.ConfigTable;
import Tables.SpriteTable;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    /***
     * Do NOT call init if the game object is contained within another game object. This will cause Errors and unexpected results
     */
    public abstract void publishToGameManager(GameManager gm);//Adds itself to the GameManager ArrayLists
    public abstract void subscribe(GameObject go);//Think of this as a linker between parent and child GameObjects [Where do you want this object in relation to the parent?]
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
     * References
     */
    protected  GameManager gm;
    protected BufferedImage img;
    /***
     * State of Object
     */
    protected boolean isCollidable;
    protected boolean isDrawable;
    /***
     * Constants
     */
    protected final int R = 2;
    protected final int ROTATIONSPEED = 4;
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
    GameObject(){
        this(ConfigTable.getConfig("MAP_WIDTH")/2, ConfigTable.getConfig("MAP_HEIGHT")/2, "wall_40x40");
    }
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
        this.img = SpriteTable.getBufferedImage(img);
        this.speed = speed;
        this.isDrawable = drawable;
        this.isCollidable = collidable;
        this.lives = lives;
        this.health = health;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
    }
        protected void checkBorder() {
        borderHit = false;
        if (x <= 30) {
            x = 30;
            borderHit = true;
        }
        if (x >= ConfigTable.getConfig("MAP_WIDTH") - 88) {
            x = ConfigTable.getConfig("MAP_WIDTH") - 88;
            borderHit = true;
        }
        if (y <= 40) {
            y = 40;
            borderHit = true;
        }
        if (y >= ConfigTable.getConfig("MAP_HEIGHT") - 80) {
            y = ConfigTable.getConfig("MAP_HEIGHT") - 80;
            borderHit = true;
        }
    }
    protected void rotateLeft() {
        this.angle -= speed*this.ROTATIONSPEED;
    }
    protected void rotateRight() {
        this.angle += speed*this.ROTATIONSPEED;
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
    }
    protected void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += speed*vx;
        y += speed*vy;
        checkBorder();
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
}
