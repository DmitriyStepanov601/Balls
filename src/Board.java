import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class describing the playing field
 * @author Dmitriy Stepanov
 */
public class Board {
    private static JLabel[][] grid;

    public static int getGridLength() {
        return grid.length;
    }
    public static Board getInstance() {
        return new Board();
    }

    public void createGrid(int gridWidth, int gridHeight, JPanel gridPanel) {
        gridPanel.setLayout(new GridLayout(gridWidth, gridHeight));
        grid = new Cell[gridWidth][gridHeight];
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = gridHeight; y >= 1; y--) {
            for (int x = 1; x <= gridWidth; x++) {
                Cell createdCell = new Cell(x, y);
                initializeCell(createdCell, gridPanel, lineBorder);
            }
        }
    }

    private void initializeCell(Cell newCell, JPanel gridPanel, Border lineBorder) {
        int x = newCell.getXx();
        int y = newCell.getYy();

        AbstractCell.getCellMap().put(newCell.getCoordinates(), newCell);
        newCell.setBorder(lineBorder);
        newCell.setVerticalAlignment(SwingConstants.CENTER);
        newCell.setHorizontalAlignment(SwingConstants.CENTER);
        newCell.setBackground(Color.WHITE);
        newCell.setOpaque(true);

        gridPanel.add(newCell);
        newCell.setState(State.EMPTY);
        grid[--x][--y] = newCell;
        AbstractCell.getEmptyCells().add(newCell);
    }
}