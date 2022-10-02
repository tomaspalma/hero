package org.tomaspalma.hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Hero extends Element {
    public Hero(int x, int y) {
        super(x, y);
    }

    public Position moveUp() {
        return new Position(currentPosition.getX(), currentPosition.getY() - 1);
    }

    public Position moveDown() {
        return new Position(currentPosition.getX(), currentPosition.getY() + 1);
    }

    public Position moveRight() {
        return new Position(currentPosition.getX() + 1, currentPosition.getY());
    }

    public Position moveLeft() {
        return new Position(currentPosition.getX() - 1, currentPosition.getY());

    }

    @Override
    public void draw(TextGraphics graphics) {
        // Cor padrão é branco, por isso senão quisermos branco fazemos isto
        // Se quiséssemos que o hero fosse um retângulo colocariamos backgroundcolor
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(currentPosition.getX(), currentPosition.getY()), "X");
    }
}
