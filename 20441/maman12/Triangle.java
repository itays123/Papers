
/**
 * Assignment 12 - Using a class to represent a triangle (as a set of 3 points)
 * 
 * @author Itay Schechner
 * @version 09-04-21
 */
public class Triangle {

    static final double EPSILON = 0.001;

    // attributes
    private Point _point1;
    private Point _point2;
    private Point _point3;

    // constructors
    /**
     * Empty constructor, sets the triangle to {(1.0,0.0),(-1.0,0.0),(0.0,1.0)}
     */
    public Triangle() {
        _point1 = new Point(1, 0);
        _point2 = new Point(-1, 0);
        _point3 = new Point(0, 1);
    }

    /**
     * Creates a new triangle object using 3 points. The points will only be used if
     * they create a valid triangle.
     * 
     * @param p1 the first point of the triangle
     * @param p2 the second point of the triangle
     * @param p3 the third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        this();
        if (isValid(p1, p2, p3)) {
            _point1 = new Point(p1);
            _point2 = new Point(p2);
            _point3 = new Point(p3);
        }
    }

    /**
     * Creates a new triangle using 6 double values representing the values of the 3
     * points. The points will only be used if they create a valid triangle.
     * 
     * @param x1 the x value of the first point.
     * @param y1 the y value of the first point.
     * @param x2 the x value of the second point.
     * @param y2 the y value of the second point.
     * @param x3 the x value of the third point.
     * @param y3 the y value of the third point.
     */
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
    }

    /**
     * Copy constructor that duplicates the value of a given triangle.
     * 
     * @param other the triangle to be copied
     */
    public Triangle(Triangle other) {
        /*
         * no need to validate the triangle values since they were validated in the
         * constructor of the other triangle.
         */
        _point1 = new Point(other._point1);
        _point2 = new Point(other._point2);
        _point3 = new Point(other._point3);
    }

    // helper EPSILON functions
    private boolean areEqual(double value1, double value2) {
        return Math.abs(value1 - value2) < EPSILON;
    }

    private boolean isLowerThanOrEqualTo(double value1, double value2) {
        /*
         * How this works: value1 <= value2 -> value1 - value2 <= 0
         * 
         * using doubles, replace this expression with the following:
         * 
         * considering 0 < EPSILON: value1 - value2 < EPSILON
         */
        return value1 - value2 < EPSILON;
    }

    // for a cleaner, more readable code
    private Point copyOf(Point p) {
        return new Point(p);
    }

    // getters and setters

    /**
     * Gets the first point
     * 
     * @return the value of the first point
     */
    public Point getPoint1() {
        return copyOf(_point1);
    }

    /**
     * Gets the second point
     * 
     * @return the value of the second point
     */
    public Point getPoint2() {
        return copyOf(_point2);
    }

    /**
     * Gets the third point
     * 
     * @return the value of the third point
     */
    public Point getPoint3() {
        return copyOf(_point3);
    }

    /**
     * Modify the first point of the triangle
     * 
     * @param p the point to be set
     */
    public void setPoint1(Point p) {
        // change _point1 only if new triangle will be valid.
        if (isValid(p, _point2, _point3)) {
            _point1 = copyOf(p);
        }
    }

    /**
     * Modify the secong point of the triangle
     * 
     * @param p the point to be set
     */
    public void setPoint2(Point p) {
        // change _point2 only if new triangle will be valid.
        if (isValid(_point1, p, _point3)) {
            _point2 = copyOf(p);
        }
    }

    /**
     * Modify the third point of the triangle
     * 
     * @param p the point to be set
     */
    public void setPoint3(Point p) {
        // change _point3 only if new triangle will be valid.
        if (isValid(_point1, _point2, p)) {
            _point3 = copyOf(p);
        }
    }

    // triangle validator

    /**
     * checks if 3 points make a valid triangle. A triangle is valid if no sum of
     * any two sides equals the third side (with precision EPSILON)
     * 
     * @param p1 the first point
     * @param p2 the second point
     * @param p3 the third point
     * @return true if the triangle created with the point values is valid.
     */
    public boolean isValid(Point p1, Point p2, Point p3) {
        double edge1 = p1.distance(p2);
        double edge2 = p2.distance(p3);
        double edge3 = p3.distance(p1);

        /*
         * for a triangle to be valid, the sum of two edges must not equal the third
         * one, meaning:
         * 
         * 1. edge1 + edge2 != edge3
         * 
         * 2. edge3 + edge1 != edge2
         * 
         * 3. edge2 + edge3 != edge1
         * 
         * since we are using doubles, we will use the EPSILON constant.
         */
        return !areEqual(edge1 + edge2, edge3) && !areEqual(edge3 + edge1, edge2) && !areEqual(edge2 + edge3, edge1);
    }

    /**
     * a string representation of the triangle.
     * 
     * @return a string in this format {(x1,y1),(x2,y2),(x3,y3)}
     */
    public String toString() {
        return "{" + _point1.toString() + "," + _point2.toString() + "," + _point3.toString() + "}";
    }

    // edges helper methods

    private double getEdge1() {
        return _point1.distance(_point2);
    }

    private double getEdge2() {
        return _point2.distance(_point3);
    }

    private double getEdge3() {
        return _point3.distance(_point1);
    }

    // edges related methods

    /**
     * Returns the perimiter of the triangle
     * 
     * @return the sum of the triangle's edges.
     */
    public double getPerimeter() {
        return getEdge1() + getEdge2() + getEdge3();
    }

    /**
     * calculate the triangle's area with Heron's furmula.
     * 
     * @return the calculation result as a double.
     */
    public double getArea() {
        /*
         * Using Heron's formula:
         *
         * area = the square root of s(s-a)(s-b)(s-c),
         *
         * with s being half the triangle's perimeter, and a,b,c being the triangle's
         * edges.
         */
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - getEdge1()) * (s - getEdge2()) * (s - getEdge3()));
    }

    /**
     * This method returns true if the triangle is an isosceles triangle(with
     * precision EPSILON).
     * 
     * @return true if there are any, false if none.
     */
    public boolean isIsosceles() {
        return areEqual(getEdge1(), getEdge2()) || areEqual(getEdge2(), getEdge3()) || areEqual(getEdge3(), getEdge1());
    }

    /**
     * Check if a triangle is pythagorean.
     * 
     * @return the check result
     */
    public boolean isPythagorean() {
        /*
         * Check if a triangle is pythagorean using the revrsed pythagoras theorem:
         * 
         * 
         * for a,b,c the edges of the triangle, if c^2 = a^2 + b^2, a^2 = b^2 + c^2, or
         * b^2 = a^2 + c^2 then the triangle is pythagorean.
         * 
         */
        double aSquared = Math.pow(getEdge1(), 2);
        double bSquared = Math.pow(getEdge2(), 2);
        double cSquared = Math.pow(getEdge3(), 2);

        return areEqual(aSquared + bSquared, cSquared) || areEqual(bSquared + cSquared, aSquared)
                || areEqual(cSquared + aSquared, bSquared);
    }

    /**
     * Checks if all 3 points of a triangle are contained in a circle
     * 
     * @param x the x value of the point being the center of the circle
     * @param y the y value of the point being the center of the circle
     * @param r the radius of the circle
     * @return true if the triangle is in a given circle.
     */
    public boolean isContainedInCircle(double x, double y, double r) {
        Point centerOfCirc = new Point(x, y);
        /*
         * for a triangle to be contained in a circle, the distance between each point
         * and the circle's center must be lower than or equal to the radius.
         */
        boolean point1InCircle = isLowerThanOrEqualTo(centerOfCirc.distance(_point1), r);
        boolean point2InCircle = isLowerThanOrEqualTo(centerOfCirc.distance(_point2), r);
        boolean point3InCircle = isLowerThanOrEqualTo(centerOfCirc.distance(_point3), r);

        return point1InCircle && point2InCircle && point3InCircle;
    }

    // triangle location related methods

    /*
     * helper function for the lowestPoint calculation
     */
    private Point lowestOf(Point p1, Point p2) {
        if (p1.isUnder(p2))
            return p1;
        else if (p1.isAbove(p2))
            return p2;
        else {
            /*
             * p1 and p2 have the same y value. if two points have the same y value, return
             * the leftmost of them.
             */
            return p1.isLeft(p2) ? p1 : p2;
        }
    }

    /**
     * helper function for the highestPoint calculation
     */
    private Point highestOf(Point p1, Point p2) {
        if (p1.isAbove(p2))
            return p1;
        else if (p1.isUnder(p2))
            return p2;
        else {
            /*
             * p1 and p2 have the same y value. if two points have the same y value, return
             * the leftmost of them.
             */
            return p1.isLeft(p2) ? p1 : p2;
        }
    }

    /**
     * searches the lowest point of the triangle.
     * 
     * @return the lowest point.
     */
    public Point lowestPoint() {
        return copyOf(lowestOf(lowestOf(_point1, _point2), _point3));
    }

    /**
     * searches the highest point of the triangle.
     * 
     * @return the highest point.
     */
    public Point highestPoint() {
        return copyOf(highestOf(highestOf(_point1, _point2), _point3));
    }

    /**
     * checks if this triangle is above a given triangle.
     * 
     * @param other the triangle to be checked
     * @return true if the triangle is completely above the other triangle.
     */
    public boolean isAbove(Triangle other) {
        return lowestPoint().isAbove(other.highestPoint());
    }

    /**
     * checks if this triangle is below a given triangle.
     * 
     * @param other the triangle to be checked
     * @return true if the triangle is completely below the other triangle
     */
    public boolean isUnder(Triangle other) {
        return other.isAbove(this);
    }

    /**
     * Checks if the entire triangle is located in one quadrant
     * 
     * @return true if all 3 point are located in the same quadrant
     */
    public boolean isLocated() {
        return _point1.quadrant() == _point2.quadrant() && _point1.quadrant() == _point3.quadrant()
                && _point1.quadrant() != 0;
    }

    /**
     * Checks if the triangle equals a given triangle.
     * 
     * @param other the triangle to check if euqals.
     * @return true if all 3 points are equal.
     */
    public boolean equals(Triangle other) {
        return _point1.equals(other._point1) && _point2.equals(other._point2) && _point3.equals(other._point3);
    }

    /**
     * Checks if the triangle is congruent with a given triangle.
     * 
     * @param other the triangle to be checked
     * @return true if the triangles' perimeter and area are equal.
     */
    public boolean isCongruent(Triangle other) {
        // return getEdge1() == other.getEdge1() && getEdge2() == other.getEdge2() &&
        // getEdge3() == other.getEdge3()
        /*
         * The logic above has major issues. For instance, what if edge1 == edge3, edge2
         * == edge2, edge3 == edge1? The triangles are still congruent.
         * 
         * for two triangles to be congruent, they must have a matching perimeter and
         * area.
         */
        return areEqual(getPerimeter(), other.getPerimeter()) && areEqual(getArea(), other.getArea());
    }

}
