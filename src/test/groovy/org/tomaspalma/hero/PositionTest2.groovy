package org.tomaspalma.hero

import spock.lang.Specification

class PositionTest2 extends Specification {
    private def position

    def setup() {
        position = new Position(15, 15)
    }

    def "It should detect positons above a certain other position"(int x, int y, boolean z) {
        expect:
            position.isAbove(new Position(x, y)) == z
        where:
            x|y|z
            15|14|false
            15|16|true
    }
    def "It should detect positons left a certain other position"(int x, int y, boolean z) {
        expect:
        position.isLeft(new Position(x, y)) == z
        where:
        x|y|z
        15|20|false
        10|16|false
        16|20|true
    }

    def "It should detect if two positions are equal"() {
        given:
            def equalPos = new Position(15, 15)
            def notEqualPos = new Position(14, 19)
        expect:
            position.equals(equalPos) == true
            position.equals(notEqualPos) == false
    }
}
