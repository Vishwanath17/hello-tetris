package com.badlogic.androidgames.tetris2;

import java.util.List;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
    
    GameState state = GameState.Ready;
    //World world;
    TetrisGrid tetris;
    int oldScore = 0;
    String score = "0";
    
    public GameScreen(Game game) {
        super(game);
        //world = new World();
        tetris = new TetrisGrid();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents);  
        
    }
    
    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }
    
    
    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 80 && event.y > 416) {
                   // world.snake.turnLeft();
                	if(tetris.checkMoveLeft()){
                		tetris.currentBlock.shiftLeft();
                	}	
                }
                if(event.x > 240 && event.y > 416) {
                    //world.snake.turnRight();
                	if(tetris.checkMoveRight()){
                		tetris.currentBlock.shiftRight();
                	}
                }
                if(event.x < 160 && event.x > 80 && event.y > 416){
                	if(tetris.checkLeftSpin()){
                		tetris.currentBlock.spinLeftT();
                	}
                }
                if(event.x < 240 && event.x > 160 && event.y > 416){
                	if(tetris.checkdown() && !tetris.checkTouching()){
                		tetris.currentBlock.down();
                		tetris.incrementScore();
                	}
                }
            }
        }
        
        //world.update(deltaTime);
        tetris.update(deltaTime);
        if(tetris.gameOver) {//world.gameOver
            if(Settings.soundEnabled)
                Assets.bitten.play(1);
            state = GameState.GameOver;
        }
        if(oldScore != tetris.score) {
            oldScore = tetris.score;
            score = "" + oldScore;
            if(Settings.soundEnabled)
                Assets.eat.play(1);
        }
    }
    
    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Running;
                        return;
                    }                    
                    if(event.y > 148 && event.y < 196) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));                        
                        return;
                    }
                }
            }
        }
    }
    
    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                   event.y >= 200 && event.y <= 264) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }
    

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        //drawWorld(world);
        if(state == GameState.Ready) 
            drawReadyUI();
        if(state == GameState.Running){
        	drawGrid(tetris);
        	drawText(g, score, 3 * g.getWidth() / 4 - score.length()*20 / 2, g.getHeight() / 3);     
            drawRunningUI();
        }
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver){
        	drawText(g, score, 3 * g.getWidth() / 4 - score.length()*20 / 2, g.getHeight() / 3);     
        	drawGameOverUI();
        }
        	
            
        
                   
    }
    
    public void drawGrid(TetrisGrid Grid){
		Graphics g = game.getGraphics();
        Block cBlock = Grid.currentBlock;
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
        
        for(int i = 0; i < Grid.GRID_HEIGHT; i++){
        	for(int j = 0; j < Grid.GRID_WIDTH; j++){
        		int color = Grid.Grid[i][j];
        		if(color > 0){
        			x = j * 16  + xinit;
        			y = i * 16  + yinit;
        			g.drawPixmap(getColorAssets(color), x, y);
        		}
        		
        	}
        }
        
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    
    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        //g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
       // g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
        g.drawPixmap(Assets.buttons2, 0, 416);
        //g.drawPixmap(pixmap, x, y, srcX, srcY, srcWidth, srcHeight)
    }
    
    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    
    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 64;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
    
    @Override
    public void pause() {
        if(state == GameState.Running)
            state = GameState.Paused;
        
        if(tetris.gameOver) {
            Settings.addScore(tetris.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
    }
}