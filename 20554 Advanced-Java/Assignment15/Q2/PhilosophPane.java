import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Represents a UI element that displays the state of a philosph thread
 */
public class PhilosophPane extends VBox {

    public static final Color HAS_STICK_COLOR = Color.WHEAT;
    public static final Color EMPTY_STICK_SLOT_COLOR = Color.GRAY;
    public static final double TITLE_FONT_SIZE = 18.0;

    private PhiliosphThread thread;
    private Label stateLabel, firstStickLabel, secondStickLabel;

    public PhilosophPane(int id, ChopsticksMonitor chopsticks) {

        this.stateLabel = new Label("");
        this.firstStickLabel = new Label();
        this.secondStickLabel = new Label();
        this.thread = new PhilosophUIThreadHandler(id, chopsticks);

        Label nameLabel = new Label("Philosoph " + id);
        nameLabel.setFont(new Font(TITLE_FONT_SIZE));
        Label sticksLabel = new Label("Holds sticks: ");
        Label needsLabel = new Label(
                "Needs sticks " + thread.getFirstChopstickNum() + " and " + thread.getSecondChopstickNum() + " to eat");

        markStickReleased(firstStickLabel);
        markStickReleased(secondStickLabel);

        getChildren().addAll(nameLabel, needsLabel, stateLabel, sticksLabel, firstStickLabel, secondStickLabel);
    }

    private void setBackgroundColor(Region reg, Color color) {
        reg.setBackground(
                new Background(new BackgroundFill(color, new CornerRadii(0), Insets.EMPTY)));
    }

    private void markStickAcquired(Label label, int stickNum) {
        setBackgroundColor(label, HAS_STICK_COLOR);
        label.setText("Stick " + stickNum);
    }

    private void markStickReleased(Label label) {
        setBackgroundColor(label, EMPTY_STICK_SLOT_COLOR);
        label.setText("Empty slot");
    }

    public void startThread() {
        thread.start();
    }

    public void stopThread() {
        thread.stopAfterRelease();
    }

    public class PhilosophUIThreadHandler extends PhiliosphThread {

        public PhilosophUIThreadHandler(int id, ChopsticksMonitor chopsticks) {
            super(id, chopsticks);
        }

        @Override
        protected void beforeStickScquire(int stickNum) {
            Platform.runLater(() -> stateLabel.setText("Acquiring stick " + stickNum + "..."));
        }

        @Override
        protected void afterStickAquire(int stickNum) {
            Platform.runLater(() -> {
                stateLabel.setText("");
                if (stickNum == getFirstChopstickNum())
                    markStickAcquired(firstStickLabel, stickNum);
                else
                    markStickAcquired(secondStickLabel, stickNum);
            });
        }

        @Override
        protected void onEating() {
            Platform.runLater(() -> stateLabel.setText("Eating..."));
        }

        @Override
        protected void afterStickRelease(int stickNum) {
            Platform.runLater(() -> {
                stateLabel.setText("");
                if (stickNum == getFirstChopstickNum())
                    markStickReleased(firstStickLabel);
                else
                    markStickReleased(secondStickLabel);
            });
        }

        @Override
        protected void onThinking() {
            Platform.runLater(() -> stateLabel.setText("Thinking..."));
        }

    }

}
