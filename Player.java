import java.awt.Graphics;
public class Player extends Entity{

    public Player(int spawnX, int spawnY, float health){
		  super("player.png", health, spawnX, spawnY);
    }

    public void updateState(){
      super.updateState();
	  }

    public void updateImages(Graphics e){
		  super.updateImages(e);
    }

}