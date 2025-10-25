package core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ExitDialog {
    public static boolean showExitDialog(JFrame parent, String title, String message) {
        JDialog dialog = new JDialog(parent, true);
        dialog.setUndecorated(true);
        dialog.setSize(360, 220);
        dialog.setLocationRelativeTo(parent);

        // Top container inside dialog box
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 35));
        panel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1, true));

        // Title of dialog box
        JLabel dialogTitle = new JLabel(title, SwingConstants.CENTER);
        dialogTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        dialogTitle.setForeground(Color.WHITE);

        // Content/message of dialog box
        JLabel dialogMessage = new JLabel(message, SwingConstants.CENTER);
        dialogMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dialogMessage.setForeground(new Color(200, 200, 210));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(dialogTitle);
        textPanel.add(dialogMessage);

        // Buttons - change colors mby
        JButton btnYes = dialogButton("Yes", new Color(220, 20, 60));
        JButton btnNo = dialogButton("No", new Color(70, 130, 180));

        final boolean[] result = {false};

        btnYes.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        btnNo.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(25, 25, 35));
        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);

        // ESC - while inside of dialog - closes dialog.
        InputMap inputMap = dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = dialog.getRootPane().getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "closeDialog");
        actionMap.put("closeDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);

        return result[0];
    }

    private static JButton dialogButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));

        // hover && !hover effects over yes/no
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }
}