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
    //Methods
    private void draw() throws IOException {
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }

    public void run() throws IOException {
        while(isGameSupposedToRun) {
            KeyStroke key;

            draw();
            key = screen.readInput(); processKey(key);
        }
    }

    // Processar uma tecla lida pelo programada
    private void processKey(KeyStroke key) throws IOException {
        switch(key.getKeyType()) {
            case ArrowUp:
                y--; // o eixo dos y esta invertido
                break;
            case ArrowDown:
                y++;
                break;
            case ArrowLeft:
                x--;
                break;
            case ArrowRight:
                x++;
                break;
            case Character:
                char character = key.getCharacter();
                if(character == 'q' || character == 'Q') screen.close();
                break;
            case EOF:
                isGameSupposedToRun = false;
                break;
        }
    }

    //Attributes
    private Screen screen;
    private int x = 10, y = 10;
    private boolean isGameSupposedToRun = true;
}
