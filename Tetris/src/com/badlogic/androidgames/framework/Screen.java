package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.tetris2.Assets;
import com.badlogic.androidgames.tetris2.Block;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }
    
    public Pixmap getColorAssets(int color){
		
		switch(color){
			case 1:	
				return Assets.orange;
			case 2:  
				return Assets.blue;
			case 3:	
				return Assets.red;
			case 4:	
				return Assets.green;
			case 5:	
				return Assets.yellow;
			case 6:	
				return Assets.light_blue;
			case 7:	
				return Assets.purple;
			
		}
		return Assets.black;
		
	}

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
