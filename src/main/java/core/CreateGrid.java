package core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CreateGrid extends JPanel {
    private int cols = 4;
    private int rows = 2;
    private int hgap = 16, vgap = 16;
    private Insets padding = new Insets(16,16,16,16);
    private final Map<Point, JComponent> cells = new HashMap<>();

    public CreateGrid() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(padding));
    }

    /** sets current col, row **/
    public void configureGrid(int cols, int rows) {
        if (cols <= 0 || rows <= 0) throw new IllegalArgumentException("cols/rows must higher than 0");
        this.cols = cols; this.rows = rows;
        relayout();
    }

    /** gaps between each grid block **/
    public void setGaps(int hgap, int vgap) {
        this.hgap = hgap; this.vgap = vgap;
        relayout();
    }

    /** padding for each grid block **/
    public void setPadding(Insets padding) {
        this.padding = padding;
        setBorder(new EmptyBorder(padding));
        relayout();
    }

    /** Sets component at a fixed row & col. param null to clear the cell. */
    public void setCell(int row, int col, JComponent comp) {
        ensureInBounds(row, col);
        Point key = new Point(col, row);
        if (comp == null) {
            cells.remove(key);
        } else {
            cells.put(key, wrapAsCard(comp));
        }
        relayout();
    }

    /** Replace only if it already exists, ignore if empty. */
    public void replaceIfPresent(int row, int col, JComponent comp) {
        Point key = new Point(col, row);
        if (cells.containsKey(key)) {
            setCell(row, col, comp);
        }
    }

    /** Clear all cells. */
    public void clear() {
        cells.clear();
        relayout();
    }

    /** repaints grid **/
    private void relayout() {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(vgap/2, hgap/2, vgap/2, hgap/2);
        gbc.weightx = 1; gbc.weighty = 1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gbc.gridx = c; gbc.gridy = r;
                JComponent cell = cells.get(new Point(c, r));
                if (cell == null) {
                    JPanel placeholder = new JPanel();
                    placeholder.setOpaque(false);
                    add(placeholder, gbc);
                } else {
                    add(cell, gbc);
                }
            }
        }

        revalidate();
        repaint();
    }

    /** Validates a valid grid .-. **/
    private void ensureInBounds(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Cell ("+row+","+col+") out of "+rows+"x"+cols+" grid");
        }
    }

    /** component wrapper **/
    private JComponent wrapAsCard(JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        content.setOpaque(false);
        card.add(content, BorderLayout.CENTER);
        return card;
    }
}
