
public class TriangleSaarTester
{
    public static void main(String[] args) 
    {
        System.out.println("********** Test Q2 Triangle - Start **********");

        Triangle tEmptyConstructor = new Triangle();
        Triangle tPointsConstructor = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1);
        Triangle tNegativePointsConstructor = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        Triangle tZeroPointsConstructor = new Triangle(0.0,0.0,0.0,0.0,0.0,0.0);
        Triangle tClosePointsConstructor = new Triangle(1.0,1.0,3.0,3.0,1.00001,1.00001); 
        Triangle tNegativeClosePointsConstructor = new Triangle(-1.0,-1.0,-3.0,-3.0,-1.00001,-1.00001); 
        Triangle tHalfPointsConstructor = new Triangle(1.0,-1.0,-3.0,3.0,-1.24,-1.00001); 
        Triangle t3PointsConstructor = new Triangle(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1)); 
        Triangle tNegative3PointsConstructor = new Triangle(new Point(-1.0,-1.0),new Point(-3.0,-3.0),new Point(-3.3,-1.1));
        Triangle tClose3PointsConstructor = new Triangle(new Point(1.0,1.0),new Point(3.0,3.0),new Point(1.000000001,1.000000001)); 
        Triangle tHalf3PointsConstructor = new Triangle(new Point(1.0,-1.0),new Point(-3.0,3.0),new Point(-3.3,-1.1)); 
        Triangle tNegativeClose3PointsConstructor = new Triangle(new Point(-1.0,-1.0),new Point(-3.0,-3.0),new Point(-1.000000001,-1.000000001)); 
        Triangle tCopyConstructor = new Triangle(tEmptyConstructor);
        Triangle tEqualsEmptyCheck=new Triangle(0.0,1.0,1.0,0.0,-1.0,0.0);

        System.out.println("testing first constructor and getters:");

        if (!(tEmptyConstructor.getPoint1().equals(new Point (1,0)))||!(tEmptyConstructor.getPoint2().equals(new Point (-1,0)))||!(tEmptyConstructor.getPoint3().equals(new Point (0,1))))
            System.out.println("\t ERROR -  constructor and getters,expected {(1.0,0.0),(-1,0),(0,1)} actual= "+tEmptyConstructor);
        else
            System.out.println("\t OK -  constructor and getters,expected {(1.0,0.0),(-1,0),(0,1)} actual= "+tEmptyConstructor);

        System.out.println("testing second  constructor and getters:");

        if (!(tPointsConstructor.getPoint1().equals(new Point (1.0,1.0)))||!(tPointsConstructor.getPoint2().equals(new Point (3.0,3.0)))||!(tPointsConstructor.getPoint3().equals(new Point (3.3,1.1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= "+tPointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= "+tPointsConstructor);

        if (!(tZeroPointsConstructor.getPoint1().equals(new Point (1,0)))||!(tZeroPointsConstructor.getPoint2().equals(new Point (-1,0)))||!(tZeroPointsConstructor.getPoint3().equals(new Point (0,1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tZeroPointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tZeroPointsConstructor);

        if (!(tClosePointsConstructor.getPoint1().equals(new Point (1,0)))||!(tClosePointsConstructor.getPoint2().equals(new Point (-1,0)))||!(tClosePointsConstructor.getPoint3().equals(new Point (0,1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tClosePointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tClosePointsConstructor);

        if (!(tNegativeClosePointsConstructor.getPoint1().equals(new Point (1,0)))||!(tNegativeClosePointsConstructor.getPoint2().equals(new Point (-1,0)))||!(tNegativeClosePointsConstructor.getPoint3().equals(new Point (0,1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tNegativeClosePointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0,-1.0,0.0,0.0,1.0)} actual= "+tNegativeClosePointsConstructor); 

        if (!(tHalfPointsConstructor.getPoint1().equals(new Point (1.0,-1.0)))||!(tHalfPointsConstructor.getPoint2().equals(new Point (-3.0,3.0)))||!(tHalfPointsConstructor.getPoint3().equals(new Point (-1.24,-1.00001))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,-1.0,-3.0,3.0,-1.24,-1.00001)} actual= "+tHalfPointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,-1.0,-3.0,3.0,-1.24,-1.00001)} actual= "+tHalfPointsConstructor);

        if (!(tNegativePointsConstructor.getPoint1().equals(new Point (-1.0, -1.0)))||!(tNegativePointsConstructor.getPoint2().equals(new Point (-3.0, -3.0)))||!(tNegativePointsConstructor.getPoint3().equals(new Point (-3.3, -1.1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1)} actual= "+tNegativePointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1)} actual= "+tNegativePointsConstructor);

        System.out.println("testing third  constructor and getters:");

        if (!(t3PointsConstructor.getPoint1().equals(new Point (1.0,1.0)))||!(t3PointsConstructor.getPoint2().equals(new Point (3.0,3.0)))||!(t3PointsConstructor.getPoint3().equals(new Point (3.3,1.1))))
            System.out.println("\t ERROR -  constructor 3 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= "+t3PointsConstructor);
        else
            System.out.println("\t OK -  constructor 3 and getters,expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} actual= "+t3PointsConstructor);

        if (!(tNegative3PointsConstructor.getPoint1().equals(new Point (-1.0, -1.0)))||!(tNegative3PointsConstructor.getPoint2().equals(new Point (-3.0, -3.0)))||!(tNegative3PointsConstructor.getPoint3().equals(new Point (-3.3, -1.1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(-1.0,-1.0),(-3.0,-3.0),(-3.3,-1.1)} actual= "+tNegative3PointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(-1.0,-1.0),(-3.0,-3.0),(-3.3,-1.1)} actual= "+tNegative3PointsConstructor);

        if (!(tHalf3PointsConstructor.getPoint1().equals(new Point (1.0, -1.0)))||!(tHalf3PointsConstructor.getPoint2().equals(new Point (-3.0, 3.0)))||!(tHalf3PointsConstructor.getPoint3().equals(new Point (-3.3, -1.1))))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,-1.0),(-3.0,3.0),(-3.3,-1.1)} actual= "+tHalf3PointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,-1.0),(-3.0,3.0),(-3.3,-1.1)} actual= "+tHalf3PointsConstructor);

        if (!(tClose3PointsConstructor.equals(tEmptyConstructor)))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tClose3PointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tClose3PointsConstructor);

        if (!(tNegativeClose3PointsConstructor.equals(tEmptyConstructor)))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tNegativeClose3PointsConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tNegativeClose3PointsConstructor);

        System.out.println("testing copy constructor: ");

        if (!(tCopyConstructor.getPoint1().equals(new Point (1.0,0.0)))||!(tCopyConstructor.getPoint2().equals(new Point (-1.0, 0.0)))||!(tCopyConstructor.getPoint3().equals(new Point (0.0, 1.0))))
            System.out.println("\t ERROR -  copy constructor  and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tCopyConstructor);
        else
            System.out.println("\t OK -  copy constructor  and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tCopyConstructor);

        tCopyConstructor = new Triangle(tPointsConstructor);
        if (tCopyConstructor.equals(tEmptyConstructor))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(1.0,0.0),(-1.0,0.0),(0.0,1.0)} actual= "+tEmptyConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(1.0,0.0),(-1,0),(0,1)} actual= "+tEmptyConstructor);

        tCopyConstructor = new Triangle(tNegativePointsConstructor);
        if (!(tCopyConstructor.equals(tNegativePointsConstructor)))
            System.out.println("\t ERROR -  constructor 2 and getters,expected {(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1)} actual= "+tEmptyConstructor);
        else
            System.out.println("\t OK -  constructor 2 and getters,expected {(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1)} actual= "+tEmptyConstructor);

        System.out.println("testing setters:"); 

        tEmptyConstructor.setPoint1(new Point(3.3,4.8));
        if (!(tEmptyConstructor.getPoint1().equals(new Point (3.3,4.8))))
            System.out.println("\t ERROR - setPoint1 point1 was (1.0,0.0) set point1 to (3.3,4.8) expected (3.3,4.8) actual= " + tEmptyConstructor.getPoint1());
        else
            System.out.println("\t OK - setPoint1 point1 was (1.0,0.0) set point1 to (3.3,4.8) expected (3.3,4.8) actual= " + tEmptyConstructor.getPoint1());

        tEmptyConstructor.setPoint1(new Point(-3.3,-4.8));
        if (!(tEmptyConstructor.getPoint1().equals(new Point (-3.3,-4.8))))
            System.out.println("\t ERROR - setPoint1 point1 was (1.0,0.0) set point1 to (-3.3,-4.8) expected (-3.3,-4.8) actual= " + tEmptyConstructor.getPoint1());
        else
            System.out.println("\t OK - setPoint1 point1 was (1.0,0.0) set point1 to (-3.3,-4.8) expected (-3.3,-4.8) actual= " + tEmptyConstructor.getPoint1());

        tEmptyConstructor.setPoint2(new Point(5.5,6.6));
        if (!(tEmptyConstructor.getPoint2().equals(new Point (5.5,6.6))))
            System.out.println("\t ERROR - setPoint2 point2 was (-1.0,0.0) set point2 to (5.5,6.6) expected (5.5,6.6) actual= " + tEmptyConstructor.getPoint2());
        else
            System.out.println("\t OK - setPoint2 point2 was (-1.0,0.0) set point2 to (5.5,6.6) expected (5.5,6.6) actual= " + tEmptyConstructor.getPoint2());

        tEmptyConstructor.setPoint1(new Point(-3.3,-4.755555555));
        if (!(tEmptyConstructor.getPoint1().equals(new Point (-3.3,-4.755555555))))
            System.out.println("\t ERROR - setPoint1 point1 was (1.0,0.0) set point1 to (-3.3,-4.755555555) expected (-3.3,-4.755555555) actual= " + tEmptyConstructor.getPoint1());
        else
            System.out.println("\t OK - setPoint1 point1 was (1.0,0.0) set point1 to (-3.3,-4.755555555) expected (-3.3,-4.755555555) actual= " + tEmptyConstructor.getPoint1());

        tEmptyConstructor.setPoint3(new Point(7.5,7.6));
        if (!(tEmptyConstructor.getPoint3().equals(new Point (7.5,7.6))))
            System.out.println("\t ERROR - setPoint3 point3 was (0.0,1.0) set point3 to (7.5,7.6) expected (7.5,7.6) actual= " + tEmptyConstructor.getPoint3());
        else
            System.out.println("\t OK - setPoint3 point3 was (0.0,1.0) set point3 to (7.5,7.6) expected (7.5,7.6) actual= " + tEmptyConstructor.getPoint3());

        tEmptyConstructor.setPoint3(new Point(0.0, 0.0));
        if (!(tEmptyConstructor.getPoint3().equals(new Point (0.0, 0.0))))
            System.out.println("\t ERROR - setPoint3 point3 was (0.0,1.0) set point3 to (0.0, 0.0) expected (0.0, 0.0) actual= " + tEmptyConstructor.getPoint3());
        else
            System.out.println("\t OK - setPoint3 point3 was (0.0,1.0) set point3 to (0.0, 0.0) expected (0.0, 0.0) actual= " + tEmptyConstructor.getPoint3());

        tEmptyConstructor.setPoint3(new Point(1.0, 1.0));
        if (!(tEmptyConstructor.getPoint3().equals(new Point (1.0, 1.0))))
            System.out.println("\t ERROR - setPoint3 point3 was (0.0,0.0) set point3 to (1.0, 1.0) expected (1.0, 1.0) actual= " + tEmptyConstructor.getPoint3());
        else
            System.out.println("\t OK - setPoint3 point3 was (0.0,0.0) set point3 to (1.0, 1.0) expected (1.0, 1.0) actual= " + tEmptyConstructor.getPoint3());

        tEmptyConstructor.setPoint2(new Point(7.7,8.8));
        tEmptyConstructor.setPoint3(new Point(7.70000001,8.800000000001));
        if (!(tEmptyConstructor.getPoint3().equals(new Point (1.0, 1.0))))
            System.out.println("\t ERROR - setPoint3 point2 was (7.7,8.8) set point3 to (7.70000001,8.800000000001) expected (7.7,8.8) actual= " + tEmptyConstructor.getPoint3());
        else
            System.out.println("\t OK - setPoint3 point2 was (7.7,8.8) set point3 to (7.70000001,8.800000000001) expected (7.7,8.8) actual= " + tEmptyConstructor.getPoint3());

        tEmptyConstructor.setPoint2(new Point(-5.5,-6.6));
        tEmptyConstructor.setPoint3(new Point(-5.50000001, -6.600000000001));
        if (!(tEmptyConstructor.getPoint3().equals(new Point (1.0, 1.0))))
            System.out.println("\t ERROR - setPoint3 point2 was (-5.5,-6.6) set point3 to (-5.50000001, -6.600000000001) expected (-5.5,-6.6) actual= " + tEmptyConstructor.getPoint3());
        else
            System.out.println("\t OK - setPoint3 point2 was (-5.5,-6.6) set point3 to (-5.50000001, -6.600000000001) expected (-5.5,-6.6) actual= " + tEmptyConstructor.getPoint3());

        System.out.println("testing toString:"); 

        if(!(tNegativePointsConstructor.toString().equals("{(-1.0,-1.0),(-3.0,-3.0),(-3.3,-1.1)}")))
            System.out.println("\t ERROR - tNegativePointsConstructor.toString() expected {(-1.0,-1.0),(-3.0,-3.0),(-3.3,-1.1)} ; actual=" + tNegativePointsConstructor.toString()) ;
        else
            System.out.println("\t OK - tNegativePointsConstructor.toString() expected {(-1.0,-1.0),(-3.0,-3.0),(-3.3,-1.1)} ; actual=" + tNegativePointsConstructor.toString()) ;

        if(!(tPointsConstructor.toString().equals("{(1.0,1.0),(3.0,3.0),(3.3,1.1)}")))
            System.out.println("\t ERROR - tPointsConstructor.toString() expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} ; actual=" + tPointsConstructor.toString()) ;
        else
            System.out.println("\t OK - tPointsConstructor.toString() expected {(1.0,1.0),(3.0,3.0),(3.3,1.1)} ; actual=" + tPointsConstructor.toString()) ;

        System.out.println("testing equals:"); 

        if (! tZeroPointsConstructor.equals(tClosePointsConstructor)) 
            System.out.println("\t ERROR - tZeroPointsConstructor.equals(tClosePointsConstructor) expected true ; actual=" + tZeroPointsConstructor.equals(tClosePointsConstructor) + ";  tZeroPointsConstructor=" + tZeroPointsConstructor + " tClosePointsConstructor=" + tClosePointsConstructor);
        else 
            System.out.println("\t OK - tZeroPointsConstructor.equals(tClosePointsConstructor) expected true ; actual=" + tZeroPointsConstructor.equals(tClosePointsConstructor) + ";  tZeroPointsConstructor=" + tZeroPointsConstructor + " tClosePointsConstructor=" + tClosePointsConstructor);

        if (! tNegativePointsConstructor.equals(tNegative3PointsConstructor)) 
            System.out.println("\t ERROR - tNegativePointsConstructor.equals(tNegative3PointsConstructor) expected true ; actual=" + tNegativePointsConstructor.equals(tNegative3PointsConstructor) + ";  tNegativePointsConstructor=" + tNegativePointsConstructor + " tNegative3PointsConstructor=" + tNegative3PointsConstructor);
        else 
            System.out.println("\t OK - tNegativePointsConstructor.equals(tNegative3PointsConstructor) expected true ; actual=" + tNegativePointsConstructor.equals(tNegative3PointsConstructor) + ";  tNegativePointsConstructor=" + tNegativePointsConstructor + " tNegative3PointsConstructor=" + tNegative3PointsConstructor);

        if ( tZeroPointsConstructor.equals(tEqualsEmptyCheck)) 
            System.out.println("\t ERROR - tZeroPointsConstructor.equals(tEqualsEmptyCheck) expected false ; actual=" + tZeroPointsConstructor.equals(tEqualsEmptyCheck) + ";  tZeroPointsConstructor=" + tZeroPointsConstructor + " tEqualsEmptyCheck=" + tEqualsEmptyCheck);
        else 
            System.out.println("\t OK - tZeroPointsConstructor.equals(tEqualsEmptyCheck) expected false ; actual=" + tZeroPointsConstructor.equals(tEqualsEmptyCheck) + ";  tZeroPointsConstructor=" + tZeroPointsConstructor + " tEqualsEmptyCheck=" + tEqualsEmptyCheck);

        System.out.println("testing isValid:"); 

        if(!tEmptyConstructor.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1)))
            System.out.println("\t ERROR - tEmptyConstructor.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))expected true ; actual=" + tEmptyConstructor.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))) ;
        else
            System.out.println("\t OK - tEmptyConstructor.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))expected true ; actual=" + tEmptyConstructor.isValid(new Point(1.0,1.0),new Point(3.0,3.0),new Point(3.3,1.1))) ;

        if(tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0)))
            System.out.println("\t ERROR - tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0))expected false ; actual=" + tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0)));
        else
            System.out.println("\t OK - tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0))expected false ; actual=" + tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0))); 

        if(tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-2.2,-2.2),new Point(-3.3,-3.3)))
            System.out.println("\t ERROR - tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-2.2,-2.2),new Point(-3.3,-3.3))expected false ; actual=" + tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-2.2,-2.2),new Point(-3.3,-3.3)));
        else
            System.out.println("\t OK - tEmptyConstructor.isValid(new Point(0.0,0.0),new Point(0.0,0.0),new Point(0.0,0.0))expected false ; actual=" + tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-2.2,-2.2),new Point(-3.3,-3.3))); 

        if(tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-1.5,-1.5),new Point(-1.10000001,-1.1000001)))
            System.out.println("\t ERROR - tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-1.5,-1.5),new Point(-1.10000001,-1.1000001) expected false ; actual=" + tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-1.5,-1.5),new Point(-1.10000001,-1.1000001)));
        else
            System.out.println("\t OK - tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-1.5,-1.5),new Point(-1.10000001,-1.1000001)expected false ; actual=" + tEmptyConstructor.isValid(new Point(-1.1,-1.1),new Point(-1.5,-1.5),new Point(-1.10000001,-1.1000001))); 

        System.out.println("testing isAbove:");

        if (!tPointsConstructor.isAbove(tNegativePointsConstructor)) 
            System.out.println("\t ERROR - tPointsConstructor.isAbove(tNegativePointsConstructor) expected true ; actual=" + tPointsConstructor.isAbove(tNegativePointsConstructor) + ";  tPointsConstructor=" + tPointsConstructor + " tNegativePointsConstructor=" + tNegativePointsConstructor);
        else  
            System.out.println("\t OK - tPointsConstructor.isAbove(tNegativePointsConstructor) expected true ; actual=" + tPointsConstructor.isAbove(tNegativePointsConstructor) + ";  tPointsConstructor=" + tPointsConstructor + " tNegativePointsConstructor=" + tNegativePointsConstructor);

        tPointsConstructor = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        Triangle t111 = new Triangle(-100.0,-100.0,-300.0,-300.0,-300.3,-100.1); 
        if (!tPointsConstructor.isAbove(t111)) 
            System.out.println("\t ERROR - tPointsConstructor.isAbove(t111) expected true ; actual=" + tPointsConstructor.isAbove(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);
        else  
            System.out.println("\t OK - tPointsConstructor.isAbove(t111) expected true ; actual=" + tPointsConstructor.isAbove(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);

        if (tPointsConstructor.isAbove(t111)) 
            System.out.println("\t OK - tPointsConstructor.isAbove(t111) expected false ; actual=" + tPointsConstructor.isAbove(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);
        else  
            System.out.println("\t ERROR - tPointsConstructor.isAbove(t111) expected false ; actual=" + tPointsConstructor.isAbove(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);

        System.out.println("testing isUnder:");

        tPointsConstructor = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        t111 = new Triangle(-100.0,-100.0,-300.0,-300.0,-300.3,-100.1); 

        if (tPointsConstructor.isUnder(t111)) 
            System.out.println("\t ERROR - tPointsConstructor.isUnder(t111) expected false ; actual=" + tPointsConstructor.isUnder(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);
        else  
            System.out.println("\t OK - tPointsConstructor.isUnder(t111) expected false ; actual=" + tPointsConstructor.isUnder(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);

        tPointsConstructor = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        t111 = new Triangle(1.1,1.1,-2.2,2.2,-3.3,-3.3); 

        if (tPointsConstructor.isUnder(t111)) 
            System.out.println("\t ERROR - tPointsConstructor.isUnder(t111) expected false ; actual=" + tPointsConstructor.isUnder(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);
        else  
            System.out.println("\t OK - tPointsConstructor.isUnder(t111) expected false ; actual=" + tPointsConstructor.isUnder(t111) + ";  tPointsConstructor=" + tPointsConstructor + " t111=" + t111);

        System.out.println("testing getArea:");

        Triangle t13 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        double area=t13.getArea();
        if (Math.abs(area-2.2)>Triangle.EPSILON)
            System.out.println("\t ERROR - t13.getArea() expected 2.19999999999997 ; actual=" + t13.getArea() + ";  t13=" + t13 );
        else  
            System.out.println("\t OK - t13.getArea() expected 2.19999999999997 ; actual=" + t13.getArea() + ";  t13=" + t13 );

        System.out.println("testing getPerimeter:");

        Triangle t14 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        double perimeter=t14.getPerimeter();
        if (Math.abs(perimeter-7.054138417557592)>Triangle.EPSILON)
            System.out.println("\t ERROR - t14.getPerimeter() expected 7.054138417557592 ; actual=" + t14.getPerimeter() + ";  t14=" + t14 );
        else  
            System.out.println("\t OK - t14.getPerimeter() expected 7.054138417557592 ; actual=" + t14.getPerimeter() + ";  t14=" + t14 );

        System.out.println("testing isIsosceles:");

        Triangle t15 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        if (t15.isIsosceles()) 
            System.out.println("\t ERROR - t15.isIsosceles() expected false ; actual=" + t15.isIsosceles() + ";  t15=" + t15 );
        else  
            System.out.println("\t OK - t15.isIsosceles() expected false ; actual=" + t15.isIsosceles() + ";  t15=" + t15 );

        t15 = new Triangle(8.081,0.0010382,4.765,5.6824,-1.8097,5.465); 
        if (!t15.isIsosceles()) 
            System.out.println("\t ERROR - t15.isIsosceles() expected true ; actual=" + t15.isIsosceles() + ";  t15=" + t15 );
        else  
            System.out.println("\t OK - t15.isIsosceles() expected true ; actual=" + t15.isIsosceles() + ";  t15=" + t15 );

        System.out.println("testing isPythagorean:");
        Triangle t16 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        if (t16.isPythagorean()) 
            System.out.println("\t ERROR - t16.isPythagorean expected false ; actual=" + t16.isPythagorean() + ";  t16=" + t16 );
        else  
            System.out.println("\t OK - t16.isPythagorean expected false ; actual=" + t16.isPythagorean() + ";  t16=" + t16 );

        t16 = new Triangle(0.0, 0.0, 3.0,0.0, 0.0, 4.0); 
        if (!t16.isPythagorean()) 
            System.out.println("\t ERROR - t16.isPythagorean expected true ; actual=" + t16.isPythagorean() + ";  t16=" + t16 );
        else  
            System.out.println("\t OK - t16.isPythagorean expected true ; actual=" + t16.isPythagorean() + ";  t16=" + t16 );

        System.out.println("testing isContainedInCircle:");

        Triangle t17 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        if (!(t17.isContainedInCircle(2.0,2.0,2.0))) 
            System.out.println("\t ERROR - t17.isContainedInCircle(2.0,2.0,2.0) expected true ; actual=" + t17.isContainedInCircle(2.0,2.0,2.0) + ";  t17=" + t17 );
        else  
            System.out.println("\t OK - t17.isContainedInCircle(2.0,2.0,2.0) expected true ; actual=" + t17.isContainedInCircle(2.0,2.0,2.0) + ";  t17=" + t17 );

        t17 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        if ((t17.isContainedInCircle(5.0,6.0,6.0))) 
            System.out.println("\t ERROR - t17.isContainedInCircle(5.0,6.0,6.0) expected false ; actual=" + t17.isContainedInCircle(5.0,6.0,6.0) + ";  t17=" + t17 );
        else  
            System.out.println("\t OK - t17.isContainedInCircle(5.0,6.0,6.0) expected false ; actual=" + t17.isContainedInCircle(5.0,6.0,6.0) + ";  t17=" + t17 );

        t17 = new Triangle(5.0,12.0,0.0,2.6834,11.0,6.0); 
        if (!(t17.isContainedInCircle(5.0,6.0,6.0))) 
            System.out.println("\t ERROR - t17.isContainedInCircle(5.0,6.0,6.0) expected true ; actual=" + t17.isContainedInCircle(5.0,6.0,6.0) + ";  t17=" + t17 );
        else  
            System.out.println("\t OK - t17.isContainedInCircle(5.0,6.0,6.0) expected true ; actual=" + t17.isContainedInCircle(5.0,6.0,6.0) + ";  t17=" + t17 );

        t17 = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        if ((t17.isContainedInCircle(-5.0,-6.0,6.0))) 
            System.out.println("\t ERROR - t17.isContainedInCircle(-5.0,-6.0,6.0) expected false ; actual=" + t17.isContainedInCircle(-5.0,-6.0,6.0) + ";  t17=" + t17 );
        else  
            System.out.println("\t OK - t17.isContainedInCircle(-5.0,-6.0,6.0) expected false ; actual=" + t17.isContainedInCircle(-5.0,-6.0,6.0) + ";  t17=" + t17 );

        t17 = new Triangle(-5.0,-12.0,-0.0,-2.6834,-11.0,-6.0); 
        if (!(t17.isContainedInCircle(-5.0,-6.0,6.0))) 
            System.out.println("\t ERROR - t17.isContainedInCircle(-5.0,-6.0,6.0) expected true ; actual=" + t17.isContainedInCircle(-5.0,-6.0,6.0) + ";  t17=" + t17 );
        else  
            System.out.println("\t OK - t17.isContainedInCircle(-5.0,-6.0,6.0) expected true ; actual=" + t17.isContainedInCircle(-5.0,-6.0,6.0) + ";  t17=" + t17 );

        System.out.println("testing isLocated:");

        Triangle t18 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        if (!(t18.isLocated())) 
            System.out.println("\t ERROR - t18.isLocated() expected true ; actual=" + t18.isLocated() + ";  t18=" + t18 );
        else  
            System.out.println("\t OK - t18.isLocated() expected true ; actual=" + t18.isLocated() + ";  t18=" + t18 );

        System.out.println("testing isCongruent:");

        Triangle t19 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        Triangle t20 = new Triangle(3.0,3.0,1.0,1.0,3.3,1.1); 
        if (!(t19.isCongruent(t20))) 
            System.out.println("\t ERROR - t19.isCongruent(t20) expected true ; actual=" + t19.isCongruent(t20) + ";  t19=" + t19 + " t20=" + t20);
        else  
            System.out.println("\t OK - t19.isCongruent(t20) expected true ; actual=" + t19.isCongruent(t20) + ";  t19=" + t19 + " t20=" + t20);

        System.out.println("testing highestPoint:");

        Triangle t21 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        Point p3=t21.highestPoint();
        if (!(p3.equals(new Point(3.0,3.0)))) 
            System.out.println("\t ERROR - t21.highestPoint() expected (3.0,3.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.highestPoint() expected (3.0,3.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );

        t21 = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        Point point3=t21.highestPoint();
        if (!(point3.equals(new Point(-1.0,-1.0)))) 
            System.out.println("\t ERROR - t21.highestPoint() expected (-1.0,-1.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.highestPoint() expected (-1.0,-1.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );

        t21 = new Triangle(1.0,1.0,3.0,1.0,3.3,0.7); 
        point3=t21.highestPoint();
        if (!(point3.equals(new Point(1.0,1.0)))) 
            System.out.println("\t ERROR - t21.highestPoint() expected (1.0,1.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.highestPoint() expected (1.0,1.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );

        t21 = new Triangle(-1.0,-1.0,-3.0,-1.0,-3.3,0.0); 
        point3=t21.highestPoint();
        if (!(point3.equals(new Point(-3.3,0.0)))) 
            System.out.println("\t ERROR - t21.highestPoint() expected (-3.3,0.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.highestPoint() expected (-3.3,0.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        t21 = new Triangle(-1.0,-1.0,-3.0,0.0,-3.3,0.0); 
        point3=t21.highestPoint();
        if (!(point3.equals(new Point(-3.3,0.0)))) 
            System.out.println("\t ERROR - t21.highestPoint() expected (-3.3,0.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.highestPoint() expected (-3.3,0.0) ; actual=" + t21.highestPoint() + ";  t21=" + t21 );

        System.out.println("testing lowestPoint:");

        Triangle t22 = new Triangle(1.0,1.0,3.0,3.0,3.3,1.1); 
        Point p4=t22.lowestPoint();
        if (!(p4.equals(new Point(1.0,1.0)))) 
            System.out.println("\t ERROR - t22.lowestPoint() expected (1.0,1.0) ; actual=" + t22.lowestPoint() + ";  t22=" + t22 );
        else  
            System.out.println("\t OK - t22.lowestPoint() expected (1.0,1.0) ; actual=" + t22.lowestPoint() + ";  t22=" + t22 );

        t21 = new Triangle(-1.0,-1.0,-3.0,-3.0,-3.3,-1.1); 
        point3=t21.lowestPoint();
        if (!(point3.equals(new Point(-3.0,-3.0)))) 
            System.out.println("\t ERROR - t21.lowestPoint() expected (-3.0,-3.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.lowestPoint() expected (-3.0,-3.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );

        t21 = new Triangle(1.0,1.0,3.0,0.7,3.3,0.7); 
        point3=t21.lowestPoint();
        if (!(point3.equals(new Point(3.0,0.7)))) 
            System.out.println("\t ERROR - t21.lowestPoint() expected (3.0,0.7) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.lowestPoint() expected (1.0,1.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );

        t21 = new Triangle(1.0,1.0,3.0,1.0,3.3,0.0); 
        point3=t21.lowestPoint();
        if (!(point3.equals(new Point(3.3,0.0)))) 
            System.out.println("\t ERROR - t21.lowestPoint() expected (3.3,0.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.lowestPoint() expected (3.3,0.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );
        t21 = new Triangle(1.0,1.0,3.0,0.0,3.3,0.0); 
        point3=t21.lowestPoint();
        if (!(point3.equals(new Point(3.0,0.0)))) 
            System.out.println("\t ERROR - t21.lowestPoint() expected (3.0,0.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );
        else  
            System.out.println("\t OK - t21.lowestPoint() expected (3.0,0.0) ; actual=" + t21.lowestPoint() + ";  t21=" + t21 );

        System.out.println("********** Test Q2 Triangle - Finished **********\n");
    }
}
