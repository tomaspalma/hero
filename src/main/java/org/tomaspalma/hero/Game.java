package org.tomaspalma.hero;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 25);
            DefaultTerminalFactory terminalFactory = new
                    DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Limpa o screen e torna-o pronto para a arena ser desenhada
    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException {
        while(arena.isGameSupposedToRun) {
            KeyStroke key;

            draw();
            key = screen.readInput();

            // Encarrega-se de fechar o jogo quando o utilizador assim quiser
            switch (key.getKeyType()) {
                case Character:
                    char character = key.getCharacter();
                    if (character == 'q' || character == 'Q') screen.close();
                    else if(/*arena.arenaStage != Arena.ArenaStages.PLAYING &&*/ (character == 'R' || character == 'r')) {
                        arena.reset();
                    }
                    break;
                case EOF:
                    arena.isGameSupposedToRun = false;
                    break;
                default:
                    arena.processKey(key);
                    break;
            }
        }
        screen.close();
        System.out.println(arena.exitMessage);
    }

    // Enviar a tecla para a lógica da arena para influenciar o jogo mediante a tecla
    private void processKey(KeyStroke key) throws IOException {
        arena.processKey(key);
    }

    //Attributes
    private Screen screen;
    public static int WIDTH = 40, HEIGHT = 25;
    private Arena arena = new Arena(WIDTH, HEIGHT);
    public static int MAX_NO_OF_COINS = 5, MAX_NO_OF_MONSTERS = 5;
    public static String LIGHTGREEN = "#90EE90", LIGHTBLUE = "#336699", LIGHTYELLOW = "#FFFF00", WHITE = "#FFFFFF";
}
