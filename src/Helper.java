import javax.swing.*;
import java.util.List;

/**
 * A class that describes the gameplay
 * @author Dmitriy Stepanov
 */
public class Helper {
    public static void startNewGame() {
        Logic.setPointsCounter(0);
        AbstractCell.getEmptyCells().clear();
        Graphics.setDefaultLabelsInfo();
        AbstractCell.getCellMap().values().forEach(cell -> {
            cell.release();
            cell.setState(State.EMPTY);
            cell.setIcon(null);
            AbstractCell.getEmptyCells().add(cell);
        });

        initGameProcess();
    }

    public static void initGameProcess() {
        new Thread(() -> {
            try { Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Graphics.getInfoLabel().setText("Start a new game");
            generateRandomImages(Graphics.getInfoLabel().getText(),false, 5);

        }).start();
    }

    public static void generateRandomImages(String textInfo, boolean lineWasDeleted, int amount) {
        if (!lineWasDeleted) {
            Graphics.getInfoLabel().setText(textInfo);
            for (int i = 0; i < amount; i++) {
                Cell cell = getRandomCell( AbstractCell.getEmptyCells());
                int index = (int) (Math.random() * ResourceLoader.BALLS.length);
                cell.setIcon((ImageIcon) ResourceLoader.BALLS[index]);
                cell.setState(State.RELEASED);
                AbstractCell.getEmptyCells().remove(cell);
            }
        }
    }

    private static Cell getRandomCell(List<Cell> freeCells) {
        int index = (int)(Math.random() * freeCells.size());
        return freeCells.get(index);
    }

    public static void accrualPoints(int lineSize) {
        double ratio = 2.1 + (double)(lineSize - 5) / 10;
        int pointsValue = Logic.getPointsCounter() + (int)(lineSize * ratio);
        Logic.setPointsCounter(pointsValue);
        Graphics.getPointsLabel().setText("Points: " + Logic.getPointsCounter());
    }

    public static void checkGameEndingCondition() {
        if (AbstractCell.getEmptyCells().size() <= 3) {
            GameOver.init();
            Graphics.getInfoLabel().setText("Game over!");
        }
    }
}