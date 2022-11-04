package org.tomaspalma.hero

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor;
import spock.lang.Specification

class WallTest extends Specification {
    private def wall;

    def setup() {
        wall = new Wall(20, 20, "#");
    }

    def "Methods inside draw of wall draw function should all be invoked"() {
        given:
            def graphicsStub = Mock(TextGraphics)
        when:
            wall.draw(graphicsStub);
        then:
            1 * graphicsStub.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
            1 * graphicsStub.enableModifiers(SGR.BOLD);
            1 * graphicsStub.putString(new TerminalPosition(20, 20), "#");
    }
}
