package com.andreluizbsn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.andreluizbsn.main.Game;

public class Water extends Entity {
	
	public int maxAnnimation = 0;
	public int currentAnnimation = 0;
	public String tileType = "";
	
	//public static BufferedImage WATERFALL_CONT = Game.spritesheet.getSprite(32,80, Game.basex, Game.basey);
	public static BufferedImage[] WATERFALL_FINAL = {
			Game.spritesheet.getSprite(48,80, Game.basex, Game.basey),
			Game.spritesheet.getSprite(48,96, Game.basex, Game.basey)
		};
	public static BufferedImage[] CALM_WATER = {
			Game.spritesheet.getSprite(0,80, Game.basex, Game.basey),
			Game.spritesheet.getSprite(0,96, Game.basex, Game.basey)
		}; 
	public static BufferedImage[] WATERFALL_CONT = {
			Game.spritesheet.getSprite(32,80, Game.basex, Game.basey),
			Game.spritesheet.getSprite(32,96, Game.basex, Game.basey)
		}; 

	public Water ( double x, double y, int width, int height, double speed, BufferedImage sprite ) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick () {
		animation(this.maxAnimation);
	}

	public void render ( Graphics g ) {
		if ( this.tileType.equals("CALM_WATER") ) {
			sprite = CALM_WATER[curAnimation];
		} else if ( this.tileType.equals("WATERFALL_CONT") ) {
			sprite = WATERFALL_CONT[curAnimation];
		} else if ( this.tileType.equals("WATERFALL_FINAL") ) {
			sprite = WATERFALL_FINAL[curAnimation];
		}   
			
		super.render(g);
	}
	
}
