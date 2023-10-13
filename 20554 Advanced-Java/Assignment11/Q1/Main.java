import java.util.Scanner;

public class Main {

    private static Game game = new Game();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean startAgain; // will start another game if true
        do {
            startGame();
            startAgain = askStartAgain();
            if (startAgain)
                game.reset();
        } while (startAgain);
    }

    /**
     * The game loop. Record guesses until game is over.
     */
    public static void startGame() {
        while (!game.isOver()) {
            System.out.println(game); // print game state
            recordGuess();
        }
        System.out.println(game); // print game state after over
    }

    /**
     * The guess loop. Get guesses from the user until guess iw valid
     */
    public static void recordGuess() {
        String guess;
        while (true) {
            System.out.print("Your next guess: ");
            guess = scanner.nextLine();
            if (guess.length() != 1)
                System.out.println("Guess should be one letter only. Try again");
            else if (!game.recordGuess(guess.charAt(0)))
                System.out.println("Letter guessed should be from the available letters only. Try again.");
            else
                break; // successfully recorded guess
        }
    }

    /**
     * Asking for another game loop. Get responses from the user until user responds
     * yes/no.
     */
    public static boolean askStartAgain() {
        String res;
        while (true) {
            System.out.print("Start new game? (yes/no): ");
            res = scanner.nextLine();
            if (res.equals("yes"))
                return true;
            else if (res.equals("no"))
                return false;
        }
    }

}
