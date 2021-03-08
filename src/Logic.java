import javafx.util.Pair;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A class that describes the game logic
 * @author Dmitriy Stepanov
 */
public class Logic {
    private static int pointsCounter;
    private static int sideLength;
    private static boolean moveAbility;
    private final Cell targetCell;
    private final List<Cell> visited;
    private final Queue<Cell> queue;
    private boolean lineState;

    public static void setPointsCounter(int pointsCounter) {
        Logic.pointsCounter = pointsCounter;
    }
    public static int getPointsCounter() {
        return pointsCounter;
    }
    private void setLineState(boolean lineState) {
        this.lineState = lineState;
    }
    private boolean getLineState() { return lineState; }

    /**
     * Constructor - creating a new logic
     * @param filledCell - filled cell the playing field
     * @param emptyCell - empty cell the playing field
     * @see Logic#Logic(Cell,Cell)
     */
    private Logic(Cell filledCell, Cell emptyCell) {
        sideLength = Board.getGridLength();
        targetCell = emptyCell;
        visited = new ArrayList<>();
        queue = new LinkedList<>();
        setLineState(false);
        moveAbility = traverse(filledCell);
        makeMove(filledCell, emptyCell);
    }

    private void makeMove(Cell filledCell, Cell emptyCell) {
        if (moveAbility) {
            new Thread(() -> {
                moveImageCell(filledCell, emptyCell);
                try { Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                linesSearch();
                Helper.generateRandomImages("   ", getLineState(), 3);
                Helper.checkGameEndingCondition();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                linesSearch();
            }).start();
        }
    }

    public static boolean moveInit(Cell filledCell, Cell emptyCell) {
        new Logic(filledCell, emptyCell);
        return moveAbility;
    }

    private void linesSearch() {
        perpendicularSearch(false);
        perpendicularSearch(true);

        for (int x = 5; x <= sideLength ; x++) {
            diagonallyLines_1( x, false);
            diagonallyLines_1( x, true);
        }

        for (int x = sideLength - 4; x >= 2; x--) {
            diagonallyLines_2( x, false);
            diagonallyLines_2( x, true);
        }
    }

    private void perpendicularSearch(boolean isVertical) {
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) ->
                isVertical ? (curr.getYy() + 1 == next.getYy()) : (curr.getXx() + 1 == next.getXx());
        for (int x = 1; x <= sideLength; x++) {
            Map<String, List<Cell>>  colorSequenceMap = new HashMap<>();

            for (int y = 1; y <= sideLength; y++) {
                Cell nextCell = isVertical ? AbstractCell.getCellMap().get(new Pair<>(x, y)) :
                        AbstractCell.getCellMap().get(new Pair<>(y, x));
                String color = nextCell.containsImage() ? nextCell.getImageColor() : "";
                List<Cell> images = color.isEmpty() ? null : colorSequenceMap.get(color);
                images = Objects.isNull(images) ? new ArrayList<>() : colorSequenceMap.get(color);
                images.add(nextCell);

                if (!color.isEmpty()) {
                    colorSequenceMap.put(color, images);
                }
            }
            colorSequenceMap.values().stream().filter(element -> element.size() >= 5)
                    .forEach(collection -> prepareLineSequence(collection, searchPredicate));
        }
    }

    private void diagonallyLines_1(int start_X, boolean isOpposite) {
        Function<Integer, Integer> linearFunction = number -> -1 * number + start_X + 1;
        Function<Integer, Integer> oppositeLinearFunction = number -> sideLength + (number - start_X);
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) -> curr.getXx() - 1 == next.getXx();
        Map<String, List<Cell>> colorSequenceMap = new HashMap<>();

        for (int x = start_X; x >= 1; x--) {
            lineSequence(x, isOpposite, linearFunction, oppositeLinearFunction, colorSequenceMap);
        }

        colorSequenceMap.values().stream().filter(element -> element.size() >= 5)
                .forEach(collection -> prepareLineSequence(collection, searchPredicate));
    }

    private void diagonallyLines_2(int start_X, boolean isOpposite) {
        Function<Integer, Integer> function = number -> Math.abs(number - (start_X - 1) - (sideLength + 1));
        Function<Integer, Integer> oppositeFunction = number -> number - start_X + 1;
        BiPredicate<Cell, Cell> searchPredicate = (curr, next) -> curr.getXx() + 1 == next.getXx();
        Map<String, List<Cell>> colorSequenceMap = new HashMap<>();

        for (int x = start_X; x <= sideLength; x++) {
            lineSequence(x, isOpposite, function, oppositeFunction, colorSequenceMap);
        }

        colorSequenceMap.values().stream().filter(element -> element.size() >= 5)
                .forEach(collection -> prepareLineSequence(collection, searchPredicate));
    }

    private void lineSequence(int x, boolean isOpposite, Function<Integer, Integer> function,
            Function<Integer, Integer> oppositeFunction, Map<String, List<Cell>> sequenceMap) {
        int y = isOpposite ? oppositeFunction.apply(x) : function.apply(x);
        Cell nextCell = AbstractCell.getCellMap().get(new Pair<>(x, y));
        String color = nextCell.containsImage() ? nextCell.getImageColor() : "";
        List<Cell> images = color.isEmpty() ? null : sequenceMap.get(color);
        images = Objects.isNull(images) ? new ArrayList<>() : sequenceMap.get(color);
        images.add(nextCell);

        if (!color.isEmpty()) {
            sequenceMap.put(color, images);
        }
    }

    private void prepareLineSequence(List<Cell> sequence, BiPredicate<Cell, Cell> predicate) {
        Map<Integer, Set<Cell>> map = new HashMap<>();
        Set<Cell> cellSet = new HashSet<>();
        Cell current = sequence.get(0);
        cellSet.add(current);
        int key = 1;

        for (int i = 0; i < sequence.size() - 1; i++) {
            Cell next = sequence.get(i + 1);
            if (predicate.test(current, next)) {
                cellSet.add(next);
            } else {
                cellSet = new HashSet<>();
                cellSet.add(next);
                key++;
            }

            if (!cellSet.isEmpty()) {
                map.put(key, cellSet);
            }
            current = next;
        }

        map.values().stream().filter( element -> element.size() >= 5).forEach(this::deleteImagesFromCells);
    }

    private void deleteImagesFromCells(Collection<Cell> line) {
        setLineState(true);
        line.forEach(cell -> { cell.setIcon(null);
            cell.setState(State.EMPTY);
            AbstractCell.getEmptyCells().add(cell);
        });
        Helper.accrualPoints(line.size());
        line.clear();
    }

    private boolean traverse(Cell node) {
        Function<Cell, List<Cell>> findNeighbors = (cell) -> Objects.isNull(cell) ?
                Collections.emptyList() : cell.getNeighbors();
        Predicate<Cell> predicate = child -> child.getState() == State.EMPTY && !visited.contains(child);
        findNeighbors.apply(node).stream().filter(predicate).forEach(child -> {
            visited.add(child);
            queue.offer(child);
            traverse( queue.poll() );
        });
        return visited.contains(targetCell);
    }

    private void moveImageCell(Cell previousCell, Cell currentCell) {
        String pictureColor = previousCell.getImageColor();
        currentCell.setIcon(ResourceLoader.ballsMap().get(pictureColor));
        previousCell.setIcon(null);
        previousCell.setState(State.EMPTY);
        currentCell.setState(State.RELEASED);
        AbstractCell.getEmptyCells().add(previousCell);
        AbstractCell.getEmptyCells().remove(currentCell);
    }
}