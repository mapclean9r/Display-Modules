package core;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CreateGrid extends JPanel {
    private int cols = 3;
    private int rows = 2;
    private int hgap = 16, vgap = 16;
    private Insets padding = new Insets(16,16,16,16);
    private final Map<Point, JComponent> cells = new HashMap<>();
    private Dimension staticCellSize = new Dimension(320, 200);

    public CreateGrid() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(padding));
    }

    /** sets current col, row **/
    public void configureGrid(int cols, int rows) {
        if (cols <= 0 || rows <= 0) throw new IllegalArgumentException("cols/rows must higher than 0");
        this.cols = cols;
        this.rows = rows;
        relayout();
    }

    /** gaps between each grid block **/
    public void setGaps(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
        relayout();
    }

    /** padding for the whole grid **/
    public void setPadding(Insets padding) {
        this.padding = padding != null ? padding : new Insets(0,0,0,0);
        setBorder(new EmptyBorder(this.padding));
        relayout();
    }


    /** Sets component at a fixed row & col. param null to clear the cell. */
    public void setCell(int row, int col, JComponent comp) {
        ensureInBounds(row, col);
        Point key = new Point(col, row);
        if (comp == null) {
            cells.remove(key);
        } else {
            cells.put(key, comp);
        }
        relayout();
    }

    /** Replace only if it already exists, ignore if empty. */
    public void replaceIfPresent(int row, int col, JComponent comp) {
        Point key = new Point(col, row);
        if (cells.containsKey(key)) setCell(row, col, comp);
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
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gbc.gridx = c; gbc.gridy = r;

                JComponent content = cells.get(new Point(c, r));
                JComponent cell = (content == null)
                        ? rigidPlaceholder(staticCellSize)
                        : wrapAsRigidCard(content, staticCellSize);

                add(cell, gbc);
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

    /** component wrapper: min/pref/max to the static cell size */
    private JComponent wrapAsRigidCard(JComponent content, Dimension size) {
        content.setOpaque(false);
        JPanel card = new JPanel(new BorderLayout()) {
            @Override public Dimension getMinimumSize()  { return size; }
            @Override public Dimension getPreferredSize(){ return size; }
            @Override public Dimension getMaximumSize()  { return size; }
        };
        card.setOpaque(false);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    /** empty placeholder to keep grid shape */
    private JComponent rigidPlaceholder(Dimension size) {
        return new JPanel() {
            { setOpaque(false); }
            @Override public Dimension getMinimumSize()  { return size; }
            @Override public Dimension getPreferredSize(){ return size; }
            @Override public Dimension getMaximumSize()  { return size; }
        };
    }

    /** preferred size so the grid doesn't stretch */
    @Override
    public Dimension getPreferredSize() {
        int totalW = padding.left + padding.right  + cols * staticCellSize.width  + (cols - 1) * hgap;
        int totalH = padding.top  + padding.bottom + rows * staticCellSize.height + (rows - 1) * vgap;
        return new Dimension(totalW, totalH);
    }
}
