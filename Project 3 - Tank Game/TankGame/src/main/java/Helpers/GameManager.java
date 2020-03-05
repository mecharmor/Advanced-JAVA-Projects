package Helpers;

import GameObject.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;

public class GameManager {

    private ArrayList<Drawable> drawables;
    private ArrayList<Collidable> collidables;
    private ArrayList<Observable> observables;

    //Used TO Merge Lists when objects are created
    private ArrayList<Collidable> collidablesCache;
    private ArrayList<Drawable> drawablesCache;
    //Memory Management
    private ArrayList<GameObject> gameObjectsMarkedForDeletion;

    //Unsure if Necessary
    private ArrayList<TankControl> observerControls;

    public void init(){
        //Initialize
        drawables = new ArrayList<>();
        collidables = new ArrayList<>();
        observables = new ArrayList<>();
        observerControls = new ArrayList<>();

        //Used so iterators will not break. the cache will merge into the main arraylists
        collidablesCache = new ArrayList<>();
        drawablesCache = new ArrayList<>();
        //marked for deletion [memory management]
        gameObjectsMarkedForDeletion = new ArrayList<>();

        /**
         * When Creating Game Object. Make sure to invoke it's publishToGameManager() method to add it to the proper ArrayList
         */

        this.drawables.add(new Background());
        (new MapLoader()).publishToGameManager(this);

        generateObservers();//Create Tanks
    }

    /***
     * ============================== Handling Collisions ======================================
     */
    public void checkCollisions(){
        /***
         * Nested Iterators for checking collidables.
         * Check if the objects are not the same so object does not collide with itself
         * check if both objects are in fact collidable
         */
        for(int i = 0; i < collidables.size(); i++){
            for(int j = 0; j < collidables.size(); j++){
                if(i!=j){
                    Collidable obj1 = collidables.get(i);
                    Collidable obj2 = collidables.get(j);
                    if(obj1.isCollidable() && obj2.isCollidable()){
                        if(obj1.getBounds().intersects(obj2.getBounds())){
                            obj1.handleCollision(obj2);
                            obj2.handleCollision(obj1);
                        }
                    }
                }
            }
        }
    }
    /***
     * ============================== Used to create Observers ======================================
     */
    public void generateObservers() {
        Tank tempObserver;

        //Tank 1 - Controls [Up, Down, Left, Right, Enter] {Enter shoots}
        tempObserver = createObserverHelper(200,200,0,0,0, "tank1");
        tempObserver.publishToGameManager(this);
        createObserverControlHelper(tempObserver, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);

        //Tank 2 - Controls [W,A,S,D,E] {E shoots}
        tempObserver = createObserverHelper(1760,150,0,0,0,"tank2");
        tempObserver.publishToGameManager(this);
        createObserverControlHelper(tempObserver, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

    }
    private Tank createObserverHelper(int x, int y, int vx, int vy, int angle, String img){
        Tank tank = new Tank(x, y, vx, vy, angle, img);
        return tank;
    }
    private void createObserverControlHelper(Tank tank, int forward, int back, int left, int right, int shoot){
        TankControl tCtrl = new TankControl(tank, forward, back, left, right, shoot);
        observerControls.add(tCtrl);
    }

    /***
     * ============================== Used to Access ArrayLists ======================================
     */

    /**
     *  append to ArrayLists of game objects --> This is used by GameObjects
     */
    public void addDrawable(Drawable dr){
        this.drawablesCache.add(dr);
    }
    public void addCollidable(Collidable cb){
        this.collidablesCache.add(cb);
    }
    public void addObservable(Observable obs){
        this.observables.add(obs);
    }
    /**
     *  get ArrayLists of game objects --> This is used by Game to update components and draw
     */
    public ArrayList<Drawable> getDrawables(){
        return this.drawables;
    }
    public ArrayList<Collidable> getCollidables(){
        return this.collidables;
    }
    public ArrayList<Observable> getObservables(){
        return this.observables;
    }
    public ArrayList<TankControl> getObserverControls() {
        return this.observerControls;
    }
    /***
     * ============================= Memory Management =====================================
     */
    public void syncCachedObjects(){
        if(!collidablesCache.isEmpty()){
            collidables.addAll(collidablesCache);
            collidablesCache.clear();
        }
        if(!drawablesCache.isEmpty()){
            drawables.addAll(drawablesCache);
            drawablesCache.clear();
        }
    }
//    public void removeUnnecessaryObjects(){
//        collidables.removeAll(gameObjectsMarkedForDeletion);
//        drawables.removeAll(gameObjectsMarkedForDeletion);
//        gameObjectsMarkedForDeletion.clear();
//    }
//    public void removeGameObject(GameObject go){
//        this.gameObjectsMarkedForDeletion.add(go);
//    }
}


