package com.andreluizbsn.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.andreluizbsn.graficos.Spritesheet;

public class Menu {
	
	public static String[] options = {
			"New Game",
			"Load Game",
			"Exit"
	};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public boolean up, down, enter;
	private BufferedImage image = new Spritesheet("/logo.png").getSprite(0, 0, 320, 156);
	private BufferedImage imageBack = new Spritesheet("/menuback.png").getSprite(0, 0, 672, 420);
	
	public static boolean pause = false, saveExists = false, saveGame = false;
	
	private static String saveFileName = "save.zelda";
	
	public static boolean hasSaveGame = false;
	
	public void tick() {
		

		if ( Game.isSound )
			Sound.menu.loop();
		
		File file = new File(saveFileName);
		saveExists = file.exists();
		
		if ( up ) {
			currentOption--;
			if ( ! hasSaveGame ) {
				currentOption = 0;
			}
			
			if ( currentOption < 0 ) {
				currentOption = maxOption;
			}
			up = false;
		} else if ( down ) {
			currentOption++;
			if ( ! hasSaveGame ) {
				currentOption = maxOption;
			}
			
			if ( currentOption > maxOption ) {
				currentOption = 0;
			}
			down = false;
		}
		
		if ( enter ) {
			enter = false;
			if ( options[currentOption].equals("New Game") || options[currentOption].equals("Continue") ) {
				Game.state = "NORMAL";
				file = new File(saveFileName);
				file.delete();
				if ( Game.isSound )
					Sound.menu.stop();
			} else if ( options[currentOption].equals("Load Game") ) {
				
				file = new File(saveFileName);
				
				if ( file.exists() ) {
					String saver = loadGame(10);
					applySave(saver);
				}
				
			} else if ( options[currentOption].equals("Exit") ) {
				System.exit(1);
			}
		}
		
	}
	
	public static void applySave( String str ) {
		
		String[] spl = str.split("/");
		
		String world = "level1.png";
		boolean hasGun = false;
		int ammo = 0, curLevel = 1;
		double life = 100;
		
		for ( int i = 0; i < spl.length; i++ ) {
			String[] spl2 = spl[i].split(":");
			switch ( spl2[0] ) {
			case "level":
				world = "level"+spl2[1]+".png";
				curLevel = Integer.parseInt(spl2[1]);
				break;
			case "weapom":
				hasGun = spl2[1].equals("1") ? true : false;
				break;
			case "ammo":
				ammo = Integer.parseInt(spl2[1]);
				break;
			case "life":
				life = Double.parseDouble(spl2[1]);
				break;
			default:
				break;
			}
		}
		
		System.out.println(world + " " + life + " " + hasGun + " " + ammo);
		
		Game.newGame( world, life, hasGun, ammo );
		Game.setCurLevel(curLevel);
		Game.state = "NORMAL";
		pause = false;
		if ( Game.isSound )
			Sound.menu.stop();
	}
	
	public static String loadGame( int encode ) {
		
		String line = "";
		
		File file = new File(saveFileName);
		
		if ( file.exists() ) {
			try {
				String singleLine = null;
				BufferedReader bf = new BufferedReader(new FileReader(saveFileName));
				
				try {
					while ( ( singleLine = bf.readLine() ) != null ) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for ( int i = 0; i < val.length; i++ ) {
							val[i]-= encode;
							trans[1]+= val[i];
						}
						line+=trans[0]+":"+trans[1]+"/";
					}
				} catch ( IOException e ) {
					// TODO: handle exception
				}
				
			} catch ( FileNotFoundException e ) {
				// TODO: handle exception
			}
		}
		
		return line;
	}
	
	public static void saveGame ( String[] val1, int[] val2, int encode ) {
		BufferedWriter bf = null;
		
		try {
			bf = new BufferedWriter(new FileWriter(saveFileName));
		} catch ( IOException e ) {}
		
		for ( int i = 0; i < val1.length; i++ ) {
			String current = val1[i]+":";
			
			char[] value = Integer.toString(val2[i]).toCharArray();
			
			for ( int j = 0; j < value.length; j++ ) {
				value[j]+= encode;
				current+=value[j]; 
			}
			
			try {
				bf.write(current);
				if ( i < val1.length - 1 )
					bf.newLine();
			} catch ( IOException e ) {}
			
		}
		
		try {
			bf.flush();
			bf.close();
		} catch ( IOException e ) {}
		
		
		
		
		
	}
	
	public void render( Graphics g ) {
		
		File file = new File(saveFileName);
		
		hasSaveGame = file.exists();
		
		g.setColor(Color.black);
		/*if ( Game.isFullscreen ) {
			g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g.drawImage(imageBack, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
			g.drawImage(image, Toolkit.getDefaultToolkit().getScreenSize().width / 4, 0, 720, 450, null);
			
			g.setColor(Color.black);
			//g.setFont(new Font("arial", Font.BOLD, 15));
			g.setFont(CustomFont.getFont(30));
			if ( options[0].equals("Continue") )
				g.drawString(options[0], Toolkit.getDefaultToolkit().getScreenSize().width / 2, 500);
			else
				g.drawString(options[0], Toolkit.getDefaultToolkit().getScreenSize().width / 2, 500);
			
			if ( hasSaveGame ) {
				g.drawString(options[1], Toolkit.getDefaultToolkit().getScreenSize().width / 2, 550);
			} else {
				g.setColor(Color.gray);
				g.drawString(options[1], Toolkit.getDefaultToolkit().getScreenSize().width / 2, 550);
				g.setColor(Color.black);
			}
			g.drawString(options[2], Toolkit.getDefaultToolkit().getScreenSize().width / 2, 600);
			
			if ( options[currentOption].equals("New Game") || options[currentOption].equals("Continue") ) {
				g.drawString(">", Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 30, 500);
			} else if ( options[currentOption].equals("Load Game") ) {
				g.drawString(">", Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 30, 550);
			} else if ( options[currentOption].equals("Exit") ) {
				g.drawString(">", Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 30, 600);
			}
		} else {*/
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.drawImage(imageBack, 0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE, null);
			g.drawImage(image, 70, 0, 320, 156, null);
			g.setColor(Color.black);
			//g.setFont(new Font("arial", Font.BOLD, 15));
			g.setFont(CustomFont.getFont(15));
			/*
			g.drawString(">Andre Game Zelda<", 100, 40);
			*/
			if ( options[0].equals("Continue") )
				g.drawString(options[0], 205, 160);
			else
				g.drawString(options[0], 200, 160);
			
			if ( hasSaveGame ) {
				g.drawString(options[1], 198, 180);
			} else {
				g.setColor(Color.gray);
				g.drawString(options[1], 198, 180);
				g.setColor(Color.black);
			}
			g.drawString(options[2], 220, 200);
			
			if ( options[currentOption].equals("New Game") || options[currentOption].equals("Continue") ) {
				g.drawString(">", 190, 160);
			} else if ( options[currentOption].equals("Load Game") ) {
				g.drawString(">", 190, 180);
			} else if ( options[currentOption].equals("Exit") ) {
				g.drawString(">", 190, 200);
			}
		//}
		
		
	}

}
