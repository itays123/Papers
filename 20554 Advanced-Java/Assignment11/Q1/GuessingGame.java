
public class GuessingGame {
    public static void main(String[] args) throws Exception {
        AlphabetBank bank = new AlphabetBank();
        boolean guessValid;
        System.out.println(bank);

        guessValid = bank.recordGuess(';');
        System.out.println(bank + " - ; - " + guessValid);

        guessValid = bank.recordGuess('b');
        System.out.println(bank + " - b - " + guessValid);

        guessValid = bank.recordGuess('b');
        System.out.println(bank + " - b - " + guessValid);

        bank.reset();
        System.out.println(bank);
    }
}
