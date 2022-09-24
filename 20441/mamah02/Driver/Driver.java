public class Driver {
    public static void main(String[] args) {
        AA a1 = new AA();
        AA a2 = new BB();
        AA a3 = new AA();
        AA a4 = new BB();
        BB b1 = new BB();
        BB b2 = new BB();

        System.out.print("Question 8: ");
        System.out.println(a3.equals(a1));

        System.out.print("Question 9: ");
        System.out.println(a4.equals(a2));

        System.out.print("Question 10: ");
        System.out.println(a1.equals(a2));

        System.out.print("Question 11: ");
        System.out.println(a2.equals(b1));

        System.out.print("Question 12: ");
        System.out.println(b1.equals(a1));

        System.out.print("Question 13: ");
        System.out.println(b2.equals(b1));

        System.out.print("Question 14: ");
        System.out.println(b1.equals(a4));
    }
}
