import config.ConfigLoader;
import config.LayoutConfig;
import config.ModuleConfig;
import core.StartProgram;
import handlers.ModuleHandler;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModuleConfig moduleConfig = ConfigLoader.loadConfig("/modules-config.json");
            LayoutConfig layoutConfig = ConfigLoader.loadLayout("/modules-layout.json");

            StartProgram window = new StartProgram("Modular Display", layoutConfig);
            window.setFullScreen();

            ModuleHandler moduleHandler = new ModuleHandler(window, moduleConfig, layoutConfig);
        });

    }

}
