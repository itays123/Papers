import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class DiningPhilosophsController {

    @FXML
    private HBox hbox;
    private PhilosophPane[] philosophPanes;

    @FXML
    void initialize() {
        ChopsticksMonitor chopsticksMonitor = new ChopsticksMonitor();
        philosophPanes = new PhilosophPane[PhiliosphThread.NUM_PHILOSOPHS];
        for (int i = 0; i < philosophPanes.length; i++) {
            philosophPanes[i] = new PhilosophPane(i, chopsticksMonitor);
            hbox.getChildren().add(philosophPanes[i]);
        }

        for (int i = 0; i < philosophPanes.length; i++) {
            philosophPanes[i].startThread();
        }
    }

    @FXML
    void onStopButtonPressed(ActionEvent event) {
        for (int i = 0; i < philosophPanes.length; i++) {
            philosophPanes[i].stopThread();
        }
    }

}
