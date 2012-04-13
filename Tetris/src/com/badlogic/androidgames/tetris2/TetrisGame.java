package com.badlogic.androidgames.tetris2;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class TetrisGame extends AndroidGame {
	
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
    
}