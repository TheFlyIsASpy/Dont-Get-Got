import java.awt.Graphics;
public class Zombie extends Entity{

    public Zombie(int spawnx, int spawny, float health){
		  super("zombie.png", health, spawnx, spawny);
    }

    public void updateState(Player p){
      //move the sprite
      super.move(2, p.getX() - this.getX(), p.getY() - this.getY());
      super.updateState();
	  }

    public void updateImages(Graphics e){
		  // Move the sprite
		  super.updateImages(e);
    }

}
