package controller;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App Entry Point
 */
public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        
        // Load the initial screen (MainDashboard)
        scene = new Scene(loadFXML("MainDashboard"), 400, 794);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/Flame_icon.png")));
        stage.setScene(scene);
        stage.setTitle("Fitness Tracker");
        stage.show();
    }

    // This method allows other controllers to switch screens
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // This looks for files in src/main/resources/fxml/
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}