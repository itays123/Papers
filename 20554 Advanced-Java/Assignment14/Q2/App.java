
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        Parent root = loader.load();
        PhoneBookController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHidden(e -> controller.persist()); // persists phone book on window close
        stage.show();
    }
}
