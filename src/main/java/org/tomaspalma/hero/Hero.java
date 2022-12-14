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
        this.energy = 100;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void decreaseEnergy(int decreaseFactor) {
        energy -= decreaseFactor;
    }

    public Position getCurrentPos() {
        return currentPosition;
    }

    // As quatro seguintes funções retornam a posição hipotética que o herói estivesse se movesse no sentido especificado
    // pelo nome das funções
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

    public String getScoreColor() {
        return scoreColor;
    }

    public void setHeroState(HeroState newHeroState) {
        heroState = newHeroState;
    }

    public TextGraphics getGraphics_() {
        return graphics_;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics_ = graphics;

        if(energy <= 40) scoreColor = Game.LIGHTRED;
        else scoreColor = Game.WHITE;

        graphics.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(currentPosition.getX(), currentPosition.getY()), "X");

        graphics.setForegroundColor(TextColor.Factory.fromString(scoreColor));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(28, 0), "Energy: " + getEnergy());
    }

    public enum HeroState {
        NORMAL,
        HIT_MONSTER,
    }
    private int energy;
    private HeroState heroState = HeroState.NORMAL;
    public String scoreColor;
    private TextGraphics graphics_;
}
