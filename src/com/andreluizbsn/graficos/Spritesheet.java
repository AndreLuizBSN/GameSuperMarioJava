package com.andreluizbsn.graficos;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	{
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x,int y,int width,int height){
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	public double getRealWidth() {
		return spritesheet.getWidth();
	}
	
	public double getRealHeight() {
		return spritesheet.getHeight();
	}
	
	public static BufferedImage invert( BufferedImage bufferedImage ) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-bufferedImage.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    return op.filter(bufferedImage, null);
	}
	
}
