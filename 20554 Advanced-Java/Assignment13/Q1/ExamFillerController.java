import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
        try {
            exam = new FileBasedExam();
            renderCurrentQuestion();
        } catch (IOException e) {
            renderText("There was an issue reading the exam data");
        }
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
        MultipleChoiceQuestion currentQuestion = exam.getCurrentQuestion();

        // initialize question label
        renderText(currentQuestion.getQuestion());

        // initialize option buttons
        RadioButton[] optionButtons = new RadioButton[MultipleChoiceQuestion.NUM_OF_INCORRECT_OPTIONS + 1];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new RadioButton(currentQuestion.getOptions()[i]);
            optionButtons[i].setOnAction(new AnswerButtonEventHandler(optionButtons, i));
        }
        statePane.getChildren().addAll(optionButtons);

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
        renderText("Finished!");
        renderText("Your grade: " + exam.getGrade());

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

    private void renderText(String string) {
        statePane.getChildren().add(new Label(string));
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

            if (exam.checkOption(answerIndex))
                renderText("Correct!");
            else
                renderText("Incorrect :(");

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
