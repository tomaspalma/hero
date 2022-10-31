package org.tomaspalma.hero

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

    def "Hero draw should execute all the methods it is supposed to"() {
        when:

    }

}
