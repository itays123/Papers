import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExamFillerController {

    @FXML
    private HBox actions;

    @FXML
    private VBox statePane;

    private MultipleChoiceExam exam;

    @FXML
    void initialize() {
        exam = new MultipleChoiceExam();
        exam.addQuestion(new MultipleChoiceQuestion("Question 1",
                new String[] { "Correct", "Incorrect 1", "Incorrect 2", "Incorrect 3" }, 0));
        exam.addQuestion(new MultipleChoiceQuestion("Question 2",
                new String[] { "Correct", "Incorrect 1", "Incorrect 2", "Incorrect 3" }, 0));
        exam.addQuestion(new MultipleChoiceQuestion("Question 3",
                new String[] { "Correct", "Incorrect 1", "Incorrect 2", "Incorrect 3" }, 0));
        exam.reset();
        renderCurrentQuestion();
    }

    // reset the screen content
    private void clearScreen() {
        statePane.getChildren().clear();
        actions.getChildren().clear();
    }

    /**
     * Render the current question on the screen
     */
    private void renderCurrentQuestion() {
        ObservableList<Node> children = statePane.getChildren();
        MultipleChoiceQuestion currentQuestion = exam.getCurrentQuestion();

        // initialize question label
        children.add(new Label(currentQuestion.getQuestion()));

        // initialize option buttons
        RadioButton[] optionButtons = new RadioButton[MultipleChoiceQuestion.NUM_OF_INCORRECT_OPTIONS + 1];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new RadioButton(currentQuestion.getOptions()[i]);
            optionButtons[i].setOnAction(new AnswerButtonEventHandler(optionButtons, i));
        }
        children.addAll(optionButtons);

    }

    /**
     * Render correct/incorrect based on answer score
     */
    private void renderAnswerFeedback(boolean isTrue) {
        statePane.getChildren().add(new Label(isTrue ? "Correct!" : "Incorrect :("));
    }

    /**
     * Render UI action - continue to next screen button
     */
    private void renderAction() {
        Button button = new Button();
        boolean hasNext = exam.hasNextQuestion();
        button.setText(hasNext ? "Next" : "Finish");
        button.setOnAction(new ActionButtonEventHandler(hasNext));
        actions.getChildren().add(button);
    }

    /**
     * Render finish exam, showing the score and an option to return
     */
    private void renderExamFinished() {

        // Add finish screen
        ObservableList<Node> children = statePane.getChildren();

        children.add(new Label("Finished!"));
        children.add(new Label("Your grade: " + exam.getGrade()));

        // Add reset action
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                clearScreen();
                exam.reset();
                renderCurrentQuestion();
            }

        });
        actions.getChildren().add(resetButton);
    }

    /**
     * An event handler for the answer button class
     */
    public class AnswerButtonEventHandler implements EventHandler<ActionEvent> {
        private RadioButton[] radioGroup;
        private int answerIndex;

        public AnswerButtonEventHandler(RadioButton[] radioGroup, int answerIndex) {
            this.radioGroup = radioGroup;
            this.answerIndex = answerIndex;
        }

        @Override
        public void handle(ActionEvent event) {
            // first, disable the option to change answers
            for (RadioButton radioButton : radioGroup) {
                radioButton.setDisable(true);
            }

            renderAnswerFeedback(exam.checkOption(answerIndex));
            renderAction();
        }
    }

    /**
     * An event handler for the action button class
     */
    public class ActionButtonEventHandler implements EventHandler<ActionEvent> {
        private boolean hasNext;

        public ActionButtonEventHandler(boolean hasNext) {
            this.hasNext = hasNext;
        }

        @Override
        public void handle(ActionEvent event) {
            // first, clear the action pane
            clearScreen();

            // if has next question, show it
            if (hasNext) {
                exam.nextQuestion();
                renderCurrentQuestion();
            }
            // otherwise, render the finish screen
            else {
                renderExamFinished();
            }
        }
    }
}
