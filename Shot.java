import java.awt.Graphics;
public class Shot extends Entity{

    private float xr;
    private float yr;

    public Shot(float xr, float yr, int spawnx, int spawny){
		  super("shot.png", 1, spawnx, spawny);
          this.xr = xr;
          this.yr = yr;
    }

    public float getXR(){
        return xr;
    }

    public float getYR(){
        return yr;
    }
    
    public void updateState(){
        super.move(3, xr, yr);
        super.updateState();
	}

    public void updateImages(Graphics e){
		  super.updateImages(e);
    }

}