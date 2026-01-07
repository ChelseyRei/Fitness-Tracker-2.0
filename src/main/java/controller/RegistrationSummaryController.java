package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.UserService;
import model.User;
import java.io.IOException;

public class RegistrationSummaryController {

    @FXML private Label nameLabel;
    @FXML private Label bmiLabel;
    @FXML private Label bmrLabel;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        User user = userService.getUserProfile();
        if (user != null) {
            nameLabel.setText(user.getName());
            bmiLabel.setText(String.format("%.2f", user.getBMI()));
            bmrLabel.setText(String.format("%.0f kcal/day", user.getBMR()));
        }
    }

    @FXML
    private void handleContinue() throws IOException {
        Main.setRoot("MainDashboard");
    }
}