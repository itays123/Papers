import java.util.Scanner;

/*
 * The program gets a number input and an option input from the user,
 * and performs calculations on the number depending on the picked option.
 */
class Number {

    public static void main(String[] args) {
        final int OPTION_REVERSE_SIGN = 1;
        final int OPTION_REVERSE_NUMBER = 2;
        final int OPTION_REVERSE_BOTH = 3;
        final int MIN_NUM_VALUE = 1000;
        final int MAX_NUM_VALUE = 9999;

        // holds the value entered by the user, and manipulated depending on the option
        int number;
        boolean numberIsValid;

        // holds the integer code of the option picked.
        int option;
        boolean optionIsValid;

        Scanner scan = new Scanner(System.in);

        // get the number as an integer from the program user
        System.out.print("Plase enter a 4 digit number: ");
        number = scan.nextInt();

        /*
         * for a number to be valid, it has to have 4 digits and be either positive or
         * negative
         */
        numberIsValid = Math.abs(number) >= MIN_NUM_VALUE && Math.abs(number) <= MAX_NUM_VALUE;
        if (numberIsValid) {

            // get the option from the user
            System.out.println(OPTION_REVERSE_SIGN + ". Reverse sign.");
            System.out.println(OPTION_REVERSE_NUMBER + ". Reverse number.");
            System.out.println(OPTION_REVERSE_BOTH + ". Reverse sign and number.");
            System.out.println("Please choose an option:");
            option = scan.nextInt();

            // for an option to be valid, it has to be either 1, 2 or 3.
            optionIsValid = option == OPTION_REVERSE_SIGN || option == OPTION_REVERSE_NUMBER
                    || option == OPTION_REVERSE_BOTH;
            if (optionIsValid) {

                // reverse result sign if needed
                if (option == OPTION_REVERSE_SIGN || option == OPTION_REVERSE_BOTH)
                    number *= -1;

                // reverse number if needed
                if (option == OPTION_REVERSE_NUMBER || option == OPTION_REVERSE_BOTH) {
                    // get the digits of the number
                    int digitOfThousands = number / 1000;
                    int digitOfHunderds = (number / 100) % 10;
                    int digitOfTens = (number / 10) % 10;
                    int digitOfUnits = number % 10;

                    // reverse the number by switching the digits place
                    number = 1000 * digitOfUnits + 100 * digitOfTens + 10 * digitOfHunderds + digitOfThousands;

                }

                System.out.println("The result is");
                System.out.println(number);

            } else { // option not valid
                System.out.println("Illegal option – you must choose " + OPTION_REVERSE_SIGN + ", "
                        + OPTION_REVERSE_NUMBER + " or " + OPTION_REVERSE_BOTH);
            }

        } else { // number is not valid
            System.out.println("Illegal number – you must enter a 4 digit number");
        }
    }

}