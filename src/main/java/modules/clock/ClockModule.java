package modules.clock;

import modules.IncludeModule;
import modules.ModuleAlias;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@ModuleAlias(value = "clock-module-default")
public class ClockModule implements IncludeModule {
    @Override
    public JComponent RunModule() {
        var panel = new JPanel(new BorderLayout());

        var clock = new JLabel("clock-module-default", SwingConstants.CENTER);
        clock.setFont(clock.getFont().deriveFont(Font.BOLD, 32f));
        var fmt = new SimpleDateFormat("HH:mm:ss");
        new Timer(1000, e -> clock.setText(fmt.format(new Date()))).start();

        //todo add config file for locations && mby font size?
        panel.add(clock, BorderLayout.CENTER);

        //remove print later mby
        System.out.println("module loaded: " + this.getClass().getAnnotation(ModuleAlias.class).value());
        return panel;
    }

}
