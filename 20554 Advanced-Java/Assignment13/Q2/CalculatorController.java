
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
            int digit = i;
            Button button = new CalculatorButton(String.valueOf(i), DIGIT_BUTTON_COLOR,
                    new CalculatorButtonActionHandler() {

                        @Override
                        public void beforeUpdate(ActionEvent event) {
                            numberBuilder.addDigit(digit);
                        }

                    });
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
                    new CalculatorButtonActionHandler() {

                        @Override
                        public void beforeUpdate(ActionEvent event) {
                            expression.addNumAndOp(numberBuilder.build(), op, numberBuilder.isDecimal());
                            numberBuilder.reset();
                        }

                    }), 3, i);
        }

        // point button
        buttonGrid.add(new CalculatorButton(".", OP_BUTTON_COLOR, new CalculatorButtonActionHandler() {

            @Override
            public void beforeUpdate(ActionEvent event) {
                numberBuilder.addDecimalPoint();
            }

        }), 2, 3);

        // negate button
        buttonGrid.add(new CalculatorButton("+/-", OP_BUTTON_COLOR, new CalculatorButtonActionHandler() {

            @Override
            public void beforeUpdate(ActionEvent event) {
                numberBuilder.negate();
            }

        }), 0, 3);

        // evaluate button
        buttonGrid.add(new CalculatorButton("=", OP_BUTTON_COLOR, new CalculatorButtonActionHandler() {

            @Override
            public void beforeUpdate(ActionEvent event) {
                expression.evaluate(numberBuilder.build());
                numberBuilder.reset();
            }

        }), 0, 4, 4, 1);
    }

    // "Bind" the text property to the expression plus the current number
    public abstract class CalculatorButtonActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            beforeUpdate(event);
            textField.textProperty().set(expression.toString() + numberBuilder.toString());
        }

        abstract void beforeUpdate(ActionEvent event);

    }
}
