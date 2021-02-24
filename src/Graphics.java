import javax.swing.*;
import java.awt.*;

/**
 * A class that describes the game's graphical interface
 * @author Dmitriy Stepanov
 */
public class Graphics {
    private static JLabel infoLabel;
    private static JLabel pointsLabel;
    private static final String DEFAULT_POINTS_VALUE = "Points: 0";

    public static JLabel getInfoLabel() { return infoLabel; }
    public static JLabel getPointsLabel() {
        return pointsLabel;
    }
    public static Graphics getInstance() { return new Graphics(); }
    public static void setDefaultLabelsInfo() {
        pointsLabel.setText(DEFAULT_POINTS_VALUE);
    }

    public void createGraphics(Game panel, int width, int height, JPanel grid) {
        infoLabel = new JLabel("Start a new game.");
        pointsLabel = new JLabel(DEFAULT_POINTS_VALUE);
        Font labelFont = new Font("", Font.BOLD, 16);
        infoLabel.setFont(labelFont);
        pointsLabel.setFont(labelFont);

        JPanel pointsPanel = new JPanel();
        JPanel ballsPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel northPanel = new JPanel();

        pointsPanel.add(pointsLabel);
        northPanel.setLayout(new BorderLayout());
        northPanel.add(pointsPanel, BorderLayout.WEST);
        northPanel.add(ballsPanel, BorderLayout.EAST);
        southPanel.add(infoLabel);
        panel.pack();
        panel.setLocation(500, 100);
        panel.setSize(width, height);

        panel.getContentPane().add(BorderLayout.SOUTH, southPanel);
        panel.getContentPane().add(BorderLayout.CENTER, grid);
        panel.getContentPane().add(BorderLayout.NORTH, northPanel);
        panel.setResizable(false);
        panel.setVisible(true);
        Helper.initGameProcess();
    }
}