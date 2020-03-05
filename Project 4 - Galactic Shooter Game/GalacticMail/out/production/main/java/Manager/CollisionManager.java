package Manager;

import GameObject.Collidable;
import GameObject.GameObject;

import java.util.ArrayList;

public class CollisionManager implements Manager{

    private ArrayList<Collidable> collidables;
    private ArrayList<Collidable> collidablesCache;
    private int flagsThrown;

    public CollisionManager(){
        collidables = new ArrayList<>();
        collidablesCache = new ArrayList<>();
        flagsThrown = 0;
    }
    public void stop(){
        for(Collidable collidable : collidables){
            ((GameObject) collidable).stop();
        }
        collidablesCache.clear();
        collidables.clear();
    }

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
    public void throwFlag(){
        this.flagsThrown++;
    }
    public int getThrownFlags(){
        return this.flagsThrown;
    }
    public void syncCachedObjects(){
        if(!collidablesCache.isEmpty()){
            collidables.addAll(collidablesCache);
            collidablesCache.clear();
        }
    }
    public ArrayList<Collidable> getCollidables(){
        return this.collidables;
    }
    public void addCollidable(Collidable cb){
        this.collidablesCache.add(cb);
    }

}
