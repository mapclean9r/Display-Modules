import core.StartProgram;
import handlers.ModuleHandler;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartProgram window = new StartProgram("Modular Display");
            window.setFullScreen();
            ModuleHandler moduleHandler = new ModuleHandler(window);
        });

    }
}
