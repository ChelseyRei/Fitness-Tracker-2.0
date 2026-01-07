package controller;

import javafx.fxml.FXML;
import java.io.IOException;

public class LogSelectionController {

    @FXML
    private void handleStrength() throws IOException {
        Main.setRoot("LogStrength");
    }

    @FXML
    private void handleCardio() throws IOException {
        Main.setRoot("LogCardio");
    }

    @FXML
    private void handleBack() throws IOException {
        Main.setRoot("MainDashboard");
    }
}