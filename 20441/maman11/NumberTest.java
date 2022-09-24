public class NumberTest {

    public static void main(String[] args) {
        for (int i = 1000; i < 10000; i++) {
            if (reverseUsingLoop(i) != reverseUsingAlgorithm(i))
                throw new RuntimeException("Number " + i + " cannot be reversed");
        }
        for (int i = -9999; i < -999; i++) {
            if (reverseUsingLoop(i) != reverseUsingAlgorithm(i))
                throw new RuntimeException("Number " + i + " cannot be reversed");
        }
        System.out.println("test ran successfully");
    }

    private static int reverseUsingLoop(int number) {
        int reversed = 0;
        while (number != 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number = number / 10;
        }
        return reversed;
    }

    private static int reverseUsingAlgorithm(int number) {
        // division will return the number of thousands in the number.
        int digitOf1000s = number / 1000;
        /*
         * the divison will return the number of 100s in the number. the 100s digit only
         * contains the division remaint of them by 10.
         */
        int digitOf100s = (number / 100) % 10;
        /*
         * The division will return the number of tens in then number. The 10s digit
         * only contains the division remaint of them by 10.
         */
        int digitOf10s = (number / 10) % 10;
        /*
         * expression will return the division remaint of the number by 10: the last
         * digit of the number.
         */
        int digitOf1s = number % 10;

        /*
         * reverse the number with this algorithm:
         * 
         * 1. the last digit is multiplied by 1000 and becomes the 1000s digit. The
         * 1000s digit becomes the last digit.
         * 
         * 2. the 10s digit is multiplied by 100 and becomed the 100s digit. The 100s
         * digit becomes the 10s digit by multipliying it by 10.
         *
         */
        return 1000 * digitOf1s + 100 * digitOf10s + 10 * digitOf100s + digitOf1000s;
    }

}
