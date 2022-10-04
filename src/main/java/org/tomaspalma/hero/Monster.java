package org.tomaspalma.hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x, y);
    }

    public void move(Position heroPosition) {
        Random random = new Random();
        int control = random.nextInt(5) + 1;
        if(control <= 4) {
            simpleMove();
        } else {
            complexMove(heroPosition);
        }
    }

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
    }

    public void complexMove(Position heroPosition) {
        Position currentPosition = getCurrentPosition();
        int newX, newY;
        if(currentPosition.isAbove(heroPosition)) {
            newY = currentPosition.getY() + 1;
        } else {
            newY = currentPosition.getY() - 1;
        }

        if(currentPosition.isLeft(heroPosition)) {
            newX = getCurrentPosition().getX() + 1;
        } else {
            newX = getCurrentPosition().getX() - 1;
        }

        setCurrentPosition(newX, newY);
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
        graphics.setForegroundColor(TextColor.Factory.fromString("#006400"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(currentPosition.getX(), currentPosition.getY()), "M");
    }
}
