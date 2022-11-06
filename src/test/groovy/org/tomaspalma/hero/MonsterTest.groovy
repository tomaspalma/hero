package org.tomaspalma.hero

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import org.w3c.dom.Text
import spock.lang.Specification

class MonsterTest extends Specification {
    private def monster;

    def setup() {
        monster = new Monster(20, 20)
    }

    def "Monster should draw itself"() {
        given:
            def graphicsStub = Mock(TextGraphics)
        when:
            monster.draw(graphicsStub)
        then:
            1 * graphicsStub.setForegroundColor(TextColor.Factory.fromString(Game.LIGHTGREEN));
            1 * graphicsStub.enableModifiers(SGR.BOLD);
            1 * graphicsStub.putString(new TerminalPosition(monster.getCurrentPosition().getX(), monster.getCurrentPosition().getY()), "M");
    }

}
