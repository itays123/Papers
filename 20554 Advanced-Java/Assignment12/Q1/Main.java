public class Main {
    public static void main(String[] args) throws Exception {
        PolynomElement el = new PolynomElement(0, 7);
        System.out.println(el);

        el = new PolynomElement(0, 1);
        System.out.println(el);

        el = new PolynomElement(0, -1);
        System.out.println(el);

        el = new PolynomElement(5, 7);
        System.out.println(el);

        el = new PolynomElement(5, 1);
        System.out.println(el);

        el = new PolynomElement(5, -1);
        System.out.println(el);
    }
}
