/*
Name: Nicolas Sheridan
Date: 10/19/19
Assignment Desc: Creates a graphical program which depicts robbers being *chased* by the police after running from a bank robbery. 
This is accomplished by using java swing and awt libraries.
*/

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class View extends JFrame implements ActionListener{

    private JLabel jl;
    private int score = 0;
    private boolean killed = false;
    private JLabel killed1;

    private class MyPanel extends JPanel {
        Controller controller;

        MyPanel(Controller c) {
            controller = c;
            addMouseListener(c);
        }

        public void paintComponent(Graphics g) {
            controller.update(g);
            revalidate();
        }
	
    }



    public View(Controller c, Model m) throws Exception{
        setTitle("Don't get got!");
        setSize(1000, 1000);
        this.jl = new JLabel("Score: 0");
        this.killed1 = new JLabel("I SAID NOT TO DO THAT!", SwingConstants.CENTER);
        MyPanel p = new MyPanel(c);
        MenuBar mb = new MenuBar(c, this, m);
        p.add(mb);
        p.add(jl);
        getContentPane().add(p);
	    addKeyListener(c);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    public void score(int num){
        this.score += num;
        jl.setText("Score: " + score);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int s){
        this.score = s;
    }

    public void reset(){
        score = 0;
        if(killed){
            killed = false;
            remove(killed1);
            revalidate();
        }
    }

    public void killed(){
        if(!killed){
            killed = true;
            add(killed1);
            revalidate();
        }
    }

}

