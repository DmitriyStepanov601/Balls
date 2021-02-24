import java.awt.event.MouseListener;

/**
 * Interface describing the manipulation of mouse actions
 * @author Dmitriy Stepanov
 */
public interface Clickable extends MouseListener  {
    void select();
    void release();
}