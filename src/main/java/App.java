import config.ConfigLoader;
import config.ModuleConfig;
import core.StartProgram;
import handlers.ModuleHandler;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModuleConfig configLoader = ConfigLoader.loadConfig("src/main/resources/module-config.json");


            StartProgram window = new StartProgram("Modular Display");
            window.setFullScreen();

            ModuleHandler moduleHandler = new ModuleHandler(window, configLoader);
        });

    }
}
