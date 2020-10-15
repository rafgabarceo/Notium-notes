package dlsu.cpei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("welcome"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Welcome to Notium");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}