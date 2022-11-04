package org.tomaspalma.hero

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import org.w3c.dom.Text
import spock.lang.Specification

class CoinTest2 extends Specification {
    private def coin;

    def setup() {
        coin = new Coin(20, 20);
    }

    def "Coin draw method should have all its functions and statements inside executed"() {
        given:
            def graphicsStub = Mock(TextGraphics);
        when:
            coin.draw(graphicsStub);
        then:
            1 * graphicsStub.setForegroundColor(TextColor.Factory.fromString(Game.LIGHTYELLOW));
            1 * graphicsStub.enableModifiers(SGR.BOLD);
            1 * graphicsStub.putString(new TerminalPosition(20, 20), "O");
    }

    def "We should be able to set a position for a coin"() {
        given:
            def x = 20;
            def y = 20;
        when:
            coin.setCurrentPosition(x, y);
        then:
            coin.getCurrentPosition() == new Position(x, y);
    }

    def "We should be able to have access to the value of coin position"() {
        when:
            coin.setCurrentPosition(20, 20);
        then:
            coin.getCurrentPosition() == new Position(20, 20);
    }
}
