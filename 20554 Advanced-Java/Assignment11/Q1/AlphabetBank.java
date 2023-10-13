/**
 * This class keeps a record of which alphabet letters were guessed already, and
 * validates char guesses accordingly.
 * 
 * @author Itay Schechner
 * @version 2023-10-13
 */
public class AlphabetBank {

    /**
     * Constant for the first alphabet letter. Can easily change between languages.
     */
    public static final char FIRST_LETTER = 'a';

    /**
     * Constant for the number of alphabet letters. Can easily change between
     * languages.
     */
    public static final int NUMBER_OF_LETTERS = 26;

    private boolean[] bitmap; // a bitmap recorder. False = not guessed, True = guessed

    /**
     * Creates a new alphabet bank object
     */
    public AlphabetBank() {
        this.bitmap = new boolean[NUMBER_OF_LETTERS]; // sets all values to false.
    }

    /**
     * Resets the alphabet bank records
     */
    public void reset() {
        for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
            this.bitmap[i] = false;
        }
    }

    /**
     * Records a letter guess.
     * 
     * @param letter the guessed letter
     * @return true if guess is valid (a letter, and not guessed), false otherwise
     */
    public boolean recordGuess(char letter) {
        int letterIndex = letter - FIRST_LETTER;
        if (letterIndex < 0 || letterIndex >= NUMBER_OF_LETTERS) // char is not a letter
            return false;

        // guess is a letter. Record guessed, and return flase if already guessed.
        boolean guessed = this.bitmap[letterIndex];
        this.bitmap[letterIndex] = true;
        return !guessed;
    }

    /**
     * Returns a list of unguessed letters
     */
    @Override
    public String toString() {
        String result = "";
        char currentLetter;
        boolean commaPrefix = false;
        for (int i = 0; i < NUMBER_OF_LETTERS; i++) {
            if (this.bitmap[i]) // letter already guessed
                continue;
            if (commaPrefix)
                result += ',';
            currentLetter = (char) (FIRST_LETTER + i);
            result += currentLetter;
            commaPrefix = true; // prefix all letetr additions with a comma, except for the first one
        }
        return result;
    }

}
