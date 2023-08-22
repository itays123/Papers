import java.util.Scanner;

/*
* The program gets an age integer input from the user and calculates the target heart rate range using the age.
*/
public class HeartRate {
    public static void main(String[] args) {
        final int CONST = 220;
        final int MIN_MULTIPLIER = 65;
        final int MAX_MULTIPLIER = 85;

        // get the age as an integer from the program user
        Scanner scan = new Scanner(System.in);
        System.out.println("This program calculates your " + "target heart rate while exercising ");
        System.out.print("Enter your age: ");
        int age = scan.nextInt();

        /*
         * calculate the target minimum and maximum heartrate using this algorithm:
         * 
         * 1. subtract the age from the constant 220.
         * 
         * 2. multiply by 65% for the min value and by 85% for the max value.
         */
        int minTargetHeartRate = (CONST - age) * MIN_MULTIPLIER / 100;
        int maxTargetHeartRate = (CONST - age) * MAX_MULTIPLIER / 100;

        // print the result in the required format
        System.out.println("Your estimated target heart rate zone is " + minTargetHeartRate + " - " + maxTargetHeartRate
                + " beats per minute.");

    }
}