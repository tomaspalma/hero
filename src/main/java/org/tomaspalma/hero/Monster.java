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

    public Position move() {
        Random random = new Random();
        int control = random.nextInt(4) + 1;
        //Tentamos mover verticalmente
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
        return new Position(0, 0);
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
