import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that describes the loading of game resources
 * @author Dmitriy Stepanov
 */
public class ResourceLoader {
    private static final URL IMAGE_ICON_URL;
    public static final Object[] BALLS;

    static {
        IMAGE_ICON_URL = Game.class.getResource("/balls.png");
        BALLS = ballsMap().values().toArray();
    }

    public static Image getImageIcon() {
        return IMAGE_ICON_URL != null ? new ImageIcon(IMAGE_ICON_URL).getImage() : null;
    }

    public static Map<String, ImageIcon> ballsMap() {
        Map<String, ImageIcon> imageMap = new HashMap<>();
        List<String> colors = Arrays.asList("black", "blue", "gray", "green", "pink", "purple", "red",
                "sapphire", "yellow");

        for (String color : colors) {
            ImageIcon image = getImageByColor(color);
            imageMap.put(color, image);
        }
        return imageMap;
    }

    private static ImageIcon getImageByColor(String color) {
        return new ImageIcon(Game.class.getResource("/balls/" + color + "-ball.png"));
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ResourceLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}