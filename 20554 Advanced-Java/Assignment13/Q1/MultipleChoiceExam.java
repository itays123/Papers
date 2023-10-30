import java.util.ArrayList;

/**
 * Represents an exam of multiple choice questions
 */
public class MultipleChoiceExam {

    private ArrayList<MultipleChoiceQuestion> questions;
    private int currentQuestionIndex;
    private int correctAnswers;

    /**
     * Constructs a new multiple choice exam
     */
    public MultipleChoiceExam() {
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to the exam
     */
    protected void addQuestion(MultipleChoiceQuestion question) {
        this.questions.add(question);
    }

    /**
     * Resets the exam - shuffles questions' option order, and resets the current
     * question index
     */
    public void reset() {
        currentQuestionIndex = 0;
        correctAnswers = 0;
        for (MultipleChoiceQuestion question : questions) {
            question.shuffleOptionsOrder();
        }
    }

    /**
     * Checks if has next question, or current question is the next one.
     */
    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size() - 1;
    }

    /**
     * Sets the exam state to face the next question
     */
    public void nextQuestion() {
        currentQuestionIndex++;
    }

    public MultipleChoiceQuestion getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    /**
     * Attempts an answer guess to the current question
     */
    public boolean checkOption(int optionIndex) {
        boolean result = questions.get(currentQuestionIndex).checkOption(optionIndex);
        if (result)
            correctAnswers++;
        return result;
    }

    /**
     * Calculates the grade, based on the correct questions count
     */
    public int getGrade() {
        return (int) (Math.ceil(100.0 * correctAnswers / questions.size()));
    }

}