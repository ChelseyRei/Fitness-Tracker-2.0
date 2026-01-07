package controller;

import javafx.fxml.FXML;
import java.io.IOException;

public class RegistrationStartController {
    
    @FXML
    private void handleStart() throws IOException {
        Main.setRoot("RegistrationForm");
    }
}