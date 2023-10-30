import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class CalculatorButton extends Button {

    public static final int BUTTON_PREF_SIZE = 80;
    public static final int BUTTON_CORNDER_RADIUS = 8;

    public CalculatorButton(String text, Paint background, EventHandler<ActionEvent> eventHandler) {
        super(text);
        setPrefSize(BUTTON_PREF_SIZE, BUTTON_PREF_SIZE);
        setBackground(new Background(
                new BackgroundFill(background, new CornerRadii(BUTTON_CORNDER_RADIUS), Insets.EMPTY)));
        setOnAction(eventHandler);
    }

}
