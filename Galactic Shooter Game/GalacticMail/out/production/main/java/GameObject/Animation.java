package GameObject;

import Manager.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public class Animation extends GameObject implements Drawable{

    private int current;
    private Timer timer;
    int pause;

    public Animation(int x, int y, String animationName, int timeOfAnimationInMs){
        super(x,y,animationName);
        this.pause = timeOfAnimationInMs/this.imgStates.size();
        current = 0;
        this.startAnimation();
    }
    public void stop(){
        if(timer != null)
            timer.cancel();
    }
    private void startAnimation(){
        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextFrame();
            }
        }, 100,pause/imgStates.size());
    }
    private void nextFrame(){
        if(current < imgStates.size()){
            this.img = this.imgStates.get(current);
            current++;
        }else{
            current=0;
            this.timer.cancel();
            this.isDrawable = false;
        }
    }
    public void publishToGameManager(GameManager gm){
        gm.addDrawable(this);
    }
    public void subscribe(GameObject go){}

    public boolean isDrawable(){return this.isDrawable;}

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
