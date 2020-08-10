package com.andreluizbsn.entities;

import java.awt.image.BufferedImage;

import com.andreluizbsn.main.Game;

public class Coin extends Entity {

	public static final BufferedImage SPRITE = Game.spritesheet.getSprite(64, 0, Game.basex, Game.basey);

	public Coin ( double x, double y, int width, int height, double speed, BufferedImage sprite ) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}

}
