import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mathematical expression
 */
public class Expression {

    public static final char OP_ADD = '+';
    public static final char OP_SUB = '-';
    public static final char OP_MUL = '*';
    public static final char OP_DIV = '/';

    private boolean isDecimalExpression;
    private List<Double> operands;
    private List<Character> operators; // the list of operators should always have exatly one operator less than
                                       // the number of operands

    // construct a new expression
    public Expression() {
        this.isDecimalExpression = false;
        this.operands = new LinkedList<>();
        this.operators = new LinkedList<>();
    }

    /**
     * Add a number and an operator
     */
    public void addNumAndOp(double num, char op, boolean isDecimal) {
        this.isDecimalExpression = this.isDecimalExpression || isDecimal;
        operands.add(num);
        operators.add(op);
    }

    /**
     * Preform the operation operands[i] <operators[i]> oprands[i+1].
     * Store the result in operands[i]
     */
    private void resolve(int index) {
        double op1 = operands.get(index), op2 = operands.remove(index + 1), result;
        switch (operators.remove(index)) {
            case OP_ADD:
                result = op1 + op2;
                break;
            case OP_SUB:
                result = op1 - op2;
                break;
            case OP_MUL:
                result = op1 * op2;
                break;
            case OP_DIV:
                result = op1 / op2;
                break;
            default:
                result = op1;
                break;
        }
        operands.set(index, result);
    }

    /**
     * Evaluate the expression. Get the last operand in order to evaluate.
     */
    public void evaluate(double lastOperand) {
        operands.add(lastOperand);

        // Apply operators order: first, resolve multiplication and division
        int i = 0;
        while (i < operators.size()) {
            if (operators.get(i) == OP_MUL || operators.get(i) == OP_DIV)
                resolve(i);
            else
                i++;
        }

        // After all multiplication and division is resolved, resolve addition and
        // subtraction
        while (operators.size() >= 0) {
            resolve(0);
        }
    }

    /**
     * Get the value of the expression, defined as the first of its operands
     */
    public double getValue() {
        return operands.get(0);
    }

    /**
     * Return if decimal expression or not
     */
    public boolean isDecimalExpression() {
        return isDecimalExpression;
    }

    @Override
    public String toString() {
        String result = "";
        int i;
        for (i = 0; i < operators.size(); i++) {
            result += valueOf(operands.get(i));
            result += operators.get(i);
        }
        if (i < operands.size())
            result += operands.get(i);
        return result;
    }

    private String valueOf(double num) {
        if (!isDecimalExpression)
            return String.valueOf((int) num);
        return String.valueOf(num);
    }

}
