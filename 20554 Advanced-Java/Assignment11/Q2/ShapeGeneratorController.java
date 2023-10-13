import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class ShapeGeneratorController {

    public static final int NUM_OF_SHAPES = 10;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    @FXML
    void initialize() {
        gc = canvas.getGraphicsContext2D();
    }

    @FXML
    void onShowButtonPress(ActionEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < NUM_OF_SHAPES; i++) {
            RandomShape shape = new RandomShape((int) canvas.getWidth(), (int) canvas.getHeight());
            shape.draw(gc);
        }
    }

}
