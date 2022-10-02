package org.tomaspalma.hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    public Element(int x, int y) {
        currentPosition = new Position(x, y);
    }

    protected void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public abstract void draw(TextGraphics graphics);

    public Position getCurrentPosition() {
        return currentPosition;
    }

    protected Position currentPosition;
}
