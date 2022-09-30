package org.tomaspalma.hero;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Screen screen) {
        Position currentPosition = hero.getCurrentPosition();
        screen.setCharacter(currentPosition.getX(), currentPosition.getY(), TextCharacter.fromCharacter('X')[0]);
    }

    public void processKey(KeyStroke key) {
        switch(key.getKeyType()) {
            case ArrowUp:
                moveHeroTo(hero.moveUp()); // o eixo dos y esta invertido
                break;
            case ArrowDown:
                moveHeroTo(hero.moveDown());
                break;
            case ArrowLeft:
                moveHeroTo(hero.moveLeft());
                break;
            case ArrowRight:
                moveHeroTo(hero.moveRight());
                break;
        }
    }

    public void moveHeroTo(Position position) {
        if(canHeroMoveTo(position)) {
            hero.setCurrentPosition(position);
        }
    }

    private boolean canHeroMoveTo(Position position) {
        if(position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height) return true;
        return false;
    }

    public Position getHeroPosition() {
        return hero.getCurrentPosition();
    }

    private int width, height;
    private Hero hero = new Hero(0, 0);
}
