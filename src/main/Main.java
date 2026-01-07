import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.UserService;
import model.User;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        UserService userService = new UserService();
        User user = userService.getUserProfile();

        // If user exists, go to Dashboard. Else, go to Registration.
        if (user != null) {
            setRoot("MainDashboard");
        } else {
            setRoot("RegistrationStart");
        }

        primaryStage.setTitle("HEAT Fitness Tracker");
        primaryStage.show();
    }

    // Static method for easy navigation from Controllers
    public static void setRoot(String fxml) throws IOException {
        // Updated path to match your new folder structure
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/view/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        // Load CSS if it exists
        String cssPath = Main.class.getResource("/css/style/" + fxml + ".css") != null 
                        ? Main.class.getResource("/css/style/" + fxml + ".css").toExternalForm() 
                        : null;
        
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}