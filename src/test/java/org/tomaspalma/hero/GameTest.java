package org.tomaspalma.hero;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GameTest {
    Game game;

    @BeforeEach
    public void generateGame() {
        game = new Game();
    }


    /*@Test
    public void screenClosesAfterGameEnd() {
        game.getArena().isGameSupposedToRun = false;
        try {
            game.run();
        } catch(IOException e) {
            e.printStackTrace();
        }
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> game.getScreen());
    } */
}
