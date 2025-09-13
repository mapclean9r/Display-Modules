package modules.helloworld;

import modules.IncludeModule;
import modules.ModuleAlias;

import javax.swing.*;
import java.awt.*;

@ModuleAlias(value = "hello-module-default")
public class HelloModule implements IncludeModule {

    @Override
    public JComponent RunModule() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel("Hello world!", SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        label.setForeground(Color.WHITE);

        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

}
