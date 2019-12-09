import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SurvivalGame implements KeyListener{
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){};
    
    public void keyTyped(KeyEvent e){ 
        if((int)e.getKeyChar() == 27){
            System.out.println("escape");
        }
        
        /*
        if(e.getKeyChar() == 'r'){
            model.initialize();
            view.repaint();
        }else if(e.getKeyChar() == 't'){
            SpriteMover sm = new SpriteMover(model, view);
            Thread t = new Thread(sm);
            t.start();
        }
        */
    }

    public static void main(String[] args) throws Exception {
        Controller c = new Controller();
        GameThread gtc = new GameThread(c);
        Thread gt = new Thread(gtc);
        gt.start();
    }
}