import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/Home.fxml"))));
        stage.setTitle("ThogaKade");
        stage.getIcons().add(new Image("img/thogakade-favicon-color.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
