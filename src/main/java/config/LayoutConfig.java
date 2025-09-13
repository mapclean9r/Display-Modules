package config;

import java.util.List;

public class LayoutConfig {
    private Grid grid;
    private List<Placement> placements;

    public Grid getGrid() { return grid; }
    public void setGrid(Grid grid) { this.grid = grid; }

    public List<Placement> getPlacements() { return placements; }
    public void setPlacements(List<Placement> placements) { this.placements = placements; }

    public static class Grid {
        private int cols;
        private int rows;

        public int getCols() { return cols; }
        public void setCols(int cols) { this.cols = cols; }

        public int getRows() { return rows; }
        public void setRows(int rows) { this.rows = rows; }
    }

    public static class Placement {
        private String alias;
        private int row;
        private int col;

        public String getAlias() { return alias; }
        public void setAlias(String alias) { this.alias = alias; }

        public int getRow() { return row; }
        public void setRow(int row) { this.row = row; }

        public int getCol() { return col; }
        public void setCol(int col) { this.col = col; }
    }
}