package com.andreluizbsn.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.andreluizbsn.graficos.Spritesheet;
import com.andreluizbsn.main.Game;
import com.andreluizbsn.world.Camera;
import com.andreluizbsn.world.World;


public class Player extends Entity{
	
	public static BufferedImage PLAYER_SPRITE_R = Game.spritesheet.getSprite(0, 16, Game.basex, Game.basey);
	public static BufferedImage PLAYER_SPRITE_L = Game.spritesheet.getSprite(0, 32, Game.basex, Game.basey);
	
	public static BufferedImage PLAYER_SLIM = Game.spritesheet.getSprite(48, 48, Game.basex, Game.basey);
	public static BufferedImage PLAYER_ROLL = Game.spritesheet.getSprite(64, 48, Game.basex, Game.basey);
	
	public static BufferedImage[] PLAYER_SPRITE_L_MOVES = new BufferedImage[4];
	public static BufferedImage[] PLAYER_SPRITE_R_MOVES = new BufferedImage[4];
		
	public boolean right, left;
	//public double gravity = 2;
	public double gravity = 0.4, vspd = 0;
	public boolean isRight = true, jump = false, isJumping = false;
	public double jumpHeight = 40, jumpFrames = 0, jumpSpeed = 2;
	
	public double life = 100;
	public int coins = 0;
	public boolean isGetLadder = false;
	public boolean isLadder = false;
	public boolean isBarLadder = false;
	
	public boolean isInAnnimation = false;
	public double upLadderY = 0;
	public PlayerAnnimation playerAnnimation;
	public Entity ladderIgnore;
	
	public boolean up, down;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		PLAYER_SPRITE_L_MOVES[0] = PLAYER_SPRITE_L;
		PLAYER_SPRITE_L_MOVES[1] = Game.spritesheet.getSprite(0, 64, Game.basex, Game.basey);
		PLAYER_SPRITE_L_MOVES[2] = Game.spritesheet.getSprite(16, 64, Game.basex, Game.basey);
		PLAYER_SPRITE_L_MOVES[3] = Game.spritesheet.getSprite(32, 64, Game.basex, Game.basey);
		
