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
            TerminalSize terminalSize = new TerminalSize(40, 20);
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

    public void setGameSupposedToRun(boolean gameSupposedToRun) {
        isGameSupposedToRun = gameSupposedToRun;
    }

    //Methods
    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException {
        while(isGameSupposedToRun) {
            KeyStroke key;

            draw();
            key = screen.readInput();

            switch(key.getKeyType()) {
                case Character:
                    char character = key.getCharacter();
                    if (character == 'q' || character == 'Q') screen.close();
                    break;
                case EOF:
                    isGameSupposedToRun = false;
                    break;
                default:
                    arena.processKey(key);
            }
        }
    }

    // Processar uma tecla lida pelo programada
    private void processKey(KeyStroke key) throws IOException {
        arena.processKey(key);
    }

    //Attributes
    private Screen screen;
    private boolean isGameSupposedToRun = true;
    private Arena arena = new Arena(40, 20);
    public static int MAX_NO_OF_COINS = 5;
}
