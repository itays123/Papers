
/**
 * Assignment 12 - Using a class to represent a point in the cartesian system.
 * 
 * @author Itay Schechner
 * @version 09-04-21
 */
public class Point {

    // attributes
    private double _x;
    private double _y;

    // constructors
    /**
     * Creates a new Point object
     * 
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point(double x, double y) {
        _x = x;
        _y = y;
    }

    /**
     * Copy constructor that creates a point with the same values as a given point
     * 
     * @param other the point to be copied
     */
    public Point(Point other) {
        _x = other._x;
        _y = other._y;
    }

    // getters and setters

    /**
     * Returns the x coordinate.
     * 
     * @return a double
     */
    public double getX() {
        return _x;
    }

    /**
     * Returns the y coordinate.
     * 
     * @return a double
     */
    public double getY() {
        return _y;
    }

    /**
     * Modify the value of the x coordinate
     * 
     * @param num the value to be set
     */
    public void setX(double num) {
        _x = num;
    }

    /**
     * Modify the value of the y coordinate
     * 
     * @param num the value to be set
     */
    public void setY(double num) {
        _y = num;
    }

    // other methods

    /**
     * Returns a stringified representation of the point in the (x,y) pattern.
     * 
     * @return a string representing the point.
     */
    public String toString() {
        return "(" + _x + "," + _y + ")";
    }

    /**
     * Check if the values a given point holds equal to this one.
     * 
     * @param other the point we are checking equality with
     * @return true of the point other equals to this point
     */
    public boolean equals(Point other) {
        return _x == other._x && _y == other._y;
    }

    /**
     * checks if the current point is above a given point.
     * 
     * @param other the given point to check
     * @return true if this point is above the other point
     */
    public boolean isAbove(Point other) {
        return _y > other._y;
    }

    /**
     * checks if the current point is under a given point.
     * 
     * @param other the given point to check
     * @return true if this point is below the other point
     */
    public boolean isUnder(Point other) {
        return other.isAbove(this);
    }

    /**
     * Checks if the current point is left to a given point
     * 
     * @param other the given point to check
     * @return true if this point is left to the other point.
     */
    public boolean isLeft(Point other) {
        return _x < other._x;
    }

    /**
     * Checks if the current point is right to a given point
     * 
     * @param other the given point to check
     * @return true if this point is right to the other point.
     */
    public boolean isRight(Point other) {
        return other.isLeft(this);
    }

    /**
     * Calculates the distance to a given point.
     * 
     * @param p the point to calculate the distance to
     * @return the distance as a double
     */
    public double distance(Point p) {
        /*
         * calculate the distance to a given point (x1,y1) using the following
         * algorithm:
         * 
         * 1. calculate the sum of (x-x1)^2 + (y-y1)^2
         * 
         * 2. square root the result
         */
        double xDifference = _x - p._x;
        double yDifference = _y - p._y;
        double distanceSquared = Math.pow(xDifference, 2) + Math.pow(yDifference, 2);
        return Math.sqrt(distanceSquared);
    }

    /**
     * Return number of quadrant or 0 if the point is on an axis
     *
     * @return an integer between 0 and 4
     */
    public int quadrant() {

        // ensure that the method returns 0 for points located on one of the axises.
        if (_x == 0 || _y == 0) {
            return 0;
        }

        if (_y > 0) { // quadrant is either 1 or 2
            if (_x > 0)
                return 1;
            else // x < 0, y > 0
                return 2;
        } else { // _y < 0, quadrant is either 3 or 4
            if (_x > 0)
                return 4;
            else // x < 0, y < 0
                return 3;
        }

    }

}