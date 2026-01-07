package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class RegistrationStartController {

    @FXML private Button registerButton;

    @FXML
    public void initialize() {
        // Navigate to the form when clicked
        registerButton.setOnAction(e -> navigate("RegistrationForm"));
    }

    // Helper method to use your Main.setRoot navigation safely
    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxml);
        }
    }
}