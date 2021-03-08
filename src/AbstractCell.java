import javafx.util.Pair;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that describes the pattern of the playing field cell
 * @author Dmitriy Stepanov
 */
public abstract class AbstractCell extends JLabel implements Clickable {
    private int Xx;
    private int Yy;

    private static final Map<Pair<Integer, Integer>, Cell> cellMap = new HashMap<>();
    private static final List<Cell> emptyCells = new ArrayList<>();
    public static Cell previousCell;
    private State state;

    public AbstractCell() {
        addMouseListener(this);
    }
    public static Map<Pair<Integer, Integer>, Cell> getCellMap() {
        return cellMap;
    }
    public static List<Cell> getEmptyCells() {
        return emptyCells;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public int getXx() {
        return Xx;
    }
    void setXx(int xx) {
        Xx = xx;
    }
    public int getYy() {
        return Yy;
    }
    void setYy(int yy) {
        Yy = yy;
    }

    public String getImageColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    public Pair<Integer, Integer> getCoordinates() {
        return new Pair<>(getXx(), getYy());
    }
    public abstract List<? extends JLabel> getNeighbors();

    @Override
    public boolean equals(Object obj) {
        boolean value = false;
        if (!(obj instanceof JButton) && !(obj instanceof JPanel)) {
            try {
                Cell other = (Cell) obj;
                value = (this.Xx == other.getXx() && this.Yy == other.getYy() );
            } catch (ClassCastException e) {
                e.getMessage();
            }
        }
        return value;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime + result + this.Xx;
        result = prime + result + this.Yy;
        return result;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }
}