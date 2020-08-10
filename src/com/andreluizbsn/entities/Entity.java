package com.andreluizbsn.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Random;

import com.andreluizbsn.main.Game;
import com.andreluizbsn.world.Camera;
import com.andreluizbsn.world.World;

public class Entity {
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected double speed;
	
	public int depth;
	
	public boolean debug = false;
	
	protected BufferedImage sprite;
	
	private int maxFrames = 4, frames = 0;
	public int curAnimation = 0, maxAnimation = 4;
	
	public static Random rand = new Random();
	
	public Entity(double x,double y,int width,int height,double speed,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH* getWidth() - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*getHeight() - Game.HEIGHT);
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick(){}
	
	public double calculateDistance(int x1,int y1,int x2,int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	/*
	public void followPatha(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}*/
	
	public static boolean isColidding(Entity e1,Entity e2){
		
		int[] pixels1 = new int[ e1.sprite.getWidth() * e1.sprite.getHeight() ];
		int[] pixels2 = new int[ e2.sprite.getWidth() * e2.sprite.getHeight() ];
		
		e1.sprite.getRGB(0, 0, e1.sprite.getWidth(), e1.sprite.getHeight(), pixels1, 0, e1.sprite.getWidth());
		e2.sprite.getRGB(0, 0, e2.sprite.getWidth(), e2.sprite.getHeight(), pixels2, 0, e2.sprite.getWidth());
		
		for ( int xx1 = 0; xx1 < e1.sprite.getWidth(); xx1++ ) {
			for ( int yy1 = 0; yy1 < e1.sprite.getHeight(); yy1++ ) {
				for ( int xx2 = 0; xx2 < e2.sprite.getWidth(); xx2++ ) {
					for ( int yy2 = 0; yy2 < e2.sprite.getHeight(); yy2++ ) {
						int pixelAtual1 = pixels1[xx1+yy1*e1.sprite.getWidth()];
						int pixelAtual2 = pixels2[xx2+yy2*e2.sprite.getWidth()];
						
						if ( pixelAtual1 == 0x00FFFFFF || pixelAtual2 == 0x00FFFFFF ) {
							continue;
						}
						
						if ( xx1+e1.getX() == xx2+e2.getX() && yy1+e1.getY() == yy2+e2.getY() ) {
							return true;
						}
					}	
				}		
			}	
		}
		
		return false;
		/*
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);*/
	}
	
	public static boolean isColiddingOld(Entity e1,Entity e2){
		
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		/*
		g.setColor(Color.red);
		g.fillRect(this.getX() - Camera.x,this.getY() - Camera.y, 16, 16);*/
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
	public void animation(int maxAnimation) {
		frames ++;
		if ( frames > maxFrames ) {
			frames = 0;
			curAnimation++;
			if ( curAnimation >= maxAnimation ) {
				curAnimation = 0;
			}
		}		
	}
	
}
