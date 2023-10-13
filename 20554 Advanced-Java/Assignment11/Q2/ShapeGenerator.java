import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShapeGenerator extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("ShapeGenerator.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Shape Generator");
        stage.setScene(scene);
        stage.show();
    }
}
