import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Random shape, generated in the NEW function and ready to be drawn on canvas.
 */
public class RandomShape {

    public static final String RECTANGLE = "Rectangle";
    public static final String OVAL = "Oval";
    public static final String LINE = "Line";
    public static final String[] SHAPES = { RECTANGLE, OVAL, LINE };
    private static final Random random = new Random();

    // shape type string. Must be a member of the SHAPES array.
    private String shapeType;

    // location on canvas coords: picked randomly
    private int locationX;
    private int locationY;

    /*
     * Bounding width and height. According to instructions, these should not be
     * above 1/4 of the canvas dimensions.
     */
    private int boundingWidth;
    private int boundingHeight;

    // The shape color
    private Color color;

    /**
     * Generates a random shape.
     * 
     * @param canvasWidth  the width of the canvas
     * @param canvasHeight the height of the canvas
     */
    public RandomShape(int canvasWidth, int canvasHeight) {
        shapeType = SHAPES[random.nextInt(SHAPES.length)];
        locationX = random.nextInt(canvasWidth);
        locationY = random.nextInt(canvasHeight);
        boundingWidth = random.nextInt(canvasWidth / 4);
        boundingHeight = random.nextInt(canvasHeight / 4);
        color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    /**
     * Draw the shape on a canvas
     * 
     * @param gc the graphics context
     */
    public void draw(GraphicsContext gc) {
        if (shapeType == LINE) {
            gc.setStroke(color);
            gc.strokeLine(locationX, locationY, locationX + boundingWidth, locationY + boundingHeight);
            return;
        }
        gc.setFill(color);
        if (shapeType == RECTANGLE)
            gc.fillRect(locationX, locationY, boundingWidth, boundingHeight);
        else if (shapeType == OVAL)
            gc.fillOval(locationX, locationY, boundingWidth, boundingHeight);
    }

}
