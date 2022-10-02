//zpackage org.tomaspalma.hero;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.tomaspalma.hero.Game;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.run();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}