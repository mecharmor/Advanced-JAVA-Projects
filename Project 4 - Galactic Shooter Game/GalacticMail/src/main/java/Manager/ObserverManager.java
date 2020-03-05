package Manager;

import GameObject.GameObject;
import GameObject.Observable;
import Helpers.GameController;
import java.util.ArrayList;

public class ObserverManager implements Manager{

    private ArrayList<Observable> observables;
    private ArrayList<GameController> observerControls;
    private boolean gameOver;

    public ObserverManager(){
        observables = new ArrayList<>();
        observerControls = new ArrayList<>();
        this.gameOver = false;
    }
    public void stop(){
        for(Observable observer : observables){
            ((GameObject)observer).stop();
        }
        observables.clear();
        observerControls.clear();
    }
    public void updateObservers() {
        for (Observable observer : observables) {
            if (observer.GameOver()) {
                this.gameOver = true;
            }
            //Update Observer Coordinates
            observer.update();
        }
    }
    public void addObservable(Observable obs){
        this.observables.add(obs);
    }
    public void addObserverControls(GameController gc){
        observerControls.add(gc);
    }
    public ArrayList<GameController> getObserverControls() {
        return this.observerControls;
    }
    public boolean getGameOver(){
        return this.gameOver;
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
}
