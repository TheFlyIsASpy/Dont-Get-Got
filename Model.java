/*
Name: Nicolas Sheridan
Date: 10/19/19
Assignment Desc: Creates a graphical program which depicts robbers being *chased* by the police after running from a bank robbery. 
This is accomplished by using java swing and awt libraries.
*/

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class Model
{
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	private ArrayList<Structure> structures = new ArrayList<Structure>();
	private View v;
	private GameThread gt;

    Model(Player player, GameThread gt) throws IOException {
		this.gt = gt;
		synchronized(sprites){
	    	this.sprites.add(player);
		}
    }

	public void setView(View v){
		this.v = v;
	}
	
	public void overWrite(ArrayList<Sprite> sprites, Player p){
		this.shots = new ArrayList<Shot>();
		this.zombies = new ArrayList<Zombie>();
		this.structures = new ArrayList<Structure>();
		this.sprites = sprites;
		this.sprites.add(p);
		Iterator i = this.sprites.iterator();
		while(i.hasNext()){
			Sprite s = (Sprite)i.next();
			if(s instanceof Zombie){
				zombies.add((Zombie)s);
			}else if(s instanceof Structure){
				structures.add((Structure)s);
			}else if(s instanceof Shot){
				shots.add((Shot)s);
			}
		}
		this.updateScene(p);
		v.repaint();
	}

	public void reset(Player p){
		sprites = new ArrayList<Sprite>();
		shots = new ArrayList<Shot>();
		zombies = new ArrayList<Zombie>();
		structures = new ArrayList<Structure>();
		sprites.add(p);
		v.reset();
	}
    public void createSprite(Sprite e){
		synchronized(sprites){
			this.sprites.add(e);
			if(e instanceof Zombie){
				zombies.add((Zombie)e);
			}else if(e instanceof Shot){
				shots.add((Shot)e);
			}else if(e instanceof Structure){
				structures.add((Structure)e);
			}
		}
    }
	
	public ArrayList<Sprite> getSprites(){
		return sprites;
	}

	public void spawnZombies(){
		Iterator i = structures.iterator();
		while(i.hasNext()){
			Structure s = (Structure)i.next();
			this.createSprite(new Zombie(s.getX(), s.getY(), 100));
		}
	}

    public void initialize(){
		synchronized(sprites){
			this.sprites = new ArrayList<Sprite>();
			sprites.add(new Player(500,500,100));
		}
	}
		
	public void update(Graphics g) {
		synchronized(sprites){
			for(Sprite s : sprites){
				s.updateImages(g);
			}
		}
    }


    public void updateScene(Player player){
		synchronized(sprites){
			Iterator i = zombies.iterator();
			while(i.hasNext()){
				Zombie z = (Zombie)i.next();
				if(z.overlaps(player)){
					gt.killed();
					v.killed();
				}
			}
			i = shots.iterator();
			while(i.hasNext()){
				boolean removed = false;
				Shot s = (Shot)i.next();
				Iterator i2 = zombies.iterator();
				while(i2.hasNext()){
					Zombie z = (Zombie)i2.next();
					if(s.overlaps(z)){
						if(z.hit()){
							i2.remove();
							sprites.remove(z);
							v.score(100);
						}
						if(!removed){
							i.remove();
							sprites.remove(s);
							removed = true;
						}
					}
				}
				i2 = structures.iterator();
				while(i2.hasNext()){
					Structure struct = (Structure)i2.next();
					if(s.overlaps(struct)){
						if(struct.hit()){
							i2.remove();
							sprites.remove(struct);
							v.score(200);
						}
						if(!removed){
							i.remove();
							sprites.remove(s);
						}
					}
				}
			}
			i = sprites.iterator();
			while(i.hasNext()){
				Sprite s = (Sprite)i.next();
				if(s instanceof Zombie){
					Zombie z = (Zombie)s;
					z.updateState(player);
				}else if(s instanceof Shot){
					Shot shot = (Shot)s;
					shot.updateState();
				}else if(s instanceof Player){
					Player p = (Player)s;
					p.updateState();
				}else if(s instanceof Structure){
					Structure struct = (Structure)s;
					struct.updateState();
				}else
					s.updateState();
			}
		}
    }
}
