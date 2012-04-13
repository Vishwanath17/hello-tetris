package com.badlogic.androidgames.tetris2;

import java.util.List;
import java.util.Random;

import android.graphics.Point;

public class TetrisGrid {
	
	static final int GRID_WIDTH = 10;
	static final int GRID_HEIGHT = 20;
	
	static final int LBLOCk = 0;
	static final int JBLOCk = 1;
	static final int ZBLOCk = 2;
	static final int SBLOCk = 3;
	static final int OBLOCk = 4;
	static final int IBLOCk = 5;
	static final int TBLOCK = 6;
	
	static final int UP = 0;
    static final int LEFT = 1;
    static final int DOWN = 2;
    static final int RIGHT = 3;
    
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 1.5f;
	static final float TICK_DECREMENT = 0.1f;
	static float tick = TICK_INITIAL;
	
	private Random rand;
	public Block currentBlock;
	public Block nextBlock;
	public int[][] Grid;
	public boolean gameOver = false;;
    float tickTime = 0;	
	public int score = 0;
	public int lineCount = 0;
	public int level = 1;
	
	public TetrisGrid(){
		
		Grid = new int[GRID_HEIGHT][GRID_WIDTH];
		clearGrid();
		rand = new Random();
		currentBlock = new Block(rand.nextInt(6),-1,-1);
		nextBlock = new Block(rand.nextInt(6),-1,-1);
	    tickTime = 0;
	    
	    
	}
	
	 public void update(float deltaTime) {
	        if (gameOver)
	            return;

	        tickTime += deltaTime;

	        while (tickTime > tick) {
	        	tickTime -= tick;
	        	boolean bottom = false;
	        	for(int i = 0;i < currentBlock.parts.size();i++){
	        		if(currentBlock.parts.get(i).y >= GRID_HEIGHT - 1){
	        			bottom = true;
	        		}
	        	}
	        	
	        	if(bottom || checkTouching() ){
	        		if (checkTopLine()){
	        			gameOver = true;
	        			return;
	        		}
	        		addBlocks(currentBlock.parts,currentBlock.color);
	        		
	        		currentBlock = nextBlock;
	        		nextBlock = new Block(rand.nextInt(6),-1,-1);
	        	} else {
	        		currentBlock.down();
	        	}
	        	checkAllLines();
	        	
	        	
	        }
	        
	 }
	 
	 public void incrementScore(){
		 score += (level * SCORE_INCREMENT);
		 level = ++lineCount / 10 + 1;
		 
	 }
	 
	 public boolean checkdown(){
		 boolean down = true;
		 
		 for(int i = 0; i < currentBlock.parts.size(); i++){
			 if(currentBlock.parts.get(i).y >= GRID_HEIGHT - 1) return false;
		 }
		 return down;
	 }
	 
	 public int maxY(){
		 int x = 0;
		 int y = 0;
		 for(int i = 0; i < 4;i++){
			 if(currentBlock.parts.get(i).y > y) y = currentBlock.parts.get(i).y;
		 }
		 return y;
	 }
	 
