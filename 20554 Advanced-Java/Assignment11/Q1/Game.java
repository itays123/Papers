/**
 * The game logic class
 * 
 * @author Itay Schechner
 * @version 2023-10-13
 */
public class Game {

    /* The secret word object */
    private SecretWord secretWord;

    /* The alphabet bank object */
    private AlphabetBank bank;

    /* Records the number of guesses */
    private int numOfGuesses;

    /**
     * Creates a new game object
     */
    public Game() {
        this.secretWord = new SecretWord();
        this.bank = new AlphabetBank();
        this.numOfGuesses = 0;
    }

    /**
     * Resets a game
     */
    public void reset() {
        secretWord.reset();
        bank.reset();
        this.numOfGuesses = 0;
    }

    /**
     * Validates a guess, and records it.
     * 
     * @param guess the guess letetr
     * @return true if guess is valid, false otherwise
     */
    public boolean recordGuess(char guess) {
        // validate guess
        if (!bank.recordGuess(guess))
            return false;
        // record validated guess
        numOfGuesses++;
        secretWord.recordGuess(guess);
        return true;
    }

    /**
     * Checks if game is over
     * 
     * @return true if over, false otherwise
     */
    public boolean isOver() {
        return secretWord.isRevealed();
    }

    /**
     * Returns a string representation of the game state
     */
    @Override
    public String toString() {
        String result = "Secret word: " + secretWord + '\n';
        if (isOver())
            result += "Successfully guessed in " + numOfGuesses + " attempts.";
        else
            result += "Available letters: " + bank;
        return result;
    }

}
