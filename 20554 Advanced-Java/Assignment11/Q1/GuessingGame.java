
public class GuessingGame {
    public static void main(String[] args) throws Exception {
        AlphabetBank bank = new AlphabetBank();
        boolean guessValid;
        SecretWord word = new SecretWord("bank");
        System.out.println(word + " - " + bank);

        guessValid = bank.recordGuess(';');
        System.out.println(bank + " - ; - " + guessValid);

        guessValid = bank.recordGuess('b');
        word.recordGuess('b');
        System.out.println(bank + " - b - " + guessValid);
        System.out.println(word);

        guessValid = bank.recordGuess('b');
        System.out.println(bank + " - b - " + guessValid);

        bank.reset();
        System.out.println(bank);
    }
}
