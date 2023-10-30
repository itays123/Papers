
/**
 * Builds a number from a series of digits. Supports decimals and negatives.
 */
public class NumberBuilder {

    public static final int UNDEFINED = -1; // since we save the sign separately, we will pick a negative number for
                                            // the undefined constant
    public static final byte BASE = 10;

    private int integerPart, decimalPart, digitsAfterDecimal;
    private boolean isDecimalPart;
    private boolean isNegative;

    /*
     * Constructs an empty number
     */
    public NumberBuilder() {
        integerPart = UNDEFINED;
        decimalPart = UNDEFINED;
        digitsAfterDecimal = 0;
        isNegative = false;
    }

    /**
     * Build the number from the current state
     */
    public double build() {
        if (integerPart == UNDEFINED)
            throw new UnsupportedOperationException("Cannot build a number with an undefined integer part");
        if (isDecimalPart && decimalPart == UNDEFINED)
            throw new UnsupportedOperationException("Cannot build decimal number with undefined decimal part");

        double result = integerPart;
        if (isDecimalPart) {
            result += decimalPart / Math.pow(BASE, digitsAfterDecimal);
        }

        if (isNegative)
            result *= -1;
        return result;
    }

    /**
     * Checks if the number has a decimal part
     */
    public boolean isDecimal() {
        return isDecimalPart && decimalPart != UNDEFINED && decimalPart != 0;
    }

    /**
     * Adds a digit to the number
     */
    public void addDigit(int digit) {
        if (!isDecimalPart) // add to integer part
            integerPart = addDigit(integerPart, digit);
        else { // add to decimal part
            decimalPart = addDigit(decimalPart, digit);
            digitsAfterDecimal++;
        }
    }

    /**
     * Add digit to a given number
     */
    private int addDigit(int number, int digit) {
        if (number == UNDEFINED)
            return digit;
        return BASE * number + digit;
    }

    /**
     * Negate the number
     */
    public void negate() {
        isNegative = !isNegative;
    }

    /**
     * Emit when a decimal point is pressed
     */
    public void addDecimalPoint() {
        if (integerPart == UNDEFINED)
            throw new UnsupportedOperationException("Must define integer part before defining decimal part");
        isDecimalPart = true;
    }

}