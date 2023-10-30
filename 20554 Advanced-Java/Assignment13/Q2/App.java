public class App {
    public static void main(String[] args) throws Exception {
        NumberBuilder num = new NumberBuilder();
        System.out.println(num);
        num.addDigit(4);
        System.out.println(num);
        num.addDigit(7);
        System.out.println(num);
        num.negate();
        System.out.println(num);
        num.addDigit(5);
        System.out.println(num);
        num.addDecimalPoint();
        System.out.println(num);
        num.addDigit(0);
        System.out.println(num);
        num.addDigit(0);
        System.out.println(num);
        num.addDigit(8);
        System.out.println(num);
    }
}
