package org.tomaspalma.hero

import spock.lang.Specification

class ArenaTest2 extends Specification {
    private def arena;

    def setup() {
        arena = new Arena(40, 40);
        arena.getCoins().clear()
    }

    def "Arena should generate Game.MAX_NO_OF_COINS"() {
        when:
            arena.createCoins()
        then:
            arena.getCoins().size() == Game.MAX_NO_OF_COINS
    }

    def "Hero should have his energy decreased"() {

    }

    /*def "Arena should be able to move its monsters"() {
        given:
            def currentMonsters = new (arena.getMonsters().
            def equalPos = false
        when:
            arena.moveMonsters()
            def newMonsters = arena.getMonsters()
        then:
            for(int i = 0; i < newMonsters.size(); i++) {
                if(newMonsters.get(i).getCurrentPosition().equals(currentMonsters.get(i).getCurrentPosition())) {
                    //equalPos = true
                }
            }
        expect:
            equalPos == false
    } */
}
