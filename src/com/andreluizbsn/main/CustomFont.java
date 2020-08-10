package com.andreluizbsn.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {
	
	
	public static Font getFont( float size ) {
		return new CustomFont().internalFont(size);
	}
		
	private Font internalFont( float size ) {
	
		//InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("/pixelart.ttf");
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
		
		
		Font newFont = null;
		
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
		} catch ( FontFormatException e ) {
			e.printStackTrace();
			newFont = new Font("arial", Font.BOLD, (int) size);
		} catch ( IOException e ) {
			e.printStackTrace();
			newFont = new Font("arial", Font.BOLD, (int) size);
		}
		
		return newFont;
		
	}
	
}
