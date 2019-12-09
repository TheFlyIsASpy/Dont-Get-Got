import java.util.Random;
import java.awt.Graphics;

public class Structure extends Entity{

    public Structure(int spawnx, int spawny, float health){
		  super("lava.png", health, spawnx, spawny);
    }
    
    public void updateState(){
      //move the sprite or other things
      super.updateState();
	  }

    public void updateImages(Graphics e){
		  super.updateImages(e);
    }

}
