package core;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CreateGrid extends JPanel {
    private int cols, rows;
    private int heightGap = 16, widthGap = 16;
    private Insets padding = new Insets(16,16,16,16);
    private final Map<Point, JComponent> cells = new HashMap<>();
    private final Dimension cellSize = new Dimension(320, 200);

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
        this.heightGap = hgap;
        this.widthGap = vgap;
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
        // do not delete, mby use for ui later
        Point key = new Point(col, row);
        if (cells.containsKey(key)){
            setCell(row, col, comp);
        }
    }

    /** Clear all cells. */
    public void clear() {
        // do not delete, ui might need it ;)
        cells.clear();
        relayout();
    }

    /** repaints grid **/
    private void relayout() {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(widthGap /2, heightGap /2, widthGap /2, heightGap /2);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gbc.gridx = c; gbc.gridy = r;

                JComponent content = cells.get(new Point(c, r));
                JComponent cell = (content == null)
                        ? fixedSizePlaceholder(cellSize)
                        : wrapAsFixedSizeCard(content, cellSize);

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
    private JComponent wrapAsFixedSizeCard(JComponent content, Dimension size) {
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
    private JComponent fixedSizePlaceholder(Dimension size) {
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
        int totalW = padding.left + padding.right  + cols * cellSize.width  + (cols - 1) * heightGap;
        int totalH = padding.top  + padding.bottom + rows * cellSize.height + (rows - 1) * widthGap;
        return new Dimension(totalW, totalH);
    }
}