	 public boolean checkLeftSpin(){
		 boolean spin = true;
		 List<BlockPart> parts = currentBlock.parts;
		 int maxY = maxY();
		 int x;
		 int y;
	    	switch(currentBlock.color-1){
	    	case LBLOCk:
	    		x = parts.get(1).x;
    			y = parts.get(1).y;	
    			if(x < 1 || x >= GRID_WIDTH-1 || maxY >= GRID_HEIGHT - 1) return false;
	    		switch(currentBlock.direction){
	    		case UP:
	    			
	    			if(Grid[y][x-1] > 0 || Grid[y][x+1] > 0 || Grid[y-1][x+1] > 0) spin = false;
	    			break;
	    		case LEFT:
	    			if(Grid[y-1][x] > 0 || Grid[y+1][x] > 0 || Grid[y-1][x-1] > 0 ) spin = false;
	    			break;
	    		case DOWN:
	    			if(Grid[y][x-1] > 0 || Grid[y][x+1] > 0 || Grid[y+1][x-1] > 0) spin = false;
	    			break;
	    		case RIGHT:
	    			if(Grid[y-1][x] > 0 || Grid[y+1][x] > 0 || Grid[y+1][x+1] > 0 ) spin = false;
	    		}
	    		break;
	    	case JBLOCk:
	    		x = parts.get(1).x;
    			y = parts.get(1).y;	
    			if(x <= 1 || x >= GRID_WIDTH-1 || maxY >= GRID_HEIGHT - 1) return false;
	    		switch(currentBlock.direction){
	    		case UP:
	    			if(Grid[y][x-1] > 0 || Grid[y][x+1] > 0 || Grid[y+1][x+1] > 0) spin = false;
	    			break;
	    		case LEFT:
	    			if(Grid[y-1][x] > 0 || Grid[y+1][x] > 0 || Grid[y-1][x+1] > 0 ) spin = false;
	    			break;
	    		case DOWN:
	    			if(Grid[y][x-1] > 0 || Grid[y][x+1] > 0 || Grid[y-1][x-1] > 0) spin = false;
	    			break;
	    		case RIGHT:
	    			if(Grid[y-1][x] > 0 || Grid[y+1][x] > 0 || Grid[y+1][x-1] > 0 ) spin = false;
	    		}
	    		break;
	    	case ZBLOCk:
	    		x = parts.get(2).x;
	    		y = parts.get(2).y;
	    		if(x < 1) return false;
	    		switch(currentBlock.direction){
	    		case UP: 			
	    			if(Grid[y][x-1] > 0 || Grid[y+1][x+1] > 0) spin = false;
	    			break;	
	    		case LEFT:
	    			if(Grid[y][x+1] > 0 || Grid[y-1][x+1] > 0) spin = false;
	    			break;	
	    		}
	    		break;
	    	case SBLOCk:  
	    		switch(currentBlock.direction){
		    		case UP: 
		    			x = parts.get(1).x;
			    		y = parts.get(1).y;
			    		if(x < 1) return false;
		    			if(Grid[y+1][x] > 0 || Grid[y+1][x-1] > 0) spin = false;
		    			break;	
		    		case LEFT:
		    			x = parts.get(2).x;
			    		y = parts.get(2).y;
		    			if(Grid[y+1][x+1] > 0 || Grid[y-1][x] > 0) spin = false;
		    			break;	
	    		}
	    		break;
	    	case OBLOCk:
	    
	    		break;
	    	case IBLOCk:
	    		x = parts.get(2).x;
	    		y = parts.get(2).y;
	    		if(x < 2 || x >= GRID_WIDTH - 1) return false;
	    		switch(currentBlock.direction){
	    		case UP:
	    			if(Grid[y][x-2] > 0 || Grid[y][x-1] > 0 || Grid[y][x+1] > 0) spin = false;
	    			break;
	    		case LEFT:
	    			if(Grid[y-2][x] > 0 || Grid[y-1][x] > 0 || Grid[y+1][x] > 0 || maxY >= GRID_HEIGHT - 1) spin = false;
	    			break;			
	    		}
	    		break;
	    	case TBLOCK:
	    		x = parts.get(1).x;
	    		y = parts.get(1).y;
	    		switch(currentBlock.direction){
	    		case UP:
	    			if(Grid[y+1][x] > 0 || maxY >= GRID_HEIGHT - 1) spin = false;
	    			break;
	    		case LEFT:
	    			if(x >= GRID_WIDTH - 1 || maxY >= GRID_HEIGHT - 1) return false;
	    			if(Grid[y][x+1] > 0) spin = false;
	    			break;
	    		case DOWN:
	    			if(Grid[y-1][x] > 0) spin = false;
	    			break;
	    		case RIGHT:
	    			if(x < 1 || maxY >= GRID_HEIGHT - 1) return false;
	    			if(Grid[y][x-1] > 0) spin = false;
	    		}
	    	}
	    	
		 return spin;
	 }
	 
