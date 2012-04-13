package com.badlogic.androidgames.tetris2;

import java.util.List;
import java.util.Random;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class HelpScreen extends Screen {

	int screenNum = 1;
	TetrisGrid tetris = new TetrisGrid();
	
    public HelpScreen(Game game) {
        super(game);
        tetris.currentBlock = new Block(Block.IBLOCk,-1,-1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        Graphics g = game.getGraphics();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < g.getWidth() && event.y < g.getHeight()) {
                    screenNum++;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();  
        g.drawPixmap(Assets.background, 0, 0);
        drawtetris(tetris,g);
        switch(screenNum){
        case 1: 
        	drawlines();
        	g.drawPixmap(Assets.help1, 135, 100);break;
        case 2: 
        	moveBlock();
        	g.drawPixmap(Assets.buttons2, 0, 416);
        	g.drawPixmap(Assets.help2, 135, 100);break;
        case 3: 
        	moveBlock2();
        	g.drawPixmap(Assets.help3, 135, 100);break;
        case 4: 
        	game.setScreen(new MainMenuScreen(game));return;
        }
        
    }
    
    public void drawlines(){
    	Random rand = new Random();
    	for(int i = TetrisGrid.GRID_HEIGHT-1; i > TetrisGrid.GRID_HEIGHT - 5;i--){
    		for(int j = 0; j < tetris.Grid[i].length; j++){
    			if(j != 4){
    				tetris.Grid[i][j] = 3;
    			}		
    		}
    	}
    }
    
    public void moveBlock(){
    	tetris.currentBlock = new Block(Block.IBLOCk,4,12);
    }
    
    public void moveBlock2(){
    	tetris.currentBlock = new Block(Block.IBLOCk,4,16);
    }
    
    public void drawtetris(TetrisGrid tetris, Graphics g){
        Block cBlock = tetris.currentBlock;
        int xinit = 18;
        int yinit = 55;
        int x;
        int y;
        
        for(int i = 0; i < TetrisGrid.GRID_HEIGHT;i++){
        	for(int j = 0; j < TetrisGrid.GRID_WIDTH;j++){
        		x = j * 16  + xinit;
        		y = i * 16  + yinit;
        		g.drawPixmap(Assets.black, x, y);
        	}	
			
		}
        
        int len = cBlock.parts.size();
        for(int i = 0; i < len; i++) {
            BlockPart part = cBlock.parts.get(i);
            x = part.x * 16  + xinit;
            y = part.y * 16  + yinit;
            Pixmap p = getColorAssets(cBlock.color);
            g.drawPixmap(p, x, y);
        }
        
        for(int i = 0; i < tetris.GRID_HEIGHT; i++){
        	for(int j = 0; j < tetris.GRID_WIDTH; j++){
        		int color = tetris.Grid[i][j];
        		if(color > 0){
        			x = j * 16  + xinit;
        			y = i * 16  + yinit;
        			g.drawPixmap(getColorAssets(color), x, y);
        		}
        		
        	}
        }
        
    }
   
    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
