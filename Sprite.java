/*
Name: Nicolas Sheridan
Date: 10/19/19
Assignment Desc: Creates a graphical program which depicts robbers being *chased* by the police after running from a bank robbery. 
This is accomplished by using java swing and awt libraries.
*/

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


class Sprite
{
	private String jpgName;
	private int locationX;
	private int locationY;
	private Image image;

	public Sprite(String jpgName)
	{
		setImage(jpgName);
		locationX = 500;
		locationY = 500;
	}
	
	public int getX() {	return locationX; }
	public int getY() {	return locationY; }
	public void setX(int x) { locationX = x; }
	public void setY(int y) { locationY = y; }
	
	public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	public Image getImage() { return image; }	
	
	public void updateImages(Graphics g) {
        // Move the sprite
		g.drawImage(getImage(), getX(), getY(), 20, 20, null);
	}

    public void updateState(){
	
    }

    public boolean overlaps(Sprite s){
		int x = s.getX();
		int y = s.getY();
		if(locationX >= x && locationX <= x+20){
			if(locationY >= y && locationY <= y+20){
				return true;
			}else if(locationY+20 >= y && locationY+20 <= y+20){
				return true;
			}
		}else if(locationX+20 >= x && locationX+20 <= x+20){
			if(locationY >= y && locationY <= y-20){
				return true;
			}else if(locationY+20 >= y && locationY+20 <= y+20){
				return true;
			}
		}
		return false;
    }
    
}