		PLAYER_SPRITE_R_MOVES[0] = PLAYER_SPRITE_R;
		PLAYER_SPRITE_R_MOVES[1] = Game.spritesheet.getSprite(0, 48, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[2] = Game.spritesheet.getSprite(16, 48, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[3] = Game.spritesheet.getSprite(32, 48, Game.basex, Game.basey);
	}
	
	
	
	public void tick(){
		if ( !isInAnnimation ) {
			depth = 2;
			//coisa nova
			boolean isL = false;
								
			if (!isLadder) {
				vspd+=gravity;
				
				
				if(!World.isFree((int)x,(int)(y+1)) && jump ) {
					vspd = -6;
					jump = false;
				}
				
				try {
				
					if(!World.isFree((int)x,(int)(y+vspd))) {
						
						int signVsp = 0;
						if(vspd >= 0) {
							signVsp = 1;
						} else {
							signVsp = -1;
						}
						while(World.isFree((int)x,(int)(y+signVsp))) {
							y = y+signVsp;
						}
						vspd = 0;
					}
				
				} catch (Exception e) {
					System.out.println("Game Over " + e.getStackTrace());
					Game.state = "GAME_OVER";
					return;
				}
			}
			
			if ( Game.state.equals("NORMAL") ) {
							
				if ( up && isLadder && World.isFree((int)(x), (int)y - 1)) {
					this.y-= 1;
				}
				
				if ( down && isLadder && World.isFree((int)(x), (int)y + 1)) {
					this.y+= 1;
				}
				
				if (!isLadder) {
				
					y = y + vspd;
					
					if (World.isFree((int)(x), (int)y + 1)) {
						for ( int i = 0; i < Game.entities.size(); i++ ) {
							if ( Game.entities.get(i) instanceof Enemy ) {
								if ( Entity.isColidding(this, Game.entities.get(i)) ) {
									isJumping = true;
									jump = true;
									vspd = -4;
									((Enemy) Game.entities.get(i)).life--;
									if ( ((Enemy) Game.entities.get(i)).life <= 0 ) {
										Game.entities.remove(i);
										break;
									}
								}
							} else {
								jump = false;
							}
						}
					} else {
						isJumping = false;
					}
				}
	
				
				if(right && World.isFree((int)(x+speed), (int)y)) {
					x+=speed;
					isRight = true;
					if ( ! isJumping )
						animation(this.maxAnimation);
				}
				else if(left && World.isFree((int)(x-speed), (int)y)) {
					x-=speed;
					isRight = false;
					if ( ! isJumping )
						animation(this.maxAnimation);
				} 
				
				for ( int i = 0; i < Game.entities.size(); i++ ) {
					if ( Game.entities.get(i) instanceof Enemy ) {
						if ( Entity.isColidding(this, Game.entities.get(i)) ) {
							//if ( Entity.rand.nextInt(100) < 30 )
								life-=0.5;
						}
					} else if ( Game.entities.get(i) instanceof Coin ) {
						if ( Entity.isColidding(this, Game.entities.get(i)) ) {
							Game.entities.remove(i);
							coins+=1;
							break;
						}
					} else if ( Game.entities.get(i) instanceof Ladder ) {
						
						if ( Entity.isColidding(this, Game.entities.get(i))) {//&& ( isGetLadder || isLadder ) ) {
							
							if (((Ladder)Game.entities.get(i)).getTypeFormat() == 4) {
								if ( (int) Game.entities.get(i).y > (int) this.y && Game.entities.get(i) != ladderIgnore) {
									isInAnnimation = true;
									playerAnnimation = PlayerAnnimation.LADDER_UP;
									upLadderY = Game.entities.get(i).y - Game.basey - 1;
									ladderIgnore = Game.entities.get(i);
								}
							}
							
							if ( ((Ladder)Game.entities.get(i)).getTypeFormat() == 2 ) {
								this.x = Game.entities.get(i).x;
							}
							
							if ( ((Ladder)Game.entities.get(i)).getTypeFormat() == 3 ) {
								this.x = Game.entities.get(i).x;
								double xL = Game.entities.get(i).x;
								double yL = Game.entities.get(i).y;
								boolean upFree = true;
								
								for ( int j = 0; j < Game.laddersPos.size(); j++ ) {
									if ( (yL / 16) - 1 == Game.laddersPos.get(j).y && (xL / 16) == Game.laddersPos.get(j).x ) {
										System.out.println("Tem mais uma escada acima");
										upFree = false;
									}
								}
	
								if ( upFree && this.y < yL && up) {
									if ( World.isFree((int) xL - 16, (int) yL - 16) ) {
										//animação de subida
										this.x = xL - 16;
										this.y = yL - 16;
									} else if ( World.isFree((int) xL + 16, (int) yL - 16) ) {
										//animação de subida
										this.x = xL + 16;
										this.y = yL - 16;
									} else {
										this.y = yL;
									}
								}
							}
							
							isL = true;
						}
						
						if ( isL ) {
							this.isLadder = true;
						} else {
							this.isLadder = false;
						}
							
							/**/
							/*
							if ( ((Ladder)Game.entities.get(i)).getTypeFormat() == 3 ) {
								double acima = (Game.entities.get(i).y/Game.basey) +1;
								World.
							}*/
							/*this.isLadder = true;
							break;
						} else {
							this.isLadder = false;
						}*/
					}
				}
				
				if ( life <= 0 ) {
					System.out.println("Game Over");
					Game.state = "GAME_OVER";
				}
			}
			
			Camera.x = Camera.clamp( (int) x - Game.WIDTH / 2, 0, World.WIDTH * Game.basex - Game.WIDTH);
			Camera.y = Camera.clamp( (int) y - Game.HEIGHT / 2, 0, World.HEIGHT * Game.basey - Game.HEIGHT);
		} else {
			if ( playerAnnimation.equals(PlayerAnnimation.LADDER_UP) && this.y > this.upLadderY ) {
				this.y-=1;
			} else {
				this.y = this.upLadderY;
				this.isInAnnimation = false;
				this.isLadder = false;
			}
		}
	}

	public void render(Graphics g) {
		if ( isRight && right ) {
			//sprite = this.PLAYER_SPRITE_R;
			sprite = PLAYER_SPRITE_R_MOVES[curAnimation];
		} else if ( isRight && ! right ) {
			sprite = PLAYER_SPRITE_R_MOVES[0];			
		} else  if ( ! isRight && left ) {
			//sprite = this.PLAYER_SPRITE_L;
			sprite = PLAYER_SPRITE_L_MOVES[curAnimation];
		} else {
			//sprite = this.PLAYER_SPRITE_L;
			sprite = PLAYER_SPRITE_L_MOVES[0];
		}
		
		if ( down && ! isLadder && !( left || right ) && !isRight ) {
			sprite = Spritesheet.invert(PLAYER_SLIM);
		} else if ( down && ! isLadder && !( left || right ) && isRight ) {
			sprite = PLAYER_SLIM;
		}
		
		if ( down && ! isLadder && ( left || right ) ) {
			sprite = PLAYER_ROLL;
		}
		
		super.render(g);
	}
}
