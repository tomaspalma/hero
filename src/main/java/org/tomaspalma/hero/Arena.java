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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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

    // Verifica se a posição que um elemento vai ser gerado não vai estar em cima de nenhum outro objeto na arena
    // As paredes não contam por que já estamos a evitá-las com os limites postos na geração aleatória (width - 2) e (height - 2)
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
        // Definir cor do chão da arena
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));

        // O último parâmetro é o caratér que vai aparecer em cada quadradinho que divide o quadrilátero
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        // Colocar as moedas no ecrã
        for(Coin coin: coins) {
            coin.draw(graphics);
        }

        // Colocar o hero no ecrã
        hero.draw(graphics);

        // Colocar os monstros no ecrã
        for(Monster monster: monsters) {
            monster.draw(graphics);
        }

        // Colocar os delimitadores da arena no ecrã
        for(Wall wall: walls) {
            wall.draw(graphics);
        }


        // Colocar no ecrã a indicação da pontuação do jogador
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(0, 0), "Score: " + score);
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHeroTo(hero.moveUp());
                moveMonsters();
                break;
            case ArrowDown:
                moveHeroTo(hero.moveDown());
                moveMonsters();
                break;
            case ArrowLeft:
                moveHeroTo(hero.moveLeft());
                moveMonsters();
                break;
            case ArrowRight:
                moveHeroTo(hero.moveRight());
                moveMonsters();
                break;
        }
    }

    // Se uma moeda estiver na mesma posição que o jogador, remove-a
    private void retrieveCoins(Position heroPosition) {
        for(int i = 0; i < coins.size(); i++) {
            if(coins.get(i).getCurrentPosition().equals(heroPosition)) {
                coins.remove(i);
                score++;
                break;
            }
        }
    }

    private boolean verifyMonsterCollisions(Position heroPosition) {
        for(int i = 0; i < monsters.size(); i++) {
            if(monsters.get(i).getCurrentPosition().equals(heroPosition)) {
                return true;
            }
        }
        return false;
    }

    // Se o herói se poder mover, movemo-lo
    public void moveHeroTo(Position position) {
        if(canHeroMoveTo(position)) {
            hero.setCurrentPosition(position.getX(), position.getY());
            if(verifyMonsterCollisions(hero.getCurrentPosition())) {
                System.exit(0);
            }
            retrieveCoins(hero.getCurrentPosition());
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
        for(int i = 0; i < monsters.size(); i++) {
            monsters.get(i).move();
        }
    }

    private final int width, height;
    private final Hero hero = new Hero(10, 10);
    private final List<Wall> walls;
    private final List<Coin> coins;
    private List<Monster> monsters;
    private int score;
}
