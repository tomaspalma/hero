package org.tomaspalma.hero

import spock.lang.Specification

import javax.swing.KeyStroke

class GameTest2 extends Specification {
    private def game;

    def setup() {
       game = new Game();
    }

    def "Draw function of game should invoke all of its methdods"() {
        when:
            game.draw();
        then:
            0 * game.getScreen().clear();
    }

    def "Run should not execute game logic when game is not supposed to run"() {
        given:
            game.getArena().isGameSupposedToRun = false;
        when:
            game.run();
        then:
            0 * game.draw();
    }

}
