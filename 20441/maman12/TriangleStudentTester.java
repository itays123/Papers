
public class TriangleStudentTester {

    public static void main(String[] args) {
        // final double EPSILON = 0.001;
        System.out.println("********** Test Q2 Triangle - Start **********");
        System.out.println(
                "********** Note that these are only basic tests. You need to think which tests to add for each constructor and method in order to test your class thoroughly.**********");

        System.out.println("testing first constructor and getters:");
        Triangle t0 = new Triangle();
        if (!(t0.getPoint1().equals(new Point(1, 0))) || !(t0.getPoint2().equals(new Point(-1, 0)))
                || !(t0.getPoint3().equals(new Point(0, 1))))
            System.out.println("\t ERROR -  constructor and getters,expected {(1.0,0.0),(-1,0),(0,1)} actual= " + t0);
        else
            System.out.println("\t OK -  constructor and getters,expected {(1.0,0.0),(-1,0),(0,1)} actual= " + t0);

        System.out.println("testing second  constructor and getters:");

        Triangle t1 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (!(t1.getPoint1().equals(new Point(1.0, 1.0))) || !(t1.getPoint2().equals(new Point(3.0, 3.0)))
                || !(t1.getPoint3().equals(new Point(3.3, 1.1))))
            System.out.println(
                    "\t ERROR -  constructor 2 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t1);
        else
            System.out.println(
                    "\t OK -  constructor 2 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t1);

        System.out.println("testing third  constructor and getters:");

        Triangle t2 = new Triangle(new Point(1.0, 1.0), new Point(3.0, 3.0), new Point(3.3, 1.1));
        if (!(t2.getPoint1().equals(new Point(1.0, 1.0))) || !(t2.getPoint2().equals(new Point(3.0, 3.0)))
                || !(t2.getPoint3().equals(new Point(3.3, 1.1))))
            System.out.println(
                    "\t ERROR -  constructor 3 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t2);
        else
            System.out.println(
                    "\t OK -  constructor 3 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t2);

        System.out.println("testing copy constructor: ");
        Triangle t3 = new Triangle(t2);
        if (!(t3.getPoint1().equals(new Point(1.0, 1.0))) || !(t3.getPoint2().equals(new Point(3.0, 3.0)))
                || !(t3.getPoint3().equals(new Point(3.3, 1.1))))
            System.out.println(
                    "\t ERROR -  copy constructor  and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t3);
        else
            System.out.println(
                    "\t OK -  copy constructor  and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= " + t3);

        System.out.println("testing setters:");
        Triangle t4 = new Triangle();
        t4.setPoint1(new Point(3.3, 4.8));
        if (!(t4.getPoint1().equals(new Point(3.3, 4.8))))
            System.out.println(
                    "\t ERROR - setPoint1 point1 was (1.0,0.0) set point1 to (3.3,4.8) expected (3.3,4.8) actual= "
                            + t4.getPoint1());
        else
            System.out.println(
                    "\t OK - setPoint1 point1 was (1.0,0.0) set point1 to (3.3,4.8) expected (3.3,4.8) actual= "
                            + t4.getPoint1());
        t4.setPoint2(new Point(5.5, 6.6));
        if (!(t4.getPoint2().equals(new Point(5.5, 6.6))))
            System.out.println(
                    "\t ERROR - setPoint2 point2 was (-1.0,0.0) set point2 to (5.5,6.6) expected (5.5,6.6) actual= "
                            + t4.getPoint2());
        else
            System.out.println(
                    "\t OK - setPoint2 point2 was (-1.0,0.0) set point2 to (5.5,6.6) expected (5.5,6.6) actual= "
                            + t4.getPoint2());

        t4.setPoint3(new Point(7.5, 7.6));
        if (!(t4.getPoint3().equals(new Point(7.5, 7.6))))
            System.out.println(
                    "\t ERROR - setPoint3 point3 was (0.0,1.0) set point3 to (7.5,7.6) expected (7.5,7.6) actual= "
                            + t4.getPoint3());
        else
            System.out.println(
                    "\t OK - setPoint3 point3 was (0.0,1.0) set point3 to (7.5,7.6) expected (7.5,7.6) actual= "
                            + t4.getPoint3());

        System.out.println("testing toString:");
        Triangle t5 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (!(t5.toString().equals("{(1.0,1.0),(3.0,3.0),(3.3,1.1)}")))
            System.out.println(
                    "\t ERROR - t5.toString() expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} ; actual=" + t5.toString());
        else
            System.out.println(
                    "\t OK - t5.toString() expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} ; actual=" + t5.toString());

        System.out.println("testing equals:");

        Triangle t6 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Triangle t7 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (!t6.equals(t7))
            System.out.println(
                    "\t ERROR - t6.equals(t7) expected true ; actual=" + t6.equals(t7) + ";  t6=" + t6 + " t7=" + t7);
        else
            System.out.println(
                    "\t OK - t6.equals(t7) expected true ; actual=" + t6.equals(t7) + ";  t6=" + t6 + " t7=" + t7);

        System.out.println("testing isValid:");

        Triangle t8 = new Triangle();
        if (!t8.isValid(new Point(1.0, 1.0), new Point(3.0, 3.0), new Point(3.3, 1.1)))

            System.out.println(
                    "\t ERROR - t8.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))expected true ; actual="
                            + t8.isValid(new Point(1.0, 1.0), new Point(3.0, 3.0), new Point(3.3, 1.1)));
        else
            System.out.println(
                    "\t OK - t8.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))expected true ; actual="
                            + t8.isValid(new Point(1.0, 1.0), new Point(3.0, 3.0), new Point(3.3, 1.1)));

        System.out.println("testing isAbove:");
        Triangle t9 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Triangle t10 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (t9.isAbove(t10))
            System.out.println("\t ERROR - t9.isAbove(t10) expected false ; actual=" + t9.isAbove(t10) + ";  t9=" + t9
                    + " t10=" + t10);
        else
            System.out.println("\t OK - t9.isAbove(t10) expected false ; actual=" + t9.isAbove(t10) + ";  t9=" + t9
                    + " t10=" + t10);

        System.out.println("testing isUnder:");

        Triangle t11 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Triangle t12 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (t11.isUnder(t12))
            System.out.println("\t ERROR - t11.isUnder(t12) expected false ; actual=" + t11.isUnder(t12) + ";  t11="
                    + t11 + " t12=" + t12);
        else
            System.out.println("\t OK - t11.isUnder(t12) expected false ; actual=" + t11.isUnder(t12) + ";  t11=" + t11
                    + " t12=" + t12);

        System.out.println("testing getArea:");

        Triangle t13 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        double area = t13.getArea();
        if (Math.abs(area - 2.2) > Triangle.EPSILON)
            System.out.println(
                    "\t ERROR - t13.getArea() expected 2.19999999999997 ; actual=" + t13.getArea() + ";  t13=" + t13);
        else
            System.out.println(
                    "\t OK - t13.getArea() expected 2.19999999999997 ; actual=" + t13.getArea() + ";  t13=" + t13);

        System.out.println("testing getPerimeter:");

        Triangle t14 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        double perimeter = t14.getPerimeter();
        if (Math.abs(perimeter - 7.054138417557592) > Triangle.EPSILON)
            System.out.println("\t ERROR - t14.getPerimeter() expected 7.054138417557592 ; actual=" + t14.getPerimeter()
                    + ";  t14=" + t14);
        else
            System.out.println("\t OK - t14.getPerimeter() expected 7.054138417557592 ; actual=" + t14.getPerimeter()
                    + ";  t14=" + t14);

        System.out.println("testing isIsosceles:");

        Triangle t15 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (t15.isIsosceles())
            System.out.println(
                    "\t ERROR - t15.isIsosceles() expected false ; actual=" + t15.isIsosceles() + ";  t15=" + t15);
        else
            System.out.println(
                    "\t OK - t15.isIsosceles() expected false ; actual=" + t15.isIsosceles() + ";  t15=" + t15);

        System.out.println("testing isPythagorean:");
        Triangle t16 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (t16.isPythagorean())
            System.out.println(
                    "\t ERROR - t16.isPythagorean expected false ; actual=" + t16.isPythagorean() + ";  t16=" + t16);
        else
            System.out.println(
                    "\t OK - t16.isPythagorean expected false ; actual=" + t16.isPythagorean() + ";  t16=" + t16);

        System.out.println("testing isContainedInCircle:");
        Triangle t17 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (!(t17.isContainedInCircle(2.0, 2.0, 2.0)))
            System.out.println("\t ERROR - t17.isContainedInCircle(2.0,2.0,2.0) expected true ; actual="
                    + t17.isContainedInCircle(2.0, 2.0, 2.0) + ";  t17=" + t17);
        else
            System.out.println("\t OK - t17.isContainedInCircle(2.0,2.0,2.0) expected true ; actual="
                    + t17.isContainedInCircle(2.0, 2.0, 2.0) + ";  t17=" + t17);

        System.out.println("testing isLocated:");

        Triangle t18 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        if (!(t18.isLocated()))
            System.out
                    .println("\t ERROR - t18.isLocated() expected true ; actual=" + t18.isLocated() + ";  t18=" + t18);
        else
            System.out.println("\t OK - t18.isLocated() expected true ; actual=" + t18.isLocated() + ";  t18=" + t18);

        System.out.println("testing isCongruent:");

        Triangle t19 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Triangle t20 = new Triangle(3.0, 3.0, 1.0, 1.0, 3.3, 1.1);
        if (!(t19.isCongruent(t20)))
            System.out.println("\t ERROR - t19.isCongruent(t20) expected true ; actual=" + t19.isCongruent(t20)
                    + ";  t19=" + t19 + " t20=" + t20);
        else
            System.out.println("\t OK - t19.isCongruent(t20) expected true ; actual=" + t19.isCongruent(t20) + ";  t19="
                    + t19 + " t20=" + t20);

        System.out.println("testing highestPoint:");
        Triangle t21 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Point p3 = t21.highestPoint();
        if (!(p3.equals(new Point(3.0, 3.0))))
            System.out.println("\t ERROR - t21.highestPoint() expected (3.0,3.0) ; actual=" + t21.highestPoint()
                    + ";  t21=" + t21);
        else
            System.out.println(
                    "\t OK - t21.highestPoint() expected (3.0,3.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21);

        System.out.println("testing lowestPoint:");

        Triangle t22 = new Triangle(1.0, 1.0, 3.0, 3.0, 3.3, 1.1);
        Point p4 = t22.lowestPoint();
        if (!(p4.equals(new Point(1.0, 1.0))))
            System.out.println(
                    "\t ERROR - t22.lowestPoint() expected (1.0,1.0) ; actual=" + t22.lowestPoint() + ";  t22=" + t22);
        else
            System.out.println(
                    "\t OK - t22.lowestPoint() expected (1.0,1.0) ; actual=" + t22.lowestPoint() + ";  t22=" + t22);

        System.out.println("********** Test Q2 Triangle - Finished **********\n");
    }
}
