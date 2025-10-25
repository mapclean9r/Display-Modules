package config;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {

    private final Color bgColor = new Color(16, 18, 28);

    public Background() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(bgColor);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }
}
