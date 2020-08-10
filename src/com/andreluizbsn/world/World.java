package com.andreluizbsn.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.andreluizbsn.entities.Coin;
import com.andreluizbsn.entities.Enemy;
import com.andreluizbsn.entities.Ladder;
import com.andreluizbsn.entities.LadderPos;
import com.andreluizbsn.entities.Player;
import com.andreluizbsn.entities.Water;
import com.andreluizbsn.graficos.Spritesheet;
import com.andreluizbsn.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	private Spritesheet background_frente;;
	private BufferedImage BACKGROUND_FRENTE;
	private Spritesheet background_tras;
	private BufferedImage BACKGROUND_TRAS;

	public World ( String path ) {
		background_frente = new Spritesheet("/fundo_frente.png");
		background_tras = new Spritesheet("/fundo_tras.png");
		BACKGROUND_FRENTE = background_frente.getSprite(0, 0, (int) background_frente.getRealWidth(), (int) background_frente.getRealHeight());
		BACKGROUND_TRAS = background_tras.getSprite(0, 0, (int) background_tras.getRealWidth(), (int) background_tras.getRealHeight());
						
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for ( int xx = 0; xx < map.getWidth(); xx++ ) {
				for ( int yy = 0; yy < map.getHeight(); yy++ ) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR);
					if ( pixelAtual == 0xFF000000 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR);
					} else if ( pixelAtual == 0xFFffffff ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL);
					} else if ( pixelAtual == 0xFFFFEEE8 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL_CONTINUOUS);
					} else if ( pixelAtual == 0xFFFFC8BC ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL_RIGHT);
					} else if ( pixelAtual == 0xFF5B2400 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL_VERTICAL);
					} else if ( pixelAtual == 0xFF0026FF ) { // player
						/*Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);*/
						Game.player = new Player( xx * Game.basex, yy * Game.basey,Game.basex,Game.basey,1.4,Player.PLAYER_SPRITE_R);
					} else if ( pixelAtual == 0xFFFF0000 ) { // Inimigo
						Enemy enemy = new Enemy(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0.5, Enemy.ENEMY);
						Game.entities.add(enemy);
					} else if ( pixelAtual == 0xFFFF6A00 ) { // coin
						Coin coin = new Coin(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 1, Coin.SPRITE);
						Game.entities.add(coin);
					} else if ( pixelAtual == 0xFF7F3300 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR_CERCADO);
					} else if ( pixelAtual == 0xFF007F0E ) {
						Ladder ladder = new Ladder(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Ladder.LADDER);
						ladder.setTypeFormat(0);
						Game.entities.add(ladder);
						Game.laddersPos.add( new LadderPos(xx, yy, ladder) );
					} else if ( pixelAtual == 0xFF00FF21 ) {
						Ladder ladder = new Ladder(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Ladder.BAR);
						ladder.setTypeFormat(1);
						Game.entities.add(ladder);
						Game.laddersPos.add( new LadderPos(xx, yy, ladder) );
					} else if ( pixelAtual == 0xFF267F00 ) {
						Ladder ladder = new Ladder(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Ladder.WALL_BAR_COMPLETE);
						ladder.setTypeFormat(3);
						Game.entities.add(ladder);
						Game.laddersPos.add( new LadderPos(xx, yy, ladder) );
					} else if ( pixelAtual == 0xFF4CFF00 ) {
						Ladder ladder = new Ladder(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Ladder.WALL_BAR);
						ladder.setTypeFormat(2);
						Game.entities.add(ladder);
						Game.laddersPos.add( new LadderPos(xx, yy, ladder) );
					} else if ( pixelAtual == 0xFF5B7F00 ) {
						Ladder ladder = new Ladder(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Ladder.LADDER_ROCK);
						ladder.setTypeFormat(4);
						Game.entities.add(ladder);
						Game.laddersPos.add( new LadderPos(xx, yy, ladder) );
					} else if ( pixelAtual == 0xFF777777 ) {
						Water water = new Water(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Water.CALM_WATER[0]);
						water.maxAnimation = 2;
						water.tileType = "CALM_WATER";
						Game.entities.add(water);
					} else if ( pixelAtual == 0xFF808080 ) {
						Water water2 = new Water(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Water.WATERFALL_CONT[0]);
						water2.maxAnimation = 2;
						water2.tileType = "WATERFALL_CONT";
						Game.entities.add(water2);
					} else if ( pixelAtual == 0xFF282828 ) {
						Water water3 = new Water(xx * Game.basex, yy * Game.basey, Game.basex, Game.basey, 0, Water.WATERFALL_FINAL[0]);
						water3.maxAnimation = 2;
						water3.tileType = "WATERFALL_FINAL";
						Game.entities.add(water3);
					} else if ( pixelAtual == 0xFF404040 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.WATERFALL_INITIAL);
					}
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static boolean isFree ( int xnext, int ynext ) {

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
		
	}

	public static void restartGame () {
		// TODO: Aplicar método para reiniciar o jogo corretamente.
		return;
	}

	public void render ( Graphics g ) {
		
		g.drawImage(BACKGROUND_TRAS, 0, 0, null);
		
		g.drawImage(BACKGROUND_FRENTE, 
				( (int) background_frente.getRealWidth()-(Game.WIDTH + ( (World.WIDTH * Game.basex) - (Camera.x + Game.WIDTH))))*-1,
				( (int) background_frente.getRealHeight()-(Game.HEIGHT + ( (World.HEIGHT * Game.basey) - (Camera.y + Game.HEIGHT))))*-1,
				null);
				
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		Tile tile;

		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);

		for ( int xx = xstart; xx <= xfinal; xx++ ) {
			for ( int yy = ystart; yy <= yfinal; yy++ ) {
				if ( xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT )
					continue;
				tile = tiles[xx + (yy * WIDTH)];
				if ( tile != null ) {
					//if ( ! tile.isWater ) {
						tile.render(g);
					/*} else {
						if ( tile.tileType.equals("CALM_WATER") ) {
							if ( tile.currentAnnimation < tile.maxAnnimation ) {
								tile = new FloorTile(xx, yy, Tile.CALM_WATER[tile.currentAnnimation]);
							} else {
								tile.currentAnnimation = 0;
								tile = new FloorTile(xx, yy, Tile.CALM_WATER[0]);
							}
						}
						tile.render(g);
					}*/
				}
			}
		}
	}
}
