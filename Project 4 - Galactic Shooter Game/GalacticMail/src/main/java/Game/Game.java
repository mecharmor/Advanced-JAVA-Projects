/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Helpers.GameController;
import Manager.GameManager;
import Tables.*;

import javax.swing.JOptionPane;


import javax.swing.*;
import java.awt.*;

public class Game extends JPanel  {

    //Manages GameObjects
    private GameManager gameManager;
    //Game Flag
    boolean gameOver;

    private JFrame jf;

    public static void main(String[] args) {
        AudioTable.init();//Load AudioInputStreams
        //Start Background music
        AudioTable.playContinuously("Background");
        do{
            Game game = new Game();

            game.init();
            try {
                while (!game.gameOver) {
                    game.checkCollisions();
                    game.update();
                    game.repaint();
                    Thread.sleep(1000 / 144);
                    game.jf.setTitle("Galactic Mail, Score: " + game.getGameScore());
                }
            } catch (InterruptedException ignored) {
            }
            //Check for new High Score
            if (game.isHighScore(game.getGameScore())) {
                game.insertNewHighScore(JOptionPane.showInputDialog("Congratulations you got a high score! Please enter your gamer tag"), game.getGameScore());
            }
            JOptionPane.showMessageDialog(null, "High Scores\n\n" + game.getHighScores());
            game.stop();
            game.jf.setVisible(false);
        }while(JOptionPane.showConfirmDialog(null,"Would You Like To Play Again?", "Continue Game?", JOptionPane.YES_NO_OPTION) == 0);
        AudioTable.stopALLContinuous();
        System.exit(0);
    }

    private void init() {
        this.jf = new JFrame("Galactic Mail, Score: " + 0);
        //Set Game Flag
        this.gameOver = false;

        //Create GameManager
        this.gameManager = new GameManager(this);
        /***
         * Initialize Tables
         */
        SpriteTable.init();//Load images
        AnimationTable.init(gameManager);//Save Generator reference to instantiate GameObject animations
        ConfigTable.init();//Set Config
        GameObjectReflectionTable.init();//Set reflection values
        HighScoresTable.init();//Load High Scores
        //Initialize Game Manager
        this.gameManager.init();
        //Key Press Listeners Added
        for(GameController tCtrl : this.gameManager.getObserverControls()){
            this.jf.addKeyListener(tCtrl);
        }
        //Set Jframe
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.setSize(ConfigTable.getConfig("SCREEN_WIDTH"), ConfigTable.getConfig("SCREEN_HEIGHT") + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }
    private void update(){
        this.gameManager.updateObservers();
        this.gameOver = this.gameManager.getGameOver();
        if(this.gameManager.allFlagsThrown()){
            this.gameManager.stop();
            this.gameManager.loadNextLevel();
        }
    }
    public void checkCollisions(){
        this.gameManager.checkCollisions();
    }

    public int getGameScore(){
        return gameManager.getGameScore();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        this.gameManager.paint(g);
        this.gameManager.syncCachedObjects();
    }
    public void addKeyListenerToJF(GameController ctrl){
        this.jf.addKeyListener(ctrl);
    }

    public boolean isHighScore(int score){
        return HighScoresTable.isHighScore(score);
    }
    public void insertNewHighScore(String name, int score){
        HighScoresTable.insertNewHighScore(name,score);
    }
    public String getHighScores(){
        return HighScoresTable.getHighScores();
    }
    public void stop(){
        this.gameManager.stop();
    }
}

/***
 REFERENCE!!!

-=-----------Timer in JAVA---------------
 (new Timer()).schedule(new TimerTask() {
@Override
public void run() {
canShootAgain();
}
}, 500);




 */