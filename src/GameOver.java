import javax.swing.*;
import java.awt.*;

/**
 * A class that describes the end-of-game dialog box
 * @author Dmitriy Stepanov
 */
public class GameOver extends JDialog {
    private static GameOver modal;

    public static void init() {
        new GameOver();
    }

    /**
     * Constructor - creating a new dialog box
     * @see GameOver#GameOver()
     */
    private GameOver() {
        super(Game.getFrames()[0], "Game Over", true);
        modal = this;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addComponentsToPane(getContentPane());
        setSize(350, 240);
        setLocationRelativeTo(Game.getFrames()[0]);
        setResizable(false);
        setVisible(true);
    }

    private static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JPanel messagePane = new JPanel();

        String text = "<html><center><H1>Congratulations!</H1></center>" +
                "<center>You have scored " + Logic.getPointsCounter() + " points.</center>" +
                "<center>There are no empty cells left in the field.</center>" +
                "<center><H2>Select the next action:<H2></center></html>";
        JLabel labelText = new JLabel(text);
        labelText.setFont(new Font("", Font.PLAIN, 17));
        labelText.setHorizontalAlignment(SwingConstants.CENTER);
        labelText.setVerticalAlignment(SwingConstants.TOP);
        labelText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        messagePane.add(labelText);

 		JButton btnNewGame = new JButton("new Game");
        btnNewGame.setLayout(new FlowLayout());
        btnNewGame.setBackground(Color.lightGray);
        btnNewGame.addActionListener(e -> {
            Helper.startNewGame();
            modal.dispose();
        });
        messagePane.add(btnNewGame);

        JButton btnExit = new JButton("Exit");
        btnExit.setLayout(new FlowLayout());
        btnExit.setBackground(Color.lightGray);
        btnExit.addActionListener(e -> Notification.getOptionPane());
        messagePane.add(btnExit);
        pane.add(messagePane);
    }
}