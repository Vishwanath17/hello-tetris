package com.badlogic.androidgames.tetris2;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Matrix;

public class Block {
	 	public static final int UP = 0;
	    public static final int LEFT = 1;
	    public static final int DOWN = 2;
	    public static final int RIGHT = 3;
	    
	    static final int LBLOCk = 0;
		static final int JBLOCk = 1;
		static final int ZBLOCk = 2;
		static final int SBLOCk = 3;
		static final int OBLOCk = 4;
		static final int IBLOCk = 5;
		static final int TBLOCK = 6;
		
		static final int ORANGE = 1;
		static final int BLUE = 2;
		static final int RED = 3;
		static final int GREEN = 4;
		static final int YELLOW = 5;
		static final int LIGHT_BLUE = 6;
		static final int PURPLE = 7;
		
		private final int STARTX = 3;
		private final int STARTY = 0;
		
		public int color;
		public int direction;
		public int type;
	    public boolean touching;
	    public List<BlockPart> parts = new ArrayList<BlockPart>();
	    
	    
	    public Block(int type, int x, int y){
	    	
	    	if(x < 0) x = STARTX;
	    	if(y < 0) y = STARTY;
	    	
	    	this.type = type;
	    	this.direction = UP;
	    	switch (type){
	    		case LBLOCk:
	    			color = ORANGE;
	    			parts.add(new BlockPart(x,y));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x,y+2));
	    			parts.add(new BlockPart(x+1,y+2));
	    			break;
	    		case JBLOCk:
	    			color = BLUE;
	    			parts.add(new BlockPart(x+1,y));
	    			parts.add(new BlockPart(x+1,y+1));
	    			parts.add(new BlockPart(x+1,y+2));
	    			parts.add(new BlockPart(x,y+2));
	    			break;
	    		case ZBLOCk:
	    			color = RED;
	    			parts.add(new BlockPart(x+1,y));
	    			parts.add(new BlockPart(x+1,y+1));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x,y+2));
	    			break;
	    		case SBLOCk:
	    			color = GREEN;
	    			parts.add(new BlockPart(x,y));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x+1,y+1));
	    			parts.add(new BlockPart(x+1,y+2));
	    			break;
	    		case OBLOCk:
	    			color = YELLOW;
	    			parts.add(new BlockPart(x,y));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x+1,y));
	    			parts.add(new BlockPart(x+1,y+1));
	    			break;
	    		case IBLOCk:
	    			color = LIGHT_BLUE;
	    			parts.add(new BlockPart(x,y));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x,y+2));
	    			parts.add(new BlockPart(x,y+3));
	    			break;
	    		case TBLOCK:
	    			color = PURPLE;
	    			parts.add(new BlockPart(x,y));
	    			parts.add(new BlockPart(x,y+1));
	    			parts.add(new BlockPart(x-1,y+1));
	    			parts.add(new BlockPart(x+1,y+1));
	    	}
	    	
	    }
	    public void down(){
			for(int i = 0; i < parts.size(); i++){
				parts.get(i).y++;
			}
	    }
	    
	    public void shiftLeft(){
	    	boolean leftSpaceOpen = true;
	    	for(int i = 0; i < parts.size(); i++){
				if(parts.get(i).x < 1) leftSpaceOpen = false;
			}
	    	if(leftSpaceOpen){
		    	for(int i = 0; i < parts.size(); i++){
		    		parts.get(i).x--;
				}
	    	}
	    }
	    
	    public void shiftRight(){
	    	boolean rightSpaceOpen = true;
	    	for(int i = 0; i < parts.size(); i++){
				if(parts.get(i).x >= TetrisGrid.GRID_WIDTH-1) rightSpaceOpen = false;
			}
	    	if(rightSpaceOpen){
		    	for(int i = 0; i < parts.size(); i++){
					parts.get(i).x++;
				}
	    	}
	    }
	    
	   
	    
	    public void spinLeftT(){

	    	int angle = -90;
	    	Matrix m = new Matrix();
	    	BlockPart center = getCenter();
	    	float[] array = toArray();
	    	switch(type){
	    	case OBLOCk: return;
	    	case LBLOCk:
	    	case JBLOCk:
	    	case TBLOCK:   	
	    		m.setRotate(angle, center.x, center.y);
	    		m.mapPoints(array);
	    		setList(array);
	    		break;
	    	case ZBLOCk:
	    		if(direction == UP) angle *= -1;
	    		m.setRotate(angle, center.x, center.y);
	    		m.mapPoints(array);
	    		setList(array);
	    		direction++;
	    		direction %= 2;
	    		break;
	    	case SBLOCk:
	    		if(direction == LEFT) angle *= -1;
	    		m.setRotate(angle, center.x, center.y);
	    		m.mapPoints(array);
	    		setList(array);
	    		if(direction == UP) 
	    			shiftLeft();
	    		else shiftRight();
	    		direction++;
	    		direction %= 2;
	    		break;
	    	case IBLOCk:
	    		if(direction == LEFT) angle *= -1;
	    		m.setRotate(angle, center.x, center.y);
	    		m.mapPoints(array);
	    		setList(array);
	    		direction++;
	    		direction %= 2;
	    	}
	
	    }
	    
	    
	    public void spinRight(){
	    	
			
	    }
	    
	    public BlockPart findCenter(){
	    	float x = 0;
	    	float y = 0;
	    	int s = parts.size();
	    	for(int i = 0; i < s;i++){
	    		x += parts.get(i).x;
	    		y += parts.get(i).y;
	    	}
	    	
	    	x /= s;
	    	y /= s;
	    	
	    	return new BlockPart(Math.round(x),Math.round(y));
	    }
	    
	    public BlockPart getCenter(){
	    	switch(type){
	    	case LBLOCk:
	    	case JBLOCk:
	    	case TBLOCK:   	
	    		return parts.get(1);
	    	}
	    	return parts.get(2);
	    }
	    
	    public void setList(float[] array){
	    	int i = 0;
	    	for(BlockPart bp : parts){
	    		bp.x = (int) array[i++];
	    		bp.y = (int) array[i++];
	    	}
	    }
	    
	    public float[] toArray(){
	    	float[] array = new float[8];
	    	int i = 0;
	    	for(BlockPart bp : parts){
	    		array[i++] = bp.x;
	    		array[i++] = bp.y;
	    	}
	    	return array;
	    }
		    

}
