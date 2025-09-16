import config.Config;
import config.ConfigLoader;
import core.StartProgram;
import handlers.ModuleHandler;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Config config = ConfigLoader.loadConfig("/config.json");

            StartProgram window = new StartProgram("Modular Display", config);
            window.setFullScreen();

            new ModuleHandler(window, config);
        });

    }

}