	 public void checkAllLines(){
		 int min = 1;
		 for(int i = 0; i < Grid.length; i++){
			 min = 1;
			 for(int j = 0; j < Grid[0].length; j++){
				 if(Grid[i][j] < min){ min = 0; }
			 }
			  if(min > 0){
					 deleteRow(i);
					 incrementScore();
				}
		 }
	 }
	 
	 public boolean checkMoveLeft(){
	    	boolean open = true;
	    	int minX = GRID_WIDTH;
	    	List<BlockPart> bp = currentBlock.parts;
	    	int x = 0;
	    	int y = 0;
	    	for(int i = 0; i < bp.size(); i++){
	    		x = bp.get(i).x;
	    		if(x < minX){
	    			minX = x;
	    		}
	    	}
	    	if(minX == 0) return false;
	    	for(int i = 0; i < bp.size(); i++){
	    		x = bp.get(i).x;
	    		if(x == minX){
	    			y = bp.get(i).y;
	    			if(Grid[y][x-1] > 0){
	    				open = false;
	    			}
	    		}
	    	}
	    	return open;
	    }
	    
	    public boolean checkMoveRight(){
	    	boolean open = true;
	    	int maxX = 0;
	    	List<BlockPart> bp = currentBlock.parts;
	    	int x = 0;
	    	int y = 0;
	    	for(int i = 0; i < bp.size(); i++){
	    		x = bp.get(i).x;
	    		if(x > maxX){
	    			maxX = x;
	    		}
	    	}
	    	if(maxX == TetrisGrid.GRID_WIDTH - 1) return false;
	    	for(int i = 0; i < bp.size(); i++){
	    		x = bp.get(i).x;
	    		if(x == maxX){
	    			y = bp.get(i).y;
	    			if(Grid[y][x+1] > 0){
	    				open = false;
	    			}
	    		}
	    	}
	    	return open;
	    }
	 
	 public void deleteRow(int row){
		 
		 for(int i = row; i > 0; i--){
				 for(int j = 0; j < Grid[0].length; j++){
					 Grid[i][j] = Grid[i-1][j];
				 }
		 }
		 for(int j = 0; j < Grid[0].length; j++){
			 Grid[0][j] = 0;
		 }
	 }
	 
	 
	public boolean checkTopLine(){
		 //Check if Top row has color value > 0
		 for(int i = 0; i < GRID_WIDTH;i++){
			 if(Grid[0][i] > 0){
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public void addBlocks(List<BlockPart> parts, int c){
		 //Write CurrentBlock to Grid value with Color
		 for (int i = 0; i < parts.size(); i++){
			 int x = parts.get(i).x;
			 int y = parts.get(i).y;
			 Grid[y][x] = c;
		 }
	 }

	public boolean checkTouching(){
		//check grid for all blocks below Cblock
		int y = 0;
		int x = 0;
		for(int i = 0;i < currentBlock.parts.size();i++){
			x = currentBlock.parts.get(i).x;
			y = currentBlock.parts.get(i).y;
			if(Grid[y+1][x] > 0){
				return true;
			}
		}
		return false;
	}
 
	public void clearGrid(){
		//write 0 to entrire Grid
		for(int i = GRID_HEIGHT-1; i >= 0;i--){
			for(int j = GRID_WIDTH-1;i >= 0; i--){
				Grid[i][j] = 0;
			}
		}
	}
	

	public void setNextBlock(int nextBlock) {
		this.nextBlock = new Block(nextBlock,-1,-1);
	}
	
	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = new Block(currentBlock,-1,-1);
	}

	public void setGrid(int[][] grid) {
		Grid = grid;
	}

	public void setScore(int score) {
		this.score = score;
	}


	
}
