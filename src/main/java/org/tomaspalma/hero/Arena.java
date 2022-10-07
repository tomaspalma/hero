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
        createCoins();
        createMonsters();
        this.isGameSupposedToRun = true;
        exitMessage = "";
        score = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Clear arena to be drawn again
    public void reset() {
        coins.clear(); createCoins();
        monsters.clear(); createMonsters();
        score = 0; hero.setEnergy(100);
        arenaStage = ArenaStages.PLAYING;
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

    private void createCoins() {
        Random random = new Random();
        for(int i = 0; i < Game.MAX_NO_OF_COINS; i++) {
            Position generatedPosition = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 2);
            if(i > 0) {
                while(!isValidPosition(generatedPosition)) {
                    generatedPosition = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) +  2);
                }
            }
            coins.add(new Coin(generatedPosition.getX(), generatedPosition.getY()));
        }
    }

    private void createMonsters() {
        Random random = new Random();
        for(int i = 0; i < Game.MAX_NO_OF_MONSTERS; i++) {
            Position generatePosition = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 2);
            while(!isValidPosition(generatePosition)) {
                generatePosition = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 2);
            }
            monsters.add(new Monster(generatePosition.getX(), generatePosition.getY()));
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

    // Verifica se a posição que um elemento vai ser gerado não vai estar em cima de nenhum outro objeto na arena
    // As paredes não contam por que já estamos a evitá-las com os limites postos na geração aleatória (width - 2) e (height - 2)
    private boolean isValidPosition(Position position) {
        Random random = new Random();

        if (hero.getCurrentPosition().equals(position)) return false;

        for(int i = 0; i < monsters.size(); i++) {
            if(monsters.get(i).getCurrentPosition().equals(position)) return false;
        }

        for(int i = 0; i < coins.size(); i++) {
            if(coins.get(i).getCurrentPosition().equals(position)) return false;
        }

        if (position.getX() == 0 || position.getX() == width - 1 || position.getY() == 1 || position.getY() == height - 1) {
            return false; 
        }


        return true;
    }

    public void draw(TextGraphics graphics) {

        // Definir cor do chão da arena
        graphics.setBackgroundColor(TextColor.Factory.fromString(Game.LIGHTBLUE));

        // O último parâmetro é o caratér que vai aparecer em cada quadradinho que divide o quadrilátero
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        switch(arenaStage) {
            case PLAYING:
                // Colocar as moedas no ecrã
                for(Coin coin: coins) {
                    coin.draw(graphics);
                }

                // Colocar os monstros no ecrã
                for(Monster monster: monsters) {
                    monster.draw(graphics);
                    if(Game.IS_DEBUGGING) System.out.println("Monster position: " + monster.getCurrentPosition().getX() + ", " + monster.getCurrentPosition().getY());
                }

                // Colocar o hero no ecrã
                hero.draw(graphics);

                // Colocar os delimitadores da arena no ecrã
                for(Wall wall: walls) {
                    wall.draw(graphics);
                }

                if(Game.IS_DEBUGGING) {
                    System.out.println("Hero: " + hero.getCurrentPosition().getX() + ", " + hero.getCurrentPosition().getY());
                    System.out.println("-------------------");
                }
                break;
            case LOST:
                graphics.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
                graphics.enableModifiers(SGR.BOLD);
                graphics.putString(new TerminalPosition(3, 10), "You lost you energy! You lose!");
                graphics.putString(new TerminalPosition(5, 11), "Press R to restart or Q to quit");
                break;
            case WON:
                graphics.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
                graphics.enableModifiers(SGR.BOLD);
                graphics.putString(new TerminalPosition(5, 10), "You collected all the coins!");
                graphics.putString(new TerminalPosition(4, 11), "You won!");
                graphics.putString(new TerminalPosition(5, 12), "Press R to restart or Q to quit");
                break;
        }

        // Colocar no ecrã a indicação da pontuação do jogador
        graphics.setForegroundColor(TextColor.Factory.fromString(Game.WHITE));
                graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(0, 0), "Score: " + score);
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHeroTo(hero.moveUp());
                break;
            case ArrowDown:
                moveHeroTo(hero.moveDown());
                break;
            case ArrowLeft:
                moveHeroTo(hero.moveLeft());
                break;
            case ArrowRight:
                moveHeroTo(hero.moveRight());
                break;
        }
        if(coins.size() == 0) {
            arenaStage = ArenaStages.WON;
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

    public void moveMonsters() {
        for(int i = 0; i < monsters.size(); i++) {
            monsters.get(i).move(hero.getCurrentPosition(), monsters, width, height);
        }
    }

    // Se o herói se poder mover, movemo-lo
    public void moveHeroTo(Position position) {
        if(canHeroMoveTo(position)) {
            if(verifyMonsterCollisions(position)) {
                decreaseHeroEnergy();
                hero.setHeroState(Hero.HeroState.HIT_MONSTER);
            } else {
                hero.setCurrentPosition(position.getX(), position.getY());
                moveMonsters();
            }
            retrieveCoins(hero.getCurrentPosition());
        }
    }

    public void decreaseHeroEnergy() {
        hero.decreaseEnergy(decreaseEnergyFactor);
        if(hero.getEnergy() == 0) arenaStage = ArenaStages.LOST;
    }

    private boolean canHeroMoveTo(Position position) {
        if (position.getX() == 0 || position.getX() == width - 1 || position.getY() == 1 || position.getY() == height - 1) {
            return false; 
        }

        return true;
    }

    public enum ArenaStages {
        LOST,
        WON,
        PLAYING
    }

    private final int width, height, decreaseEnergyFactor = 20;
    private final Hero hero = new Hero(10, 10);
    private final List<Wall> walls;
    private List<Coin> coins = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();
    private int score;
    public boolean isGameSupposedToRun;
    public String exitMessage;
    public ArenaStages arenaStage = ArenaStages.PLAYING;
}
