import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Reading polynom p");
        Polynom p = readPolynom();
        System.out.println("p = " + p + "\n\n");

        System.out.println("Reading polynom q");
        Polynom q = readPolynom();
        System.out.println("q = " + q);

        System.out.println("p + q = " + p.plus(q));
        System.out.println("p - q = " + p.minus(q));
        System.out.println("p' = " + p.derive());
        System.out.println("q' = " + q.derive());
        System.out.println("p == q? " + p.equals(q));
    }

    /**
     * Reads a polynomial from the user.
     * 
     * @return the polynomial read
     */
    public static Polynom readPolynom() {
        int[] powers;
        double[] scalars;
        int size;

        size = readInt("How many elements would you like to have in the polynom?: ");
        powers = new int[size];
        scalars = new double[size];
        for (int i = 0; i < size; i++) {
            System.out.println("Element #" + (i + 1) + ":");
            powers[i] = readInt("Element power: ");
            scalars[i] = readDouble("Element coefficient: ");
        }

        try {
            return new Polynom(powers, scalars);
        } catch (Exception e) {
            // with the way we designed our input, this should never happen.
            // return a dummy polynom for compilation to succeed.
            return new Polynom();
        }
    }

    /**
     * Reads an integer, given a message provided by the programmer.
     * 
     * @param message the message to display
     * @return the integer enetered by the user
     */
    public static int readInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    /**
     * Reads a double, given a message provided by the programmer.
     * 
     * @param message the message to display
     * @return the double enetered by the user
     */
    public static double readDouble(String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }
}
