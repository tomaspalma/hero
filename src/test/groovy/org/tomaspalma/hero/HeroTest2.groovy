package org.tomaspalma.hero

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification

class HeroTest2 extends Specification {
    private def hero;

    def setup() {
        hero = new Hero(20, 20);
    }

    def "Hero should move to the right"() {
        when:
        // It is unecessary to check for negative x coordinates because the her owill never be in that kind of position s
            def newPosOfHero = hero.moveRight();
        then:
            newPosOfHero == new Position(21, 20)
    }

    def "Hero should move to the left"() {
        when:
            def newPosOfHero = hero.moveLeft();
        then:
            newPosOfHero == new Position(19, 20)
    }

    def "Hero should move up"() {
        when:
            def newPosOfHero = hero.moveUp();
        then:
            newPosOfHero == new Position(20, 19);
    }

    def "Hero should move down"() {
        when:
            def newPosOfHero = hero.moveDown();
        then:
            newPosOfHero == new Position(20, 21);
    }

    def "Score color should be red when energy <= 40"() {
        given:
            def graphicsStub = Mock(TextGraphics);
        when:
            hero.setEnergy(40);
            hero.draw(graphicsStub);
        then:
            hero.getScoreColor() == Game.LIGHTRED;

        when:
            hero.setEnergy(20);
            hero.draw(graphicsStub);
        then:
            hero.getScoreColor() == Game.LIGHTRED;
    }

    def "Score color should be white when energy > 40"() {
        given:
            def graphicsStub = Mock(TextGraphics);
        when:
            hero.setEnergy(41);
            hero.draw(graphicsStub);
        then:
            hero.getScoreColor() == Game.WHITE;

        when:
            hero.setEnergy(83);
            hero.draw(graphicsStub);
        then:
            hero.getScoreColor() == Game.WHITE;
    }

    def "Hero draw should execute all the methods it is supposed to"() {
        given:
            def graphicsStub = Mock(TextGraphics);
            def scoreColor = Game.WHITE;
        when:
            hero.draw(graphicsStub);
        then:
            1 * graphicsStub.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
            1 * graphicsStub.enableModifiers(SGR.BOLD);
            1 * graphicsStub.putString(new TerminalPosition(hero.getCurrentPos().getX(), hero.getCurrentPos().getY()), "X");
            1 * graphicsStub.setForegroundColor(TextColor.Factory.fromString(scoreColor));
            1 * graphicsStub.enableModifiers(SGR.BOLD);
            1 * graphicsStub.putString(new TerminalPosition(28, 0), "Energy: " + hero.getEnergy());
    }

}
