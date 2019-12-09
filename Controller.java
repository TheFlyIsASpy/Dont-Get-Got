/*
Name: Nicolas Sheridan
Date: 10/19/19
Assignment Desc: Creates a graphical program which depicts robbers being *chased* by the police after running from a bank robbery. 
This is accomplished by using java swing and awt libraries.
*/

import java.awt.Graphics;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



class Controller implements MouseListener, KeyListener
{
    Model model;
    View view;
    Player player;
    GameThread gt;
    Random rand = new Random();
    int frame = 0;
    int timer = 0;
    int change = 3;
    int chance = 10*144;

    Map<String, Boolean> keyStatus = new HashMap<String, Boolean>();

    Controller() throws IOException, Exception {
        this.player = new Player(500,500,100);
        keyStatus.put("w", false);
        keyStatus.put("a", false);
        keyStatus.put("s", false);
        keyStatus.put("d", false);
        this.gt = new GameThread(this);
        this.model = new Model(player, gt);
        this.view = new View(this, model);
        model.setView(view);
        this.gt.start();
    }

    public int getTime(){
        return this.timer;
    }
    public int getChance(){
        return this.chance;
    }
    public void setTime(int t){
        this.timer = t;
    }
    public void setChance(int c){
        this.chance = c;
    }
    public void setPlayer(Player p){
        this.player = p;
    }
    public void pauseGame(){
        gt.pauseGame();
    }

    public void resumeGame(){
        gt.resumeGame();
    }

    public void resetGameThread(){
        gt.reset();
    }

    public void reset(){
        gt.pauseGame();
        this.player = new Player(500,500,100);
        this.frame = 0;
        this.timer = 0;
        this.change = 3;
        this.chance = 10*144;
        model.reset(player);
        gt.reset();
    }

    public void setGameThread(GameThread gt){
        this.gt = gt;
    }
    public void update(Graphics g) {
        model.update(g);
    }

    public void updateGame(){
        if(frame % 144 == 0){
            frame = 0;
            timer++;
            chance -= change;
            model.spawnZombies();
            view.score(timer);
        }
        if(chance <= 48){
            chance = 48;
            change = 0;
        }
        if(rand.nextInt(chance)==0){
            model.createSprite(new Structure(rand.nextInt((990 - 10) + 1), rand.nextInt((990 - 10) + 1), 100));
        }
        if(keyStatus.get("w"))
            player.move(1,0,-1);
        if(keyStatus.get("a"))
            player.move(1,-1,0);
        if(keyStatus.get("s"))
            player.move(1,0,1);
        if(keyStatus.get("d"))
            player.move(1,1,0);
        model.updateScene(player);
        view.repaint();
        frame++;
    }

    public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
            Shot s = new Shot(e.getX() - player.getX(), e.getY() - player.getY(), player.getX(), player.getY());
            model.createSprite(s);
		} else if (SwingUtilities.isRightMouseButton(e))  {
		}
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            keyStatus.put("w", true);
        }else if(e.getKeyChar() == 'a'){
            keyStatus.put("a", true);
        }else if(e.getKeyChar() == 's'){
            keyStatus.put("s", true);
        }else if(e.getKeyChar() == 'd'){
            keyStatus.put("d", true);
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            keyStatus.put("w", false);
        }else if(e.getKeyChar() == 'a'){
            keyStatus.put("a", false);
        }else if(e.getKeyChar() == 's'){
            keyStatus.put("s", false);
        }else if(e.getKeyChar() == 'd'){
            keyStatus.put("d", false);
        }
    };
    
    public void keyTyped(KeyEvent e){
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public static void main(String[] args) throws Exception {
        new Controller();
    }
}

