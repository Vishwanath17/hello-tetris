package com.badlogic.androidgames.tetris2;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);      
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");
        
        Assets.buttons2 = g.newPixmap("buttons2.png", PixmapFormat.ARGB4444);
        Assets.blue = g.newPixmap("blue.png", PixmapFormat.RGB565);
        //Assets.clear = g.newPixmap("clear.png", PixmapFormat.RGB565);
        Assets.black = g.newPixmap("black.png", PixmapFormat.RGB565);
        Assets.green = g.newPixmap("green.png", PixmapFormat.RGB565);
        Assets.light_blue = g.newPixmap("light_blue.png",PixmapFormat.RGB565);
        Assets.orange = g.newPixmap("orange.png",PixmapFormat.RGB565);
        Assets.purple = g.newPixmap("purple.png",PixmapFormat.RGB565);
        Assets.red = g.newPixmap("red.png",PixmapFormat.RGB565);
        Assets.yellow = g.newPixmap("yellow.png",PixmapFormat.RGB565);
        
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}