package com.andreluizbsn.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import com.andreluizbsn.main.Game;

public class Tile {
	
	//public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(144,0, Game.basex, Game.basey);
	public static BufferedImage TILE_FLOOR_CERCADO = Game.spritesheet.getSprite(48,32, Game.basex, Game.basey);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16,0,16,16);
	public static BufferedImage TILE_WALL_CONTINUOUS = Game.spritesheet.getSprite(16,16, Game.basex, Game.basey);
	public static BufferedImage TILE_WALL_RIGHT = Game.spritesheet.getSprite(16,32, Game.basex, Game.basey);
	public static BufferedImage TILE_WALL_VERTICAL = Game.spritesheet.getSprite(16*4,32, Game.basex, Game.basey);
	
	public static BufferedImage WATERFALL_INITIAL = Game.spritesheet.getSprite(16,80, Game.basex, Game.basey);

	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x,int y,BufferedImage sprite){
		
		final int color = TILE_FLOOR.getRGB(0, 0);

	      final Image imageWithTransparency = makeColorTransparent(TILE_FLOOR, new Color(color));
	
	      TILE_FLOOR = imageToBufferedImage(imageWithTransparency);

		
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g){
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
	}
	
	//transparency
	private static BufferedImage imageToBufferedImage(final Image image)
	   {
	      final BufferedImage bufferedImage =
	         new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	      final Graphics2D g2 = bufferedImage.createGraphics();
	      g2.drawImage(image, 0, 0, null);
	      g2.dispose();
	      return bufferedImage;
	    }
	
	 public static Image makeColorTransparent(final BufferedImage im, final Color color)
	   {
	      final ImageFilter filter = new RGBImageFilter()
	      {
	         // the color we are looking for (white)... Alpha bits are set to opaque
	         public int markerRGB = color.getRGB() | 0xFFFFFFFF;

	         public final int filterRGB(final int x, final int y, final int rgb)
	         {
	            if ((rgb | 0xFF000000) == markerRGB)
	            {
	               // Mark the alpha bits as zero - transparent
	               return 0x00FFFFFF & rgb;
	            }
	            else
	            {
	               // nothing to do
	               return rgb;
	            }
	         }
	      };

	      final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	      return Toolkit.getDefaultToolkit().createImage(ip);
	   }


}
