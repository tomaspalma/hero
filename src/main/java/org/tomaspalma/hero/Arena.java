package org.tomaspalma.hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        score = 0;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        //Adicionar paredes em toda a horizontal
        for(int i = 0; i < width; i++) {
            walls.add(new Wall(i, 1, "-"));
            walls.add(new Wall(i, height - 1, "-"));
        }

        //Adicionar paredes em toda a vertical
        for(int i = 2; i < height - 1; i++) {
            walls.add(new Wall(0, i, "|"));
            walls.add(new Wall(width - 1, i, "|"));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        List<Coin> coins = new ArrayList<>();
        for(int i = 0; i < Game.MAX_NO_OF_COINS; i++) {
            Position validPosition = generateValidPositionOf(coins);
            coins.add(new Coin(validPosition.getX(), validPosition.getY()));
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        for(int i = 0; i < Game.MAX_NO_OF_COINS; i++) {
            Position validPosition = generateValidPositionOf(monsters);
            monsters.add(new Monster(validPosition.getX(), validPosition.getY()));
        }
        return monsters;
    }

    private <T extends Element> Position generateValidPositionOf(List<T> elements) {
        Position generatedPosition;
        Random random = new Random();
        while(true) {
            int x = random.nextInt(width - 2) + 1, y = random.nextInt(height - 2) + 1;
            generatedPosition = new Position(x, y);

            if (hero.getCurrentPosition().equals(generatedPosition)) continue;
            else if (elements.size() != 0 && elements.get(elements.size() - 1).getCurrentPosition().equals(generatedPosition))
                continue;

            break;
        }

        return generatedPosition;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));

        // O último parâmetro é o caratér que vai aparecer em cada quadradinho que divide o quadrilátero
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for(Coin coin: coins) {
            coin.draw(graphics);
        }
        hero.draw(graphics);
        for(Monster monster: monsters) {
            monster.draw(graphics);
        }
        for(Wall wall: walls) {
            wall.draw(graphics);
        }


        // Texto de informação sobre o jogador
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(0, 0), "Score: " + score);
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> moveHeroTo(hero.moveUp());
            case ArrowDown -> moveHeroTo(hero.moveDown());
            case ArrowLeft -> moveHeroTo(hero.moveLeft());
            case ArrowRight -> moveHeroTo(hero.moveRight());
        }
    }

    private void retrieveCoins(Position heroPosition) {
        for(int i = 0; i < coins.size(); i++) {
            if(coins.get(i).getCurrentPosition().equals(heroPosition)) {
                coins.remove(i);
                score++;
                break;
            }
        }


    }

    public void moveHeroTo(Position position) {
        if(canHeroMoveTo(position)) {
            hero.setCurrentPosition(position);
            retrieveCoins(position);
        }
    }

    private boolean canHeroMoveTo(Position position) {
        for(Wall wall: walls) {
            if(wall.getCurrentPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public void moveMonsters() {

    }

    private final int width, height;
    private final Hero hero = new Hero(10, 10);
    private final List<Wall> walls;
    private final List<Coin> coins;
    private List<Monster> monsters;
    private int score;
}
