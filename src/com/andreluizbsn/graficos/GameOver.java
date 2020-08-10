package com.andreluizbsn.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver {
	
	public void render( Graphics g ) {
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Game Over!!!",50,50);
	}

}
