import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents an exam that came from a file
 */
public class FileBasedExam extends MultipleChoiceExam {

    public static final String DEFAULT_FILE_NAME = "exam.txt";

    /**
     * Constructs a file based exam based on the default file name
     */
    public FileBasedExam() throws IOException {
        this(DEFAULT_FILE_NAME);
    }

    /**
     * Constructs an exam from a file name. The file should be in the following
     * format:
     * <question> [line break] <correct answer> [line break] <incorrect answers,
     * separated by line breaks>
     */
    public FileBasedExam(String filePathname) throws IOException {
        super();
        final int linesPerQuestion = MultipleChoiceQuestion.NUM_OF_INCORRECT_OPTIONS + 2; // question + correct answer +
                                                                                          // incorrect answers
        File file = new File(filePathname);
        Scanner input = new Scanner(file);

        String currentLine, questionTitle = "";
        String[] options = new String[MultipleChoiceQuestion.NUM_OF_INCORRECT_OPTIONS + 1];
        int currentLineNumber = -1, optionIndex;

        while (input.hasNextLine()) {
            currentLine = input.nextLine();
            currentLineNumber = (currentLineNumber + 1) % linesPerQuestion;
            if (currentLineNumber == 0) {// first line of the question - question line
                questionTitle = currentLine;
                continue;
            }

            // option line
            optionIndex = currentLineNumber % linesPerQuestion - 1; // 5m+1=>0, 5m+2=>1, 5m+3=>2, 5m+4=>3
            options[optionIndex] = currentLine;
            if (currentLineNumber == linesPerQuestion - 1) // last line of the question
                addQuestion(new MultipleChoiceQuestion(questionTitle, options, 0));
        }

        input.close();

        reset();
    }

}
