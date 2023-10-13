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
    private boolean[] revealed;

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
        this.revealed = new boolean[word.length()]; // all false
    }

    /**
     * Records the guess of a letter.
     * 
     * @param letter the letter guessed
     * @return true if word is fully revealed, false otherwise
     */
    public boolean recordGuess(char letter) {
        boolean wordRevealed = true; // change to false if there are unrevealed letetrs
        for (int i = 0; i < word.length(); i++) {
            if (revealed[i]) // letter revealed already
                continue;
            if (word.charAt(i) == letter) // correct guess
                revealed[i] = true;
            else // letter not revealed previously, and should not reveal now
                wordRevealed = false;
        }
        return wordRevealed;
    }

    /**
     * A string representation of the secret wםrd, hiding non-revealed letters
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            if (revealed[i])
                result += word.charAt(i);
            else
                result += '_';
        }
        return result;
    }

}
