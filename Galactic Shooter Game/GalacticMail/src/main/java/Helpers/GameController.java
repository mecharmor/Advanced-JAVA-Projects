package Helpers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GameObject.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TankControl is in charge of invoking methods in an observer that it is attached to.
 */
public class GameController implements KeyListener {

    private GameObject go;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    
    public GameController(GameObject go, int up, int down, int left, int right, int shoot) {
        this.go = go;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("oof cheat codes?: " + ke.getKeyChar());
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.go.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.go.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.go.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.go.toggleRightPressed();
        }
        if (keyPressed == shoot){
            this.go.toggleShootPressed();
        }
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.go.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.go.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.go.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.go.unToggleRightPressed();
        }
        if(keyReleased == shoot){
            this.go.unToggleShootPressed();
        }
    }
}
