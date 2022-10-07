package org.tomaspalma.hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;
import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x, y);
    }

    public void move(Position heroPosition, List<Monster> monsters, int arenaWidth, int arenaHeight) {
        complexMove(heroPosition, monsters, arenaWidth, arenaHeight);
    }

    /* Código para movimento totalmente aleatório
    public void simpleMove() {
        Random random = new Random();
        int control = random.nextInt(4) + 1;
        switch(control) {
            case 1:
                moveUp();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveRight();
                break;
            default:
                moveLeft();
                break;
        }
    } */

    public void complexMove(Position heroPosition, List<Monster> monsters, int arenaWidth, int arenaHeight) {
        Random random = new Random();
        int control = random.nextInt(2) + 1, newX = currentPosition.getX(), newY = currentPosition.getY();
        Position currentPosition = getCurrentPosition(), newPosition;
        if(control == 1) {
            if(currentPosition.isAbove(heroPosition)) {
                newY = currentPosition.getY() + 1;
            } else {
                newY = currentPosition.getY() - 1;
            }
        } else {
            if(currentPosition.isLeft(heroPosition)) {
                newX = getCurrentPosition().getX() + 1;
            } else {
                newX = getCurrentPosition().getX() - 1;
            }
        }

        newPosition = new Position(newX, newY);
        if (newX == 0 || newX == arenaWidth - 1 || newY == 1 || newY == arenaHeight - 1) {
            return;
        }
        for(Monster monster: monsters) {
            if(newPosition.equals(monster.getCurrentPosition())) return;
        }

        if(newPosition.equals(heroPosition)) return;
        setCurrentPosition(newPosition.getX(), newPosition.getY());
    }

    private void moveUp() {
        if(getCurrentPosition().getY() - 1 == 1) {
            moveDown();
        } else {
            setCurrentPosition(getCurrentPosition().getX(), getCurrentPosition().getY() - 1);
        }
    }

    private void moveDown() {
        if(getCurrentPosition().getY() + 1 == Game.HEIGHT - 1) {
            moveUp();
        } else {
            setCurrentPosition(getCurrentPosition().getX(), getCurrentPosition().getY() + 1);
        }
    }

    private void moveRight() {
        if(getCurrentPosition().getX() + 1 == Game.WIDTH) {
            moveLeft();
        } else {
            setCurrentPosition(getCurrentPosition().getX() + 1, getCurrentPosition().getY());
        }
    }

    private void moveLeft() {
        if(getCurrentPosition().getX() - 1 == 0) {
            moveRight();
        } else {
            setCurrentPosition(getCurrentPosition().getX() - 1, getCurrentPosition().getY());
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString(Game.LIGHTGREEN));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(currentPosition.getX(), currentPosition.getY()), "M");
    }
}
