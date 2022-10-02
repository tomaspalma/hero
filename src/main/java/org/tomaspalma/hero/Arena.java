package org.tomaspalma.hero;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        //Adicionar paredes em toda a horizontal
        for(int i = 0; i < width; i++) {
            walls.add(new Wall(i, 0, "-"));
            walls.add(new Wall(i, height - 1, "-"));
        }

        //Adicionar paredes em toda a vertical
        for(int i = 1; i < height - 1; i++) {
            walls.add(new Wall(0, i, "|"));
            walls.add(new Wall(width - 1, i, "|"));
        }

        return walls;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        // O último parâmetro é o caratér que vai aparecer em cada quadradinho que divide o quadrilátero
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for(Wall wall: walls) {
            wall.draw(graphics);
        }
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
        for(Wall wall: walls) {
            if(wall.getCurrentPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public Position getHeroPosition() {
        return hero.getCurrentPosition();
    }

    private int width, height;
    private Hero hero = new Hero(10, 10);
    private List<Wall> walls;
}
