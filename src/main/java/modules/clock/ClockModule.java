package modules.clock;

import modules.IncludeModule;
import modules.ModuleAlias;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ModuleAlias(value = "clock-module-default")
public class ClockModule implements IncludeModule {

    @Override
    public JComponent RunModule() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel("", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        dateLabel.setForeground(new Color(200, 200, 220));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(timeLabel);
        panel.add(dateLabel);

        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            dateLabel.setText(now.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")));
        });
        timer.setInitialDelay(0);
        timer.start();

        return panel;
    }

}
