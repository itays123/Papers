import java.util.ArrayList;

/**
 * A class representing a polynomial, or a collection of polynom elements.
 * 
 * @author Itay Schechner
 */
public class Polynom {
    private ArrayList<PolynomElement> elements;

    /**
     * private constructor, create polynomial using a preconstructed arraylist of
     * elements.
     * Note that this creates an aliasing situation, so it's up to the class
     * developer to prevent such case
     */
    private Polynom(ArrayList<PolynomElement> elements) {
        this.elements = elements;
    }

    /**
     * Public constructor using two arrays, one for the powers and one for the
     * scalars.
     * 
     * @throws Exception if arrays are not of the same length
     */
    public Polynom(int[] powers, double[] scalars) throws Exception {

        if (powers.length != scalars.length)
            throw new Exception("Powers and scalars arrays must be of the same length");
        int length = powers.length;

        elements = new ArrayList<>(length);

        // Add pairs of scalar-power to the elements array list
        for (int i = 0; i < scalars.length; i++) {
            elements.add(new PolynomElement(powers[i], scalars[i]));
        }

        // sort elements by power in descending order. I chose bubble sort for
        // simplicity, but more efficient sorts could be easily implemeneted.
        PolynomElement temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (elements.get(j).getPower() < elements.get(j + 1).getPower()) {
                    temp = elements.get(j);
                    elements.set(j, elements.get(j + 1));
                    elements.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Sums this polynomial with another one
     * 
     * @param other the polynom to add
     * @return the polynomial sum
     */
    public Polynom plus(Polynom other) {
        ArrayList<PolynomElement> sumElements = new ArrayList<>();

        PolynomElement currElement, otherElement;
        int thisIdx = 0, otherIdx = 0, thisSize = elements.size(), otherSize = other.elements.size();
        // iterate throgh both polynomial's list, and add element with maximum power.
        while (thisIdx < thisSize && otherIdx < otherSize) {
            currElement = elements.get(thisIdx);
            otherElement = other.elements.get(otherIdx);

            // Add next element in this polynomial, if it maintains the elements' order
            if (currElement.getPower() > otherElement.getPower()) {
                sumElements.add(currElement);
                thisIdx++;
            }
            // Add next element in the other polynomial, if it maintains the elements' order
            else if (currElement.getPower() < otherElement.getPower()) {
                sumElements.add(otherElement);
                otherIdx++;
            }
            // else, next elements have equal powers. Add the sum of them.
            else {
                sumElements.add(currElement.plus(otherElement));
                thisIdx++;
                otherIdx++;
            }
        }

        // at most one of the polynomials might have remaining elements. Add them all in
        // order.
        while (thisIdx < thisSize) {
            sumElements.add(elements.get(thisIdx));
            thisIdx++;
        }
        while (otherIdx < otherSize) {
            sumElements.add(other.elements.get(otherSize));
            otherIdx++;
        }

        return new Polynom(sumElements);
    }

    /**
     * Returns a negation of the current polynomial
     */
    private Polynom negate() {
        ArrayList<PolynomElement> negatedElements = new ArrayList<>(elements.size());

        for (int i = 0; i < elements.size(); i++) {
            negatedElements.add(elements.get(i).negate());
        }

        return new Polynom(negatedElements);
    }

    /**
     * Returns the difference between this polynom and the other one
     * 
     * @param other the polynom to subtract
     * @return the difference polynom
     */
    public Polynom minus(Polynom other) {
        return plus(other.negate());
    }

    /**
     * Derives the polynom
     * 
     * @return The derivative polynom
     */
    public Polynom derive() {
        // since derivation maintains power order, we can derive each element and add by
        // order
        ArrayList<PolynomElement> derivedElements = new ArrayList<>(elements.size());

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getPower() != 0) // no reason to add a (0,0) element to the derivative
                derivedElements.add(elements.get(i).derive());
        }

        return new Polynom(derivedElements);
    }

    /**
     * A string representation of the polynomial
     */
    @Override
    public String toString() {
        boolean prefixWithPlus = false;
        String result = "";

        for (PolynomElement element : elements) {
            if (element.getScalar() == 0) // no reason to add a "+0x^n" to the string representation
                continue;
            if (prefixWithPlus && element.getScalar() > 0)
                result += '+';
            result += element;
            prefixWithPlus = true; // will evaluate to true after the first element
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynom))
            return false;
        Polynom other = ((Polynom) obj);
        if (elements.size() != other.elements.size())
            return false;
        for (int i = 0; i < elements.size(); i++) {
            if (!elements.get(i).equals(other.elements.get(i)))
                return false;
        }
        return true;
    }

}
