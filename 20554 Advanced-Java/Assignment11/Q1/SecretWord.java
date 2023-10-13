/**
 * A class representing the secret word logic.
 * 
 * @author Itay Schechner
 * @version 2023-10-13
 */
public class SecretWord {

    /**
     * The secret word
     */
    private String word;

    /**
     * Letters revealed bitmap
     */
    private boolean[] revealedBitmap;

    /**
     * Word revealed indicator
     */
    private boolean revealed;

    /**
     * Generates a secret word from a random word from the word bank
     */
    public SecretWord() {
        this.reset();
    }

    /**
     * Create a secret word from a known word
     * 
     * @param word the secret word
     */
    public SecretWord(String word) {
        this.reset(word);
    }

    /**
     * Resets the secret word with a random word from the word bank
     * 
     */
    public void reset() {
        this.reset(WordBank.getRandomWord());
    }

    /**
     * Resets the secret word
     * 
     * @param word the new secret word
     */
    private void reset(String word) {
        this.word = word;
        this.revealedBitmap = new boolean[word.length()]; // all false
        this.revealed = false;
    }

    /**
     * Records the guess of a letter.
     * 
     * @param letter the letter guessed
     */
    public void recordGuess(char letter) {
        revealed = true; // change to false if there are unrevealed letetrs
        for (int i = 0; i < word.length(); i++) {
            if (revealedBitmap[i]) // letter revealed already
                continue;
            if (word.charAt(i) == letter) // correct guess
                revealedBitmap[i] = true;
            else // letter not revealed previously, and should not reveal now
                revealed = false;
        }
    }

    /**
     * @return true if word is revealed, false otherwise
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * A string representation of the secret word, hiding non-revealed letters
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            if (revealedBitmap[i])
                result += word.charAt(i);
            else
                result += '_';
        }
        return result;
    }

}
