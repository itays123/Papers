
public class PointStudentTester {

    public static void main(String[] args) {
        final double EPSILON = 0.001;
        System.out.println("********** Test Q1 Point - Start **********");

        System.out.println("********** Note that these are only basic tests. You need to think which tests to add for each constructor and method in order to test your class thoroughly.**********");

        System.out.println("testing first constructor and getters:");

        Point p0 = new Point(0,0); 
        if (p0.getX() != 0 || p0.getY() != 0)
            System.out.println("\t ERROR -  constructor and getters,expected (0.0,0.0) actual= (" + p0.getX() + "," + p0.getY() +")");
        else
            System.out.println("\t OK -  constructor and getters expected (0.0,0.0) actual=(" + p0.getX() + "," + p0.getY() +")");
        p0 = new Point(0,4); 
        if (p0.getX() != 0 || p0.getY() != 4.0)
            System.out.println("\t ERROR - constructor and getters expected (0.0,4.0) actual= (" + p0.getX() + "," + p0.getY() +")");
        else
            System.out.println("\t OK -  constructor and getters expected (0.0,4.0) actual=(" + p0.getX() + "," + p0.getY() +")");
        p0 = new Point(3,0); 
        if (p0.getX() != 3.0 || p0.getY() != 0)
            System.out.println("\t ERROR - constructor and getters expected (3.0,0.0) actual= (" + p0.getX() + "," + p0.getY() +")");
        else
            System.out.println("\t OK - constructor and getters expected (3.0,0.0) actual=(" + p0.getX() + "," + p0.getY() +")");

        p0 = new Point(3.3, 4.4);
        if (p0.getX() != 3.3 || p0.getY() != 4.4)
            System.out.println("\t ERROR - constructor and getters (x, y): expected(3.3,4.4); actual=" + p0);
        else 
            System.out.println("\t OK - constructor and getters (x, y): expected(3.3,4.4); actual=" + p0);

        System.out.println("testing copy constructor: ");

        Point p1 = new Point(p0);
        if (p1.getX() != 3.3 || p1.getY() != 4.4)
            System.out.println("\t ERROR - copy constructor (other p): expected(3.3,4.4) ; actual=" + p1);
        else 
            System.out.println("\t OK - copy constructor (other p): expected(3.3,4.4) ; actual=" + p1);

        System.out.println("testing setters:"); 

        p0 = new Point(3,4); 
        p0.setX(5);
        if (p0.getX() != 5 || p0.getY() != 4)
            System.out.println("\t ERROR - setX point was (3.0,4.0) set x to 5.0 expected (5.0,4.0) actual= (" + p0.getX() + "," + p0.getY() +")");
        else
            System.out.println("\t OK - setX point was (3.0,4.0) set x to 5.0 expected (5.0,4.0)  actual=(" + p0.getX() + "," + p0.getY() +")");
        p0 = new Point(3,4); 
        p0.setY(5.0);
        if (p0.getX() != 3 || p0.getY() != 5)
            System.out.println("\t ERROR - setY point was (3.0,4.0) set y to 5.0 expected (3.0,5.0) actual= (" + p0.getX() + "," + p0.getY() +")");
        else
            System.out.println("\t OK - setY point was (3.0,4.0) set y to 5.0 expected (3.0,5.0)  actual=(" + p0.getX() + "," + p0.getY() +")");

        System.out.println("testing toString:"); 

        Point p2 = new Point (4.0, 3.0);
        if(!(p2.toString().equals("(4.0,3.0)")))
            System.out.println("\t ERROR - p2.toString() expected (4.0,3.0) ; actual=" + p2.toString()) ;
        else
            System.out.println("\t OK - p2.toString() expected (4.0,3.0) ; actual=" + p2.toString()) ;

        System.out.println("testing equals:"); 

        p1=new Point(2,3);
        p2=new Point(2,3);
        if (! p1.equals(p2)) 
            System.out.println("\t ERROR - p1.equals(p2) expected true ; actual=" + p1.equals(p2) + ";  p1=" + p1 + " p2=" + p2);
        else 
            System.out.println("\t OK - p1.equals(p2) expected true ; actual=" + p1.equals(p2) + ";  p1=" + p1 + " p2=" + p2);

        System.out.println("testing isAbove:");

        p1=new Point(2,4);
        p2=new Point(2,3);
        if (! p1.isAbove(p2)) 
            System.out.println("\t ERROR - p1.isAbove(p2) expected true ; actual=" + p1.isAbove(p2) + ";  p1=" + p1 + " p2=" + p2);
        else 
            System.out.println("\t OK - p1.isAbove(p2) expected true ; actual=" + p1.isAbove(p2) + ";  p1=" + p1 + " p2=" + p2);

        System.out.println("testing isUnder:"); 

        p1=new Point(2,3);
        p2=new Point(2,4);
        if (! p1.isUnder(p2)) 
            System.out.println("\t ERROR - p1.isUnder(p2) expected true ; actual=" + p1.isUnder(p2) + ";  p1=" + p1 + " p2=" + p2);
        else 
            System.out.println("\t OK - p1.isUnder(p2) expected true ; actual=" + p1.isUnder(p2) + ";  p1=" + p1 + " p2=" + p2);

        System.out.println("testing isLeft:"); 

        p1=new Point(2,4);
        p2=new Point(3,4);
        if (! p1.isLeft(p2)) 
            System.out.println("\t ERROR - p1.isLeft(p2) expected true ; actual=" + p1.isLeft(p2) + ";  p1=" + p1 + " p2=" + p2);
        else 
            System.out.println("\t OK - p1.isLeft(p2) expected true ; actual=" + p1.isLeft(p2) + ";  p1=" + p1 + " p2=" + p2);

        System.out.println("testing isRight:"); 

        p1=new Point(3,4);
        p2=new Point(2,4);
        if (! p1.isRight(p2)) 
            System.out.println("\t ERROR - p1.isRight(p2) expected true ; actual=" + p1.isRight(p2) + ";  p1=" + p1 + " p2=" + p2);
        else 
            System.out.println("\t OK - p1.isRight(p2) expected true ; actual=" + p1.isRight(p2) + ";  p1=" + p1 + " p2=" + p2);

        System.out.println("testing distance:"); 

        p0 = new Point (0,0);
        p2 = new Point (4.0, 3.0);
        double d=p0.distance(p2);
        if (Math.abs(d-5)>EPSILON)
            System.out.println("\t ERROR - p0.distance(p2) expected 5 ; actual=" + p0.distance(p2) + " p0=" + p0 + "; p2=" + p2);
        else
            System.out.println("\t OK - p0.distance(p2) expected 5 ; actual=" + p0.distance(p2) + " p0=" + p0 + "; p2=" + p2);

        System.out.println("testing quadrant:"); 

        p2 = new Point (4.0, 3.0);
        int quad =p2.quadrant();
        if (quad != 1) 
            System.out.println("\t ERROR - p2.quadrant() expected 1 ; actual=" + p2.quadrant() + " p2=" + p2);
        else
            System.out.println("\t OK - p2.quadrant() expected 1 ; actual=" + p2.quadrant() + " p2=" + p2);

        System.out.println("********** Test Q1 Point - Finished **********\n");
    }}

