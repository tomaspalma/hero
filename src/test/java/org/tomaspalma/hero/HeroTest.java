package org.tomaspalma.hero;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

public class HeroTest {
    Hero hero;
    Game game = new Game();

    @BeforeEach
    public void generateMockGame() {
        Game game = new Game();
        hero = new Hero(50, 50);
    }

    @Test
    public void moveUpTest() {
        Position expectedPosition = new Position(50, 49);
        Position result = hero.moveUp();
        Assertions.assertEquals(expectedPosition, result);
    }

    @Test
    public void moveDownTest() {
        Position expectedResult = new Position(50, 51);
        Position result = hero.moveDown();
        Assertions.assertEquals(expectedResult, result);
    }

    //Test IF ENERGY IS LESS THAN OR EQUAL TO 40 IF IT SETS TO RED, OTHERWISE SET TO WHITE
    @Test
    public void heroDrawChangeColor() {
        hero.setEnergy(40);
        hero.draw(game.getScreen().newTextGraphics());
        Assertions.assertEquals(hero.getScoreColor(), Game.LIGHTRED);
        hero.setEnergy(39);
        hero.draw(game.getScreen().newTextGraphics());
        Assertions.assertEquals(hero.getScoreColor(), Game.LIGHTRED);
        hero.setEnergy(41);
        hero.draw(game.getScreen().newTextGraphics());
        Assertions.assertEquals(hero.getScoreColor(), Game.WHITE);
        hero.setEnergy(60);
        hero.draw(game.getScreen().newTextGraphics());
        Assertions.assertEquals(hero.getScoreColor(), Game.WHITE);
        hero.setEnergy(20);
        hero.draw(game.getScreen().newTextGraphics());
        Assertions.assertEquals(hero.getScoreColor(), Game.LIGHTRED);
    }

    @Test

}
