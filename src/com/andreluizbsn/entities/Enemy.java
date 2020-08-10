package com.andreluizbsn.entities;

import java.awt.image.BufferedImage;

import com.andreluizbsn.main.Game;
import com.andreluizbsn.world.World;

public class Enemy extends Entity {
	
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(32, 16, Game.basex, Game.basey);
	
	public boolean right = true, left = false;
	
	public int life = 4;

	public Enemy ( double x, double y, int width, int height, double speed, BufferedImage sprite ) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void tick () {
		if ( World.isFree((int) x, (int) (y + 1))) {
			y+=1;
		}
		
		if ( right && World.isFree((int) (x+speed), (int) y)) {
			x+=speed;
			if ( World.isFree((int) (x + width), (int) y + 1) || ! World.isFree((int) (x + speed), (int) y)) {
				right = false;
				left = true;
			}
		} else if ( left && World.isFree((int) (x-speed), (int) y)  ) {
			x-=speed;
			if ( World.isFree((int) (x - width), (int) y + 1) || ! World.isFree((int) (x - speed), (int) y)) {
				right = true;
				left = false;
			}
		} else if ( right ) {
			right = false;
			left = true;
		} else if ( left) {
			right = true;
			left = false;
		}
		
	}
	
/*
	public void render(Graphics g) {
		sprite = this.ENEMY;
		
		super.render(g);
	}*/
}
