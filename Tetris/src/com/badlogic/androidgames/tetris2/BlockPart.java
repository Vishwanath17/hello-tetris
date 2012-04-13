package com.badlogic.androidgames.tetris2;

import android.graphics.Color;
import android.graphics.Point;

public class BlockPart{
	int x;
	int y;
	public BlockPart(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void setXY(int x,int y){
		this.x = x;
		this.y = x;
	}
}
