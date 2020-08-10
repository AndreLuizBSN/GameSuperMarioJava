package com.andreluizbsn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.andreluizbsn.main.Game;

public class Ladder extends Entity{
	
	public static BufferedImage LADDER = Game.spritesheet.getSprite(64,16, Game.basex, Game.basey);
	public static BufferedImage BAR = Game.spritesheet.getSprite(80,16, Game.basex, Game.basey);
	public static BufferedImage WALL_BAR_COMPLETE = Game.spritesheet.getSprite(112,16, Game.basex, Game.basey);
	public static BufferedImage WALL_BAR = Game.spritesheet.getSprite(96,16, Game.basex, Game.basey);
	public static BufferedImage LADDER_ROCK = Game.spritesheet.getSprite(128,16, Game.basex, Game.basey);
	
	/*
	 * 0 - ladder
	 * 1 - upbar 
	 * 2 - WALL BAR
	 * 3 - WALL BAR complete 
	 * 4 - LADDER ROCK 
	 * */
	private int typeFormat = 0;

	public Ladder ( double x, double y, int width, int height, double speed, BufferedImage sprite ) {
		super(x, y, width, height, speed, sprite);
	}
	
	public int getTypeFormat () {
		return typeFormat;
	}
	
	public void setTypeFormat ( int typeFormat ) {
		this.typeFormat = typeFormat;
	}

	public void render(Graphics g) {
		/*
		g.setColor(Color.red);
		g.fillRect(this.getX() - Camera.x,this.getY() - Camera.y, 16, 16);*/
		super.render(g);
		//g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
}
