
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CalculatorController {

    public static final int BUTTON_PREF_SIZE = 80;
    public static final int BUTTON_CORNDER_RADIUS = 8;
    public static final Color DIGIT_BUTTON_COLOR = Color.LIGHTGRAY;

    @FXML
    private GridPane buttonGrid;

    @FXML
    private TextField textField;

    private NumberBuilder numberBuilder;
    private StringProperty calculatorState;

    @FXML
    public void initialize() {

        numberBuilder = new NumberBuilder();
        calculatorState = textField.textProperty();
        // Build buttons on grid
        // Grid structure:
        // Row 0: 7 8 9 /
        // Row 1: 4 5 6 *
        // Row 2: 1 2 3 -
        // Row 3: +/- 0 . +
        // Row 4: =
        Button button;

        // digit buttons:
        for (int i = 0; i < NumberBuilder.BASE; i++) {
            button = new Button(String.valueOf(i));
            button.setOnAction(new DigitButtonEventHandler(i));
            button.setBackground(new Background(
                    new BackgroundFill(DIGIT_BUTTON_COLOR, new CornerRadii(BUTTON_CORNDER_RADIUS), Insets.EMPTY)));
            button.setPrefSize(BUTTON_PREF_SIZE, BUTTON_PREF_SIZE);
            if (i == 0)
                buttonGrid.add(button, 1, 3);
            else
                buttonGrid.add(button, (i - 1) % 3, 2 - (i - 1) / 3);
        }

        // bind textField to represent the state
    }

    // this button event handler binds the current button clicked to the text state
    public class TextBinderEventHandler implements EventHandler<ActionEvent> {

        private String value; // the value to add

        public TextBinderEventHandler(String value) {
            this.value = value;
        }

        @Override
        public void handle(ActionEvent event) {
            calculatorState.setValue(calculatorState.getValue() + value);
        }
    }

    // this handler handles the clicking on digit buttons
    public class DigitButtonEventHandler extends TextBinderEventHandler {

        private int digit; // the value to add

        public DigitButtonEventHandler(int digit) {
            super(String.valueOf(digit));
            this.digit = digit;
        }

        @Override
        public void handle(ActionEvent event) {
            numberBuilder.addDigit(digit);
            super.handle(event);
        }
    }

    // this handler is for the decimal point button
    public class DecimalPointEventHandler extends TextBinderEventHandler {

        public DecimalPointEventHandler() {
            super(".");
        }

        @Override
        public void handle(ActionEvent event) {
            numberBuilder.addDecimalPoint();
            super.handle(event);
        }
    }

}
