import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A class that describes the cell of the playing field
 * @author Dmitriy Stepanov
 */
public class Cell extends AbstractCell {
    public Cell(int x, int y) {
        setXx(x);
        setYy(y);
    }

    public boolean containsImage() {
        return this.getIcon() != null;
    }     

    @Override
    public void select() {
        if (containsImage()) {
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            setState(State.SELECTED);
            previousCell = this;
        }
    }

    @Override
    public void release() {
        if (containsImage()) {
            setState(State.RELEASED);
        }
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public List<Cell> getNeighbors() {
        List<Cell> neighborsList = new LinkedList<>();
        int gridLength = Board.getGridLength();

        if ((getXx() > 1 && getXx() < gridLength) && (getYy() > 1 && getYy() < gridLength)) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy())));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy())));
        } else if (getXx() > 1 && getXx() < gridLength) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy())));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy())));
            if (getYy() == 1) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            } else if (getYy() == gridLength) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            }
        } else if (getYy() > 1 && getYy() < gridLength) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            if (getXx() == 1) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy())));
            } else if (getXx() == gridLength) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy())));
            }
        } else if (getXx() == 1) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy())));
            if (getYy() == 1) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            } else if (getYy() == gridLength) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            }
        } else if (getXx() == gridLength) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy())));
            if (getYy() == 1) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            } else if (getYy() == gridLength) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            }
        }
        return neighborsList;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell currentCell = this;
        switch (currentCell.getState()) {
            case SELECTED:
                currentCell.release();
                break;
            case RELEASED:
                if (!Objects.isNull(previousCell)) {
                    previousCell.release();
                }
                currentCell.select();
                break;
            case EMPTY:
                if (!Objects.isNull(previousCell) && (previousCell.getState() == State.SELECTED)) {
                    boolean moveComplete = Logic.moveInit(previousCell, currentCell);
                    if (moveComplete) {
                        previousCell.release();
                        previousCell = null;
                    }
                }
                break;
        }
    }
}