import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuBar extends JMenuBar implements ActionListener{

    private boolean paused = false;
    private Controller c;
    private View v;
    private Model m;

    public MenuBar(Controller c, View v, Model m){
        this.c = c;
        this.v = v;
        this.m = m;
        JMenu f = new JMenu("File");
        JMenuItem pause = new JMenuItem("Pause");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem instr = new JMenuItem("Instructions");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem exit = new JMenuItem("Exit");
        pause.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        instr.addActionListener(this);
        reset.addActionListener(this);
        exit.addActionListener(this);
        add(pause);
        add(save);
        add(load);
        add(instr);
        add(reset);
        add(exit);
    }

    public void actionPerformed(ActionEvent e)
    { 
        String com = e.getActionCommand();
        if (com.equals("Pause")){
            if(paused){
                c.resumeGame();
                paused = false;
            }else{
                c.pauseGame();
                paused = true;
            }
        }
        if(com.equals("Save")) {
            try{
                File file = new File("save.txt");
                if(file.createNewFile()){
                }else{
                    if(file.delete()){
                        file.createNewFile();
                    }else{
                    }
                }
                FileOutputStream fop = new FileOutputStream(file);
                ArrayList<Sprite> sprites = m.getSprites();
                Iterator i = sprites.iterator();
                while(i.hasNext()){
                    Sprite s = (Sprite)i.next();
                    String content = "";
                    if(s instanceof Player){
                        Player p = (Player)s;
                        content = "Player " + p.getX() + " " + p.getY() + " " + p.getHealth() + " ";
                    }else if(s instanceof Zombie){
                        Zombie z = (Zombie)s;
                        content = "Zombie " + z.getX() + " " + z.getY() + " " + z.getHealth() + " ";
                    }else if(s instanceof Structure){
                        Structure struct = (Structure)s;
                        content = "Structure " + struct.getX() + " " + struct.getY() + " " + struct.getHealth() + " ";
                    }else if(s instanceof Shot){
                        Shot shot = (Shot)s;
                        content = "Shot "  + shot.getXR() + " " + shot.getYR() + " " + shot.getX() + " " + shot.getY() + " " + shot.getHealth() + " ";
                    }
                    byte[] byteContent = content.getBytes();
                    fop.write(byteContent);
                }
                String score = "Score " + v.getScore() + " ";
                String timer = "Timer " + c.getTime() + " ";
                String chance = "Chance " + c.getChance() + " ";
                byte[] byteContent = score.getBytes();
                fop.write(byteContent);
                byteContent = timer.getBytes();
                fop.write(byteContent);
                byteContent = chance.getBytes();
                fop.write(byteContent);
                fop.close();  
            }catch(IOException ex){
            }

        } 
        if(com.equals("Load")) {
            c.pauseGame();
            File f = new File("save.txt");
            Scanner sc = null;
            try{
                sc = new Scanner(f);
            }catch(FileNotFoundException ex){
            }
            ArrayList<Sprite> sprites = new ArrayList<Sprite>();
            Player player = null;
            v.reset();
            while(sc.hasNext()){
                String s = sc.next();
                if(s.equals("Player")){
                    player = new Player(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), Float.parseFloat(sc.next()));
                }else if(s.equals("Zombie")){
                    sprites.add(new Zombie(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), Float.parseFloat(sc.next())));
                }else if(s.equals("Structure")){
                    sprites.add(new Structure(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), Float.parseFloat(sc.next())));
                }else if(s.equals("Shot")){
                    sprites.add(new Shot(Float.parseFloat(sc.next()), Float.parseFloat(sc.next()), Integer.parseInt(sc.next()), Integer.parseInt(sc.next())));
                }else if(s.equals("Score")){
                    v.setScore(Integer.parseInt(sc.next()));
                }else if(s.equals("Timer")){
                    c.setTime(Integer.parseInt(sc.next()));
                }else if(s.equals("Chance")){
                    c.setChance(Integer.parseInt(sc.next()));
                }
            }
            c.setPlayer(player);
            m.overWrite(sprites, player);
            c.resetGameThread();

        } 
        if(com.equals("Instructions")){
            
        }
        if(com.equals("Reset")){
            c.reset();
        }
        if(com.equals("Exit")){
            System.exit(0);
        }
    }

}