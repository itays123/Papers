import java.util.Random;

/**
 * Represents a multiple choice question - a question with one correct option
 * and {@code NUM_OF_INCORRECT_OPTIONS} incorrect ones.
 */
public class MultipleChoiceQuestion {

    public static final int NUM_OF_INCORRECT_OPTIONS = 3;

    private String question;
    private String[] options;
    private int correctOptionIndex;

    /**
     * Constructs a new MultipleChoiceQuestionObject
     */
    public MultipleChoiceQuestion(String question, String[] options, int correctOptionIndex) {
        this.question = question;

        if (options.length != NUM_OF_INCORRECT_OPTIONS + 1)
            throw new IllegalArgumentException(
                    "Number of options must be exactly " + (NUM_OF_INCORRECT_OPTIONS + 1));
        if (correctOptionIndex < 0 || correctOptionIndex > NUM_OF_INCORRECT_OPTIONS)
            throw new IllegalArgumentException(
                    "Correct option index must be between 0 and " + NUM_OF_INCORRECT_OPTIONS);

        this.options = new String[NUM_OF_INCORRECT_OPTIONS + 1];
        System.arraycopy(options, 0, this.options, 0, NUM_OF_INCORRECT_OPTIONS + 1);
        this.correctOptionIndex = correctOptionIndex;
    }

    /**
     * Shuffle the order of the questions
     */
    public void shuffleOptionsOrder() {
        Random rand = new Random();
        for (int i = 0; i < options.length; i++) {
            int randomIndexToSwap = rand.nextInt(options.length);
            String temp = options[randomIndexToSwap];
            options[randomIndexToSwap] = options[i];
            options[i] = temp;

            // if we swapped the correct answer, change correctAnswerIndex accordingly
            if (correctOptionIndex == i)
                correctOptionIndex = randomIndexToSwap;
            else if (correctOptionIndex == randomIndexToSwap)
                correctOptionIndex = i;
        }
    }

    /**
     * A getter for the question field
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the options. Note that these might be shuffled
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Checks if the option index given matches the correct option index
     */
    public boolean checkOption(int optionIndex) {
        return correctOptionIndex == optionIndex;
    }

}
