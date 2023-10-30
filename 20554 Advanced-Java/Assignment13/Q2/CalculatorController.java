
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CalculatorController {

    public static final Color DIGIT_BUTTON_COLOR = Color.LIGHTGRAY;
    public static final Color OP_BUTTON_COLOR = Color.ORANGE;

    @FXML
    private GridPane buttonGrid;

    @FXML
    private TextField textField;

    private NumberBuilder numberBuilder;
    private Expression expression;

    @FXML
    public void initialize() {
        numberBuilder = new NumberBuilder();
        expression = new Expression();
        // Build buttons on grid
        // Grid structure:
        // Row 0: 7 8 9 /
        // Row 1: 4 5 6 *
        // Row 2: 1 2 3 -
        // Row 3: +/- 0 . +
        // Row 4: =

        // digit buttons:
        for (int i = 0; i < NumberBuilder.BASE; i++) {
            Button button = new CalculatorButton(String.valueOf(i), DIGIT_BUTTON_COLOR, new DigitButtonEventHandler(i));
            if (i == 0)
                buttonGrid.add(button, 1, 3);
            else
                buttonGrid.add(button, (i - 1) % 3, 2 - (i - 1) / 3);
        }

        // operator buttons
        char[] operators = new char[] { Expression.OP_DIV, Expression.OP_MUL, Expression.OP_SUB, Expression.OP_ADD };
        for (int i = 0; i < operators.length; i++) {
            char op = operators[i];
            buttonGrid.add(new CalculatorButton(String.valueOf(op), OP_BUTTON_COLOR,
                    new OperatorPointEventHandler(op)), 3, i);
        }

    }

    // this button event handler binds the current button clicked to the text state
    public class TextBinderEventHandler implements EventHandler<ActionEvent> {

        private String value; // the value to add

        public TextBinderEventHandler(String value) {
            this.value = value;
        }

        @Override
        public void handle(ActionEvent event) {
            StringProperty calculatorState = textField.textProperty();
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

    // this handler is for the mathematical operator buttons
    public class OperatorPointEventHandler extends TextBinderEventHandler {

        private char operator;

        public OperatorPointEventHandler(char operator) {
            super(String.valueOf(operator));
        }

        @Override
        public void handle(ActionEvent event) {
            expression.addNumAndOp(numberBuilder.build(), operator, numberBuilder.isDecimal());
            numberBuilder.reset();
            super.handle(event);
        }
    }

}
