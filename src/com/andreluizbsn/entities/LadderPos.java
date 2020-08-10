package com.andreluizbsn.entities;

public class LadderPos {

	public int x, y;
	public Ladder ladder;
	
	public LadderPos ( int x, int y, Ladder ladder ) {
		this.ladder = ladder;
		this.x = x;
		this.y= y;
	}
	
}
