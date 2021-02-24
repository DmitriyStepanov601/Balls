import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A class that describes the game interface
 * @author Dmitriy Stepanov
 */
public class Game extends JFrame {

    /**
     * Constructor - creating a new game interface
     * @param width - the width of the window
     * @param height - the height of the window
     * @param gridWidth - width of the playing field
     * @param gridHeight - height of the playing field
     * @see Game#Game(int,int,int,int)
     */
    public Game(int width, int height, int gridWidth, int gridHeight) {
        super("Balls");
        setIconImage(ResourceLoader.getImageIcon());
        windowClosingSetUp();
        JPanel gridPanel = new JPanel();
        Board.getInstance().createGrid(gridWidth, gridHeight, gridPanel);
        Graphics.getInstance().createGraphics(this, width, height, gridPanel);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void windowClosingSetUp() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Notification.getOptionPane();
            }
        });
    }

    public static void main(String[] args) {
        new Game(600,630,10,10);
    }
}