/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GameObject.*;// Import GameObject Package
import Helpers.GameManager;
import Helpers.PaintManager;
import Helpers.TankControl;
import Tables.ConfigTable;
import Tables.GameObjectTable;
import Tables.SpriteTable;
import javax.swing.JOptionPane;


import javax.swing.*;
import java.awt.*;

public class Game extends JPanel  {

    //Manages GameObjects
    private GameManager gameManager;
    //Manages Painting
    private PaintManager paintManager;
    //Game Flag
    boolean gameOver;

    private JFrame jf;

    public static void main(String[] args) {
        Thread x;
        Game game = new Game();
        SpriteTable.init();//Load images
        ConfigTable.init();//Set Config
        GameObjectTable.init();//Set reflection values
        game.init();//Init tables above here

        try {
            while (!game.gameOver) {
                game.checkCollisions();
                game.update();
                game.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {}
        JOptionPane.showMessageDialog(null,"The Game Has Ended");
    }
    private void update(){
        for(Observable observer : gameManager.getObservables()){
            if(observer.GameOver()){
                this.gameOver = true;
            }
            observer.update();//Update Coordinates for all observers
        }
    }
    private void syncCachedObjects(){
        gameManager.syncCachedObjects();
    }

    private void init() {
        this.jf = new JFrame("Tank Game");
        //Set Game Flag
        this.gameOver = false;

        //initialize Helpers
        this.gameManager = new GameManager();
        this.gameManager.init();
        this.paintManager = new PaintManager(this.gameManager);

        //Key Press Listeners Added
        for(TankControl tCtrl : this.gameManager.getObserverControls()){
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
    public void checkCollisions(){
        this.gameManager.checkCollisions();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        this.paintManager.paint(g);
        syncCachedObjects();
    }
}