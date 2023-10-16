/**
 * The polynom element class, consisting of a scalar and a power field,
 * representing a polynom element in the form of scalar * x^power
 * 
 * @author Itay Schechner
 */
public class PolynomElement {

    private int power;

    private double scalar;

    /**
     * Constructor for a polynom element object
     * 
     * @param power  the element's power
     * @param scalar the element's scalar
     */
    public PolynomElement(int power, double scalar) {
        this.power = power;
        this.scalar = scalar;
    }

    /**
     * Returns the element's power for comparison
     * 
     * @return the element's power
     */
    public int getPower() {
        return power;
    }

    /**
     * Adds the polynom element to another one
     * 
     * @param other the element to add
     * @return the sum of the two elements
     * @throws IllegalArgumentException if other element does not have the same
     *                                  power as this one
     */
    public PolynomElement plus(PolynomElement other) {
        if (power != other.power) // powers not equal
            throw new IllegalArgumentException("Can only add polynom elements with same power");
        return new PolynomElement(power, scalar + other.scalar);
    }

    /**
     * Negates the polynom element
     * 
     * @return an element with the negated scalar
     */
    public PolynomElement negate() {
        return new PolynomElement(power, -scalar);
    }

    /**
     * Derives the polynomial
     * 
     * @return the derivative of the polynomial
     */
    public PolynomElement derive() {
        int newPower = power == 0 ? 0 : power - 1;
        double newScalar = power * scalar;
        return new PolynomElement(newPower, newScalar);
    }

    /**
     * Creates a string representation in the form of sign + scalar + power
     */
    @Override
    public String toString() {
        double abs;
        String sign = "", scalarAbs = "", xPowered = "";
        // create sign string
        if (scalar < 0.0)
            sign = "-";

        abs = Math.abs(scalar);
        if (abs != 1.0 || power == 0)
            scalarAbs += abs;

        if (power != 0)
            xPowered = "x^" + power;

        return sign + scalarAbs + xPowered;
    }

}
