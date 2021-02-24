import javax.swing.*;

/**
 * The final class that describes the actions when closing the window
 * @author Dmitriy Stepanov
 */
final class Notification {
    private Notification() {
        UIManager.put("OptionPane.yesButtonText", "Continue");
        UIManager.put("OptionPane.noButtonText", "Finish");

        ImageIcon iconImg = new ImageIcon(ResourceLoader.loadImage("/notif.png"));
        int res = JOptionPane.showConfirmDialog(Game.getFrames()[0],
                "Are you sure you want to quit the game?", "Notification",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, iconImg);

        if (res == JOptionPane.NO_OPTION ) {
            System.exit(0);
        }
    }

    public static void getOptionPane() {
        new Notification();
    }
}