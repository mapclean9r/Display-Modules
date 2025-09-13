import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartProgram extends JFrame {
    private GraphicsDevice device;

    public StartProgram(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        // ESC - ask to close app , opens dialog
        var root = getRootPane();
        var im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        var am = root.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "confirm-exit");
        am.put("confirm-exit", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                tryClose();
            }
        });
    }

    // Makes the program fullscreen
    public void setFullScreen() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        setVisible(true);
    }

    // For dialog - ESC - closing app yes/no
    private void tryClose() {
        boolean ok = ConfirmDialogs.showExitDialog(this, "Exit application?", "Are you sure you want to exit?");
        if (ok) {
            // exit fullscreen
            if (device != null && device.getFullScreenWindow() == this) {
                device.setFullScreenWindow(null);
            }
            dispose();
            System.exit(0);
        }
    }
}

