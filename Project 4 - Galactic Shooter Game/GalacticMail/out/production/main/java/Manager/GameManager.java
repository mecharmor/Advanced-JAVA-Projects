package Manager;

import Game.Game;
import GameObject.*;
import GameObject.Observable;
import Helpers.GameController;
import Helpers.GameObjectGenerator;
import Tables.AudioTable;
import Tables.ConfigTable;

import java.awt.*;
import java.util.*;

public class GameManager implements Manager{
    /**
     * Game Manager has the power to hold the scores
     * and end the game
     */
    private int gameScore;
    private int totalFlags;
    /**
     * Managers
     */
    private CollisionManager collisionManager;
    private ObserverManager observerManager;
    private PaintManager paintManager;
    /***
     * Game Object Generator Helper
     */
    private GameObjectGenerator gameObjectGenerator;
    private Game mainGame;

    public GameManager(Game game){
        mainGame = game;
    }

    public void init(){
        /***
         * Initialize Managers
         */
        collisionManager = new CollisionManager();
        observerManager = new ObserverManager();
        paintManager = new PaintManager();

        //Set default game score
        this.gameScore = ConfigTable.getConfig("GAME_SCORE");
        //Instantiate ArrayLists
        this.paintManager.addDrawable(new Background());
        this.gameObjectGenerator = new GameObjectGenerator(this);
        this.totalFlags = gameObjectGenerator.loadNextLevel();
    }
    public void addGameScore(int add){
        this.gameScore += add;
    }
    public int getGameScore(){
        return this.gameScore;
    }
    public void loadNextLevel(){
        //Reset
        collisionManager = new CollisionManager();
        observerManager = new ObserverManager();
        paintManager = new PaintManager();
        //Generate Objects
        this.paintManager.addDrawable(new Background());
        this.totalFlags = gameObjectGenerator.loadNextLevel();
    }
    /***
     * Observer Manager Methods
     */
    public void setGameOver(boolean temp){this.observerManager.setGameOver(temp);}
    public void updateObservers(){this.observerManager.updateObservers();}
    public void addObservable(Observable obs){
        this.observerManager.addObservable(obs);
    }
    public void addObserverControls(GameController gc){
        this.mainGame.addKeyListenerToJF(gc);
        this.observerManager.addObserverControls(gc);
    }
    public ArrayList<GameController> getObserverControls() {
        return this.observerManager.getObserverControls();
    }
    public boolean getGameOver(){return this.observerManager.getGameOver();}
    /***
     * Paint Manager Methods
     */
    public void addDrawable(Drawable dr){
        this.paintManager.addDrawable(dr);
    }
    public void paint(Graphics g) {
        this.paintManager.paint(g);
    }
    /***
     * Collision Manager Methods
     */
    public void throwFlag(){
        this.collisionManager.throwFlag();
    }
    public void addCollidable(Collidable cb){
        this.collisionManager.addCollidable(cb);
    }
    public void checkCollisions(){
        this.collisionManager.checkCollisions();
    }
    public boolean allFlagsThrown(){
        return (this.totalFlags == collisionManager.getThrownFlags());
    }
    /***
     * Synchronization
     */
    public void syncCachedObjects(){
        this.paintManager.syncCachedObjects();
        this.collisionManager.syncCachedObjects();
    }
    public void stop(){
        collisionManager.stop();
        observerManager.stop();
        paintManager.stop();
    }
}


