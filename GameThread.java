/*
Name: Nicolas Sheridan
Date: 10/19/19
Assignment Desc: Creates a graphical program which depicts robbers being *chased* by the police after running from a bank robbery. 
This is accomplished by using java swing and awt libraries.
*/

public class GameThread extends Thread{
    private Controller c;
	private volatile boolean isPaused;
	private volatile boolean killed;

    public GameThread(Controller c){
		this.c = c;
		this.isPaused = false;
	}

    public void run(){
		while(true){
			try{
				checkIfPaused();
				checkIfKilled();
				c.updateGame();
				Thread.sleep(1000/144);
			}catch(InterruptedException e){}
		}
    }

	public void pauseGame(){
		isPaused = true;
	}

	public void killed(){
		killed = true;
	}

	public synchronized void resumeGame(){
		isPaused = false;
		notifyAll();
	}

	public synchronized void reset(){
		isPaused = false;
		killed = false;
		notifyAll();
	}

	public synchronized void checkIfPaused() throws InterruptedException{
			while(isPaused){
				wait();
			}
	}

	public synchronized void checkIfKilled() throws InterruptedException{
		while(killed){
			wait();
		}
	} 
}
