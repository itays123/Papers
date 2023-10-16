public class Main {
    public static void main(String[] args) throws Exception {
        Polynom p = new Polynom(new int[] { 2, 0 }, new double[] { 1.0, 15.0 });
        System.out.println(p);
        Polynom q = new Polynom(new int[] { 0, 1, 2, 3 }, new double[] { 7.0, -1.0, -3.0, 8.0 });
        System.out.println(q);
        Polynom r = new Polynom(new int[] { 5, 3, 10, 0 }, new double[] { 6.5, -4.9, 2.8, -12.0 });
        System.out.println(r);
        System.out.println(p.plus(q));
        System.out.println(p.minus(q));
        System.out.println(q.derive());
    }
}
