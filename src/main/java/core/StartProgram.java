package core;

import config.Background;
import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartProgram extends JFrame {
    private GraphicsDevice device;
    private final CreateGrid grid = new CreateGrid();

    public StartProgram(String title, Config config) {
        super(title);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        Background background = new Background();
        background.setLayout(new BorderLayout());
        setContentPane(background);

        var root = getRootPane();
        var im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        var am = root.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "confirm-exit");
        am.put("confirm-exit", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                closeAppDialogOption();
            }
        });

        grid.setOpaque(false);
        grid.configureGrid(config.getGrid().getCols(), config.getGrid().getRows());
        grid.setPadding(new Insets(24, 24, 24, 24));
        grid.setGaps(24, 24);

        JScrollPane scroll = new JScrollPane(
                grid,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        background.add(scroll, BorderLayout.CENTER);
    }

    /** Sets the screen to fullscreen based on local device **/
    public void setFullScreen() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);
        setVisible(true);
    }

    /** Displays the module to the specific grid placement **/
    public void setModuleAt(int row, int col, JComponent moduleUI) {
        grid.setCell(row, col, moduleUI);
    }

    /** For dialog - closing app yes/no **/
    private void closeAppDialogOption() {
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

