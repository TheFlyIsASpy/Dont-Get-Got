import java.awt.Graphics;

public class Entity extends Sprite{

	private float health;
	
    public Entity(String img, float health, int spawnx, int spawny){
		super(img);
		super.setX(spawnx);
      	super.setY(spawny);
		this.health = health;
    }

	public float getHealth(){
		return health;
	}
    public void move(int distance, double xRatio, double yRatio){
		double slopeVectorDistance;
		slopeVectorDistance = Math.sqrt(Math.pow(xRatio, 2) + Math.pow(yRatio, 2));
		super.setX((int)(super.getX() + (distance * (xRatio/slopeVectorDistance))));
		super.setY((int)(super.getY() + (distance * (yRatio/slopeVectorDistance))));
    }

	public boolean hit(){
		this.health -= 50;
		if(health <= 0){
			return true;
		}
		return false;
	}

	public void updateState(){
		super.updateState();
	}

    public void updateImages(Graphics e){
	    // Move the sprite
	    super.updateImages(e);
    }

}
