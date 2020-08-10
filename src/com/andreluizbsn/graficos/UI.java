package com.andreluizbsn.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.andreluizbsn.entities.Coin;
import com.andreluizbsn.main.Game;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(8, 1, 100, 12);
		g.setColor(new Color(0, 89, 0));
		g.fillRect(8, 1, (int) ((Game.player.life/100d) * 100), 12);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString(((int)Game.player.life + "/100").toString(),45,11);
		
		g.drawImage(Coin.SPRITE, ( Game.WIDTH * Game.SCALE ) - 70, 2, null);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(String.valueOf(Game.player.coins), ( Game.WIDTH * Game.SCALE ) - 50,17);
	}
	
}
