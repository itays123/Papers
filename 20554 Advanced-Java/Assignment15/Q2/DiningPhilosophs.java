import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiningPhilosophs extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("DiningPhilosophs.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Dining Philosophs");
        stage.setScene(scene);
        stage.show();
    }
}
